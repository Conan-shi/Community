package views;

import dao.RWFileForUser;
import models.User;
import com.google.gson.Gson;
import dao.LoadOldManData;
import utils.GetSelectedRow;
import utils.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

//生活管家界面
public class StewardInterface {
    JFrame jf = new JFrame();

    final int WIDTH = 600;
    final int HEIGHT = 400;

    public void init(String sName,String account)throws Exception{
        jf.setTitle("活力长者社区： 生活管家 "+sName+" 欢迎您");

        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("images\\logo.png")));

        //设置菜单栏
        JMenuBar jmb = new JMenuBar();
        JMenu jMenu = new JMenu("设置");
        JMenuItem m1 = new JMenuItem("切换账号");
        JMenuItem m2 = new JMenuItem("退出程序");
        m1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new MainInterface().init();
                    jf.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        m2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        jMenu.add(m1);
        jMenu.add(m2);
        jmb.add(jMenu);
        jf.setJMenuBar(jmb);

        Box vBox = Box.createVerticalBox();

        JPanel btnPanel1 = new JPanel();
        btnPanel1.setMaximumSize(new Dimension(WIDTH, 80));
        btnPanel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton upDateOldManBtn = new JButton("修改顾客信息");

        btnPanel1.add(upDateOldManBtn);

        JPanel btnPanel2 = new JPanel();
        btnPanel2.setMaximumSize(new Dimension(WIDTH, 80));
        btnPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton returnBtn = new JButton("退出");
        btnPanel2.add(returnBtn);

        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new MainInterface().init();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jf.dispose();
            }
        });

        String[] ts = {" ", "ID","姓名", "性别", "出生日期", "电话"};
        Vector<String> titles = new Vector<>();
        for (String title : ts) {
            titles.add(title);
        }

        int selectedRow= GetSelectedRow.getSelectedRow(account);
        Vector<Vector> tableData = LoadOldManData.loadOldManData(selectedRow);

        TableModel tableModel = new DefaultTableModel(tableData, titles);
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.getColumnModel().getColumn(0).setCellRenderer((table1, value, isSelected, hasFocus, row, column) -> {
            JCheckBox jck = new JCheckBox();
            jck.setSelected(isSelected);
            return jck;
        });


        //设置只能选中一行
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setMaximumSize(new Dimension(WIDTH, 1000));

        upDateOldManBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow==-1){
                    JOptionPane.showMessageDialog(jf, "请选择顾客", "错误", JOptionPane.ERROR_MESSAGE);
                }else {
                    try {
                        new UpdateOldManInterface().init(sName,selectedRow,account);
                        jf.dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        vBox.add(btnPanel1);
        vBox.add(jScrollPane);
        vBox.add(btnPanel2);

        jf.add(vBox);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);

    }



}