package il.co.topq.systems.report.component.jaxbWrappers;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class UserRoleList {

	private List<String> userRoles;

	public UserRoleList() {
	}

	public UserRoleList(List<String> userRoles) {
		this.userRoles = userRoles;
	}

	public List<String> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<String> userRoles) {
		this.userRoles = userRoles;
	}

}
