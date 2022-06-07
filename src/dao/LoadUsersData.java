package dao;

import models.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Vector;

public class LoadUsersData {
    //加载用户数据
    public static Vector<Vector> loadUsersData() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader("files\\usersMessage"));

        ArrayList<User> users = new ArrayList<User>();

        Gson gson = new Gson();
        String line;
        while ((line = br.readLine()) != null) {
            User user1 = gson.fromJson(line, User.class);
            users.add(user1);
        }

        br.close();
        if(users.size()==0){
            return new Vector<Vector>();
        }

        Vector<Vector> tableData=new Vector<>();

        for(User user:users){
            Vector<Object> userData = new Vector<>();
            userData.add(" ");
            userData.add(user.getId());
            userData.add(user.getAccount());
            userData.add(user.getName());
            userData.add(user.getGender());
            userData.add(user.getBirthDate());
            userData.add(user.getTelephoneNumber());
            userData.add(user.getAuthority());

            tableData.add(userData);
        }
        return tableData;
    }
}
