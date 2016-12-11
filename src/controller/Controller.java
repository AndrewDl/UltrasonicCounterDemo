package controller;

import javafx.application.Platform;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    ControllerSerialControlPanel controllerSerialControlPanel;
    ControllerPeopleDisplay controllerPeopleDisplay;

    public Sensor s1 = null;

    CollectedData c = new CollectedData(50);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerSerialControlPanel = ControllerSerialControlPanel.Instance;
        controllerPeopleDisplay = ControllerPeopleDisplay.Instance;

        controllerPeopleDisplay.init(this);
        controllerSerialControlPanel.init(this);


    }

    public void CreateSensor(){
        s1 = new Sensor(controllerSerialControlPanel.getSerial());
        s1.AddOnDataAvailableHandler(data -> onNewData(data));
        s1.AddOnDataAvailableHandler(data -> Platform.runLater(() -> controllerPeopleDisplay.Draw(c.getDataFocusNew())) );
        c.addOnCountChange(() -> Platform.runLater(() -> controllerPeopleDisplay.setCount(c.getCount())));
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
