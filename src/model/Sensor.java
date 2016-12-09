package model;

import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import sample.Main;

/**
 * Created by Andrew on 08.12.2016.
 */
public class Sensor implements SerialPortEventListener{

    public Sensor(){
        test();
    }

    SerialPort serialPort = new SerialPort("COM8");

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if(serialPortEvent.isRXCHAR() && serialPortEvent.getEventValue() > 0){
            try {
                //Получаем ответ от устройства, обрабатываем данные и т.д.
                String data = serialPort.readString(serialPortEvent.getEventValue());
                System.out.println(data);
                //И снова отправляем запрос
                //serialPort.writeString("Get data");
            }
            catch (SerialPortException ex) {
                System.out.println(ex);
            }
        }
    }

    public interface OnDataAvailableHandler{
        void handle();
    }

    private ArrayList<OnDataAvailableHandler> onDataAvailableHandlers = new ArrayList<>();

    public void AddOnDataAvailableHandler(OnDataAvailableHandler handler){
        onDataAvailableHandlers.add(handler);
    }

    public void RemoveOnDataAvailableHandler(OnDataAvailableHandler handler){
        onDataAvailableHandlers.remove(handler);
    }

    void test() {
        try {
            //Открываем порт
            serialPort.openPort();
            //Выставляем параметры
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            //Включаем аппаратное управление потоком
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                    SerialPort.FLOWCONTROL_RTSCTS_OUT);
            //Устанавливаем ивент лисенер и маску
            serialPort.addEventListener(this, SerialPort.MASK_RXCHAR);
            //Отправляем запрос устройству
            //serialPort.writeString("Get data");
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }


    }
}
