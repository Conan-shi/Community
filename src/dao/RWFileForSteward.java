package dao;

import com.google.gson.Gson;
import models.Steward;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RWFileForSteward {
    public static ArrayList<Steward> readFile()throws IOException {
        BufferedReader br=new BufferedReader(new FileReader("files\\ServiceObjectMessage"));
        ArrayList<Steward> stewards = new ArrayList<>();
        Gson gson=new Gson();
        String line;
        while((line=br.readLine())!=null){
            Steward steward = gson.fromJson(line, Steward.class);
            stewards.add(steward);
        }

        br.close();
        return stewards;

    }
}
