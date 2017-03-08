package br.com.jorgeacetozi.ebookChat.chatroom.domain.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;

import br.com.jorgeacetozi.ebookChat.chatroom.domain.model.InstantMessage;

public interface InstantMessageRepository extends CassandraRepository<InstantMessage> {
	
	List<InstantMessage> findInstantMessagesByUsernameAndChatRoomId(String username, String chatRoomId);
}
