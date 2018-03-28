package chat7.app.pro.chatapppro7.models;

import org.parceler.Parcel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Parcel
public class Chat {
    Map<String, Boolean> userIds = new HashMap<>();
    Map<String, Message> messages = new HashMap<>();
    String pushId;

    public Chat() {}

    public Chat(Map<String, Boolean> userIds) {
        this.userIds = userIds;
    }

    public Map<String, Boolean> getUserIds() {
        return userIds;
    }

    public void setUserIds(Map<String, Boolean> userIds) {
        this.userIds = userIds;
    }

    public Map<String, Message> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Message> messages) {
        this.messages = messages;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
