package utils;

import dao.RWFileForUser;
import models.User;

import java.io.IOException;
import java.util.ArrayList;

public class GetSelectedRow {
    public static int getSelectedRow(String account)throws IOException {
        ArrayList<User> users = RWFileForUser.readFile();

        int index=0;
        for(int i=0;i<users.size();i++){
            if(users.get(i).getAccount().equals(account)){
                index=i;
            }
        }
        return index;
    }
}
