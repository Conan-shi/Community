package controllers;

import com.google.gson.Gson;
import dao.RWFileForUser;
import models.OldMan;
import models.User;
import utils.ReadFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddOldController {

    public void addOld(OldMan oldMan)throws IOException{
        BufferedWriter bw=new BufferedWriter(new FileWriter("files\\oldManMessage",true));
        Gson gson=new Gson();
        ArrayList<OldMan> oldMEN = ReadFile.readFile("oldMan");
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
