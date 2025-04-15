package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.StrategyService;
import com.example.demo.dto.UserEmailDTO;
import com.example.demo.dto.UserOrdersDTO;

@RestController
@CrossOrigin
public class StrategyController {

    
	@Autowired
	private UserEmailDTO userEmailDTO;
	@Autowired
	StrategyService strategyService;

    @GetMapping(value= {"/api/backtest/{strategyId}"})
	public ResponseEntity<?> deployStrategy(@PathVariable Integer strategyId ) throws Exception{
		
		try{
			 strategyService.deployStrategy(userEmailDTO,strategyId);
		   return new ResponseEntity<String>("{\"status\":\"deployed\"}",new HttpHeaders(),HttpStatus.OK);
	   }
	   catch(Exception e){
		   return new ResponseEntity<String>("{\"status\":\"error\"}",new HttpHeaders(),HttpStatus.OK);
   
	   }
	}

	@GetMapping(value= {"/api/user-orders/{metaId}"})
	public ResponseEntity<?> getAllUserOrders(@PathVariable Integer metaId ) throws Exception{
		
		return new ResponseEntity<UserOrdersDTO>(
				strategyService.getAllUserOrders(metaId),new HttpHeaders(),HttpStatus.OK
				);
	}


}
