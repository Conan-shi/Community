package views;

import controllers.DelUserController;
import models.OldMan;
import models.Steward;
import models.User;
import com.google.gson.Gson;
import utils.DelUser;
import dao.RWFileForOldMan;
import utils.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

//确认删除用户界面
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
                try {
                    DelUserController delUserController = DelUserController.getSingletonInstance();
                    delUserController.delUser(selectedRow);

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





}
