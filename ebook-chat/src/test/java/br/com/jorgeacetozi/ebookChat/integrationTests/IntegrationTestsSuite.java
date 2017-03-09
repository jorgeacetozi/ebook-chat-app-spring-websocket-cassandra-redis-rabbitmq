package br.com.jorgeacetozi.ebookChat.integrationTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.jorgeacetozi.ebookChat.integrationTests.authentication.api.AuthenticationControllerTest;
import br.com.jorgeacetozi.ebookChat.integrationTests.authentication.domain.service.DefaultUserServiceTest;
import br.com.jorgeacetozi.ebookChat.integrationTests.chatroom.api.ChatRoomControllerTest;
import br.com.jorgeacetozi.ebookChat.integrationTests.chatroom.domain.service.CassandraInstantMessageServiceTest;
import br.com.jorgeacetozi.ebookChat.integrationTests.chatroom.domain.service.RedisChatRoomServiceTest;
import br.com.jorgeacetozi.ebookChat.integrationTests.test.AbstractIntegrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  CassandraInstantMessageServiceTest.class,
  RedisChatRoomServiceTest.class,
  DefaultUserServiceTest.class,
  AuthenticationControllerTest.class,
  ChatRoomControllerTest.class
})
public class IntegrationTestsSuite extends AbstractIntegrationTest {

}
