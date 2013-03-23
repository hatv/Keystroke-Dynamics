package fr.vhat.keydyn.client.widgets;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.TabPanel;

import fr.vhat.keydyn.client.events.ChangeGroupRequestedEvent;
import fr.vhat.keydyn.client.events.ChangeGroupRequestedEventHandler;

/**
 * The GroupTabPanel class represents a customized TabPanel from which we can
 * switch to another TabPanel of the Application.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
public class GroupTabPanel extends TabPanel implements HasHandlers {

	private SimpleEventBus simpleEventBus;

    public GroupTabPanel() {
    	simpleEventBus = new SimpleEventBus();
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
    	simpleEventBus.fireEvent(event);
    }

    public HandlerRegistration addChangeGroupRequestedEventHandler(
            ChangeGroupRequestedEventHandler handler) {
        return simpleEventBus.addHandler(ChangeGroupRequestedEvent.TYPE, handler);
    }

    public void changeGroupRequested() {
    	ChangeGroupRequestedEvent event = new ChangeGroupRequestedEvent(0);
    	fireEvent(event);
    }
}
