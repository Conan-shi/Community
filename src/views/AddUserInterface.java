package views;

import controllers.AddUserController;
import models.Steward;
import models.User;
import com.google.gson.Gson;
import component.BackGroundPanel;
import utils.Check;
import utils.ReadFile;
import utils.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddUserInterface {
    JFrame jf = new JFrame("新增用户");

    final int WIDTH = 500;
    final int HEIGHT = 390;

    //组装视图
    public void init(String adName) throws Exception {
        //设置窗口属性
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("images\\logo.png")));

        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File("images\\addUser.png")));
        bgPanel.setBounds(0, 0, WIDTH, HEIGHT);

        Box vBox = Box.createVerticalBox();

        //组装账号
        Box uBox=Box.createHorizontalBox();
        JLabel uLabel=new JLabel("账号：(必填)");
        JTextField uField=new JTextField(15);
        uField.setToolTipText("6-18位英文字母数字");

        uBox.add(uLabel);
        uBox.add(Box.createHorizontalStrut(20));
        uBox.add(uField);

        //组装密码
        Box pBox=Box.createHorizontalBox();
        JLabel pLabel=new JLabel("密码：(必填)");
        JTextField pField=new JTextField(15);
        pField.setToolTipText("8-18位字符，含有大写字母、小写字母、数字、特殊符号");


        pBox.add(pLabel);
        pBox.add(Box.createHorizontalStrut(20));
        pBox.add(pField);

        //组装姓名
        Box nBox=Box.createHorizontalBox();
        JLabel nLabel=new JLabel("姓名：(必填)");
        JTextField nField=new JTextField(15);
        nField.setToolTipText("1-10位英文字母汉字数字");



        nBox.add(nLabel);
        nBox.add(Box.createHorizontalStrut(20));
        nBox.add(nField);

        //组装性别
        Box gBox=Box.createHorizontalBox();
        JLabel gLabel=new JLabel("性别");
        JRadioButton maleBtn=new JRadioButton("男",true);
        JRadioButton femaleBtn=new JRadioButton("女",false);

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
        bField.setToolTipText("年-月-日");

        bBox.add(bLabel);
        bBox.add(Box.createHorizontalStrut(20));
        bBox.add(bField);

        //组装联系电话
        Box tBox=Box.createHorizontalBox();
        JLabel tLabel=new JLabel("联系电话：");
        JTextField tField=new JTextField(15);
        tField.setToolTipText("5-20位数字");

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
                String account=uField.getText().trim();
                String password=pField.getText().trim();
                String name=nField.getText().trim();
                String gender=bg.isSelected(maleBtn.getModel())?maleBtn.getText():femaleBtn.getText();
                String birthDate=bField.getText().trim();
                String telephoneNUmber=tField.getText().trim();
                String authority=authoritySelect.getSelectedItem().toString();

                User user=new User(account,password,name,gender,birthDate,telephoneNUmber,authority);

                try {
                    if(!Check.ifAccountIsUsed(user)){
                        JOptionPane.showMessageDialog(jf,"账户已存在"," 错误提示",JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        if(!Check.checkForm(user)){
                            JOptionPane.showMessageDialog(jf,"输入的信息格式有误，请重新输入","错误提示",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                AddUserController addUserController =AddUserController.getSingletonInstance();
                try {
                    addUserController.addUser(user);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                JOptionPane.showMessageDialog(jf,"添加成功！"," ",JOptionPane.INFORMATION_MESSAGE);

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



//    public static void main(String[] args) throws Exception {
//        new AddUserInterface().init();
//    }
}