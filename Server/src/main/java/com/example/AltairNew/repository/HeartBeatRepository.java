package com.example.AltairNew.repository;


import java.time.LocalDate;

public interface HeartBeatRepository {

    String createHeartBeatTable();
    void insertHeartBeatData(LocalDate localDate,int hours, int minutes, int heatbeatrate);
    void AllHeartBeatData();
}
