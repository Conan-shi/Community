package models;

import java.util.ArrayList;

public class Steward extends User{
    private ArrayList<String> oldManAccounts;

    public ArrayList<String> getOldManAccounts() {
        return oldManAccounts;
    }


    public Steward(User user) {
        super(user.getAccount(), user.getPassword(), user.getName(), user.getGender(), user.getBirthDate(), user.getAccount(), user.getAuthority());
        if(oldManAccounts==null){
            oldManAccounts=new ArrayList<>();
        }

    }

    public void addOldManId(String oldManAccount){
        oldManAccounts.add(oldManAccount);
    }

    @Override
    public String toString() {
        return "Steward{" +
                "oldManAccounts=" + oldManAccounts +
                '}';
    }
}
