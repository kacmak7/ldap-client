package org.parafia.webapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.parafia.Constants;
import org.parafia.model.Errors;
import org.parafia.model.LabelValue;
import org.parafia.model.Messages;
import org.parafia.model.Role;
import org.parafia.model.User;
import org.parafia.service.RoleManager;
import org.parafia.service.UserManager;
import org.parafia.service.exceptions.ObjectExistsException;
import org.parafia.webapp.propertyeditors.CustomPropertyEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Simple class to retrieve a list of users from the database.
 *
 * <p>
 * <a href="UserController.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */

@Controller
@SessionAttributes(value = { "successMessages", "cookieLogin", "errors" })
public class UserController
{

	private Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private UserManager userMgr;
	@Autowired
	private RoleManager roleMgr;
	@Autowired
	private BaseControllerTemp base;

	@ModelAttribute("successMessages")
	public Messages<String> getMessages()
	{
		return new Messages<>();
	}

	@ModelAttribute("errors")
	public Errors<String> getErrors()
	{
		return new Errors<>();
	}

	@ModelAttribute("cookieLogin")
	public String getcookieLogin()
	{
		return new String();
	}

	@SuppressWarnings("unchecked")
	@InitBinder
	public void initBinder(final WebDataBinder binder)
	{
		CustomPropertyEditor rolesEditor = new CustomPropertyEditor(roleMgr.getRoles());
		binder.registerCustomEditor(Role.class, rolesEditor);
	}

	// -- TESTED --
	@ModelAttribute("availableRoles")
	public List<LabelValue> getRoleList()
	{
		@SuppressWarnings("unchecked")
		List<Role> roles = roleMgr.getRoles();
		List<LabelValue> roleList = new ArrayList<LabelValue>();

		if (roles != null)
		{
			for (Role c : roles)
			{
				roleList.add(new LabelValue(c.getName(), String.valueOf(c.getId())));
			}
		}

		return roleList;
	}

	// -- TESTED --
	@RequestMapping("admin/users*")
	public ModelAndView handleRequest() throws Exception
	{
		if (log.isDebugEnabled())
			log.debug("entering 'handleRequest' method...");
		return new ModelAndView("admin/userList", Constants.USER_LIST, userMgr.getUsers(null));
	}

	// -- TESTED --
	@RequestMapping("/submitUser") // A form submission
	public ModelAndView submitUser(@ModelAttribute("user") User usr,
			@RequestHeader(value = "Referer", required = false) String ref,
			@RequestParam(value = "userRoles", required = false) List<String> roles,
			@ModelAttribute("successMessages") Messages<String> messages)
			throws ObjectExistsException
	{
		ModelAndView mv = new ModelAndView();
		if (usr.getConfirmPassword() != null && usr.getPassword() != null
				&& usr.getPassword().equals(usr.getConfirmPassword()))
		{
			Boolean admin = true;
			if (roles != null)
				for (String r : roles)
				{
					usr.addRole(roleMgr.getRole(Long.valueOf(r)));
				}
			userMgr.saveUser(usr);
			if (usr.getVersion() == 0)
				;// messages.add(base.getText("user.added", usr.getFullName(), locale));
			else
				;// messages.add(base.getText("user.updated.byAdmin", usr.getFullName(),
					// locale));
			if (admin)
			{
				mv.setView(new RedirectView("admin/users.html"));
				return mv;
			}
			else
			{
				mv.setView(new RedirectView("/mail/viewMails"));
				return mv;
			}

		}
		{
			RedirectView rw = new RedirectView(ref);
			mv.setView(rw);
		}
		mv.addObject("user", usr);
		return mv;
	}

	// -- TESTED --
	@RequestMapping("/deleteUser")
	public RedirectView deleteUser(@ModelAttribute("user") User usr,
			@ModelAttribute("successMessages") Messages<String> messages)
	{
		userMgr.removeUser(usr.getId().toString());
		Locale locale = LocaleContextHolder.getLocale();
		messages.add(base.getText("user.deleted", usr.getFullName(), locale));
		return new RedirectView("admin/users.html");
	}

	@RequestMapping("/userform*") // Not a form submission
	public ModelAndView showUserForm(@ModelAttribute("user") @Valid User usr,
			@RequestParam(value = "method", required = false) String method,
			@RequestParam(value = "version", required = false) String version,
			@RequestParam(value = "id", required = false) String userId,
			@ModelAttribute("successMessages") Messages<String> messages,
			@ModelAttribute("cookieLogin") String cookieLogin, @ModelAttribute("errors") Errors<String> errors)
			throws Exception
	{
		// if user logged in with remember me, display a warning that they can't change
		// passwords
		log.debug("checking for remember me login...");

		AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		if (auth != null)
		{

			if (resolver.isRememberMe(auth))
			{
				cookieLogin = "true";
				Locale locale = LocaleContextHolder.getLocale();
				messages.add(base.getText("userProfile.cookieLogin", locale));
			}
		}

		User user;
		if (userId == null && !isAdd(method))
		{
			user = userMgr.loadUserByUsername(auth.getName());
		}
		else if (!StringUtils.isBlank(userId) && !"".equals(version))
		{
			user = userMgr.getUser(userId);
		}
		else
		{
			user = new User();
			user.addRole(roleMgr.getRole(Constants.USER_ROLE));
		}

		user.setConfirmPassword(user.getPassword());

		ModelAndView mv = new ModelAndView();
		mv.addObject("user", user);
		mv.setViewName("userForm");
		Boolean admin = false;
		for(GrantedAuthority r : auth.getAuthorities())
			if(r.equals(Constants.ADMIN_ROLE))
			{
				admin = true;
				break;
			}

		if (!admin)
		{
			if (isAdd(method) || userId != null)
			{
				log.warn("User '" + auth.getName() + "' is trying to edit user with id '" + userId + "'");
				// throw new AccessDeniedException("You do not have permission to modify other
				// users.");
				errors.add("You're not allowed to modify other users");
			}
		}

		return mv;
	}

	private boolean isAdd(String method)
	{
		return (method != null && method.equalsIgnoreCase("add"));
	}
}
