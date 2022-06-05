package controllers;

import dao.RWFileForBus;
import models.Bus;

import java.io.IOException;

public class AddBusController {
    private static AddBusController singletonInstance=null;

    static public AddBusController getSingletonInstance(){
        if(singletonInstance==null){
            singletonInstance=new AddBusController();
        }
        return singletonInstance;
    }

    private AddBusController(){}

    public void addBus(Bus bus)throws IOException {
        RWFileForBus.updateFile(bus);
    }
}
