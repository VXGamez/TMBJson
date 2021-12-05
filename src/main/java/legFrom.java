
public class legFrom {
    private String name;
    private Double lon;
    private Double lat;
    private String orig;
    private String stopId;
    private String stopCode;

    public legFrom(String name, Double lon, Double lat, String orig, String stopId, String stopCode) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.orig = orig;
        this.stopId = stopId;
        this.stopCode = stopCode;
    }


    public String getName() {
        return name;
    }
    public String getStopCode() {
        return stopCode;
    }

}