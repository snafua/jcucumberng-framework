package jcucumberng.framework.exceptions;

/**
 * {@code UnsupportedByMethodException} is thrown when the method in
 * {@code ui-map.properties} is unrecognized in the key-value pair.
 * 
 * @author Kat Rollo &lt;rollo.katherine@gmail.com&gt;
 */
@SuppressWarnings("serial")
public class UnsupportedByMethodException extends RuntimeException {
	public UnsupportedByMethodException(String message) {
		super(message);
	}
}
