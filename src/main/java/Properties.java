import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Properties {


    private Long CODI_ESTACIO;
    private String NOM_ESTACIO;
    private String PICTO;
    private String PICTO_GRUP;
    private Integer ID_PARADA;
    private Integer CODI_PARADA;
    private String NOM_PARADA;
    private String NOM_LINIA;
    private String ADRECA;
    private Integer ID_POBLACIO;
    private String NOM_POBLACIO;
    private Integer ID_DISTRICTE;
    private String NOM_DISTRICTE;
    private Date DATA;
    private Date DATA_INAUGURACIO;
    private String NOM_VIA;
    private String NOM_PROPERA_VIA;

    public String getNOM_LINIA() {
        return NOM_LINIA;
    }

    public String getPICTO_GRUP() {
        return PICTO_GRUP;
    }

    public Long getCODI_ESTACIO() {
        return CODI_ESTACIO;
    }

    public LocalDate getDATA_INAUGURACIO() {
        Instant instant = this.DATA_INAUGURACIO.toInstant();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return instant.atZone(defaultZoneId).toLocalDate();
    }

    public String getNOM_ESTACIO() {
        return NOM_ESTACIO;
    }

    public Integer getCODI_PARADA() {
        return CODI_PARADA;
    }

    public String getNOM_PARADA() {
        return NOM_PARADA;
    }

}
