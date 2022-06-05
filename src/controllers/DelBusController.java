package controllers;

import dao.RWFileForBus;
import models.Bus;

import java.io.IOException;
import java.util.ArrayList;

public class DelBusController {
    private static DelBusController singletonInstance=null;

    static public DelBusController getSingletonInstance(){
        if(singletonInstance==null){
            singletonInstance=new DelBusController();
        }
        return singletonInstance;
    }

    private DelBusController(){}

    public void delBus(int selectedRow)throws IOException {
        ArrayList<Bus> buses = RWFileForBus.readFile();
        buses.remove(selectedRow);

        RWFileForBus.writeFile(buses);
    }


}
