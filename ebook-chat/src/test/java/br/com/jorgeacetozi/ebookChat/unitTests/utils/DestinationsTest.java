package br.com.jorgeacetozi.ebookChat.unitTests.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import br.com.jorgeacetozi.ebookChat.utils.Destinations;

public class DestinationsTest {
	
	private final String chatRoomId = "123";
	
	@Test
	public void shouldGetPublicMessagesDestination() {
		assertThat(Destinations.ChatRoom.publicMessages("123"), is("/topic/" + chatRoomId + ".public.messages"));
	}

	@Test
	public void shouldGetPrivateMessagesDestination() {
		assertThat(Destinations.ChatRoom.privateMessages("123"), is("/queue/" + chatRoomId + ".private.messages"));
	}
	
	@Test
	public void shouldGetConnectedUsersDestination() {
		assertThat(Destinations.ChatRoom.connectedUsers("123"), is("/topic/" + chatRoomId + ".connected.users"));
	}
}
