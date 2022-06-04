package controllers;

import dao.RWFileForBus;
import models.Bus;

import java.io.IOException;
import java.util.ArrayList;

public class DelBusController {
    public void delBus(int selectedRow)throws IOException {
        ArrayList<Bus> buses = RWFileForBus.readFile();
        buses.remove(selectedRow);

        RWFileForBus.writeFile(buses);
    }


}
