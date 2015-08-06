/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datos;

import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import procesamiento.CaracterIlegal;
import procesamiento.Procesador;

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
public class FuncionPersonalizada extends Funcion {
    private String[] rpn;
    private String[] argumentos;

    /**
     *
     * @param nombre
     * @param argumentos
     * @param rpn
     */
    public FuncionPersonalizada(String nombre, String[] argumentos, 
            String[] rpn) {
        this.nombre = nombre;
        this.rpn = rpn;
        
        if (argumentos != null) {
            this.argumentos = argumentos;
        } else {
            this.argumentos = new String[0];
        }
        
        this.numeroArgumentos = this.argumentos.length;
    }
    
    /**
     * Método principal de FuncionPersonalizada.
     * 
     * @param args Los argumentos del programa.
     */
    public static void main(String[] args) {
//        FuncionPersonalizada instancia = new FuncionPersonalizada();
    }

    /**
     *
     * @return
     */
    @Override
    public Object computar() {
        try {
            TreeMap<String, String[]> mapa = new TreeMap<>();
            
            for (int i = 0; i < argumentosActuales.length; i++) {
                mapa.put(argumentos[i], Procesador.convertirRPN(
                        argumentosActuales[i].toString()));
            }
            
            Queue<String> cola = new LinkedList<>();
            
            for (int i = 0; i < rpn.length; i++) {
                Object cambio = mapa.get(rpn[i]);
                
                if(cambio != null) {
                    for (String item : (String[]) cambio) {
                        cola.offer(item);
                    }
                } else {
                    cola.offer(rpn[i]);
                }
            }
            
            String[] rpnActual = cola.toArray(new String[cola.size()]);
//            System.out.println(Arrays.toString(rpnActual));
            return Procesador.evaluarRPN(rpnActual);
        } catch (CaracterIlegal ex) {
            Logger.getLogger(FuncionPersonalizada.class.getName()).log(
                    Level.SEVERE, null, ex);
            return toString();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String obtenerDescripcion() {
        return "";
    }

    /**
     *
     * @return
     */
    @Override
    public String obtenerDescripcionLarga() {
        return "";
    }
}
