package br.com.jorgeacetozi.ebookChat.utils;

public class Destinations {

	public static class ChatRoom {
		
		public static String publicMessages(String chatRoomId) {
			return "/topic/" + chatRoomId + ".public.messages";
		}
		
		public static String privateMessages(String chatRoomId) {
			return "/queue/" + chatRoomId + ".private.messages";
		}
		
		public static String connectedUsers(String chatRoomId) {
			return "/topic/" + chatRoomId + ".connected.users";
		}
	}
}
