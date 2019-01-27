package Network;

import java.io.Serializable;

public class Command implements Serializable {
    private CommandTypes type;
    private Object content;

    public Command(CommandTypes type , Object content){
        this.type = type;
        this.content = content;
    }


    public CommandTypes getType() {
        return type;
    }

    public Object getContent() {
        return content;
    }
}
