package Network;

import java.io.Serializable;

public class Command implements Serializable {
    private CommandTypes type;
    private Object content;
    private String receiver;
    private String sender;
    private int numberOfLines;

    public Command(CommandTypes type , Object content){
        this.type = type;
        this.content = content;
    }

    public Command(CommandTypes type, Object content, String sender, String receiver){
        this(type, content);
        this.receiver = receiver;
        this.sender = sender;
    }

    public Command(CommandTypes type, Object content, int numberOfLines){
        this(type, content);
        this.numberOfLines = numberOfLines;
    }

    public Command(CommandTypes type, String sender, String receiver){
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Command(CommandTypes type, Object content, String sender){
        this(type, content);
        this.sender = sender;
    }

    public void setType(CommandTypes type) {
        this.type = type;
    }

    public int getNumberOfLines(){
        return numberOfLines;
    }

    public Command(CommandTypes type){
        this.type = type;
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
