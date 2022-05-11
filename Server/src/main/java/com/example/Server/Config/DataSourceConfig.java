package com.example.Server.Config;

import com.xxdb.DBConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;


@Configuration
public class DataSourceConfig {

    /**
     * This is a database connection instance where it connects the database on port 8900.
     * @return connection
     */
    @Bean
    public DBConnection dbConnection() throws IOException {
        DBConnection connection = new DBConnection();
        connection.connect("localhost",8900);
        connection.login("admin", "123456", false);
        return connection;
    }

}
