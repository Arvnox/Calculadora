/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class FuncionNativa extends Funcion {
    java.lang.reflect.Method metodoJava;
    java.lang.Class clase;
    
    /**
     *
     * @param nombre
     * @param numeroArgumentos
     * @param metodoJava
     * @param clase
     */
    public FuncionNativa(String nombre, int numeroArgumentos, 
            java.lang.reflect.Method metodoJava, Class clase) {
        this.numeroArgumentos = numeroArgumentos;
        this.metodoJava = metodoJava;
        this.clase = clase;
        this.nativa = true;
        this.nombre = nombre;
        
        for (Class<?> claseParametro : metodoJava.getParameterTypes()) {
            if(claseParametro.isAssignableFrom(String.class)) {
                this.tieneArgumentosString = true;
                break;
            }
        }
    }

    /**
     *
     * @param nombre
     * @param numeroArgumentos
     * @param metodoJava
     * @param clase
     * @param descripcion
     */
    public FuncionNativa(String nombre, int numeroArgumentos,
            java.lang.reflect.Method metodoJava, Class clase, 
            String descripcion) {
        this(nombre, numeroArgumentos, metodoJava, clase);
        this.descripcion = descripcion.intern();
    }

    /**
     *
     * @param nombre
     * @param numeroArgumentos
     * @param metodoJava
     * @param clase
     * @param descripcion
     * @param descripcionLarga
     */
    public FuncionNativa(String nombre, int numeroArgumentos,
            java.lang.reflect.Method metodoJava, Class clase, 
            String descripcion, String descripcionLarga) {
        this(nombre, numeroArgumentos, metodoJava, clase, descripcion);
        this.descripcionLarga = descripcionLarga.intern();
    }
    
    /**
     *
     * @param nombre
     * @param numeroArgumentos
     * @param metodoJava
     * @param clase
     * @param descripcion
     * @param descripcionLarga
     * @param categorizacion  
     */
    public FuncionNativa(String nombre, int numeroArgumentos,
            java.lang.reflect.Method metodoJava, Class clase, 
            String descripcion, String descripcionLarga, String[] 
                    categorizacion) {
        this(nombre, numeroArgumentos, metodoJava, clase, descripcion, 
                descripcionLarga);
        
        if (categorizacion != null) {
            this.categorizacion = categorizacion;
        }
    }
    
    /**
     * Método principal de FuncionNativa.
     *
     * @param args Los argumentos del programa.
     */
    public static void main(String[] args) {
        try {
            //FuncionNativa instancia = new FuncionNativa();
//            System.out.println(Funcion.obtenerFuncionNativa("cos").evaluar(
//                    new Object[]{Math.PI / 2}));
            System.out.println(Funcion.obtenerFuncionNativa("integrar").evaluar(
                    new Object[]{"x ^ 2", 0, 10}));
        } catch (IllegalAccessException | IllegalArgumentException 
                | InvocationTargetException ex) {
            Logger.getLogger(FuncionNativa.class.getName()).log(Level.SEVERE, 
                    null, ex);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Object computar() {
        if(computable) {
            try {
                return new BigDecimal(evaluar(argumentosActuales).toString());
            } catch (    IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException ex) {
//                Logger.getLogger(FuncionNativa.class.getName()).log(
//                        Level.SEVERE, null, ex);
                return toString();
            }
        } else {
            return toString();
        }
    }

    /**
     *
     * @return
     */
    @Override
    public String obtenerDescripcion() {
        return descripcion;
    }

    /**
     *
     * @return
     */
    @Override
    public String obtenerDescripcionLarga() {
        return descripcionLarga;
    }
    
    private Object evaluar(Object[] lista) throws IllegalAccessException, 
            IllegalArgumentException, InvocationTargetException {
        Object[] argumentosDouble = new Object[lista.length];
        
        for (int i = 0; i < argumentosDouble.length; i++) {
            if (lista[i] instanceof Double) {
                argumentosDouble[i] = ((Double) lista[i]).doubleValue();
            } else if(lista[i] instanceof BigDecimal) {
                argumentosDouble[i] = ((BigDecimal) lista[i]).doubleValue();
            } else {
                argumentosDouble[i] = lista[i];
            }
        }
        
        return metodoJava.invoke(clase, argumentosDouble);
    }
}
