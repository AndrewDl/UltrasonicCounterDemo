package controller;

import java.util.ArrayList;

/**
 * Created by Andrew on 10.12.2016.
 */
public class CollectedData {

    public interface ICountChangeHandler{
        void handle();
    }

    /**
     * Подія на зміну значення лічильника
     */
    private ArrayList<ICountChangeHandler> onChangehandlers = new ArrayList<>();

    /**
     * Додати обробник події зміни лічильника
     * @param handler
     */
    public void addOnCountChange(ICountChangeHandler handler){
        onChangehandlers.add(handler);
    }

    /**
     * Видалити обробник події зміни лічильника
     * @param handler
     */
    public void removeOnCountChange(ICountChangeHandler handler){
        onChangehandlers.remove(handler);
    }

    /**
     * Запуск події зміни лічильника
     */
    private void fireOnCountEvent(){
        for (ICountChangeHandler h : onChangehandlers)
            h.handle();
    }

    private int Size = 100;

    private int[] sensorData;
    private int sensorDataIndex = 0;

    private int lastCountPointer;
    private int Count = 0;
    private int CountOld = 0;

    boolean newZone = true;

    /**
     * Об'єкт збору даних
     * @param size розмір масиву даних, що аналізуватиметься
     */
    public CollectedData(int size){
        Size = size;

        sensorData = new int[Size];
        lastCountPointer = sensorData.length;

    }

    /**
     * Метод додає нові дані та проводить їх аналіз
     * @param data нові дані
     */
    public void addData(int data){
        if (sensorDataIndex == sensorData.length) {
            sensorDataIndex = 0;
            //sensorData = sensorDataDefault.clone();
        }

        ///////////////////////////////////////////////////

        if (lastCountPointer == 0){
            int[] array = getDataFocusNew();
            int acceptLevel = 40;
            int pulseWidth = 0;
            int pulseThreshold = 1;

            for (int i = 0; i < array.length ; i++) {

                if (array[i] <= acceptLevel) {

                    pulseWidth++;

                    if ((newZone == true)&&(pulseWidth > pulseThreshold)) {
                        Count++;
                        newZone = false;
                    }
                } else {
                    newZone = true;
                    pulseWidth = 0;
                }
            }
            lastCountPointer = sensorData.length;
        } else {
            lastCountPointer--;
        }


        if (Count>CountOld){
            fireOnCountEvent();
        }

        ///////////////////////////////////////////////////

        sensorData[sensorDataIndex] = data;
        sensorDataIndex++;

    }

    /**
     * Повертає кількість проходів
     * @return
     */
    public int getCount(){
        return Count;
    }


    public int getCurrentIndex(){
        return sensorDataIndex;
    }

    /**
     * Повертає кільцевий масив даних у чистому вигляді
     * @return
     */
    private int[] getData(){
        return sensorData.clone();
    }

    /**
     * Повертає масив даних, де самий новий елемент - останній
     * @return
     */
    public int[] getDataFocusNew(){
        int[] array = getData();

        int[] arrayNew = new int[array.length];

        int index = sensorDataIndex;

        for (int i = 0, j=index; i < array.length-index; i++, j++) {
            arrayNew[i] = array[j];
        }

        for (int i = array.length - index, j=0; i < arrayNew.length; i++, j++) {
            arrayNew[i] = array[j];
        }

        return arrayNew;
    }
}
