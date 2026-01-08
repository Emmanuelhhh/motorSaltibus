package com.tde.motorSALTIBUS.config;

import java.util.Collections;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories(
        basePackages = "com.tde.motorSALTIBUS.persistence.destination",
        entityManagerFactoryRef = "writingEntityManagerFactory",
        transactionManagerRef = "writingTransactionManager"
)
public class WritingDataSourceJPAConfig {
	
	
	// Configuración de DataSource para escritura
    @Bean(name = "destinoDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.destination")
    public DataSource destinoDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "writingEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean writingEntityManagerFactory(
            @Qualifier("destinoDataSource") DataSource destinoDataSource, 
            EntityManagerFactoryBuilder builder) {
        return builder
        		.dataSource(destinoDataSource)
                .packages("com.tde.motorSALTIBUS.persistence.destination")
                .properties(Collections.singletonMap("hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect"))
                .build();
    }

    @Bean(name = "writingTransactionManager")
    public PlatformTransactionManager writingTransactionManager(
            @Qualifier("writingEntityManagerFactory") LocalContainerEntityManagerFactoryBean writingEntityManagerFactory) {
        return new JpaTransactionManager(writingEntityManagerFactory.getObject());
    }
}



