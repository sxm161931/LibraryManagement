package com.test.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.test.entity.AuthorsEntity;
import com.test.entity.BookEntity;
import com.test.entity.CreateSession;

@ManagedBean
@SessionScoped
public class BooksUtilityBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UploadedFile file;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
	private static final File LOCATION = new File("S:\\File");

	public void fileUploadListener(FileUploadEvent e) {
		// Get uploaded file from the FileUploadEvent
		this.file = e.getFile();
		// Print out the information of the file
		System.out.println("Uploaded File Name Is :: "+file.getFileName()+" :: Uploaded File Size :: "+file.getSize());
		 String prefix = FilenameUtils.getBaseName(file.getFileName()); 
		 System.out.println("prefix"+prefix);
	        String suffix = FilenameUtils.getExtension(file.getFileName());
	        System.out.println("suffix"+suffix);
	        File save=null;
			try {
				save = File.createTempFile(prefix + "-", "." + suffix, LOCATION);
				 System.out.println(save.getAbsolutePath());
				Files.write(save.toPath(), file.getContents());
				if(save!=null){
					BufferedReader br = new BufferedReader(new FileReader(save.getAbsolutePath()));
					SessionFactory sf = CreateSession.getSession();
					br.readLine();
					String line = "";
					String cvsSplitBy = "\\t";
					while ((line = br.readLine()) != null) {
						Session	session = sf.openSession();
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
								
							} catch (Exception exp) {
								// TODO Auto-generated catch block
								// trans.rollback();
								exp.getMessage();
								 addMessage("Operation Failed");
							} 
							finally {
								session.close();
							}
						}

					}
					 addMessage("Books added successfully");
				}
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	       
	        
	
	}
	
	public void addMessage(String btn) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(btn));
    }
	
	
}
