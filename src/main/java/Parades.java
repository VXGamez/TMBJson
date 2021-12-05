import java.sql.Timestamp;
import java.util.List;

public class Parades {
    private Integer totalFeatures;
    private Integer numberMatched;
    private Integer numberReturned;
    private Timestamp timeStamp;
    private String type;
    private List<Feature> features;


    public List<Feature> getFeatures() {
        return features;
    }

}
