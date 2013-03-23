package fr.vhat.keydyn.client.events;

import com.google.gwt.event.shared.EventHandler;

public interface ChangeGroupRequestedEventHandler extends EventHandler {
	void onChangeGroupRequested(ChangeGroupRequestedEvent event);
}
