package com.test.testCases;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class Book {

	String isbn;
	String title;
	String authors;
	boolean available;
	String cover;
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
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	
	public List<Book> create(int size)
	{
		int init = size-10;
		System.out.println(init);
		List<Book> bookList = new ArrayList<>();
		System.out.println("here");
		Book b1 = new Book();
		b1.setIsbn("0195153448");
		b1.setTitle("Classical Mythology");
		String s ="Mark P. O. Morford,Robert J. Lenardon";
//		ArrayList<String> authors = (ArrayList<String>) Arrays.asList(s.split(","));
		b1.setAuthors(s);
		b1.setAvailable(true);
		b1.setCover("http://www.openisbn.com/cover/0195153448_72.jpg");
		for(int i=init;i<size;i++)
			bookList.add(b1);
		return  bookList;
	}
}
