import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Localitzacions {

    public String name;
    public List<Double> coordinates;
    public String description;
    public String architect;
    public Integer inauguration;
    public List<String> characteristics;
    public Integer stars;
    public LocalDate dataDesada;
    public String tipus;

    public Localitzacions(String name, List<Double> coordinates, String description, String architect, Integer inauguration, List<String> characteristics, Integer stars, LocalDate dataDesada, String tipus) {
        this.name = name;
        this.coordinates = coordinates;
        this.description = description;
        this.architect = architect;
        this.inauguration = inauguration;
        this.characteristics = characteristics;
        this.stars = stars;
        this.dataDesada = dataDesada;
        this.tipus = tipus;
    }

    public String getName() {
        return name;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public String getDescription() {
        return description;
    }

    public String getArchitect() {
        return architect;
    }

    public Integer getInauguration() {
        return inauguration;
    }

    public List<String> getCharacteristics() {
        return characteristics;
    }

    public Integer getStars() {
        return stars;
    }

    public LocalDate getDataDesada() {
        return dataDesada;
    }

    public String getTipus() {
        return tipus;
    }
}
