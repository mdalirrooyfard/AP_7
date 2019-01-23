package Network;

public class Command {
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
