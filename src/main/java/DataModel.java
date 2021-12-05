
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;

public class DataModel {

    private List<Localitzacions> locations;
    private List<Location> location = new ArrayList<>();
    private List<Hotel> hotels = new ArrayList<>();
    private List<Monument> monuments = new ArrayList<>();
    private List<Restaurante> restaurantes = new ArrayList<>();


    public List<Location> getLocations() {
        return location;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public List<Monument> getMonuments() {
        return monuments;
    }

    public List<Restaurante> getRestaurantes() {
        return restaurantes;
    }

    public List<Localitzacions> getLocalitzacions() {
        return locations;
    }

    public boolean ifLocationExists(String location){
        boolean exists = false;
        for(Location l: this.location){
            if(location.equals(l.getName())){
                exists = true;
            }
        }
        return exists;
    }

    public void printaLocations(){
        for(Location l: this.location){
            System.out.println("\t- " + l.getName());
        }
    }

    public void printLocationList(List<Location> loc){
        for(Location l: loc){
            System.out.println("\t- " + l.getName());
        }
    }


    @Override
    public String toString() {
    return new ToStringBuilder(this).append("locations", location).toString();
    }

}
