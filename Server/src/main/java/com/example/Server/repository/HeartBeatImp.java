package com.example.Server.repository;

import com.xxdb.DBConnection;
import com.xxdb.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HeartBeatImp implements HeartBeatRepository{

    @Autowired
    DBConnection dbConnection;

    private final String tableName="heartbeat";

    public HeartBeatImp(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }


    /**
     * This method is checks whether the heartbeat is present or not.
     * @return the true if table is present or false.
     */
    public boolean CheckTable()
    {
        try {

            this.dbConnection.run("select * from "+tableName+"");
            return true;
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }

    }
    /**
     * This method is Query Where it creates a streamTable heartbeat.
     * @return the String whether the table is already present or Tabel created successfully.
     */
    @Override
    public String createHeartBeatTable() {

        if(CheckTable())
        {
            return "Table Already Created";
        }
        try {
            this.dbConnection.run("share streamTable(10000:0,`heartbeatrate`date`minute,[SHORT,DATE,MINUTE]) as "+tableName+"\n");
            this.dbConnection.run("def saveData(data){ "+tableName+".tableInsert(data)}");
            return "Table Created Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Table Failed to Create";
        }
    }
    /**
     * This method is creates a row data for the heartbeat table.
     * @return the row details that was to be inserted on the table.
     */
    private BasicTable createBasicTable(LocalDate localDate,int hours, int minutes, int heatbeatrate) {
        List<String> colNames = new ArrayList<>();
        colNames.add("heartbeatrate");
        colNames.add("date");
        colNames.add("minute");
        List<Vector> cols = new ArrayList<>() {
        };
        short[] vshort = new short[]{(short) heatbeatrate};
        BasicShortVector bshv = new BasicShortVector(vshort);
        cols.add(bshv);
        int[] vdate = new int[]{Utils.countDays(localDate)};
        BasicDateVector bdatev = new BasicDateVector(vdate);
        cols.add(bdatev);
        int[] vminute = new int[]{Utils.countMinutes(LocalTime.of(hours, minutes))};
        BasicMinuteVector bminutev = new BasicMinuteVector(vminute);
        cols.add(bminutev);
        return new BasicTable(colNames, cols);
    }

    /**
     * This method is Query Where it appends the new row into the streamTable heartbeat.
     * @return the true whether for successfully insert else false.
     */
    @Override
    public boolean insertHeartBeatData(LocalDate localDate,int hours, int minutes, int heatbeatrate) {
        try {
            this.dbConnection.run("def saveData(data){ "+tableName+".tableInsert(data)}");
            BasicTable table1 = createBasicTable(localDate,hours,minutes,heatbeatrate);
            List<Entity> args = new ArrayList<>(1);
            args.add(table1);
            this.dbConnection.run("saveData", args);
            BasicTable dt = (BasicTable) this.dbConnection.run("select * from "+tableName+"");
            System.out.println(dt.getString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }

    }
    /**
     * This method is Query Where it fetches all the data in the streamTable heartbeat.
     * @return the data in the String format else null.
     */
    @Override
    public String getAllHeartBeatData() {
        try {
            BasicTable dt = (BasicTable) this.dbConnection.run("select * from "+tableName+"");
            System.out.println(dt.getString());
            return dt.getString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    /**
     * This method is Query Where it fetches rows of the streamTable heartbeat data on Particular Date.
     * @return the String format data else null.
     */
    @Override
    public String getHeartBeatDataonParticularDate(String date) {
        try {
            BasicTable dt = (BasicTable) this.dbConnection.run("select * from "+tableName+" where date ="+date+"");
            System.out.println(dt.getString());
            return dt.getString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    /**
     * This method is Query Where it fetches rows of the streamTable heartbeat data on Particular Date and Time.
     * @return the String format data else null.
     */
    @Override
    public String getHeartBeatDataonParticularDateTime(String date,String time) {
        try {
            BasicTable dt = (BasicTable) this.dbConnection.run("select * from "+tableName+" where date ="+date+" and minute = "+time+"m");
            System.out.println(dt.getString());
            return dt.getString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
