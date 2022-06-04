package controllers;

import dao.RWFileForBus;
import models.Bus;

import java.io.IOException;

public class AddBusController {

    public void addBus(Bus bus)throws IOException {
        RWFileForBus.updateFile(bus);
    }
}
