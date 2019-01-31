package org.parafia.service.exceptions;

public class MoreThanOneGeomException extends Exception {
	private static final long serialVersionUID = 8973777160500456382L;

	/**
     * Constructor for ObjectExistsException.
     *
     * @param message exception message
     */
    public MoreThanOneGeomException(final String message) {
        super(message);
    }
}