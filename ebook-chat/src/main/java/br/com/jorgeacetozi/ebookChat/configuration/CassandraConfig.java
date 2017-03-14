package br.com.jorgeacetozi.ebookChat.configuration;

import java.util.Arrays;
import org.springframework.cassandra.core.keyspace.CreateKeyspaceSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.mapping.CassandraMappingContext;

@Profile({"dev","test"})
@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
 
    @Override
    protected String getKeyspaceName() {
        return "ebook_chat";
    }
    
    @Bean
    @Override
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints("localhost");
        cluster.setPort(9042);
        cluster.setKeyspaceCreations(
        		Arrays.asList(
        				new CreateKeyspaceSpecification("ebook_chat")
        				.ifNotExists()
        				.withSimpleReplication(1))
        		);
        cluster.setStartupScripts(Arrays.asList(
        		"USE ebook_chat",
        		"CREATE TABLE IF NOT EXISTS messages (" +
					"username text," +
					"chatRoomId text," +
					"date timestamp," +
					"fromUser text," +
					"toUser text," +
					"text text," +
					"PRIMARY KEY ((username, chatRoomId), date)" +
				") WITH CLUSTERING ORDER BY (date ASC)"
        		)
        );
        return cluster;
    }

    @Bean
    @Override
    public CassandraMappingContext cassandraMapping() 
      throws ClassNotFoundException {
        return new BasicCassandraMappingContext();
    }
}
