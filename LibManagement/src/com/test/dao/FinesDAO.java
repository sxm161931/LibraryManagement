package com.test.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.test.bean.FineBean;
import com.test.entity.BookEntity;
import com.test.entity.BookLoansEntity;
import com.test.entity.CreateSession;
import com.test.entity.FineEntity;
import com.test.utility.DateUtility;

public class FinesDAO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static float FINE_PER_DAY = 0.25f; 
	//CreateSession testHib = new CreateSession();
	
/*	public String updateFineDetails(FineBean fine)
	{
		String msg1="",msg2="",finalMsg="";
		
		if(fine.getSelectedValue().equalsIgnoreCase("individual"))
		{
			
			Session session = testHib.getSession().openSession();
			Query queryLoanResult = session.createQuery(" from BookLoansEntity where (bId.cardId =:cardId ) and ( dueDate < dateIn or dueDate < :today  )");
			queryLoanResult.setParameter("cardId", Integer.parseInt(fine.getBorrowerId()));
			queryLoanResult.setParameter("today", Timestamp.valueOf(DateUtility.getCurrentDate()));
			List<BookLoansEntity> loanList = new ArrayList<>();
			loanList = queryLoanResult.list();
			if(loanList.size()>0)
			{
				System.out.println("inside list");
				
				for(BookLoansEntity loan : loanList)
				{
					FineEntity fineEntity = null;
					fineEntity =(FineEntity) session.get(FineEntity.class, loan.getLoan_id());
					if(fineEntity != null)
					{
						System.out.println("in fine");
						//update
					}
					else
					{
						//insert
						System.out.println("not in fine");
						msg1 =insertFineEntityNotReturned(fine.getBorrowerId());
						msg2 =insertFineEntityReturned(fine.getBorrowerId());
						if(msg1.equals("success") && msg2.equals("success"))
						{
							finalMsg ="Fines updated successfully";
						}
					}
				}
			}
			else
			{
				//save
				finalMsg ="No books checked out";
				
			}
			session.close();
		}
		return finalMsg;
	}*/
	
	public static String insertFine(String borrowerId)
	{
		String message="failed";
		Session session = CreateSession.getSession().openSession();
		Query queryloan = session.createQuery("from BookLoansEntity where (bId.cardId like :cardId ) and ((dateIn is null and dueDate < :today) or (dateIn is not null and dueDate < dateIn)  )");
		if(borrowerId!=""){
			queryloan.setParameter("cardId", Integer.parseInt(borrowerId));
		}
		else
		{
			queryloan = session.createQuery("from BookLoansEntity where ((dateIn is null and dueDate < :today) or (dateIn is not null and dueDate < dateIn)  )");
			//queryloan.setParameter("cardId", "%");
		}
			
		queryloan.setParameter("today", Timestamp.valueOf(DateUtility.getCurrentDate()));
		List<BookLoansEntity> bookLoanList = queryloan.list();
		session.close();
		if(bookLoanList.size()>0){
		for(BookLoansEntity loan : bookLoanList)
		{
			System.out.println("id" +loan.getLoan_id());
			session = CreateSession.getSession().openSession();
			FineEntity fine = null;
			fine =	(FineEntity) session.get(FineEntity.class, loan.getLoan_id());
			long daysDiff =0;
			try{
			if(fine == null && loan.getLoan_id()>0)
			{
				System.out.println("new record");
				fine = new FineEntity();
				
				fine.setBookLoan(loan);
				fine.setLoanId(loan.getLoan_id());
				fine.setPaid(false);
				
				if(loan.getDateIn()==null && (loan.getDueDate().compareTo(Timestamp.valueOf(DateUtility.getCurrentDate())) == -1))
				{
					daysDiff = DateUtility.getDateDiff(DateUtility.getCurrentDate(), String.valueOf(loan.getDueDate())) ;
					if(daysDiff>0){
					fine.setFineAmt(String.format("%.2f", daysDiff*FINE_PER_DAY));
					session.save(fine);
					}
				}
				else if (loan.getDateIn()!=null && (loan.getDueDate().compareTo(loan.getDateIn()) == -1))
				{
					daysDiff = DateUtility.getDateDiff(String.valueOf(loan.getDateIn()), String.valueOf(loan.getDueDate())) ;
					if(daysDiff>0){
					fine.setFineAmt(String.format("%.2f", daysDiff*FINE_PER_DAY));
					session.save(fine);
					}
				}
				
			}
			else
			{
				System.out.println("existing record"+fine.getLoanId());
				if(!fine.isPaid())
				{
					if(loan.getDateIn()==null && (loan.getDueDate().compareTo(Timestamp.valueOf(DateUtility.getCurrentDate())) == -1))
					{
						daysDiff = DateUtility.getDateDiff(DateUtility.getCurrentDate(), String.valueOf(loan.getDueDate())) ;
					}
					else if (loan.getDateIn()!=null && (loan.getDueDate().compareTo(loan.getDateIn()) == -1))
					{
						daysDiff = DateUtility.getDateDiff(String.valueOf(loan.getDateIn()), String.valueOf(loan.getDueDate())) ;
					}
					fine.setFineAmt(String.format("%.2f", daysDiff*FINE_PER_DAY));
					session.saveOrUpdate(fine);
				}
			}
			
			//if()
			
			
				
				Transaction trans = session.beginTransaction();
				trans.commit();
				message = "Fines successfully updated.";
			
			}catch (Exception e) {
				System.out.println(e.getMessage());
					message="Sorry try later.";
			}
			finally{
				session.close();
			}
			
		}
		}
		else
		{
			message="No fines to update";
		}
		return message;
	}
	
	/*public String insertFineEntityReturned(String borrowerId)
	{
		System.out.println("insertFineEntityReturned");
		String message="";
		Session session = testHib.getSession().openSession();
		Query queryloan = session.createQuery("from BookLoansEntity where (bId.cardId like :bId ) and dateIn is null and dueDate < dateIn");
		if(borrowerId!="")
			queryloan.setParameter("cardId", Integer.parseInt(borrowerId));
		else
			queryloan.setParameter("cardId", "%");
		//queryloan.setParameter("today", Timestamp.valueOf(DateUtility.getCurrentDate()));
		List<BookLoansEntity> bookLoanList = queryloan.list();
		session.close();
		for(BookLoansEntity loan : bookLoanList)
		{
			session = testHib.getSession().openSession();
			FineEntity fine = new FineEntity();
			fine.setLoanId(loan.getLoan_id());
			fine.setPaid(false);
			long daysDiff = DateUtility.getDateDiff(String.valueOf(loan.getDateIn()), String.valueOf(loan.getDueDate())) ;
			fine.setFineAmt(String.format("%.2f", daysDiff*FINE_PER_DAY));
			
			try{
				session.saveOrUpdate(fine);
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
		return message;
	}*/
	
	public static List<FineBean> fetchFineDetails(String userId)
	{
		Session session = CreateSession.getSession().openSession();
		Query queryFines = session.createQuery("from BookLoansEntity where bId.cardId = :userId and fine.paid = false");
		queryFines.setParameter("userId", Integer.parseInt(userId));
		List<BookLoansEntity> fineList = queryFines.list();
		System.out.println(fineList.size());
		List<FineBean> fineBeanList = new ArrayList<>();
		if(fineList.size()>0)
		{
			for(BookLoansEntity fine : fineList)
			{
				FineBean bean = new FineBean();
				bean.setFineAmt(fine.getFine().getFineAmt());
				bean.setIsbn(fine.getBook().getIsbn());
				bean.setTitle(fine.getBook().getTitle());
				bean.setLoanId(String.valueOf(fine.getLoan_id()));
				bean.setCover(fine.getBook().getCover());
				
				
				/*Query queryBooks = session.createQuery("from BookEntity where isbn = :isbn");
				queryBooks.setParameter("isbn", fine.getBookLoan().getBook().getIsbn());*/
				
				BookEntity book = (BookEntity) session.get(BookEntity.class, fine.getBook().getIsbn());
				System.out.println(book.isAvailable());
				System.out.println(fine.getBook().getTitle());
				bean.setAvailable(fine.getBook().isAvailable());
				System.out.println(fine.getBook().isAvailable());
				
				fineBeanList.add(bean);
			}
		}
		session.close();
		System.out.println(fineBeanList.size());
		return fineBeanList;
		//return fineList;
	}
	
	public void payFine(List<String> loanIdList)
	{
		if(loanIdList.size() > 0)
		{
			
			for(String loanId : loanIdList)
			{
				Session session = CreateSession.getSession().openSession();
				BookLoansEntity bookLoansEntity = (BookLoansEntity) session.get(BookLoansEntity.class, Integer.parseInt(loanId));
				BookEntity book = bookLoansEntity.getBook();
				book.setAvailable(true);
				
				
			}
		}
	}
	
	
	 public static String makePayment(List<String> loanId)
	 {
		 String message="No value selected.";
		 for(String id : loanId){
		 Session session = CreateSession.getSession().openSession();
		 FineEntity fine = (FineEntity) session.get(FineEntity.class, Integer.parseInt(id));
		 fine.setPaid(true);
		 fine.setPaidDate(Timestamp.valueOf(DateUtility.getCurrentDate()));
		 try{
				session.saveOrUpdate(fine);
				Transaction trans = session.beginTransaction();
				trans.commit();
				message = "Payment received.";
				//SendFileEmail.sendPaymentMail(this.loanIdListSelected);
			
			}catch (Exception e) {
				System.out.println(e.getMessage());
					message="Sorry try later.";
			}
			finally{
				session.close();
			}
		 
		 }
		 
		 return message;
	 }

	 
	 public static List<FineBean> fetchFineHistoryDetails(String userId)
		{
		 System.out.println(userId);
			Session session = CreateSession.getSession().openSession();
			Query queryFines = null;
			if(userId!=""){
				
			
				queryFines =session.createQuery("select sum(f.fineAmt),b.bId.cardId from FineEntity as f,BookLoansEntity as b  where b.loan_id = f.loanId and f.paid = true and b.bId.cardId = :userId");
				queryFines.setParameter("userId", Integer.parseInt(userId));
			}
			else
			{
				queryFines =session.createQuery("select sum(f.fineAmt),b.bId.cardId from FineEntity as f,BookLoansEntity as b  where b.loan_id = f.loanId and f.paid = true group by b.bId.cardId");
			}
				
			List<Object[]> fineList = queryFines.list();
			List<FineBean> fineBeanList = new ArrayList<>();
			if(fineList.size()>0)
			{
				for(Object[] fine : fineList)
				{
				//	BookLoansEntity bookLoan = (BookLoansEntity) fine[1];
					System.out.println(fine[0]);
					System.out.println(fine[1]);
					FineBean bean = new FineBean();
					bean.setBorrowerId(String.valueOf(fine[1]));
					bean.setFineAmt((String) fine[0]);
					//System.out.println(bookLoan.getLoan_id());
					//System.out.println(bookLoan.getbId().getCardId());
					/*FineBean bean = new FineBean();
					bean.setFineAmt(fine.getFine().getFineAmt());
					bean.setIsbn(fine.getBook().getIsbn());
					bean.setTitle(fine.getBook().getTitle());
					bean.setLoanId(String.valueOf(fine.getLoan_id()));
					bean.setBorrowerId(String.valueOf(fine.getbId().getCardId()));
					System.out.println(fine.getFine().getPaidDate());
					bean.setPaidDate(DateUtility.getStringDate((fine.getFine().getPaidDate())));*/
					//bean.setCover(fine.getBook().getCover());
					
					/*Query queryBooks = session.createQuery("from BookEntity where isbn = :isbn");
					queryBooks.setParameter("isbn", fine.getBookLoan().getBook().getIsbn());*/
					
			/*		BookEntity book = (BookEntity) session.get(BookEntity.class, fine.getBook().getIsbn());
					System.out.println(book.isAvailable());
					System.out.println(fine.getBook().getTitle());
					bean.setAvailable(fine.getBook().isAvailable());
					System.out.println(fine.getBook().isAvailable());*/
					
					fineBeanList.add(bean);
				}
			}
			session.close();
			return fineBeanList;
		}
	 
	 	public static Map<String , Integer> fineChart()
	 	{
	 		Session session = CreateSession.getSession().openSession();
	 		SQLQuery queryFines = null;
			queryFines =session.createSQLQuery("select  count(Date_in), DATE_FORMAT(Date_in, '%Y-%m') as month, DATE_FORMAT(Date_in, '%Y') as testYr from book_loans group by DATE_FORMAT(Date_in, '%Y-%m') having testYr = DATE_FORMAT(now(), '%Y')  order by month desc");
	 		List<Object[]> fineDetails = queryFines.list();
	 		System.out.println(fineDetails.size());
	 		Map<String , Integer> result = new HashMap<>();
	 		for(Object[] obj : fineDetails)
	 		{
	 			Integer count = ((BigInteger)obj[0]).intValue();
	 			/*ChartBean chart = new ChartBean();
	 			chart.setYear((String)obj[1]);
	 			chart.setNumber(count);*/
	 			System.out.println((BigInteger)obj[0]);
	 			System.out.println((String)obj[1]);
	 			result.put((String)obj[1], count);
	 	}
	 		List<String> monthList = new ArrayList<>();
	 		Calendar cal = Calendar.getInstance();
	 		for(int i=0;i<6;i++)
	 		{
	 			if(i==0)
	 				cal.add(Calendar.MONTH, 0);
	 			else
	 		cal.add(Calendar.MONTH, -1);
	 		Date resultDate = cal.getTime();
	 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
	 		String month = dateFormat.format(resultDate);
	 		monthList.add(month);
	 		}
	 		/*for(String month : monthList)
	 		{
	 			if(!result.containsKey(month))
	 			{
	 				result.remove(month);
	 				result.put(month, 0);
	 			}
	 			
	 		}*/
	 		
	 		for (Iterator<String> iterator = monthList.iterator(); iterator.hasNext(); ) {
	 		    String month = iterator.next();
	 		   if(!result.containsKey(month))
	 			{
	 				result.remove(month);
	 				result.put(month, 0);
	 			}
	 		}
	 		session.close();
	 		return result;
	 	}
	 	
	 	public static Map<String , Integer> fineChartCheckOut()
	 	{
	 		Session session = CreateSession.getSession().openSession();
	 		SQLQuery queryFines = null;
			queryFines =session.createSQLQuery("select  count(Date_out), DATE_FORMAT(Date_out, '%Y-%m') as month, DATE_FORMAT(Date_out, '%Y') as testYr from book_loans group by DATE_FORMAT(Date_out, '%Y-%m') having testYr = DATE_FORMAT(now(), '%Y')  order by month desc");
	 		List<Object[]> fineDetails = queryFines.list();
	 		System.out.println(fineDetails.size());
	 		Map<String , Integer> result = new HashMap<>();
	 		for(Object[] obj : fineDetails)
	 		{
	 			Integer count = ((BigInteger)obj[0]).intValue();
	 			/*ChartBean chart = new ChartBean();
	 			chart.setYear((String)obj[1]);
	 			chart.setNumber(count);*/
	 			System.out.println((BigInteger)obj[0]);
	 			System.out.println((String)obj[1]);
	 			result.put((String)obj[1], count);
	 	}
	 		List<String> monthList = new ArrayList<>();
	 		Calendar cal = Calendar.getInstance();
	 		for(int i=0;i<6;i++)
	 		{
	 			if(i==0)
	 				cal.add(Calendar.MONTH, 0);
	 			else
	 		cal.add(Calendar.MONTH, -1);
	 		Date resultDate = cal.getTime();
	 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
	 		String month = dateFormat.format(resultDate);
	 		monthList.add(month);
	 		}
	 		
	 		
	 		for (Iterator<String> iterator = monthList.iterator(); iterator.hasNext(); ) {
	 		    String month = iterator.next();
	 		   if(!result.containsKey(month))
	 			{
	 				result.remove(month);
	 				result.put(month, 0);
	 			}
	 		}
	 		
	 		/*for(String month : monthList)
	 		{
	 			if(!result.containsKey(month))
	 			{
	 				result.remove(month);
	 				result.put(month, 0);
	 			}
	 			
	 		}*/
	 		
	 		session.close();
	 		return result;
	 	}
	 	
	 	public static Map<String , Integer> fineChartDue()
	 	{
	 		Session session = CreateSession.getSession().openSession();
	 		SQLQuery queryFines = null;
			queryFines =session.createSQLQuery("select  count(Due_date), DATE_FORMAT(Due_date, '%Y-%m') as month, DATE_FORMAT(Due_date, '%Y') as testYr from book_loans group by DATE_FORMAT(Due_date, '%Y-%m') having testYr = DATE_FORMAT(now(), '%Y')  order by month desc");
	 		List<Object[]> fineDetails = queryFines.list();
	 		System.out.println(fineDetails.size());
	 		Map<String , Integer> result = new HashMap<>();
	 		for(Object[] obj : fineDetails)
	 		{
	 			Integer count = ((BigInteger)obj[0]).intValue();
	 			/*ChartBean chart = new ChartBean();
	 			chart.setYear((String)obj[1]);
	 			chart.setNumber(count);*/
	 			System.out.println((BigInteger)obj[0]);
	 			System.out.println((String)obj[1]);
	 			result.put((String)obj[1], count);
	 	}
	 		List<String> monthList = new ArrayList<>();
	 		Calendar cal = Calendar.getInstance();
	 		for(int i=0;i<6;i++)
	 		{
	 			if(i==0)
	 				cal.add(Calendar.MONTH, 0);
	 			else
	 		cal.add(Calendar.MONTH, -1);
	 		Date resultDate = cal.getTime();
	 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
	 		String month = dateFormat.format(resultDate);
	 		monthList.add(month);
	 		}
	 		/*for(String month : monthList)
	 		{
	 			if(!result.containsKey(month))
	 			{
	 				//result.remove(month);
	 				result.put(month, 0);
	 			}
	 			
	 		}*/
	 		
	 		for (Iterator<String> iterator = monthList.iterator(); iterator.hasNext(); ) {
	 		    String month = iterator.next();
	 		   if(!result.containsKey(month))
	 			{
	 				result.remove(month);
	 				result.put(month, 0);
	 			}
	 		}
	 		
	 		/* for (Map.Entry<String, Integer> entry : result.entrySet()) {
	        	if(!monthList.contains(entry.getKey()))
	        			{
	        				result.remove(entry.getKey());
	        			}
	        }*/
	 		session.close();
	 		return result;
	 	}
	 	
	 	public static List<FineBean> fetchReceiptDetails(Set<String> idList)
	 	{
	 		Session session = CreateSession.getSession().openSession();
	 		List<FineBean> fineBean = new ArrayList<FineBean>();
	 		for(String id : idList)
	 		{
	 			
	 			BookLoansEntity book =(BookLoansEntity) session.get(BookLoansEntity.class, Integer.parseInt(id));
	 			FineBean bean = new FineBean();
	 			bean.setFineAmt(book.getFine().getFineAmt());
	 			bean.setCover(book.getBook().getCover());
	 			bean.setTitle(book.getBook().getTitle());
	 			bean.setIsbn(book.getBook().getIsbn());
	 			bean.setLoanId(String.valueOf(book.getLoan_id()));
	 			bean.setName(book.getbId().getbName());
	 			fineBean.add(bean);
	 			
	 		}
	 		session.close();
	 		return fineBean;
	 	}
}
