import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Location {

    public String name;
    public List<Double> coordinates;
    public String description;
    public LocalDate dataDesada;
    public String tipus;

    public Location(String name, List<Double> coordinates, String description) {
        this.name = name;
        this.coordinates = coordinates;
        this.description = description;

    }

    public LocalDate getDataDesada() {
        return dataDesada;
    }

    public String getTipus() {
        return tipus;
    }

    public void setDataDesada(LocalDate dataDesada) {
        this.dataDesada = dataDesada;
    }

    public void setTipus(String tipus) {
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

    public static double DistanciaKm(List<Double> origen,List<Double> destino){
        double sindLat = Math.sin(Math.toRadians(destino.get(1) - origen.get(1)) / 2);
        double sindLng = Math.sin(Math.toRadians(destino.get(0) - origen.get(0)) / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)*Math.cos(Math.toRadians(origen.get(1))) * Math.cos(Math.toRadians(destino.get(1)));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        return 6371 * va2;
    }



}
