package com.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.test.entity.CreateSession;
import com.test.entity.Department;

public class GetDeptDetails {
	
	public void GetDeptDetails()
	{
		
	}
	
	public List<Department> getDeptDetails()
	{
		CreateSession testHib = new CreateSession();
	        
			Session session = testHib.getSession().openSession();
		//Session session = sf.openSession();
		
		System.out.println("*************************");
		System.out.println(session.isConnected());
		List<Department> deptList = new ArrayList<Department>();
		Department d = new Department();
		d.setDeptNo(1);
		d.setDname("one");
		d.setMgrSSN("sdsds");
		d.setMrgStartDt("sd");
		deptList.add(d);
		System.out.println(session.createCriteria(Department.class).list());
	/*	List list = session.createCriteria(Department.class).list();
		for(Object l : list)
		{
			deptList.add((Department) l);
		}*/
		deptList= session.createCriteria(Department.class).list();
		//deptList.add((Department) list);
		System.out.println(deptList.size());
		//session.close();
		//Criteria criteria =session.createCriteria(Department.class).add(Restrictions.eq("dname", "Administration"));
		return deptList;
	}

}
