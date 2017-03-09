package br.com.jorgeacetozi.ebookChat.integrationTests.chatroom.domain.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import br.com.jorgeacetozi.ebookChat.chatroom.domain.model.ChatRoom;
import br.com.jorgeacetozi.ebookChat.chatroom.domain.model.ChatRoomUser;
import br.com.jorgeacetozi.ebookChat.chatroom.domain.model.InstantMessage;
import br.com.jorgeacetozi.ebookChat.chatroom.domain.model.InstantMessageBuilder;
import br.com.jorgeacetozi.ebookChat.chatroom.domain.repository.ChatRoomRepository;
import br.com.jorgeacetozi.ebookChat.chatroom.domain.repository.InstantMessageRepository;
import br.com.jorgeacetozi.ebookChat.chatroom.domain.service.ChatRoomService;
import br.com.jorgeacetozi.ebookChat.chatroom.domain.service.InstantMessageService;
import br.com.jorgeacetozi.ebookChat.integrationTests.test.EbookChatTest;
import br.com.jorgeacetozi.ebookChat.utils.SystemMessages;
import br.com.jorgeacetozi.ebookChat.utils.SystemUsers;

@RunWith(SpringRunner.class)
@EbookChatTest
public class CassandraInstantMessageServiceTest {
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Autowired
	private InstantMessageService instantMessageService;
	
	@Autowired
	private ChatRoomRepository chatRoomRepository;
	
	@Autowired
	private InstantMessageRepository instantMessageRepository;
	
	private ChatRoom chatRoom;
	
	@Before
	public void setup() {
		chatRoom = new ChatRoom("123", "Dream Theater", "Discuss about best band ever!");
		chatRoomService.save(chatRoom);
	}
	
	@After
	public void destroy() {
		chatRoomRepository.delete(chatRoom);
		instantMessageRepository.deleteAll();
	}
	
	@Test
	public void shouldReceiveWelcomeAndGoodByeMessagesFromAdmin() {
		final ChatRoomUser jorgeAcetozi = new ChatRoomUser("jorge_acetozi");
		List<InstantMessage> jorgeAcetoziMessages;
		
		chatRoomService.join(jorgeAcetozi, chatRoom);
		
		jorgeAcetoziMessages = instantMessageService.findAllInstantMessagesFor("jorge_acetozi", "123");
		assertThat(jorgeAcetoziMessages.size(), is((1)));
		
		InstantMessage welcomeMessage = jorgeAcetoziMessages.get(0);
		
		assertThat(welcomeMessage.getUsername(), is(jorgeAcetozi.getUsername()));
		assertThat(welcomeMessage.getChatRoomId(), is(chatRoom.getId()));
		assertThat(welcomeMessage.getFromUser(), is(SystemUsers.ADMIN.getUsername()));
		assertThat(welcomeMessage.getToUser(), is(nullValue()));
		assertThat(welcomeMessage.getText(), is(SystemMessages.welcome(chatRoom.getId(), jorgeAcetozi.getUsername()).getText()));
		
		chatRoomService.leave(jorgeAcetozi, chatRoom);
		
		jorgeAcetoziMessages = instantMessageService.findAllInstantMessagesFor(jorgeAcetozi.getUsername(), chatRoom.getId());
		assertThat(jorgeAcetoziMessages.size(), is((2)));
		
		InstantMessage goodbyeMessage = jorgeAcetoziMessages.get(1);
		
		assertThat(goodbyeMessage.getUsername(), is(jorgeAcetozi.getUsername()));
		assertThat(goodbyeMessage.getChatRoomId(), is(chatRoom.getId()));
		assertThat(goodbyeMessage.getFromUser(), is(SystemUsers.ADMIN.getUsername()));
		assertThat(goodbyeMessage.getToUser(), is(nullValue()));
		assertThat(goodbyeMessage.getText(), is(SystemMessages.goodbye(chatRoom.getId(), jorgeAcetozi.getUsername()).getText()));
	}
	
	@Test
	public void shouldStoreEntireConversationWithPublicMessage() {
		final ChatRoomUser jorgeAcetozi = new ChatRoomUser("jorge_acetozi");
		List<InstantMessage> jorgeAcetoziMessages;
		
		chatRoomService.join(jorgeAcetozi, chatRoom);
		
		InstantMessage publicMessage = new InstantMessageBuilder()
				.newMessage()
				.withChatRoomId(chatRoom.getId())
				.publicMessage()
				.fromUser(jorgeAcetozi.getUsername())
				.withText("There's no people to chat with me here... I'm leaving now :(");

		chatRoomService.sendPublicMessage(publicMessage);
		chatRoomService.leave(jorgeAcetozi, chatRoom);
		
		jorgeAcetoziMessages = instantMessageService.findAllInstantMessagesFor(jorgeAcetozi.getUsername(), chatRoom.getId());
		assertThat(jorgeAcetoziMessages.size(), is((3)));
		
		InstantMessage messageOne = jorgeAcetoziMessages.get(0);
		InstantMessage messageTwo = jorgeAcetoziMessages.get(1);
		InstantMessage messageThree = jorgeAcetoziMessages.get(2);
		
		assertThat(messageOne.getText(), is(SystemMessages.welcome(chatRoom.getId(), jorgeAcetozi.getUsername()).getText()));
		
		assertThat(messageTwo.getUsername(), is(jorgeAcetozi.getUsername()));
		assertThat(messageTwo.getChatRoomId(), is(chatRoom.getId()));
		assertThat(messageTwo.getFromUser(), is(jorgeAcetozi.getUsername()));
		assertThat(messageTwo.getToUser(), is(nullValue()));
		assertThat(messageTwo.getText(), is(publicMessage.getText()));
		
		assertThat(messageThree.getText(), is(SystemMessages.goodbye(chatRoom.getId(), jorgeAcetozi.getUsername()).getText()));
	}
	
	@Test
	public void shouldStoreEntireConversationWithPrivateMessages() {
		final ChatRoomUser jorgeAcetozi = new ChatRoomUser("jorge_acetozi");
		final ChatRoomUser johnPetrucci = new ChatRoomUser("john_petrucci");
		List<InstantMessage> jorgeAcetoziMessages, johnPetrucciMessages;
		
		chatRoomService.join(jorgeAcetozi, chatRoom);
		chatRoomService.join(johnPetrucci, chatRoom);
		
		InstantMessage privateMessageOne = new InstantMessageBuilder()
				.newMessage()
				.withChatRoomId(chatRoom.getId())
				.privateMessage()
				.toUser(johnPetrucci.getUsername())
				.fromUser(jorgeAcetozi.getUsername())
				.withText("Hey John, can you play 30 notes per second?");
		chatRoomService.sendPrivateMessage(privateMessageOne);
		
		InstantMessage privateMessageTwo = new InstantMessageBuilder()
				.newMessage()
				.withChatRoomId(chatRoom.getId())
				.privateMessage()
				.toUser(jorgeAcetozi.getUsername())
				.fromUser(johnPetrucci.getUsername())
				.withText("Of course! That's so easy...");
		chatRoomService.sendPrivateMessage(privateMessageTwo);
		
		chatRoomService.leave(jorgeAcetozi, chatRoom);
		chatRoomService.leave(johnPetrucci, chatRoom);
		
		jorgeAcetoziMessages = instantMessageService.findAllInstantMessagesFor(jorgeAcetozi.getUsername(), chatRoom.getId());
		assertThat(jorgeAcetoziMessages.size(), is((5)));
		
		InstantMessage jorgeAcetoziMessageOne = jorgeAcetoziMessages.get(0);
		InstantMessage jorgeAcetoziMessageTwo = jorgeAcetoziMessages.get(1);
		InstantMessage jorgeAcetoziMessageThree = jorgeAcetoziMessages.get(2);
		InstantMessage jorgeAcetoziMessageFour = jorgeAcetoziMessages.get(3);
		InstantMessage jorgeAcetoziMessageFive = jorgeAcetoziMessages.get(4);
		
		assertThat(jorgeAcetoziMessageOne.getText(), is(SystemMessages.welcome(chatRoom.getId(), jorgeAcetozi.getUsername()).getText()));
		assertThat(jorgeAcetoziMessageTwo.getText(), is(SystemMessages.welcome(chatRoom.getId(), johnPetrucci.getUsername()).getText()));
		
		assertThat(jorgeAcetoziMessageThree.getUsername(), is(jorgeAcetozi.getUsername()));
		assertThat(jorgeAcetoziMessageThree.getChatRoomId(), is(chatRoom.getId()));
		assertThat(jorgeAcetoziMessageThree.getFromUser(), is(jorgeAcetozi.getUsername()));
		assertThat(jorgeAcetoziMessageThree.getToUser(), is(johnPetrucci.getUsername()));
		assertThat(jorgeAcetoziMessageThree.getText(), is(privateMessageOne.getText()));
		
		assertThat(jorgeAcetoziMessageFour.getUsername(), is(jorgeAcetozi.getUsername()));
		assertThat(jorgeAcetoziMessageFour.getChatRoomId(), is(chatRoom.getId()));
		assertThat(jorgeAcetoziMessageFour.getFromUser(), is(johnPetrucci.getUsername()));
		assertThat(jorgeAcetoziMessageFour.getToUser(), is(jorgeAcetozi.getUsername()));
		assertThat(jorgeAcetoziMessageFour.getText(), is(privateMessageTwo.getText()));
		
		assertThat(jorgeAcetoziMessageFive.getText(), is(SystemMessages.goodbye(chatRoom.getId(), jorgeAcetozi.getUsername()).getText()));
		
		johnPetrucciMessages = instantMessageService.findAllInstantMessagesFor(johnPetrucci.getUsername(), chatRoom.getId());
		assertThat(johnPetrucciMessages.size(), is((5)));
		
		InstantMessage johnPetrucciMessageOne = johnPetrucciMessages.get(0);
		InstantMessage johnPetrucciMessageTwo = johnPetrucciMessages.get(1);
		InstantMessage johnPetrucciMessageThree = johnPetrucciMessages.get(2);
		InstantMessage johnPetrucciMessageFour = johnPetrucciMessages.get(3);
		InstantMessage johnPetrucciMessageFive = johnPetrucciMessages.get(4);
		
		assertThat(johnPetrucciMessageOne.getText(), is(SystemMessages.welcome(chatRoom.getId(), johnPetrucci.getUsername()).getText()));
		
		assertThat(johnPetrucciMessageTwo.getUsername(), is(johnPetrucci.getUsername()));
		assertThat(johnPetrucciMessageTwo.getChatRoomId(), is(chatRoom.getId()));
		assertThat(johnPetrucciMessageTwo.getFromUser(), is(jorgeAcetozi.getUsername()));
		assertThat(johnPetrucciMessageTwo.getToUser(), is(johnPetrucci.getUsername()));
		assertThat(johnPetrucciMessageTwo.getText(), is(privateMessageOne.getText()));
		
		assertThat(johnPetrucciMessageThree.getUsername(), is(johnPetrucci.getUsername()));
		assertThat(johnPetrucciMessageThree.getChatRoomId(), is(chatRoom.getId()));
		assertThat(johnPetrucciMessageThree.getFromUser(), is(johnPetrucci.getUsername()));
		assertThat(johnPetrucciMessageThree.getToUser(), is(jorgeAcetozi.getUsername()));
		assertThat(johnPetrucciMessageThree.getText(), is(privateMessageTwo.getText()));
		
		assertThat(johnPetrucciMessageFour.getText(), is(SystemMessages.goodbye(chatRoom.getId(), jorgeAcetozi.getUsername()).getText()));
		assertThat(johnPetrucciMessageFive.getText(), is(SystemMessages.goodbye(chatRoom.getId(), johnPetrucci.getUsername()).getText()));
	}
}
