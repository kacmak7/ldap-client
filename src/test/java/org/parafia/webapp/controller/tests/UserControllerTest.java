package org.parafia.webapp.controller.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parafia.Constants;
import org.parafia.model.LabelValue;
import org.parafia.model.Role;
import org.parafia.model.User;
import org.parafia.service.RoleManager;
import org.parafia.service.UserManager;
import org.parafia.webapp.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
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
public class UserControllerTest
{
	@Autowired
	private UserController controller;
	@Autowired
	private RoleManager roleMgr;
	@Autowired
	private UserManager usrMgr;
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
	public void request() throws Exception
	{
		List<?> users = usrMgr.getUsers(null);
		mvc.perform(MockMvcRequestBuilders.get("/admin/users.html").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.TEXT_HTML)).andExpect(MockMvcResultMatchers.view().name("admin/userList"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute(Constants.USER_LIST, users));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void aviableRoles()
	{
		List<LabelValue> tested = controller.getRoleList();
		@SuppressWarnings("unchecked")
		List<Role> roles = roleMgr.getRoles();
		List<LabelValue> base = new ArrayList<>();
		for (Role r : roles)
		{
			base.add(new LabelValue(r.getName(), String.valueOf(r.getId())));
		}
		LabelValue[] expected = new LabelValue[1];
		expected = base.toArray(expected);
		LabelValue[] actual = new LabelValue[1];
		actual = tested.toArray(actual);
		Assert.assertArrayEquals(expected, actual);
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void userUpdate() throws Exception
	{
		MvcResult res = mvc.perform(MockMvcRequestBuilders.get("/userform").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).param("id", "-1").with(new RequestPostProcessor() {

					@Override
					public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request)
					{
						request.addUserRole(Constants.ADMIN_ROLE);
						request.addUserRole(Constants.USER_ROLE);
						return request;
					}
				})).andReturn();
		User u;
		{
			Object o = res.getModelAndView().getModel().get("user");
			u = User.class.cast(o);
		}
		usrMgr.detach(u);

		mvc.perform(MockMvcRequestBuilders.post("/submitUser").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).param("username", u.getUsername())
				.param("firstName", u.getFirstName()).param("lastName", u.getLastName())
				.param("id", u.getId().toString()).param("accountExpired", String.valueOf(u.isAccountExpired()))
				.param("accountLocked", String.valueOf(u.isAccountLocked()))
				.param("credentialsExpired", String.valueOf(u.isCredentialsExpired()))
				.param("enabled", String.valueOf(u.isEnabled())).param("password", "test")
				.param("confirmPassword", "test").param("userRoles", "-1").param("userRoles", "-2")
				.param("version", u.getVersion().toString()))
				.andExpect(MockMvcResultMatchers.status().isMovedTemporarily())
				.andExpect(MockMvcResultMatchers.redirectedUrl("admin/users.html?cookieLogin="));
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	//@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "classpath:/userUpdateDb.xml")
	public void submitUser() throws Exception
	{
		String usrName = "Nehekhara";
		List<String> roles = new ArrayList<>();
		roles.add("-2");

		mvc.perform(MockMvcRequestBuilders.post("/submitUser").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).param("username", usrName).param("firstName", "Tester")
				.param("lastName", "Test").param("accountExpired", "false").param("accountLocked", "false")
				.param("credentialsExpired", "false").param("enabled", "false").param("password", "123")
				.param("confirmPassword", "123").param("userRoles", "-2").header("Referer", "x"))
				.andExpect(MockMvcResultMatchers.status().isFound())
				.andExpect(MockMvcResultMatchers.redirectedUrl("admin/users.html?cookieLogin="));

		@SuppressWarnings("unchecked")
		List<User> users = usrMgr.getUsers(null);
		for (User u : users)
		{
			if (u.getUsername().equalsIgnoreCase(usrName))
			{
				for (Role r : u.getRoles())
					Assert.assertEquals((Long)(-2L), r.getId());
				break;
			}
		}

	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	//@ExpectedDatabase(assertionMode = DatabaseAssertionMode.NON_STRICT, value = "classpath:/userDeleteDb.xml")
	public void deleteUser() throws Exception
	{
		mvc.perform(MockMvcRequestBuilders.post("/deleteUser").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).param("id", "-1").param("firstName", "user")
				.param("lastName", "tomcat")).andExpect(MockMvcResultMatchers.status().isFound())
				.andExpect(MockMvcResultMatchers.redirectedUrl(
						"admin/users.html?cookieLogin=&successMessages=Profil+u%3Fytkownika+user+tomcat+zosta%3F+pomy%3Flnie+usuni%3Fty."));
		@SuppressWarnings("unchecked")
		List<User> users = usrMgr.getUsers(null);
		Assert.assertEquals(1, users.size());
		Assert.assertEquals("admin", users.get(0).getUsername());
	}

	@Test
	@DatabaseSetup("classpath:/testDb.xml")
	public void showForm() throws Exception
	{
		User usr = usrMgr.getUser("-2");
		mvc.perform(MockMvcRequestBuilders.get("/userform").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).param("id", "-2").with(new RequestPostProcessor() {

					@Override
					public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request)
					{
						request.addUserRole(Constants.ADMIN_ROLE);
						request.addUserRole(Constants.USER_ROLE);
						return request;
					}
				})).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("userForm"))
				.andExpect(MockMvcResultMatchers.model().attribute("user", usr));
	}
}
