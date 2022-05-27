package ui;

import Actors.Bus;
import Actors.OldMan;
import util.RWFileForBus;
import util.RWFileForOldMan;
import util.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class CheckPassengerInterface {
    JFrame jf = new JFrame("查看乘客信息");

    final int WIDTH = 600;
    final int HEIGHT = 400;

    public void init(String bmName,int selectedRow)throws Exception{
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("images\\logo.png")));

        Box vBox = Box.createVerticalBox();
        JPanel btnPanel1 = new JPanel();

        btnPanel1.setMaximumSize(new Dimension(WIDTH, 80));
        btnPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton delBtn = new JButton("删除乘客");
        JButton returnBtn = new JButton("返回");
        btnPanel1.add(delBtn);
        btnPanel1.add(Box.createHorizontalStrut(200));
        btnPanel1.add(returnBtn);

        String[] ts = {" ", "ID","账号","姓名", "性别", "出生日期", "电话"};
        Vector<String> titles = new Vector<>();
        for (String title : ts) {
            titles.add(title);
        }

        Vector<Vector> tableData =loadOldManData(selectedRow);

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


        vBox.add(jScrollPane);
        vBox.add(btnPanel1);

        jf.add(vBox);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);

        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedPassengerRow = table.getSelectedRow();
                if (selectedPassengerRow == -1) {
                    JOptionPane.showMessageDialog(jf, "请选择乘客", "错误", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        ArrayList<Bus> buses = RWFileForBus.readFile();
                        Bus bus = buses.get(selectedRow);
                        ArrayList<String> passengers = bus.getPassengers();
                        String account = passengers.get(selectedPassengerRow);

                        new ConfirmPassengerInterface().init(bmName,selectedRow,selectedPassengerRow,account);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    jf.dispose();

                }
            }
        });

        returnBtn.addActionListener(new ActionListener() {
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

    public static Vector<Vector> loadOldManData(int selectedRow) throws Exception {
        ArrayList<Bus> buses = RWFileForBus.readFile();

        Bus bus = buses.get(selectedRow);
        ArrayList<String> passengers = bus.getPassengers();
        ArrayList<OldMan> allOldMEN = RWFileForOldMan.readFile();
        ArrayList<OldMan> oldMEN = new ArrayList<>();

        for(OldMan oldMan:allOldMEN){
            for(String account:passengers){
                if(oldMan.getAccount().equals(account)){
                    oldMEN.add(oldMan);
                }
            }

        }


        Vector<Vector> tableData = new Vector<>();

        for (OldMan oldMan : oldMEN) {
            Vector<Object> oldManData = new Vector<>();
            oldManData.add(" ");
            oldManData.add(oldMan.getId());
            oldManData.add(oldMan.getAccount());
            oldManData.add(oldMan.getName());
            oldManData.add(oldMan.getGender());
            oldManData.add(oldMan.getBirthDate());
            oldManData.add(oldMan.getTelephoneNumber());

            tableData.add(oldManData);
        }
        return tableData;
    }

}
