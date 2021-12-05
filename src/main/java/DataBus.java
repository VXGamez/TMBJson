import java.util.List;

public class DataBus {
    String status;
    String code;
    Tmp data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(Tmp data) {
        this.data = data;
    }

    public DataBus(String status, List<iBus> data) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Tmp getData() {
        return data;
    }
}
