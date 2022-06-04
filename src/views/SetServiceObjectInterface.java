package views;

import controllers.SetServiceObjectController;
import dao.RWFileForOldMan;
import dao.RWFileForSteward;
import models.OldMan;
import models.Steward;
import com.google.gson.Gson;
import component.BackGroundPanel;
import utils.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SetServiceObjectInterface {
    JFrame jf = new JFrame("设置服务对象");

    final int WIDTH = 500;
    final int HEIGHT = 300;

    public void init(String adName) throws Exception {
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("images\\logo.png")));

        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File("images\\setServiceObject.png")));
        bgPanel.setBounds(0, 0, WIDTH, HEIGHT);

        Box btnBox = Box.createHorizontalBox();
        JButton confirmBtn = new JButton("确认");
        JButton backBtn = new JButton("返回");
        btnBox.add(confirmBtn);
        btnBox.add(Box.createHorizontalStrut(100));
        btnBox.add(backBtn);

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AdministratorInterface().init(adName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jf.dispose();
            }
        });

        Box sBox = Box.createHorizontalBox();
        JLabel sLabel = new JLabel("生活管家");
        JComboBox<String> stewardSelect = new JComboBox<String>();

        JLabel oLabel = new JLabel("顾客");
        JComboBox<String> oldManSelect = new JComboBox<String>();

        ArrayList<Steward> stewards = RWFileForSteward.readFile();

        for (Steward steward : stewards) {
            String s = steward.getName() + "(" + steward.getAccount() + ")";
            stewardSelect.addItem(s);
        }

        ArrayList<OldMan> oldMEN = RWFileForOldMan.readFile();

        for (OldMan oldMan : oldMEN) {
            if (oldMan.getStewardAccount() .equals("")) {
                String s = oldMan.getName() + "(" + oldMan.getAccount() + ")";
                oldManSelect.addItem(s);
            }
        }

        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = (String) stewardSelect.getSelectedItem();
                String s2 = (String) oldManSelect.getSelectedItem();
                SetServiceObjectController setServiceObjectController = new SetServiceObjectController();
                setServiceObjectController.setServiceObjectController(s,s2,stewards,oldMEN);

                JOptionPane.showMessageDialog(jf,"设置成功","",JOptionPane.INFORMATION_MESSAGE);
                try {
                    new AdministratorInterface().init(adName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jf.dispose();

            }
        });


        sBox.add(sLabel);
        sBox.add(Box.createHorizontalStrut(20));
        sBox.add(stewardSelect);
        sBox.add(Box.createHorizontalStrut(40));
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

    }

//    public static void main(String[] args) throws Exception {
//        new SetServiceObjectInterface().init();
//    }




}
