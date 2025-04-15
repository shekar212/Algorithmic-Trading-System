package com.example.demo.Services;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Dao.MaDAO;
import com.example.demo.Dao.MetaDataDAO;
import com.example.demo.Dao.OrderDao;
import com.example.demo.Entity.MetaEntity;
import com.example.demo.Entity.OrderEntity;
import com.example.demo.dto.CandleDto;
import com.example.demo.dto.MetaData;
import com.example.demo.dto.OrderDTO;
import com.example.demo.dto.UserEmailDTO;
import com.example.demo.dto.UserOrdersDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

@Service
public class StrategyService {
    @Autowired
	private ModelMapper mapper;
    @Autowired
    MetaDataDAO metaDataDAO;
    
	@Autowired
	private MaDAO maDAO;

    @Autowired
    private OrderDao orderDao;

    private static final Logger LOGGER=Logger.getLogger(StrategyService.class.getName());
    
    @Transactional
    public void deployStrategy(UserEmailDTO userEmailDTO, int id) throws ParseException {
        try{
            boolean bought = false;
            MetaEntity entity = metaDataDAO.findById(id).get();
            LOGGER.info(":line 48");
            ResponseEntity<String> response = null;
            try{
                
                RestTemplate restTemplate = new RestTemplate();
                LOGGER.info(":line 55");
            String url = "https://alpha-vantage.p.rapidapi.com/query?interval=5min&function=TIME_SERIES_INTRADAY&symbol="+entity.getSymbol().getSymbol()+"&datatype=json&output_size=compact";
            LOGGER.info(":line 57 "+url);
            HttpHeaders headers = new HttpHeaders();
            headers.set("content-type", "application/octet-stream");
            headers.set("X-RapidAPI-Key", "1bdbf731a1msh11c8a2645a3ddc8p115bb6jsn88b596f7ed41");
            headers.set("X-RapidAPI-Host", "alpha-vantage.p.rapidapi.com");
            LOGGER.info(":line 60");
            HttpEntity<String> HttpEntity = new HttpEntity<>(null, headers);
            LOGGER.info(":line 61");
            response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity, String.class);
            
     
            
            }catch(Exception ex){
                LOGGER.info(ex.getMessage());
            }
            
            JsonObject jsonObject = null;
            try{
                jsonObject = (JsonObject) new JsonParser().parse(response.getBody());
                //getting root object
                  LOGGER.info(jsonObject.toString());
                }catch(Exception ex2){
                LOGGER.info(ex2.getMessage());
            }
            JsonObject intervalContent = jsonObject.get("Time Series (5min)").getAsJsonObject();
         ArrayList<CandleDto> candleDtos = new ArrayList<>();
        LOGGER.info(candleDtos.toString()+":line 61");
         for (Map.Entry<String, JsonElement> entry : intervalContent.entrySet()) {
     
             //this gets the dynamic keys
              String  dateKey = entry.getKey();
             CandleDto candleDto = new CandleDto();
             candleDto.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateKey));
              //you can get any thing now json element,array,object according to json.
     
              JsonObject jsonObject2 = entry.getValue().getAsJsonObject();
              JsonPrimitive closePrice = jsonObject2.get("4. close").getAsJsonPrimitive();
              candleDto.setPrice(closePrice.getAsDouble());
     
             candleDtos.add(candleDto);
          }
     
          Collections.reverse(candleDtos);
     
          List<Integer> values = maDAO.findByMeta(entity).stream().map(m->m.getAvg()).collect(Collectors.toList());
          ArrayList<List<Double>> smaList = new ArrayList<>();
          for(int val:values){
             smaList.add(calculateSma(candleDtos, val));
          }
          int cycles = 0;
          double boughtPrice = 0;
          double soldPrice = 0;
          boolean sold = false;
          for(int i =1; i<smaList.size()-1;i++){
                 if(cycles < entity.getCycles()){

                     
                      for(int j=0;j<smaList.get(i).size();j++){
                         if(!bought){
                         if(smaList.get(i).get(j) > smaList.get(i-1).get(j) && 
                         smaList.get(i+1).get(j) > smaList.get(i).get(j)) {
                             //buy signal rejected
                             LOGGER.info("buy signal detected:"+candleDtos.get(j).getPrice());
                             boughtPrice = candleDtos.get(j).getPrice();
                             OrderEntity order = new OrderEntity();
                             order.setPrice(boughtPrice);
                             order.setType("buy");
                             order.setMeta(entity);
                             order.setTime(candleDtos.get(j).getTime());
                             orderDao.save(order);
                             bought = true;
                         }
                      }  
                      else{
                         double targetPrice = (1+(entity.getTargetPercent()/100))*boughtPrice;
                         Double stopLoss = (1-(entity.getStopLoss()/100))*boughtPrice;
                         LOGGER.info(", TG:"+targetPrice+", SL:"+stopLoss);
                         if(candleDtos.get(j).getPrice() >= targetPrice || candleDtos.get(j).getPrice() <= stopLoss){
                             LOGGER.info("sell signal detected:"+candleDtos.get(j).getPrice()+", TG:"+targetPrice+", SL:"+stopLoss);
                             soldPrice = candleDtos.get(j).getPrice();
                             cycles++;
                             sold = true;
                             OrderEntity order = new OrderEntity();
                             order.setPrice(soldPrice);
                             order.setType("sell");
                             order.setTime(candleDtos.get(j).getTime());
                             order.setMeta(entity);
                             orderDao.save(order);
                             break;
                         }
                     }
                     }
                 }
          }
          
          if(sold!=true){
            soldPrice = candleDtos.get(candleDtos.size()-1).getPrice();
            sold = true;
            cycles++;
            OrderEntity order = new OrderEntity();
            order.setPrice(soldPrice);
            order.setType("sell");
            order.setTime(candleDtos.get(candleDtos.size()-1).getTime());
            order.setMeta(entity);
            orderDao.save(order);
            LOGGER.info("sell signal detected:"+soldPrice);
          }
          
     
        }catch(Exception e){
            LOGGER.info(e.getMessage());
        }
       
     
    }

    List<Double> calculateSma(List<CandleDto> candleDtos, int period) {
        Double[] sma = new Double[candleDtos.size() - period + 1];

        for (int i = period - 1; i < candleDtos.size(); i++) {
            double sum = 0;

            for (int j = i - period + 1; j <= i; j++) {
                sum += candleDtos.get(j).getPrice() ;
            }

            sma[i - period + 1] = sum / period;
        }

        return Arrays.asList(sma);
    }

	public UserOrdersDTO getAllUserOrders(Integer metaId) {
		UserOrdersDTO dto = new UserOrdersDTO();
		MetaEntity meta = metaDataDAO.getOne(metaId);
		dto.setMeta(mapper.map(meta, MetaData.class));
		List <OrderEntity> list = orderDao.findByMeta(meta);
        ArrayList<OrderDTO> orderDTOs = new ArrayList<>();
        for (OrderEntity order: list){
            orderDTOs.add(mapper.map(order,OrderDTO.class
            ));
        }
        dto.setOrders(orderDTOs);
		return dto;
	}

    
   

}
