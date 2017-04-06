package com.test.testCases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class DisplaySearchBookResult implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	List<Book> bookList ;
	int endListVal;
	public int getEndListVal() {
		endListVal +=10;
		return endListVal;
	}



	public void setEndListVal(int endListVal) {
		this.endListVal = endListVal;
	}



	Book b;
	//String authorNames;
	
	
	
	@PostConstruct
	public void init()
	{
		//endListVal += 10;
	b = new Book();
	List<Book> l = new ArrayList<>();
	l=(b.create(getEndListVal()));
	if(bookList == null)
		bookList = new ArrayList<>();
	bookList.addAll(l);
	
	
	}



	public List<Book> getBookList() {
	
		return bookList;
	}



	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}



	public Book getB() {
		return b;
	}



	public void setB(Book b) {
		this.b = b;
	}



	

}
