import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Main {


    public static void main(String[] args){
        Parades parada;
        Path current = Paths.get("src/main/resources/Localitzacions.json");
        String arxiu = current.toAbsolutePath().toString();
        Path current2 = Paths.get("src/main/resources/user.json");
        String users = current2.toAbsolutePath().toString();

        boolean salir = false;

        try(BufferedReader reader = new BufferedReader(new FileReader(arxiu))){
            Gson gson = new GsonBuilder().create();
            DataModel Locations = gson.fromJson(reader, DataModel.class);
            Funcions.orderLocations(Locations);
            System.out.println("\n\nFichero JSON leido correctamente.\n\n");
            File tmpDir = new File(users);
            boolean exists = tmpDir.exists();
            if(!exists) {
                Funcions.desaJson(users);
            }

            BufferedReader reader2 = new BufferedReader(new FileReader(users));
            Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
            User Usuari = gson2.fromJson(reader2, User.class);
            if(Usuari.isHasLocations()){
                Locations.getLocations().addAll(Usuari.getLocations_creades());
            }
            System.out.println("Benvingut de nou a l'aplicació de TMBJson " + Usuari.getUsername() +"!");
            List<Location> buscades= new ArrayList<>();
            List<Integer> mostradesT;
            List<RutesBuscades> rutesBuscades = new ArrayList<>(Usuari.getRutesBuscades());
            mostradesT = Funcions.paradesPrefes(Usuari);
            while (!salir){
                System.out.println("\n\n------------Menu------------\n");
                System.out.println("\t1. Gestió d'usuari\n\t2. Buscar localitzacions\n\t3. Plantejar Ruta\n\t4. Temps d'espera del bus\n\t5. Sortir\n");
                System.out.print("Selecciona la opció: ");
                Scanner s2 = new Scanner(System.in);
                int num = s2.nextInt();
                switch(num){
                    case 1:
                        boolean sortida_user = false;
                        while(!sortida_user){
                            System.out.println("\n\n\ta) Les meves localitzacions\n\tb) Historial de localitzacions\n\tc) Les meves rutes\n\td) Parades i estacions preferides\n\te) Estacions inaugurades el meu any de naixement\n\tf) Tornar al menu principal\n");
                            System.out.print("Selecciona la opció: ");
                            s2 = new Scanner(System.in);
                            char opcio = s2.next().charAt(0);
                            switch (opcio){
                                case 'a':
                                    System.out.println("\n\n------------Les Teves Localitzacions------------");
                                    Locations.printaLocations();
                                    System.out.println("-------------------------------------------------\n");
                                    boolean sortir_a = false;

                                    while(!sortir_a) {
                                        System.out.println("\n\nVols crear una nova localització? (sí/no)");
                                        s2 = new Scanner(System.in);
                                        String crear = s2.nextLine();

                                        while (!crear.equalsIgnoreCase("si") && !crear.equalsIgnoreCase("no") && !crear.equalsIgnoreCase("sí") ) {
                                            System.out.println("\nAquesta no és una opció vàlida.");
                                            System.out.println("\n\nVols crear una nova localització? (sí/no)");
                                            s2 = new Scanner(System.in);
                                            crear = s2.nextLine();
                                        }

                                        if (crear.equalsIgnoreCase("si") || crear.equalsIgnoreCase("sí")) {
                                            Location l;
                                            l = Funcions.novaLocalitzacio(Locations);
                                            Usuari.addLocation(l);
                                            Locations.getLocations().add(l);
                                            Usuari.setHasLocations(true);
                                            System.out.println("La informació s'ha registrat amb èxit!\n\n-La Salle Campus Barcelona\n\n");
                                        }else{
                                            sortir_a = true;
                                        }
                                    }
                                    break;
                                case 'b':
                                    if(buscades.size()>0){
                                        System.out.println("\n\nLocalitzacions buscades: ");
                                        Collections.reverse(buscades);
                                        Locations.printLocationList(buscades);
                                        Collections.reverse(buscades);
                                    }else{
                                        System.out.println("\n\nEncara no has buscat cap localització!\n Per buscar-ne una, accedeix a l'opció 2 del menú principal.");
                                    }
                                    break;
                                case 'c':
                                    if(rutesBuscades.size()>0){
                                        int contador = 1;
                                        for(RutesBuscades i : rutesBuscades){
                                            System.out.println("-> Ruta " + contador);
                                            System.out.println("\t- Origen: " + i.getOrigen());
                                            System.out.println("\t- Desti: " + i.getDestino());
                                            System.out.println("\t- Dia de Sortida: " + i.getDiaSortida() + " a les " + i.getHoraSortida());
                                            System.out.println("\t- Màxima distància caminant: " + i.getMaxWalkDistance() + " metres");
											System.out.println("\t- Combinació més ràpida:");
                                            Funcions.printaRecorregut(i.getItinerari());
                                            contador++;
                                        }
                                    }else{
                                        System.out.println("\n\nEncara no has realitzat cap ruta :(\nPer buscar-ne una, accedeix a l'opció 3 del menú principal");
                                    }
                                    break;
                                case 'd':
                                    if(Usuari.isHasFavorites()){
                                        double dist=0;
                                        parada = API.getParades("bus/parades");
                                        List<Feature> totes = new ArrayList<>();
                                        List<iBus> bus = null;
                                        for(Feature f : parada.getFeatures()){
                                            f.setTipo("BUS");
                                            totes.add(f);
                                        }
                                        parada = API.getParades("metro/estacions");
                                        for(Feature f : parada.getFeatures()){
                                            f.setTipo("METRO");
                                            totes.add(f);
                                        }
                                        System.out.println("\n\nLlistat de preferits amb parades a un radi de 0.5km");
                                        DataBus datos = null;
                                        for(Location l: Usuari.getPreferides()){
                                            int cont = 0;
                                            System.out.println("- " + l.getName());
                                            List<Integer> mostrades = new ArrayList<>();
                                            Funcions.mergeSort(totes, bus, l, 1);
                                            for(Feature f: totes){
                                                dist = Location.DistanciaKm(l.getCoordinates(),f.getGeometry().getCoordinates());
                                                if( dist <= 0.5 & !mostrades.contains(f.getProperties().getCODI_PARADA())){
                                                    cont++;
                                                    if(f.getTipo().equalsIgnoreCase("BUS")){
                                                        System.out.print("\t" + cont + ". " + f.getProperties().getNOM_PARADA() + " (" + f.getProperties().getCODI_PARADA() + ") " + f.getTipo() + " ");
                                                        datos = API.getInfoParada(f.getProperties().getCODI_PARADA().toString());
                                                        for(iBus b : datos.getData().getIbus()){
                                                        System.out.print(b.getLine() + " ");
                                                        }
                                                        System.out.println();
                                                    }else if(f.getTipo().equalsIgnoreCase("METRO")){
                                                        System.out.println("\t" + cont + ". " + f.getProperties().getNOM_ESTACIO() + " (" + f.getProperties().getCODI_ESTACIO() + ") " + f.getTipo() + f.getProperties().getPICTO_GRUP().replaceAll("L", " $0"));
                                                    }
                                                    mostrades.add(f.getProperties().getCODI_PARADA());
                                                    if(!mostradesT.contains(f.getProperties().getCODI_PARADA())){
                                                        mostradesT.add(f.getProperties().getCODI_PARADA());
                                                    }
                                                }
                                            }
                                            if(mostrades.size()==0){
                                            System.out.println("\tNo hi ha parades o estacions aprop d'aquesta localització.");
                                            }
                                        }
                                    }else{
                                        System.out.println("\n\nPer tenir parades i estacions preferides es requereix haver creat una localització preferida anteriorment.");
                                    }

                                    break;
                                case 'e':
                                    parada = API.getParades("metro/estacions");
                                    boolean inaug = false;
                                    System.out.println("Estacions inaugurades el " + Usuari.getYear() +":");
                                    for(Feature f : parada.getFeatures()){
                                        if(f.getProperties().getDATA_INAUGURACIO().getYear() == Usuari.getYear()){
                                            inaug = true;
                                            System.out.println("\t- " + f.getProperties().getNOM_ESTACIO()+ " " + f.getProperties().getNOM_LINIA());
                                        }
                                    }
                                    if(!inaug){
                                        System.out.println("\tCap estació de metro es va inaugurar el teu any de naixement :(");
                                    }
                                    break;
                                case 'f':
                                    sortida_user = true;
                                    break;
                                default:
                                    System.out.println("\n\nLa opció introduida no és una de les opcions del menú.\n\n");
                                    break;
                            }
                        }
                        break;
                    case 2:
                        System.out.println("\n\nIntrodueix el nom d'una Localització: ");
                        s2 = new Scanner(System.in);
                        String buscar = s2.nextLine();
                        Location l=null;
                        boolean found = false;
                        boolean guardat = false;
                        for(Location k : Locations.getLocations()){
                            if(k.getName().equals(buscar)){
                                l = k;
                                found = true;
                            }
                        }
                        if(found){
                            buscades.add(l);
                            System.out.println("Posició: " + l.getCoordinates().get(1) + ", " + l.getCoordinates().get(0));
                            System.out.println("Descripció: \n" + l.getDescription() + "\n\n");

                            System.out.println("Vols guardar la localització trobada com a preferida? (sí/no)");
                            s2 = new Scanner(System.in);
                            String opt = s2.nextLine();

                            while (!opt.equalsIgnoreCase("si") && !opt.equalsIgnoreCase("no") && !opt.equalsIgnoreCase("sí") ) {
                                System.out.println("\nAquesta no és una opció vàlida.");
                                System.out.println("Vols guardar la localització trobada com a preferida? (sí/no)");
                                s2 = new Scanner(System.in);
                                opt = s2.nextLine();
                            }
                            if(opt.equalsIgnoreCase("si") || opt.equalsIgnoreCase("sí")){
                                for(Location luc: Usuari.getPreferides()){
                                  if(luc.getName().equals(buscar)){
                                    guardat = true;
                                  }
                                }
                                if(!guardat){
                                    System.out.println("Tipus(casa/feina/estudis/oci/cultura): ");
                                    s2 = new Scanner(System.in);
                                    String tipus = s2.nextLine();
                                    while(!tipus.equalsIgnoreCase("casa") && !tipus.equalsIgnoreCase("feina") && !tipus.equalsIgnoreCase("oci")&& !tipus.equalsIgnoreCase("cultura")&& !tipus.equalsIgnoreCase("estudis") ){
                                        System.out.println("Error! S'ha d'introduir \"casa\", \"feina\", \"estudis\", \"oci\" o \"cultura\".");
                                        System.out.println("Tipus(casa/feina/estudis/oci/cultura): ");
                                        s2 = new Scanner(System.in);
                                        tipus = s2.nextLine();
                                    }
                                    System.out.println(l.getName() + " s'ha assignat com a una nova localització preferida.");
                                    if(!Usuari.isHasFavorites()){
                                        Usuari.setHasFavorites(true);
                                    }
                                    l.setTipus(tipus);
                                    l.setDataDesada(java.time.LocalDate.now());
                                    Usuari.addPreferit(l);
                                }else{
								    System.out.println("Aquesta localització ja l'has desat anteriorment com a preferida");
								}
                            }
                        }else{
                            System.out.println("Ho sentim, no hi ha cap localització amb aquest nom.");
                        }

                        break;
                    case 3:

                        List<Double> list = new ArrayList<>();
                        Ruta ruta;
                        int ok1=0;
                        int ok2=0;
                        System.out.println("\n\nOrigen? (lat,lon/nom localització)");
                        s2 = new Scanner(System.in);
                        String origen = s2.nextLine();
                        ok1 = Funcions.checkLocation(origen, Locations.getLocations(), list);
                        while(ok1==0){
                            System.out.println("Ho sentim, aquesta localitzacio no es vàlida :(");
                            System.out.println("\n\nOrigen? (lat,lon/nom localització)");
                            s2 = new Scanner(System.in);
                            origen = s2.nextLine();
                            ok1 = Funcions.checkLocation(origen, Locations.getLocations(), list);
                        }
                        System.out.println("\n\nDestí? (lat,lon/nom localitzacio)");
                        s2 = new Scanner(System.in);
                        String desti = s2.nextLine();
                        ok2 = Funcions.checkLocation(desti, Locations.getLocations(), list);
                        while(ok2==0){
                            System.out.println("Ho sentim, aquesta localitzacio no es vàlida :(");
                            System.out.println("\n\nDesti? (lat,lon/nom localització)");
                            s2 = new Scanner(System.in);
                            desti = s2.nextLine();
                            ok2 = Funcions.checkLocation(desti, Locations.getLocations(), list);
                        }
                        System.out.println("\n\nDia/hora seran sortida o d'arribada?");
                        s2 = new Scanner(System.in);
                        String sort_arr = s2.nextLine();
                        while(!sort_arr.equalsIgnoreCase("s") && !sort_arr.equalsIgnoreCase("a")){
                            System.out.println("\n\nError! S'ha d'introduir \"s\" o \"a\"!");
                            System.out.println("\n\nDia/hora seran sortida o d'arribada?");
                            s2 = new Scanner(System.in);
                            sort_arr = s2.nextLine();
                        }

                        System.out.println("\n\nDia? (MM-DD-YYYY)");
                        s2 = new Scanner(System.in);
                        String dia  = s2.nextLine();

                        System.out.println("\n\nHora? (HH:MMam/HH:MMpm)");
                        s2 = new Scanner(System.in);
                        String hora = s2.nextLine();

                        System.out.println("\n\nMàxima distància caminant en metres?");
                        s2 = new Scanner(System.in);
                        int dist = s2.nextInt();

                        List<Double> okF1 = new ArrayList<>();
                        List<Double> okF2 = new ArrayList<>();

                        if(ok1 == 1){
                            String [] parts= origen.split(", ");
                            double info1 = Double.parseDouble(parts[0]);
                            double info2 = Double.parseDouble(parts[1]);
                            okF1.add(info1);
                            okF1.add(info2);
                            if(ok2 == 1){
                                String [] parts2= desti.split(", ");
                                double info3 = Double.parseDouble(parts2[0]);
                                double info4 = Double.parseDouble(parts2[1]);
                                okF2.add(info3);
                                okF2.add(info4);
                            }else{
                                okF2 = Funcions.coordenades(desti, Locations.getLocations());
                            }
                        }else{
                            okF1 = Funcions.coordenades(origen, Locations.getLocations());
                            if(ok2 == 1){
                                String [] parts3= desti.split(", ");
                                double info5 = Double.parseDouble(parts3[0]);
                                double info6 = Double.parseDouble(parts3[1]);
                                okF2.add(info5);
                                okF2.add(info6);
                            }else{
                                okF2 = Funcions.coordenades(desti, Locations.getLocations());
                            }
                        }

                        ruta = API.getRuta(okF1, okF2 , dia, hora, sort_arr, dist, true);
                        System.out.println("\n\n");
                        if(ruta.getStatus() == null){
                            RutesBuscades r = new RutesBuscades();
							System.out.println("Combinació més ràpida:");
                            Funcions.printaRecorregut(ruta.getPlan().getItineraries().get(0));
                            r.setItinerari(ruta.getPlan().getItineraries().get(0));
                            r.setOrigen(origen);
                            r.setDestino(desti);
                            r.setDiaSortida(dia);
                            r.setHoraSortida(hora);
                            r.setMaxWalkDistance(dist);
                            rutesBuscades.add(r);
                        }else if(ruta.getStatus().equalsIgnoreCase("error")){
                            System.out.println("\n\n" + ruta.getError());
                        }
                        break;

                    case 4:
                        DataBus dades = null;
                        List<iBus> stops;
                        boolean trobat = false;
                        String codi = "";
                        List<Feature> list1 = null;
                        Location p = null;
                        while(!trobat){
                            System.out.println("\n\nIntrodueix el codi de parada: ");
                            s2 = new Scanner(System.in);
                            codi = s2.nextLine();
                            dades = API.getInfoParada(codi);
                            if(dades.getCode()==null){
                                trobat = true;
                            }else if(dades.getCode().equalsIgnoreCase("NotFoundException")){
                                System.out.println("Error! Codi de parada no existeix");
                            }
                        }
                        if (mostradesT.contains(codi)) {
                            System.out.println("Parada preferida!");
                        }
                        stops =dades.getData().getIbus();
                        Funcions.mergeSort(list1,stops , p, 2);
                        for(iBus bus : stops){
                            System.out.println(bus.getLine() + " - " +bus.getDestination() + " - " + bus.gettInMin() + " min");
                        }
                        break;
                    case 5:
                        Usuari.setRutesBuscades(rutesBuscades);
                        User.updateUser(Usuari);
                        salir = true;
                        break;
                    default:
                        System.out.println("\n\nLa opció introduida no és una de les opcions del menú.\n\n");
                    break;
                }
            }
        } catch (IOException e) {
        e.printStackTrace();
        }
    }


}
