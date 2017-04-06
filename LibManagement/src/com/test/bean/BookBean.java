package com.test.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ComponentSystemEvent;

import com.test.dao.BookDAO;

@ManagedBean(name="bookBean")
@SessionScoped
public class BookBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String isbn;
	String title;
	String cover;
	String publisher;
	String pages;
	String authors;
	boolean available;
	String word;
	String icon;
	Set<BookLoansBean> bookLoanBean;
	List<BookBean> bookList;
	String selectedBook;
	BookBean selectedSearchedBook;
	String bId;
	
	
	public String getbId() {
		return bId;
	}
	public void setbId(String bId) {
		this.bId = bId;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public Set<BookLoansBean> getBookLoanBean() {
		return bookLoanBean;
	}
	public void setBookLoanBean(Set<BookLoansBean> bookLoanBean) {
		this.bookLoanBean = bookLoanBean;
	}
	
	
	public BookBean getSelectedSearchedBook() {
		return selectedSearchedBook;
	}
	public void setSelectedSearchedBook(BookBean selectedSearchedBook) {
		this.selectedSearchedBook = selectedSearchedBook;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public List<BookBean> getBookList() {
		return bookList;
	}
	public void setBookList(List<BookBean> bookList) {
		this.bookList = bookList;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	
	
	public String getSelectedBook() {
		return selectedBook;
	}
	public void setSelectedBook(String selectedBook) {
		this.selectedBook = selectedBook;
	}
	

	public String fetchSearchedResult()
	{
		//this.bookList = BookDAO.fetchResults(this.getWord());
		
		//this.setWord("");
		System.out.println("word"+this.word);
		this.setBookList(BookDAO.fetchResults(this.getWord()));
		System.out.println(this.bookList.size());
		if(this.bookList.size()>0){
		//return "searchResult";
			return "modifiedSearchResult";
		}
		else{
			addMessage("No Result Found.");
			return "";
			
		}
		
	}
	
	public void addMessage(String btn) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(btn));
    }
	
	public String fetchSelectedBook()
	{
		System.out.println("here");
		System.out.println(this.selectedBook);
		return "#";
	}
	
	public void init(ComponentSystemEvent ce)
	{
		/*if(this.word != "")
		this.bookList = BookDAO.fetchResults(this.word);*/
		System.out.println("init called");
		this.getBookList();
		//return "modifiedSearchResult";
		/*FacesContext fc = FacesContext.getCurrentInstance();
		ConfigurableNavigationHandler nav
		   = (ConfigurableNavigationHandler)
			fc.getApplication().getNavigationHandler();

		nav.performNavigation("modifiedSearchResult");*/
	}
	
	public void selectedBookFromSearch(ActionEvent event)
	{
		System.out.println("selectedBookFromSearch");
		//System.out.println(book.title);
		this.selectedSearchedBook = (BookBean) event.getComponent().getAttributes().get("bookSelected");
		System.out.println(this.selectedSearchedBook.title);
		//RequestContext.getCurrentInstance().openDialog("selectedSearchBook");
		//return "";
	}
}
