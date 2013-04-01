package fr.vhat.keydyn.shared;

import com.google.gwt.resources.client.TextResource;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;

/**
 * A XML reader in order to read the Frequently Asked Questions XML file.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class XMLReader {

	private Document xml;

	/**
	 * Constructor.
	 * @param xml XML TextResource to read.
	 */
	public XMLReader(TextResource xml) {
		this.xml = XMLParser.parse(xml.getText());
	}

	/**
	 * Return the number of items in the XML file.
	 * @return Number of items : questions and answers.
	 */
	public int getLength() {
		return this.xml.getElementsByTagName("faq-item").getLength();
	}

	/**
	 * Return the image of the given index item.
	 * @param index Index of the item image to return in the XML file.
	 * @return Image name of the index item question.
	 */
	public String getImage(int index) {
		return this.xml.getElementsByTagName("image").item(index)
				.getFirstChild().getNodeValue().trim();
	}

	/**
	 * Return the question of the given index item.
	 * @param index Index of the item question to return in the XML file.
	 * @return Question corresponding to the given index.
	 */
	public String getQuestion(int index) {
		return this.xml.getElementsByTagName("question").item(index)
				.getFirstChild().getNodeValue().trim();
	}

	/**
	 * Return the answer of the given index item.
	 * @param index Index of the item answer to return in the XML file.
	 * @return Answer of the index item question.
	 */
	public String getAnswer(int index) {
		return this.xml.getElementsByTagName("answer").item(index)
				.getFirstChild().getNodeValue().trim();
	}
}
