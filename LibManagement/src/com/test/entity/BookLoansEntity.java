package com.test.entity;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BOOK_LOANS")
public class BookLoansEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int loan_id;
	BookEntity book;
	BorrowerEntity bId;
	FineEntity fine;
	Timestamp dueDate;
	Timestamp dateout;
	Timestamp dateIn;
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="Loan_id",updatable=false)
	public int getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(int loan_id) {
		this.loan_id = loan_id;
	}
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="Card_id",nullable=false)
	public BorrowerEntity getbId() {
		return bId;
	}
	public void setbId(BorrowerEntity bId) {
		this.bId = bId;
	}
	
	@ManyToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="ISBN")
	public BookEntity getBook() {
		return book;
	}
	public void setBook(BookEntity book) {
		this.book = book;
	}
	@OneToOne(mappedBy="bookLoan", cascade=CascadeType.ALL)
	public FineEntity getFine() {
		return fine;
	}
	public void setFine(FineEntity fine) {
		this.fine = fine;
	}
	
	@Column(name="Due_date",updatable=false)
	public Timestamp getDueDate() {
		return dueDate;
	}
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	
	@Column(name="Date_out",updatable=false)
	public Timestamp getDateout() {
		return dateout;
	}
	public void setDateout(Timestamp dateout) {
		this.dateout = dateout;
	}
	@Column(name="Date_in")
	public Timestamp getDateIn() {
		return dateIn;
	}
	public void setDateIn(Timestamp dateIn) {
		this.dateIn = dateIn;
	}
	
	
	
	

}
