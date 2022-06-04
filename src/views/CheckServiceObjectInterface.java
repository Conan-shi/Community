package views;

import dao.LoadOldManData;
import utils.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

public class CheckServiceObjectInterface {
    JFrame jf = new JFrame("查看服务对象");

    final int WIDTH = 600;
    final int HEIGHT = 400;

    public void init(String name,int selectedRow)throws Exception{
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("images\\logo.png")));

        Box vBox = Box.createVerticalBox();
        JPanel btnPanel1 = new JPanel();

        btnPanel1.setMaximumSize(new Dimension(WIDTH, 80));
        btnPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton returnBtn = new JButton("返回");
        btnPanel1.add(returnBtn);
        returnBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new AdministratorInterface().init(name);
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


        vBox.add(jScrollPane);
        vBox.add(btnPanel1);

        jf.add(vBox);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);

    }

}
