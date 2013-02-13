package fr.vhat.keydyn.client.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
public class User implements Serializable {
	@Id
	Long id;
	String login;
	String email;
	int age;
	String gender;
	String country;
	int computerExperience;
	int computerUsage;
	Date registrationDate;

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getComputerExperience() {
		return computerExperience;
	}
	public void setComputerExperience(int computerExperience2) {
		this.computerExperience = computerExperience2;
	}
	public int getComputerUsage() {
		return computerUsage;
	}
	public void setComputerUsage(int computerUsage) {
		this.computerUsage = computerUsage;
	}
	public Date getDate() {
		return registrationDate;
	}
	public void setDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
}
