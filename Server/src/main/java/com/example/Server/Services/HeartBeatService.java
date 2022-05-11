package com.example.Server.Services;


import com.example.Server.Model.HeartBeatModel;
import com.example.Server.repository.HeartBeatImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HeartBeatService {

    @Autowired
    HeartBeatImp heartBeatImp;

    /**
     * This method is service Where it calls create streamTable heartbeat.
     * @return the String format data of the result.
     */
    public String createData()
    {
        return heartBeatImp.createHeartBeatTable();

    }

    /**
     * This method is service Where it whether the request body is in correct format or not.
     * If the request body is in correct format then it call insert to append data in the table.
     * @return the String format data of the result.
     */
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
                   boolean result = heartBeatImp.insertHeartBeatData(localDate,Integer.parseInt(strArr[0]),
                           Integer.parseInt(strArr[1]),
                           heartBeatModel.getHeatbeatrate());
                   if(result)
                   {
                       return "Record Inserted";
                   }
                   else
                   {
                       return "Internal Error";
                   }
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
        }
        catch (Exception e)
        {
         System.out.println(e);
         return "Time and Date Format Error";
        }

    }

    /**
     * This method is convert date in to the format of '2013.08.19' like structure.
     * @return the String
     */
    public String getdatetostring(String date)
    {
        try {
            LocalDate localDate = LocalDate.parse(date);
            String s = localDate.getYear() +".";
            if(localDate.getMonthValue() > 9)
            {
                s = s + localDate.getMonthValue() +".";
            }
            else
            {
                s = s + "0"+ localDate.getMonthValue() +".";
            }
            if(localDate.getDayOfMonth() > 9)
            {
                s = s + localDate.getDayOfMonth();
            }
            else
            {
                s = s + "0"+ localDate.getDayOfMonth();
            }
            return s;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    /**
     * This method is service Where it checks the params date format or time format is correct or not.
     * If it is correct then it call respective functions for fetching data.
     * @return the String format data of the result.
     */
    public String readData(String date, String time)
    {
        if (date == null && time == null)
        {
            return heartBeatImp.getAllHeartBeatData();
        }
        else if(time == null)
        {
            try {
                String s = getdatetostring(date);
                if(s != null)
                {
                    return heartBeatImp.getHeartBeatDataonParticularDate(s);
                }
                else
                {
                    throw new Exception("Invalid Format Query Params");
                }

            }
            catch (Exception e)
            {
                return "Invalid Format Query Params";
            }
        }
        else
        {
            try {
                String s = getdatetostring(date);
                if(s != null)
                {
                    return heartBeatImp.getHeartBeatDataonParticularDateTime(s,time);
                }
                else
                {
                    throw new Exception("Invalid Format Query Params");
                }

            }
            catch (Exception e)
            {
               return "Invalid Format Query Params";
            }

        }
    }

}
