import java.time.LocalDate;
import java.util.List;

public class Restaurante extends Location{

    public List<String> characteristics;

    public Restaurante(String name, List<Double> coordinates, String description, List<String> characteristics) {
        super(name, coordinates, description);
        this.characteristics = characteristics;
    }
}
