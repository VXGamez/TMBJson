import java.util.List;

public class Leg {
    private Double distance;
    private String mode;
    private String route;
    private Boolean interlineWithPreviousLeg;
    private legFrom from;
    private legTo to;
    private Long duration;
    private Boolean transitLeg;
    private String headsign;
    private String routeShortName;
    private String routeLongName;

    public Leg(Double distance, String mode, String route, Boolean interlineWithPreviousLeg, legFrom from, legTo to, Long duration, Boolean transitLeg, String headsign, String routeShortName, String routeLongName) {
        this.distance = distance;
        this.mode = mode;
        this.route = route;
        this.interlineWithPreviousLeg = interlineWithPreviousLeg;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.transitLeg = transitLeg;
        this.headsign = headsign;
        this.routeShortName = routeShortName;
        this.routeLongName = routeLongName;
    }

    public String getMode() {
        return mode;
    }

    public String getRoute() {
        return route;
    }

    public legFrom getFrom() {
        return from;
    }

    public legTo getTo() {
        return to;
    }

    public Long getDuration() {
        return duration;
    }

}