package com.sp.service;



import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.sp.authentification.config.JwtTokenUtil;
import com.sp.entity.Users;
import com.sp.model.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

	@Autowired
    private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public UsersService() {
		
	}

	public List<Users> getUserList() {
		List<Users> result = 
				  StreamSupport.stream(userRepository.findAll().spliterator(), false)
				  .collect(Collectors.toList());
		return result;
	}

	public Users addUser( Users u) {
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		u.setWallet(5000);
		userRepository.save(u);
		return u;
	}
	
	public void deleteUser(Users u) {
		userRepository.delete(u);
	}
	
	public Users findUsersById(Integer id) {
		return userRepository.findById(id);
	}
	
	public List<Users> findByName(String name) {
		return userRepository.findByName(name);
	}
	
	public List<Users> findUsersIdByJwtToken(String jwtToken) {
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		return userRepository.findByName(username);
	}
}

