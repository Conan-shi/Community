package controllers;

import models.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CheckIn {
    public static boolean checkIn(String account, String password) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("files\\usersMessage"));

        ArrayList<User> array = new ArrayList<User>();

        String line;
        boolean flag = false;
        while ((line = br.readLine()) != null) {
            Gson gson = new Gson();
            User user = gson.fromJson(line, User.class);
            if (user.getAccount().equals(account) && user.getPassword().equals(password)) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public static User getUser(String account) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("files\\usersMessage"));

        ArrayList<User> users = new ArrayList<User>();
        String name = null;

        String line;
        while ((line = br.readLine()) != null) {
            Gson gson = new Gson();
            User user = gson.fromJson(line, User.class);
            users.add(user);
        }
        User user0=null;

        for(User user:users){
            if(user.getAccount().equals(account)){
                user0=user;
            }
        }

        return user0;
    }

}

