package il.co.topq.systems.report.common.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

	private static final long serialVersionUID = 1170970442013370537L;

	@XmlAttribute
	private Integer id;
	@XmlElement
	private String username;
	@XmlElement
	private String password;
	@XmlElement
	private String permissions;
	@XmlTransient
	private String uiConfigurations;
	@XmlElement
	private String firstName;
	@XmlElement
	private String lastName;

	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public String getUiConfigurations() {
		return uiConfigurations;
	}

	public void setUiConfigurations(String uiConfigurations) {
		this.uiConfigurations = uiConfigurations;
	}

	// TODO: delete this to string as it is only used for debug
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
