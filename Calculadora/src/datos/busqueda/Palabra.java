/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datos.busqueda;

import java.util.Objects;
import java.util.TreeSet;

/**
 *
 * @author Andrés Sarmiento Tobón <ansarmientoto at unal.edu.co>
 */
public class Palabra implements Comparable<Palabra> {
    
    private static TreeSet<Palabra> palabras = new TreeSet<>();
    private String palabra;
    private TreeSet<Documento> documentos = new TreeSet<>();

    static void limpiarPalabras() {
        palabras = new TreeSet<>();
    }
    
    private Palabra(String palabra) {
        this.palabra = palabra.intern();
    }
    
    /**
     * Método principal de Palabra.
     * 
     * @param args Los argumentos del programa.
     */
    public static void main(String[] args) {
//        Palabra instancia = new Palabra();
    }
    
    public static Palabra obtenerPalabra(String palabra) {
        if(palabra == null || palabra.length() == 0) {
            return null;
        }
        
        for (Palabra palabraActual : palabras) {
            if(palabraActual.palabra.equalsIgnoreCase(palabra)) {
                return palabraActual;
            }
        }
        
        Palabra palabraActual = new Palabra(palabra);
        palabras.add(palabraActual);
        return palabraActual;
    }
    
    public Documento[] getListaDocumentos() {
        if(documentos == null || documentos.isEmpty()) {
            return new Documento[0];
        }
        
        return documentos.toArray(new Documento[documentos.size()]);
    }
    
    public void anadirDocumento(Documento documento) {
        if (documento != null) {
            documentos.add(documento);
        }
    }
    
    public String obtenerPalabra() {
        return palabra;
    }
    
    @Override
    public String toString() {
        return palabra;
    }

    @Override
    public int compareTo(Palabra o) {
        if(o == null) {
            return 0;
        }
        
        return palabra.compareToIgnoreCase(o.palabra);
    }
    
    @Override
    public boolean equals(Object o) {
        return (o == null || !(o instanceof Palabra))? false : compareTo(
                (Palabra) o) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.palabra);
        return hash;
    }
}
