package utils;

import java.awt.*;

public class ScreenUtils {
    //获取界面的长宽

    public static int getScreenWidth(){
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    public static int getScreenHeight(){
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }
}
