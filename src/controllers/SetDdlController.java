package controllers;

import dao.RWFileForBus;
import models.Bus;
import utils.Check;

import java.io.IOException;
import java.util.ArrayList;

public class SetDdlController {
    public boolean setDdl(String ddl,int selectedRow)throws IOException {
        ArrayList<Bus> buses = RWFileForBus.readFile();
        Bus bus = buses.get(selectedRow);
        if(!Check.checkBusDdl(ddl,bus.getStartTime())){
            return false;
        }else {
            ArrayList<Bus> buses1 = RWFileForBus.readFile();
            buses1.get(selectedRow).setDdl(ddl);

            RWFileForBus.writeFile(buses1);
        }

        return true;
    }
}
