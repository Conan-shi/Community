package dao;

import models.OldMan;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class RWFileForOldMan {
    public static ArrayList<OldMan> readFile()throws IOException {
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

    public static void writeFile(ArrayList<OldMan> oldMEN)throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter("files\\oldManMessage"));
        Gson gson=new Gson();
        for(OldMan oldMan:oldMEN){
            String s = gson.toJson(oldMan);
            bw.write(s);
            bw.newLine();
            bw.flush();
        }
        bw.close();
    }
}
