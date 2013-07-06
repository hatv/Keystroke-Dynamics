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
// import fr.vhat.keydyn.client.pages.ContactPage;
import fr.vhat.keydyn.client.pages.FAQPage;
import fr.vhat.keydyn.client.pages.HomePage;
import fr.vhat.keydyn.client.pages.LoginPage;
import fr.vhat.keydyn.client.pages.LogoutPage;
import fr.vhat.keydyn.client.pages.RecognitionPage;
import fr.vhat.keydyn.client.pages.RegistrationPage;
import fr.vhat.keydyn.client.pages.StatisticsPage;
import fr.vhat.keydyn.client.pages.TestPage;
import fr.vhat.keydyn.client.pages.TrainingPage;

/**
 * The GroupTabPanel class represents a customized TabPanel from which we can
 * switch to another TabPanel of the Application.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class GroupTabPanel extends TabPanel implements HasHandlers {

	private SimpleEventBus simpleEventBus;
	private boolean connected;

	private StatisticsPage statisticsTab;
	private TrainingPage trainingTab;

	/**
	 * Constructor.
	 * @param connected True if it is a connected groupTabPanel.
	 */
    public GroupTabPanel(boolean connected) {

    	simpleEventBus = new SimpleEventBus();
    	this.connected = connected;

    	Tab FAQPageTab = new FAQPage(this);
    	Tab aboutPageTab = new AboutPage(this);
    	// Tab contactPageTab = new ContactPage(this);

    	if (!connected) {

	    	Tab homePageTab = new HomePage(this);
	    	this.add(homePageTab);

	    	// Init parameter must be true for an applet, false for JavaScript.
	    	LoginPage.init(this, false);
	    	Tab loginPageTab = LoginPage.getInstance();
	    	this.add(loginPageTab);

	    	Tab registrationPageTab = new RegistrationPage(this);
	    	this.add(registrationPageTab);

	    	// Tab FAQPageTab = new FAQPage(this);
	    	this.add(FAQPageTab);

	    	// Tab aboutPageTab = new AboutPage(this);
	    	this.add(aboutPageTab);

	    	// Tab contactPageTab = new ContactPage(this);
	    	// this.add(contactPageTab);

    	} else {

    		statisticsTab = new StatisticsPage(this);
    		this.add(statisticsTab);

    		// Init parameter must be true for an applet, false for JavaScript.
	    	TrainingPage.init(this, false);
	    	trainingTab = TrainingPage.getInstance();
	    	this.add(trainingTab);

	    	// Init parameter must be true for an applet, false for JavaScript.
	    	TestPage.init(this, false);
	    	Tab testPageTab = TestPage.getInstance();
	    	this.add(testPageTab);

	    	RecognitionPage.init(this);
	    	// Tab recognitionPageTab = RecognitionPage.getInstance();
	    	// this.add(recognitionPageTab);

	    	this.add(FAQPageTab);

	    	Tab logoutTab = new LogoutPage(this);
	    	this.add(logoutTab);
    	}
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

    public void changeGroupRequested(int groupIndex) {
    	ChangeGroupRequestedEvent event =
    			new ChangeGroupRequestedEvent(groupIndex);
    	fireEvent(event);
    }

    public void selectTab(int index) {
    	super.selectTab(index);
    	this.refreshTabs();
    }

    public void refreshTabs() {
    	if (this.connected) {
    		trainingTab.refresh();
    		statisticsTab.refresh();
    	}
    }
}
