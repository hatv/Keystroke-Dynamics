package fr.vhat.keydyn.shared.entities;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Serialize;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import fr.vhat.keydyn.shared.StatisticsUnit;

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
	private List<Key<KDPassword>> kdPasswordKeys =
			new ArrayList<Key<KDPassword>>();
	// Enrollment
	@Index
	private boolean isEnoughTrained;
	// Computation
	@Serialize
	private StatisticsUnit means;
	@Serialize
	private StatisticsUnit sd;
	private Float threshold;

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
	 * @param typingUsage Typing usage level.
	 * @param registrationDate Registration date.
	 */
	public User(String login, String password, String hashedPassword,
			String email, int age, String gender, String country,
			int computerExperience, int typingUsage, Date registrationDate) {
		this.setActive(true);
		this.setLogin(login);
		this.setPassword(password);
		this.setHashedPassword(hashedPassword);
		this.setEmail(email);
		this.setAge(age);
		this.setGender(gender);
		this.setCountry(country);
		this.setComputerExperience(computerExperience);
		this.setTypingUsage(typingUsage);
		this.setRegistrationDate(registrationDate);
		this.setEnoughTrained(false);
		this.setThreshold((float)0);
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
		int charactersNumber = password.length();
		this.setMeans(new StatisticsUnit(charactersNumber));
		this.setSd(new StatisticsUnit(charactersNumber));
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

	public boolean isEnoughTrained() {
		return isEnoughTrained();
	}

	public void setEnoughTrained(boolean isEnoughTrained) {
		this.isEnoughTrained = isEnoughTrained;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public StatisticsUnit getMeans() {
		return means;
	}

	public void setMeans(StatisticsUnit means) {
		this.means = means;
	}

	public StatisticsUnit getSd() {
		return sd;
	}

	public void setSd(StatisticsUnit sd) {
		this.sd = sd;
	}

	public Float getThreshold() {
		return threshold;
	}

	public void setThreshold(Float threshold) {
		this.threshold = threshold;
	}

	public List<Key<KDPassword>> getKDPasswordKeys() {
		return kdPasswordKeys;
	}

	public void setKDPasswordKeys(List<Key<KDPassword>> kDPasswordKeys) {
		kdPasswordKeys = kDPasswordKeys;
	}

	/**
	 * Return the key of the given index Keystroke Dynamics Password.
	 * @param index Index of the key to retrieve.
	 * @return Key of the given index Keystroke Dynamics Password.
	 */
	public Key<KDPassword> getKDPasswordKey(int index) {
		return kdPasswordKeys.get(index);
	}

	/**
	 * Return the number of Keystroke Dynamics Password stored in the data store
	 * for this user.
	 * @return Number of data stored.
	 */
	public int getKDPasswordNumber() {
		return kdPasswordKeys.size();
	}

	/**
	 * Add a Keystroke Dynamics Password key to the user list.
	 * @param kdPasswordKey Key to add to the list.
	 */
	public void addKDPasswordKey(Key<KDPassword> kdPasswordKey) {
		kdPasswordKeys.add(kdPasswordKey);
	}
}
