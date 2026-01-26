package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {
	static private String driver;
	static private String url;
	static private String username;
	static private String password;

	static {
		try {
			Properties props = new Properties();
			InputStream in = ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			if (in == null) {
				throw new RuntimeException("db.properties file not found!");
			}

			props.load(in);

			driver = props.getProperty("db.driver");
			url = props.getProperty("db.url");
			username = props.getProperty("db.username");
			password = props.getProperty("db.password");

			Class.forName(driver); // loading driver class dynamically
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading DB Config!");
		}

	}

	static public Connection getConnection() {
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Database Connection Failed!");
		}
	}
}
