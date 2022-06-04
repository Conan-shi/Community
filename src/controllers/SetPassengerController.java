package controllers;

import dao.RWFileForBus;
import models.Bus;

import java.io.IOException;
import java.util.ArrayList;

public class SetPassengerController {
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
