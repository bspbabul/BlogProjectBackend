package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.config.AppContants;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payloads.UserDto;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 @Autowired
	 private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userdto) {
		User user = this.dtoToUser(userdto);
		//user.setPassword(passwordEncoder.encode(userdto.getPassword()));
		User saveUser = this.userRepo.save(user);
		return this.userToDto(saveUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userid) {
		User user = this.userRepo.findById(userid)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userid));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());

		User updatedUser = this.userRepo.save(user);
		return this.userToDto(updatedUser);

	}

	@Override
	public UserDto getbyUserId(Integer userid) {
		System.out.println(userid);
		User user = this.userRepo.findById(userid)
				
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userid));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getallUsers() {
		List<User> users = this.userRepo.findAll();

		return users.stream().map(e -> this.userToDto(e)).collect(Collectors.toList());
	}

	@Override
	public void deleteUser(Integer userid) {

		User user = this.userRepo.findById(userid)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userid));

		this.userRepo.delete(user);
	}

	public User dtoToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserDto userToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto registerNewUser(UserDto userdto) {
        
        Optional<User> existingUser = userRepo.findByEmail(userdto.getEmail());
        if (existingUser.isPresent()) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        User user = this.dtoToUser(userdto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepo.findById(AppContants.ROLE_NORMAL)
                            .orElseThrow(() -> new ResourceNotFoundException("Role", "id", AppContants.ROLE_NORMAL));
        user.getRoles().add(role);
        User saveUser = this.userRepo.save(user);
        return this.userToDto(saveUser);
    }

}
