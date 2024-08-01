/**
 * 
 */
package com.banks.web.sa.dashboard.controller;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.DonutChartModel;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.banks.erp.library.util.dto.DropdownDTO;
import com.banks.erp.library.util.util.CommonUtilService;
import com.banks.erp.sa.dashboard.dto.GoogleMapDTO;
import com.banks.erp.sa.dashboard.dto.LoanProduct;
import com.banks.erp.sa.dashboard.dto.TmrAttendance;
import com.banks.erp.sa.dashboard.dto.TmrAttendanceClusterWise;
import com.banks.erp.sa.dashboard.dto.TmrAttendanceRegionWise;
import com.banks.erp.sa.dashboard.dto.UniqueUddoktaVisitCount;
import com.banks.erp.sa.dashboard.dto.VisitCount;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


/**
 * @author Rajib Kumer Ghosh
 *
 */

@SuppressWarnings("serial")
@Named
@ViewScoped
public class DashboardView implements Serializable {
	

	
	@Inject
	private CommonUtilService commonUtilService; 	
	
	private DashboardModel model;
	
	private PieChartModel pieModel1;
	private PieChartModel pieModel2;
	private PieChartModel pieModel3;
	private PieChartModel pieModel4;
	private PieChartModel pieModel5;
	
	private MapModel mapModel1;
	private Marker marker;
	
	private LineChartModel areaModel;
	private LineChartModel lineModel1;
	private LineChartModel zoomModel;
	private BarChartModel barModel;
	private BarChartModel barModel1;
	private BarChartModel barModel2;
	private BarChartModel barModel3;
	private BarChartModel barModel4;
	private BarChartModel mixedModel;
	private CartesianChartModel combinedModel;
	private DonutChartModel donutModel;
	private List<LoanProduct> loanProductList;
	private List<VisitCount> visitCountList;
	private List<UniqueUddoktaVisitCount> uniqueUddoktaVisitCountList;
	private List<TmrAttendance> tmrAttendanceList;
	private List<TmrAttendanceRegionWise> tmrAttendanceRegionWiseList;
	private List<TmrAttendanceClusterWise> tmrAttendanceClusterWiseList;
	private List<String> images;

	public DashboardModel getModel() {
        return model;
    }
	
	public PieChartModel getPieModel1() {
		return pieModel1;
	}

	public void setPieModel1(PieChartModel pieModel1) {
		this.pieModel1 = pieModel1;
	}

	public PieChartModel getPieModel2() {
		return pieModel2;
	}

	public void setPieModel2(PieChartModel pieModel2) {
		this.pieModel2 = pieModel2;
	}	
    
    public PieChartModel getPieModel3() {
		return pieModel3;
	}

	public void setPieModel3(PieChartModel pieModel3) {
		this.pieModel3 = pieModel3;
	}
	
	public PieChartModel getPieModel4() {
		return pieModel4;
	}

	public void setPieModel4(PieChartModel pieModel4) {
		this.pieModel4 = pieModel4;
	}

	public PieChartModel getPieModel5() {
		return pieModel5;
	}

	public void setPieModel5(PieChartModel pieModel5) {
		this.pieModel5 = pieModel5;
	}

	public MapModel getMapModel1() {
		return mapModel1;
	}

	public void setMapModel1(MapModel mapModel1) {
		this.mapModel1 = mapModel1;
	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public LineChartModel getAreaModel() {
		return areaModel;
	}

	public void setAreaModel(LineChartModel areaModel) {
		this.areaModel = areaModel;
	}	

	public LineChartModel getLineModel1() {
		return lineModel1;
	}

	public void setLineModel1(LineChartModel lineModel1) {
		this.lineModel1 = lineModel1;
	}

	public LineChartModel getZoomModel() {
		return zoomModel;
	}

	public void setZoomModel(LineChartModel zoomModel) {
		this.zoomModel = zoomModel;
	}	

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public BarChartModel getBarModel1() {
		return barModel1;
	}

	public void setBarModel1(BarChartModel barModel1) {
		this.barModel1 = barModel1;
	}

	public BarChartModel getBarModel2() {
		return barModel2;
	}

	public void setBarModel2(BarChartModel barModel2) {
		this.barModel2 = barModel2;
	}

	public BarChartModel getBarModel3() {
		return barModel3;
	}

	public void setBarModel3(BarChartModel barModel3) {
		this.barModel3 = barModel3;
	}

	public BarChartModel getBarModel4() {
		return barModel4;
	}

	public void setBarModel4(BarChartModel barModel4) {
		this.barModel4 = barModel4;
	}

	public BarChartModel getMixedModel() {
		return mixedModel;
	}

	public void setMixedModel(BarChartModel mixedModel) {
		this.mixedModel = mixedModel;
	}

	public CartesianChartModel getCombinedModel() {
		return combinedModel;
	}

	public void setCombinedModel(CartesianChartModel combinedModel) {
		this.combinedModel = combinedModel;
	}

	public DonutChartModel getDonutModel() {
		return donutModel;
	}

	public void setDonutModel(DonutChartModel donutModel) {
		this.donutModel = donutModel;
	}

	public List<LoanProduct> getLoanProductList() {
		return loanProductList;
	}

	public void setLoanProductList(List<LoanProduct> loanProductList) {
		this.loanProductList = loanProductList;
	}
	
	public List<VisitCount> getVisitCountList() {
		return visitCountList;
	}

	public void setVisitCountList(List<VisitCount> visitCountList) {
		this.visitCountList = visitCountList;
	}

	public List<UniqueUddoktaVisitCount> getUniqueUddoktaVisitCountList() {
		return uniqueUddoktaVisitCountList;
	}

	public void setUniqueUddoktaVisitCountList(List<UniqueUddoktaVisitCount> uniqueUddoktaVisitCountList) {
		this.uniqueUddoktaVisitCountList = uniqueUddoktaVisitCountList;
	}

	public List<TmrAttendance> getTmrAttendanceList() {
		return tmrAttendanceList;
	}

	public void setTmrAttendanceList(List<TmrAttendance> tmrAttendanceList) {
		this.tmrAttendanceList = tmrAttendanceList;
	}

	public List<TmrAttendanceRegionWise> getTmrAttendanceRegionWiseList() {
		return tmrAttendanceRegionWiseList;
	}

	public void setTmrAttendanceRegionWiseList(List<TmrAttendanceRegionWise> tmrAttendanceRegionWiseList) {
		this.tmrAttendanceRegionWiseList = tmrAttendanceRegionWiseList;
	}

	public List<TmrAttendanceClusterWise> getTmrAttendanceClusterWiseList() {
		return tmrAttendanceClusterWiseList;
	}

	public void setTmrAttendanceClusterWiseList(List<TmrAttendanceClusterWise> tmrAttendanceClusterWiseList) {
		this.tmrAttendanceClusterWiseList = tmrAttendanceClusterWiseList;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	
	@PostConstruct
    public void init() {
        
        /*For Grid Table and Bar Chart*/
        getVisitCountsList();
        getUniqueUddoktaVisitCountsList();
        getTmrAttendancesList();
        getTmrAttendancesRegionWiseList();
        getTmrAttendancesClusterWiseList();
        
        /*For Button with Dialog*/
        getLoanProductsList();
        
        /*For Flow Images*/
        getFlowImages();
        
        model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();
         
        column1.addWidget("totalCustomer");
        column1.addWidget("totalLoan");
         
        column2.addWidget("totalDeposit");
        column2.addWidget("totalLoanOutstanding");
         
        column3.addWidget("totalDepositOutstanding");
 
        model.addColumn(column1);
        model.addColumn(column2);
        model.addColumn(column3);
        
        /*For Pie Chart*/
        createPieModel1();
        createPieModel2();
        createPieModel3();
        createPieModel4();
        createPieModel5();
        
        /*For GMap Model*/
        createMapModel1();
        
        /*For Area Chart Model*/
        createAreaModel();
        
        /*For Line Chart Line Model*/
        createLineModels();
        
        /*For Bar Chart Model*/
        createBarModel();
        createBarModel1();
        createBarModel2();
        createBarModel3();
        createBarModel4();
        
        /*For Mixed Bar & Line Chart Model Using Chartjs*/
        createMixedModel();
        
        /*For Mixed Bar & Line Chart Model Using Chart*/
        createCombinedModel();
        
        /*For Donut Chart Model*/
        createDonutModel();
    }
     
    public void handleReorder(DashboardReorderEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary("Reordered: " + event.getWidgetId());
        message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());
         
        addMessage(message);
    }
     
    public void handleClose(CloseEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Panel Closed", "Closed panel id:'" + event.getComponent().getId() + "'");
         
        addMessage(message);
    }
     
    public void handleToggle(ToggleEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, event.getComponent().getId() + " toggled", "Status:" + event.getVisibility().name());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    
    

    //******************************* Production Get Data List *******************************
    
    /* Table Grid(VisitCountsList) Start Here */
    private void getVisitCountsList() {
    	visitCountList = new ArrayList<VisitCount>();
    	try {
			//visitCountList.add(new VisitCount(194448, "6%", 12304, 356037, 148342, 176276, 188356));         	
    		visitCountList = commonUtilService.getVisitCountsList(VisitCount.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /* Table Grid(VisitCountsList) End Here */
    
    /* Table Grid(UniqueUddoktaVisitCountsList) Start Here */
    private void getUniqueUddoktaVisitCountsList() {
    	uniqueUddoktaVisitCountList = new ArrayList<UniqueUddoktaVisitCount>();
    	try {	
    		uniqueUddoktaVisitCountList = commonUtilService.getUniqueUddoktaVisitCountsLast7DaysList(UniqueUddoktaVisitCount.class);
		} catch (Exception e) {
			e.printStackTrace();
		}    	
    }
    /* Table Grid(UniqueUddoktaVisitCountsList) End Here */
    
    /* Table Grid(TmrAttendanceList) Start Here */
    private void getTmrAttendancesList() {
    	tmrAttendanceList = new ArrayList<TmrAttendance>();
    	try {	
    		tmrAttendanceList = commonUtilService.getTmrAttendanceLast7DaysList(TmrAttendance.class);
		} catch (Exception e) {
			e.printStackTrace();
		}   	
    }
    /* Table Grid(TmrAttendanceList) End Here */
    
    /* Table Grid(TmrAttendanceList) Start Here */
    private void getTmrAttendancesRegionWiseList() { 
    	tmrAttendanceRegionWiseList = new ArrayList<TmrAttendanceRegionWise>();
    	Integer roleID = 5;
    	String userStatus = "active";
    	try {	
    		tmrAttendanceRegionWiseList = commonUtilService.getTmrAttendancesRegionWiseList(TmrAttendanceRegionWise.class, roleID, userStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}   	
    }
    /* Table Grid(TmrAttendanceList) End Here */
    
    /* Table Grid(TmrAttendanceList) Start Here */
    private void getTmrAttendancesClusterWiseList() {
    	tmrAttendanceClusterWiseList = new ArrayList<TmrAttendanceClusterWise>();
    	Integer roleID = 5;
    	String userStatus = "active";
    	try {	
    		tmrAttendanceClusterWiseList = commonUtilService.getTmrAttendancesClusterWiseList(TmrAttendanceClusterWise.class, roleID, userStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}   	
    }
    /* Table Grid(TmrAttendanceList) End Here */
    
    
    //******************************* Production Graphs and Grids *******************************      
    
    /*Bar Chart Start Here*/
    /*Bar Chart-1 Model(uniqueUddoktaVisitedLast7Days) Start Here*/
    private BarChartModel initBarModel1() {
        BarChartModel model = new BarChartModel();
        
        //uniqueUddoktaVisitCountList = getUniqueUddoktaVisitCountsList();
 
        ChartSeries isl = new ChartSeries();
        isl.setLabel("Unique Uddokta Visited");
        
        for (UniqueUddoktaVisitCount uniqueUddoktaVisitCount : uniqueUddoktaVisitCountList) {
        	//isl.set(uniqueUddoktaVisitCount.getCreateDate(), uniqueUddoktaVisitCount.getUniqueUddoktaVisitedLast7Days());
        	if (uniqueUddoktaVisitCount.getUniqueUddoktaVisitedLast7Days() <= 250) {        	
        		isl.set(uniqueUddoktaVisitCount.getCreateDate(), 250);
        	} else {
        		isl.set(uniqueUddoktaVisitCount.getCreateDate(), uniqueUddoktaVisitCount.getUniqueUddoktaVisitedLast7Days());
        	}        	
        }
        
    	model.addSeries(isl);
 
        return model;
    }
    
    private void createBarModel1() {
    	barModel1 = initBarModel1();
    	 
        barModel1.setTitle("Unique Uddokta Visited in Last 7 Days");
        barModel1.setAnimate(true);
        barModel1.setLegendPosition("ne");
        barModel1.setBarWidth(30); 
 
        Axis xAxis = barModel1.getAxis(AxisType.X);
        xAxis.setLabel("<---- Date ---->");
 
        Axis yAxis = barModel1.getAxis(AxisType.Y);
        yAxis.setLabel("<----Count---->");
        yAxis.setMin(0);
        yAxis.setMax(15000);
    }
    /*Bar Chart-1 Model END Here*/
    
    /*Bar Chart-2 Model(tmrAttendanceLast7Days) Start Here*/
    private BarChartModel initBarModel2() {
        BarChartModel model = new BarChartModel();
        
        //tmrAttendanceList = getTmrAttendancesList();
 
        ChartSeries isl = new ChartSeries();
        isl.setLabel("TMR Attendance");
        
        for (TmrAttendance tmrAttendance : tmrAttendanceList) {
        	if (tmrAttendance.getTmrAttendanceLast7Days() <= 20) {
        		isl.set(tmrAttendance.getCreateDate(), 20);
        	} else {
        		isl.set(tmrAttendance.getCreateDate(), tmrAttendance.getTmrAttendanceLast7Days());
        	}        	
        }
        
        model.addSeries(isl);
 
        return model;
    }
    
    private void createBarModel2() {
    	barModel2 = initBarModel2();
    	 
        barModel2.setTitle("TMR Attendance in Last 7 Days");
        barModel2.setAnimate(true);
        barModel2.setLegendPosition("ne");
        barModel2.setBarWidth(30); 
 
        Axis xAxis = barModel2.getAxis(AxisType.X);
        xAxis.setLabel("<---- Date ---->");
 
        Axis yAxis = barModel2.getAxis(AxisType.Y);
        yAxis.setLabel("<----Count---->");
        yAxis.setMin(0);
        yAxis.setMax(1000);
    }
    /*Bar Chart-2 Model END Here*/
    
    /*Bar Chart-3 Model(Region Wise) Start Here*/
    private BarChartModel initBarModel3() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries isl = new ChartSeries();
        isl.setLabel("Total TMR");    
        for (TmrAttendanceRegionWise tmrAttendanceRegionWise : tmrAttendanceRegionWiseList) {
        	if (tmrAttendanceRegionWise.getTmrTotal() <= 5) {
        		isl.set(tmrAttendanceRegionWise.getGroupName(), 5);
        	} else {
        		isl.set(tmrAttendanceRegionWise.getGroupName(), tmrAttendanceRegionWise.getTmrTotal());
        	}        	
        }
 
        ChartSeries nisl = new ChartSeries();
        nisl.setLabel("Presence TMR");  
        for (TmrAttendanceRegionWise tmrAttendanceRegionWise : tmrAttendanceRegionWiseList) {
        	if (tmrAttendanceRegionWise.getTmrPresence() <= 5) {
        		nisl.set(tmrAttendanceRegionWise.getGroupName(), 5);
        	} else {
        		nisl.set(tmrAttendanceRegionWise.getGroupName(), tmrAttendanceRegionWise.getTmrPresence());
        	}        	
        }
 
        model.addSeries(isl);
        model.addSeries(nisl);
 
        return model;
    }
    
    private void createBarModel3() {
    	barModel3 = initBarModel3();
    	 
        barModel3.setTitle("Region Wise TMR Presence");
        barModel3.setAnimate(true);
        barModel3.setLegendPosition("ne");
        barModel3.setBarWidth(10); 
 
        Axis xAxis = barModel3.getAxis(AxisType.X);
        xAxis.setLabel("<---- Region ---->");
 
        Axis yAxis = barModel3.getAxis(AxisType.Y);
        yAxis.setLabel("<----Count---->");
        yAxis.setMin(0);
        yAxis.setMax(130);
    }
    /*Bar Chart-3 Model END Here*/
    
    /*Bar Chart-4 Model(Cluster Wise) Start Here*/
    private BarChartModel initBarModel4() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries isl = new ChartSeries();
        isl.setLabel("Total TMR");  
        for (TmrAttendanceClusterWise tmrAttendanceClusterWise : tmrAttendanceClusterWiseList) {
        	if (tmrAttendanceClusterWise.getTmrTotal() <= 20) {
        		isl.set(tmrAttendanceClusterWise.getGroupName(), 20);
        	} else {
        		isl.set(tmrAttendanceClusterWise.getGroupName(), tmrAttendanceClusterWise.getTmrTotal());
        	}        	
        }
 
        ChartSeries nisl = new ChartSeries();
        nisl.setLabel("Presence TMR");    
        for (TmrAttendanceClusterWise tmrAttendanceClusterWise : tmrAttendanceClusterWiseList) {
        	if (tmrAttendanceClusterWise.getTmrPresence() <= 20) {
        		nisl.set(tmrAttendanceClusterWise.getGroupName(), 20);
        	} else {
        		nisl.set(tmrAttendanceClusterWise.getGroupName(), tmrAttendanceClusterWise.getTmrPresence());
        	}        	
        }
 
        model.addSeries(isl);
        model.addSeries(nisl);
 
        return model;
    }
    
    private void createBarModel4() {
    	barModel4 = initBarModel4();
    	 
        barModel4.setTitle("Cluster Wise TMR Presence");
        barModel4.setAnimate(true);
        barModel4.setLegendPosition("ne");
        barModel4.setBarWidth(10); 
 
        Axis xAxis = barModel4.getAxis(AxisType.X);
        xAxis.setLabel("<---- Cluster ---->");
 
        Axis yAxis = barModel4.getAxis(AxisType.Y);
        yAxis.setLabel("<---- Count ---->");
        yAxis.setMin(0);
        yAxis.setMax(400);
    }
    /*Bar Chart-4 Model END Here*/
    /*Bar Chart END Here*/  
    
    
    /*Pie Chart Start Here*/        
    /*Pie Chart - 1 Start Here For Role 5, 20, 3 (Local DB Test 5,9,14)*/
    private void createPieModel1() {
    	String nativeQuery = "SELECT COUNT(*) AS countValue FROM PRISM_USERS WHERE U_STATUS = 'active' AND U_ROLE = 5 "
    			+ "UNION ALL "
    			+ "SELECT COUNT(*) AS countValue FROM PRISM_USERS WHERE U_STATUS = 'active' AND U_ROLE = 20 "
    			+ "UNION ALL "
    			+ "SELECT COUNT(*) AS countValue FROM PRISM_USERS WHERE U_STATUS = 'active' AND U_ROLE = 3 ";
    	String key = "countValue";
		List<Number> userCountList = commonUtilService.getNumberValueList(nativeQuery, key);
        
		pieModel1 = new PieChartModel();
        ChartData data = new ChartData();
         
        PieChartDataSet dataSet = new PieChartDataSet();
        dataSet.setData(userCountList);
         
        List<String> bgColors = new ArrayList<>();
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);
         
        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Total TMR");
        labels.add("Total TMS");
        labels.add("Total TO-TM-PRO");
        data.setLabels(labels);
        
        //pieModel1.setShowDataLabels(true); //TODO need alternative for this
        pieModel1.setData(data);
    }
    
    /*Pie Chart - 2 Start Here For Role (1,-1,2,3,10,11,12,13), 4, (5,15,16,17,18,19,20)*/
    private void createPieModel2() {
    	String nativeQuery = "SELECT COUNT(*) AS countValue FROM PRISM_USERS WHERE U_STATUS = 'active' AND U_ROLE IN(1,-1,2,3,10,11,12,13) "
    			+ "UNION ALL "
    			+ "SELECT COUNT(*) AS countValue FROM PRISM_USERS WHERE U_STATUS = 'active' AND U_ROLE = 4 "
    			+ "UNION ALL "
    			+ "SELECT COUNT(*) AS countValue FROM PRISM_USERS WHERE U_STATUS = 'active' AND U_ROLE IN(5,15,16,17,18,19,20) ";
    	String key = "countValue";
		List<Number> userTransactionAmountList = commonUtilService.getNumberValueList(nativeQuery, key);
        
        pieModel2 = new PieChartModel();
        ChartData data = new ChartData();
         
        PieChartDataSet dataSet = new PieChartDataSet();
        dataSet.setData(userTransactionAmountList);
         
        List<String> bgColors = new ArrayList<>();
        /*bgColors.add("#5dade2");
        bgColors.add("#FF5733");
        bgColors.add("#a569bd");*/
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);
         
        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Total Others Employee");
        labels.add("Total DH");
        labels.add("Total DH Employee");
        data.setLabels(labels);
         
        pieModel2.setData(data);
    }
    
    /*Pie Chart - 3 Start Here For Total Uddokta, Current month total visit, Current month unique Uddokta visit ---- TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -0)*/
    private void createPieModel3() {
    	String nativeQuery = "SELECT COUNT(*) AS countValue FROM PRISM_UDDOKTAS "
    			+ "UNION ALL "
    			+ "SELECT COUNT(*) AS countValue FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -0), 'DD-MON-YY') AND SYSDATE "
    			+ "UNION ALL "
    			+ "SELECT COUNT(DISTINCT fk_uddokta_id) AS countValue FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -0), 'DD-MON-YY') AND SYSDATE ";
    	String key = "countValue";
		List<Number> disbursementAmountList = commonUtilService.getNumberValueList(nativeQuery, key);
        pieModel3 = new PieChartModel();
        ChartData data = new ChartData();
         
        PieChartDataSet dataSet = new PieChartDataSet();
        dataSet.setData(disbursementAmountList);
         
        List<String> bgColors = new ArrayList<>();
        /*bgColors.add("#5dade2");
        bgColors.add("#FF5733");
        bgColors.add("#a569bd");*/
        bgColors.add("rgb(255, 99, 132)");
        bgColors.add("rgb(54, 162, 235)");
        bgColors.add("rgb(255, 205, 86)");
        dataSet.setBackgroundColor(bgColors);
         
        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Total Uddokta");
        labels.add("Total Uddokta Visit(MTD)");
        labels.add("Unique Uddokta Visit(MTD)");
        data.setLabels(labels);
         
        pieModel3.setData(data);
    }
    
    /*Pie Chart - 4 Start Here For Unique Uddokta, Previous 3 month unique Uddokta visit ---- TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1)*/
    private void createPieModel4() {
    	String nativeQuery = "SELECT COUNT(DISTINCT fk_uddokta_id) AS countValue FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1), 'DD-MON-YY') AND TO_CHAR(LAST_DAY(ADD_MONTHS(SYSDATE, -1)), 'DD-MON-YY') "
    			+ "UNION ALL "
    			+ "SELECT COUNT(DISTINCT fk_uddokta_id) AS countValue FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -2), 'DD-MON-YY') AND TO_CHAR(LAST_DAY(ADD_MONTHS(SYSDATE, -2)), 'DD-MON-YY') "
    			+ "UNION ALL "
    			+ "SELECT COUNT(DISTINCT fk_uddokta_id) AS countValue FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -3), 'DD-MON-YY') AND TO_CHAR(LAST_DAY(ADD_MONTHS(SYSDATE, -3)), 'DD-MON-YY') ";
    	String key = "countValue";
		List<Number> previousMonthUniqueUddoktaVisitList = commonUtilService.getNumberValueList(nativeQuery, key);
        pieModel4 = new PieChartModel();
        ChartData data = new ChartData();
         
        PieChartDataSet dataSet = new PieChartDataSet();
        dataSet.setData(previousMonthUniqueUddoktaVisitList);
         
        List<String> bgColors = new ArrayList<>();
        bgColors.add("#5dade2");
        bgColors.add("#FF5733");
        bgColors.add("#a569bd");
        dataSet.setBackgroundColor(bgColors);
         
        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Last Month Unique Uddokta Visit");
        labels.add("Last 2nd Months Unique Uddokta Visit");
        labels.add("Last 3rd Months Unique Uddokta Visit");
        data.setLabels(labels);
         
        pieModel4.setData(data);
    }
    
    /*Pie Chart - 5 Start Here For Total Uddokta, Previous 3 month total Uddokta visit ---- TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1)*/
    private void createPieModel5() {
    	String nativeQuery = "SELECT COUNT(*) AS countValue FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -1), 'DD-MON-YY') AND TO_CHAR(LAST_DAY(ADD_MONTHS(SYSDATE, -1)), 'DD-MON-YY') "
    			+ "UNION ALL "
    			+ "SELECT COUNT(*) AS countValue FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -2), 'DD-MON-YY') AND TO_CHAR(LAST_DAY(ADD_MONTHS(SYSDATE, -2)), 'DD-MON-YY') "
    			+ "UNION ALL "
    			+ "SELECT COUNT(*) AS countValue FROM TMR_DATAS WHERE CREATE_DATE BETWEEN TO_CHAR(ADD_MONTHS(TRUNC(SYSDATE, 'MM'), -3), 'DD-MON-YY') AND TO_CHAR(LAST_DAY(ADD_MONTHS(SYSDATE, -3)), 'DD-MON-YY') ";
    	String key = "countValue";
		List<Number> previousMonthTotalUddoktaVisitList = commonUtilService.getNumberValueList(nativeQuery, key);
        pieModel5 = new PieChartModel();
        ChartData data = new ChartData();
         
        PieChartDataSet dataSet = new PieChartDataSet();
        dataSet.setData(previousMonthTotalUddoktaVisitList);
         
        List<String> bgColors = new ArrayList<>();
        bgColors.add("#5dade2");
        bgColors.add("#FF5733");
        bgColors.add("#a569bd");
        dataSet.setBackgroundColor(bgColors);
         
        data.addChartDataSet(dataSet);
        List<String> labels = new ArrayList<>();
        labels.add("Last Month Total Uddokta Visit");
        labels.add("Last 2nd Months Total Uddokta Visit");
        labels.add("Last 3rd Months Total Uddokta Visit");
        data.setLabels(labels);
         
        pieModel5.setData(data);
    }
    /*Pie Chart END Here*/
    
    
    /*GMap Start Here*/
    private void createMapModel1() {
    	List<GoogleMapDTO> googleMapDTOList = commonUtilService.getGoogleMapDTOList(GoogleMapDTO.class);
		LatLng coord = null;
    	
    	mapModel1 = new DefaultMapModel();

        //Shared coordinates
    	for (GoogleMapDTO googleMapDTO: googleMapDTOList) {
    		coord = new LatLng(googleMapDTO.getLattitude().doubleValue(), googleMapDTO.getLongitude().doubleValue());
    		mapModel1.addOverlay(new Marker(coord, googleMapDTO.getTitle(), googleMapDTO.getImageName(), googleMapDTO.getImagePath()));
    	} 
    }

    //TODO now not using, but in future will be used
    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }
    /*GMap Chart END Here*/
    
    
    //******************************* Sample Get Data List *******************************
    
    /* Flow Images Start Here */
    private void getFlowImages() {
    	images = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            images.add("rajib" + i + ".png");
        }
    }
    /* Flow Images End Here */
    
    /* Dialog Panel on click Start Here */
    private void getLoanProductsList() {
    	loanProductList = new ArrayList<LoanProduct>();
        
    	loanProductList.add(new LoanProduct("5001", "3 Months", "Active", new BigDecimal("124567890.25")));
    	loanProductList.add(new LoanProduct("5002", "6 Months", "In-Active",  new BigDecimal("124567890.25")));
    	loanProductList.add(new LoanProduct("5003", "9 Months", "In-Active",  new BigDecimal("124567890.25")));
    	loanProductList.add(new LoanProduct("5004", "12 Months", "Active",  new BigDecimal("124567890.25")));
    	loanProductList.add(new LoanProduct("5005", "36 Months", "Active",  new BigDecimal("124567890.25")));
    	loanProductList.add(new LoanProduct("5005", "60 Months", "Active",  new BigDecimal("124567890.25")));
    	loanProductList.add(new LoanProduct("5006", "Campaign 1", "Active",  new BigDecimal("124567890.25")));
    	loanProductList.add(new LoanProduct("5007", "Campaign 2", "Active",  new BigDecimal("124567890.25")));
    	loanProductList.add(new LoanProduct("5008", "Campaign 3", "In-Active",  new BigDecimal("124567890.25")));
        loanProductList.add(new LoanProduct("5009", "Campaign 4", "Active",  new BigDecimal("124567890.25")));
        loanProductList.add(new LoanProduct("5010", "Campaign 5", "Active",  new BigDecimal("124567890.25")));
        loanProductList.add(new LoanProduct("5011", "Campaign 6", "Active",  new BigDecimal("124567890.25")));
 
    }
    /* Dialog Panel on click End Here */
    
    
    
    //******************************* Sample Graphs and Grids *******************************
    
    /*Bar Chart-0 Model Start Here*/
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
 
        ChartSeries isl = new ChartSeries();
        isl.setLabel("Ecom-Payment(Cr.)");
        isl.set("2017", 150);
        isl.set("2018", 25);
        isl.set("2019", 120);
        isl.set("2020", 100);
        isl.set("2021", 144);
 
        ChartSeries nisl = new ChartSeries();
        nisl.setLabel("Bill-Payment(Cr.)");
        nisl.set("2017", 135);
        nisl.set("2018", 120);
        nisl.set("2019", 145);
        nisl.set("2019", 160);
        nisl.set("2021", 140);
 
        model.addSeries(isl);
        model.addSeries(nisl);
 
        return model;
    }
    
    private void createBarModel() {
    	barModel = initBarModel();
    	 
        barModel.setTitle("Customer Bar Chart");
        barModel.setAnimate(true);
        barModel.setLegendPosition("ne");
 
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("<---- Year ---->");
 
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("<----Acquisition---->");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
    /*Bar Chart-0 Model END Here*/
    
    /*Line Chart Area Model Start Here*/
    private void createAreaModel() {
        areaModel = new LineChartModel();
 
        LineChartSeries ci = new LineChartSeries();
        ci.setFill(true);
        ci.setLabel("Cash In");
        ci.set("2017", 150);
        ci.set("2018", 25);
        ci.set("2019", 120);
        ci.set("2020", 100);
        ci.set("2021", 144);
 
        LineChartSeries co = new LineChartSeries();
        co.setFill(true);
        co.setLabel("Cash Out");
        co.set("2017", 90);
        co.set("2018", 120);
        co.set("2019", 52);
        ci.set("2020", 100);
        ci.set("2021", 144);
 
        areaModel.addSeries(ci);
        areaModel.addSeries(co);
 
        areaModel.setTitle("Transaction Chart");
        areaModel.setAnimate(true);
        areaModel.setLegendPosition("ne");
        areaModel.setStacked(true);
        areaModel.setShowPointLabels(true);
 
        Axis xAxis = new CategoryAxis("<---- Years ---->");
        areaModel.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = areaModel.getAxis(AxisType.Y);
        yAxis.setLabel("<----Trans Amount Cr---->");
        yAxis.setMin(0);
        yAxis.setMax(300);
    }
    /*Line Chart Area Model END Here*/
    
    /*Line Chart Line Model Start Here*/
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
 
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Send Money");
 
        series1.set(1, 2);
        series1.set(2, 1);
        series1.set(3, 3);
        series1.set(4, 6);
        series1.set(5, 8);
 
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Mobile Recharge");
 
        series2.set(1, 6);
        series2.set(2, 3);
        series2.set(3, 2);
        series2.set(4, 7);
        series2.set(5, 9);
 
        model.addSeries(series1);
        model.addSeries(series2);
 
        return model;
    }
    
    private void createLineModels() {
        lineModel1 = initLinearModel();
        lineModel1.setTitle("Linear Transaction Chart");
        lineModel1.setAnimate(true);
        lineModel1.setLegendPosition("n");
 
        Axis xAxis = lineModel1.getAxis(AxisType.X);
        xAxis.setLabel("<---- Years ---->");
        
        Axis yAxis = lineModel1.getAxis(AxisType.Y);
        yAxis.setLabel("<----Transaction---->");
        yAxis.setMin(0);
        yAxis.setMax(10);        
 
        /*zoomModel = initLinearModel();
        zoomModel.setTitle("Zoom");
        zoomModel.setZoom(true);
        zoomModel.setLegendPosition("e");
        yAxis = zoomModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(10);*/
    }
    /*Line Chart Line Model END Here*/
        
    //TODO
    /*Mixed Bar & Line Chart Model Start Here*/
    public void createMixedModel() {
        mixedModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet dataSet = new BarChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(10);
        values.add(20);
        values.add(30);
        values.add(40);
        dataSet.setData(values);
        dataSet.setLabel("Bar Dataset");
        dataSet.setBorderColor("rgb(255, 99, 132)");
        dataSet.setBackgroundColor("rgba(255, 99, 132, 0.2)");

        LineChartDataSet dataSet2 = new LineChartDataSet();
        List<Number> values2 = new ArrayList<>();
        values2.add(10);
        values2.add(30);
        values2.add(50);
        values2.add(20);
        dataSet2.setData(values2);
        dataSet2.setLabel("Line Dataset");
        dataSet2.setFill(false);
        dataSet2.setBorderColor("rgb(54, 162, 235)");

        data.addChartDataSet(dataSet);
        data.addChartDataSet(dataSet2);

        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        data.setLabels(labels);

        //mixedModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);

        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);
        //mixedModel.setOptions(options);
    }
    /*Mixed Bar & Line Chart Model End Here*/
    
    /*Combined Model Bar & Line Chart Model Start Here*/
    private void createCombinedModel() {
        combinedModel = new BarChartModel();
 
        BarChartSeries eComPayment = new BarChartSeries();
        eComPayment.setLabel("Ecom-Payment(Cr.)");
 
        eComPayment.set("Apr", 90);
        eComPayment.set("May", 120);
        eComPayment.set("Jun", 100);
        eComPayment.set("Jul", 144);
        eComPayment.set("Aug", 125);
        eComPayment.set("Sep", 75);
        
        BarChartSeries qrPayment = new BarChartSeries();
        qrPayment.setLabel("QR-Payment(Cr.)");
 
        qrPayment.set("Apr", 70);
        qrPayment.set("May", 110);
        qrPayment.set("Jun", 90);
        qrPayment.set("Jul", 110);
        qrPayment.set("Aug", 100);
        qrPayment.set("Sep", 65);
 
        LineChartSeries bilPayment = new LineChartSeries();
        bilPayment.setLabel("Bill-Payment(Cr.)");
 
        bilPayment.set("Apr", 100);
        bilPayment.set("May", 52);
        bilPayment.set("Jun", 150);
        bilPayment.set("Jul", 130);
        bilPayment.set("Aug", 115);
        bilPayment.set("Sep", 80);
 
        combinedModel.addSeries(eComPayment);
        combinedModel.addSeries(qrPayment);
        combinedModel.addSeries(bilPayment);
 
        combinedModel.setTitle("Bar and Line");
        combinedModel.setLegendPosition("ne");
        combinedModel.setAnimate(true);
        combinedModel.setMouseoverHighlight(true);
        combinedModel.setShowDatatip(true);
        combinedModel.setShowPointLabels(true);
        combinedModel.setShadow(true);
        Axis xAxis = combinedModel.getAxis(AxisType.X);
        xAxis.setLabel("<---- Months ---->");

        Axis yAxis = combinedModel.getAxis(AxisType.Y);
        yAxis.setLabel("<----Amount---->");
        yAxis.setMin(0);
        yAxis.setMax(200);
    }
    /*Combined Model Bar & Line Chart Model End Here*/
     
    
    /*Donut Chart Model Start Here*/
    private DonutChartModel initDonutModel() {
        DonutChartModel model = new DonutChartModel();
 
        Map<String, Number> circle1 = new LinkedHashMap<String, Number>();
        circle1.put("Product 1", 150);
        circle1.put("Product 2", 400);
        circle1.put("Product 3", 200);
        circle1.put("Product 4", 10);
        model.addCircle(circle1);
 
        Map<String, Number> circle2 = new LinkedHashMap<String, Number>();
        circle2.put("Product 1", 540);
        circle2.put("Product 2", 125);
        circle2.put("Product 3", 702);
        circle2.put("Product 4", 421);
        model.addCircle(circle2);
 
        Map<String, Number> circle3 = new LinkedHashMap<String, Number>();
        circle3.put("Product 1", 40);
        circle3.put("Product 2", 325);
        circle3.put("Product 3", 402);
        circle3.put("Product 4", 421);
        model.addCircle(circle3);
 
        return model;
    }
    
    private void createDonutModel() {        
        donutModel = initDonutModel();
        donutModel.setTitle("Products Revenue");
        donutModel.setLegendPosition("e");
        donutModel.setSliceMargin(5);
        donutModel.setShowDataLabels(true);
        donutModel.setDataFormat("value");
        donutModel.setShadow(false);
    }
    /*Donut Chart Model End Here*/
}
