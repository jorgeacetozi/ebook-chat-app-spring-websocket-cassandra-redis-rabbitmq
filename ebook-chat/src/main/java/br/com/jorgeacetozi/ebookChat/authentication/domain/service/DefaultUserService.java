package br.com.jorgeacetozi.ebookChat.authentication.domain.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jorgeacetozi.ebookChat.authentication.domain.model.Role;
import br.com.jorgeacetozi.ebookChat.authentication.domain.model.User;
import br.com.jorgeacetozi.ebookChat.authentication.domain.repository.RoleRepository;
import br.com.jorgeacetozi.ebookChat.authentication.domain.repository.UserRepository;

@Service
public class DefaultUserService implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@Transactional
	public User createUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByName("ROLE_USER");
		user.addRoles(Arrays.asList(userRole));
		return userRepository.save(user);
	}
}
