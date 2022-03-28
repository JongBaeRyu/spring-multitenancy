package com.jb.sample.multitenancy.multitenancy.config.database;

import com.jb.sample.multitenancy.multitenancy.config.web.ThreadTenantStorage;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return ThreadTenantStorage.getTenantId();
    }
}