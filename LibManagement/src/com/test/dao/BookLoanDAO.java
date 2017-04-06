package com.test.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.test.bean.BookLoansBean;
import com.test.entity.BookEntity;
import com.test.entity.BookLoansEntity;
import com.test.entity.BorrowerEntity;
import com.test.entity.CreateSession;
import com.test.utility.DateUtility;

public class BookLoanDAO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int MAX_ALLOWEDBOOK = 3;
	//CreateSession testHib = new CreateSession();
	
	
	public static String checkOutBook(BookLoansBean bookLoan)
	{
		Session session = CreateSession.getSession().openSession();
		String message = "success";
		BorrowerEntity borrower = (BorrowerEntity) session.get(BorrowerEntity.class,  Integer.parseInt(bookLoan.getbId()));
		if(borrower!=null)
		{
		Query queryStudent = session.createQuery("from BookLoansEntity where ( bId.cardId =:cardId ) and dateIn is null");
		queryStudent.setParameter("cardId", Integer.parseInt(bookLoan.getbId()));
		Integer countBooksDue = (Integer)queryStudent.list().size();
		if(countBooksDue < 3)
		{
			Query queryBook = session.createQuery("from BookEntity where  isbn =:isbn and available=true");
			queryBook.setParameter("isbn", bookLoan.getIsbn());
			Integer countBookAvailable =(Integer) queryBook.list().size();
			
			if(countBookAvailable > 0)
			{
				BookLoansEntity bookLoansEntity = new BookLoansEntity();
				
				
				  	
				    
				    bookLoansEntity.setDateout(Timestamp.valueOf(DateUtility.getCurrentDate()));
				    bookLoansEntity.setDueDate(Timestamp.valueOf(DateUtility.getDueDate()));
				    BorrowerEntity b= (BorrowerEntity) session.get(BorrowerEntity.class,Integer.parseInt( bookLoan.getbId()));
				    bookLoansEntity.setbId(b);
				    BookEntity book = (BookEntity) session.get(BookEntity.class, bookLoan.getIsbn());
				    book.setAvailable(false);
				    bookLoansEntity.setBook(book);
				    
				    
				    try{
				    	session.saveOrUpdate(bookLoansEntity);
						Transaction trans = session.beginTransaction();
						trans.commit();
						message = "Book successfully checked out.";
					}catch (Exception e) {
						
						
							message="failed";
					}finally{
					session.close();
					
					}
				    
				    
			}
			else 
			{
				message ="Book not available";
			}
		}
		else 
		{
			message ="Sorry !!! Maximum books already checked out.";
		}
		}
		else
		{
			message = "Card details not found";
		}
		return message;
	}
	
	
	public static List<BookLoansBean> getSelectedcheckInBooks(BookLoansBean bookLoansBean)
	{
		Session session = CreateSession.getSession().openSession();
		Query queryBookLoan = null;
		if(bookLoansBean.getbId()=="" && bookLoansBean.getIsbn()!="")
		{
			
			queryBookLoan = session.createQuery("from BookLoansEntity where ( (book.isbn) like  :isbn )  and dateIn is null");
			queryBookLoan.setParameter("isbn", "%"+(bookLoansBean.getIsbn())+"%");
			//queryBookLoan.setParameter("bName", ("%"+bookLoansBean.getName()+"%").toLowerCase());
		}
		  
		
		else if(bookLoansBean.getbId()=="" && bookLoansBean.getName()!="")
		{
			
			queryBookLoan = session.createQuery("from BookLoansEntity where ( lower(bId.bName) like :bName )  and dateIn is null");
			queryBookLoan.setParameter("bName", ("%"+bookLoansBean.getName()+"%").toLowerCase());
			//queryBookLoan.setParameter("bName", ("%"+bookLoansBean.getName()+"%").toLowerCase());
		}
		else if(bookLoansBean.getbId()!=""){
			System.out.println(bookLoansBean.getbId());
		 queryBookLoan = session.createQuery("from BookLoansEntity where (bId.cardId =:cardId )  and dateIn is null");
		//bId.cardId =:cardId or  lower(book.isbn) like :isbn or
		queryBookLoan.setParameter("cardId", Integer.parseInt(bookLoansBean.getbId()));
		//queryBookLoan.setParameter("isbn", (bookLoansBean.getIsbn()));
		//queryBookLoan.setParameter("bName", ("%"+bookLoansBean.getName()+"%").toLowerCase());
		}
		List<BookLoansEntity> entityList = null;
		List<BookLoansBean> beanList = new ArrayList<>();
		if(queryBookLoan!=null){
			entityList= queryBookLoan.list();
		
		
		if(entityList.size()>0){
		for(BookLoansEntity bean : entityList)
		{
			BookLoansBean b = new BookLoansBean();
			//BookEntity book = session.get(BookEntity.class, bean.g)
			b.setbId(String.valueOf(bean.getbId().getCardId()));
			b.setIsbn(bean.getBook().getIsbn());
			b.setName((bean.getbId().getbName()));
			b.setLoan_id(bean.getLoan_id());
			beanList.add(b);
		}
		}
		}
		return beanList;
	}
	
	
	public static String checkInBooks(List<BookLoansBean> lists)
	{
		String message ="";
		if(lists.size()>0)
		{
			
			
			for(BookLoansBean bean : lists)
			{
				Session session = CreateSession.getSession().openSession();
				BookLoansEntity loanEntity = new BookLoansEntity();
				loanEntity.setLoan_id(bean.getLoan_id());
				BorrowerEntity borrower = (BorrowerEntity) session.get(BorrowerEntity.class, Integer.parseInt(bean.getbId()));
				loanEntity.setbId(borrower);
				BookEntity book = (BookEntity) session.get(BookEntity.class, bean.getIsbn());
				book.setAvailable(true);
				loanEntity.setBook(book);
				loanEntity.setDateIn(Timestamp.valueOf(DateUtility.getCurrentDate()));
				
				try{
					session.update(loanEntity);
					Transaction trans = session.beginTransaction();
					trans.commit();
					message = "Books successfully checked in.";
				
				}catch (Exception e) {
					
						message="Sorry try later.";
				}
				finally{
					session.close();
				}
			}
				
				
				
				
		}
		return message;
	}
	
	/*public void addBookLoan(BookLoansBean bookLoan)
	{
		CreateSession testHib = new CreateSession();
		Session session = testHib.getSession().openSession();
		Query queryStudent = session.createQuery("from BorrowerEntity where  cardId =:cardId");
		queryStudent.setParameter("cardId", bookLoan.getbId());
		Integer countStudent = (Integer)queryStudent.list().size();
		System.out.println(countStudent);
		if(countStudent>0)
		{
		Query query = session.createQuery("from BookLoansEntity where dateIn = null and  bId.cardId =:cardId");
		query.setParameter("cardId", bookLoan.getbId());
		Integer count = (Integer)query.list().size();
		//count=3;
		int allowedBooks = MAX_ALLOWEDBOOK - count;
		if(count < 3 && allowedBooks >= bookLoan.getBook().size())
		{
			for(BookBean book : bookLoan.getBook())
			{
				
				Query queryBook = session.createQuery("from BookEntity where available = true and  isbn =:isbn");
				queryBook.setParameter("isbn", book.getIsbn());
				System.out.println("size :"+queryBook.list().size());
			}
			
		}
		else
			System.out.println("exceeded");
		
		System.out.println(count);
		}
		else {
			System.out.println("student not present");
		}
		session.close();
	}
	*/
	
	/*public static void main(String[] args)
	{
		System.out.println("here");
		BookLoanDAO test = new BookLoanDAO();
		BookLoansBean bookBean = new BookLoansBean();
		bookBean.setbId("11");
		BookBean b = new BookBean();
		b.setIsbn("0195153448");
		Set<BookBean> setBook = new HashSet<>();
		setBook.add(b);
		bookBean.setBook(setBook);
		test.addBookLoan(bookBean);
	}*/
}
