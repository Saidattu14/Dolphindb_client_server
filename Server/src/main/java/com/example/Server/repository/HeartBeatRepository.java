package com.example.Server.repository;


import java.time.LocalDate;


public interface HeartBeatRepository {

    String createHeartBeatTable();
    boolean insertHeartBeatData(LocalDate localDate,int hours, int minutes, int heatbeatrate);
    String getAllHeartBeatData();
    String getHeartBeatDataonParticularDateTime(String date,String time);
    String getHeartBeatDataonParticularDate(String date);
}
