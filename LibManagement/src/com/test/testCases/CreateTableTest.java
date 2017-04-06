package com.test.testCases;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.test.entity.CreateSession;

public class CreateTableTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		SessionFactory s = CreateSession.getSession();
		Session session =s.openSession();
		Transaction trans = session.beginTransaction();
		
		/*Set<AuthorsEntity> authors = new HashSet<>();
		AuthorsEntity a1 = new AuthorsEntity();*/
	/*	a1.setAuthorId(1);
		a1.setName("a1");
		AuthorsEntity a2 = new AuthorsEntity();
		a2.setAuthorId(2);
		a2.setName("a2");
		authors.add(a2);
		authors.add(a1);
		BookEntity b1 = new BookEntity();
		b1.setIsbn("hjgjh");
		b1.setTitle("first");
		b1.setAuthors(authors);
		BookEntity b2 = new BookEntity();
		b2.setIsbn("hghg");
		b2.setTitle("sec");
		b2.setAuthors(authors);
		session.save(b1);
		session.save(b2);*/
		/*a1.setName("test yty");
		session.save(a1);*/
		try {
			//trans.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//trans.rollback();
			e.printStackTrace();
		}
		finally{
			session.close();
		}
	}

}
