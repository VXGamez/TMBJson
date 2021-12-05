import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Funcions {


    static void orderLocations(DataModel l){
        for(Localitzacions loc : l.getLocalitzacions()){
            if(loc.getArchitect() != null){
                l.getMonuments().add(new Monument(loc.getName(), loc.getCoordinates(), loc.getDescription(), loc.getArchitect(), loc.getInauguration()));
            }else if(loc.getCharacteristics()!=null){
                l.getRestaurantes().add(new Restaurante(loc.getName(), loc.getCoordinates(), loc.getDescription(), loc.getCharacteristics()));
            }else if(loc.getStars()!=null){
                l.getHotels().add(new Hotel(loc.getName(), loc.getCoordinates(), loc.getDescription(), loc.getStars()));
            }
            l.getLocations().add(new Location(loc.getName(), loc.getCoordinates(), loc.getDescription()));
        }
    }

    static void desaJson(String users) throws IOException {
        System.out.println("Siusplau introdueix un nom de usuari: ");
        Scanner s1 = new Scanner(System.in);
        String user = s1.nextLine();
        System.out.println("Siusplau introdueix el teu correu electrònic: ");
        s1 = new Scanner(System.in);
        String email = s1.nextLine();
        System.out.println("Siusplau introdueix el teu any de naixement: ");
        s1 = new Scanner(System.in);
        int any = s1.nextInt();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user);
        jsonObject.put("email", email);
        jsonObject.put("year", any);
        jsonObject.put("HasLocation", false);
        List<Location> loc= new ArrayList<>();
        List<RutesBuscades> loc2= new ArrayList<>();
        jsonObject.put("HasFavorites", false);
        jsonObject.put("locations_creades", loc);
        jsonObject.put("preferides", loc);
        jsonObject.put("rutesBuscades", loc2);
        FileWriter file = new FileWriter(users);
        file.write(jsonObject.toJSONString());
        file.close();
        System.out.println("\nS'ha registrar la informació amb èxit!");
    }

    static Location novaLocalitzacio(DataModel Locations){
        List<Double> list = new ArrayList<>();
        boolean ok = false;

        System.out.println("\n\nNom de la localització: ");
        Scanner s3 = new Scanner(System.in);
        String nom_l = s3.nextLine();
        while (Locations.ifLocationExists(nom_l)) {
            System.out.println("\n\nError! Aquesta localització ja existeix.");
            System.out.println("\nNom de la localització: ");
            s3 = new Scanner(System.in);
            nom_l = s3.nextLine();
        }
        int okey =3;
        ok=false;
        while(!ok || okey == 3){
            System.out.println("Longitud: ");
            s3 = new Scanner(System.in);
            String longitud_l = s3.nextLine();
            System.out.println("Latitud: ");
            s3 = new Scanner(System.in);
            String latitud_l = s3.nextLine();
            list.add(Double.parseDouble(longitud_l));
            list.add(Double.parseDouble(latitud_l));
            ok = Funcions.checkCoordinates(list.get(0), list.get(1));
            okey = Funcions.checkLocation(".-|dev|-.",Locations.getLocations(), list);
            if(!ok){
                System.out.println("Error! Coordenades no vàlides!");
            }else if(okey == 3){
                System.out.println("Error! Coordenades ja existeixen amb un altre nom!");
            }
        }
        System.out.println("Descripció: ");
        s3 = new Scanner(System.in);
        String descripcio = s3.nextLine();
        Location l = new Location(nom_l, list, descripcio);
        return l;
    }


    static void printaRecorregut(Itinerary it){
        System.out.println("\t  Temps de trajecte: " + (int) Math.round((float)it.getDuration()/60) + " min");
        System.out.println("\t  Origen");
        System.out.println("\t  |");
        for(int j = 0; j < it.getLegs().size(); j++){
            if(it.getLegs().get(j).getMode().equalsIgnoreCase("WALK")){
                System.out.println("\t  camina durant " + (int) Math.round(((float)it.getLegs().get(j).getDuration()/60)) + " min");
                System.out.println("\t  |");
            }else if(it.getLegs().get(j).getMode().equalsIgnoreCase("BUS") || (it.getLegs().get(j).getMode().equalsIgnoreCase("SUBWAY"))){
                System.out.println("\t  " + it.getLegs().get(j).getRoute() + " " + it.getLegs().get(j).getFrom().getName() + " (" + it.getLegs().get(j).getFrom().getStopCode()+ ") " + "-> " + it.getLegs().get(j).getTo().getName()+ " (" + it.getLegs().get(j).getTo().getStopCode()+ ") " + (int) Math.round(((float)it.getLegs().get(j).getDuration()/60)) + " min");
                System.out.println("\t  |");
            }
        }
        System.out.println("\t  Destí");
    }

    static Boolean checkCoordinates(double longitude, double latitude){
        boolean okey = false;
        if((longitude>=-180.0000 && longitude<=180.0000) && (latitude>=-90.0000 && latitude<=90.0000)){
            okey = true;
        }
        return okey;
    }

    static List<Integer> paradesPrefes(User Usuari) throws IOException {
        Parades parada;
        List<Integer> valors = new ArrayList<>();
        List<iBus> bus2 = null;
        if(Usuari.isHasFavorites()){
            double dist=0;
            parada = API.getParades("bus/parades");
            List<Feature> totes = new ArrayList<>();
            for(Feature f : parada.getFeatures()){
                f.setTipo("BUS");
                totes.add(f);
            }
            parada = API.getParades("metro/estacions");
            for(Feature f : parada.getFeatures()){
                f.setTipo("METRO");
                totes.add(f);
            }
            for(Location l: Usuari.getPreferides()){
                mergeSort(totes,bus2, l, 1);
                for(Feature f: totes){
                    dist = Location.DistanciaKm(l.getCoordinates(),f.getGeometry().getCoordinates());
                    if( dist <= 0.5 & !valors.contains(f.getProperties().getCODI_PARADA())){
                        valors.add(f.getProperties().getCODI_PARADA());
                    }
                }
            }
        }
        return valors;
    }


    static int checkLocation(String info, List<Location> localitzacions, List<Double> coordenades){
        int f = 0;
        boolean ok;
        if(info.contains(",") && info.contains(".")){
            String [] parts= info.split(", ");
            double info1 = Double.parseDouble(parts[0]);
            double info2 = Double.parseDouble(parts[1]);

            List<Double> coord = new ArrayList<>();
            coord.add(info1);
            coord.add(info2);
            ok = checkCoordinates(info1, info2);

            if(ok){
                for(Location l : localitzacions){
                    if(l.getCoordinates().containsAll(coord)){
                        f = 1;
                    }
                }
            }
        }else if(info.equals(".-|dev|-.")){
            for(Location l : localitzacions){
                if(l.getCoordinates().containsAll(coordenades)){
                    f = 3;
                }
            }
        }else{
            for(Location l : localitzacions){
                if(l.getName().equalsIgnoreCase(info)){
                    f = 2;
                }
            }
        }
        return f;
    }

    static List<Double> coordenades(String nomLoc, List<Location> localitzacions){
        List<Double> value = new ArrayList<>();
        for(Location l : localitzacions){
            if(nomLoc.equalsIgnoreCase(l.getName())){
                value = l.getCoordinates();
            }
        }
        Collections.reverse(value);
        return value;
    }

    private static void sort(List<Feature> alist, List<Feature> blist,List<iBus> alist2, List<iBus> blist2, List<Feature> list, List<iBus> list2, Location l, int criterio) {
        int alistIndex = 0, blistIndex = 0, listIndex = 0;
        if(criterio == 1){
            while (alistIndex < alist.size() && blistIndex < blist.size()) {
                if (Location.DistanciaKm(l.getCoordinates(), alist.get(alistIndex).getGeometry().getCoordinates()) < Location.DistanciaKm(l.getCoordinates(),blist.get(blistIndex).getGeometry().getCoordinates())) {
                    list.set(listIndex, alist.get(alistIndex));
                    alistIndex++;
                }else{
                    list.set(listIndex, blist.get(blistIndex));
                    blistIndex++;
                }
                listIndex++;
            }
            while (alistIndex < alist.size()) {
                list.set(listIndex, alist.get(alistIndex));
                alistIndex++;
                listIndex++;
            }
            while (blistIndex < blist.size()) {
                list.set(listIndex, blist.get(blistIndex));
                blistIndex++;
                listIndex++;
            }
        }else if(criterio == 2){
            while (alistIndex < alist2.size() && blistIndex < blist2.size()) {
                if(alist2.get(alistIndex).gettInMin()< blist2.get(blistIndex).gettInMin()) {
                    list2.set(listIndex, alist2.get(alistIndex));
                    alistIndex++;
                }else{
                    list2.set(listIndex, blist2.get(blistIndex));
                    blistIndex++;
                }
                listIndex++;
            }
            while (alistIndex < alist2.size()) {
                list2.set(listIndex, alist2.get(alistIndex));
                alistIndex++;
                listIndex++;
            }
            while (blistIndex < blist2.size()) {
                list2.set(listIndex, blist2.get(blistIndex));
                blistIndex++;
                listIndex++;
            }
        }
    }

    static void mergeSort(List<Feature> list, List<iBus> list2, Location l, int criterio) {
        if(criterio == 1){
            List<Feature> alist = new ArrayList<>(list.subList(0, list.size() / 2));
            List<Feature> blist = new ArrayList<>(list.subList(list.size() / 2, list.size()));
            List<iBus> alist2 = new ArrayList<>();
            List<iBus> blist2 = new ArrayList<>();
            if (list.size() > 1) {
                mergeSort(alist,list2, l, criterio);
                mergeSort(blist,list2, l, criterio);
                sort(alist, blist, alist2, blist2, list, list2, l, criterio);
            }
        }else if(criterio == 2){
            List<Feature> alist = new ArrayList<>();
            List<Feature> blist = new ArrayList<>();
            List<iBus> alist2 = new ArrayList<>(list2.subList(0, list2.size() / 2));
            List<iBus> blist2 = new ArrayList<>(list2.subList(list2.size() / 2, list2.size()));
            if (list2.size() > 1) {
                mergeSort(list, alist2, l, criterio);
                mergeSort(list, blist2, l, criterio);
                sort(alist, blist, alist2, blist2, list, list2, l, criterio);
            }
        }
    }



}
