import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

import netscape.javascript.JSObject;

public class KeyboardApplet extends Applet implements KeyListener {

	private static final long serialVersionUID = 1L;
	long first = 0;
	int i = 0;
	List <Long>pressed = new LinkedList<Long>();
	List <Long>released = new LinkedList<Long>();
	List <Character>characters = new LinkedList<Character>();
	List <Long>typed = new LinkedList<Long>();
	boolean newData = false;

	public void init() {
		setBackground(Color.lightGray);
		addKeyListener(this);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() != KeyEvent.VK_ENTER) {
			long tsp = e.getWhen();
			if (first == 0)
				first = tsp;
			pressed.add(tsp-first);
		}
	}
	public void keyReleased(KeyEvent e) {
		char c = e.getKeyChar();
		long tsp = e.getWhen();
		if (c == KeyEvent.VK_ENTER) {
			i++;
			System.out.println("chars    " + characters);
			System.out.println("typed    " + typed);
			System.out.println("pressed  " + pressed);
			System.out.println("released " + released);
			newData = true;
			repaint();
		}
		else if (c != KeyEvent.VK_ENTER) {
			released.add(tsp-first);
		}
	}
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		long tsp = e.getWhen();
		if (c != KeyEvent.CHAR_UNDEFINED && c != KeyEvent.VK_ENTER) {
			typed.add(tsp-first);
			characters.add(c);
		}
	}
	public void callJS() {
		try {
			JSObject window = JSObject.getWindow(this);
			window.call("appletCallback",
					new String[] {"chars    " + characters.toString()});
			window.call("appletCallback",
					new String[] {"typed    " + typed.toString()});
			window.call("appletCallback",
					new String[] {"pressed  " + pressed.toString()});
			window.call("appletCallback",
					new String[] {"released " + released.toString()});
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	public void paint(Graphics g) {
		if (newData) {
			g.setColor(Color.black);
			g.drawString("chars    " + characters.toString(), 5, 15);
			g.drawString("typed    " + typed.toString(), 5, 30);
			g.drawString("pressed  " + pressed.toString(), 5, 45);
			g.drawString("released " + released.toString(), 5, 60);
			this.callJS();
			first = 0;
			characters.clear();
			typed.clear();
			pressed.clear();
			released.clear();
			newData = false;
		}
	}
}
