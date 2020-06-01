package pl.jaz.jazapp;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.integration.cdi.CDILiquibaseConfig;
import liquibase.integration.cdi.annotations.LiquibaseType;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import liquibase.servicelocator.LiquibaseService;

import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
//import javax.ws.rs.Produces;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class LiquibaseConfig {
    @Produces
    @LiquibaseType
    public CDILiquibaseConfig createConfig(){
        CDILiquibaseConfig config = new CDILiquibaseConfig();
        config.setChangeLog("db.changelog/db.changelog.yaml");
        return config;
    }

    @Produces
    @LiquibaseType
    public DataSource createDataSource(){
        var config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/jazapp");
        config.setUsername("jazapp");
        config.setPassword("jazapp!98");

        return new HikariDataSource(config);
    }

    @Produces
    @LiquibaseType
    public ResourceAccessor create() { return new ClassLoaderResourceAccessor(getClass().getClassLoader()); }
}
