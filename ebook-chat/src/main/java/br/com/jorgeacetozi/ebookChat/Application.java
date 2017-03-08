package br.com.jorgeacetozi.ebookChat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import br.com.jorgeacetozi.ebookChat.chatroom.websocket.WebSocketEvents;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public WebSocketEvents presenceEventListener(SimpMessagingTemplate messagingTemplate) {
		return new WebSocketEvents();
	}
}
