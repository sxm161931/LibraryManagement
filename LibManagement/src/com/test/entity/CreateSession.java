package com.test.entity;



import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class CreateSession {

	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	    Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry  serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        //new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).
        SessionFactory  sessionFactory = configuration.buildSessionFactory((org.hibernate.service.ServiceRegistry) serviceRegistry);
		Session session = sessionFactory.openSession();
		// Session ss=sf.openSession();
		System.out.println(session.isOpen());
		Department d =(Department) session.get(Department.class, 1);
		System.out.println(d.dname);
		Criteria criteria =session.createCriteria(Department.class).add(Restrictions.eq("dname", "Administration"));
		Department d2=(Department) criteria.list().get(0);
		System.out.println(d2.mgrSSN);
		GetDeptDetails getDetails = new GetDeptDetails();
		List<Department> list=getDetails.getDeptDetails();
		System.out.println(list.size());
		session.close();

	}
	*/
	public static SessionFactory getSession()
	{
		 Configuration configuration = new Configuration();
	        configuration.configure();
	        ServiceRegistry  serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
	                configuration.getProperties()).build();
	        //new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).
	        SessionFactory  sessionFactory = configuration.buildSessionFactory((org.hibernate.service.ServiceRegistry) serviceRegistry);
			
			return sessionFactory;
	}

}
