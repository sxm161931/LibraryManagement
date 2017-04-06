package com.test.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;





@Entity(name = "department")
public class Department {

	@Id
	@Column(name = "Dnumber")
	int deptNo;
	@Column(name = "Dname")
	String dname;
	@Column(name = "Mgrssn")
	String mgrSSN;
	@Column(name = "Mgr_start_date")
	String mrgStartDt;
	public int getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getMgrSSN() {
		return mgrSSN;
	}
	public void setMgrSSN(String mgrSSN) {
		this.mgrSSN = mgrSSN;
	}
	public String getMrgStartDt() {
		return mrgStartDt;
	}
	public void setMrgStartDt(String mrgStartDt) {
		this.mrgStartDt = mrgStartDt;
	}
	
	

}
