package com.test.bean;

import java.io.Serializable;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

import com.test.dao.FinesDAO;

@ManagedBean
@SessionScoped
public class ChartBean implements Serializable{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PieChartModel barModel;
	private PieChartModel checkOutModel;
	private PieChartModel dueModel;
	
	
	 
	 public PieChartModel getDueModel() {
		return dueModel;
	}

	public void setDueModel(PieChartModel dueModel) {
		this.dueModel = dueModel;
	}

	public PieChartModel getCheckOutModel() {
		return checkOutModel;
	}

	public void setCheckOutModel(PieChartModel checkOutModel) {
		this.checkOutModel = checkOutModel;
	}

	public PieChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(PieChartModel barModel) {
		this.barModel = barModel;
	}

	
	public void createBarModel(ComponentSystemEvent e) {
		
		System.out.println("inside create bar");
	        barModel = initBarModel();
	         
	        barModel.setTitle("Check-Ins Pie Chart");
	        barModel.setLegendPosition("w");
	        
	        checkOutModel = initCheckOutModel();
	        checkOutModel.setTitle("Check-Outs Pie Chart");
	        checkOutModel.setLegendPosition("w");
	        
	        dueModel = initDueDateModel();
	        dueModel.setTitle("Books Due Date Pie Chart");
	        dueModel.setLegendPosition("w");
	         
	        /*Axis xAxis = barModel.getAxis(AxisType.X);
	        xAxis.setLabel("Month");
	         
	        Axis yAxis = barModel.getAxis(AxisType.Y);
	        yAxis.setLabel("Check Outs");
	        yAxis.setMin(0);
	        yAxis.setMax(200);*/
	    }

	 private PieChartModel initBarModel() {
	        this.barModel = new PieChartModel();
	 
	        ChartSeries checkOuts = new ChartSeries();
	        checkOuts.setLabel("Check-Ins");
/*	        barModel.set("2004", 120);
	        barModel.set("2005", 100);
	        barModel.set("2006", 44);
	        barModel.set("2007", 150);
	        barModel.set("2008", 25);*/
	        
	       
	 
	       /* 
	        * sql query
	        * 
	        * select count(*), DATE_FORMAT(Date_out, "%Y-%m"), DATE_FORMAT(Date_out, "%Y") as testYr from book_loans
	        group by DATE_FORMAT(Date_out, "%Y-%m") having testYr = DATE_FORMAT(now(), "%Y");  
	        
	            	 
	         select count(Date_out), DATE_FORMAT(Date_out, "%Y-%m"), 
	         DATE_FORMAT(Date_out, "%Y") as testYr from book_loans group by DATE_FORMAT(Date_out, "%Y-%m") 
	         having testYr = DATE_FORMAT(now(), "%Y"); 
			
				select  count(Date_in), DATE_FORMAT(Date_out, "%Y-%m"), DATE_FORMAT(Date_out, "%Y") as testYr 
				from book_loans group by DATE_FORMAT(Date_out, "%Y-%m") 
				having testYr = DATE_FORMAT(now(), "%Y");   
				
					 
					 select  count(Date_in), DATE_FORMAT(Date_out, "%Y-%m") as month, DATE_FORMAT(Date_out, "%Y") as testYr from book_loans group by DATE_FORMAT(Date_out, "%Y-%m") 
					 having testYr = DATE_FORMAT(now(), "%Y")  order by month desc limit 6;
	            	 
	        model.addSeries(checkOuts);*/
	         
	        Map<String, Integer>  resultList = FinesDAO.fineChart();
	        
	        for (Map.Entry<String, Integer> entry : resultList.entrySet()) {
	        	 barModel.set(entry.getKey(), entry.getValue());	
	        }
	        
	        return barModel;
	    }
	 
	 private PieChartModel initCheckOutModel() {
	        this.checkOutModel = new PieChartModel();
	 
	        ChartSeries checkOuts = new ChartSeries();
	        checkOuts.setLabel("Check-Outs");

	         
	        Map<String, Integer>  resultList = FinesDAO.fineChartCheckOut();
	        
	        for (Map.Entry<String, Integer> entry : resultList.entrySet()) {
	        	checkOutModel.set(entry.getKey(), entry.getValue());	
	        }
	        
	        return checkOutModel;
	    }
	 
	 private PieChartModel initDueDateModel() {
	        this.dueModel = new PieChartModel();
	 
	        ChartSeries checkOuts = new ChartSeries();
	        checkOuts.setLabel("Due Books");

	         
	        Map<String, Integer>  resultList = FinesDAO.fineChartDue();
	        
	        for (Map.Entry<String, Integer> entry : resultList.entrySet()) {
	        	dueModel.set(entry.getKey(), entry.getValue());	
	        }
	        
	        return dueModel;
	    }
	 
	 public void itemSelect(ItemSelectEvent event) {
	       /* FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
	                        "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex());
	         */
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Item selected",
                    "Item Index: " + event.getItemIndex() + ", Series Index:" + event.getSeriesIndex()));
	    }
}
