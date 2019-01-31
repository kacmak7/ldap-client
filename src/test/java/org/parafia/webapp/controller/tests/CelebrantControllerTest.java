package org.parafia.webapp.controller.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parafia.model.Celebrant;
import org.parafia.service.impl.CelebrantManager;
import org.parafia.webapp.controller.CelebrantController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/DbTest-context.xml")
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class })
@Transactional
@DatabaseSetup("classpath:/emptyDb.xml")
public class CelebrantControllerTest
{
	@Autowired
	private CelebrantManager celMgr;
	@Autowired
	private CelebrantController celCon;
	@Autowired
	private WebApplicationContext wac;

	MockMvc mvc;

	@Before
	public void setup()
	{
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void celebrantsList() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/tomes/celebrantsList.html").contentType(MediaType.TEXT_HTML)
				.accept(MediaType.TEXT_HTML)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/tomes/celebrantsList"));
	}

	@Test
	public void getAllCelebrants()
	{
		List<Celebrant> expected = celMgr.getAll();
		List<Celebrant> actual = celCon.getAllCelebrants();
		Assert.assertEquals(expected, actual);
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void editCelebrant() throws Exception
	{
		Celebrant celebrant = celMgr.get(145347L);
		mvc.perform(MockMvcRequestBuilders.get("/tomes/editCelebrant.html").param("edit", "true").param("id", "145347"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("celebrant", celebrant))
				.andExpect(MockMvcResultMatchers.view().name("/tomes/editCelebrant"));
	}

	@Test
	//@ExpectedDatabase(value="classpath:/testDb.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void saveCelebrant() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.post("/tomes/editCelebrant.html").contentType(MediaType.TEXT_HTML)
				.accept(MediaType.TEXT_HTML).param("active", "true").param("firstName", "Test")
				.param("surname", "Test")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("common/closeReloadParent"));
		celMgr.getAll();
	}
	
	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	//@ExpectedDatabase(value="classpath:/celebrantsUpdateDb.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void updateCelebrant() throws Exception
	{
		Celebrant c = celMgr.get(145347L);
		celMgr.detach(c);
		mvc.perform(MockMvcRequestBuilders.post("/tomes/editCelebrant.html").contentType(MediaType.TEXT_HTML)
				.accept(MediaType.TEXT_HTML).param("active", String.valueOf(c.isActive())).param("firstName", "Test")
				.param("surname", c.getSurname()).param("id", String.valueOf(c.getId()))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("common/closeReloadParent"));
		celMgr.getAll();
	}
}
