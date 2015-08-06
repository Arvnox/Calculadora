/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datos;

/**
 *
 * @author Andrés Sarmiento Tobón <ansarmientoto at unal.edu.co>
 */
public class FuncionOculta implements Comparable<FuncionOculta> {
    /**
     *
     */
    public final String funcion;
    /**
     *
     */
    public final String variable;

    /**
     *
     * @param funcion
     * @param variable
     */
    public FuncionOculta(String funcion, String variable) {
        this.funcion = funcion;
        this.variable = variable;
    }
    
    /**
     * Método principal de FuncionOculta.
     * 
     * @param args Los argumentos del programa.
     */
    public static void main(String[] args) {
        FuncionOculta instancia = new FuncionOculta("hola", "chao");
    }
    
    @Override
    public String toString() {
        return "{\"" + variable + "\", \"" + funcion + "\"}";
    }

    @Override
    public int compareTo(FuncionOculta o) {
        if(o == null) {
            return 0;
        }
        
        int comparaVariable = variable.compareTo(o.variable);
        int comparaFuncion = funcion.compareToIgnoreCase(o.funcion);
        
        if(comparaVariable == 0) {
            return comparaFuncion;
        } else {
            return comparaVariable;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof FuncionOculta) {
            return compareTo((FuncionOculta) o) == 0;
        } else {
            return false;
        }
    }
}
