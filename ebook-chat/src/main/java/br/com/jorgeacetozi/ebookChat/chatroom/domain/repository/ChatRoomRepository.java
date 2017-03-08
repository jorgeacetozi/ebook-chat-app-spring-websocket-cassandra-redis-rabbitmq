package br.com.jorgeacetozi.ebookChat.chatroom.domain.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.jorgeacetozi.ebookChat.chatroom.domain.model.ChatRoom;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, String> {

}
