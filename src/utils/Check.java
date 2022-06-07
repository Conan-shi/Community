package utils;

import dao.RWFileForBus;
import dao.RWFileForOldMan;
import dao.RWFileForUser;
import models.Bus;
import models.OldMan;
import models.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Check {
    //检查用户信息格式是否正确
    public static boolean checkForm(User user) throws IOException {
        if (user.getAccount() == null || user.getPassword() == null || user.getName() == null || user.getAuthority() == null) {
            return false;
        } else if (user.getAccount().length() < 6 || user.getAccount().length() > 18) {
            return false;
        } else if (!checkPassWord(user)) {
            return false;
        } else if (user.getName().length() < 1 || user.getName().length() > 10) {
            return false;
        } else if (user.getBirthDate().length() != 0) {
            if (!checkBirthDate(user)) {
                return false;
            }
        } else if (user.getTelephoneNumber().length() != 0) {
            if (user.getTelephoneNumber().length() < 5 || user.getTelephoneNumber().length() > 20) {
                return false;
            }

        }
        return true;
    }

    public static boolean ifAccountIsUsed(User user) throws IOException {
        boolean flag = true;
        ArrayList<User> users = RWFileForUser.readFile();
        if (users.size() == 0) {
            return true;
        } else {
            for (User s : users) {
                if (s.getAccount().equals(user.getAccount())) {
                    flag = false;
                    break;
                }
            }
        }
        return flag;
    }

    public static boolean ifAccountIsUsed(OldMan oldMan) throws IOException {
        boolean flag = false;
        ArrayList<OldMan> oldMEN = RWFileForOldMan.readFile();
        if (oldMEN.size() == 0) {
            return false;
        } else {
            for (OldMan oldMan1 : oldMEN) {
                if (oldMan1.getAccount().equals(oldMan.getAccount())) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    public static boolean checkPassWord(User user) {
        if (user.getPassword().length() < 8 || user.getPassword().length() > 18) {
            return false;
        } else {
            ArrayList<Character> array = new ArrayList<Character>();
            int index = 0;
            String s = user.getPassword();
            while (index < s.length()) {
                array.add(s.charAt(index));
                index++;
            }

            int capital = 0;
            int lowercase = 0;
            int numbers = 0;
            int others = 0;

            for (Character c : array) {
                if (c >= 'A' && c <= 'Z') {
                    capital++;
                } else if (c >= 'a' && c <= 'z') {
                    lowercase++;
                } else if (c >= '0' && c <= '9') {
                    numbers++;
                } else {
                    others++;
                }
            }

            if (capital == 0 || lowercase == 0 || numbers == 0 || others == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkBirthDate(User user) {
        ArrayList<Character> array = new ArrayList<Character>();
        int index = 0;
        String s = user.getBirthDate();

        while (index < s.length()) {
            array.add(s.charAt(index));
            index++;
        }

        boolean flag = true;
        if (s.length() != 10) {
            flag = false;
        }
        Character cha = "-".charAt(0);

        for (int i = 0; i < array.size(); i++) {
            if (i == 4 || i == 7) {
                if (Character.compare(array.get(i), cha) != 0) {
                    flag = false;
                    break;
                }
            } else {
                if (!(array.get(i) >= '0' && array.get(i) <= '9')) {
                    flag = false;
                    break;
                }
            }
        }

        return flag;
    }

    public static boolean checkForm(OldMan oldMan) throws IOException {

        if (oldMan.getName() == null || oldMan.getAccount() == null) {
            return false;
        } else if (oldMan.getAccount().length() < 6 || oldMan.getAccount().length() > 18) {
            return false;
        } else if (oldMan.getName().length() < 1 || oldMan.getName().length() > 10) {
            return false;
        } else if (oldMan.getBirthDate().length() != 0) {
            if (!checkBirthDate(oldMan)) {
                return false;
            }
        } else if (oldMan.getTelephoneNumber().length() != 0) {
            if (oldMan.getTelephoneNumber().length() < 5 || oldMan.getTelephoneNumber().length() > 20) {
                return false;
            }

        }

        return true;
    }

    public static boolean checkBirthDate(OldMan oldMan) {
        ArrayList<Character> array = new ArrayList<Character>();
        int index = 0;
        String s = oldMan.getBirthDate();
        while (index < s.length()) {
            array.add(s.charAt(index));
            index++;
        }

        boolean flag = true;
        if (s.length() != 10) {
            flag = false;
        }
        Character cha = "-".charAt(0);

        for (int i = 0; i < array.size(); i++) {
            if (i == 4 || i == 7) {
                if (Character.compare(array.get(i), cha) != 0) {
                    flag = false;
                    break;
                }
            } else {
                if (!(array.get(i) >= '0' && array.get(i) <= '9')) {
                    flag = false;
                    break;
                }
            }
        }

        return flag;
    }

    public static boolean checkIfIsFull() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("files\\oldManMessage"));

        ArrayList<OldMan> oldMEN = new ArrayList<OldMan>();
        Gson gson = new Gson();

        String line;
        while ((line = br.readLine()) != null) {
            OldMan oldMan = gson.fromJson(line, OldMan.class);
            oldMEN.add(oldMan);
        }
        br.close();

        boolean flag = true;
        for (OldMan oldMan : oldMEN) {
            if (oldMan.getStewardAccount().equals("")) {
                flag = false;
            }
        }
        return flag;
    }

    public static boolean checkBusForm(Bus bus) {
        String s = bus.getStartTime();
        ArrayList<Character> array = new ArrayList<Character>();
        int index = 0;
        while (index < s.length()) {
            array.add(s.charAt(index));
            index++;
        }

        if (array.size() != 5) {
            return false;
        } else {
            for (int i = 0; i < array.size(); i++) {
                Character c = array.get(i);
                if (i == 2 && c != (":".charAt(0))) {
                    return false;
                } else {
                    if (i != 2 && !(c >= '0' && c <= '9')) {
                        return false;
                    }
                }
            }
        }

        StringTokenizer st2 = new StringTokenizer(bus.getStartTime(), ":");
        int sh = Integer.parseInt(st2.nextToken());
        int sm = Integer.parseInt(st2.nextToken());
        String sp = bus.getServePeriod();
        switch (sp){
            case "上午":
                if(!(sh>=4&&sh<=11)){
                    return false;
                }
                break;
            case "下午":
                if(!(sh>11&&sh<=18)){
                    return false;
                }
                break;
            case "晚上":
                if(!((sh>=0&&sh<4)||(sh>18&&sh<=23))){
                    return false;
                }
                break;
        }

        return true;
    }

    public static boolean checkBusDdl(String ddl,String startTime) {
        ArrayList<Character> array = new ArrayList<Character>();
        int index = 0;
        while (index < ddl.length()) {
            array.add(ddl.charAt(index));
            index++;
        }

        if (array.size() != 5) {
            return false;
        } else {
            for (int i = 0; i < array.size(); i++) {
                Character c = array.get(i);
                if (i == 2 && c != (":".charAt(0))) {
                    return false;
                } else {
                    if (i != 2 && !(c >= '0' && c <= '9')) {
                        return false;
                    }
                }
            }
        }
        StringTokenizer st1 = new StringTokenizer(ddl, ":");
        int dh = Integer.parseInt(st1.nextToken());
        int dm = Integer.parseInt(st1.nextToken());

        StringTokenizer st2 = new StringTokenizer(startTime, ":");
        int sh = Integer.parseInt(st2.nextToken());
        int sm = Integer.parseInt(st2.nextToken());


        if(dh>sh){
            return false;
        }else if(sm>sh){
            return false;
        }

        return true;
    }

    public static boolean checkRoutIdIsUsed(Bus bus)throws IOException{
        boolean flag=false;
        ArrayList<Bus> buses = RWFileForBus.readFile();

        if(buses.size()==0){
            return false;
        }else {
            for(Bus bus1:buses){
                if(bus1.getRoutId().equals(bus.getRoutId())){
                    flag=true;
                    break;
                }
            }
        }
        return flag;

    }

    public static boolean checkHaveSteward()throws IOException{
        ArrayList<User> users = RWFileForUser.readFile();
        boolean flag=false;

        for (User user:users){
            if (user.getAuthority().equals("生活管家")){
                flag=true;
            }
        }
        return flag;
    }


    public static void main(String[] args) throws IOException{
        User user=new User();
        user.setGender("男");
        user.setName("lll");
        user.setAccount("31013922");
        user.setPassword("$Tx521120");
        user.setTelephoneNumber("1111111122");
        user.setBirthDate("1111-11-11");
        user.setAuthority("管理员");


        System.out.println(Check.checkForm(user));
    }

}
