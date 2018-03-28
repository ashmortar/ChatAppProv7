package chat7.app.pro.chatapppro7.models;

import org.parceler.Parcel;

import java.util.List;


@Parcel
public class Chat {
    List<String> userIds;
    List<Message> messages;
    String pushId;

    public Chat() {}

    public Chat(List<String> userIds) {
        this.userIds = userIds;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getPushId() {
        return pushId;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
