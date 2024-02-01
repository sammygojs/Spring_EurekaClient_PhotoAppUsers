package online.sumitakoliya.photoapp.api.users.ui.controllers;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import online.sumitakoliya.photoapp.api.users.service.UsersService;
import online.sumitakoliya.photoapp.api.users.shared.UserDto;
import online.sumitakoliya.photoapp.api.users.ui.model.CreateUserRequestModel;
import online.sumitakoliya.photoapp.api.users.ui.model.CreateUserResponseModel;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	UsersService usersService;
	
	@GetMapping("/status")
	public String status() {
		return "Working on port : "+env.getProperty("local.server.port");
	}
	
	@PostMapping
	public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
		
//		userDetails.setUserId()
		ModelMapper modelMapper = new ModelMapper();
//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
//		usersService.createUser(userDto);
		UserDto createdUser = usersService.createUser(userDto);
//		System.out.println(createdUser);
		
		CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);
		
//		return "create user method is called";
		return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
	}
}
