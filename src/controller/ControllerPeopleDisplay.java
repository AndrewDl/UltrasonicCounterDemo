package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Andrew on 08.12.2016.
 */
public class ControllerPeopleDisplay implements Initializable {
    public static ControllerPeopleDisplay Instance;

    @FXML LineChart lineChart;
    @FXML Label labelPeopleNumber;

    public ControllerPeopleDisplay(){
        Instance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void draw(){
        XYChart.Series series = new XYChart.Series<Number, Number>();

        series.getData().add(new XYChart.Data(7,9));
        series.getData().add(new XYChart.Data(8,11));
        lineChart.getData().add(series);
        labelPeopleNumber.setText("test");
    }
}
