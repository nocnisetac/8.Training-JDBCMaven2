package project.jdbc.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.*;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;


import project.jdbc.domen.Person;

/**
 * @author nocnisetac
 * Here we use DataSource class and BasicDataSource from org.apache.commons.dbcp package that
 * provides Java DataSource implementation that works as an abstraction layer between our program
 * and different JDBC drivers..
 * The major benefit of Java DataSource is when it’s used within a Context and with JNDI.
 * Also, for having database configuration loosely coupled, we will read them from property file.
 * Best option is to use server for creating connection pool and then use it in our application by
 * getting connection from pool using JNDI.
 */
public class DaoImplementTwo {
	
	public static DataSource getDataSource() {
		Properties props = new Properties();
		FileInputStream fis = null;
		BasicDataSource ds = new BasicDataSource();
		try {
			fis = new FileInputStream("db.properties");
			props.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		ds.setDriverClassName(props.getProperty("MYSQL_DB_DRIVER_CLASS"));
		ds.setUrl(props.getProperty("MYSQL_DB_URL"));
		ds.setUsername(props.getProperty("MYSQL_DB_USERNAME"));
		ds.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
		return ds;
	}

	public Person getPerson(int person_id) {
		DataSource ds = DaoImplementTwo.getDataSource();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			ps = conn.prepareStatement("SELECT * FROM person WHERE person_id=?");
			ps.setInt(1, person_id);
			Person person = null;
			rs = ps.executeQuery();
			if (rs.next()) {
				person = new Person(person_id, rs.getString("person_name"), rs.getInt("person_age"));
			}
			rs.close();
			ps.close();
			return person;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
	}
}
