package com.example.demo.Services;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Dao.MaDAO;
import com.example.demo.Dao.MetaDataDAO;
import com.example.demo.Dao.StrategyDAO;
import com.example.demo.Dao.SymbolDAO;
import com.example.demo.Dao.UserDAO;
import com.example.demo.Entity.MaEntity;
import com.example.demo.Entity.MetaEntity;
import com.example.demo.Entity.UserEntity;
import com.example.demo.dto.Generic1;
import com.example.demo.dto.Generic2;
import com.example.demo.dto.MaBaseDto;
import com.example.demo.dto.MaDto;
import com.example.demo.dto.MetaData;
import com.example.demo.dto.RegisterDto;
import com.example.demo.dto.UserEmailDTO;

@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private SymbolDAO symbolDAO;

	@Autowired
	private MaDAO maDAO;


	@Autowired
	private StrategyDAO strategyDAO;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	private MetaDataDAO metaDAO;
	
	private static final Logger LOGGER=Logger.getLogger(UserService.class.getName());
	
	public String getEmployeeNameByEmail(String email) {
		return userDAO.findByEmailAndPasswordNotNull(email).getFname();
	}
	
	@Transactional
	public boolean register(RegisterDto cred  ) {
		UserEntity user=new UserEntity();
		//user=userDAO.findByEmail(cred.getEmail());
        if(userDAO.findByEmail(cred.getEmail())!=null){
            return false;
        }
        user.setFname(cred.getFname());
        user.setLname(cred.getLname());
        user.setEmail(cred.getEmail());
        user.setPassword( encoder.encode( cred.getPassword() ) );
			userDAO.save(user);
			return true;
		
		
	}

    public List<Generic1> getStrategies() {
        return strategyDAO.findAll().stream().map(m->mapper.map(m, Generic1.class)).collect(Collectors.toList());
    }

	public List<Generic2> getSymbolByTerm(String term) {
		return symbolDAO.findFirst10BySymbolStartingWithOrNameStartingWith(term,term).stream().map(m->mapper.map(m, Generic2.class)).collect(Collectors.toList());
	}

	@Transactional
	public int saveMeta(MetaData dto, UserEmailDTO emailDTO) {
		UserEntity user = userDAO.findByEmail(emailDTO.getEmail());
		LOGGER.info(emailDTO.getEmail()+ ":email");
		MetaEntity meta = new MetaEntity();
		meta.setStocks(dto.getStocks());
		meta.setCycles(dto.getCycles());
		meta.setEndTime(dto.getEndTime());
		meta.setStartTime(dto.getStartTime());
		meta.setInterval(dto.getInterval());
		meta.setTargetPercent(dto.getTargetPercent());
		meta.setStopLoss(dto.getStopLoss());
		meta.setStrategy(strategyDAO.getOne(dto.getStrategyId()));
		meta.setStatus("incomplete");
		meta.setSymbol(symbolDAO.getOne(dto.getSymbolId()));
		meta.setUser(user);
		meta = metaDAO.save(meta);


		return meta.getId();
	}

	@Transactional
	public void saveMa(MaDto dto, int metaId) {
		MetaEntity meta = metaDAO.findById(metaId).get();
		for(MaBaseDto ma:dto.getAvgs()){
			MaEntity ent = new MaEntity();
			ent.setAvg(ma.getValue());
			if(ma.getCondition() != null){
				ent.setEntryCond(ma.getCondition());
			}
			ent.setMeta(meta);
			maDAO.save(ent);
		}
		meta.setStatus("complete");
	
	}

	public List<Generic1> getUserStrategies(UserEmailDTO userEmailDTO) {
		UserEntity user = userDAO.findByEmail(userEmailDTO.getEmail());
		
		return metaDAO.findByUserAndStatus(user, "complete").
		stream().map(m->{
			Generic1 g = mapper.map(m.getStrategy(), Generic1.class);
			g.setDescp(m.getStatus());
			g.setId(m.getId());
			return g;
		}).collect(Collectors.toList());
	}

   
}