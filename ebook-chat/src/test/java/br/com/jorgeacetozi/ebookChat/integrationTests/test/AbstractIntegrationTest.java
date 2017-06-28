package br.com.jorgeacetozi.ebookChat.integrationTests.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.WaitingConsumer;

public class AbstractIntegrationTest {

	@ClassRule
	public static final GenericContainer cassandra = new FixedHostPortGenericContainer("cassandra:3.0")
			.withFixedExposedPort(9042, 9042);

	@ClassRule
	public static final GenericContainer mysql = new FixedHostPortGenericContainer("mysql:5.7")
			.withFixedExposedPort(3306, 3306)
			.withEnv("MYSQL_DATABASE", "ebook_chat")
			.withEnv("MYSQL_ROOT_PASSWORD", "root");

	@ClassRule
	public static final GenericContainer redis = new FixedHostPortGenericContainer("redis:3.0.6")
			.withFixedExposedPort(6379, 6379);

	@ClassRule
	public static final GenericContainer rabbitmq = new FixedHostPortGenericContainer("jorgeacetozi/rabbitmq-stomp:3.6")
			.withFixedExposedPort(61613, 61613)
			.withExposedPorts(61613);

	@BeforeClass
	public static void waitForMysqlContainerStartup() throws InterruptedException, TimeoutException {
		WaitingConsumer consumer = new WaitingConsumer();
		mysql.followOutput(consumer);
		consumer.waitUntil(frame -> 
		    frame.getUtf8String().contains("mysqld: ready for connections."), 90, TimeUnit.SECONDS);
	}
}
