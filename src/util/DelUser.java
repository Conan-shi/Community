package util;

import java.util.ArrayList;
import java.util.LinkedList;

public class DelUser {
    public static ArrayList delUser(ArrayList array,int n){
        LinkedList<Object> objects = new LinkedList<>();
        for(Object item:array){
            objects.add(item);
        }
        objects.remove(n);
        Object[] objects1 = objects.toArray();
        ArrayList<Object> objects2 = new ArrayList<>();
        for(Object item:objects1){
            objects2.add(item);
        }
        return objects2;
    }
}
