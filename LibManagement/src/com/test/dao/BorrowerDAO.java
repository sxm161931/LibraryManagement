package com.test.dao;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.test.bean.BorrowerBean;
import com.test.entity.BorrowerEntity;
import com.test.entity.CreateSession;
import com.test.utility.SendFileEmail;

public class BorrowerDAO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

		BorrowerDAO b = new BorrowerDAO();
		b.addBorrower("641-17-6222");
	}
*/
	public static String addBorrower(BorrowerBean borrowerBean)
	{
		Session session =null ;
		String message="";
		if(session==null){
			session= CreateSession.getSession().openSession();
		}
		BorrowerEntity borrowerEntity = new BorrowerEntity();
		borrowerEntity.setAddress(borrowerBean.getAddress()+","+borrowerBean.getCity()+","+borrowerBean.getState());
		borrowerEntity.setbName(borrowerBean.getfName()+ " "+borrowerBean.getlName());
		borrowerEntity.setEmail(borrowerBean.getEmail());
		borrowerEntity.setPhone(borrowerBean.getPhone());
		borrowerEntity.setSsn(borrowerBean.getSsn());
		
		try{
			Integer id =(Integer) session.save(borrowerEntity);
			Transaction trans = session.beginTransaction();
			trans.commit();
			message = "Card created with Id : "+id;
			SendFileEmail.sendUserCreationMail(borrowerBean.getfName(),message);
		}
		
		catch (Exception e) {
			message="duplicate";
			
			
		}finally{
		session.close();
		
		}
		return message;
	}
}
