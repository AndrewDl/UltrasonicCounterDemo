package controller;

import javafx.application.Platform;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    ControllerSerialControlPanel controllerSerialControlPanel;
    ControllerPeopleDisplay controllerPeopleDisplay;

    public Sensor s1 = null;

    final int size = 100;

    int[] sensorDataDefault = new int[size];
    int[] sensorData = new int[size];
    int sensorDataIndex = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerSerialControlPanel = ControllerSerialControlPanel.Instance;
        controllerPeopleDisplay = ControllerPeopleDisplay.Instance;

        controllerPeopleDisplay.init(this);
        controllerSerialControlPanel.init(this);

        for (int i = 0; i < sensorDataDefault.length; i++) {
            sensorDataDefault[i] = 999;
        }

    }

    public void CreateSensor(){
        s1 = new Sensor(controllerSerialControlPanel.getSerial());
        s1.AddOnDataAvailableHandler(data -> onNewData(data));
        s1.AddOnDataAvailableHandler(data -> Platform.runLater(() -> controllerPeopleDisplay.Draw(sensorData,sensorDataIndex-1)) );
    }

    private void onNewData(int data){
        if (sensorDataIndex == sensorData.length) {
            sensorDataIndex = 0;
            //sensorData = sensorDataDefault.clone();
        }
        sensorData[sensorDataIndex] = data;
        sensorDataIndex++;
    }
}
