package com.jb.sample.multitenancy.multitenancy.config.database.runtime;

import com.jb.sample.multitenancy.multitenancy.config.web.ThreadTenantStorage;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    private ConcurrentHashMap<Object, DataSource> backupTargetDataSources = new ConcurrentHashMap<>();

    public TenantRoutingDataSource(ConcurrentHashMap<Object, DataSource> backupTargetDataSources) {
        this.backupTargetDataSources = backupTargetDataSources;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return ThreadTenantStorage.getTenantId();
    }

    public void addDataSourceToTargetDataSource(String key, DataSource dataSource) {
        backupTargetDataSources.put(key, dataSource);
        this.setTargetDataSource(backupTargetDataSources);
    }

    public void setTargetDataSource(Map targetDataSource) {
        super.setTargetDataSources(targetDataSource);
        super.afterPropertiesSet();
    }

//    @PostConstruct
//    public void initBackupDatasource() {
//
//    }
}