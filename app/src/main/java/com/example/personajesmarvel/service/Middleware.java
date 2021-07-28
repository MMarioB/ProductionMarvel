package com.example.personajesmarvel.service;

import com.example.personajesmarvel.env.Keys;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

public class Middleware {

    public long timestamp() {
        Date date = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MILLISECOND, 0);
        date = new java.sql.Timestamp(c.getTime().getTime());

        long dateInMillis = date.getTime() / 1000;

        return dateInMillis;
    }

    public String md5() {

        long date = timestamp();

        String s = date + Keys.PRIVATE_KEY + Keys.PUBLIC_KEY;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(s.getBytes(), 0, s.length());

        String md5 = new BigInteger(1, m.digest()).toString(16);

        return md5;
    }
}
