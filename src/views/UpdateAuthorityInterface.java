package views;

import controllers.UpDateAuthorityController;
import models.OldMan;
import models.Steward;
import models.User;
import com.google.gson.Gson;
import component.BackGroundPanel;
import dao.RWFileForOldMan;
import dao.RWFileForUser;
import utils.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

//更改权限界面
public class UpdateAuthorityInterface {
    JFrame jf = new JFrame("修改权限");

    final int WIDTH = 500;
    final int HEIGHT = 390;


    //组装视图
    public void init(String adName,int selectedRow) throws Exception {
        ArrayList<User> users = RWFileForUser.readFile();
        User user = users.get(selectedRow);
        if(user.getAuthority().equals("管理员")){
            JOptionPane.showMessageDialog(jf,"管理员不可修改","错误",JOptionPane.ERROR_MESSAGE);
            new AdministratorInterface().init(adName);
            return;

        }

        //设置窗口属性
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("images\\logo.png")));

        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File("images\\addUser.png")));
        bgPanel.setBounds(0, 0, WIDTH, HEIGHT);

        Box vBox = Box.createVerticalBox();

        //组装账号
        Box uBox=Box.createHorizontalBox();
        JLabel uLabel=new JLabel("账号：");
        JTextField uField=new JTextField(15);
        uField.setText(user.getAccount());

        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(20));
        uBox.add(uField);

        //组装密码
        Box pBox=Box.createHorizontalBox();
        JLabel pLabel=new JLabel("密码：");
        JTextField pField=new JTextField(15);
        pField.setText(user.getPassword());

        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(20));
        pBox.add(pField);

        //组装姓名
        Box nBox=Box.createHorizontalBox();
        JLabel nLabel=new JLabel("姓名：");
        JTextField nField=new JTextField(15);
        nField.setText(user.getName());


        nBox.add(nLabel);
        nBox.add(Box.createHorizontalStrut(20));
        nBox.add(nField);

        //组装性别
        Box gBox=Box.createHorizontalBox();
        JLabel gLabel=new JLabel("性别");
        JRadioButton maleBtn=new JRadioButton("男",user.getGender().equals("男"));
        JRadioButton femaleBtn=new JRadioButton("女",user.getGender().equals("女"));

        //实现单选的效果
        ButtonGroup bg=new ButtonGroup();
        bg.add(maleBtn);
        bg.add(femaleBtn);

        gBox.add(gLabel);
        gBox.add(Box.createHorizontalStrut(20));
        gBox.add(maleBtn);
        gBox.add(femaleBtn);
        gBox.add(Box.createHorizontalStrut(120));

        //组装出生日期
        Box bBox=Box.createHorizontalBox();
        JLabel bLabel=new JLabel("出生日期：");
        JTextField bField=new JTextField(15);
        bField.setText(user.getBirthDate());

        bBox.add(bLabel);
        bBox.add(Box.createHorizontalStrut(20));
        bBox.add(bField);

        //组装联系电话
        Box tBox=Box.createHorizontalBox();
        JLabel tLabel=new JLabel("联系电话：");
        JTextField tField=new JTextField(15);
        tField.setText(user.getTelephoneNumber());

        tBox.add(tLabel);
        tBox.add(Box.createHorizontalStrut(20));
        tBox.add(tField);

        //组装权限
        Box aBox = Box.createHorizontalBox();
        JLabel aLabel = new JLabel("权限：");
        JComboBox<String> authoritySelect = new JComboBox<String>();
        authoritySelect.addItem("后勤管理");
        authoritySelect.addItem("生活管家");

        aBox.add(aLabel);
        aBox.add(Box.createHorizontalStrut(20));
        aBox.add(authoritySelect);

        //组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton confirmBtn = new JButton("确认");
        JButton backBtn = new JButton("返回");
        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取用户录入的数据
                user.setAuthority(authoritySelect.getSelectedItem().toString());
                users.set(selectedRow,user);
                UpDateAuthorityController upDateAuthorityController =UpDateAuthorityController.getSingletonInstance();
                try {
                    upDateAuthorityController.upDateAuthority(user,users);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                JOptionPane.showMessageDialog(jf,"修改成功！"," ",JOptionPane.INFORMATION_MESSAGE);

                try {
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

        btnBox.add(confirmBtn);
        btnBox.add(Box.createHorizontalStrut(80));
        btnBox.add(backBtn);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(uBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(pBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(nBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(gBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(bBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(tBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(aBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(btnBox);

        bgPanel.add(vBox);

        jf.add(bgPanel);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);


    }

}
