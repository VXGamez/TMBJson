
public class Feature {

    private String type;
    private String id;
    private Geometry geometry;
    private String geometryName;
    private Properties properties;
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public Properties getProperties() {
        return properties;
    }

}
