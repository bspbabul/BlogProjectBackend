package com.example.demo.service;

import java.util.List;

import com.example.demo.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto userdto);	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user,Integer userid);
	
	UserDto getbyUserId(Integer userid);
	
	List<UserDto> getallUsers();
	
	void deleteUser(Integer userid);

}
