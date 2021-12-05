public class RutesBuscades {
    private String origen;
    private String destino;
    private String diaSortida;
    private String horaSortida;
    private int maxWalkDistance;
    private Itinerary itinerari;

    public RutesBuscades(){

    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDiaSortida() {
        return diaSortida;
    }

    public void setDiaSortida(String diaSortida) {
        this.diaSortida = diaSortida;
    }

    public String getHoraSortida() {
        return horaSortida;
    }

    public void setHoraSortida(String horaSortida) {
        this.horaSortida = horaSortida;
    }

    public int getMaxWalkDistance() {
        return maxWalkDistance;
    }

    public void setMaxWalkDistance(int maxWalkDistance) {
        this.maxWalkDistance = maxWalkDistance;
    }

    public Itinerary getItinerari() {
        return itinerari;
    }

    public void setItinerari(Itinerary itinerari) {
        this.itinerari = itinerari;
    }
}
