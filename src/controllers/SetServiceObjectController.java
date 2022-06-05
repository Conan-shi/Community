package controllers;

import com.google.gson.Gson;
import dao.RWFileForOldMan;
import models.OldMan;
import models.Steward;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SetServiceObjectController {
    private static SetServiceObjectController singletonInstance=null;

    static public SetServiceObjectController getSingletonInstance(){
        if(singletonInstance==null){
            singletonInstance=new SetServiceObjectController();
        }
        return singletonInstance;
    }

    private SetServiceObjectController(){}

    public void setServiceObjectController(String s, String s2, ArrayList<Steward> stewards,ArrayList<OldMan> oldMEN){
        StringTokenizer st1 = new StringTokenizer(s, "(");
        String c = st1.nextToken();
        String account0 = st1.nextToken();
        StringTokenizer st2 = new StringTokenizer(account0, ")");
        String stewardAccount = st2.nextToken();

        StringTokenizer st3 = new StringTokenizer(s2, "(");
        String c2 = st3.nextToken();
        String account00 = st3.nextToken();
        StringTokenizer st4 = new StringTokenizer(account00, ")");
        String oldManAccount = st4.nextToken();

        for (Steward steward : stewards) {
            if (steward.getAccount().equals(stewardAccount)) {
                for (OldMan oldMan : oldMEN) {
                    if (oldMan.getAccount().equals(oldManAccount)) {
                        oldMan.setStewardAccount(steward.getAccount());
                        steward.addOldManId(oldMan.getAccount());

                    }
                }
            }
        }

        try {
            writeFile(oldMEN,stewards);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void writeFile(ArrayList<OldMan> oldMEN, ArrayList<Steward> stewards) throws IOException {
        RWFileForOldMan.writeFile(oldMEN);

        BufferedWriter bw2 = new BufferedWriter(new FileWriter("files\\ServiceObjectMessage"));
        Gson gson = new Gson();
        for (Steward steward : stewards) {
            String s5 = gson.toJson(steward);
            bw2.write(s5);
            bw2.newLine();
            bw2.flush();

        }
        bw2.close();
    }
}
