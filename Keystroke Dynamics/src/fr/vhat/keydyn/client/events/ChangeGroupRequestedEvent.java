package fr.vhat.keydyn.client.events;

import com.google.gwt.event.shared.GwtEvent;

public class ChangeGroupRequestedEvent
extends GwtEvent<ChangeGroupRequestedEventHandler> {
	public static Type<ChangeGroupRequestedEventHandler> TYPE =
			new Type<ChangeGroupRequestedEventHandler>();

    private final int groupIndex;

    public ChangeGroupRequestedEvent(int groupIndex) {
        this.groupIndex = groupIndex;
    }

    @Override
    public Type<ChangeGroupRequestedEventHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(ChangeGroupRequestedEventHandler handler) {
        handler.onChangeGroupRequested(this);
    }

    public int getGroupIndex() {
        return groupIndex;
    }
}
