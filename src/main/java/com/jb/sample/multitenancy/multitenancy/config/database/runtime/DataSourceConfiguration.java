package com.jb.sample.multitenancy.multitenancy.config.database.runtime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


@Configuration
@Import(TenantDataSourceRegister.class)
public class DataSourceConfiguration {

    @Autowired
    private TenantRoutingDataSource tenantRoutingDataSource;

}
