package com.test.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.test.bean.BookBean;
import com.test.bean.SearchAttribute;
import com.test.entity.CreateSession;


public class BookDAO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static List<BookBean> fetchResults(String enteredString)
	{
		String[] values = enteredString.replaceAll("^[,\\s]+", "").split("[,\\s]+");
		List<BookBean> bookBeanResult = new ArrayList<>();
		Session session = CreateSession.getSession().openSession();
		HashMap<String , Set<String>> bookAuth = new HashMap<>();
		HashMap<String, SearchAttribute> bookOther = new HashMap<>();
		/*HashMap<String, Boolean> available = new HashMap<>();
		HashMap<String, String> cover = new HashMap<>();*/
		for(String word : values)
		{
			
			//searchedResult = session.createQuery("select bk from BookEntity bk JOIN bk.authors  bka WHERE concat ( lower(bk.isbn) , lower(bk.title), lower(bka.authorId)) like :word ");
			Query query = session.createQuery("select b.isbn, b.title, a.name, b.cover, b.available FROM BookAuthorsEntity ba, BookEntity b, AuthorsEntity a where ba.id.isbn = b.isbn and ba.id.authorId = a.authorId and ((lower(b.isbn) like :word) or (lower(b.title) like :word) or (lower(a.name) like :word))");
			
			query.setParameter("word", "%"+word.toLowerCase()+"%");

			//searchedResult = session.createQuery("from BookEntity where lower(isbn) like :word or lower(title) like :word or lower (authors.authorId) like :word  ");
			//searchedResult.setParameter("word", ("%"+word+"%").toLowerCase());
			//List<BookEntity> bookList = (List<BookEntity>)searchedResult.list();
			List<Object[]> bookList =query.list();
			System.out.println(bookList.size());
			if(bookList.size()>0)
			{
			
			
			for(Object[] book : bookList)
			{
		
				//String author = (String) book[2];
				if(bookOther.size() == 0)
				{
					SearchAttribute attr = new SearchAttribute();
					attr.setAvailable((boolean) book[4]);
					attr.setCover((String) book[3]);
					attr.setTitle((String) book[1]);
					
					
					bookOther.put((String) book[0], attr);
				}
				else if(bookOther.size()>0 && bookOther.get(book[0])==null)
				{
					SearchAttribute attr = new SearchAttribute();
					attr.setAvailable((boolean) book[4]);
					attr.setCover((String) book[3]);
					attr.setTitle((String) book[1]);
					bookOther.put((String) book[0], attr);
				}
				
				
				if(bookAuth.size() >0 && bookAuth.get(book[0])!=null)
				{
					if(!bookAuth.get(book[0]).contains(book[2]))
					bookAuth.get((String) book[0]).add((String) book[2]);
					
				}
				else
				{
					Set<String> auth = new HashSet<>();
					auth.add((String) book[2]);
					
					bookAuth.put((String) book[0], auth);
				}
					
					
				
				
				
			
				
				
				
				
			}
			
			
			
			
			
		/*	bookBean.setCover((String) book[3]);
			bookBean.setIsbn((String) book[0]);
			bookBean.setTitle((String) book[1]);
			bookBean.setAvailable((boolean) book[4]);
			bookBean.setAuthors(auths);
			System.out.println("Title :" +(String) book[1] );
			
			System.out.println("Book :" +(String) book[0]);
			System.out.println("Authors :"+ auths);
			bookBeanResult.add(bookBean);*/
			
			//bookBeanResult = new ArrayList<>(bookSet);
			
			}
			System.out.println(bookBeanResult.size());
		}
		
		
		 Iterator it = bookOther.entrySet().iterator();
		 System.out.println(bookOther.size());
		 while(it.hasNext())
		 {
			  Map.Entry pair = (Map.Entry)it.next();
			  StringBuilder sb = new StringBuilder();
			  SearchAttribute search = (SearchAttribute) pair.getValue();
				for (String s : bookAuth.get(pair.getKey())) 
				{ 
					sb.append(s).append(","); 
				} 
				String auths = sb.deleteCharAt(sb.length() - 1).toString();
			BookBean book = new BookBean();
			book.setAuthors(auths);
			book.setAvailable(search.isAvailable());
			if(search.isAvailable())
			{
				book.setIcon("fa fa-check");
			}
			else
			{
				book.setIcon("fa fa-close");
			}
			book.setCover(search.getCover());
			book.setIsbn((String) pair.getKey().toString());
			book.setTitle(search.getTitle());
			bookBeanResult.add(book);
			 
			 
		 }
		
		session.close();
		return  (bookBeanResult);
	}

}
