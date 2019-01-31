package org.parafia;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parafia.model.FianceePair;
import org.parafia.model.Grave;
import org.parafia.service.GraveyardManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/DbTest-context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@WebAppConfiguration
@Transactional
@DatabaseSetup("classpath:/emptyDb.xml")
public class ObjectTraverserTest
{
	@Autowired
	private GraveyardManager grvMgr;
	@Autowired
	private WebApplicationContext wac;
	
	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void test() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Grave g = grvMgr.getGrave(73192L);
		Map<String, String> params = new HashMap<>();
		
		params.put("doubled.id", g.getDoubled().getId().toString());
		params.put("doubled.name", g.getDoubled().getName());
		params.put("grounded.id", g.getGrounded().getId().toString());
		params.put("grounded.name", g.getGrounded().getName());
		params.put("id", g.getId().toString());
		params.put("notices", g.getNotices());
		params.put("number", String.valueOf(g.getNumber()));
		params.put("owned.id", g.getOwned().getId().toString());
		params.put("owned.name", g.getOwned().getName());
		params.put("owner.firstName", g.getOwner().getFirstName());
		params.put("owner.surname", g.getOwner().getSurname());
		params.put("postgisString", g.getPostgisString());
		params.put("sector", g.getSector());
		params.put("validTo", String.valueOf(g.getValidTo()));
		
		MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.request(HttpMethod.POST, "test123");
		
		rb = ObjectTraverser.traverse(rb, g, null, null, "files", "levels", "persons", "points", "owner.address", "owner.id" , "owner.phone");
		HttpServletRequest rq = rb.buildRequest(wac.getServletContext());
		
		for(Entry<String, String> e : params.entrySet())
			Assert.assertEquals(e.getValue(), rq.getParameter(e.getKey()));
	}
	
	@Test
	public void test1() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		FianceePair fp = (FianceePair) ObjectTraverser.fillMockData(FianceePair.class, null, null, 2);
		ObjectTraverser.fillStrings(fp, null);
		Assert.assertNotNull(fp.getFileNumber());
	}
}
