package com.test.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="BORROWER")
public class BorrowerEntity implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int cardId;
	String ssn;
	String bName;
	String address;
	String phone;
	String email;
	Set<BookLoansEntity> bookLoan;
	
	@Id
	@GeneratedValue
	@Column(name="Card_id",updatable=false)
	public int getCardId() {
		return cardId;
	}
	public void setCardId(int cardId) {
		this.cardId = cardId;
	}
	
	@Column(name="Ssn",unique=true,nullable=false)
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	
	
	@Column(name="Address",nullable=false)
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name="Phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name="Email",length=100)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy ="bId",fetch=FetchType.LAZY)
	public Set<BookLoansEntity> getBookLoan() {
		return bookLoan;
	}
	public void setBookLoan(Set<BookLoansEntity> bookLoan) {
		this.bookLoan = bookLoan;
	}
	
	@Column(name = "Bname",nullable=false)
	public String getbName() {
		return bName;
	}
	public void setbName(String bName) {
		this.bName = bName;
	}
	
	
	

}
