package net.pixeleon.khpi.year2;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.math3.distribution.FDistribution;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.converter.DoubleStringConverter;

public class AnovaController implements Initializable {

	private ObservableList<Double> obsListA;
	private ObservableList<Double> obsListB;
	private ObservableList<Double> obsListC;
	private ObservableList<Double> obsListD;
	private double ssw, ssb, dfw, dfb, msw, msb, fcrit, freal, p;
	private boolean h0;
	
	@FXML ListView<Double> listViewD;
	@FXML ListView<Double> listViewB;
	@FXML ListView<Double> listViewC;
	@FXML ListView<Double> listViewA;
	@FXML Button buttonAddA;
	@FXML Button buttonRemoveA;
	@FXML Button buttonAddB;
	@FXML Button buttonRemoveB;
	@FXML Button buttonAddC;
	@FXML Button buttonRemoveC;
	@FXML Button buttonAddD;
	@FXML Button buttonRemoveD;
	@FXML Button buttonAnalyze;
	@FXML Button buttonClear;
	@FXML TextField textBoxSSW;
	@FXML Label labelConclusion;
	@FXML TextField textBoxDFW;
	@FXML TextField textBoxMSW;
	@FXML TextField textBoxFCrit;
	@FXML TextField textBoxP;
	@FXML TextField textBoxSSB;
	@FXML TextField textBoxDFB;
	@FXML TextField textBoxMSB;
	@FXML TextField textBoxFReal;
	@FXML TextField textBoxAlpha;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listViewA.setPlaceholder(new Label(""));
		listViewA.setCellFactory(TextFieldListCell.forListView(new DoubleStringConverter()));
		listViewB.setPlaceholder(new Label(""));
		listViewB.setCellFactory(TextFieldListCell.forListView(new DoubleStringConverter()));
		listViewC.setPlaceholder(new Label(""));
		listViewC.setCellFactory(TextFieldListCell.forListView(new DoubleStringConverter()));
		listViewD.setPlaceholder(new Label(""));
		listViewD.setCellFactory(TextFieldListCell.forListView(new DoubleStringConverter()));
		obsListA = FXCollections.observableArrayList(4.0,4.0,3.0,3.0,1.0);
		obsListB = FXCollections.observableArrayList(4.0,5.0,5.0,3.0,5.0);
		obsListC = FXCollections.observableArrayList(4.0,4.0,3.0,3.0,4.0);
		obsListD = FXCollections.observableArrayList(4.0,4.0,3.0,3.0,5.0);
		initListViews();
	}
	
	private void initLists() {
		List<Double> listA = new ArrayList<Double>();
		obsListA = FXCollections.observableList(listA);
		List<Double> listB = new ArrayList<Double>();
		obsListB = FXCollections.observableList(listB);
		List<Double> listC = new ArrayList<Double>();
		obsListC = FXCollections.observableList(listC);
		List<Double> listD = new ArrayList<Double>();
		obsListD = FXCollections.observableList(listD);
	}

	private void initListViews() {
		listViewA.setItems(obsListA);
		listViewB.setItems(obsListB);
		listViewC.setItems(obsListC);
		listViewD.setItems(obsListD);
	}

	@FXML public void doAddA(ActionEvent event) {
		if (obsListA == null) {
			initLists();
		}
		obsListA.add(0.0);
		initListViews();
	}

	@FXML public void doRemoveA(ActionEvent event) {
		if (obsListA == null) {
            return;
        }
        if (obsListA.size() > 0) {
            obsListA.remove(obsListA.size() - 1);
        }
        if (obsListA.size() <= 0) {
            obsListA = null;
        }
	}

	@FXML public void doAddB(ActionEvent event) {
		if (obsListB == null) {
			initLists();
		}
		obsListB.add(0.0);
		initListViews();
	}

	@FXML public void doRemoveB(ActionEvent event) {
		if (obsListB == null) {
            return;
        }
        if (obsListB.size() > 0) {
            obsListB.remove(obsListB.size() - 1);
        }
        if (obsListB.size() <= 0) {
            obsListB = null;
        }
	}

	@FXML public void doAddC(ActionEvent event) {
		if (obsListC == null) {
			initLists();
		}
		obsListC.add(0.0);
		initListViews();
	}

	@FXML public void doRemoveC(ActionEvent event) {
		if (obsListC == null) {
            return;
        }
        if (obsListC.size() > 0) {
            obsListC.remove(obsListC.size() - 1);
        }
        if (obsListC.size() <= 0) {
            obsListC = null;
        }
	}

	@FXML public void doAddD(ActionEvent event) {
		if (obsListD == null) {
			initLists();
		}
		obsListD.add(0.0);
		initListViews();
	}

	@FXML public void doRemoveD(ActionEvent event) {
		if (obsListD == null) {
            return;
        }
        if (obsListD.size() > 0) {
            obsListD.remove(obsListD.size() - 1);
        }
        if (obsListD.size() <= 0) {
            obsListD = null;
        }
	}

	@FXML public void doAnalyze(ActionEvent event) {
		ssw = 0;
		ssw += getSampleSS(obsListA);
		ssw += getSampleSS(obsListB);
		ssw += getSampleSS(obsListC);
		ssw += getSampleSS(obsListD);
		List<Double> listPopul = new ArrayList<Double>();
		listPopul.addAll(obsListA);
		listPopul.addAll(obsListB);
		listPopul.addAll(obsListC);
		listPopul.addAll(obsListD);
		ssb = getSampleSS(listPopul) - ssw;
		dfb = 3;
		dfw = listPopul.size()-4;
		msb = ssb / dfb;
		msw = ssw / dfw;
		freal = msb / msw;
		double confid = Double.parseDouble(textBoxAlpha.getText());
		FDistribution fd = new FDistribution(dfb,dfw);
		fcrit = fd.inverseCumulativeProbability(1-confid);
		p=1-fd.cumulativeProbability(freal);
		h0 = freal > fcrit ? false : true;
		updateTextFields();
		
	}
	

	public double getSampleMean(List<Double> list) {
		double total = 0;
		for(int i = 0; i < list.size(); i++) {
			total += list.get(i);
		}
		return total / list.size();
	}
	
	public double getSampleSS(List<Double> list) {
		double ss = 0;
		double mean = getSampleMean(list);
		for(int i = 0; i < list.size(); i++) {
			double xi = list.get(i) - mean;
			ss += xi * xi;
		}
		return ss;
	}
	

	public String getConclusion() {
		if (freal <= fcrit)
			return "Conclusion: Null hypothesis is accepted";
		else
			return "Conclusion: Null hypothesis is rejected!";
	}
	
	
	private void updateTextFields() {
		textBoxSSW.setText(ssw+"");
		textBoxSSB.setText(ssb+"");
		textBoxDFW.setText(dfw+"");
		textBoxDFB.setText(dfb+"");
		textBoxMSW.setText(msw+"");
		textBoxMSB.setText(msb+"");
		textBoxFCrit.setText(fcrit+"");
		textBoxFReal.setText(freal+"");
		textBoxP.setText(p+"");
		if (h0)
			labelConclusion.setText("Conclusion: Null hypothesis is accepted");
		else
			labelConclusion.setText("Conclusion: Null hypothesis is rejected!");
	}

	@FXML public void doClear(ActionEvent event) {
		textBoxSSW.setText("");
		textBoxSSB.setText("");
		textBoxDFW.setText("");
		textBoxDFB.setText("");
		textBoxMSW.setText("");
		textBoxMSB.setText("");
		textBoxFCrit.setText("");
		textBoxFReal.setText("");
		textBoxP.setText("");
		labelConclusion.setText("Conclusion: ");
		listViewA.setItems(null);
		listViewB.setItems(null);
		listViewC.setItems(null);
		listViewD.setItems(null);
		textBoxAlpha.setText("0.05");
		initLists();
		initListViews();
	}
	
	

}
