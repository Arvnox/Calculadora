/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datos.busqueda;

import java.math.BigDecimal;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import java.math.MathContext;
import java.math.RoundingMode;
import static java.util.Arrays.deepEquals;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Andrés Sarmiento Tobón <ansarmientoto at unal.edu.co>
 */
public class ComparadorDocumentos implements Comparator<Documento>, 
        Comparable<ComparadorDocumentos> {
    private static TreeSet<ComparadorDocumentos> comparadoresPrevios 
            = new TreeSet<>();
    private TreeMap<Documento, Double> documentosHechos = new TreeMap<>();
    private String[] palabras;
    
    /**
     * Método principal de ComparadorDocumentos.
     * 
     * @param args Los argumentos del programa.
     */
    public static void main(String[] args) {
//        ComparadorDocumentos instancia = new ComparadorDocumentos();
    }
    
    public static ComparadorDocumentos obtenerComparador(String[] palabras) {
        if(palabras == null) {
            palabras = new String[0];
        }
        
        for (ComparadorDocumentos comparadorDocumentos : comparadoresPrevios) {
            if(deepEquals(palabras, comparadorDocumentos.palabras)) {
                return comparadorDocumentos;
            }
        }
        
        ComparadorDocumentos comparadorActual = new ComparadorDocumentos(
                palabras);
        comparadoresPrevios.add(comparadorActual);
        return comparadorActual;
    }
    
    static void limpiarComparadores() {
        comparadoresPrevios = new TreeSet<>();
    }

    private ComparadorDocumentos(String[] palabras) {
        this.palabras = palabras;
    }

    @Override
    public int compare(Documento documentoUno, Documento documentoDos) {
        if(documentoUno == null) {
            if(documentoDos == null) {
                return 0;
            } else {
                return 1;
            }
        } else if(documentoDos == null) {
            return -1;
        }
        
        if(documentoUno.obtenerDocumento().intern() == documentoDos.
                obtenerDocumento().intern()) {
            return 0;
        }
        
        double uno = 0;
        
        if(documentosHechos.containsKey(documentoUno)) {
            uno = documentosHechos.get(documentoUno);
        } else {
            uno = calcularIndice(documentoUno, palabras);
            documentosHechos.put(documentoUno, uno);
        }
        
        double dos = 0;
        
        if(documentosHechos.containsKey(documentoDos)) {
            dos = documentosHechos.get(documentoDos);
        } else {
            dos = calcularIndice(documentoDos, palabras);
            documentosHechos.put(documentoDos, dos);
        }
        
        if(uno < dos) {
            return -1;
        } else if(uno > dos) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int compareTo(ComparadorDocumentos o) {
        return (palabras == null || o == null || o.palabras == null) ? 0 
                : armarPalabra(palabras).compareToIgnoreCase(armarPalabra(
                o.palabras));
    }

    @Override
    public boolean equals(Object o) {
        return (palabras == null || o == null || !(o instanceof 
                ComparadorDocumentos) || ((ComparadorDocumentos) o).
                palabras == null) ? false : armarPalabra(palabras).
                equalsIgnoreCase(armarPalabra(((ComparadorDocumentos) o).
                palabras));
    }
    
    private double calcularIndice(Documento documento, String[] palabras) {
        if(palabras == null || documento == null || palabras.length == 0) {
            return 0;
        }
        
        int[][] indices = new int[palabras.length][];
        
        for (int i = 0; i < palabras.length; i++) {
            indices[i] = documento.obtenerIndicesPalabra(palabras[i]);
        }
        
        BigDecimal maximo = new BigDecimal(documento.obtenerNumeroPalabras());
        BigDecimal total = contarParejas(indices).multiply(maximo);
        
        BigDecimal indice = ZERO;
        BigDecimal anadirFinal = ZERO;
        BigDecimal solaPosibilidad  = ZERO;
        
        for (int i = 0; i < indices.length - 1; i++) {
            if(indices[i].length == 0) {
                anadirFinal = anadirFinal.add(maximo);
            } else {
                if(solaPosibilidad == ZERO) {
                    solaPosibilidad = new BigDecimal(indices[i].length);
                }
                
                for (int j = 0; j < indices[i].length; j++) {
                    for (int k = i + 1; k < indices.length; k++) {
                        if(indices[k].length == 0) {
                            anadirFinal = anadirFinal.add(maximo);
                        } else {
                            for (int l = 0; l < indices[k].length; l++) {
                                BigDecimal ij = new BigDecimal(indices[i][j]);
                                BigDecimal kl = new BigDecimal(indices[k][l]);
                                indice = indice.add(ij.subtract(kl).abs());
                            }
                        }
                    }
                }
            }
        }
        
        if(solaPosibilidad == ZERO) {
            solaPosibilidad = new BigDecimal(indices[indices.length - 1]
                    .length);
        }
                
        if(total.signum() == 0) {
            MathContext contexto = new MathContext(128, RoundingMode.HALF_UP);
            return maximo.subtract(ONE.divide(solaPosibilidad, 
                    contexto)).doubleValue();
        } else if(indice == ZERO || indice.signum() == 0) {
            return total.doubleValue();
        } else {
            MathContext contexto = new MathContext(128, RoundingMode.HALF_UP);
            return indice.divide(total, contexto).add(anadirFinal)
                    .doubleValue();
        }
    }

    private BigDecimal contarParejas(int[][] indices) {
        BigDecimal total = ZERO;
        
        for (int i = 0; i < indices.length - 1; i++) {
            BigDecimal preTotal = ZERO;
            
            for (int j = i + 1; j < indices.length; j++) {
                preTotal = preTotal.add(new BigDecimal(indices[j].length));
            }
            
            total = total.add(preTotal.multiply(new BigDecimal(indices[i]
                    .length)));
        }
        
        return total;
    }

    private String armarPalabra(String[] palabras) {
        if(palabras == null || palabras.length == 0) {
            return "";
        }
        
        StringBuilder constructor = new StringBuilder(10 * palabras.length);
        constructor = constructor.append(palabras[0]);
        
        for (int i = 1; i < palabras.length; i++) {
            constructor = constructor.append("|").append(palabras[i]);
        }
        
        return constructor.toString();
    }
}
