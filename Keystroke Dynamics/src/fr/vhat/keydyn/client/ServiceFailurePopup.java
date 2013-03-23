package fr.vhat.keydyn.client;

import java.math.BigDecimal;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class ServiceFailurePopup {

	private PopupPanel popup;

    protected final int SHOW_TIMER_DELAY = 40; //ms 
    private Timer showTimer;

    protected final int HIDE_TIMER_DELAY = 40; //ms
    private Timer hideTimer;

    private static final BigDecimal DELTA = BigDecimal.valueOf(1, 1);

    public ServiceFailurePopup(String message) {
        this.popup = new PopupPanel(true, false);
        this.popup.setWidget(new Label(message));
        //this.popup.setGlassEnabled(true);
        this.popup.center();

        Timer timer = new Timer()
        {
            @Override
            public void run()
            {
                hidePopup();
            }
        };
        timer.schedule(3000);

        this.showTimer = new Timer() {
            @Override
            public void run() {
                String opacityStr = popup.getElement().getStyle().getOpacity();
                boolean increased = false;
                if (!isBlank(opacityStr)) {
                    try {
                        BigDecimal opacity = new BigDecimal(opacityStr);
                        if (opacity.compareTo(new BigDecimal(1)) < 0) {
                             popup.getElement().getStyle().setOpacity(opacity.add(DELTA).doubleValue());
                             increased = true;
                         }
                     } catch (NumberFormatException nfe) {
 // fallback to showing
                     }
                 }
                 if (!increased) {
                     popup.getElement().getStyle().setOpacity(1);
                     cancel();
                 }
             }
         };
         this.hideTimer = new Timer() {
             @Override
             public void run() {
                 String opacityStr = popup.getElement().getStyle().getOpacity();
                 boolean decreased = false;
                 if (!isBlank(opacityStr)) {
                     try {
                         BigDecimal opacity = new BigDecimal(opacityStr);
                         if (opacity.compareTo(DELTA) > 0) {
                            popup.getElement().getStyle().setOpacity(opacity.subtract(DELTA).doubleValue());
                            decreased = true;
                        }
                    } catch (NumberFormatException nfe) {
 // fallback to hiding
                    }
                }
                if (!decreased) {
                    popup.hide();
                    cancel();
                }
            }
        };
    }
        public void showPopup(){
            popup.getElement().getStyle().setOpacity(0);
            popup.show();
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
