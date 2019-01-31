package org.parafia.model;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("session")
@Component
public class Messages<E> extends ArrayList<E>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7442435349739449687L;

}
