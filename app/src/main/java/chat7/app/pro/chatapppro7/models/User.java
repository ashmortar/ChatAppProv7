package chat7.app.pro.chatapppro7.models;


import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class User {
    String userName;
    String uId;
    List<String> chats = new ArrayList<>();

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

    public List<String> getChats() {
        return chats;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setChats(List<String> chats) {
        this.chats = chats;
    }
}
