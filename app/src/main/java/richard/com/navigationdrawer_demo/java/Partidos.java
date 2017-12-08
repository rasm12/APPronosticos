package richard.com.navigationdrawer_demo.java;

/**
 * Created by Richard on 23/11/2017.
 */

public class Partidos {
    /**
     *  "nroFecha": "1",
     "local": "3",
     "visita": "2",
     "golLocal": null,
     "golVisita": null,
     "resultadoFinal": null,
     "localName": "Sol",
     "guestName": "Olimpia"
     */

    private String nroFecha;
    private String local;
    private String visita;
    private String golLocal;
    private String golVisita;
    private String resultadoFinal;
    private String localName;
    private String guestName;

    public String getNroFecha() {
        return nroFecha;
    }

    public void setNroFecha(String nroFecha) {
        this.nroFecha = nroFecha;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getGolLocal() {
        return golLocal;
    }

    public void setGolLocal(String golLocal) {
        this.golLocal = golLocal;
    }

    public String getGolVisita() {
        return golVisita;
    }

    public void setGolVisita(String golVisita) {
        this.golVisita = golVisita;
    }

    public String getResultadoFinal() {
        return resultadoFinal;
    }

    public void setResultadoFinal(String resultadoFinal) {
        this.resultadoFinal = resultadoFinal;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getVisita() {
        return visita;
    }

    public void setVisita(String visita) {
        this.visita = visita;
    }
}
