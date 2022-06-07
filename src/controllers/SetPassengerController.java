package controllers;

import dao.RWFileForBus;
import models.Bus;

import java.io.IOException;
import java.util.ArrayList;

//添加乘客功能的控制器
public class SetPassengerController {
    private static SetPassengerController singletonInstance=null;

    static public SetPassengerController getSingletonInstance(){
        if(singletonInstance==null){
            singletonInstance=new SetPassengerController();
        }
        return singletonInstance;
    }

    private SetPassengerController(){}

    public void setPassenger(int selectedRow,String oldManAccount)throws IOException {
        ArrayList<Bus> buses = RWFileForBus.readFile();
        Bus bus = buses.get(selectedRow);

        addPassengerNumber(bus);
        bus.addPassengers(oldManAccount);
        RWFileForBus.writeFile(buses);
    }

    public void addPassengerNumber(Bus bus) {
        String reservedNumber = bus.getReservedNumber();
        int num = Integer.parseInt(reservedNumber);
        num++;

        bus.setReservedNumber(String.valueOf(num));

    }
}
