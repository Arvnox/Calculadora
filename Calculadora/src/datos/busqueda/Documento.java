/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datos.busqueda;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Andrés Sarmiento Tobón <ansarmientoto at unal.edu.co>
 */
public class Documento implements Comparable<Documento> {
    
    private static TreeSet<Documento> documentos = new TreeSet<>();
    /**
     * Cada palabra con su respectivo índice de apariciones en el texto.
     */
    private TreeMap<String, List<Integer>> palabras = new TreeMap<>();
    private String documento;
    private int numeroPalabras;
    
    private Documento(String documento) {
        this.documento = documento == null ? "" : documento.intern();
        procesarCadena();
    }
    
    /**
     * Método principal de Documento.
     * 
     * @param args Los argumentos del programa.
     */
    public static void main(String[] args) {
//        Documento instancia = new Documento();
    }
    
    public static Documento obtenerDocumento(String documento) {
        for (Documento documentoActual : documentos) {
            if(documentoActual.documento.equals(documento)) {
                return documentoActual;
            }
        }
        
        Documento documentoNuevo = new Documento(documento);
        documentos.add(documentoNuevo);
        return documentoNuevo;
    }
    
    public static void anadirDocumento(String documento) {
        for (Documento documentoActual : documentos) {
            if(documentoActual.documento.equals(documento)) {
                return;
            }
        }
        
        Documento documentoNuevo = new Documento(documento);
        documentos.add(documentoNuevo);
    }
    
    @Override
    public int compareTo(Documento o) {
        return o == null ? 0 : documento.compareTo(o.documento);
    }
    
    @Override
    public boolean equals(Object o) {
        return (o == null || !(o instanceof Documento)) ? false : documento
                .equals(((Documento) o).documento);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.documento);
        return hash;
    }
    
    @Override
    public String toString() {
        return documento;
    }
    
    public String obtenerDocumento() {
        return documento;
    }
    
    public int obtenerNumeroPalabras() {
        return numeroPalabras;
    }
    
    public int[] obtenerIndicesPalabra(String palabra) {
        if(palabra == null || palabra.length() == 0 || !palabras.containsKey(
                palabra)) {
            return new int[0];
        }
        
        List<Integer> lista = palabras.get(palabra);
        Integer[] listaEnteros = lista.toArray(new Integer[lista.size()]);
        int[] indices = new int[lista.size()];
        
        for (int i = 0; i < indices.length; i++) {
            indices[i] = listaEnteros[i].intValue();
        }
        
        return indices;
    }
    
    private void procesarCadena() {
        numeroPalabras = 0;
        
        if(documento.trim().length() == 0) {
            return;
        }
        
        String[] listaPalabras = Busqueda.convertirDocumento(documento)
                .split(" ");
        
        for (int i = 0; i < listaPalabras.length; i++) {
            listaPalabras[i] = listaPalabras[i].toLowerCase();
            
            if(!Busqueda.esExcluible(listaPalabras[i])) {
                procesarPalabraIndividual(listaPalabras[i], numeroPalabras);
                numeroPalabras++;
            }
        }
    }
    
    private void procesarPalabraIndividual(String palabra, int indice) {
        palabra = palabra.toLowerCase();
        
        if(palabras.containsKey(palabra)) {
            List<Integer> lista = palabras.get(palabra);
                    
            if(!lista.contains(indice)) {
                lista.add(indice);
            }
        } else {
            List<Integer> lista = new LinkedList<>();
            lista.add(indice);
            palabras.put(palabra, lista);
        }
        
        Palabra.obtenerPalabra(palabra).anadirDocumento(this);
    }
    
    static void limpiarDocumentos() {
        documentos = new TreeSet<>();
    }
}
