package com.example.mydataway.utils;
import net.hasor.core.ApiBinder;
import net.hasor.core.DimModule;
import net.hasor.db.JdbcModule;
import net.hasor.db.Level;
import net.hasor.spring.SpringModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
@DimModule
@Component
public class HasorModule implements SpringModule {
    @Autowired
    private DataSource dataSource = null;
    @Override
    public void loadModule(ApiBinder apiBinder) throws Throwable {
        apiBinder.installModule(new JdbcModule(Level.Full, this.dataSource));
//        apiBinder.installModule(new JdbcModule(Level.Full, "ds_A", dsA)); // 数据源A
//        apiBinder.installModule(new JdbcModule(Level.Full, "ds_B", dsB)); // 数据源B
    }
}
