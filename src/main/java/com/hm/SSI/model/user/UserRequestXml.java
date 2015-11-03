package com.hm.SSI.model.user;

import javax.xml.bind.annotation.XmlRootElement;

import com.sun.xml.txw2.annotation.XmlElement;

@Deprecated
@XmlRootElement(name = "request")
public class UserRequestXml {
	
	private String ciCode;
	private String password;
	/**
	 * @return the ciCode
	 */
	public String getCiCode() {
		return ciCode;
	}
	/**
	 * @param ciCode the ciCode to set
	 */
	@XmlElement
	public void setCiCode(String ciCode) {
		this.ciCode = ciCode;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	@XmlElement
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
