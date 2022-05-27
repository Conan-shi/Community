package util;

import Actors.Bus;
import Actors.User;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;

public class RWFileForBus {
    public static ArrayList<Bus> readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("files\\busMessage"));

        ArrayList<Bus> buses = new ArrayList<Bus>();
        Gson gson = new Gson();

        String line;
        while ((line = br.readLine()) != null) {
            Bus bus = gson.fromJson(line, Bus.class);
            buses.add(bus);
        }
        br.close();
        return buses;
    }

    public static void writeFile(ArrayList<Bus> buses) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("files\\busMessage"));
        Gson gson = new Gson();
        for (Bus bus : buses) {
            String s = gson.toJson(bus);
            bw.write(s);
            bw.newLine();
            bw.flush();
        }
        bw.close();
    }

    public static void updateFile(Bus bus) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("files\\busMessage", true));
        Gson gson = new Gson();
        String s = gson.toJson(bus);
        bw.write(s);
        bw.newLine();
        bw.flush();

        bw.close();
    }
}
