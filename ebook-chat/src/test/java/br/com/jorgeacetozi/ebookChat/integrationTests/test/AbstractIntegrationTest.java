package br.com.jorgeacetozi.ebookChat.integrationTests.test;

import org.junit.ClassRule;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;

public class AbstractIntegrationTest {
	
	@ClassRule
	public static final GenericContainer cassandra = new FixedHostPortGenericContainer("cassandra:3.0")
			.withFixedExposedPort(9042, 9042)
			.withNetworkMode("host");
	
	@ClassRule
	public static final GenericContainer mysql = new FixedHostPortGenericContainer("mysql:5.7")
			.withFixedExposedPort(3306, 3306)
			.withEnv("MYSQL_DATABASE", "ebook_chat")
			.withEnv("MYSQL_ROOT_PASSWORD","root")
			.withNetworkMode("host");
	
	@ClassRule
	public static final GenericContainer redis = new FixedHostPortGenericContainer("redis:3.0.6")
			.withFixedExposedPort(6379, 6379);
}
