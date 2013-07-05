package fr.vhat.keydyn.shared.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

import fr.vhat.keydyn.shared.KeystrokeSequence;

import java.io.Serializable;
import java.util.Date;

/**
 * Describe the AuthenticationAttempt entity in order to be used by Objectify.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
@Entity
public class AuthenticationAttempt implements Serializable {
	@Id
	private Long id;
	@Index
	private String attackerLogin;
	@Index
	private String victimLogin;
	@Index
	private String word;
	@Index
	private int victimDataNumberWhileAttack;
	@Index
	private int difficultyLevel;
	@Index
	private int wordLength;
	@Serialize
	private KeystrokeSequence keystrokeSequence;
	private Date attemptDate;
	@Index
	boolean success;

	@SuppressWarnings("unused")
	private AuthenticationAttempt() {}

	/**
	 * Constructor.
	 * @param attackerLogin Login of the attacker.
	 * @param victimLogin Login of the victim.
	 * @param keystrokeSequence Keystroke Sequence of the password.
	 * @param difficultyLevel Level of difficulty chosen by the attacker.
	 * @param victimDataNumberWhileAttack Number of data vectors on which the
	 * system was trained when the attack did happen.
	 * @param attemptDate Date of the authentication attempt.
	 * @param success True if the attack was successful.
	 */
	public AuthenticationAttempt(String attackerLogin, String victimLogin,
			KeystrokeSequence keystrokeSequence, int difficultyLevel,
			int victimDataNumberWhileAttack, Date attemptDate,
			boolean success) {
		this.setAttackerLogin(attackerLogin);
		this.setVictimLogin(victimLogin);
		this.setKeystrokeSequence(keystrokeSequence);
		this.setDifficultyLevel(difficultyLevel);
		this.setVictimDataNumberWhileAttack(victimDataNumberWhileAttack);
		this.setAttemptDate(attemptDate);
		this.setSuccess(success);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttackerLogin() {
		return attackerLogin;
	}

	public void setAttackerLogin(String attackerLogin) {
		this.attackerLogin = attackerLogin;
	}

	public String getVictimLogin() {
		return victimLogin;
	}

	public void setVictimLogin(String victimLogin) {
		this.victimLogin = victimLogin;
	}

	public int getVictimDataNumberWhileAttack() {
		return victimDataNumberWhileAttack;
	}

	public void setVictimDataNumberWhileAttack(
			int victimDataNumberWhileAttack) {
		this.victimDataNumberWhileAttack = victimDataNumberWhileAttack;
	}

	public int getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public int getWordLength() {
		return wordLength;
	}

	public void setWordLength(int wordLength) {
		this.wordLength = wordLength;
	}

	public Date getAttemptDate() {
		return attemptDate;
	}

	public void setAttemptDate(Date attemptDate) {
		this.attemptDate = attemptDate;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
		this.setWordLength(word.length());
	}

	public KeystrokeSequence getKeystrokeSequence() {
		return keystrokeSequence;
	}

	public void setKeystrokeSequence(KeystrokeSequence keystrokeSequence) {
		this.keystrokeSequence = keystrokeSequence;
		this.setWord(this.keystrokeSequence.getPhrase());
	}
}
