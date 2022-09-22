package maktab.team.library.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

import javax.sql.DataSource;

@UtilityClass
public class DataBase {

    @Getter
    @Setter
    public static boolean autoCommit = false;

    private DataSource datasource;

    public DataSource getDataSource() {
        if (datasource == null) {
            final HikariConfig config = new HikariConfig();

            config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
            config.addDataSourceProperty("serverName", "localhost");
            config.addDataSourceProperty("portNumber", "5432");
            config.addDataSourceProperty("databaseName", "postgres");
            config.addDataSourceProperty("user", "postgres");
            config.addDataSourceProperty("password", "4447");

            config.setMaximumPoolSize(10);
            config.setAutoCommit(true);

            datasource = new HikariDataSource(config);
        }
        return datasource;
    }
}
