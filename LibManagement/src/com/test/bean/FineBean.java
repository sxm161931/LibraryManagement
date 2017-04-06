package com.test.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.test.dao.FinesDAO;

@ManagedBean(name="fineBean")
@ViewScoped
public class FineBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	String fineAmt;
	boolean paid;
	String borrowerId;
	String selectedValue;
	String loanId;
	String isbn;
	String title;
	String cover;
	String totalFine;
	String paidDate;
	String name;
	boolean available;
	String styleClass="selectionEnabled";
	List<FineBean> beanList;
	Map<String,Boolean> checkMap = new HashMap<String,Boolean>();
	List<String> loanIdListSelected = new ArrayList<>();
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPaidDate() {
		return paidDate;
	}
	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}
	public String getFineAmt() {
		return fineAmt;
	}
	public void setFineAmt(String fineAmt) {
		this.fineAmt = fineAmt;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public String getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(String borrowerId) {
		this.borrowerId = borrowerId;
	}
	public String getSelectedValue() {
		return selectedValue;
	}
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
	
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public Map<String, Boolean> getCheckMap() {
		return checkMap;
	}
	public void setCheckMap(Map<String, Boolean> checkMap) {
		this.checkMap = checkMap;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public String getTotalFine() {
		return totalFine;
	}
	public void setTotalFine(String totalFine) {
		this.totalFine = totalFine;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public List<FineBean> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<FineBean> beanList) {
		this.beanList = beanList;
	}
	
	
	public List<String> getLoanIdListSelected() {
		return loanIdListSelected;
	}
	public void setLoanIdListSelected(List<String> loanIdListSelected) {
		this.loanIdListSelected = loanIdListSelected;
	}
	public void setFineDetails(ActionEvent e)
	{
		//System.out.println(this.getSelectedValue());
		if(this.selectedValue.equalsIgnoreCase("all"))
		{
			this.borrowerId = "";
		}
		
		String msg =FinesDAO.insertFine(this.borrowerId);
		addMessage(msg);
		this.borrowerId = "";
		//return "/pages/updateFine.xhtml";
	}

	public void addMessage(String btn) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(btn));
    }
	
	public String fetchUserFineDetails()
	{
		this.beanList=FinesDAO.fetchFineDetails(this.borrowerId);
		if(this.beanList.size()<=0)
		{
		System.out.println("here");
		addMessage("No books due.");
		this.borrowerId="";
		return "viewFine";
		}
		else
		{
			float sum =0;
			for(FineBean bean : this.beanList){
				sum += Float.parseFloat(bean.getFineAmt());
				System.out.println(bean.getStyleClass());
			}
			System.out.println(sum);
			this.setTotalFine(String.valueOf(sum));
			return "payFine";
		}
	}
	
	public String payFine()
	{
		String msg ="";
		if(checkMap.size()>0){
			System.out.println("here");
			
		for (Entry<String,Boolean> entry : checkMap.entrySet()) {
			if (entry.getValue()) {
				System.out.println(entry.getKey());
				this.loanIdListSelected.add(entry.getKey());
				
				
			}
		}
		msg= FinesDAO.makePayment(this.loanIdListSelected);
		if(msg.equalsIgnoreCase("Payment received."))
		{
			this.beanList=null;
			this.beanList = FinesDAO.fetchReceiptDetails(new HashSet<>(this.loanIdListSelected));
			float sum =0;
			for(FineBean bean : this.beanList)
			{
					
					this.name = bean.name;
					sum += Float.parseFloat(bean.getFineAmt());
					System.out.println(bean.getStyleClass());
				
			}
			this.setTotalFine(String.valueOf(sum));
			return "paymentReceipt";
		}
		else
			return "";
		
		}
		else{
			if(beanList.size()>0)
			msg = "Please return the book to proceed with payment";
			else
				msg ="No selection made";
		}
		
		addMessage(msg);
		
		/*else
		{
			addMessage("No data entered.");
		}*/
		checkMap = new HashMap<String,Boolean>();
		return "";
	}
	
	public String getFineHistory()
	{
		
		/*this.beanList = FinesDAO.fetchFineHistoryDetails(this.borrowerId);
		if(this.beanList.size()>0)
		{
			float sum =0;
			for(FineBean bean : this.beanList){
				sum += Float.parseFloat(bean.getFineAmt());
				System.out.println(bean.getStyleClass());
			}
			System.out.println(sum);
			this.setTotalFine(String.valueOf(sum));
			return "resultFineHistory";
		}
			
		else
		{
			addMessage("No fines available !!!");
			return "";
		}*/
			
		System.out.println(this.selectedValue);
		//System.out.println(this.borrowerId);
		if(this.selectedValue.equalsIgnoreCase("all"))
		{
			this.borrowerId = "";
		}
		
		this.beanList = FinesDAO.fetchFineHistoryDetails(this.borrowerId);
		if(this.beanList.size()>0)
		{
			
			return "resultFineHistory";
		}
			
		else
		{
			addMessage("No fines available !!!");
			return "";
		}
	}
	
	
}
