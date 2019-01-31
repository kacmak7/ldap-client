package org.parafia.webapp.controller.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parafia.ObjectTraverser;
import org.parafia.model.Person;
import org.parafia.model.printcards.Baptism;
import org.parafia.model.printcards.Confirmation;
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
import org.springframework.web.bind.annotation.RequestParam;
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
public class TomeConfirmationListControllerTest {
	
	@Autowired
	private PersonManager personManager;
	
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mvc;
	
	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void formBackingObject() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/tomes/confirmationTome.html"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("tomes/confirmationTome"));
	}
	
	@Test 
	@DatabaseSetup("classpath:/testDb.xml")
	public void onSubmit() throws Exception 
	{
		Person person = personManager.get(139L);
		Confirmation confirmation = (Confirmation) ObjectTraverser.fillMockData(Confirmation.class, person, "test", 1);
		MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.post("/tomes/confirmationTome.html");
		rb = ObjectTraverser.traverse(rb, confirmation, null, null);
		mvc.perform(rb)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("tomes/confirmationTome"));
	}
}
