package org.parafia.webapp.controller.tests;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parafia.Constants;
import org.parafia.model.Niche;
import org.parafia.service.GraveyardManager;
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
public class ColumbariumControllerTest
{
	@Autowired
	private GraveyardManager grvMgr;
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
	public void handleRequest() throws Exception
	{
		Integer nicheId = 1;
		mvc.perform(MockMvcRequestBuilders.get("/columbarium/columbarium.html").accept(MediaType.TEXT_HTML)
				.contentType(MediaType.TEXT_HTML).param("niche", nicheId.toString()))
				.andExpect(MockMvcResultMatchers.status().isMovedTemporarily()).andExpect(MockMvcResultMatchers
						.redirectedUrl("columbarium/nicheDetails.html?nicheNumber=" + nicheId.toString()));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void showForm() throws Exception
	{
		Niche n = grvMgr.getNiche(130023L);

		mvc.perform(MockMvcRequestBuilders.get("/columbarium/nicheDetails.html").accept(MediaType.TEXT_HTML)
				.contentType(MediaType.TEXT_HTML).param("columbarium.id", n.getColumbarium().getId().toString())
				.param("columbarium.name", n.getColumbarium().getName()).param("niche.id", n.getId().toString())
				.param("nicheId", n.getId().toString()).param("nicheNumber", String.valueOf(n.getNumber()))
				.param("notices", n.getNotices()).param("number", String.valueOf(n.getNumber()))
				.param("owned.id", n.getOwned().getId().toString()).param("owned.name", n.getOwned().getName())
				.param("owner.firstName", n.getOwner().getFirstName()).param("owner.surname", n.getOwner().getSurname())
				.param("validTo", String.valueOf(n.getValidTo()))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("columbarium/nicheDetails"))
				.andExpect(MockMvcResultMatchers.model().attribute("niche", n));
	}

	/*
	 * @Test
	 * 
	 * @DatabaseSetup("classpath:/columbariumsDb.xml")
	 * 
	 * @ExpectedDatabase(value = "classpath:/nicheSubmitDb.xml", assertionMode =
	 * DatabaseAssertionMode.NON_STRICT) public void submit() throws Exception {
	 * mvc.perform(MockMvcRequestBuilders.post("/columbarium/submitNiche.html").
	 * accept(MediaType.TEXT_HTML) .contentType(MediaType.TEXT_HTML)); }
	 */

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void showList() throws Exception
	{
		List<Niche> niches = grvMgr.loadNiches();
		mvc.perform(MockMvcRequestBuilders.get("/columbarium/findList").accept(MediaType.TEXT_HTML)
				.contentType(MediaType.TEXT_HTML)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("nichesList", niches))
				.andExpect(MockMvcResultMatchers.view().name("columbarium/findList"));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void onSubmit() throws Exception
	{
		List<Niche> nl = grvMgr.loadNichesWithPersons(null, null, null, null, "43");

		mvc.perform(MockMvcRequestBuilders.post("/columbarium/submitList").accept(MediaType.TEXT_HTML)
				.contentType(MediaType.TEXT_HTML).param(Constants.NICHE_FILTER + ".number", "43"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("columbarium/findList"))
				.andExpect(MockMvcResultMatchers.model().attribute("nichesList", nl));
	}
}
