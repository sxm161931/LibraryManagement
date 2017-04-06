package com.test.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;
import org.primefaces.model.tagcloud.TagCloudModel;

@ManagedBean(name="tagCloud")
@SessionScoped
public class TagCloud implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TagCloudModel model;
	
	List<TagCloud> images;
    String image;
	
	
   

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<TagCloud> getImages() {
		System.out.println("here");
		return images;
	}

	public void setImages(List<TagCloud> images) {
		this.images = images;
	}

	

    public void init(ComponentSystemEvent event) {
        model = new DefaultTagCloudModel();
        model.addTag(new DefaultTagCloudItem("Check-In", 1));
        model.addTag(new DefaultTagCloudItem("Pay Fine", "#", 3));
        model.addTag(new DefaultTagCloudItem("Check-Out", 2));
        model.addTag(new DefaultTagCloudItem("Add User", "#", 5));
        model.addTag(new DefaultTagCloudItem("Update Fine", 4));
      //  model.addTag(new DefaultTagCloudItem("Add Book", "#", 2 ));
        
      
            images = new ArrayList<>();
            TagCloud tag = new TagCloud();
            tag.image="http://www.openisbn.com/cover/0821766929_72.jpg";
            images.add(tag);
            tag.image = "http://www.openisbn.com/cover/0399149422_72.jpg";
             images.add(tag);
             tag.image = "http://www.openisbn.com/cover/0821725998_72.jpg";
            images.add(tag);
           /* images.add("http://www.openisbn.com/cover/1577480112_72.jpg");
            images.add("http://www.openisbn.com/cover/0449213765_72.jpg");
            images.add("http://www.openisbn.com/cover/0786703229_72.jpg");
            images.add("http://www.openisbn.com/cover/0373708726_72.jpg");
            images.add("http://www.openisbn.com/cover/0373710356_72.jpg");
            images.add("http://www.openisbn.com/cover/0373710259_72.jpg");
            images.add("http://www.openisbn.com/cover/0446391360_72.jpg");
            images.add("http://www.openisbn.com/cover/0446373559_72.jpg");
            images.add("http://www.openisbn.com/cover/0671039415_72.jpg");
           */
            
        
    }

	public TagCloudModel getModel() {
		return model;
	}

	public void setModel(TagCloudModel model) {
		this.model = model;
	}

	 public void onSelect(SelectEvent event) {
		    TagCloudItem item = (TagCloudItem) event.getObject();
		    String label = item.getLabel().toLowerCase();
		  System.out.println(label);
		  if (label.equalsIgnoreCase("check-in")) {
		      label = "checkInBook";
		    } else if (label.equalsIgnoreCase("pay fine")) {
		      label = "viewFine";
		    } else if (label.equalsIgnoreCase("check-out")) {
		      label = "checkOutBook";
		    }
		    else if (label.equalsIgnoreCase("add user")) {
			      label = "addBorrower";
			    }
		    else if (label.equalsIgnoreCase("update fine")) {
			      label = "updateFine";
			    }
		  ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		   
		  try {
			  ec.redirect(ec.getRequestContextPath()+"/pages/" + label +".xhtml");
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
		  }
    
	 
	 
	   }
