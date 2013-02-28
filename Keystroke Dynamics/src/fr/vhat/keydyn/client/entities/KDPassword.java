package fr.vhat.keydyn.client.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

import fr.vhat.keydyn.shared.KeystrokeSequence;

import java.io.Serializable;
import java.util.Date;

/**
 * Describe the KDPassword entity in order to be used by Objectify.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
@Entity
public class KDPassword implements Serializable {
	@Id
	Long id;
	@Index
	String word;
	@Index
	int length;
	@Index
	String author;
	@Serialize
	KeystrokeSequence keystrokeSequence;
	Date typingDate;

	@SuppressWarnings("unused")
	private KDPassword() {}

	/**
	 * Constructor.
	 * @param word Password.
	 * @param pressTimes Pressed times.
	 * @param releaseTimes Released times.
	 * @param typingDate Typing date.
	 */
	public KDPassword(String word, String author,
			KeystrokeSequence keystrokeSequence, Date typingDate) {
		this.setWord(word);
		this.setAuthor(author);
		this.setKeystrokeSequence(keystrokeSequence);
		this.setTypingDate(typingDate);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
		this.length = word.length();
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public KeystrokeSequence getKeystrokeSequence() {
		return keystrokeSequence;
	}

	public void setKeystrokeSequence(KeystrokeSequence keystrokeSequence) {
		this.keystrokeSequence = keystrokeSequence;
		this.word = this.keystrokeSequence.getPhrase();
	}

	public Date getTypingDate() {
		return typingDate;
	}

	public void setTypingDate(Date typingDate) {
		this.typingDate = typingDate;
	}
}
