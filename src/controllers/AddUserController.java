package controllers;

import com.google.gson.Gson;
import dao.RWFileForUser;
import models.Steward;
import models.User;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//添加用户功能的控制器
public class AddUserController {
    private static AddUserController singletonInstance=null;

    static public AddUserController getSingletonInstance(){
        if(singletonInstance==null){
            singletonInstance=new AddUserController();
        }
        return singletonInstance;
    }

    private AddUserController(){}

    public void addUser(User user)throws IOException {
        BufferedWriter bw=new BufferedWriter(new FileWriter("files\\usersMessage",true));
        Gson gson=new Gson();
        ArrayList<User> users = RWFileForUser.readFile();
        if(users.size()==0){
            user.setId("1");
        }else {
            int lastId=Integer.parseInt(users.get(users.size() - 1).getId());
            user.setId(String.valueOf(lastId+1)) ;
        }
        if(user.getAuthority().equals("生活管家")){
            Steward steward = new Steward(user);
            writeFile(steward);
        }
        String s = gson.toJson(user);
        bw.write(s);
        bw.newLine();
        bw.flush();
    }

    public void writeFile(Steward steward) throws IOException{
        BufferedWriter bw=new BufferedWriter(new FileWriter("files\\ServiceObjectMessage",true));
        Gson gson=new Gson();
        String s = gson.toJson(steward);
        bw.write(s);
        bw.newLine();
        bw.flush();
    }


}
