package com.example.recluse.geziyorum.test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class RemoteDbTest {

    public static void main(String[] args){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = new Date(sdf.parse("1995-03-01").getTime());
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
