package com.test.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;




@Entity
@Table(name = "BOOK_AUTHORS")
public class BookAuthorsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private BookAuthorPK id;

    public BookAuthorsEntity() {
    }

	public BookAuthorPK getId() {
		return this.id;
	}

	public void setId(BookAuthorPK id) {
		this.id = id;
	}
	
}
