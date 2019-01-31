package org.parafia.webapp.controller.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parafia.ObjectTraverser;
import org.parafia.model.Person;
import org.parafia.model.printcards.BaptismKnowAct;
import org.parafia.service.FamilyManager;
import org.parafia.service.PersonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
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
public class BaptismKnowActControllerTest
{
	@Autowired
	private FamilyManager famMgr;
	@Autowired
	private PersonManager personManager;
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mvc;

	@Before
	public void setup()
	{
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void showForm() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/baptismKnowActEdit.html").param("personId", "33033"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("baptismPlaces", famMgr.getBaptismPlaces()))
				.andExpect(MockMvcResultMatchers.view().name("print/baptismKnowActEdit"));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	//@ExpectedDatabase(value = "classpath:/baptismKnowActSaveCheck.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void onSubmit() throws Exception
	{	
		Person person = personManager.get(33033L);
		personManager.detach(person);
		BaptismKnowAct act = (BaptismKnowAct) ObjectTraverser.fillMockData(BaptismKnowAct.class, person, "test", 1);
		act.getWitness().getPractising().setId(1L);
		person.getRemaining().setBaptismKnowAct(act);
		
		MockHttpServletRequestBuilder rb =MockMvcRequestBuilders.post("/baptismKnowActEdit.html"); 
		rb.param("personId", "33033");
		rb = ObjectTraverser.traverse(rb, person, null, null);
		mvc.perform(rb)
		.andExpect(MockMvcResultMatchers.status().isFound())
		.andExpect(MockMvcResultMatchers.redirectedUrl("baptismKnowActEdit.html?"
				+ "successMessages=Dane+osoby+zosta%3Fy+pomy%3Flnie+zaktualizowane&"
				+ "personId=33033"));
	}
}
