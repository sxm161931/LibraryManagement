package com.test.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the book_authors database table.
 * 
 */
@Embeddable
public class BookAuthorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String isbn;

	@Column(name="AUTHOR_ID")
	private int authorId;

    public BookAuthorPK() {
    }
	public String getIsbn() {
		return this.isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getAuthorId() {
		return this.authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BookAuthorPK)) {
			return false;
		}
		BookAuthorPK castOther = (BookAuthorPK)other;
		return 
			this.isbn.equals(castOther.isbn)
			&& (this.authorId == castOther.authorId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.isbn.hashCode();
		hash = hash * prime + this.authorId;
		
		return hash;
    }
}