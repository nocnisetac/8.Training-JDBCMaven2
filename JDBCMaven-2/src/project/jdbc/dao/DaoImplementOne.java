package project.jdbc.dao;

import java.sql.*;

import project.jdbc.domen.Person;

public class DaoImplementOne {
	public Person getPerson(int person_id) {
		Connection conn = null;
		try { 
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// This will disable SSL and also suppress the SSL errors:  ?autoReconnect=true&useSSL=false
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/simple_db?autoReconnect=true&useSSL=false", "root", "nocnisetac");
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM person WHERE person_id=?");
			ps.setInt(1, person_id);
			Person person = null;
			ResultSet rs = ps.executeQuery();
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
