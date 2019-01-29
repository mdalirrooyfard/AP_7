package Network;

import java.io.Serializable;

public class Command implements Serializable {
    private CommandTypes type;
    private Object content;
    private String receiver;
    private String sender;

    public Command(CommandTypes type , Object content){
        this.type = type;
        this.content = content;
    }

    public Command(CommandTypes type, Object content, String sender, String receiver){
        this(type, content);
        this.receiver = receiver;
        this.sender = sender;
    }

    public String getReceiver(){
        return receiver;
    }

    public String getSender(){
        return sender;
    }

    public CommandTypes getType() {
        return type;
    }

    public Object getContent() {
        return content;
    }
}
