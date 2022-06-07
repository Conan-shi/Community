package controllers;

import dao.RWFileForBus;
import models.Bus;

import java.io.IOException;
import java.util.ArrayList;

//删除乘客功能的控制器
public class DelPassengerController {
    private static DelPassengerController singletonInstance=null;

    static public DelPassengerController getSingletonInstance(){
        if(singletonInstance==null){
            singletonInstance=new DelPassengerController();
        }
        return singletonInstance;
    }

    private DelPassengerController(){}

    public void delPassenger(int selectedRow,int selectedPassengerRow) throws IOException {
        ArrayList<Bus> buses = RWFileForBus.readFile();
        Bus bus = buses.get(selectedRow);

        ArrayList<String> passengers = bus.getPassengers();
        passengers.remove(selectedPassengerRow);

        int num = Integer.parseInt(bus.getReservedNumber());
        num--;
        bus.setReservedNumber(String.valueOf(num));


        RWFileForBus.writeFile(buses);
    }
}
