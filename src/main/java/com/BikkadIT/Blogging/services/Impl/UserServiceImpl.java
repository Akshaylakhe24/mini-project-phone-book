package com.BikkadIT.Blogging.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BikkadIT.Blogging.exceptions.*;
import com.BikkadIT.Blogging.model.User;
import com.BikkadIT.Blogging.paylods.UserDto;
import com.BikkadIT.Blogging.repository.UserRepo;
import com.BikkadIT.Blogging.service.UserServiceI;

@Service
public class UserServiceImpl implements UserServiceI{
	
	@Autowired
	private UserRepo userRepo; 
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createrUser(UserDto userDto) {
		// TODO Auto-generated method stub
	
		User user= this.dtoToUser(userDto); 
		
		User savedUser = this.userRepo.save(user);
		 
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		
		return userDto1; 
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// TODO Auto-generated method stub
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		// TODO Auto-generated method stub
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer UserId) {
		// TODO Auto-generated method stub
		
		User user= this.userRepo.findById(UserId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", UserId));
		this.userRepo.delete(user);
		
	}
	
	private User dtoToUser(UserDto userDto)
	{
		
		User user = this.modelMapper.map(userDto, User.class);
		
		
		
//		User user = new User(); 
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
		
	}
	
	private UserDto userToDto(User user)
	{
		UserDto userDto = this.modelMapper.map(user, UserDto.class); 
		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
		
	}

}
