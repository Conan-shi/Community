package views;

import models.User;
import component.BackGroundPanel;
import controllers.CheckIn;
import controllers.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MainInterface {
    JFrame jf=new JFrame("活力长者社区");

    final int WIDTH=500;
    final int HEIGHT=300;

    //组装视图
    public void init() throws Exception{
        //设置窗口相关的属性
        jf.setBounds((ScreenUtils.getScreenWidth()-WIDTH)/2,(ScreenUtils.getScreenHeight()-HEIGHT)/2,WIDTH,HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("images\\logo.png")));

        //设置窗口的内容
        BackGroundPanel bgPanel=new BackGroundPanel(ImageIO.read(new File("images\\dengLu.png")));

        //组装登录相关的元素
        Box vBox=Box.createVerticalBox();

        //组装用户名
        Box uBox=Box.createHorizontalBox();
        JLabel uLabel=new JLabel("账户");
        JTextField uField=new JTextField(15);

        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(20));
        uBox.add(uField);

        //组装密码
        Box pBox=Box.createHorizontalBox();
        JLabel pLabel=new JLabel("密码");
        JTextField pField=new JTextField(15);

        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(20));
        pBox.add(pField);

        //组装按钮
        Box btnBox=Box.createHorizontalBox();
        JButton loginBtn=new JButton("登录");
        JButton registerBtn=new JButton("管理员注册");

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取用户输入参数
                String account=uField.getText().trim();
                String password=pField.getText().trim();

                try {
                    if(CheckIn.checkIn(account,password)){
                        User user = CheckIn.getUser(account);
                        switch (user.getAuthority()){
                            case "管理员":
                                new AdministratorInterface().init(user.getName());
                                jf.dispose();
                                break;
                            case "生活管家":
                                new StewardInterface().init(user.getName(),user.getAccount());
                                jf.dispose();
                                break;
                            case "后勤管理":
                                new BusManagerInterface().init(user.getName());
                                jf.dispose();
                                break;
                        }

                    }else {
                        JOptionPane.showMessageDialog(jf,"账号或密码错误，请重新输入","错误",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //跳转到注册界面
                try {
                    new AddAdministratorInterface().init();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                //当前界面消失
                jf.dispose();

            }
        });

        btnBox.add(loginBtn);
        btnBox.add(Box.createHorizontalStrut(100));
        btnBox.add(registerBtn);

        vBox.add(Box.createVerticalStrut(50));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(40));
        vBox.add(btnBox);

        bgPanel.add(vBox);
        jf.add(bgPanel);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);


    }

    //客户端程序的入口
    public static void main(String[] args) throws Exception {
        new MainInterface().init();
    }

}
