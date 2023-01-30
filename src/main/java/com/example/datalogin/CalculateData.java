package com.example.datalogin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CalculateData {

    public static void createTable(String table){
        String CREATE = "CREATE TABLE IF NOT EXISTS " + table + "(" +
                "NAME VARCHAR(50)," +
                "PRICE INTEGER)";
        try {
            JDBCConfig.getStatement().executeUpdate(CREATE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String insert(String name, int price, String table){
        String INSERT = "INSERT INTO " + table +" (NAME, PRICE) " +
                "VALUES( '" + name + "', " + price + ")";

        try {
            JDBCConfig.getStatement().executeUpdate(INSERT);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return "Inserted";
    }

    public static ObservableList<PieChart.Data> getChart(String table){
        String SELECT = "SELECT NAME,  SUM(PRICE) AS PRICE_SUM FROM "+ table +" GROUP BY NAME";

        try {
            ResultSet result = JDBCConfig.getStatement().executeQuery(SELECT);

            ObservableList<PieChart.Data> observableList = FXCollections.observableArrayList();

            while (result.next()){
                observableList.add(new PieChart.Data(result.getString("NAME"), result.getInt("PRICE_SUM")));
            }

            return observableList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String amount(String table){
        String SELECT = "SELECT SUM(PRICE) AS PRICE_SUM FROM " + table;

        List<Integer> amount = new ArrayList<>();

        try {
            ResultSet result = JDBCConfig.getStatement().executeQuery(SELECT);
            while (result.next()){
                amount.add(result.getInt("PRICE_SUM"));
            }
            return "ALL : " + amount.get(0) + "$";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
