package com.test.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="BOOK")
public class BookEntity implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String isbn;
	String title;
	String cover;
	String publisher;
	String pages;
	boolean available;
	Set<BookLoansEntity> bookLoan;
	
	Set<AuthorsEntity> authors;
	
	@Id
	@Column(name="ISBN", length=10,updatable=false,unique=true,nullable=false)
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	
	@Column(name = "TITLE", nullable=false)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	@ManyToMany(cascade = CascadeType.ALL,fetch= FetchType.EAGER)
	@JoinTable(name = "BOOK_AUTHORS", joinColumns = {@JoinColumn( name="ISBN" ) },foreignKey =@ForeignKey(name ="FK_ISBN"), inverseJoinColumns={@JoinColumn(name="AUTHOR_ID")}, inverseForeignKey=@ForeignKey(name ="FK_AUTHOR_ID"))
	
	public Set<AuthorsEntity> getAuthors() {
		return authors;
	}
	public void setAuthors(Set<AuthorsEntity> authors) {
		this.authors = authors;
	}
	/*public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	*/
	
	@Column(name="Cover")
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	@Column(name="Publisher")
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	@Column(name="Pages")
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	
	@Column(name="Available",columnDefinition = "boolean default true")
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy ="book")
	public Set<BookLoansEntity> getBookLoan() {
		return bookLoan;
	}
	public void setBookLoan(Set<BookLoansEntity> bookLoan) {
		this.bookLoan = bookLoan;
	}
	
	

}
