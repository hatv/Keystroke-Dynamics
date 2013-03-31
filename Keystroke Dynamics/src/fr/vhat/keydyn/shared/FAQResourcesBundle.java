package fr.vhat.keydyn.shared;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.TextResource;

/**
 * Manage a bundle of images and XML for the Frequently Asked Questions page.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public interface FAQResourcesBundle extends ClientBundle {

	String path = "FAQResources/";

	@Source(path + "keyboard.png")
	ImageResource keyboard();

	@Source(path + "globe.png")
	ImageResource globe();

	@Source(path + "clipboard.png")
	ImageResource clipboard();

	@Source(path + "user.png")
	ImageResource user();

	@Source(path + "java.png")
	ImageResource java();

	@Source(path + "learn.png")
	ImageResource learn();

	@Source(path + "test.png")
	ImageResource test();

	@Source(path + "secure.png")
	ImageResource secure();

	@Source(path + "key.png")
	ImageResource key();

	@Source(path + "chronometer.png")
	ImageResource chronometer();

	@Source(path + "calendar.png")
	ImageResource calendar();

	@Source(path + "chart.png")
	ImageResource chart();

	@Source(path + "diagram.png")
	ImageResource diagram();

	@Source(path + "help.png")
	ImageResource help();

	@Source(path + "info.png")
	ImageResource info();

	@Source(path + "shield.png")
	ImageResource shield();

	@Source(path + "statistics.png")
	ImageResource statistics();

	@Source(path + "faq.xml")
	TextResource FAQ();
}
