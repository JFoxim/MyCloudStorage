package common.messages;

public class CommandMessage extends AbstractMessage {
    public static final int CMD_MSG_AUTH_OK = 23792837;
    public static final int CMD_MSG_AUTH_WRONG = 55792822;
    public static final int CMD_MSG_REQUEST_USER_CREATE = 66732429;
    public static final int CMD_MSG_USER_CREATE_OK = 337328529;
    public static final int CMD_MSG_USER_CREATE_WRONG = 337328529;
    public static final int CMD_MSG_REQUES_FILE_DOWNLOAD = 398472948;
    public static final int CMD_MSG_REQUEST_FILES_LIST = 340274982;
    public static final int CMD_MSG_REQUEST_SERVER_DELETE_FILE = 23962746;

    private int type;
    private Object[] attachment;

    public Object[] getAttachment(){
        return attachment;
    }

    public CommandMessage(int type, Object... attachment){
        this.type = type;
        this.attachment = attachment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
