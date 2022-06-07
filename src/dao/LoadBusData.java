package dao;

import models.Bus;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Vector;

public class LoadBusData {
    //加载班车数据
    public static Vector<Vector> loadBusesData() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("files\\busMessage"));

        ArrayList<Bus> buses = new ArrayList<Bus>();

        Gson gson = new Gson();
        String line;
        while ((line = br.readLine()) != null) {
            Bus bus = gson.fromJson(line, Bus.class);
            buses.add(bus);
        }

        br.close();
        if(buses.size()==0){
            return new Vector<Vector>();
        }

        Vector<Vector> tableData=new Vector<>();

        for(Bus bus:buses){
            Vector<Object> busData = new Vector<>();
            busData.add(" ");
            busData.add(bus.getId());
            busData.add(bus.getRoutId());
            busData.add(bus.getRoutName());
            busData.add(bus.getDirection());
            busData.add(bus.getRoutType());
            busData.add(bus.getServeDate());
            busData.add(bus.getServePeriod());
            busData.add(bus.getStartTime());
            busData.add(bus.getDdl());
            busData.add(bus.getReservedNumber());
            busData.add(bus.getRemark());

            tableData.add(busData);
        }
        return tableData;
    }
}
