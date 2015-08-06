package procesamiento;

/**
 * <a rel="license" href="
 * http://creativecommons.org/licenses/by-nc-nd/3.0/deed.es"><img alt="Licencia 
 * Creative Commons" style="border-width:0" src="
 * http://i.creativecommons.org/l/by-nc-nd/3.0/88x31.png" /></a><br /><span xmln
 * s:dct="http://purl.org/dc/terms/" property="dct:title">Calculadora</span> por
 * <span xmlns:cc="http://creativecommons.org/ns#" property="cc:attributionName"
 * >Andrés Sarmiento Tobón</span> se encuentra bajo una <a rel="license" href="
 * http://creativecommons.org/licenses/by-nc-nd/3.0/deed.es">Licencia Creative 
 * Commons Atribución-NoComercial-SinDerivadas 3.0 Unported</a>
 * @author Andrés Sarmiento Tobón <href>ansarmientoto@unal.edu.co</href>
 */
public class CaracterIlegal extends Exception {
    private static final long serialVersionUID = -10000L;
    private String cadena;
    private int indice;

    /**
     * Creates a new instance of
     * <code>CaracterIlegal</code> without detail message.
     */
    private CaracterIlegal() {
    }

    /**
     * Constructs an instance of
     * <code>CaracterIlegal</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    private CaracterIlegal(String msg) {
        super(msg);
    }
    
    /**
     *
     * @param cadena
     * @param indice
     */
    public CaracterIlegal(String cadena, int indice) {
        this("Error cerca del caracter \"" + cadena.charAt(indice) + "\"");
        this.cadena = cadena;
        this.indice = indice;
    }

    /**
     *
     * @return
     */
    public String getCadena() {
        return cadena;
    }

    /**
     *
     * @return
     */
    public int getIndice() {
        return indice;
    }
}
