package br.com.jorgeacetozi.ebookChat.integrationTests.authentication.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import br.com.jorgeacetozi.ebookChat.authentication.domain.model.User;
import br.com.jorgeacetozi.ebookChat.authentication.domain.repository.UserRepository;
import br.com.jorgeacetozi.ebookChat.authentication.domain.service.UserService;
import br.com.jorgeacetozi.ebookChat.integrationTests.test.EbookChatTest;

@RunWith(SpringRunner.class)
@EbookChatTest
@WebAppConfiguration
public class AuthenticationControllerTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private WebApplicationContext wac;
    
    @Autowired
    private FilterChainProxy springSecurityFilter;

    private MockMvc mockMvc;

    @Before
    public void setup() {
    	this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilter).build();
    }
    
    @Test
    public void shouldNavigateToLoginPage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    public void shouldNavigateToNewAccountPage() throws Exception {
        this.mockMvc.perform(get("/new-account"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("new-account"));
    }
    
    @Test
    public void shouldAuthenticateAdminWithRoleAdmin() throws Exception {
        this.mockMvc.perform(formLogin("/login").user("admin").password("admin"))
                .andExpect(authenticated().withRoles("ADMIN"));
    }
    
    @Test
    public void shouldAuthenticateUserWithRoleUser() throws Exception {
    	User user = new User("jorge_acetozi", "123456", "Jorge Acetozi", "jorge.acetozi@ebookchat.com.br");
    	userService.createUser(user);
    	
        this.mockMvc.perform(formLogin("/login").user(user.getUsername()).password("123456"))
                .andExpect(authenticated().withRoles("USER"));
        
        userRepository.delete(user);
    }
    
    @Test
    public void shouldNotAuthenticateNonExistingUser() throws Exception {
        this.mockMvc.perform(formLogin("/login").user("ghost").password("ghost"))
                .andExpect(unauthenticated());
    }
}
