package com.example.recluse.geziyorum.test;

import com.example.recluse.geziyorum.db.helper.RemoteDbHelper;

public class RemoteDbTest {

    public static void main(String[] args){
        RemoteDbHelper db = new RemoteDbHelper();
        System.out.println(db.checkPassword("cemaltaskiran@gmail.com","secret"));
    }
}
