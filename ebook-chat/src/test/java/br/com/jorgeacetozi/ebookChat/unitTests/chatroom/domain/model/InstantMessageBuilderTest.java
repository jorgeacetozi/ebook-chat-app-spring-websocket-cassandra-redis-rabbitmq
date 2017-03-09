package br.com.jorgeacetozi.ebookChat.unitTests.chatroom.domain.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

import org.junit.Test;

import br.com.jorgeacetozi.ebookChat.chatroom.domain.model.InstantMessage;
import br.com.jorgeacetozi.ebookChat.chatroom.domain.model.InstantMessageBuilder;
import br.com.jorgeacetozi.ebookChat.utils.SystemUsers;

public class InstantMessageBuilderTest {

	private final String chatRoomId = "123";
	private final String fromUser = "jorge_acetozi";
	private final String toUser = "michael_romeo";
	private final String publicMessageText = "Hello guys... I hope you are enjoying my eBook!";
	private final String privateMessageText = "I'm listening to Symphony X right now!";
	private final String systemMessageText = "This is a system message from admin user!";

	@Test
	public void shouldCreatePublicInstantMessage() {
		InstantMessage publicMessage = new InstantMessageBuilder()
				.newMessage()
				.withChatRoomId(chatRoomId)
				.publicMessage()
				.fromUser(fromUser)
				.withText(publicMessageText);

		assertThat(publicMessage.isPublic(), is(true));
		assertThat(publicMessage.isFromAdmin(), is(false));
		assertThat(publicMessage.getChatRoomId(), is(chatRoomId));
		assertThat(publicMessage.getFromUser(), is(fromUser));
		assertThat(publicMessage.getToUser(), is(nullValue()));
		assertThat(publicMessage.getText(), is(publicMessageText));
	}

	@Test
	public void shouldCreatePrivateInstantMessage() {
		InstantMessage privateMessage = new InstantMessageBuilder()
				.newMessage()
				.withChatRoomId(chatRoomId)
				.privateMessage()
				.toUser(toUser)
				.fromUser(fromUser).
				withText(privateMessageText);

		assertThat(privateMessage.isPublic(), is(false));
		assertThat(privateMessage.isFromAdmin(), is(false));
		assertThat(privateMessage.getChatRoomId(), is(chatRoomId));
		assertThat(privateMessage.getFromUser(), is(fromUser));
		assertThat(privateMessage.getToUser(), is(toUser));
		assertThat(privateMessage.getText(), is(privateMessageText));
	}

	@Test
	public void shouldCreateSystemInstantMessage() {
		InstantMessage systemMessage = new InstantMessageBuilder()
				.newMessage()
				.withChatRoomId(chatRoomId)
				.systemMessage()
				.withText(systemMessageText);

		assertThat(systemMessage.isPublic(), is(true));
		assertThat(systemMessage.isFromAdmin(), is(true));
		assertThat(systemMessage.getChatRoomId(), is(chatRoomId));
		assertThat(systemMessage.getFromUser(), is(SystemUsers.ADMIN.getUsername()));
		assertThat(systemMessage.getToUser(), is(nullValue()));
		assertThat(systemMessage.getText(), is(systemMessageText));
	}

}
