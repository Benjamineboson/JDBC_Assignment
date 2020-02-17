package org.example.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Data {
    private static final String url;
    private static final String user;
    private static final String pw;

    static{
        Properties properties = new Properties();
        try{
            properties.load(new FileInputStream("resources/connection.properties"));
        }catch (IOException e) {
            e.printStackTrace();
        }
        url = properties.getProperty("url");
        user = properties.getProperty("user");
        pw = properties.getProperty("password");
    }

    public static Connection getConnection () throws SQLException {
        return DriverManager.getConnection(url,user,pw);
    }
}
