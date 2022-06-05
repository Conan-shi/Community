package views;

import controllers.UpDateOldManController;
import models.OldMan;
import models.Steward;
import com.google.gson.Gson;
import component.BackGroundPanel;
import utils.Check;
import utils.ReadFile;
import utils.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class UpdateOldManInterface {
    JFrame jf = new JFrame("顾客信息修改");

    final int WIDTH = 500;
    final int HEIGHT = 390;


    //组装视图
    public void init(String sName,int selectedRow, String stewardAccount) throws Exception {
        OldMan oldMan = ReadFile.getOldMan(stewardAccount, selectedRow);

        //设置窗口属性
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("images\\logo.png")));

        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File("images\\addUser.png")));
        bgPanel.setBounds(0, 0, WIDTH, HEIGHT);

        Box vBox = Box.createVerticalBox();

        //组装姓名
        Box nBox = Box.createHorizontalBox();
        JLabel nLabel = new JLabel("姓名：");
        JTextField nField = new JTextField(15);
        nField.setText(oldMan.getName());


        nBox.add(nLabel);
        nBox.add(Box.createHorizontalStrut(20));
        nBox.add(nField);

        //组装性别
        Box gBox = Box.createHorizontalBox();
        JLabel gLabel = new JLabel("性别");
        JRadioButton maleBtn = new JRadioButton("男", oldMan.getGender().equals("男"));
        JRadioButton femaleBtn = new JRadioButton("女", oldMan.getGender().equals("女"));

        //实现单选的效果
        ButtonGroup bg = new ButtonGroup();
        bg.add(maleBtn);
        bg.add(femaleBtn);

        gBox.add(gLabel);
        gBox.add(Box.createHorizontalStrut(20));
        gBox.add(maleBtn);
        gBox.add(femaleBtn);
        gBox.add(Box.createHorizontalStrut(120));

        //组装出生日期
        Box bBox = Box.createHorizontalBox();
        JLabel bLabel = new JLabel("出生日期：");
        JTextField bField = new JTextField(15);
        bField.setText(oldMan.getBirthDate());

        bBox.add(bLabel);
        bBox.add(Box.createHorizontalStrut(20));
        bBox.add(bField);

        //组装联系电话
        Box tBox = Box.createHorizontalBox();
        JLabel tLabel = new JLabel("联系电话：");
        JTextField tField = new JTextField(15);
        tField.setText(oldMan.getTelephoneNumber());

        tBox.add(tLabel);
        tBox.add(Box.createHorizontalStrut(20));
        tBox.add(tField);

        //组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton confirmBtn = new JButton("确认");
        JButton backBtn = new JButton("返回");

        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = oldMan.getAccount();
                String name = nField.getText().trim();
                String gender = bg.isSelected(maleBtn.getModel()) ? maleBtn.getText() : femaleBtn.getText();
                String birthDate = bField.getText().trim();
                String telephoneNUmber = tField.getText().trim();

                OldMan oldMan = new OldMan("", account, name, gender, birthDate, telephoneNUmber);

                try {
                    if (!Check.checkForm(oldMan)) {
                        JOptionPane.showMessageDialog(jf, "输入的信息格式有误，请重新输入", "错误提示", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    UpDateOldManController upDateOldManController = UpDateOldManController.getSingletonInstance();
                    upDateOldManController.upDateOldMan(oldMan);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(jf, "修改成功！", " ", JOptionPane.INFORMATION_MESSAGE);

                try {
                    new StewardInterface().init(sName,stewardAccount);
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
                    new StewardInterface().init(sName,stewardAccount);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jf.dispose();
            }
        });

        btnBox.add(confirmBtn);
        btnBox.add(Box.createHorizontalStrut(80));
        btnBox.add(backBtn);

        vBox.add(Box.createVerticalStrut(60));
        vBox.add(nBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(gBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(bBox);
        vBox.add(Box.createVerticalStrut(15));
        vBox.add(tBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(btnBox);

        bgPanel.add(vBox);

        jf.add(bgPanel);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);


    }


}


