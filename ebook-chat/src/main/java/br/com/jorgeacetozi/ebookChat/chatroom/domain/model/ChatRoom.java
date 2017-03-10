package br.com.jorgeacetozi.ebookChat.chatroom.domain.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.google.common.annotations.VisibleForTesting;

@RedisHash("chatrooms")
public class ChatRoom {
	
	@Id
	private String id;
	private String name;
	private String description;
	private List<ChatRoomUser> connectedUsers = new ArrayList<>();
	
	public ChatRoom() {
	
	}
	
	@VisibleForTesting
	public ChatRoom(String id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<ChatRoomUser> getConnectedUsers() {
		return connectedUsers;
	}
	public void addUser(ChatRoomUser user) {
		this.connectedUsers.add(user);
	}
	public void removeUser(ChatRoomUser user) {
		this.connectedUsers.remove(user);
	}
	public int getNumberOfConnectedUsers(){
		return this.connectedUsers.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChatRoom other = (ChatRoom) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
