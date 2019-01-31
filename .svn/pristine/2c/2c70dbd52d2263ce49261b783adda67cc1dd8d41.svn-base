package org.parafia.webapp.controller.tests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parafia.ObjectTraverser;
import org.parafia.model.Addressee;
import org.parafia.service.AddresseeManager;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AddresseeEditControllerTest
{
	@Autowired
	private AddresseeManager adrMgr;
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
		Addressee a = adrMgr.get(76220L);
		mvc.perform(MockMvcRequestBuilders.get("/mail/addresseeEdit.html").param("id", "76220"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("addressee", a))
				.andExpect(MockMvcResultMatchers.view().name("/mail/addresseeEdit"));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	//@ExpectedDatabase(value = "classpath:/addresseeDeleteDb.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void deleteAddressee() throws Exception
	{
		Addressee a = adrMgr.get(76220L);
		adrMgr.detach(a);
		mvc.perform(ObjectTraverser.traverse(MockMvcRequestBuilders.post("/mail/addresseeEdit/delete.html"), a, null, null))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("common/closeReloadParent"));
		adrMgr.getAll();
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	//@ExpectedDatabase(value = "classpath:/addresseesSubmitDb.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void submitAddressee() throws Exception
	{
		Addressee a = (Addressee) ObjectTraverser.fillMockData(Addressee.class, null, "B", 2, "id");
		mvc.perform(ObjectTraverser.traverse(MockMvcRequestBuilders.post("/mail/addresseeEdit.html"), a, null, null))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("common/closeReloadParent"));
		adrMgr.getAll();
	}
}
