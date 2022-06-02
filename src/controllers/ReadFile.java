package controllers;

import models.Bus;
import models.OldMan;
import models.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {
    public static ArrayList readFile(String s) throws IOException {
        FileReader fr=null;
        switch (s){
            case "user":
                fr=new FileReader("files\\usersMessage");
                break;
            case "oldMan":
                fr=new FileReader("files\\oldManMessage");
                break;
            case "bus":
                fr=new FileReader("files\\busMessage");
                break;

        }
        BufferedReader br = new BufferedReader(fr);

        ArrayList users = new ArrayList();

        Gson gson = new Gson();
        String line;
        while ((line = br.readLine()) != null) {
            switch (s){
                case "user":
                    User user1 = gson.fromJson(line, User.class);
                    users.add(user1);
                    break;
                case "oldMan":
                    OldMan oldMan=gson.fromJson(line,OldMan.class);
                    users.add(oldMan);
                    break;
                case "bus":
                    Bus bus=gson.fromJson(line,Bus.class);
                    users.add(bus);
                    break;

            }

        }

        br.close();
        return users;
    }

    public static User getUser(int selectedRow)throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("files\\usersMessage"));

        ArrayList<User> users = new ArrayList<User>();

        Gson gson = new Gson();
        String line;
        while ((line = br.readLine()) != null) {
            User user = gson.fromJson(line, User.class);
            users.add(user);
        }
        br.close();

        return users.get(selectedRow);
    }

    public static OldMan getOldMan(String stewardAccount,int selectedRow)throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("files\\oldManMessage"));

        ArrayList<OldMan>  oldMEN= new ArrayList<OldMan>();

        Gson gson = new Gson();
        String line;
        while ((line = br.readLine()) != null) {
            OldMan oldMan = gson.fromJson(line, OldMan.class);
            if(oldMan.getStewardAccount().equals(stewardAccount)){
                oldMEN.add(oldMan);
            }
        }
        br.close();

        return oldMEN.get(selectedRow);
    }

    public ArrayList<OldMan> readFile() throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("files\\oldManMessage"));

        ArrayList<OldMan> oldMEN = new ArrayList<OldMan>();
        Gson gson=new Gson();

        String line;
        while ((line = br.readLine()) != null) {
            OldMan oldMan = gson.fromJson(line, OldMan.class);
            oldMEN.add(oldMan);
        }
        br.close();

        return oldMEN;
    }
}
