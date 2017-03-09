package br.com.jorgeacetozi.ebookChat.unitTests.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import br.com.jorgeacetozi.ebookChat.chatroom.domain.model.InstantMessage;
import br.com.jorgeacetozi.ebookChat.utils.SystemMessages;
import br.com.jorgeacetozi.ebookChat.utils.SystemUsers;

public class SystemMessagesTest {
	
	private final String chatRoomId = "123";
	private final String username = "jorge_acetozi";

	@Test
	public void shouldGetWelcomeInstantMessage() {
		InstantMessage welcomeMessage = SystemMessages.welcome(chatRoomId, username);
		
		assertThat(welcomeMessage.isPublic(), is(true));
		assertThat(welcomeMessage.isFromAdmin(), is(true));
		assertThat(welcomeMessage.getChatRoomId(), is(chatRoomId));
		assertThat(welcomeMessage.getFromUser(), is(SystemUsers.ADMIN.getUsername()));
		assertThat(welcomeMessage.getToUser(), is(nullValue()));
		assertThat(welcomeMessage.getText(), is(username + " joined us :)"));
	}
	
	@Test
	public void shouldGetGoodbyeInstantMessage() {
		InstantMessage goodbyeMessage = SystemMessages.goodbye(chatRoomId, username);
		
		assertThat(goodbyeMessage.isPublic(), is(true));
		assertThat(goodbyeMessage.isFromAdmin(), is(true));
		assertThat(goodbyeMessage.getChatRoomId(), is(chatRoomId));
		assertThat(goodbyeMessage.getFromUser(), is(SystemUsers.ADMIN.getUsername()));
		assertThat(goodbyeMessage.getToUser(), is(nullValue()));
		assertThat(goodbyeMessage.getText(), is(username + " left us :("));
	}
}
