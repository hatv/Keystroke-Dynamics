package fr.vhat.keydyn.client.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

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
	String pressTimes;
	String releaseTimes;
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
	public KDPassword(String word, String author, String pressTimes,
			String releaseTimes, Date typingDate) {
		this.setWord(word);
		this.setAuthor(author);
		this.setPressTimes(pressTimes);
		this.setReleaseTimes(releaseTimes);
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

	public String getPressTimes() {
		return pressTimes;
	}

	public void setPressTimes(String pressTimes) {
		this.pressTimes = pressTimes;
	}

	public String getReleaseTimes() {
		return releaseTimes;
	}

	public void setReleaseTimes(String releaseTimes) {
		this.releaseTimes = releaseTimes;
	}

	public Date getTypingDate() {
		return typingDate;
	}

	public void setTypingDate(Date typingDate) {
		this.typingDate = typingDate;
	}
}
