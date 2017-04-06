package com.test.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="FINES")
public class FineEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int loanId;
	BookLoansEntity bookLoan;
	String fineAmt;
	boolean paid;
	Timestamp paidDate;
	
	
	@Id
	@Column(name="Loan_Id",updatable=false)
	@GeneratedValue( generator="gen" )
	@GenericGenerator(name="gen", strategy="foreign",
	parameters=@Parameter(name="property", value="bookLoan"))
	public int getLoanId() {
		return loanId;
	}
	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}
	
	@Column(name="Fine_amt")
	public String getFineAmt() {
		return fineAmt;
	}


	@OneToOne
	@PrimaryKeyJoinColumn
	public BookLoansEntity getBookLoan() {
		return bookLoan;
	}
	public void setBookLoan(BookLoansEntity bookLoan) {
		this.bookLoan = bookLoan;
	}
	public void setFineAmt(String fineAmt) {
		this.fineAmt = fineAmt;
	}
	
	@Column(name ="Paid",columnDefinition = "boolean default false")
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
	@Column(name ="PaidDate")
	public Timestamp getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(Timestamp paidDate) {
		this.paidDate = paidDate;
	}
	
	
	

}
