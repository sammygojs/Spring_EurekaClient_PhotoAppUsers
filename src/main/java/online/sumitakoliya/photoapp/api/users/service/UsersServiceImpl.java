package online.sumitakoliya.photoapp.api.users.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.sumitakoliya.photoapp.api.users.data.UserEntity;
import online.sumitakoliya.photoapp.api.users.data.UsersRepository;
import online.sumitakoliya.photoapp.api.users.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {
	
	UsersRepository usersRepository;
	
	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {
		
//		userDetails.setUserId(UUID.randomUUID().toString());
//		userDetails.setUserId(Long.getLong(UUID.randomUUID().toString()));
		userDetails.setUserId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
		
		userDetails.setEncryptedPassword("test");
//		userDetails.setUserId(userDetails);
		
		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
//		userEntity.setEncryptedPassword("test");
		
		usersRepository.save(userEntity);
		
		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
		 
		return returnValue;
	}
 
}
