package com.mposyandu.mposyandu.tools;

public class Database {
    private static final String url = "https://mposyandu.id";
//    private static final String url = "http://192.168.0.110/mp";
    private static final String notif = "https://fcm.googleapis.com/fcm/send";

    public static String getNotif() {
        return notif;
    }
    public static String getUrl() {
        return url;
    }

}
