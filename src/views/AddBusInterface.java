package views;

import controllers.AddBusController;
import models.Bus;
import models.Steward;
import com.google.gson.Gson;
import component.BackGroundPanel;
import utils.Check;
import dao.RWFileForBus;
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

public class AddBusInterface {
    JFrame jf = new JFrame("新建班车");

    final int WIDTH = 564;
    final int HEIGHT = 364;

    //组装视图
    public void init(String bmName) throws Exception {
        //设置窗口属性
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("images\\logo.png")));

        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File("images\\addBus.png")));
        bgPanel.setBounds(0, 0, WIDTH, HEIGHT);


        //组装线路代码、线路名称

        JLabel riLabel = new JLabel("线路代码");
        JTextField riField = new JTextField(15);

        JLabel rnLabel = new JLabel("线路名称");
        JTextField rnField = new JTextField(15);

        //组装线路类型、线路方向
        JLabel rtLabel = new JLabel("线路类型");
        JRadioButton inBtn = new JRadioButton("岛内班车", true);
        JRadioButton outBtn = new JRadioButton("岛外班车", false);

        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(inBtn);
        bg1.add(outBtn);

        JLabel dLabel = new JLabel("线路方向");
        JRadioButton upBtn = new JRadioButton("上行", true);
        JRadioButton downBtn = new JRadioButton("下行", false);

        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(upBtn);
        bg2.add(downBtn);


        //组装运营日期、运营时段
        JLabel sdLabel = new JLabel("运营日期");
        JComboBox<String> serviceDateSelect = new JComboBox<String>();
        serviceDateSelect.addItem("星期一");
        serviceDateSelect.addItem("星期二");
        serviceDateSelect.addItem("星期三");
        serviceDateSelect.addItem("星期四");
        serviceDateSelect.addItem("星期五");
        serviceDateSelect.addItem("星期六");
        serviceDateSelect.addItem("星期日");

        JLabel spLabel = new JLabel("运营时段");
        JComboBox<String> servicePeriodSelect = new JComboBox<String>();
        servicePeriodSelect.addItem("上午");
        servicePeriodSelect.addItem("下午");
        servicePeriodSelect.addItem("晚上");


        //组装发车时间、备注
        JLabel stLabel = new JLabel("发车时间");
        JTextField stField = new JTextField(15);
        stField.setToolTipText("例：13:50,15:50");

        JLabel rLabel = new JLabel("备注：  ");
        JTextField rField = new JTextField(15);


        Box hBox = Box.createHorizontalBox();
        Box lBox = Box.createVerticalBox();
        Box rBox = Box.createVerticalBox();

        Box a1Box = Box.createHorizontalBox();
        Box b1Box = Box.createHorizontalBox();
        Box c1Box = Box.createHorizontalBox();
        Box d1Box = Box.createHorizontalBox();

        Box a2Box = Box.createHorizontalBox();
        Box b2Box = Box.createHorizontalBox();
        Box c2Box = Box.createHorizontalBox();
        Box d2Box = Box.createHorizontalBox();

        a1Box.add(riLabel);
        a1Box.add(Box.createHorizontalStrut(20));
        a1Box.add(riField);

        a2Box.add(rnLabel);
        a2Box.add(Box.createHorizontalStrut(20));
        a2Box.add(rnField);

        b1Box.add(rtLabel);
        b1Box.add(Box.createHorizontalStrut(20));
        b1Box.add(inBtn);
        b1Box.add(outBtn);

        b2Box.add(dLabel);
        b2Box.add(Box.createHorizontalStrut(20));
        b2Box.add(upBtn);
        b2Box.add(downBtn);
        b2Box.add(Box.createHorizontalStrut(50));

        c1Box.add(sdLabel);
        c1Box.add(Box.createHorizontalStrut(20));
        c1Box.add(serviceDateSelect);

        c2Box.add(spLabel);
        c2Box.add(Box.createHorizontalStrut(20));
        c2Box.add(servicePeriodSelect);

        d1Box.add(stLabel);
        d1Box.add(Box.createHorizontalStrut(20));
        d1Box.add(stField);

        d2Box.add(rLabel);
        d2Box.add(Box.createHorizontalStrut(20));
        d2Box.add(rField);

        lBox.add(Box.createVerticalStrut(40));
        lBox.add(a1Box);
        lBox.add(Box.createVerticalStrut(20));
        lBox.add(b1Box);
        lBox.add(Box.createVerticalStrut(20));
        lBox.add(c1Box);
        lBox.add(Box.createVerticalStrut(20));
        lBox.add(d1Box);

        rBox.add(Box.createVerticalStrut(40));
        rBox.add(a2Box);
        rBox.add(Box.createVerticalStrut(20));
        rBox.add(b2Box);
        rBox.add(Box.createVerticalStrut(20));
        rBox.add(c2Box);
        rBox.add(Box.createVerticalStrut(20));
        rBox.add(d2Box);


        hBox.add(Box.createHorizontalStrut(50));
        hBox.add(lBox);
        hBox.add(Box.createHorizontalStrut(50));
        hBox.add(rBox);
        hBox.add(Box.createHorizontalStrut(50));


        //组装按钮
        Box btnBox = Box.createHorizontalBox();
        JButton confirmBtn = new JButton("确认");
        JButton backBtn = new JButton("返回");

        btnBox.add(confirmBtn);
        btnBox.add(Box.createHorizontalStrut(80));
        btnBox.add(backBtn);


        Box vBox = Box.createVerticalBox();
        vBox.add(hBox);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(btnBox);

        bgPanel.add(vBox);

        jf.add(bgPanel);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);

        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String routId = riField.getText().trim();
                String routName = rnField.getText().trim();
                String routType = bg1.isSelected(inBtn.getModel()) ? inBtn.getText() : outBtn.getText();
                String direction = bg2.isSelected(upBtn.getModel()) ? upBtn.getText() : downBtn.getText();
                String serveDate = (String) serviceDateSelect.getSelectedItem();
                String servePeriod=(String) servicePeriodSelect.getSelectedItem();
                String startTime=stField.getText().trim();
                String remark=rField.getText().trim();

                try {
                    Bus bus=new Bus(getLastId(),routId,routName,direction,routType,serveDate,servePeriod,startTime,"未设置","0",remark);
                    if(bus.getRoutId().equals("")){
                        JOptionPane.showMessageDialog(jf,"路线代码不能为空","错误提示",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(bus.getRoutName().equals("")){
                        JOptionPane.showMessageDialog(jf,"路线名称不能为空","错误提示",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(bus.getStartTime().equals("")){
                        JOptionPane.showMessageDialog(jf,"发车时间不能为空","错误提示",JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if(Check.checkRoutIdIsUsed(bus)){
                        JOptionPane.showMessageDialog(jf,"班车代码已存在"," 错误提示",JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if(!Check.checkBusForm(bus)){
                        JOptionPane.showMessageDialog(jf,"输入的信息格式有误，请重新输入","错误提示",JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    AddBusController addBusController = new AddBusController();
                    addBusController.addBus(bus);

                    JOptionPane.showMessageDialog(jf,"添加成功！"," ",JOptionPane.INFORMATION_MESSAGE);
                    new BusManagerInterface().init(bmName);

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

    public void writeFile(Steward steward) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("files\\ServiceObjectMessage", true));
        Gson gson = new Gson();
        String s = gson.toJson(steward);
        bw.write(s);
        bw.newLine();
        bw.flush();
    }

//    public static void main(String[] args) throws Exception {
//        new AddBusInterface().init();
//    }

    public static String getLastId()throws IOException{
        ArrayList<Bus> buses = RWFileForBus.readFile();
        int index=0;
        if(buses.size()==0){
            index=1;
        }else {
            Bus lastBus=buses.get(buses.size()-1);
            index=Integer.parseInt(lastBus.getId())+1;
        }
        String s = String.valueOf(index);
        return s;
    }
}