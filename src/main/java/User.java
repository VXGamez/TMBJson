import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class User {
    private String username;
    private String email;
    private int year;
    private List<Location> locations_creades;
    private List<Location> preferides;
    private boolean HasLocations;
    private boolean HasFavorites;
    private List<RutesBuscades> rutesBuscades;



    public List<RutesBuscades> getRutesBuscades() {
        return rutesBuscades;
    }

    public void setRutesBuscades(List<RutesBuscades> rutesBuscades) {
        this.rutesBuscades = rutesBuscades;
    }

    public boolean isHasFavorites() {
        return HasFavorites;
    }

    public void setHasFavorites(boolean hasFavorites) {
        HasFavorites = hasFavorites;
    }

    public void addPreferit(Location preferides) {
        this.preferides.add(preferides);
    }

    public void setHasLocations(boolean hasLocation) {
        HasLocations = hasLocation;
    }

    public boolean isHasLocations() {
        return HasLocations;
    }

    public List<Location> getLocations_creades() {
        return locations_creades;
    }

    public void addLocation(Location loc){
        this.locations_creades.add(loc);
    }

    public String getUsername() {
        return username;
    }

    public int getYear() {
        return year;
    }

    public List<Location> getPreferides(){
      return preferides;
    }

    public static void updateUser(User usr){
        Path current2 = Paths.get("src/main/resources/user.json");
        String users = current2.toAbsolutePath().toString();

        Gson prova = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(users)) {
            prova.toJson(usr, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}
