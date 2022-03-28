package com.jb.sample.multitenancy.multitenancy.config.database.runtime;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class TenantDataSourceRegister implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        log.debug("registerBeanDefinitions start!!");
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(TenantRoutingDataSource.class);
        genericBeanDefinition.setSynthetic(true);
        MutablePropertyValues propertyValues = genericBeanDefinition.getPropertyValues();

        Map<Object, DataSource> dataSources = getDefaultDataSources();
        propertyValues.add("defaultTargetDataSource", dataSources.get("Default"));
        propertyValues.add("targetDataSources", dataSources);

        registry.registerBeanDefinition("dataSource",genericBeanDefinition);
    }

    public Map<Object, DataSource> getDefaultDataSources() {
        Map<Object, DataSource> defaultDataSources = new HashMap<>();
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create()
                .url("jdbc:h2:mem:multi1")
                .username("sa")
                .password("")
                .driverClassName("org.h2.Driver");
        DataSource dataSource = dataSourceBuilder.build();
        defaultDataSources.put("multi1",dataSource);
        defaultDataSources.put("Default",dataSource);

//        HikariConfig configuration = new HikariConfig();
//        configuration.setJdbcUrl("jdbc:h2:mem:multi1");
//        configuration.setUsername("sa");
//        configuration.setPassword("");
//        configuration.setDriverClassName("org.h2.Driver");
//        HikariDataSource hikariDataSource = new HikariDataSource(configuration);
//        defaultDataSources.put("multi1",hikariDataSource);
//        defaultDataSources.put("Default",hikariDataSource);

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        return defaultDataSources;
    }
}
