package controllers;

import com.google.gson.Gson;
import dao.RWFileForOldMan;
import dao.RWFileForSteward;
import dao.RWFileForUser;
import models.OldMan;
import models.Steward;
import models.User;
import utils.DelUser;
import views.ConfirmUserInterface;

import java.io.*;
import java.util.ArrayList;

//删除用户功能的控制器
public class DelUserController {
    private static DelUserController singletonInstance=null;

    static public DelUserController getSingletonInstance(){
        if(singletonInstance==null){
            singletonInstance=new DelUserController();
        }
        return singletonInstance;
    }

    private DelUserController(){}

    public  void delUser(int selectedRow) throws Exception {
        ArrayList<User> users = RWFileForUser.readFile();

        delete(users,selectedRow);

    }

    public void delete(ArrayList<User> users, int selectedRow) throws Exception {
        User selectedUser = users.get(selectedRow);
        ArrayList delUser = DelUser.delUser(users, selectedRow);
        RWFileForUser.WriteFile(delUser);

        if (selectedUser.getAuthority().equals("生活管家")) {
            ArrayList<Steward> stewards = RWFileForSteward.readFile();
            for (int i = 0; i < stewards.size(); i++) {
                Steward steward = stewards.get(i);
                if (steward.getAccount().equals(selectedUser.getAccount())) {
                    stewards.remove(i);
                }
            }
            writeFile(stewards);

            ArrayList<OldMan> oldMEN = RWFileForOldMan.readFile();

            for(OldMan oldMan:oldMEN){
                if(oldMan.getStewardAccount().equals(selectedUser.getAccount())){
                    oldMan.setStewardAccount("");
                }
            }

           RWFileForOldMan.writeFile(oldMEN);
        }

    }

    public void writeFile(ArrayList<Steward> stewards) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("files\\ServiceObjectMessage"));
        Gson gson = new Gson();

        for (Steward steward : stewards) {
            String s = gson.toJson(steward);
            bw.write(s);
            bw.newLine();
            bw.flush();
        }

        bw.close();

    }

}
