package org.parafia.webapp.controller.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parafia.ObjectTraverser;
import org.parafia.model.FianceePair;
import org.parafia.model.printfiancees.AssistanceDelegation;
import org.parafia.service.FianceeManager;
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
public class DelegationEditControllerTest {

	@Autowired
	private FianceeManager fianceeManager;
	@Autowired
	private WebApplicationContext wac;
	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void showForm() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/delegation.html").param("fianceePairId", "79557"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("print/delegationEdit"));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	//@ExpectedDatabase(value = "classpath:/delegationEditDBCheck.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void onSubmit() throws Exception {
		FianceePair fianceePair = fianceeManager.get(79557L);
		AssistanceDelegation delegation = (AssistanceDelegation) ObjectTraverser
				.fillMockData(AssistanceDelegation.class, fianceePair, "test", 1);
		fianceePair.setAssistanceDelegation(delegation);

		MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.post("/delegation.html").param("fianceePairId", "79557");
		rb = ObjectTraverser.traverse(rb, fianceePair, null, null);
		mvc.perform(rb).andExpect(MockMvcResultMatchers.status().isFound())
				.andExpect(MockMvcResultMatchers.redirectedUrl("delegation.html?"
						+ "successMessages=Dane+intencji+zosta%3Fy+pomy%3Flnie+zaktualizowane&"
						+ "fianceePairId=79557"));
	}

}
