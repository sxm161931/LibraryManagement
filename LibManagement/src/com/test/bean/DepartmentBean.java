package com.test.bean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ComponentSystemEvent;

import com.test.dao.GetDeptDetails;
import com.test.entity.Department;

@ManagedBean
@SessionScoped
public class DepartmentBean {
	
	
	List<Department> deptList;
	int deptNo;
	String dname;
	String mgrSSN;
	String mrgStartDt;
	
	
	
	public DepartmentBean() {
		super();
	}



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



	public List<Department> getDeptList() {
		return deptList;
	}



	public void setDeptList(List<Department> deptList) {
		this.deptList = deptList;
	}



	
	 public void getPreRenderDetails(ComponentSystemEvent c) {
		 if(deptList==null){
		GetDeptDetails getDetails = new GetDeptDetails();
		deptList = getDetails.getDeptDetails();
		 }
       
    }
	 
	 
	 public String afterFormSubmit()
	 {
		 System.out.println(this.dname);
		 return "success";
	 }
	

}
