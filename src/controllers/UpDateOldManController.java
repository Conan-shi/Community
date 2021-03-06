package controllers;

import com.google.gson.Gson;
import dao.RWFileForOldMan;
import models.OldMan;
import models.Steward;

import java.io.*;
import java.util.ArrayList;

//更改老人信息功能的控制器
public class UpDateOldManController {
    private static UpDateOldManController singletonInstance=null;

    static public UpDateOldManController getSingletonInstance(){
        if(singletonInstance==null){
            singletonInstance=new UpDateOldManController();
        }
        return singletonInstance;
    }

    private UpDateOldManController(){}

    public void upDateOldMan(OldMan oldMan)throws IOException{
        ArrayList<OldMan> oldMEN = RWFileForOldMan.readFile();

        for (int i=0;i<oldMEN.size();i++) {
            OldMan oldMan1=oldMEN.get(i);
            if (oldMan1.getAccount().equals(oldMan.getAccount())) {
                oldMan1.setName(oldMan.getName());
                oldMan1.setGender(oldMan.getGender());
                oldMan1.setBirthDate(oldMan.getBirthDate());
                oldMan1.setTelephoneNumber(oldMan.getTelephoneNumber());
            }
        }

       RWFileForOldMan.writeFile(oldMEN);

    }



}
