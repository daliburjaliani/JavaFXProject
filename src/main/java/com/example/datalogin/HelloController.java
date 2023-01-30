package com.example.datalogin;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.SQLException;



public class HelloController {

    @FXML
    private Label message;

    @FXML
    private TextField nickName;

    @FXML
    private PasswordField password;

    @FXML
    private TextField type;

    @FXML
    private TextField price;

    @FXML
    private Label amount;

    @FXML
    private Label insertMessage;

    @FXML
    private PieChart pieChart;

    public static String tableName;

    @FXML
    protected void LogInButton() throws IOException {
        String nick = nickName.getText();
        String psw = password.getText();

        HelloApplication app = new HelloApplication();

        if(!nick.trim().isEmpty() && !psw.trim().isEmpty()) {

            if (DataUtil.logIn(new User(nick, psw)) == 1) {
                message.setText("User not found");
                nickName.clear();
                password.clear();
            } else {
                tableName = nick;
                app.changeScene("secPage.fxml");
            }
        } else {
            message.setText("Fill th fields");
        }

    }


    public void SignUpButton(ActionEvent actionEvent){
        String nick = nickName.getText();
        String psw = password.getText();

        if(!nick.trim().isEmpty() && !psw.trim().isEmpty()){
            String result = null;
            try {
                result = DataUtil.createUser(new User(nick, psw));
                CalculateData.createTable(nick);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            message.setText(result);
        } else {
            message.setText("Fill th fields");
        }

        nickName.clear();
        password.clear();
    }

    public void insertButton(ActionEvent actionEvent) {
        String exp = type.getText();
        int prc = Integer.parseInt(price.getText());
        String result = CalculateData.insert(exp, prc, tableName);

        insertMessage.setText(result);

        type.clear();
        price.clear();
    }

    public void getChartButton(ActionEvent actionEvent) {
        String am = CalculateData.amount(tableName);
        amount.setText(am);
        pieChart.setData(CalculateData.getChart(tableName));
        insertMessage.setText("");
    }

    public void logOutButton(ActionEvent actionEvent) throws IOException {

        HelloApplication app = new HelloApplication();
        app.changeScene("hello-view.fxml");

    }
}