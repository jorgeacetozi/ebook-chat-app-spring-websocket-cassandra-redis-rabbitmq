package br.com.jorgeacetozi.ebookChat.unitTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.jorgeacetozi.ebookChat.unitTests.chatroom.domain.service.RedisChatRoomServiceTest;
import br.com.jorgeacetozi.ebookChat.unitTests.chatroom.domain.model.InstantMessageBuilderTest;
import br.com.jorgeacetozi.ebookChat.unitTests.utils.DestinationsTest;
import br.com.jorgeacetozi.ebookChat.unitTests.utils.SystemMessagesTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  InstantMessageBuilderTest.class,
  DestinationsTest.class,
  SystemMessagesTest.class,
  RedisChatRoomServiceTest.class
})
public class UnitTestsSuite {

}
