package org.parafia.webapp.controller.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parafia.ObjectTraverser;
import org.parafia.model.FianceePair;
import org.parafia.model.printfiancees.Certificate;
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
public class CertificateControllerTest
{
	@Autowired
	private FianceeManager fiaMgr;
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
	public void formBacking() throws Exception
	{
		FianceePair fp = fiaMgr.get(79557L);
		mvc.perform(MockMvcRequestBuilders.get("/certificate.html").param("fianceePairId", "79557"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("fianceePair", fp))
				.andExpect(MockMvcResultMatchers.view().name("print/certificateEdit"));
	}
	
	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	//@ExpectedDatabase(value="classpath:/certificateDBCheck.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void onSubmit() throws Exception
	{
		FianceePair fp = fiaMgr.get(79557L);
		Certificate ce =  (Certificate) ObjectTraverser.fillMockData(Certificate.class, fp, "test", 1);
		fp.setCertificate(ce);
		MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.post("/certificate.html");
		rb = rb.param("fianceePairId", "79557");
		rb = ObjectTraverser.traverse(rb, fp, null, null);
		mvc.perform(rb).andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.view().name("print/certificateEdit"));
	}
}
