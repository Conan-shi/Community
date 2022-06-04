package views;

import controllers.SetDdlController;
import models.Bus;
import utils.Check;
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

public class SetDDLInterface {
    JFrame jf = new JFrame("设置截止时间");

    final int WIDTH = 300;
    final int HEIGHT = 200;

    public void init(String bmName,int selectedRow)throws IOException{
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

        JPanel labelPanel=new JPanel();

        Box dBox = Box.createHorizontalBox();
        JLabel ddlLabel = new JLabel("截止时间");
        JTextField dField = new JTextField(10);
        dField.setToolTipText("例：13:50,15:50");


        labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER));


        labelPanel.add(ddlLabel);
        labelPanel.add(Box.createHorizontalStrut(20));
        labelPanel.add(dField);



        Box vBox = Box.createVerticalBox();

        vBox.add(Box.createVerticalStrut(30));
        vBox.add(labelPanel);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(btnBox);

        bottomPanel.add(vBox);

        jf.add(bottomPanel);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);

        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ddl=dField.getText().trim();
                try {
                    ArrayList<Bus> buses = RWFileForBus.readFile();
                    Bus bus = buses.get(selectedRow);

                    SetDdlController setDdlController = new SetDdlController();
                    if(setDdlController.setDdl(ddl,selectedRow)){
                        JOptionPane.showMessageDialog(jf,"设置成功！"," ",JOptionPane.INFORMATION_MESSAGE);
                        new BusManagerInterface().init(bmName);

                    }else {
                        JOptionPane.showMessageDialog(jf,"输入格式有误，请重新输入","错误提示",JOptionPane.ERROR_MESSAGE);
                        return;
                    }

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





}
