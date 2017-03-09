package br.com.jorgeacetozi.ebookChat.integrationTests.chatroom.api;

import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jorgeacetozi.ebookChat.chatroom.domain.model.ChatRoom;
import br.com.jorgeacetozi.ebookChat.integrationTests.test.EbookChatTest;

@RunWith(SpringRunner.class)
@EbookChatTest
@WebAppConfiguration
public class ChatRoomControllerTest {
	
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
    public void shouldNotCreateChatRoomWhenThereIsNoAuthentication() throws Exception {
    	ChatRoom chatRoom = new ChatRoom("123", "Dream Theater", "Discuss about best band ever!");
    	
        this.mockMvc.perform(post("/chatroom")
        		.content(new ObjectMapper().writeValueAsString(chatRoom)))
                .andDo(print())
                .andExpect(unauthenticated());
    }
    
    @Test
    public void shouldCreateChatRoomWhenUserHasRoleAdmin() throws Exception {
    	ChatRoom chatRoom = new ChatRoom("123", "Dream Theater", "Discuss about best band ever!");
    	
        this.mockMvc.perform(
	        			post("/chatroom")
	        			.with(user("admin").roles("ADMIN"))
	        			.contentType(MediaType.APPLICATION_JSON)
	        			.content(new ObjectMapper().writeValueAsString(chatRoom))
	        		)
	                .andDo(print())
	                .andExpect(status().isCreated())
        			.andExpect(jsonPath("$.id", is(chatRoom.getId())))
        			.andExpect(jsonPath("$.name", is(chatRoom.getName())))
        			.andExpect(jsonPath("$.description", is(chatRoom.getDescription())));
    }
    
    @Test
    public void shouldNotCreateChatRoomWhenUserDoesntHaveRoleAdmin() throws Exception {
    	ChatRoom chatRoom = new ChatRoom("123", "Dream Theater", "Discuss about best band ever!");
    	
        this.mockMvc.perform(
	        			post("/chatroom")
	        			.with(user("xuxa").roles("USER"))
	        			.contentType(MediaType.APPLICATION_JSON)
	        			.content(new ObjectMapper().writeValueAsString(chatRoom))
	        		)
	                .andDo(print())
	                .andExpect(status().isForbidden());
    }
}
