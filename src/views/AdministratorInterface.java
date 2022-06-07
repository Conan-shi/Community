package views;

import dao.LoadUsersData;
import dao.RWFileForUser;
import models.User;
import utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

//管理员界面
public class AdministratorInterface {
    JFrame jf = new JFrame();

    final int WIDTH = 600;
    final int HEIGHT = 400;


    //组装视图
    public void init(String name) throws Exception {
        jf.setTitle("活力长者社区：管理员 "+name+" 欢迎您");

        //给窗口设置属性
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

        JButton addOldBtn = new JButton("    添加老人   ");
        addOldBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AddOldInterface().init(name);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jf.dispose();
            }
        });

        btnPanel1.add(addOldBtn);

        JPanel btnPanel2 = new JPanel();

        btnPanel2.setMaximumSize(new Dimension(WIDTH, 80));

        JButton addUserBtn = new JButton("新增用户");
        JButton delUserBtn = new JButton("删除用户");
        JButton checkServiceObjectBtn = new JButton("查看服务对象");
        JButton setSerViceObjectBtn = new JButton("设置服务对象");


        addUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AddUserInterface().init(name);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jf.dispose();
            }
        });

        setSerViceObjectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!Check.checkHaveSteward()){
                        JOptionPane.showMessageDialog(jf, "没有生活管家，无法设置服务对象", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if(Check.checkIfIsFull()){
                        JOptionPane.showMessageDialog(jf, "无可设置服务对象的老人", "错误", JOptionPane.ERROR_MESSAGE);
                    }else {
                        new SetServiceObjectInterface().init(name);
                        jf.dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        btnPanel2.add(addUserBtn);
        btnPanel2.add(Box.createHorizontalStrut(10));
        btnPanel2.add(delUserBtn);
        btnPanel2.add(Box.createHorizontalStrut(10));
        btnPanel2.add(checkServiceObjectBtn);
        btnPanel2.add(Box.createHorizontalStrut(150));
        btnPanel2.add(setSerViceObjectBtn);

        JButton changeAuthorityBtn = new JButton("更改权限");
        JButton exitBtn = new JButton("退出");

        exitBtn.addActionListener(new ActionListener() {
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

        JPanel btnPanel3 = new JPanel();

        btnPanel3.setMaximumSize(new Dimension(WIDTH, 80));
        btnPanel3.add(changeAuthorityBtn);
        btnPanel3.add(Box.createHorizontalStrut(300));
        btnPanel3.add(exitBtn);

        //组装表格
        String[] ts = {" ", "ID", "账户", "姓名", "性别", "出生日期", "电话", "权限"};
        Vector<String> titles = new Vector<>();
        for (String title : ts) {
            titles.add(title);
        }

        Vector<Vector> tableData = LoadUsersData.loadUsersData();

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

        TableColumn firstColumn = table.getColumnModel().getColumn(0);
        firstColumn.setPreferredWidth(30);

        TableColumn secondColumn = table.getColumnModel().getColumn(1);
        secondColumn.setPreferredWidth(30);

        TableColumn fifthColumn = table.getColumnModel().getColumn(4);
        fifthColumn.setPreferredWidth(30);



        //设置只能选中一行
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setMaximumSize(new Dimension(WIDTH, 1000));

        delUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(jf, "请选择用户", "错误", JOptionPane.ERROR_MESSAGE);
                } else {

                    try {

                        ArrayList<User> users = RWFileForUser.readFile();
                        User selectedUser = users.get(selectedRow);

                        String account=selectedUser.getAccount();
                        new ConfirmUserInterface().init(name,selectedRow,account);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    jf.dispose();

                }
            }
        });

        changeAuthorityBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(jf, "请选择用户", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        new UpdateAuthorityInterface().init(name,selectedRow);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    jf.dispose();
                }


            }
        });

        checkServiceObjectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    int selectedRow = table.getSelectedRow();
                    if(selectedRow==-1){
                        JOptionPane.showMessageDialog(jf, "请选择用户", "错误", JOptionPane.ERROR_MESSAGE);
                    }else {
                        ArrayList<User> users = RWFileForUser.readFile();
                        User user = users.get(selectedRow);
                        if(!user.getAuthority().equals("生活管家")){
                            JOptionPane.showMessageDialog(jf, "请选择生活管家", "错误", JOptionPane.ERROR_MESSAGE);
                        }else {
                            new CheckServiceObjectInterface().init(name,selectedRow);
                            jf.dispose();
                        }

                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        });

        vBox.add(btnPanel1);
        vBox.add(btnPanel2);
        vBox.add(jScrollPane);
        vBox.add(btnPanel3);

        jf.add(vBox);


        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);


    }

//    public static void main(String[] args) throws Exception {
//        new AdministratorInterface().init();
//    }



}
