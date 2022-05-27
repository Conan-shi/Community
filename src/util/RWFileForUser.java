package util;

import Actors.OldMan;
import Actors.User;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class RWFileForUser {
    public static ArrayList<User> readFile()throws IOException{
        BufferedReader br = new BufferedReader(new FileReader("files\\usersMessage"));

        ArrayList<User> users = new ArrayList<User>();
        Gson gson=new Gson();

        String line;
        while ((line = br.readLine()) != null) {
            User user = gson.fromJson(line, User.class);
            users.add(user);
        }
        br.close();
        return users;
    }

    public static void WriteFile(ArrayList<User> users)throws IOException {
        BufferedWriter bw=new BufferedWriter(new FileWriter("files\\usersMessage"));
        Gson gson=new Gson();
        for(User user:users){
            String s = gson.toJson(user);
            bw.write(s);
            bw.newLine();
            bw.flush();
        }
        bw.close();
    }
}
