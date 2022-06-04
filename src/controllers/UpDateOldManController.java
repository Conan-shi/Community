package controllers;

import com.google.gson.Gson;
import dao.RWFileForOldMan;
import models.OldMan;
import models.Steward;

import java.io.*;
import java.util.ArrayList;

public class UpDateOldManController {
    public static void upDateOldMan(OldMan oldMan)throws IOException{
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
