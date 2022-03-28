package com.jb.sample.multitenancy.multitenancy.controller;

import com.jb.sample.multitenancy.multitenancy.config.database.runtime.TenantRoutingDataSource;
import com.jb.sample.multitenancy.multitenancy.global.BeanUtils;
import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;

@Controller
public class TenantController {

    @GetMapping("/api/tenant/register")
    @ResponseBody
    public String tenantRegister( String username,  String password,  String url,  String tenantName) {
        TenantRoutingDataSource tenantDataSource = (TenantRoutingDataSource) BeanUtils.getBean(TenantRoutingDataSource.class);
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create()
                .url(url)
                .driverClassName("org.h2.Driver")
                .username(username)
                .password(password);

        DataSource newDatasource = dataSourceBuilder.build();
        tenantDataSource.addDataSourceToTargetDataSource(tenantName, newDatasource);

        Flyway flyway = Flyway.configure().dataSource(newDatasource).load();
        flyway.migrate();

//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setUrl(url);
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUsername(username);
//        dataSource.setPassword(password);
//        tenantDataSource.addDataSourceToTargetDataSource(tenantName, dataSource);

        return "success";
    }
}
