package br.com.jorgeacetozi.ebookChat;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.jorgeacetozi.ebookChat.chatroom.domain.model.InstantMessageBuilderTest;
import br.com.jorgeacetozi.ebookChat.utils.DestinationsTest;
import br.com.jorgeacetozi.ebookChat.utils.SystemMessagesTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  InstantMessageBuilderTest.class,
  DestinationsTest.class,
  SystemMessagesTest.class
})
public class UnitTestsSuite {

}
