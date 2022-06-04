package controllers;

import com.google.gson.Gson;
import dao.RWFileForUser;
import models.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddAdministratorController {

    public void addAdministrator(User manager)throws IOException{
            BufferedWriter bw=new BufferedWriter(new FileWriter("files\\usersMessage",true));
            Gson gson=new Gson();
            ArrayList<User> users = RWFileForUser.readFile();
            if(users.size()==0){
                manager.setId("1");
            }else {
                int lastId=Integer.parseInt(users.get(users.size() - 1).getId());
                manager.setId(String.valueOf(lastId+1)) ;
            }
            String s = gson.toJson(manager);
            bw.write(s);
            bw.newLine();
            bw.flush();

    }
}
