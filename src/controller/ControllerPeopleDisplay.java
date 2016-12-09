package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Andrew on 08.12.2016.
 */
public class ControllerPeopleDisplay implements Initializable {
    public static ControllerPeopleDisplay Instance;

    @FXML LineChart lineChart;

    public ControllerPeopleDisplay(){
        Instance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void draw(){
        lineChart.getData().add(new XYChart.Data(5,6));
        lineChart.getData().add(new XYChart.Data(7,9));
        lineChart.getData().add(new XYChart.Data(8,11));
    }
}
