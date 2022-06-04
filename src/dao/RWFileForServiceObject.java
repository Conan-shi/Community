package dao;

import com.google.gson.Gson;
import models.Steward;

import java.io.*;
import java.util.ArrayList;

public class RWFileForServiceObject {

    public void upDateFile(Steward steward) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("files\\ServiceObjectMessage", true));
        Gson gson = new Gson();
        String s = gson.toJson(steward);
        bw.write(s);
        bw.newLine();
        bw.flush();
        bw.close();
    }

    public ArrayList<Steward> readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("files\\ServiceObjectMessage"));
        ArrayList<Steward> stewards = new ArrayList<>();

        Gson gson = new Gson();
        String line;
        while ((line = br.readLine()) != null) {
            Steward steward = gson.fromJson(line, Steward.class);
            stewards.add(steward);
        }
        br.close();
        return stewards;
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
