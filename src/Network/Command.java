package Network;

public class Command {
    private CommandTypes type;
    private Object content;
    private int port;
    public Command(CommandTypes type , Object content){
        this.type = type;
        this.content = content;
    }

    public Command(CommandTypes type, Object content, int port){
        this(type, content);
        this.port = port;
    }

    public int getPort(){
        return port;
    }

    public CommandTypes getType() {
        return type;
    }

    public Object getContent() {
        return content;
    }
}
