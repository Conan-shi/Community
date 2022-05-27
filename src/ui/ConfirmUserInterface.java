package ui;

import Actors.OldMan;
import Actors.Steward;
import Actors.User;
import com.google.gson.Gson;
import util.DelUser;
import util.RWFileForOldMan;
import util.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import static ui.SetServiceObjectInterface.readFile;

public class ConfirmUserInterface {
    JFrame jf = new JFrame("警告");

    final int WIDTH = 300;
    final int HEIGHT = 200;

    public void init(String adName,int selectedRow,String account)throws IOException {

        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("images\\logo.png")));

        JPanel bottomPanel=new JPanel();
        bottomPanel.setBounds(0, 0, WIDTH, HEIGHT);

        Box btnBox = Box.createHorizontalBox();
        JButton confirmBtn = new JButton("确认");
        JButton backBtn = new JButton("返回");
        btnBox.add(confirmBtn);
        btnBox.add(Box.createHorizontalStrut(100));
        btnBox.add(backBtn);

        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader("files\\usersMessage"));
                    ArrayList<User> users = new ArrayList<>();
                    Gson gson = new Gson();
                    String line;
                    while ((line = br.readLine()) != null) {
                        User user = gson.fromJson(line, User.class);
                        users.add(user);
                    }
                    br.close();

                    delete(users,selectedRow);
                    JOptionPane.showMessageDialog(jf, "删除成功", " ", JOptionPane.INFORMATION_MESSAGE);
                    new AdministratorInterface().init(adName);

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
                    new AdministratorInterface().init(adName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jf.dispose();
            }
        });

        JPanel labelPanel=new JPanel();

        JLabel oLabel = new JLabel("是否确认删除账号为："+account+" 的用户");
        labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        Box hBox = Box.createHorizontalBox();


        labelPanel.add(oLabel);



        Box vBox = Box.createVerticalBox();

        vBox.add(Box.createVerticalStrut(30));
        vBox.add(labelPanel);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(btnBox);

        bottomPanel.add(vBox);

        jf.add(bottomPanel);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);

    }

    public void delete(ArrayList<User> users, int selectedRow) throws Exception {
        User selectedUser = users.get(selectedRow);
        ArrayList delUser = DelUser.delUser(users, selectedRow);
        Gson gson=new Gson();

        BufferedWriter bw = new BufferedWriter(new FileWriter("files\\usersMessage"));
        for (Object user : delUser) {
            String s = gson.toJson(user);
            bw.write(s);
            bw.newLine();
            bw.flush();
        }
        bw.close();

        if (selectedUser.getAuthority().equals("生活管家")) {
            ArrayList<Steward> stewards = readFile();
            for (int i = 0; i < stewards.size(); i++) {
                Steward steward = stewards.get(i);
                if (steward.getAccount().equals(selectedUser.getAccount())) {
                    stewards.remove(i);
                }
            }
            writeFile(stewards);

            ArrayList<OldMan> oldMEN = RWFileForOldMan.readFile();

            for(OldMan oldMan:oldMEN){
                if(oldMan.getStewardAccount().equals(selectedUser.getAccount())){
                    oldMan.setStewardAccount("");
                }
            }

            BufferedWriter bw2=new BufferedWriter(new FileWriter("files\\oldManMessage"));
            for(OldMan oldMan:oldMEN){
                String s = gson.toJson(oldMan);
                bw2.write(s);
                bw2.newLine();
                bw2.flush();
            }
            bw2.close();
        }



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
