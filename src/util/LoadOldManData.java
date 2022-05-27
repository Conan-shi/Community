package util;

import Actors.OldMan;
import Actors.Steward;
import Actors.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Vector;

public class LoadOldManData {
    public static Vector<Vector> loadOldManData(int selectedRow) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("files\\usersMessage"));

        ArrayList<User> users = new ArrayList<User>();

        Gson gson = new Gson();
        String line;
        while ((line = br.readLine()) != null) {
            User user = gson.fromJson(line, User.class);
            users.add(user);
        }
        br.close();

        ArrayList<OldMan> oldMEN;
        if (users.size() == 0) {
            return new Vector<Vector>();
        } else {
            User user = users.get(selectedRow);

            String stewardAccount = user.getAccount();

            BufferedReader br3 = new BufferedReader(new FileReader("files\\oldManMessage"));

            ArrayList<OldMan> oldMEN0 = new ArrayList<OldMan>();

            String line3;
            while ((line3 = br3.readLine()) != null) {
                OldMan oldMan = gson.fromJson(line3, OldMan.class);
                oldMEN0.add(oldMan);
            }
            br3.close();

            oldMEN = new ArrayList<>();
            for (OldMan oldMan : oldMEN0) {
                if (oldMan.getStewardAccount().equals(stewardAccount)) {
                    oldMEN.add(oldMan);
                }
            }



        }

        Vector<Vector> tableData = new Vector<>();

        for (OldMan oldMan : oldMEN) {
            Vector<Object> oldManData = new Vector<>();
            oldManData.add(" ");
            oldManData.add(oldMan.getId());
            oldManData.add(oldMan.getName());
            oldManData.add(oldMan.getGender());
            oldManData.add(oldMan.getBirthDate());
            oldManData.add(oldMan.getTelephoneNumber());

            tableData.add(oldManData);
        }
        return tableData;
    }
}
