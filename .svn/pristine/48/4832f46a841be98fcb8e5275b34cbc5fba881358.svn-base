package org.parafia.webapp.controller.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.parafia.ObjectTraverser;
import org.parafia.model.FianceePair;
import org.parafia.model.Person;
import org.parafia.service.FianceeManager;
import org.parafia.service.PersonManager;
import org.parafia.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class FianceeEditControllerTest {

	@Autowired
	FianceeManager fianceeManager; 
	@Autowired
	PersonManager personManager;
	@Autowired
	UserManager userManager;
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
		mvc.perform(MockMvcRequestBuilders.get("/print/fianceesform.html")
					.param("fianceePairId", "79557"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("print/fianceeEdit"));
		
		mvc.perform(MockMvcRequestBuilders.get("/print/fianceesform.html")
				.param("person1Id", "79486")
				.param("person2Id", "79488"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("print/fianceeEdit"));
	}
	
	
	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	//@ExpectedDatabase(value = "classpath:/fianceeEditDeleteDBCheck.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void processFormSubmissionDelete() throws Exception
	{
		String code = "47d2059098f276ba789a340931f58a9e94f11a21"; //admin
		
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		Mockito.when(authentication.getName()).thenReturn("admin");
		SecurityContextHolder.setContext(securityContext);

		mvc.perform(MockMvcRequestBuilders.post("/print/fianceesform/delete.html")
				.param("code", code)
				.param("fianceePairId", "79557"))
				.andExpect(MockMvcResultMatchers.status().isFound())
				.andExpect(MockMvcResultMatchers.redirectedUrl("/tomes/fianceesTome.html?"
						+ "successMessages=Dane+zwi%3Fzku+narzeczonych+zosta%3Fy+pomy%3Flnie+usuni%3Fte"));
		fianceeManager.flush();
	}
	
	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	//@ExpectedDatabase(value = "classpath:/fianceeEditDBCheck.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void onSubmit() throws Exception
	{	
		Person person = personManager.get(139L);
		personManager.detach(person);
		
		FianceePair fianceePair = (FianceePair) ObjectTraverser.fillMockData(FianceePair.class, person, "test", 1);
		
		MockHttpServletRequestBuilder rb =MockMvcRequestBuilders.post("/print/fianceesform.html").param("fianceePairId", "79559");
		fianceeManager.detach(fianceePair);
		fianceePair.getFianceeHe().getRemaining().getBaptismKnowAct().getWitness().getPractising().setId(1L);
		fianceePair.getFianceeShe().getRemaining().getBaptismKnowAct().getWitness().getPractising().setId(1L);
		
		rb = ObjectTraverser.traverse(rb, fianceePair, null, null);
		mvc.perform(rb)
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
