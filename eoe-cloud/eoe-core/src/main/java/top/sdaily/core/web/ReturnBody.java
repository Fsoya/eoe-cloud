package top.sdaily.core.web;

/**
 * Created by soya on 2016/12/13.
 */
public class ReturnBody {
    private int status = 3001;
    private String msg;
    private Object data;

    public int getStatus() {
        return status;
    }

    public ReturnBody setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ReturnBody setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ReturnBody setData(Object data) {
        this.data = data;
        return this;
    }

    public static ReturnBody success(){
        return new ReturnBody();
    }

    public static ReturnBody failed(){
        return new ReturnBody().setStatus(3002);
    }

    public static ReturnBody error(){
        return new ReturnBody().setStatus(3003);
    }
}
