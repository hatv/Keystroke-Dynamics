package fr.vhat.keydyn.client.entities;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
public class KDData implements Serializable {
	@Id
	Long id;
	String word;
	String pressTimes;
	String releaseTimes;
	Date typingDate;

	@SuppressWarnings("unused")
	private KDData() {}
	
	public KDData(String word, String pressTimes, String releaseTimes,
			Date typingDate) {
		this.setWord(word);
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
