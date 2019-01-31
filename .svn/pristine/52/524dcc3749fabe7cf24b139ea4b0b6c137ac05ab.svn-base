package org.parafia.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents the basic "user" object in AppFuse that allows for
 * authentication and user management. It implements Acegi Security's
 * UserDetails interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Updated by
 *         Dan Kibler (dan@getrolling.com) Extended to implement Acegi
 *         UserDetails interface by David Carter david@carter.net
 */
@Entity
@Table(name = "app_user")
public class User extends BaseObject implements Serializable, UserDetails
{
	private static final long serialVersionUID = 3832626162173359411L;

	private Long id;
	private String username; // required
	private String password; // required
	private String confirmPassword;
	private String firstName; // required
	private String lastName; // required
	private String deleteCode;
	private Integer version;
	private Set<Role> roles = new HashSet<Role>();
	private boolean enabled;
	private boolean accountExpired;
	private boolean accountLocked;
	private boolean credentialsExpired;

	/**
	 * Default constructor - creates a new instance with no values set.
	 */
	public User() {
	}

	/**
	 * Create a new instance and set the username.
	 * 
	 * @param username
	 *            login name for user.
	 */
	public User(final String username) {
		this.username = username;
	}

	public User(User u) {
		id = u.getId();
		username = u.getUsername();
		password = u.getPassword();
		confirmPassword = u.getConfirmPassword();
		firstName = u.getFirstName();
		lastName = u.getLastName();
		deleteCode = u.getDeleteCode();
		version = u.getVersion();
		roles = u.getRoles();
		enabled = u.isEnabled();
		accountExpired = u.isAccountExpired();
		accountLocked = u.isAccountLocked();
		credentialsExpired = u.isCredentialsExpired();
	};

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId()
	{
		return id;
	}

	@Column(nullable = false, length = 50, unique = true)
	public String getUsername()
	{
		return username;
	}

	@Column(nullable = false)
	public String getPassword()
	{
		return password;
	}

	@Transient
	public String getConfirmPassword()
	{
		return confirmPassword;
	}

	@Column(name = "first_name", nullable = false, length = 50)
	public String getFirstName()
	{
		return firstName;
	}

	@Column(name = "last_name", nullable = false, length = 50)
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * Returns the full name.
	 * 
	 * @return firstName + ' ' + lastName
	 */
	@Transient
	public String getFullName()
	{
		return firstName + ' ' + lastName;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_id") }, inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles()
	{
		return roles;
	}

	/**
	 * Convert user roles to LabelValue objects for convenience.
	 * 
	 * @return a list of LabelValue objects with role information
	 */
	@Transient
	public List<LabelValue> getRoleList()
	{
		List<LabelValue> userRoles = new ArrayList<LabelValue>();

		if (this.roles != null)
		{
			for (Role role : roles)
			{
				// convert the user's roles to LabelValue Objects
				userRoles.add(new LabelValue(role.getName(), String.valueOf(role.getId())));
			}
		}

		return userRoles;
	}

	/**
	 * Adds a role for the user
	 * 
	 * @param role
	 *            the fully instantiated role
	 */
	public void addRole(Role role)
	{
		getRoles().add(role);
	}

	// TODO: may be wrongly configured - maybe I should put role.getName instead?
	@Transient
	public Set<GrantedAuthority> getAuthorities()
	{
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (Role role : this.roles)
		{
			authorities.add(new GrantedAuthorityImpl(role.getAuthority()));
		}
		return authorities;
	}

	@Version
	public Integer getVersion()
	{
		return version;
	}

	@Column(name = "account_enabled")
	public boolean isEnabled()
	{
		return enabled;
	}

	@Column(name = "account_expired", nullable = false)
	public boolean isAccountExpired()
	{
		return accountExpired;
	}

	/**
	 * @see org.acegisecurity.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Transient
	public boolean isAccountNonExpired()
	{
		return !isAccountExpired();
	}

	@Column(name = "account_locked", nullable = false)
	public boolean isAccountLocked()
	{
		return accountLocked;
	}

	/**
	 * @see org.acegisecurity.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Transient
	public boolean isAccountNonLocked()
	{
		return !isAccountLocked();
	}

	@Column(name = "credentials_expired", nullable = false)
	public boolean isCredentialsExpired()
	{
		return credentialsExpired;
	}

	@Column(name = "delete_code", length = 63)
	public String getDeleteCode()
	{
		return deleteCode;
	}

	public void setDeleteCode(String deleteCode)
	{
		this.deleteCode = deleteCode;
	}

	/**
	 * @see org.acegisecurity.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Transient
	public boolean isCredentialsNonExpired()
	{
		return !credentialsExpired;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public void setRoles(Set<Role> roles)
	{
		this.roles = roles;
	}

	public void setVersion(Integer version)
	{
		this.version = version;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public void setAccountExpired(boolean accountExpired)
	{
		this.accountExpired = accountExpired;
	}

	public void setAccountLocked(boolean accountLocked)
	{
		this.accountLocked = accountLocked;
	}

	public void setCredentialsExpired(boolean credentialsExpired)
	{
		this.credentialsExpired = credentialsExpired;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof User))
		{
			return false;
		}

		final User user = (User) o;

		return !(username != null ? !username.equals(user.getUsername()) : user.getUsername() != null);

	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode()
	{
		return (username != null ? username.hashCode() : 0);
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString()
	{
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("username", this.username)
				.append("enabled", this.enabled).append("accountExpired", this.accountExpired)
				.append("credentialsExpired", this.credentialsExpired).append("accountLocked", this.accountLocked);

		Set<GrantedAuthority> auths = this.getAuthorities();
		if (auths != null)
		{
			sb.append("Granted Authorities: ");

			for (GrantedAuthority auth : auths)
			{
				sb.append(auth);
				sb.append(", ");
			}
		}
		else
		{
			sb.append("No Granted Authorities");
		}
		return sb.toString();
	}
}
