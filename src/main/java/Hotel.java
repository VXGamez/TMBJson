import java.time.LocalDate;
import java.util.List;

public class Hotel extends Location{

    public Integer stars;

    public Hotel(String name, List<Double> coordinates, String description, Integer stars) {
        super(name, coordinates, description);
        this.stars = stars;
    }
}
