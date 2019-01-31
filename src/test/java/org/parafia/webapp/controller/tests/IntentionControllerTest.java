package org.parafia.webapp.controller.tests;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parafia.Constants;
import org.parafia.model.Intention;
import org.parafia.model.IntentionAnnotation;
import org.parafia.service.IntentionAnnotationManager;
import org.parafia.service.IntentionManager;
import org.parafia.webapp.controller.IntentionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
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
public class IntentionControllerTest
{
	@Autowired
	private IntentionController controller;
	@Autowired
	private IntentionManager intMgr;
	@Autowired
	private IntentionAnnotationManager intaMgr;
	@Autowired
	private WebApplicationContext wac;

	MockMvc mvc;

	@Before
	public void setup()
	{
		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void getActiveCelebrants()
	{
		List<?> result = controller.getActiveCelebrants(1L, false);
		Assert.assertEquals(2, result.size());
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void getCelebrantList()
	{
		List<?> result = controller.getCelebrantList();
		Assert.assertEquals(2, result.size());
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void getAllTypes()
	{
		List<?> types = intMgr.loadAllTypes();
		List<?> typesA = controller.getAllTypes();
		Assert.assertEquals(types, typesA);
	}

	@Test
	public void getDefaultDate()
	{
		MockHttpSession s = new MockHttpSession();
		s.setAttribute(Constants.INTENTION_DEFAULT_DATE, new Date(123456L));
		String date = controller.getDefaultDate(s);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String expected = sdf.format(new Date(123456L));
		Assert.assertEquals(expected, date);
	}

	@Test
	public void getJsonFeed() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/tomes/intentions.json").accept(MediaType.APPLICATION_JSON)
				.param("_", "123456789").param("start", "2017-10-04").param("end", "2017-10-11"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void drawCalendar() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.get("/tomes/intentionTome.html").accept(MediaType.TEXT_HTML))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/tomes/intentionTome"));
	}

	@Test
	public void listIntentions() throws Exception
	{
		List<Intention> intentions = new ArrayList<>();
		intentions = intMgr.loadIntentions(new Date());
		mvc.perform(MockMvcRequestBuilders.get("/tomes/intentionsList.html").accept(MediaType.TEXT_HTML)
				.param("list", "1").param("date", "123456")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/tomes/intentionsList"))
				.andExpect(MockMvcResultMatchers.model().attribute("intentions", intentions));
	}

	@Test
	public void addIntention() throws Exception
	{
		Intention intention = new Intention();
		intention.setDate(new Date(123456L));
		mvc.perform(MockMvcRequestBuilders.get("/tomes/editIntention.html").accept(MediaType.TEXT_HTML)
				.param("edit", "true").param("date", "123456")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/tomes/editIntention"))
				.andExpect(MockMvcResultMatchers.model().attribute("intention", intention));
	}

	@Test
	public void addIntentionAnnotation() throws Exception
	{
		IntentionAnnotation intention = new IntentionAnnotation();
		intention.setDate(new Date(123456L));
		mvc.perform(MockMvcRequestBuilders.get("/tomes/editIntention.html").accept(MediaType.TEXT_HTML)
				.param("edit", "true").param("date", "123456").param("allDay", "true"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/tomes/editIntentionAnnotation"))
				.andExpect(MockMvcResultMatchers.model().attribute("intentionAnnotation", intention));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void deleteIntention() throws Exception
	{
		List<Intention> intentions = new ArrayList<>();
		intentions = intMgr.loadIntentions(new Date(123456L));
		for (Intention i : intentions)
		{
			if (i.getId() == 2L)
			{
				intentions.remove(i);
				break;
			}
		}
		mvc.perform(MockMvcRequestBuilders.get("/tomes/intentionTome.html").accept(MediaType.TEXT_HTML)
				.param("delete", "145330").param("date", "123456")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/tomes/intentionsList"))
				.andExpect(MockMvcResultMatchers.model().attribute("intentions", intentions));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	//@ExpectedDatabase(value = "classpath:/celebrantsDbAfterInsert.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void saveIntention() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.post("/tomes/editIntention.html").accept(MediaType.TEXT_HTML)
				.param("acceptor", "145347").param("celebrantList", "145347").param("celebrantList", "145328").param("confession", "145328")
				.param("date", "123456").param("offering", "1234").param("type", "1").param("text", "lorem ipsum")
				.param("notices", "null").param("duration", "1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("common/closeReloadParent"));
		intMgr.loadIntentions(new Date(123456L));
	}

	@Test
	//@ExpectedDatabase(value="classpath:/intentionsAnnotationsDb.xml", assertionMode=DatabaseAssertionMode.NON_STRICT)
	public void saveIntentionAnnotation() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.post("/tomes/editIntention.html").accept(MediaType.TEXT_HTML)
				.param("allDay", "true").param("annotation", "test").param("date", "123456"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("common/closeReloadParent"));
		intaMgr.loadIntentionAnnotations(new Date(123456L), new Date(654321L));
	}
}
