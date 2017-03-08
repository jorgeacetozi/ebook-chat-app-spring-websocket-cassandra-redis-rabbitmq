package br.com.jorgeacetozi.ebookChat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.jorgeacetozi.ebookChat.chatroom.domain.service.CassandraInstantMessageServiceTest;
import br.com.jorgeacetozi.ebookChat.chatroom.domain.service.RedisChatRoomServiceTest;
import br.com.jorgeacetozi.ebookChat.test.AbstractIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  CassandraInstantMessageServiceTest.class,
  RedisChatRoomServiceTest.class
})
public class IntegrationTestsSuite extends AbstractIntegrationTest {

}
