package views;

import controllers.DelPassengerController;
import models.Bus;
import dao.RWFileForBus;
import utils.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//确认删除乘客界面
public class ConfirmPassengerInterface {
    JFrame jf = new JFrame("警告");

    final int WIDTH = 300;
    final int HEIGHT = 200;

    public void init(String bmName,int selectedRow,int selectedPassengerRow,String account)throws IOException {

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
                    DelPassengerController delPassengerController = DelPassengerController.getSingletonInstance();
                    delPassengerController.delPassenger(selectedRow,selectedPassengerRow);

                    JOptionPane.showMessageDialog(jf, "删除成功", " ", JOptionPane.INFORMATION_MESSAGE);
                    new CheckPassengerInterface().init(bmName,selectedRow);

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
                    new CheckPassengerInterface().init(bmName,selectedRow);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jf.dispose();
            }
        });

        JPanel labelPanel=new JPanel();

        JLabel oLabel = new JLabel("是否确认删除账号为："+account+" 的乘客");
        labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));


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
