package views;

import models.OldMan;
import models.Steward;
import com.google.gson.Gson;
import component.BackGroundPanel;
import controllers.ScreenUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SetServiceObjectInterface {
    JFrame jf = new JFrame("设置服务对象");

    final int WIDTH = 500;
    final int HEIGHT = 300;

    public void init(String adName) throws Exception {
        jf.setBounds((ScreenUtils.getScreenWidth() - WIDTH) / 2, (ScreenUtils.getScreenHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        jf.setResizable(false);
        jf.setIconImage(ImageIO.read(new File("images\\logo.png")));

        BackGroundPanel bgPanel = new BackGroundPanel(ImageIO.read(new File("images\\setServiceObject.png")));
        bgPanel.setBounds(0, 0, WIDTH, HEIGHT);

        Box btnBox = Box.createHorizontalBox();
        JButton confirmBtn = new JButton("确认");
        JButton backBtn = new JButton("返回");
        btnBox.add(confirmBtn);
        btnBox.add(Box.createHorizontalStrut(100));
        btnBox.add(backBtn);

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

        Box sBox = Box.createHorizontalBox();
        JLabel sLabel = new JLabel("生活管家");
        JComboBox<String> stewardSelect = new JComboBox<String>();

        JLabel oLabel = new JLabel("顾客");
        JComboBox<String> oldManSelect = new JComboBox<String>();

        ArrayList<Steward> stewards = readFile();

        for (Steward steward : stewards) {
            String s = steward.getName() + "(" + steward.getAccount() + ")";
            stewardSelect.addItem(s);
        }

        BufferedReader br2 = new BufferedReader(new FileReader("files\\oldManMessage"));
        ArrayList<OldMan> oldMEN = new ArrayList();
        String line2;
        Gson gson=new Gson();
        while ((line2 = br2.readLine()) != null) {
            OldMan oldMan = gson.fromJson(line2, OldMan.class);
            oldMEN.add(oldMan);
        }
        br2.close();

        for (OldMan oldMan : oldMEN) {
            if (oldMan.getStewardAccount() .equals("")) {
                String s = oldMan.getName() + "(" + oldMan.getAccount() + ")";
                oldManSelect.addItem(s);
            }
        }

        confirmBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = (String) stewardSelect.getSelectedItem();
                StringTokenizer st1 = new StringTokenizer(s, "(");
                String c = st1.nextToken();
                String account0 = st1.nextToken();
                StringTokenizer st2 = new StringTokenizer(account0, ")");
                String stewardAccount = st2.nextToken();

                String s2 = (String) oldManSelect.getSelectedItem();
                StringTokenizer st3 = new StringTokenizer(s2, "(");
                String c2 = st3.nextToken();
                String account00 = st3.nextToken();
                StringTokenizer st4 = new StringTokenizer(account00, ")");
                String oldManAccount = st4.nextToken();


                for (Steward steward : stewards) {
                    if (steward.getAccount().equals(stewardAccount)) {
                        for (OldMan oldMan : oldMEN) {
                            if (oldMan.getAccount().equals(oldManAccount)) {
                                oldMan.setStewardAccount(steward.getAccount());
                                steward.addOldManId(oldMan.getAccount());

                            }
                        }
                    }
                }

                try {
                    writeFile(oldMEN,stewards);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(jf,"设置成功","",JOptionPane.INFORMATION_MESSAGE);
                try {
                    new AdministratorInterface().init(adName);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                jf.dispose();

            }
        });


        sBox.add(sLabel);
        sBox.add(Box.createHorizontalStrut(20));
        sBox.add(stewardSelect);
        sBox.add(Box.createHorizontalStrut(40));
        sBox.add(oLabel);
        sBox.add(Box.createHorizontalStrut(20));
        sBox.add(oldManSelect);

        Box vBox = Box.createVerticalBox();
        vBox.add(Box.createVerticalStrut(80));
        vBox.add(sBox);
        vBox.add(Box.createVerticalStrut(50));
        vBox.add(btnBox);

        bgPanel.add(vBox);

        jf.add(bgPanel);

        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);

    }

//    public static void main(String[] args) throws Exception {
//        new SetServiceObjectInterface().init();
//    }

    public static void writeFile(ArrayList<OldMan> oldMEN, ArrayList<Steward> stewards) throws IOException {
        BufferedWriter bw1 = new BufferedWriter(new FileWriter("files\\oldManMessage"));
        Gson gson = new Gson();
        for (OldMan oldMan : oldMEN) {
            String s4 = gson.toJson(oldMan);
            bw1.write(s4);
            bw1.newLine();
            bw1.flush();
        }
        bw1.close();

        BufferedWriter bw2 = new BufferedWriter(new FileWriter("files\\ServiceObjectMessage"));
        for (Steward steward : stewards) {
            String s5 = gson.toJson(steward);
            bw2.write(s5);
            bw2.newLine();
            bw2.flush();

        }
        bw2.close();
    }

    public static ArrayList<Steward> readFile()throws IOException{
        BufferedReader br=new BufferedReader(new FileReader("files\\ServiceObjectMessage"));
        ArrayList<Steward> stewards = new ArrayList<>();
        Gson gson=new Gson();
        String line;
        while((line=br.readLine())!=null){
            Steward steward = gson.fromJson(line, Steward.class);
            stewards.add(steward);
        }

        br.close();
        return stewards;

    }
}
