package controllers;

import com.google.gson.Gson;
import dao.RWFileForOldMan;
import models.OldMan;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//添加老人功能的控制器
public class AddOldController {
    private static AddOldController singletonInstance=null;

    static public AddOldController getSingletonInstance(){
        if(singletonInstance==null){
            singletonInstance=new AddOldController();
        }
        return singletonInstance;
    }

    private AddOldController(){}

    public void addOld(OldMan oldMan)throws IOException{
        BufferedWriter bw=new BufferedWriter(new FileWriter("files\\oldManMessage",true));
        Gson gson=new Gson();
        ArrayList<OldMan> oldMEN = RWFileForOldMan.readFile();
        if(oldMEN.size()==0){
            oldMan.setId("1");
        }else {
            int lastId=Integer.parseInt(oldMEN.get(oldMEN.size() - 1).getId());
            oldMan.setId(String.valueOf(lastId+1)) ;
        }
        String s = gson.toJson(oldMan);
        bw.write(s);
        bw.newLine();
        bw.flush();

    }
}
