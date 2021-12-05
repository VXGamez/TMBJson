import java.util.List;

public class Itinerary {
    private Long duration;
    private Long transitTime;
    private Boolean walkLimitExceeded;
    private Long transfers;
    private List<Leg> legs;


    public Itinerary(Long duration, Long transitTime, Boolean walkLimitExceeded, Long transfers, List<Leg> legs) {
        this.duration = duration;
        this.transitTime = transitTime;
        this.walkLimitExceeded = walkLimitExceeded;
        this.transfers = transfers;
        this.legs = legs;
    }


    public Long getDuration() {
        return duration;
    }

    public List<Leg> getLegs() {
        return legs;
    }

}
