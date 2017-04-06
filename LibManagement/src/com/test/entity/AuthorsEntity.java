package com.test.entity;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "AUTHORS")
public class AuthorsEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int authorId;
	String name;
	
	Set<BookEntity> books;
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "AUTHOR_ID", unique = true)
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	@Column(name="NAME",nullable=false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToMany(mappedBy="authors",cascade = CascadeType.ALL)
	public Set<BookEntity> getBooks() {
		return books;
	}
	public void setBooks(Set<BookEntity> books) {
		this.books = books;
	}
	
	

}
