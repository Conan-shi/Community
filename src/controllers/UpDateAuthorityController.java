package controllers;

import com.google.gson.Gson;
import dao.RWFileForOldMan;
import dao.RWFileForUser;
import models.OldMan;
import models.Steward;
import models.User;

import java.io.*;
import java.util.ArrayList;

//更新管理权限功能的控制器
public class UpDateAuthorityController {
    private static UpDateAuthorityController singletonInstance=null;

    static public UpDateAuthorityController getSingletonInstance(){
        if(singletonInstance==null){
            singletonInstance=new UpDateAuthorityController();
        }
        return singletonInstance;
    }

    private UpDateAuthorityController(){}

    public void upDateAuthority(User user,ArrayList<User> users)throws IOException{
            if(user.getAuthority().equals("生活管家")){
                Steward steward = new Steward(user);
                upDateSOMFile(steward);
            }else {
                ArrayList<Steward> stewards = readFileFromSOM();
                for(int i=0;i< stewards.size();i++){
                    if(stewards.get(i).getAccount().equals(user.getAccount())){
                        stewards.remove(i);
                    }
                }
                writeFileToSOM(stewards);

                ArrayList<OldMan> oldMEN = RWFileForOldMan.readFile();
                for(int i=0;i<oldMEN.size();i++){
                    OldMan oldMan = oldMEN.get(i);
                    if(oldMan.getStewardAccount().equals(user.getAccount())){
                        oldMan.setStewardAccount("");
                    }
                }
                RWFileForOldMan.writeFile(oldMEN);

            }
            RWFileForUser.WriteFile(users);

    }

    public void upDateSOMFile(Steward steward) throws IOException{
        BufferedWriter bw=new BufferedWriter(new FileWriter("files\\ServiceObjectMessage",true));
        Gson gson=new Gson();
        String s = gson.toJson(steward);
        bw.write(s);
        bw.newLine();
        bw.flush();
        bw.close();
    }

    public ArrayList<Steward> readFileFromSOM()throws IOException{
        BufferedReader br=new BufferedReader(new FileReader("files\\ServiceObjectMessage"));
        ArrayList<Steward> stewards=new ArrayList<>();

        Gson gson=new Gson();
        String line;
        while((line=br.readLine())!=null){
            Steward steward = gson.fromJson(line, Steward.class);
            stewards.add(steward);
        }
        br.close();
        return stewards;
    }

    public void writeFileToSOM(ArrayList<Steward> stewards) throws IOException{
        BufferedWriter bw=new BufferedWriter(new FileWriter("files\\ServiceObjectMessage"));
        Gson gson=new Gson();

        for(Steward steward:stewards){
            String s = gson.toJson(steward);
            bw.write(s);
            bw.newLine();
            bw.flush();
        }

        bw.close();

    }


}
