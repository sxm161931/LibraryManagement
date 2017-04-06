/*package com.test.testCases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.test.entity.AuthorsEntity;
import com.test.entity.BookAuthorPK;
import com.test.entity.BookAuthorsEntity;
import com.test.entity.BookEntity;
import com.test.entity.CreateSession;

public class BooksCSVReader {

	public static void main(String[] args) {

		String csvFile = "S:\\UTD Courses\\Spring17\\CS 6360\\Projects\\Proj1\\books2.csv";
		String line = "";
		String cvsSplitBy = "\\t";
		Session session=null;
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			SessionFactory sf = CreateSession.getSession();
			
			
			br.readLine();
			while ((line = br.readLine()) != null) {
				session = sf.openSession();
				// use comma as separator
				String[] bookDetails = line.split(cvsSplitBy);
				int length = bookDetails.length;
				// int pages = Integer.parseInt(bookDetails[length-1]);
				// String authors = bookDetails[3];
				String page = bookDetails[length - 1];
				if (Integer.parseInt(page) > 0) {
					// System.out.println(authors);
					//System.out.println(page);
					Set<Integer> authorList = new HashSet<>();
					
					String[] authors1 = bookDetails[3].split(",");
					for (String s : authors1) {
						s = s.replaceAll("[^\\x00-\\x7F]", "");
						AuthorsEntity auth = null;
						Query query = session.createQuery("from AuthorsEntity  where name = :name ");
						query.setParameter("name", s);
					    if(query.list().size()>0)
					    {
					    	auth = (AuthorsEntity) query.list().get(0);
					    	if(!authorList.contains(auth.getAuthorId()))
					    	authorList.add(auth.getAuthorId());
					    }
						
					    else{
						auth = new AuthorsEntity();
						//String resultString = subjectString.replaceAll("[^\\x00-\\x7F]", "");
						auth.setName(s);
						int authId = (Integer) (session.save(auth));
						//if(!authorList.contains(authId))
						authorList.add(authId);
					   // }
						
					}
					String bookId = "";
					BookEntity book  = (BookEntity) session.get(BookEntity.class, bookDetails[0]);
					if(book == null){
					book = new BookEntity();
					//book.setAuthors((authorList));
					BookEntity book = new BookEntity();
					book.setCover(bookDetails[4]);
					book.setIsbn(bookDetails[0]);
					book.setPages(page);
					book.setAvailable(true);
					book.setPublisher(bookDetails[5].replaceAll("[^\\x00-\\x7F]", ""));
					book.setTitle(bookDetails[2].replaceAll("[^\\x00-\\x7F]", ""));
					bookId = (String) session.save(book);
					}
					else
					{
						bookId = book.getIsbn();
					}
					BookAuthorsEntity bookAuthor = new BookAuthorsEntity();
					
					for(int i : authorList)
					{
						BookAuthorPK bpk = new BookAuthorPK();
						bpk.setAuthorId(i);
						bpk.setIsbn(bookId);
						bookAuthor.setId(bpk);
						session.saveOrUpdate(bookAuthor);
					}
					
					
					Transaction trans = session.beginTransaction();
					try {
						 trans.commit();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// trans.rollback();
						e.getMessage();
					} 
					finally {
						session.close();
					}
				}

			}
			System.out.println("end of file");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
*/