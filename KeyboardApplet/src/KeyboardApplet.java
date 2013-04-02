import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Java Applet which access the keystrokes times directly from the OS data.
 * @author Victor Hatinguais, www.victorhatinguais.fr
 */
@SuppressWarnings("serial")
public class KeyboardApplet extends Applet implements KeyListener {

	long first = 0;
	List<Long> pressed = new LinkedList<Long>();
	List<Long> released = new LinkedList<Long>();
	List<Character> characters = new LinkedList<Character>();
	String string = new String();
	//List<Long> typed = new LinkedList<Long>();
	boolean newData = false;

	/**
	 * Initialize the Applet.
	 */
	public void start() {
		setBackground(Color.white);
		addKeyListener(this);
		try {
			// Only found solution that work with Firefox has been defined in
			// callJSFunction.
			callJSFunction("appletLoaded()");

			/* Following tests don't work on Firefox but work fine on IE and
			 * Chrome.
			 */
			// JSObject win = JSObject.getWindow(this);
			// win.eval("functionName(\"param\")");
			// win.eval("javascript:functionName(\"param\")");
			// win.eval("window.functionName(\"param\")");
			// win.eval("javascript:window.functionName(\"param\")");
			// win.call("functionName", new String[]{"param"});
			// win.call("functionName", new Object[]{"param"});

		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	/**
	 * Action which is done on key press event.
	 */
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if ((code >= KeyEvent.VK_A && code <= KeyEvent.VK_Z)
				|| code == KeyEvent.VK_SPACE) {
			char c = e.getKeyChar();
			long tsp = e.getWhen();
			if (first == 0)
				first = tsp;
			pressed.add(tsp - first);
			characters.add(c);
			string = string + c;
			this.callJSCharacter(Character.toString(c));
		}
	}

	/**
	 * Action which is done on key release event.
	 */
	public void keyReleased(KeyEvent e) {
		if (pressed.size() != 0) {
			int code = e.getKeyCode();
			if ((code >= KeyEvent.VK_A && code <= KeyEvent.VK_Z)
					|| code == KeyEvent.VK_SPACE) {
				long tsp = e.getWhen();
				released.add(tsp - first);
			}
			else {
				System.out.println("chars    " + characters);
				//System.out.println("typed    " + typed);
				System.out.println("pressed  " + pressed);
				System.out.println("released " + released);
				newData = true;
				repaint();
			}
		}
	}

	/**
	 * Action which is done on key typed event.
	 */
	public void keyTyped(KeyEvent e) {	}

	/**
	 * Call the JavaScript function <appletCallback> which has to be defined
	 * on the web page which display the Applet.
	 * Send the entire word or passphrase or typing sequence to the JS.
	 */
	private void callJSString() {
		String code = "appletCallback(\"" + string + ";" + pressed.toString()
							+ ";" + released.toString() + "\")";
		callJSFunction(code);
	}

	/**
	 * Call the JavaScript function <appletCallbackChar> which has to be defined
	 * on the web page which display the Applet.
	 * Send the last typed character to the JS.
	 */
	private void callJSCharacter(String c) {
		String code = "appletCallbackChar(\"" + c + "\")";
		callJSFunction(code);
	}

	/**
	 * Function which call the JavaScript code of the page.
	 * @param code Specific code to execute in the JavaScript world.
	 */
	private void callJSFunction(String code) {
		try {
			getAppletContext().showDocument(new URL("javascript:" + code));
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	/**
	 * Define what is displayed on the Applet.
	 */
	public void paint(Graphics g) {
		if (newData) {
			g.setColor(Color.black);
			g.drawString("chars    " + characters.toString(), 5, 15);
			//g.drawString("typed    " + typed.toString(), 5, 30);
			g.drawString("pressed  " + pressed.toString(), 5, 30);
			g.drawString("released " + released.toString(), 5, 45);

			this.callJSString();

			first = 0;
			characters.clear();
			string = "";
			//typed.clear();
			pressed.clear();
			released.clear();
			newData = false;
		}
	}
}
