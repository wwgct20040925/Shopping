package com.gct;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataTimeTest {

    @Test
    public void dataTime(){
        Date date = new Date();
        System.out.println(date);
        SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");//设置当前时间的格式，为年-月-日
        System.out.println(dateFormat.format(date));
        SimpleDateFormat dateFormat_min=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");//设置当前时间的格式，为年-月-日 时-分-秒
        System.out.println(dateFormat_min.format(date));
        System.out.println(date.toGMTString());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
    }
}
