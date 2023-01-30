package com.example.datalogin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private int id;
    private String nick;
    private String Password;


    public User(String nick, String password) {
        this.nick = nick;
        Password = password;
    }


}
