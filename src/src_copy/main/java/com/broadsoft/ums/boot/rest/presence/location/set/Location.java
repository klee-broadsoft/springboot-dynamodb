package com.broadsoft.ums.boot.rest.presence.location.set;

public class Location {

	private String cnty;
	private String city;
	private String reg;
	private String tz;
	private String loctext;

	public String getCnty() {
		return cnty;
	}

	public void setCnty(String cnty) {
		this.cnty = cnty;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getTz() {
		return tz;
	}

	public void setTz(String tz) {
		this.tz = tz;
	}

	public String getLoctext() {
		return loctext;
	}

	public void setLoctext(String loctext) {
		this.loctext = loctext;
	}
}