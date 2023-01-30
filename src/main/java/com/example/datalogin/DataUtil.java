package com.example.datalogin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class DataUtil {

    private DataUtil(){

    }

    public static void createTable(){
        String CREATE = "CREATE TABLE IF NOT EXISTS USERS(" +
                "ID INTEGER PRIMARY KEY AUTO_INCREMENT," +
                "NICK VARCHAR(100)," +
                "PASSWORD VARCHAR(50))";

        try {
            JDBCConfig.getStatement().executeUpdate(CREATE);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static String createUser(User user) throws SQLException {
        String INSERT = "INSERT INTO USERS(NICK, PASSWORD) " +
                "VALUES('" + user.getNick() + "', '" + user.getPassword() + "')";

        String CHECK = "SELECT NICK FROM USERS";

        try {

            ResultSet resultSet = JDBCConfig.getStatement().executeQuery(CHECK);
            while (resultSet.next()){
                if(Objects.equals(user.getNick(), resultSet.getString("NICK"))) {
                    return "Nick is already used";
                }
            }
            JDBCConfig.getStatement().executeUpdate(INSERT);
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

        return "User created!";
    }

    public static int logIn(User user){
        String LOGIN = "SELECT NICK, PASSWORD FROM USERS";

        try {
            ResultSet resultSet = JDBCConfig.getStatement().executeQuery(LOGIN);

            while (resultSet.next()){
                if(Objects.equals(user.getNick(), resultSet.getString("NICK")) && Objects.equals(user.getPassword(), resultSet.getString("PASSWORD"))){
                    return 0;
                }
            }

            return 1;

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
