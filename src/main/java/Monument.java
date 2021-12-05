import java.time.LocalDate;
import java.util.List;

public class Monument extends Location{

    public String architect;
    public Integer inauguration;


    public Monument(String name, List<Double> coordinates, String description, String architect, Integer inauguration) {
        super(name, coordinates, description);
        this.architect = architect;
        this.inauguration = inauguration;
    }
}
