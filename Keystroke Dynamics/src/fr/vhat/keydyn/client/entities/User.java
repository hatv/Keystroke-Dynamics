package fr.vhat.keydyn.client.entities;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Serialize;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Describe the User entity in order to be used by Objectify.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
@Entity
public class User implements Serializable {
	@Id
	private Long id;
	@Index
	private boolean isActive; // Active while account is not deleted
	@Index
	private String login;
	private String password;
	private String hashedPassword;
	private String email;
	@Index
	private int age;
	@Index
	private String gender;
	@Index
	private String country;
	@Index
	private int computerExperience;
	@Index
	private int typingUsage;
	private Date registrationDate;
	private List<Key<KDPassword>> KDDataKeys = new ArrayList<Key<KDPassword>>();
	// Enrollment
	@Index
	private int enrollmentStep; // from 0 (no data) to 3 (more than 30 data)
	private Date lastStepDate;
	private Date lastMailSentDate;
	// Computation
	@Serialize
	private int[][] means;
	@Serialize
	private int[][] sd;

	@SuppressWarnings("unused")
	private User() {}
	
	/**
	 * Constructor.
	 * @param login Login.
	 * @param password Password.
	 * @param hashedPassword Hashed password to be stored in the data store.
	 * @param email E-mail.
	 * @param age Age.
	 * @param gender Gender among "Male" and "Female".
	 * @param country Country.
	 * @param computerExperience Computer experience level.
	 * @param computerUsage Typing usage level.
	 * @param registrationDate Registration date.
	 */
	public User(String login, String password, String hashedPassword,
			String email, int age, String gender, String country,
			int computerExperience, int computerUsage, Date registrationDate) {
		this.setActive(true);
		this.setLogin(login);
		this.setPassword(password);
		this.setHashedPassword(hashedPassword);
		this.setEmail(email);
		this.setAge(age);
		this.setGender(gender);
		this.setCountry(country);
		this.setComputerExperience(computerExperience);
		this.setTypingUsage(computerUsage);
		this.setRegistrationDate(registrationDate);
	}
	
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

	public int getTypingUsage() {
		return typingUsage;
	}

	public void setTypingUsage(int computerUsage) {
		this.typingUsage = computerUsage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public Key<KDPassword> getKDDataKey(int index) {
		return KDDataKeys.get(index);
	}

	public int getKDDataSize() {
		return KDDataKeys.size();
	}

	public void addKDDataKey(Key<KDPassword> kdDataKey) {
		KDDataKeys.add(kdDataKey);
	}

	public List<Key<KDPassword>> getKDDataKeys() {
		return KDDataKeys;
	}

	public void setKDDataKeys(List<Key<KDPassword>> kDDataKeys) {
		KDDataKeys = kDDataKeys;
	}

	public int getEnrollmentStep() {
		return enrollmentStep;
	}

	public void setEnrollmentStep(int enrollmentStep) {
		this.enrollmentStep = enrollmentStep;
	}

	public Date getLastStepDate() {
		return lastStepDate;
	}

	public void setLastStepDate(Date lastStepDate) {
		this.lastStepDate = lastStepDate;
	}

	public Date getLastMailSentDate() {
		return lastMailSentDate;
	}

	public void setLastMailSentDate(Date lastMailSentDate) {
		this.lastMailSentDate = lastMailSentDate;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int[][] getMeans() {
		return means;
	}

	public void setMeans(int[][] means) {
		this.means = means;
	}

	public int[][] getSd() {
		return sd;
	}

	public void setSd(int[][] sd) {
		this.sd = sd;
	}
}
