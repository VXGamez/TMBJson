

public class legTo {
    private String name;
    private String stopId;
    private String stopCode;
    private Double lon;
    private Double lat;
    private String orig;

    public legTo(String name, String stopId, String stopCode, Double lon, Double lat, String orig) {
        this.name = name;
        this.stopId = stopId;
        this.stopCode = stopCode;
        this.lon = lon;
        this.lat = lat;
        this.orig = orig;
    }


    public String getName() {
        return name;
    }

    public String getStopCode() {
        return stopCode;
    }

}