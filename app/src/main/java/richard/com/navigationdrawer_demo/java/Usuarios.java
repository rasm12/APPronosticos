package richard.com.navigationdrawer_demo.java;

/**
 * Created by Richard on 26/11/2017.
 */

public class Usuarios {
    private int icon;
    private String nombre;
    private Integer total;

    public Usuarios() {
        super();
    }

    public Usuarios(int icon, String nombre, Integer total) {
        super();
        this.icon = icon;
        this.nombre = nombre;
        this.total = total;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
