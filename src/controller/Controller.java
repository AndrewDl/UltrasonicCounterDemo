package controller;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jssc.SerialPortException;
import model.Sensor;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    ControllerSerialControlPanel controllerSerialControlPanel;
    ControllerPeopleDisplay controllerPeopleDisplay;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controllerSerialControlPanel = ControllerSerialControlPanel.Instance;
        controllerPeopleDisplay = ControllerPeopleDisplay.Instance;

        //Sensor s1 = new Sensor();

        controllerPeopleDisplay.draw();
    }
}
