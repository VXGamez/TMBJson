import java.util.List;

public class Plan {
    private Long date;
    private List<Itinerary> itineraries;

    public Plan(Long date, List<Itinerary> itineraries) {
        this.date = date;
        this.itineraries = itineraries;
    }
    public List<Itinerary> getItineraries() {
        return itineraries;
    }

}
