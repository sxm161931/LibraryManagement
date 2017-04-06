package com.test.bean;

import java.io.Serializable;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.test.dao.BorrowerDAO;
import com.test.entity.BookLoansEntity;


@ManagedBean(name="borrowerBean")
@RequestScoped
public class BorrowerBean  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String ssn;
	String fName;
	String lName;
	String address;
	String city;
	String state;
	String phone;
	String email;
	Set<BookLoansEntity> bookLoan;
	
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<BookLoansEntity> getBookLoan() {
		return bookLoan;
	}
	public void setBookLoan(Set<BookLoansEntity> bookLoan) {
		this.bookLoan = bookLoan;
	}
	public BorrowerBean() {
		super();
	}
	
	
	
	
	 public void reset() {
	this.address="";
	this.city="";
	this.email="";
	this.fName="";
	this.lName="";
	this.phone="";
	this.state="";
	this.ssn="";
	}
	
	public void addBorrower(ActionEvent e)
	{
		System.out.println("here");
		System.out.println(this.getSsn());
		System.out.println(this.getPhone());
		String message="sucess";
		message=		BorrowerDAO.addBorrower(this);
		
		if(message.equalsIgnoreCase("duplicate"))
		{
			addMessage("Operation Failed!!! SSN already present.");
		}
		else if(message.equalsIgnoreCase("failed"))
		{
			addMessage("Operation Failed!!!");
		}
		else{
			System.out.println(message);
			addMessage(message);
		
			
		}
		
		reset();
		
	}
	/*public void reset(ActionEvent e) {
		System.out.println("reset");
        RequestContext.getCurrentInstance().reset("borrowerForm:borrowerPanel");
    }*/
	 public void addMessage(String btn) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( btn));
	    }
}
