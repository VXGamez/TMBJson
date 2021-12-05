import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class iBus {


    private String routeId;
    private String line;
    @SerializedName("text-ca")
    @Expose
    private String textCa;
    @SerializedName("t-in-s")
    @Expose
    private Long tInS;
    private String destination;
    @SerializedName("t-in-min")
    @Expose
    private Long tInMin;

    public String getLine() {
        return line;
    }

    public String getDestination() {
        return destination;
    }

    public Long gettInMin() {
        return tInMin;
    }

}
