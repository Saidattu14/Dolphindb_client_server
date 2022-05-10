package com.example.AltairNew.repository;

import com.dolphindb.jdbc.JDBCStatement;
import com.xxdb.DBConnection;
import com.xxdb.data.*;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.*;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class HeartBeatImp implements HeartBeatRepository{


    private static final String JDBC_DRIVER = "com.dolphindb.jdbc.Driver";
    // Connect to DolphinDB server
    private static final String url1 = "jdbc:dolphindb://localhost:8900";
    // Connect to DolphinDB Database
    private static final String url2 = "jdbc:dolphindb://localhost:8900?databasePath=C://jdbc&partitionType=VALUE&partitionScheme=2013.06.13..2013.06.13";


    private String database="dfs://testDatabase";
    private String tableName="heartbeat";


    public static void printData(ResultSet rs) throws SQLException {
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        int len = resultSetMetaData.getColumnCount();
        String s1 = "minute";
        String s2 = "date";
        String s3 = "heartbeatrateperminute";
        while (rs.next()) {
            for (int i = 1; i <= len; ++i) {
                System.out.print(
                        MessageFormat.format("{0}: {1},    ",
                                resultSetMetaData.getColumnName(i),
                                rs.getObject(i)

                        )
                );
            }
//            String minute = rs.getString(1);
//            String date = rs.getString(2);
//            int rate = rs.getInt(3);
//            System.out.println(minute + ", " + date + ", " + rate +
//                    ", ");
         System.out.print("\n");
        }
    }

    public boolean CheckTable()
    {
        try {
//            stm.execute("memTable = loadTable('" + database + "','" + tableName + "')");
            return true;
        }
        catch (Exception e)
        {
            return false;
        }

    }
    private BasicTable createBasicTable() {
        List<String> colNames = new ArrayList<String>();
        colNames.add("cshort");
        colNames.add("cdate");
        colNames.add("cminute");

        List<Vector> cols = new ArrayList<Vector>() {
        };

        //cshort
        short[] vshort = new short[]{32767, 29};
        BasicShortVector bshv = new BasicShortVector(vshort);
        cols.add(bshv);
        //cdate
        int[] vdate = new int[]{Utils.countDays(LocalDate.of(2018, 2, 14)), Utils.countDays(LocalDate.of(2018, 8, 15))};
        BasicDateVector bdatev = new BasicDateVector(vdate);
        cols.add(bdatev);
        //cminute
        int[] vminute = new int[]{Utils.countMinutes(LocalTime.of(16, 30)), Utils.countMinutes(LocalTime.of(9, 30))};
        BasicMinuteVector bminutev = new BasicMinuteVector(vminute);
        cols.add(bminutev);
        //csecond
        BasicTable t1 = new BasicTable(colNames, cols);
        return t1;
    }
    private BasicTable createBasicTable1() {
        List<String> colNames = new ArrayList<String>();
        colNames.add("cshort");
        colNames.add("cdate");
        colNames.add("cminute");

        List<Vector> cols = new ArrayList<Vector>() {
        };

        //cshort
        short[] vshort = new short[]{32767, 29};
        BasicShortVector bshv = new BasicShortVector(vshort);
        cols.add(bshv);
        //cdate
        int[] vdate = new int[]{Utils.countDays(LocalDate.of(2018, 2, 14)), Utils.countDays(LocalDate.of(2018, 8, 15))};
        BasicDateVector bdatev = new BasicDateVector(vdate);
        cols.add(bdatev);
        //cminute
        int[] vminute = new int[]{Utils.countMinutes(LocalTime.of(16, 30)), Utils.countMinutes(LocalTime.of(9, 30))};
        BasicMinuteVector bminutev = new BasicMinuteVector(vminute);
        cols.add(bminutev);
        //csecond
        BasicTable t1 = new BasicTable(colNames, cols);
        return t1;
    }
    public void writeDfsTable(DBConnection conn) throws IOException, IOException {
        BasicTable table1 = createBasicTable();
        BasicTable table2 = createBasicTable1();
        conn.login("admin", "123456", false);
        conn.run("t = table(10000:0,`cshort`cdate`cminute,[SHORT,DATE,MINUTE])\n");
        conn.run("if(existsDatabase('dfs://testDatabase')){dropDatabase('dfs://testDatabase')}");
        conn.run("db = database('dfs://testDatabase',RANGE,2018.01.01..2018.12.31)");
        conn.run("db.createPartitionedTable(t,'tb1','cdate')");
        conn.run("def saveData(data){ loadTable('dfs://testDatabase','tb1').tableInsert(data)}");
        List<Entity> args = new ArrayList<Entity>(1);
        args.add(table1);
        List<Entity> args1 = new ArrayList<Entity>(1);
        args1.add(table2);
        conn.run("saveData", args);
        conn.run("saveData", args1);
        BasicTable dt = (BasicTable) conn.run("select * from loadTable('dfs://testDatabase','tb1')");
        System.out.println(dt.getString());
        if (dt.rows() != 2) {
            System.out.println("failed");
        }
    }

    @Override
    public String createHeartBeatTable() {
//        if(CheckTable())
//        {
//            return "Table Already Created";
//        }
        try {
            DBConnection conn = new DBConnection();
            conn.connect("localhost",8900);
            writeDfsTable(conn);
            return "Table Created Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Table Failed to Create";
        }
    }

    @Override
    public void insertHeartBeatData(LocalDate localDate,int hours, int minutes, int heatbeatrate) {
        try {

            Class.forName(JDBC_DRIVER);
            Properties info = new Properties(); // Login info
            info.put("user", "admin");// Username
            info.put("password", "123456");// Password(default admin password)
            Connection conn = DriverManager.getConnection(url2, info);
            JDBCStatement stm = (JDBCStatement)conn.createStatement();
            stm.execute("dfsTable = loadTable('" + database + "','" + tableName + "')");
            //SQL insert statement
            PreparedStatement stmt = conn.prepareStatement("insert into dfsTable values(?,?,?)");
            stmt.setObject(1, LocalTime.of(13,39));
            stmt.setInt(2,30);
            stmt.setDate(3,Date.valueOf(LocalDate.of(2013,06,13)));
            stmt.executeUpdate();
            ResultSet rs = stm.executeQuery("select * from loadTable(\""+database+"\", `"+tableName+")");
            printData(rs);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }
    @Override
    public void AllHeartBeatData() {
        try {
            Class.forName(JDBC_DRIVER);
            Properties info = new Properties(); // Login info
            info.put("user", "admin");// Username
            info.put("password", "123456");// Password(default admin password)
            Connection conn = DriverManager.getConnection(url2, info);
            JDBCStatement stm = (JDBCStatement)conn.createStatement();
            stm.execute("dfsTable = loadTable('" + database + "','" + tableName + "')");
            ResultSet rs =  stm.executeQuery("select * from dfsTable");
            printData(rs);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ksksksksk");
        }

    }


}
