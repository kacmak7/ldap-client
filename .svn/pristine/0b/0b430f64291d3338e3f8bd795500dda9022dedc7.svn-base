package org.parafia.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="Missing permissions")
public class AccessDeniedException extends org.springframework.security.access.AccessDeniedException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7382845859963046397L;

	public AccessDeniedException(String msg) {
		super(msg);
	}

}
