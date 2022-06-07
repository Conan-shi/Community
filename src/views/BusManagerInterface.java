package views;

import dao.LoadBusData;
import dao.RWFileForBus;
import models.Bus;
import utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

//后勤管理界面
public class BusManagerInterface {
    JFrame jf = new JFrame();

    final int WIDTH = 600;
    final int HEIGHT = 400;

    //组装视图
    public void init(String bmName) throws Exception {
        jf.setTitle("活力长者社区：后勤管理 "+bmName+" 欢迎您");

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

        JButton addBusBtn = new JButton("    新建班车   ");

        btnPanel1.add(addBusBtn);

        JPanel btnPanel2 = new JPanel();

        btnPanel2.setMaximumSize(new Dimension(WIDTH, 80));

        JButton delBusBtn = new JButton("删除班车");
        JButton setDDLBtn = new JButton("设置截止时间");
        JButton reserveBtn = new JButton("预约登记");
        JButton checkPassengerBtn = new JButton("查看乘客信息");

        btnPanel2.add(delBusBtn);
        btnPanel2.add(Box.createHorizontalStrut(10));
        btnPanel2.add(setDDLBtn);
        btnPanel2.add(Box.createHorizontalStrut(10));
        btnPanel2.add(reserveBtn);
        btnPanel2.add(Box.createHorizontalStrut(150));
        btnPanel2.add(checkPassengerBtn);

        JButton exitBtn = new JButton("退出");

        JPanel btnPanel3 = new JPanel();

        btnPanel3.setMaximumSize(new Dimension(WIDTH, 80));
        btnPanel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnPanel3.add(exitBtn);

        //组装表格
        String[] ts = {" ", "ID", "线路代码", "线路名称", "方向", "线路类型","运营日期", "运营时段", "发车时间","截止时间","预约人数","备注"};
        Vector<String> titles = new Vector<>();
        for (String title : ts) {
            titles.add(title);
        }

        Vector<Vector> tableData = LoadBusData.loadBusesData();

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

        TableColumn firsetColumn = table.getColumnModel().getColumn(0);
        firsetColumn.setPreferredWidth(30);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        //设置只能选中一行
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane jScrollPane = new JScrollPane(table);
        jScrollPane.setMaximumSize(new Dimension(WIDTH, 1000));

        addBusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AddBusInterface().init(bmName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jf.dispose();
            }
        });

        delBusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(jf, "请选择班车", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        ArrayList<Bus> buses = RWFileForBus.readFile();
                        Bus bus = buses.get(selectedRow);
                        new ConfirmBusInterface().init(bmName,selectedRow,bus.getRoutId());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    jf.dispose();

                }

            }
        });

        setDDLBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(jf, "请选择班车", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        new SetDDLInterface().init(bmName,selectedRow);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    jf.dispose();

                }
            }
        });

        reserveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(jf, "请选择班车", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        ArrayList<Bus> buses = RWFileForBus.readFile();
                        Bus bus = buses.get(selectedRow);
                        if(bus.getDdl().equals("未设置")){
                            JOptionPane.showMessageDialog(jf, "班车预约截止时间未设置", "错误", JOptionPane.ERROR_MESSAGE);
                            return;
                        }else {
                            if(CheckTime.checkTime(bus)){
                                new SetPassengerInterface().init(bmName,selectedRow);
                            }else {
                                JOptionPane.showMessageDialog(jf, "不为运营时间或已过截止时间", "错误", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    jf.dispose();

                }

            }
        });

        checkPassengerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(jf, "请选择班车", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        new CheckPassengerInterface().init(bmName,selectedRow);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    jf.dispose();
                }


            }
        });

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

        vBox.add(btnPanel1);
        vBox.add(btnPanel2);
        vBox.add(jScrollPane);
        vBox.add(btnPanel3);

        jf.add(vBox);


        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);


    }

//    public static void main(String[] args) throws Exception {
//        new BusManagerInterface().init();
//    }

}
