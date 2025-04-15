package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Services.UserService;
import com.example.demo.dto.Generic1;
import com.example.demo.dto.Generic2;
import com.example.demo.dto.MaDto;
import com.example.demo.dto.MetaData;
import com.example.demo.dto.UserEmailDTO;
import com.example.demo.dto.UserOrdersDTO;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
	UserService userService;
	@Autowired
	private UserEmailDTO userEmailDTO;

    @GetMapping(value= {"/api/strategies"})
	public ResponseEntity<?> getAllStrategies( ) throws Exception{
		
		return new ResponseEntity<List<Generic1>>(
				userService.getStrategies(),new HttpHeaders(),HttpStatus.OK
				);
	}

    @GetMapping(value= {"/api/symbol/{term}"})
	public ResponseEntity<?> getSymbols(@PathVariable String term ) throws Exception{
		
		return new ResponseEntity<List<Generic2>>(
				userService.getSymbolByTerm(term),new HttpHeaders(),HttpStatus.OK
				);
	}

    @PostMapping("/api/save-metadata/{strategyId}")
	public ResponseEntity<?> saveMeta(@RequestBody MetaData dto) throws Exception{
		try{
        int id = userService.saveMeta(dto,userEmailDTO);
        return new ResponseEntity<String>("{\"status\":\"saved\", \"id\":"+id+"}",new HttpHeaders(),HttpStatus.OK);
    }
    catch(Exception e){
        return new ResponseEntity<String>("{\"status\":\"error\"}",new HttpHeaders(),HttpStatus.OK);

    }
		
	}

	@PostMapping("/api/save-ma/{metaId}")
	public ResponseEntity<?> saveMa(@PathVariable int metaId,@RequestBody MaDto dto) throws Exception{
		
		try{
         userService.saveMa(dto,metaId);
        return new ResponseEntity<String>("{\"status\":\"saved\"}",new HttpHeaders(),HttpStatus.OK);
    }
    catch(Exception e){
        return new ResponseEntity<String>("{\"status\":\"error\"}",new HttpHeaders(),HttpStatus.OK);

    }
		
	}

	@GetMapping(value= {"/api/user-strategies"})
	public ResponseEntity<?> getAllStrategiesComp( ) throws Exception{
		
		return new ResponseEntity<List<Generic1>>(
				userService.getUserStrategies(userEmailDTO),new HttpHeaders(),HttpStatus.OK
				);
	}

	

}
