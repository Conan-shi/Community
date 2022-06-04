package controllers;

import dao.RWFileForBus;
import models.Bus;

import java.io.IOException;
import java.util.ArrayList;

public class DelPassengerController {
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
