package com.example.AltairNew.Services;


import com.example.AltairNew.Model.HeartBeatModel;
import com.example.AltairNew.repository.HeartBeatImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HeartBeatService {

    @Autowired
    HeartBeatImp heartBeatImp;

    public String createData()
    {
        return heartBeatImp.createHeartBeatTable();

    }
    public String insertData(HeartBeatModel heartBeatModel)
    {
        try {
            String date = heartBeatModel.getDate();
            LocalDate localDate = LocalDate.parse(date);
            String [] strArr = heartBeatModel.getTime().split(":");
            if(Integer.parseInt(strArr[0]) >= 0 && Integer.parseInt(strArr[0]) <= 23)
            {
               if(Integer.parseInt(strArr[1]) >= 0 && Integer.parseInt(strArr[1]) <= 59)
               {
                   heartBeatImp.insertHeartBeatData(
                           localDate,Integer.parseInt(strArr[0]),
                           Integer.parseInt(strArr[1]),
                           heartBeatModel.getHeatbeatrate()
                   );
               }
               else
               {
                   throw new Exception("Invalid Time error");
               }
            }
            else
            {
                throw new Exception("Invalid Time error");
            }
            return "Insert record";
        }
        catch (Exception e)
        {
         System.out.println(e);
         return "Time and Date Format Error";
        }

    }
    public void readData(String date, String time)
    {
        if (date == null && time == null)
        {
            heartBeatImp.AllHeartBeatData();
        }

    }

}
