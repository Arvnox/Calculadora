/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datos.busqueda;

import static java.util.Arrays.sort;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 *
 * @author Andrés Sarmiento Tobón <ansarmientoto at unal.edu.co>
 */
public class Busqueda {
    private static Pattern patronExcluido = Pattern.compile("yo|m[iíIÍ]([ao]|)"
            + "(s|)|con[mts]igo|t[uúUÚ](s|)|ti|vos|usted(es|)|[eéEÉ]l|ell[ao]"
            + "(s|)|s[iíIÍ]|[vn]osotr[oa]s|me|nos|te|os|se|l[oa](s|)|le(s|)"
            + "|[st]uy[oa](s|)|sus|[nv]uestr[oa](s|)|[eéEÉ]s((t|)[ao](s|))"
            + "(e|t[eéEÉ]s)|[eéEÉ]s(([ao](s|))|e)|aqu[eéEÉ]l((lo|la)(s|))"
            + "|(alg|ning|)un[oa](s|)|nada|"
            + "(poc|escas|much|demasiad|tod|otr|mism)[ao](s|)|vari[ao]s"
            + "|tan(|t[ao](s|))|alguien|nadie|(cual|quien)(es|)quiera"
            + "|dem[aáAÁ]s|no|a|ante|bajo|cabe|con|contra|de|desde|durante|en"
            + "|entre|hacia|hasta|mediante|para|por|seg[uúÚU]n|sin|so|sobre"
            + "|tras|versus|v[íiÍI]a|y|e|o|u|a|ni|pero|sino|conque|luego|tanto"
            + "|entonces|as[iíÍI]|qu[éeÉE]|obstante|m[aáAÁ]s|menos|excepto|es"
            + "|ahora");
    private static Pattern patronLetra = Pattern.compile(
            "[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚâêîôûÂÊÎÔÛäëïöüÄËÏÖÜÿŸŷŶàèìòùÀÈÌÒÙæœÆŒçÇß]");  
    
    /**
     * Método principal de Busqueda.
     * 
     * @param args Los argumentos del programa.
     */
    public static void main(String[] args) {
        Busqueda instancia = new Busqueda();
    }
    
    public static boolean esExcluible(String palabra) {
        if(palabra == null) {
            return true;
        }
        
        palabra = convertirPalabra(palabra);
        
        if(palabra.length() == 0) {
            return true;
        }
        
        return patronExcluido.matcher(palabra).matches();
    }

    public static String convertirPalabra(String palabra) {
        if(palabra == null || palabra.length() == 0) {
            return "";
        }
        
        char[] caracteres = palabra.toCharArray();
        
        for (int i = 0; i < caracteres.length; i++) {
            if(!patronLetra.matcher(String.valueOf(caracteres[i])).matches()) {
                caracteres[i] = ' ';
            }
        }
        
        String salida = new String(caracteres).trim();
        
        
        
        if(salida.contains(" ")) {
            throw new IllegalArgumentException("Palabra inseparable: " + 
                    palabra);
        } else {
            return salida;
        }
    }
    
    public static String convertirDocumento(String palabra) {
        if(palabra == null || palabra.length() == 0) {
            return "";
        }
        
        char[] caracteres = palabra.toCharArray();
        
        for (int i = 0; i < caracteres.length; i++) {
            if(!patronLetra.matcher(String.valueOf(caracteres[i])).matches()) {
                caracteres[i] = ' ';
            } else {
                switch(caracteres[i]) {
                    case 'á':
                    case 'â':
                    case 'ä':
                    case 'à':
                        caracteres[i] = 'a';
                        break;
                    case 'Á':
                    case 'Â':
                    case 'Ä':
                    case 'À':
                        caracteres[i] = 'A';
                        break;
                    case 'é':
                    case 'ê':
                    case 'ë':
                    case 'è':
                        caracteres[i] = 'e';
                        break;
                    case 'É':
                    case 'Ê':
                    case 'Ë':
                    case 'È':
                        caracteres[i] = 'E';
                        break;
                    case 'í':
                    case 'î':
                    case 'ï':
                    case 'ì':
                        caracteres[i] = 'i';
                        break;
                    case 'Í':
                    case 'Î':
                    case 'Ï':
                    case 'Ì':
                        caracteres[i] = 'I';
                        break;
                    case 'ó':
                    case 'ô':
                    case 'ö':
                    case 'ò':
                        caracteres[i] = 'o';
                        break;
                    case 'Ó':
                    case 'Ô':
                    case 'Ö':
                    case 'Ò':
                        caracteres[i] = 'O';
                        break;
                    case 'û':
                    case 'ú':
                    case 'ü':
                    case 'ù':
                        caracteres[i] = 'u';
                        break;
                    case 'Û':
                    case 'Ú':
                    case 'Ü':
                    case 'Ù':
                        caracteres[i] = 'U';
                        break;
                    case 'ÿ':
                    case 'ŷ':
                    case 'Ÿ':
                    case 'Ŷ':
                        caracteres[i] = 'Y';
                        break;
                }
            }
        }
        
        return new String(caracteres).trim();
    }
    
    public static Documento[] buscar(String palabras) {
        if(palabras == null || palabras.length() == 0) {
            return new Documento[0];
        }
        
        String[] palabrasActuales = convertirDocumento(palabras).split(" ");
        
        if(palabrasActuales.length == 0) {
            return new Documento[0];
        } else {
            return buscar(palabrasActuales);
        }
    }
    
    public static Documento[] buscar(String[] palabras) {
        List<String> listaPalabrasUsables = new LinkedList<>();
        
        for (String palabra : palabras) {
            String palabraActual = palabra.toLowerCase();
            
            if(!esExcluible(palabraActual)) {
                listaPalabrasUsables.add(palabraActual);
            }
        }
        
        String[] palabrasUsables = listaPalabrasUsables.toArray(new String[
                listaPalabrasUsables.size()]);
        TreeSet<Documento> listaDocumentos = new TreeSet<>();
        
        for (int i = 0; i < palabrasUsables.length; i++) {
            Documento[] documentos = Palabra.obtenerPalabra(palabrasUsables[i]).
                    getListaDocumentos();
            
            for (Documento documento : documentos) {
                if(!listaDocumentos.contains(documento)) {
                    listaDocumentos.add(documento);
                }
            }
        }
        
        Documento[] documentos = listaDocumentos.toArray(new Documento[
                listaDocumentos.size()]);
        sort(documentos, ComparadorDocumentos.obtenerComparador(
                palabrasUsables));
        
        return documentos;
    }

    public static void limpiarBaseDatos() {
        Palabra.limpiarPalabras();
        Documento.limpiarDocumentos();
        ComparadorDocumentos.limpiarComparadores();
    }
    
    public static boolean contienePalabrasComunes(String cadena) {
        String[] palabras = convertirDocumento(cadena).trim().split(" ");
        
        for (String palabra : palabras) {
            if(esExcluible(palabra)) {
                return true;
            }
        }
        
        return false;
    }
}
