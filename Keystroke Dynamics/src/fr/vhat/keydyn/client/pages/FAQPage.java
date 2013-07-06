package fr.vhat.keydyn.client.pages;

import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.vhat.keydyn.client.widgets.GroupTabPanel;
import fr.vhat.keydyn.client.widgets.Page;
import fr.vhat.keydyn.shared.FAQResourcesBundle;
import fr.vhat.keydyn.shared.XMLReader;

/**
 * Represent the Frequently Asked Questions page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class FAQPage extends Page {

	/**
	 * Constructor of the Frequently Asked Questions page.
	 */
	public FAQPage(GroupTabPanel owner) {
		super("F.A.Q.", IconType.QUESTION_SIGN, owner);
	}

	@Override
	protected Widget getContent() {

		VerticalPanel panel = new VerticalPanel();
		Paragraph introduction = new Paragraph("Vous trouverez ci-dessous une" +
				" liste de questions que vous pourriez vous poser à propos de" +
				" cette application.");
		introduction.addStyleName("indent");
		panel.add(introduction);

		DecoratedStackPanel stackPanel = new DecoratedStackPanel();
		panel.add(stackPanel);

		String question;
		HTML answer;
		String header;
		String image = new String();
		FAQResourcesBundle resources = GWT.create(FAQResourcesBundle.class);
		XMLReader xml = new XMLReader(resources.FAQ());

		for (int i = 0 ; i < xml.getLength() ; ++i) {
			question = new String(xml.getQuestion(i));
			answer = new HTML(xml.getAnswer(i));
			answer.addStyleName("justify");
			image = xml.getImage(i);
			header = new String(getHeaderString(
					question, this.getImage(image)));
			stackPanel.add(answer, header, true);
		}

		return panel;
	}

	/**
	 * Build an HTML header for the stack panel.
	 * @param text Title of the stack element.
	 * @param image Image to add to the stack element.
	 * @return HTML header element.
	 */
	private String getHeaderString(String text, ImageResource image) {

		// Add the image and text to a horizontal panel
		HorizontalPanel hPanel = new HorizontalPanel();
		hPanel.setSpacing(0);
		hPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		hPanel.add(new Image(image));
		HTML headerText = new HTML(text);
		headerText.setStyleName("cw-StackPanelHeader");
		hPanel.add(headerText);

		// Return the HTML string for the panel
		return hPanel.getElement().getString();
	}

	/**
	 * Return the ImageResource corresponding to a given string.
	 * @param image String corresponding with the image.
	 * @return ImageResource.
	 */
	public ImageResource getImage(String image) {

		FAQResourcesBundle resources = GWT.create(FAQResourcesBundle.class);

		if (image.equals("keyboard")) {
			return resources.keyboard();
		} else if (image.equals("globe")) {
			return resources.globe();
		} else if (image.equals("clipboard")) {
			return resources.clipboard();
		} else if (image.equals("user")) {
			return resources.user();
		} else if (image.equals("java")) {
			return resources.java();
		} else if (image.equals("learn")) {
			return resources.learn();
		} else if (image.equals("test")) {
			return resources.test();
		} else if (image.equals("secure")) {
			return resources.secure();
		} else if (image.equals("key")) {
			return resources.key();
		} else if (image.equals("chronometer")) {
			return resources.chronometer();
		} else if (image.equals("calendar")) {
			return resources.calendar();
		} else if (image.equals("chart")) {
			return resources.chart();
		} else if (image.equals("diagram")) {
			return resources.diagram();
		} else if (image.equals("help")) {
			return resources.help();
		} else if (image.equals("info")) {
			return resources.info();
		} else if (image.equals("shield")) {
			return resources.shield();
		} else if (image.equals("statistics")) {
			return resources.statistics();
		} else if (image.equals("calculator")) {
			return resources.calculator();
		} else if (image.equals("smartphone")) {
			return resources.smartphone();
		} else {
			return null;
		}
	}
}
