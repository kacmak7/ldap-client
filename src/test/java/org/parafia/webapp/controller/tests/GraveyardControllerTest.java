package org.parafia.webapp.controller.tests;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parafia.Constants;
import org.parafia.ObjectTraverser;
import org.parafia.model.Grave;
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
public class GraveyardControllerTest
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
	public void drawYard() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/yard/yard.html")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/yard/yard"));
	}

	@Test
	public void yardFind() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/yard/yardFind.html")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/yard/yardFind"));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void submitList() throws Exception
	{
		List<Long> ids = new ArrayList<>();
		{
			List<Grave> gr = grvMgr.findGraves(null, null, null, null, "2");
			for (Grave g : gr)
				ids.add(g.getId());
		}
		mvc.perform(MockMvcRequestBuilders.post("/yard/yardFind.html").contentType(MediaType.TEXT_HTML)
				.accept(MediaType.TEXT_HTML).param("graveNumber", "2")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("redGraves", ids))
				.andExpect(MockMvcResultMatchers.view().name("/yard/yard"));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void listBacking() throws Exception
	{
		List<Grave> g = grvMgr.getAll();
		mvc.perform(MockMvcRequestBuilders.get("/yard/yardFindList.html"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("gravesList", g))
				.andExpect(MockMvcResultMatchers.view().name("/yard/yardFindList"));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void submitFindList() throws Exception
	{
		List<Grave> g = grvMgr.findGraves(null, null, null, null, "2");
		mvc.perform(MockMvcRequestBuilders.post("/yard/yardFindList.html").param("graveNumber", "2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("gravesList", g))
				.andExpect(MockMvcResultMatchers.view().name("/yard/yardFindList"));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void yardInwalid() throws Exception
	{
		List<Grave> g = grvMgr
				.loadInvalid(Integer.valueOf(new SimpleDateFormat(Constants.DATE_FORMAT_ONLY_YEAR).format(new Date())));
		mvc.perform(MockMvcRequestBuilders.get("/yard/yardInvalid.html"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("graves", g))
				.andExpect(MockMvcResultMatchers.view().name("/yard/yardInvalid"));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void garveDetailsGet() throws Exception
	{
		Grave g = grvMgr.getGrave(73192L);
		mvc.perform(MockMvcRequestBuilders.get("/yard/graveDetails.html").param("graveId", "73192"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("grave", g))
				.andExpect(MockMvcResultMatchers.view().name("/yard/graveDetails"));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	//@ExpectedDatabase(value="classpath:/graveUpdateDb.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void submitGrave() throws Exception
	{
		Grave g = grvMgr.getGrave(130023L);
		grvMgr.detach(g);
		MockHttpServletRequestBuilder rb = MockMvcRequestBuilders.post("/yard/graveDetails.html");
		rb = ObjectTraverser.traverse(rb, g, null, "validTo");
		
		/*
		rb = rb.param("doubled.id", g.getDoubled().getId().toString());
		rb = rb.param("doubled.name", g.getDoubled().getName());
		rb = rb.param("grounded.id", g.getGrounded().getId().toString());
		rb = rb.param("grounded.name", g.getGrounded().getName());
		rb = rb.param("id", g.getId().toString());
		rb = rb.param("notices", g.getNotices());
		rb = rb.param("number", String.valueOf(g.getNumber()));
		rb = rb.param("owned.id", g.getOwned().getId().toString());
		rb = rb.param("owned.name", g.getOwned().getName());
		rb = rb.param("owner.firstName", g.getOwner().getFirstName());
		rb = rb.param("owner.surname", g.getOwner().getSurname());
		rb = rb.param("postgisString", g.getPostgisString());
		rb = rb.param("sector", g.getSector());*/
		
		rb = rb.param("validTo", String.valueOf(4096));
		
		mvc.perform(rb).andExpect(MockMvcResultMatchers.status().isOk());
	}
}
