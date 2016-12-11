package controller;

import model.DataClasses.Data;
import model.DataClasses.DataCollect;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import model.DataClasses.DataLogger;
import model.DataClasses.DataSender;
import model.ParametersClasses.Parameters;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    ControllerSerialControlPanel controllerSerialControlPanel;
    ControllerPeopleDisplay controllerPeopleDisplay;

    Parameters parameters = Parameters.getInstance();

    Timer t = new Timer(parameters.getDataSendTimeout(), e -> onTimer());

    public Sensor s1 = null;

    DataCollect c = new DataCollect(100);
    DataLogger dataLogger = new DataLogger();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerSerialControlPanel = ControllerSerialControlPanel.Instance;
        controllerPeopleDisplay = ControllerPeopleDisplay.Instance;

        controllerPeopleDisplay.init(this);
        controllerSerialControlPanel.init(this);

        t.start();
    }

    private void onTimer(){
        dataLogger.SendData();
    }

    public void CreateSensor(){
        Parameters parameters = Parameters.getInstance();

        s1 = new Sensor(controllerSerialControlPanel.getSerial());
        s1.AddOnDataAvailableHandler(data -> onNewData(data));
        s1.AddOnDataAvailableHandler(data -> Platform.runLater(() -> controllerPeopleDisplay.Draw(c.getDataFocusNew())) );
        c.addOnCountChange(() -> Platform.runLater(() -> controllerPeopleDisplay.setCount(c.getCount())));
        c.addOnCountChange(() -> {
            if (c.getCountDifference()!=0)
                dataLogger.addData( new Data(c.getCountDifference(),0));
        });
    }

    private void onNewData(int data){
        /*if (sensorDataIndex == sensorData.length) {
            sensorDataIndex = 0;
            //sensorData = sensorDataDefault.clone();
        }
        sensorData[sensorDataIndex] = data;
        sensorDataIndex++;
        */
        c.addData(data);
    }
}
