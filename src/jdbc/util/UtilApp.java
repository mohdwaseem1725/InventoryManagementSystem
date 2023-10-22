package jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class UtilApp {
	private UtilApp() {
	}

	public static Connection getJdbcConnection() throws IOException, SQLException {
		FileInputStream fis = new FileInputStream("D:\\Java Training\\JdbcProjects\\src\\application.properies");
		Properties properties = new Properties();
		properties.load(fis);

		// Creating the Connection
		String url = properties.getProperty("url");
		String userName = properties.getProperty("user");
		String password = properties.getProperty("password");

		Connection connection = DriverManager.getConnection(url, userName, password);

		return connection;
	}

	public static void closeResouces(Connection con, Statement stm, ResultSet res) throws SQLException {
		if (con != null) {
			con.close();
		}
		if (stm != null) {
			stm.close();
		}
		if (res != null) {
			res.close();
		}
	}
}
