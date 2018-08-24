package jcucumberng.framework.api;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.IOException;

import org.openqa.selenium.Dimension;

/**
 * {@code SystemIO} handles actions relating to the user's machine such as input
 * and output devices.
 * 
 * @author Kat Rollo <rollo.katherine@gmail.com>
 */
public final class SystemIO {

	// Prevent instantiation
	private SystemIO() {
	}

	/**
	 * Gets the native resolution of the local machine.
	 * 
	 * @return Dimension - the screen size in WxH (e.g. 1920x1080)
	 */
	public static Dimension getDimension() {
		java.awt.Dimension awtDimension = Toolkit.getDefaultToolkit().getScreenSize();
		short width = (short) awtDimension.getWidth();
		short height = (short) awtDimension.getHeight();
		Dimension dimension = new Dimension(width, height);
		return dimension;
	}

	/**
	 * Accepts a single key entry. The key is pressed and released immediately.
	 * 
	 * @param key the constant from {@code java.awt.event.KeyEvent}
	 * @throws AWTException
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void pressKey(int key) throws AWTException, NumberFormatException, IOException {
		Robot robot = new Robot();
		robot.keyPress(key);
		robot.keyRelease(key);
		int waitTime = Integer.parseInt(ConfigLoader.frameworkConf("key.press.wait"));
		int millis = DateTime.convertSecsToMillisWithRange(waitTime, 1, 60);
		robot.delay(millis);
		robot = null; // Destroy robot
	}

	/**
	 * Accepts multiple key entries (e.g. shortcut command). The keys are pressed
	 * simultaneously and released in reverse order.
	 * 
	 * @param keys an array of KeyEvent constants from
	 *             {@code java.awt.event.KeyEvent}, specify keys in order
	 * @throws AWTException
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void pressKeys(int[] keys) throws AWTException, NumberFormatException, IOException {
		Robot robot = new Robot();
		for (int ctr = 0; ctr < keys.length; ctr++) {
			robot.keyPress(keys[ctr]); // Press and hold keys
		}
		int waitTime = Integer.parseInt(ConfigLoader.frameworkConf("key.press.wait"));
		int millis = DateTime.convertSecsToMillisWithRange(waitTime, 1, 60);
		robot.delay(millis);
		for (int ctr = keys.length - 1; ctr > -1; ctr--) {
			robot.keyRelease(keys[ctr]); // Release keys in reverse order
		}
		robot = null;
	}

}