package project.jdbc;

import project.jdbc.dao.DaoImplementOne;
import project.jdbc.dao.DaoImplementTwo;
import project.jdbc.domen.Person;

public class JdbcDemo {

	public static void main(String[] args) {
		//Person person = new DaoImplementOne().getPerson(2);
		Person person = new DaoImplementTwo().getPerson(2);
		System.out.println("Name: "+person.getPerson_name()+", Age: "+person.getPerson_age());
	}
}
