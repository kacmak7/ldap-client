package org.parafia.model;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value="session")
@Component
public class Errors<E> extends ArrayList<E>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3592345680364657928L;

}
