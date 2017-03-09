package br.com.jorgeacetozi.ebookChat.integrationTests.authentication.domain.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.jorgeacetozi.ebookChat.authentication.domain.model.Role;
import br.com.jorgeacetozi.ebookChat.authentication.domain.model.User;
import br.com.jorgeacetozi.ebookChat.authentication.domain.repository.RoleRepository;
import br.com.jorgeacetozi.ebookChat.authentication.domain.repository.UserRepository;
import br.com.jorgeacetozi.ebookChat.authentication.domain.service.UserService;
import br.com.jorgeacetozi.ebookChat.integrationTests.test.EbookChatTest;

@RunWith(SpringRunner.class)
@EbookChatTest
public class DefaultUserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void shouldCreateNewUserWithRoleUser() {
		User user = new User("jorge_acetozi", "123456", "Jorge Acetozi", "jorge.acetozi@ebookchat.com.br");
		Role userRole = roleRepository.findByName("ROLE_USER");
		User createdUser = userService.createUser(user);
		
		assertThat(createdUser.getRoles(), Matchers.contains(userRole));
		assertThat(createdUser.getName(), is(user.getName()));
		assertThat(createdUser.getUsername(), is(user.getUsername()));
		assertThat(createdUser.getEmail(), is(user.getEmail()));
		
		userRepository.delete(createdUser);
	}
}
