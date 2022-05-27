package ui;

import Actors.Bus;
import Actors.OldMan;
import Actors.Steward;
import com.google.gson.Gson;
import component.BackGroundPanel;
import util.CheckTime;
import util.RWFileForBus;
import util.RWFileForOldMan;
import util.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SetPassengerInterface {
    JFrame jf = new JFrame("添加乘客");

    final int WIDTH = 500;
    final int HEIGHT = 300;

    public void init(String bmName,int selectedRow) throws Exception {
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("images\\logo.png")));

        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File("images\\addBus.png")));
        bgPanel.setBounds(0, 0, WIDTH, HEIGHT);

        Box btnBox = Box.createHorizontalBox();
        JButton confirmBtn = new JButton("确认");
        JButton backBtn = new JButton("返回");
        btnBox.add(confirmBtn);
        btnBox.add(Box.createHorizontalStrut(100));
        btnBox.add(backBtn);


        Box sBox = Box.createHorizontalBox();

        JLabel oLabel = new JLabel("顾客");
        JComboBox<String> oldManSelect = new JComboBox<String>();

        ArrayList<OldMan> oldMEN = loadVoidOldMan(selectedRow);

        for (OldMan oldMan : oldMEN) {
            String s = oldMan.getName() + "(" + oldMan.getAccount() + ")";
            oldManSelect.addItem(s);

        }


        sBox.add(oLabel);
        sBox.add(Box.createHorizontalStrut(20));
        sBox.add(oldManSelect);

        Box vBox = Box.createVerticalBox();
        vBox.add(Box.createVerticalStrut(80));
        vBox.add(sBox);
        vBox.add(Box.createVerticalStrut(50));
        vBox.add(btnBox);

        bgPanel.add(vBox);

        jf.add(bgPanel);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);

        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s2 = (String) oldManSelect.getSelectedItem();
                StringTokenizer st3 = new StringTokenizer(s2, "(");
                String c2 = st3.nextToken();
                String account00 = st3.nextToken();
                StringTokenizer st4 = new StringTokenizer(account00, ")");
                String oldManAccount = st4.nextToken();

                try {
                    ArrayList<Bus> buses = RWFileForBus.readFile();
                    Bus bus = buses.get(selectedRow);

                    addPassengerNumber(bus);
                    bus.addPassengers(oldManAccount);
                    RWFileForBus.writeFile(buses);

                    JOptionPane.showMessageDialog(jf, "添加乘客成功！", " ", JOptionPane.INFORMATION_MESSAGE);
                    new BusManagerInterface().init(bmName);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                jf.dispose();

            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new BusManagerInterface().init(bmName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jf.dispose();
            }
        });

    }


    public static ArrayList<OldMan> loadVoidOldMan(int selectedRow) throws IOException {
        ArrayList<Bus> buses = RWFileForBus.readFile();
        Bus bus = buses.get(selectedRow);

        ArrayList<String> passengers = bus.getPassengers();
        ArrayList<OldMan> oldMEN = RWFileForOldMan.readFile();

        if (passengers.size() == 0) {
            return oldMEN;
        } else {
            for (String account : passengers) {
                for (int i = 0; i < oldMEN.size(); i++) {
                    OldMan oldMan = oldMEN.get(i);
                    if (oldMan.getAccount().equals(account)) {
                        oldMEN.remove(i);
                    }
                }
            }
        }
        return oldMEN;

    }

    public void addPassengerNumber(Bus bus) {
        String reservedNumber = bus.getReservedNumber();
        int num = Integer.parseInt(reservedNumber);
        num++;

        bus.setReservedNumber(String.valueOf(num));

    }
}
