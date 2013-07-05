package fr.vhat.keydyn.shared.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

import fr.vhat.keydyn.shared.KeystrokeSequence;

import java.io.Serializable;
import java.util.Date;

/**
 * Describe the KDSentence entity in order to be used by Objectify.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
@Entity
public class KDSentence implements Serializable {
	@Id
	private Long id;
	@Index
	private String sentence;
	@Index
	private int length;
	@Index
	private String author;
	@Serialize
	private KeystrokeSequence keystrokeSequence;
	private Date typingDate;

	@SuppressWarnings("unused")
	private KDSentence() {}

	/**
	 * Constructor.
	 * @param sentence Sentence.
	 * @param author Author.
	 * @param keystrokeSequence Keystroke Sequence of the sentence.
	 * @param typingDate Typing date.
	 */
	public KDSentence(String sentence, String author,
			KeystrokeSequence keystrokeSequence, Date typingDate) {
		this.setSentence(sentence);
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

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
		this.setLength(sentence.length());
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
		this.setSentence(this.keystrokeSequence.getPhrase());
	}

	public Date getTypingDate() {
		return typingDate;
	}

	public void setTypingDate(Date typingDate) {
		this.typingDate = typingDate;
	}
}
