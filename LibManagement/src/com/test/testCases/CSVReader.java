package com.test.testCases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.test.entity.AuthorsEntity;
import com.test.entity.BookEntity;
import com.test.entity.CreateSession;

public class CSVReader {

	public static void main(String[] args) {

		String csvFile = "S:\\UTD Courses\\Spring17\\CS 6360\\Projects\\Proj1\\books.csv";
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
					Set<AuthorsEntity> authorList = new HashSet<AuthorsEntity>();
					try {
					String[] authors1 = bookDetails[3].split(",");
					for (String s : authors1) {
						AuthorsEntity auth = null;
						Query query = session.createQuery("from AuthorsEntity  where name = :name ");
					    query.setParameter("name", s);
					    if(query.list().size()>1)
					    {
					    	auth = (AuthorsEntity) query.list().get(0);
					    }
						
					    else{
						auth = new AuthorsEntity();
						//String resultString = subjectString.replaceAll("[^\\x00-\\x7F]", "");
						s = s.replaceAll("[^\\x00-\\x7F]", "");
						auth.setName(s);
					    }
						authorList.add(auth);
					}

					BookEntity book = new BookEntity();
					book.setAuthors((authorList));
					book.setCover(bookDetails[4]);
					book.setIsbn(bookDetails[0]);
					book.setPages(page);
					String publisher = bookDetails[5].replaceAll("[^\\x00-\\x7F]", "");
					publisher = publisher.replaceAll("&amp;", "and");
					book.setPublisher(publisher);
					String title = bookDetails[2].replaceAll("[^\\x00-\\x7F]", "");
					title = title.replaceAll("&amp;", "and");
					book.setTitle(title);
					book.setAvailable(true);
					
					session.saveOrUpdate(book);
					Transaction trans = session.beginTransaction();
					
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
