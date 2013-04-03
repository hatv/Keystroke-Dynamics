package fr.vhat.keydyn.client.widgets;

import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.SimpleEventBus;

import fr.vhat.keydyn.client.events.ChangeGroupRequestedEvent;
import fr.vhat.keydyn.client.events.ChangeGroupRequestedEventHandler;
import fr.vhat.keydyn.client.pages.AboutPage;
import fr.vhat.keydyn.client.pages.ContactPage;
import fr.vhat.keydyn.client.pages.FAQPage;
import fr.vhat.keydyn.client.pages.HomePage;
import fr.vhat.keydyn.client.pages.LoginPage;
import fr.vhat.keydyn.client.pages.RegistrationPage;

/**
 * The GroupTabPanel class represents a customized TabPanel from which we can
 * switch to another TabPanel of the Application.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class GroupTabPanel extends TabPanel implements HasHandlers {

	private SimpleEventBus simpleEventBus;

	/**
	 * Constructor.
	 * @param connectedOption True if it is a connected groupTabPanel.
	 */
    public GroupTabPanel(boolean connectedOption, int activeIndex) {
    	simpleEventBus = new SimpleEventBus();
    	Tab homePageTab = new HomePage(this);
    	this.add(homePageTab);
    	// Last init parameter must be true for an applet, false for JavaScript.
    	LoginPage.init(this, true);
    	Tab loginPageTab = LoginPage.getInstance();
    	this.add(loginPageTab);
    	Tab registrationPageTab = new RegistrationPage(this);
    	this.add(registrationPageTab);
    	Tab FAQPageTab = new FAQPage(this);
    	this.add(FAQPageTab);
    	Tab aboutPageTab = new AboutPage(this);
    	this.add(aboutPageTab);
    	Tab contactPageTab = new ContactPage(this);
    	this.add(contactPageTab);
    	this.selectTab(activeIndex);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
    	simpleEventBus.fireEvent(event);
    }

    public HandlerRegistration addChangeGroupRequestedEventHandler(
            ChangeGroupRequestedEventHandler handler) {
        return simpleEventBus.addHandler(
        		ChangeGroupRequestedEvent.TYPE, handler);
    }

    public void changeGroupRequested() {
    	ChangeGroupRequestedEvent event = new ChangeGroupRequestedEvent(0);
    	fireEvent(event);
    }
}
