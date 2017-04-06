package com.test.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.test.dao.BookLoanDAO;
import com.test.entity.FineEntity;

@ManagedBean(name="bookLoansBean")
@RequestScoped
public class BookLoansBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int loan_id;
	BookBean book;
	String bId;
	FineEntity fine;
	Timestamp dueDate;
	Timestamp dateout;
	Timestamp dateIn;
	String isbn;
	String name;
	List<BookLoansBean> list;
	List<BookLoansBean> selectedLoans;
	
	public int getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(int loan_id) {
		this.loan_id = loan_id;
	}
	public BookBean getBook() {
		return book;
	}
	public void setBook(BookBean book) {
		this.book = book;
	}
	public String getbId() {
		System.out.println("get");
		
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
	public FineEntity getFine() {
		return fine;
	}
	public void setFine(FineEntity fine) {
		this.fine = fine;
	}
	public Timestamp getDueDate() {
		return dueDate;
	}
	public void setDueDate(Timestamp dueDate) {
		this.dueDate = dueDate;
	}
	public Timestamp getDateout() {
		return dateout;
	}
	public void setDateout(Timestamp dateout) {
		this.dateout = dateout;
	}
	public Timestamp getDateIn() {
		return dateIn;
	}
	public void setDateIn(Timestamp dateIn) {
		this.dateIn = dateIn;
	}
	
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	

	
	
	public List<BookLoansBean> getSelectedLoans() {
		return selectedLoans;
	}
	public void setSelectedLoans(List<BookLoansBean> selectedLoans) {
		this.selectedLoans = selectedLoans;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<BookLoansBean> getList() {
		return list;
	}
	public void setList(List<BookLoansBean> list) {
		this.list = list;
	}
	public void reset()
	{
		//this.bId="";
		this.isbn="";
		this.name="";
	}
	public void checkOutBook(ActionEvent e)
	{
		String message=BookLoanDAO.checkOutBook(this);
		System.out.println(message);
		addMessage(message);
		reset();
	}
	public void addMessage(String btn) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(btn));
    }
	
	
	
	public String checkInBooks()
	{
		this.list = null;
		this.list=BookLoanDAO.getSelectedcheckInBooks(this);
		if(list.size()>0)
		{
			reset();
			return "checkInBookNext";
		}
			
		else
		{
			addMessage("No books due");
			reset();
			return "checkInBook";
		}
		
	}
	
	public String getSelectedBookLoans()
	{
		System.out.println("here in selected books");
		System.out.println(this.getSelectedLoans().size());
		String message =BookLoanDAO.checkInBooks(this.selectedLoans);
		addMessage(message);
		this.selectedLoans = new ArrayList<>();
		reset();
		return "checkInBook";
	}
	
	public void searchedCheckOut(ActionEvent e)
	{
		/* String value = FacesContext.getCurrentInstance().
					getExternalContext().getRequestParameterMap().get("hidden1");*/
		System.out.println("here");
		this.book = (BookBean) e.getComponent().getAttributes().get("bookSelected");
		//String id =  (String) e.getComponent().getAttributes().get("idSelected");
		System.out.println(this.bId);
		this.isbn = this.book.isbn;
		System.out.println(this.book.isbn);
		String message=BookLoanDAO.checkOutBook(this);
		System.out.println(message);
		addMessage(message);
		this.bId="";
	}
	public void setBId(ValueChangeEvent ev) {
		System.out.println("inside setbiid");
		
		  this.bId=((String) ev.getNewValue());
		  this.book.bId =  this.bId;
		  System.out.println(this.bId + " and " + this.book.bId);
		  // prevent setter being called again during update-model phase
		 // ((UIInput) ev.getComponent()).setLocalValueSet(false);
}
}
