package org.parafia;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public abstract class ObjectTraverser
{
	/**
	 * This method finds and fills string fields of given {@link Object} if they
	 * have getters and setters that names can be created by uppercasing first field
	 * name letter and concatenating it to word get or set. Method is not recursive!
	 * 
	 * @param o
	 *            {@link Object} to be checked
	 * @param parent
	 * 				Parent {@link Object} to avoid cyclic recursion
	 * 
	 * @return {@link Object} that was modified by this function
	 */
	public static Object fillStrings(Object o, Object parent)
	{
		List<Class<?>> simpleClasses = new ArrayList<>();
		simpleClasses.add(Integer.class);
		simpleClasses.add(Long.class);
		simpleClasses.add(Boolean.class);
		simpleClasses.add(String.class);
		simpleClasses.add(Double.class);
		simpleClasses.add(Date.class);
		
		Field[] fields = o.getClass().getDeclaredFields();
		List<String> stringFieldNames = new ArrayList<>();
		List<String> complexFieldNames = new ArrayList<>();
		for (Field f : fields)
		{
			if (f.getType() == String.class)
			{
				stringFieldNames.add(f.getName());
				continue;
			}
			Boolean simple = false;
			for(Class<?> c : simpleClasses)
				if(f.getType() == c || f.getType().isPrimitive())
				{
					simple = true;
					break;
				}
			if(!simple)
				complexFieldNames.add(f.getName());
		}
		ArrayList<String> tmp = new ArrayList<>();
		for (String s : stringFieldNames)
		{
			String firstLetter = s.substring(0, 1);
			String back = s.substring(1);
			firstLetter = firstLetter.toUpperCase();
			tmp.add(firstLetter + back);
		}
		stringFieldNames = tmp;
		tmp = new ArrayList<>();
		for (String s : complexFieldNames)
		{
			String firstLetter = s.substring(0, 1);
			String back = s.substring(1);
			firstLetter = firstLetter.toUpperCase();
			tmp.add(firstLetter + back);
		}
		complexFieldNames = tmp;
		for (String n : stringFieldNames)
		{
			String get = "get";
			String set = "set";
			String value;
			try
			{
				value = (String) o.getClass().getMethod(get + n).invoke(o);
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e)
			{
				continue;
			}
			if (value == null)
			{
				try
				{
					o.getClass().getMethod(set + n, String.class).invoke(o, "");
				}
				catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e)
				{
					continue;
				}
			}
		}
		for (String n : complexFieldNames)
		{
			String get = "get";
			Object value;
			try
			{
				value = o.getClass().getMethod(get + n).invoke(o);
				if(value != null)
				{
					if(parent != null && value.getClass() == parent.getClass())
						continue;
					ObjectTraverser.fillStrings(o.getClass().getMethod(get + n).invoke(o), o);
				}
				else {
					String set = "set";
					Class<?> clazz = o.getClass().getMethod(get + n).getReturnType();
					
					value = clazz.newInstance();
					o.getClass().getMethod(set + n, clazz).invoke(o, clazz.cast(value));
					
					if(parent != null && value.getClass() == parent.getClass())
						continue;
					ObjectTraverser.fillStrings(o.getClass().getMethod(get + n).invoke(o), o);
				}
			}
			catch (Exception e)
			{
				continue;
			}
		}
		return o;
	}

	/**
	 * This method is designed for building mock objects used for testing. Object
	 * data fields are filled with supplied mock values, and if object requires
	 * other object as field value, additional recursive call to this method is
	 * performed.
	 * 
	 * @param c
	 *            {@link Class} of object to be created
	 * @param mockStrValue
	 *            Value used to fill up {@link String} fields
	 * @param mockIntValue
	 *            Value used to fill up {@link Integer}, {@link Long} and
	 *            {@link Boolean} fields
	 * @param names
	 *            {@link String}[] of fields names to skip initialization for
	 * 
	 *            <p>
	 *            {@link Boolean} value is filled up as:
	 *            <b>{@code mockIntValue != 0}</b>
	 * 
	 * @return New instance of {@link Class} passed as parameter <b>c</b> with all
	 *         fields filled with mock data.
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Object fillMockData(Class<?> c, Object parent, String mockStrValue, Integer mockIntValue,
			String... names)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Object o = c.newInstance();
		Class<?> parClass = null;
		if (parent != null)
			parClass = parent.getClass();

		List<Class<?>> simpleClasses = new ArrayList<>();
		simpleClasses.add(Integer.class);
		simpleClasses.add(Long.class);
		simpleClasses.add(Boolean.class);
		simpleClasses.add(String.class);
		simpleClasses.add(Double.class);
		simpleClasses.add(Date.class);

		List<Method> met = new ArrayList<>();

		Method[] methods = c.getMethods();
		for (Method m : methods)
			if (m.getName().startsWith("set"))
				met.add(m);

		for (Method m : met)
		{
			if (m.getName().endsWith("Id"))
				continue;
			String mName = m.getName().substring(3);
			Boolean skip = false;
			for (String n : names)
				if (mName.equalsIgnoreCase(n))
					skip = true;
			if (skip)
				continue;
			Class<?>[] params = m.getParameterTypes();
			if (params.length != 1)
				continue;

			Class<?> par = params[0];
			if (List.class.isAssignableFrom(par) || Set.class.isAssignableFrom(par) || par.isArray())
				continue;
			Boolean simple = false;
			for (Class<?> cl : simpleClasses)
				if (par.equals(cl))
				{
					simple = true;
					break;
				}

			if (simple)
			{
				if (par.equals(String.class))
				{

					if (m.getName().endsWith("Date"))
						m.invoke(o, "1-01-1970");
					else
					{
						m.invoke(o, mockStrValue);
					}
				}
				else if (par.equals(Date.class))
					m.invoke(o, new Date(mockIntValue.longValue()));
				else if (par.equals(Boolean.class))
					m.invoke(o, mockIntValue != 0);
				else if (par.equals(Long.class))
					m.invoke(o, mockIntValue.longValue());
				else
					m.invoke(o, mockIntValue);
			}
			else if (parClass != null && par.equals(parClass))
			{
				m.invoke(o, parent);
			}
			else if(!par.isPrimitive())
				m.invoke(o, ObjectTraverser.fillMockData(par, o, mockStrValue, mockIntValue, names));
		}
		return o;
	}

	/**
	 * 
	 * @param rb
	 *            Request builder to add parameters to
	 * @param o
	 *            Object to traverse
	 * @param names
	 *            Fields to skip
	 * @param parent
	 *            parent name
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static MockHttpServletRequestBuilder traverse(MockHttpServletRequestBuilder rb, Object o, Object parObj,
			String parent, String... names)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		if (o == null)
			return rb;
		Logger log = Logger.getLogger(ObjectTraverser.class);

		List<Class<?>> simpleClasses = new ArrayList<>();
		simpleClasses.add(Integer.class);
		simpleClasses.add(Long.class);
		simpleClasses.add(Boolean.class);
		simpleClasses.add(String.class);
		simpleClasses.add(Double.class);

		List<Method> met = new ArrayList<>();

		Method[] methods = o.getClass().getMethods();
		for (Method m : methods)
			if (m.getName().startsWith("get"))
				met.add(m);
		for (Method m : met)
		{
			Class<?>[] params = m.getParameterTypes();
			if (params.length != 0)
				continue;

			String mName = m.getName();
			if (mName.endsWith("Class"))
				continue;
			String pName = mName.substring(3);
			if (!pName.equals(""))
				pName = pName.replaceFirst(pName.substring(0, 1), pName.substring(0, 1).toLowerCase());
			if (parent != null)
				pName = parent + "." + pName;

			Boolean skip = false;
			for (String n : names)
				if (pName.equals(n))
				{
					skip = true;
					break;
				}
			if (skip)
				continue;

			Boolean simpleClass = false;
			Class<?> retType = m.getReturnType();
			if (parObj != null && retType.equals(parObj.getClass()))
				continue;
			for (Class<?> c : simpleClasses)
				if (retType.equals(c))
				{
					simpleClass = true;
					break;
				}

			if (retType.isArray())
			{
				Object[] retArray = (Object[]) m.invoke(o);
				if (retArray == null)
					continue;
				log.debug("Invoked: " + o.getClass().getName() + "." + m.getName() + "()");
				for (Object ob : retArray)
					rb = ObjectTraverser.traverse(rb, ob, o, pName, names);
				continue;
			}
			else if (List.class.isAssignableFrom(retType))
			{
				List<?> retList = (List<?>) m.invoke(o);
				if (retList == null)
					continue;
				log.debug("Invoked: " + o.getClass().getName() + "." + m.getName() + "()");
				for (Object ob : retList)
					rb = ObjectTraverser.traverse(rb, ob, o, pName, names);
				continue;
			}
			else if (Set.class.isAssignableFrom(retType))
			{
				Set<?> retSet = (Set<?>) m.invoke(o);
				if (retSet == null)
					continue;
				log.debug("Invoked: " + o.getClass().getName() + "." + m.getName() + "()");
				for (Object ob : retSet)
					rb = ObjectTraverser.traverse(rb, ob, o, pName, names);
				continue;
			}

			Object retObj = m.invoke(o);
			log.debug("Invoked: " + o.getClass().getName() + "." + m.getName() + "()");
			if (retObj == null)
				continue;

			if (retType.isPrimitive() || simpleClass)
			{
				if (retType.isPrimitive())
				{
					log.debug("Param: " + pName + " value: " + String.valueOf(retObj));
					rb = rb.param(pName, String.valueOf(retObj));
				}
				else if (retType.equals(String.class))
				{
					log.debug("Param: " + pName + " value: " + (String) retObj);
					rb = rb.param(pName, (String) retObj);
				}
				else
				{
					log.debug("Param: " + pName + " value: " + retObj.toString());
					rb = rb.param(pName, retObj.toString());
				}
			}
			else
			{
				rb = ObjectTraverser.traverse(rb, retObj, o, pName, names);
			}
		}
		return rb;
	}
}
