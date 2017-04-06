package com.test.testCases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.test.entity.BorrowerEntity;
import com.test.entity.CreateSession;

public class StudentCSVReader {

	public static void main(String[] args) {

		String csvFile = "S:\\UTD Courses\\Spring17\\CS 6360\\Projects\\Proj1\\borrowers.csv";
		String line = "";
		String cvsSplitBy = ",";
		CreateSession test = new CreateSession();
		Session session=null;
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			SessionFactory sf = test.getSession();
			session = sf.openSession();
			
			br.readLine();
			while ((line = br.readLine()) != null) {
			//line = br.readLine();
				// use comma as separator
				String[] studentDetails = line.split(cvsSplitBy);
				//System.out.println(studentDetails.length);
				BorrowerEntity b = new BorrowerEntity();
				b.setSsn(studentDetails[1]);
				b.setAddress(studentDetails[5]+","+ studentDetails[6]+","+studentDetails[7]);
				b.setbName(studentDetails[2]+" "+ studentDetails[3]);
				b.setEmail(studentDetails[4]);
				b.setPhone(studentDetails[studentDetails.length-1]);
				//b.setCardId(cardId);
				/*b.setEmail(studentDetails[4]);
				b.setfName(studentDetails[2]);
				b.setlName(studentDetails[3]);
				b.setPhone(studentDetails[8]);
				b.setSsn(studentDetails[1]);
				b.setState(studentDetails[7]);
				b.setCity(studentDetails[6]);
				b.setAddress(studentDetails[5]);
				
				StringBuilder cardNoGen = new StringBuilder(10);
				cardNoGen.append(b.getfName().substring(0, 1));
				cardNoGen.append(b.getlName().substring(0, 1));
				int padding = 8 - b.getBorrowerId();*/
				
					try {
						session.save(b);
						Transaction trans = session.beginTransaction();
						trans.commit();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						// trans.rollback();
						e.printStackTrace();
					} 
				}

			}
		 catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			session.close();
		}
	}
}
