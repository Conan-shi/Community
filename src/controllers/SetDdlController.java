package controllers;

import dao.RWFileForBus;
import models.Bus;
import utils.Check;

import java.io.IOException;
import java.util.ArrayList;

//设置截止时间功能的控制器
public class SetDdlController {
    private static SetDdlController singletonInstance=null;

    static public SetDdlController getSingletonInstance(){
        if(singletonInstance==null){
            singletonInstance=new SetDdlController();
        }
        return singletonInstance;
    }

    private SetDdlController(){}

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
