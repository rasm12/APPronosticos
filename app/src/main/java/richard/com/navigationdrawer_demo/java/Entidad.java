package richard.com.navigationdrawer_demo.java;

/**
 * Created by Richard on 29/11/2017.
 */

public class Entidad {
    private Integer total;
    private String nombre;
    private String contenido = "CONTENIDO";

    public Entidad(Integer total, String nombre, String contenido) {
        this.total = total;
        this.nombre = nombre;
        this.contenido = contenido;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
