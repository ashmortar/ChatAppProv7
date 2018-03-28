package chat7.app.pro.chatapppro7.models;


import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Parcel
public class User {
    String userName;
    String uId;
    Map<String, Boolean> chats = new HashMap<>();

    public User() {}

    public User(String userName, String uId) {
        this.userName = userName;
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public String getuId() {
        return uId;
    }

    public Map<String, Boolean> getChats() {
        return chats;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setChats(Map<String, Boolean> chats) {
        this.chats = chats;
    }
}
