package fr.vhat.keydyn.client.widgets;

import java.math.BigDecimal;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

public class ServiceFailurePopup extends PopupPanel {

	// Show timer for fade in effect
    protected final int SHOW_TIMER_DELAY = 5; // milliseconds
    private Timer showTimer;

    // Hide timer for fade out effect
    protected final int HIDE_TIMER_DELAY = 10; // milliseconds
    private Timer hideTimer;

    // Opacity steps
    private static final BigDecimal DELTA = BigDecimal.valueOf(2, 2);

    /**
     * Constructor.
     * @param message HTML message to display in the popup.
     * @param timeout Delay before the popup automatically hide itself.
     */
    public ServiceFailurePopup(String message) {
    	this(message, 5000);
    }

    /**
     * Constructor.
     * @param message HTML message to display in the popup.
     * @param timeout Delay before the popup automatically hide itself.
     */
    public ServiceFailurePopup(String message, int timeout) {
        super(true, false);
        this.setWidget(new HTML(message));
        this.setGlassEnabled(true);
        this.center();

        Timer timer = new Timer()
        {
            @Override
            public void run()
            {
                hidePopup();
            }
        };
        timer.schedule(timeout);

        this.showTimer = new Timer() {
            @Override
            public void run() {
            	String opacityStr = getElement().getStyle().getOpacity();
            	boolean increased = false;
            	if (!isBlank(opacityStr)) {
            		try {
            			BigDecimal opacity =
            					new BigDecimal(opacityStr);
            			if (opacity.compareTo(new BigDecimal(1)) < 0) {
            				getElement().getStyle().setOpacity(
            						opacity.add(DELTA).doubleValue());
            				getGlassElement().getStyle().setOpacity(
            						opacity.doubleValue()/4);
            				increased = true;
            			}
            		} catch (NumberFormatException nfe) {
            			// Fallback to showing
            		}
            	}
            	if (!increased) {
            		getElement().getStyle().setOpacity(1);
            		getGlassElement().getStyle().setOpacity(1./4);
            		cancel();
            	}
            }
        };

         this.hideTimer = new Timer() {
             @Override
             public void run() {
                 String opacityStr = getElement().getStyle().getOpacity();
                 boolean decreased = false;
                 if (!isBlank(opacityStr)) {
                     try {
                    	 BigDecimal opacity = new BigDecimal(opacityStr);
                    	 if (opacity.compareTo(DELTA) > 0) {
                    		 getElement().getStyle().setOpacity(
                    				 opacity.subtract(DELTA).doubleValue());
                    		 getGlassElement().getStyle().setOpacity(
                    				 opacity.doubleValue()/4);
                    		 decreased = true;
                         }
                     } catch (NumberFormatException nfe) {
                    	 // Fallback to hiding
                     }
                 }
                 if (!decreased) {
                	 hide();
                	 cancel();
                 }
             }
         };
    }

    public void showPopup(){
    	getElement().getStyle().setOpacity(0);
    	getGlassElement().getStyle().setOpacity(0);
    	show();
    	showTimer.scheduleRepeating(SHOW_TIMER_DELAY);
    }

    public void hidePopup(){
    	showTimer.cancel();
    	hideTimer.scheduleRepeating(HIDE_TIMER_DELAY);
    }

    public static boolean isBlank(final String str) {
    	return (str == null) || (str.trim().length() == 0);
    }
}
