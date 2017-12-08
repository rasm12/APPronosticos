package richard.com.navigationdrawer_demo.java;

import java.util.List;

/**
 * Created by Richard on 29/11/2017.
 */

public class ResumenPorUsuario  extends GenericoRespuesta{
    /**
     *
     * "usuariosList": [{

            "nombre": "DXX",
            "total": 1
        }, {
            "nombre": "R12",
            "total": 2
        }]
     */
    private List<Entidad> usuariosList;


    public List<Entidad> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<Entidad> usuariosList) {
        this.usuariosList = usuariosList;
    }
}
