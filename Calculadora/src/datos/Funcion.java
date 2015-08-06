/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import datos.quimica.Elemento;
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.NaN;
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.System.arraycopy;
import java.math.BigDecimal;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import java.math.BigInteger;
import java.math.MathContext;
import static java.math.RoundingMode.HALF_UP;
import java.util.Arrays;
import static java.util.Arrays.sort;
import java.util.LinkedList;
import java.util.NavigableMap;
import java.util.Queue;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.UNICODE_CASE;
import static java.util.regex.Pattern.compile;
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
 * <p/>
 * @author Andrés Sarmiento Tobón <href>ansarmientoto@unal.edu.co</href>
 */
public abstract class Funcion implements Comparable<Funcion> {
    /**
     *
     */
    public final static MathContext CONTEXTO = new MathContext(128, HALF_UP);

    /**
     *
     */
    private static TreeSet<Funcion> funciones;
    /**
     *
     */
    private static final TreeMap<String, String> funcionesNativas;
    /**
     *
     */
    public boolean aRPN = false;
    /**
     *
     */
    protected int numeroArgumentos = 0;
    /**
     *
     */
    protected boolean nativa = false;
    /**
     *
     */
    protected String nombre = "";
    /**
     *
     */
    protected Object[] argumentosActuales = new Object[0];
    /**
     *
     */
    private static Pattern patronVariable = compile("[a-z]++" +
             "|[0-9]++[a-z]++|[a-z]++[0-9]++", UNICODE_CASE);
    /**
     * Verdadero si todos los argumentos son todos números, falso en otro caso.
     */
    protected boolean computable = false;
    /**
     *
     */
    protected boolean tieneArgumentosString = false;
    /**
     *
     */
    protected String nombreUsuario = "";
    /**
     *
     */
    protected String descripcion = "";
    /**
     *
     */
    protected String descripcionLarga = "";
    /**
     *
     */
    protected String[] categorizacion = new String[0];

    /**
     *
     */
    public static final BigDecimal PI = new BigDecimal("3.141592653589793238462"
            + "6433832795028841971693993751058209749445923078164062862089986280"
            + "3482534211706798214808651328230664709384460955058223172535940812"
            + "8481117450284102701938521105559644622948954930381964428810975665"
            + "9334461284756482337867831652712019091456485669234603486104543266"
            + "4821339360726024914127372458700660631558817488152092096282925409"
            + "17153643678925903600113305305488204665213841469519415116094");
    
    /**
     *
     */
    public static final BigDecimal E = new BigDecimal("2.7182818284590452353602"
            + "8747135266249775724709369995957496696762772407663035354759457138"
            + "2178525166427427466391932003059921817413596629043572900334295260"
            + "5956307381323286279434907632338298807531952510190115738341879307"
            + "0215408914993488416750924476146066808226480016847741185374234544"
            + "2437107539077744992069551702761838606261331384583000752044933826"
            + "5602976067371132007093287091274437470472306969772093101416928368"
            + "1902551510865746377211125238978442505695369677078544996996794686"
            + "4454905987931636889230098793127736178215424999229576351482208269"
            + "8951936680331825288693984964651058209392398294887933203625094431"
            + "1730123819706841614039701983767932068328237646480429531180232878"
            + "2509819455815301756717361332069811250996181881593041690351598888"
            + "5193458072738667385894228792284998920868058257492796104841984443"
            + "6346324496848756023362482704197862320900216099023530436994184914"
            + "6314093431738143640546253152096183690888707016768396424378140592"
            + "7145635490613031072085103837505101157477041718986106873969655212"
            + "671546889570350354021234078498193343210681701210056278");
    
    static {
        long time = System.nanoTime();
        TreeSet<Funcion> listaFunciones = new TreeSet<>();
        int numeroDiferentes = 0;
        String descripcionFuncion;
        String descripcionLargaFuncion;
        String alternativas;
        String nombre;
        TreeMap<String, String> mapaFunciones = new TreeMap<>();
        String[] categoria;
        String[] categoriaTrigonometricas = new String[]{"Trigonométricas"};
        String[] categoriaHiperbolicas = new String[]{"Hiperbólicas"};
        String[] categoriaEstadisticas = new String[]{"Estadísticas"};
        String[] categoriaEstadisticasMedias = new String[]{"Estadísticas", "Medias"};
        String[] categoriaEstadisticasAsimetrias = new String[]{"Estadísticas", "Asimetrías"};
        String[] categoriaEstadisticasCurtosis = new String[]{"Estadísticas", "Curtosis"};
        String[] categoriaEstadisticasCentil = new String[]{"Estadísticas", "Centiles"};
        String[] categoriaVolumenes = new String[]{"Geométricas", "Volúmenes"};
        String[] categoriaAreas = new String[]{"Geométricas", "Áreas"};
        String[] categoriaDistancias = new String[]{"Geométricas", "Distancias"};
        String[] categoriaPerimetros = new String[]{"Geométricas", "Perímetros"};
        String[] categoriaSalud = new String[]{"Salud"};
        String[] categoriaTiempo = new String[]{"Tiempo"};
        String[] categoriaLogaritmos = new String[]{"Números", "Logaritmos"};
        String[] categoriaAleatorios = new String[]{"Números", "Aleatorios"};
        String[] categoriaDerivadas = new String[]{"Números", "Derivadas"};
        String[] categoriaNumeros = new String[]{"Números"};
        String[] categoriaNumerosPrimos = new String[]{"Números", "Primos"};
        String[] categoriaQuimica = new String[]{"Química"};

        try {
            //Seno
            descripcionFuncion = "Seno(x)";
            descripcionLargaFuncion = "Seno(x): Cateto opuesto (al ángulo x) " +
                    "dividido entre la hipotenusa, en un triángulo rectángulo.";
            categoria = categoriaTrigonometricas;
            alternativas = "sen, seno, sin, sine";
            nombre = "sen";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class.getMethod(
                    "sin", new Class[] {double.class}), Math.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "seno";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod(
                    "sin", new Class[] {double.class}), Math.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "sin";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class.getMethod(
                    "sin", new Class[] {double.class}), Math.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "sine";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod(
                    "sin", new Class[] {double.class}), Math.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Coseno
            descripcionFuncion = "Coseno(x)";
            descripcionLargaFuncion = "Coseno(x): Cateto adyacente (al ángulo x"
                    + ") dividido entre la hipotenusa, en un triángulo rectángu"
                    + "lo.";
            categoria = categoriaTrigonometricas;
            alternativas = "cos, coseno, cosine";
            
            nombre = "cos";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class.getMethod(
                    "cos", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "cosine";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod(
                    "cos", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "coseno";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod(
                    "cos", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Tangente
            descripcionFuncion = "Tangente(x)";
            descripcionLargaFuncion = "Tangente(x): Cateto opuesto (al ángulo x"
                    + ") dividido entre el cateto adyacente, en un triángulo re"
                    + "ctángulo.";
            categoria = categoriaTrigonometricas;
            alternativas = "tan, tangent, tangente";
            
            nombre = "tan";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class.getMethod(
                    "tan", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "tangent";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class.getMethod(
                    "tan", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "tangente";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class.getMethod(
                    "tan", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Hipotenusa
            descripcionFuncion = "Hipotenusa(x, y)";
            descripcionLargaFuncion = "Hipotenusa(x, y): Calcula la hipotenusa "
                    + "de una triángulo rectángulo con catetos x e y.";
            categoria = categoriaTrigonometricas;
            alternativas = "hipot, hypot, hipotenusa, hypotenuse";
            
            nombre = "hypot";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    Math.class.getMethod("hypot", new Class[] {double.class,
                        double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "hyptenuse";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    Math.class.getMethod("hypot", new Class[] {double.class,
                        double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "hipot";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    Math.class.getMethod("hypot", new Class[] {double.class,
                        double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "hipotenusa";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    Math.class.getMethod("hypot", new Class[] {double.class,
                        double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Factorial
            descripcionFuncion = "Factorial(x)";
            descripcionLargaFuncion = "Factorial(x): Calcula el producto desde "
                    + "1 hasta x, incluido. Es decir, calcula: 1 * 2 * 3 * ... "
                    + "* (x - 1) * x.";
            categoria = categoriaNumeros;
            alternativas = "fact, factorial";
            
            nombre = "fact";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("fact",
                    new Class[] {double.class}),
                    FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "factorial";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("fact",
                    new Class[] {double.class}),
                    FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Valor Absoluto
            descripcionFuncion = "Valor Absoluto(x)";
            descripcionLargaFuncion = "Valor Absoluto(x): Si x < 0 devuelve -x;"
                    + " si x >= 0 devuelve x.";
            categoria = categoriaNumeros;
            alternativas = "abs, vabs, absolute, absoluto, absolutevalue, valorabsoluto";
            
            nombre = "abs";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("abs", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "vabs";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("abs", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "absolute";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("abs", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "absoluto";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("abs", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "absolutevalue";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("abs", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "valorabsoluto";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("abs", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Cosecante
            descripcionFuncion = "Cosecante(x)";
            descripcionLargaFuncion = "Cosecante(x): La hipotenusa dividida ent"
                    + "re el cateto opuesto (al ángulo x), en un triángulo rect"
                    + "ángulo.";
            categoria = categoriaTrigonometricas;
            alternativas = "csc, cosecant, cosecante";
            
            nombre = "csc";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("csc", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "cosecant";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("csc", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "cosecante";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("csc", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Secante
            descripcionFuncion = "Secante(x)";
            descripcionLargaFuncion = "Secante(x): La hipotenusa dividida entre"
                    + " el cateto adyacente (al ángulo x), en un triángulo rect"
                    + "ángulo.";
            categoria = categoriaTrigonometricas;
            alternativas = "sec, secant, secante";
            
            nombre = "sec";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("sec", new Class[] {
                        double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "secant";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("sec", new Class[] {
                        double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "secante";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("sec", new Class[] {
                        double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Cotangente
            descripcionFuncion = "Cotangente(x)";
            descripcionLargaFuncion = "Cotangente(x): Cateto adyacente (al ángu"
                    + "lo x) dividido entre el cateto opuesto, en un triángulo "
                    + "rectángulo.";
            categoria = categoriaTrigonometricas;
            alternativas = "cot, cotangent, cotangente";
            
            nombre = "cot";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cot", new Class[] {
                        double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "cotangent";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cot", new Class[] {
                        double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "cotangente";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cot", new Class[] {
                        double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Arcoseno
            descripcionFuncion = "Arcoseno(x)";
            descripcionLargaFuncion = "Arcoseno(x): Tamaño del ángulo en un tri"
                    + "ángulo rectángulo con hipotenusa igual a 1 y cateto opue"
                    + "sto igual a x.";
            categoria = categoriaTrigonometricas;
            alternativas = "asen, asin, arcsen, arcsin, arcoseno, arcsine";
            
            nombre = "asen";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("asin", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "asin";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("asin", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arcsen";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("asin", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arcsin";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("asin", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arcoseno";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("asin", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arcsine";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("asin", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Arcocoseno
            descripcionFuncion = "Arcocoseno(x)";
            descripcionLargaFuncion = "Arcocoseno(x): Tamaño del ángulo en un t"
                    + "riángulo rectángulo con hipotenusa igual a 1 y cateto ad"
                    + "jacente igual a x.";
            categoria = categoriaTrigonometricas;
            alternativas = "acos, arccos, arcocoseno, arccosine";
            
            nombre = "acos";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("acos", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arccos";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("acos", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arcocoseno";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("acos", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arccosine";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("acos", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Arcotangente
            descripcionFuncion = "Arcotangente(x)";
            descripcionLargaFuncion = "Arcocotangente(x): Tamaño del ángulo en "
                    + "un triángulo rectángulo con cateto adyacente igual a 1 y"
                    + " cateto opuesto igual a x.";
            categoria = categoriaTrigonometricas;
            alternativas = "atan, arctan, arctangent, arcotangente";
            
            nombre = "atan";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("atan", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arctan";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("atan", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arctangent";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("atan", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arcotangente";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("atan", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Arcocosecante
            descripcionFuncion = "Arcocosecante(x)";
            descripcionLargaFuncion = "Arcocosecante(x): Tamaño del ángulo en u"
                    + "n triángulo rectángulo con hipotenusa igual a x y cateto"
                    + " opuesto igual a 1.";
            categoria = categoriaTrigonometricas;
            alternativas = "acsc, arccsc, arccosecant, arcocosecante";
            
            nombre = "acsc";
            listaFunciones.add(new FuncionNativa(nombre, 1, FuncionesNativas
                    .class.getMethod("acsc", new Class[] {double.class}),
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arccsc";
            listaFunciones.add(new FuncionNativa(nombre, 1, FuncionesNativas
                    .class.getMethod("acsc", new Class[] {double.class}),
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arccosecant";
            listaFunciones.add(new FuncionNativa(nombre, 1, FuncionesNativas
                    .class.getMethod("acsc", new Class[] {double.class}),
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arcocosecante";
            listaFunciones.add(new FuncionNativa(nombre, 1, FuncionesNativas
                    .class.getMethod("acsc", new Class[] {double.class}),
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Arcosecante
            descripcionFuncion = "Arcosecante(x)";
            descripcionLargaFuncion = "Arcosecante(x): Tamaño del ángulo en un "
                    + "triángulo rectángulo con hipotenusa igual a x y cateto a"
                    + "djacente igual a 1.";
            categoria = categoriaTrigonometricas;
            alternativas = "asec, arcsec, arcsecant, arcosecante";
            
            nombre = "asec";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("asec",
                    new Class[] {double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arcsec";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("asec",
                    new Class[] {double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arcsecant";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("asec",
                    new Class[] {double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arcosecante";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("asec",
                    new Class[] {double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Arcocotangente
            descripcionFuncion = "Arcocotangente(x)";
            descripcionLargaFuncion = "Arcocotangente(x): Tamaño del ángulo en "
                    + "un triángulo rectángulo con cateto adyacente igual a x y"
                    + " cateto opuesto igual a 1.";
            categoria = categoriaTrigonometricas;
            alternativas = "acot, arccot, arccotangent, arcocotangente";
            
            nombre = "acot";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("acot",
                    new Class[] {double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arccot";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("acot",
                    new Class[] {double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arccotangent";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("acot",
                    new Class[] {double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arcocotangente";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("acot",
                    new Class[] {double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Logaritmo Natural o Neperiano
            descripcionFuncion = "Logaritmo Natural";
            descripcionLargaFuncion = "LogaritmoNatural(x): Logari"
                    + "tmo en base e. Donde e es el número que representa el lí"
                    + "mite de una inversión de 1 unidad monetaria con un inter"
                    + "és continuo del 100%. A e se le refiere a veces como Num"
                    + "ero de Euler o Número de Napier.";
            categoria = categoriaLogaritmos;
            alternativas = "ln, logn, loge, naturallogarithm, logaritmonatural";
            
            nombre = "ln";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("log", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "logn";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("log", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "loge";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("log", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "naturallogarithm";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("log", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "logaritmonatural";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("log", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Logaritmo
            descripcionFuncion = "Logaritmo(x, y)";
            descripcionLargaFuncion = "Logaritmo(x, y): Logaritmo en base x de "
                    + "y. El logaritmo es el número c, tal que x ^ c = y.";
            categoria = categoriaLogaritmos;
            alternativas = "log, logarithm, logaritmo";
            
            nombre = "log";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("logb",
                    new Class[] {double.class,
                        double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "logarithm";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("logb",
                    new Class[] {double.class,
                        double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "logaritmo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("logb",
                    new Class[] {double.class,
                        double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Seno Hiperbólico
            descripcionFuncion = "Seno Hiperbólico(a)";
            descripcionLargaFuncion = "Seno Hiperbólico(a): La distancia entre "
                    + "la hipérbola x ^ 2 - y ^ 2 = 1 con el eje de las abscisa"
                    + "s. Donde a es el doble del área comprendida entre la hip"
                    + "érbola y la recta y = x. La función se define también co"
                    + "mo (e^a - e^(-a)) / 2.";
            categoria = categoriaHiperbolicas;
            alternativas = "senh, sinh, senoh, sineh, senohiperbolico, hyperbol"
                    + "icsine";
            
            nombre = "senh";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("sinh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "sinh";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("sinh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "senoh";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("sinh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "sineh";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("sinh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "senohiperbolico";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("sinh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "hyperbolicsine";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("sinh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Coseno Hiperbólico
            descripcionFuncion = "Coseno Hiperbolico(a)";
            descripcionLargaFuncion = "Coseno Hiperbolico(a): La distancia entr"
                    + "e la hipérbola x ^ 2 - y ^ 2 = 1 con el eje de las orden"
                    + "adas. Donde a es el doble del área comprendida entre la "
                    + "hipérbola y la recta y = x. La función se define también"
                    + " como (e^a + e^(-a)) / 2.";
            categoria = categoriaHiperbolicas;
            alternativas = "cosh, cosenoh, cosineh, cosenohiperbolico, hyperbol"
                    + "iccosine";
            
            nombre = "cosh";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("cosh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "cosenoh";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("cosh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "cosineh";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("cosh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "cosenohiperbolico";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("cosh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "hyperboliccosine";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("cosh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Tangente Hiperbólica
            descripcionFuncion = "Tangente Hiperbólica(a)";
            descripcionLargaFuncion = "Tangente Hiperbólica(a): Cociente entre "
                    + "la distancia entre la hipérbola x ^ 2 - y ^ 2 = 1 con el" 
                    + " eje de las abscisas y la distancia de la hipérbola con "
                    + "el eje de las ordenadas. Donde a es el doble del área co"
                    + "mprendida entre la hipérbola y la recta y = x. La funció"
                    + "n se define también como (e^a - e^(-a)) / (e^a + e^(-a))"
                    + ".";
            categoria = categoriaHiperbolicas;
            alternativas = "tanh, tangenth, tangenteh, hyperbolictangent, tangentehiperbolica";
            
            nombre = "tanh";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("tanh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "tangenth";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("tanh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "tangenteh";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("tanh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "hyperbolictangent";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("tanh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "tangentehiperbolica";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("tanh", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Redondeo Entero
            descripcionFuncion = "Redondeo Entero(x)";
            descripcionLargaFuncion = "Redondeo Entero(x): Devuelve el entero q"
                    + "ue está más cercano a x.";
            categoria = categoriaNumeros;
            alternativas = "rent, rint, rentero, rinteger, redondeoentero, inte"
                    + "gerround";
            
            nombre = "rent";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("rint", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rint";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("rint", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rentero";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("rint", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rinteger";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("rint", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "redondeoentero";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("rint", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "integerround";
            listaFunciones.add(new FuncionNativa(nombre, 1, Math.class
                    .getMethod("rint", new Class[] {double.class}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Residuo
            descripcionFuncion = "Residuo(x, y)";
            descripcionLargaFuncion = "Residuo(x, y): Retorna el residuo de x /"
                    + " y con el signo de x.";
            categoria = categoriaNumeros;
            alternativas = "res, residuo, remainder";
            
            nombre = "res";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("mod", new Class[] {
                        double.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "residuo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("mod", new Class[] {
                        double.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "remainder";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("mod", new Class[] {
                        double.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            //MCD
            descripcionFuncion = "Máximo Común Divisor(x, y)";
            descripcionLargaFuncion = "Máximo Común Divisor(x, y): Máximo númer"
                    + "o que divide exactamente a x e y.";
            categoria = categoriaNumeros;
            alternativas = "mcd, gcd, maximocomundivisor, greatestcommondivisor"
                    + "";
            
            nombre = "mcd";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("gcd", new Class[] {
                        double.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "gcd";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("gcd", new Class[] {
                        double.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "maximocomundivisor";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("gcd", new Class[] {
                        double.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "greatestcommondivisor";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("gcd", new Class[] {
                        double.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            //MCM
            descripcionFuncion = "Mínimo Común Múltiplo(x, y)";
            descripcionLargaFuncion = "Mínimo Común Múltiplo(x, y): Mínimo núme"
                    + "ro que es dividido exactamente por x e y.";
            categoria = categoriaNumeros;
            alternativas = "mcm, lcm, minimocomunmultiplo, leastcommonmultiple"
                    + "";
            
            nombre = "mcm";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("mcm", new Class[] {
                        double.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "lcm";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("mcm", new Class[] {
                        double.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "minimocomunmultiplo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("mcm", new Class[] {
                        double.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "leastcommonmultiple";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("mcm", new Class[] {
                        double.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            //Aleatorio
            descripcionFuncion = "Aleatorio()";
            descripcionLargaFuncion = "Aleatorio(): Devuelve un número entre 0 "
                    + "y 1 perteneciente a una distribución uniforme.";
            categoria = categoriaAleatorios;
            alternativas = "aleatorio, random";
            
            nombre = "aleatorio";
            listaFunciones.add(new FuncionNativa(nombre, 0, Math.class
                    .getMethod("random", new Class[] {}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "random";
            listaFunciones.add(new FuncionNativa(nombre, 0, Math.class
                    .getMethod("random", new Class[] {}), Math.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Aleatorio Gaussiano
            descripcionFuncion = "Aleatorio Gaussiano()";
            descripcionLargaFuncion = "Aleatorio Gaussiano(): Devuelve un númer"
                    + "o entre 0 y 1 perteneciente a una distribución Normal.";
            categoria = categoriaAleatorios;
            alternativas = "gauss, gaussian, gaussiano";
            
            nombre = "gauss";
            listaFunciones.add(new FuncionNativa(nombre, 0,
                    FuncionesNativas.class.getMethod("gaussian", new Class[] {}),
                    FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "gaussiano";
            listaFunciones.add(new FuncionNativa(nombre, 0,
                    FuncionesNativas.class.getMethod("gaussian", new Class[] {}),
                    FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "gaussian";
            listaFunciones.add(new FuncionNativa(nombre, 0,
                    FuncionesNativas.class.getMethod("gaussian", new Class[] {}),
                    FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Derivación
            descripcionFuncion = "Derivacion(Una variable)(f(x), a)";
            descripcionLargaFuncion = "Derivacion(Una variable)(f(x), a): La de"
                    + "rivada de f(x) en a usando los puntos adyacentes a a com"
                    + "o referencias.";
            categoria = categoriaDerivadas;
            alternativas = "derivar, derive";
            
            nombre = "derivar";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("derivar", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "derive";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("derivar", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Derivación (Segunda)
            descripcionFuncion = "Segunda Derivada(Una variable)(f(x), a)";
            descripcionLargaFuncion = "Segunda Derivada(Una variable)(f(x), a):"
                    + " La segunda derivada de f(x) en a usando los puntos adya"
                    + "centes a a como referencias.";
            categoria = categoriaDerivadas;
            alternativas = "derivardos, derivetwo";
            
            nombre = "derivardos";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("derivar2", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "derivetwo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("derivar2", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Derivación(Tercera)
            descripcionFuncion = "Tercera Derivada(Una variable)(f(x), a)";
            descripcionLargaFuncion = "Tercera Derivada(Una variable)(f(x), a):"
                    + " La tercera derivada de f(x) en a usando los puntos adya"
                    + "centes a a como referencias.";
            categoria = categoriaDerivadas;
            alternativas = "derivartres, derivethree";
            
            nombre = "derivartres";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("derivar3", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "derivethree";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("derivar3", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Derivación (Cuarta)
            descripcionFuncion = "Cuarta Derivada(Una variable)(f(x), a)";
            descripcionLargaFuncion = "Cuarta Derivada(Una variable)(f(x), a): "
                    + "La cuarta derivada de f(x) en a usando los puntos adyace"
                    + "ntes a a como referencias.";
            categoria = categoriaDerivadas;
            alternativas = "derivarcuatro, derivefour";
            
            nombre = "derivarcuatro";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("derivar4", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "derivefour";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("derivar4", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Integración
            descripcionFuncion = "Integración(f(x), a, b)";
            descripcionLargaFuncion = "Integración(f(x), a, b): Área bajo f(x) "
                    + "entre a y b.";
            categoria = categoriaNumeros;
            alternativas = "integrar, integrate";
            
            nombre = "integrar";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("integrar", new Class[] {
                        java.lang.String.class, double.class, double.class}),
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "integrate";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("integrar", new Class[] {
                        java.lang.String.class, double.class, double.class}),
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Minimo
            descripcionFuncion = "Mínimo(f(x), a, b)";
            descripcionLargaFuncion = "Mínimo(f(x), a ,b): Encuentra el menor v"
                    + "alor de f(x) en el intervalo [a, b].";
            categoria = categoriaNumeros;
            alternativas = "min, minimo, minimum";
            
            nombre = "min";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("min", new Class[] {
                        java.lang.String.class, double.class, double.class}),
                    FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "minimo";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("min", new Class[] {
                        java.lang.String.class, double.class, double.class}),
                    FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "minimum";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("min", new Class[] {
                        java.lang.String.class, double.class, double.class}),
                    FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            //Maximo
            descripcionFuncion = "Máximo(f(x), a, b)";
            descripcionLargaFuncion = "Máximo(f(x), a ,b): Encuentra el mayor v"
                    + "alor de f(x) en el intervalo [a, b].";
            categoria = categoriaNumeros;
            alternativas = "max, maximo, maximum";
            
            nombre = "max";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("max", new Class[] {
                        java.lang.String.class, double.class, double.class}),
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "maximo";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("max", new Class[] {
                        java.lang.String.class, double.class, double.class}),
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "maximum";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("max", new Class[] {
                        java.lang.String.class, double.class, double.class}),
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Promedio
            descripcionFuncion = "Promedio([Números])";
            descripcionLargaFuncion = "Promedio([Números]): La media aritmética"
                    + " de un grupo de números.";
            categoria = categoriaEstadisticasMedias;
            alternativas = "prom, promedio, maritmetica, mediaaritmetica, avg, "
                    + "average, arithmeticmean";
            
            nombre = "prom";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("prom", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "promedio";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("prom", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "maritmetica";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("prom", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "mediaaritmetica";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("prom", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "avg";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("prom", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "average";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("prom", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arithmeticmean";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("prom", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Maximo sobre datos
            descripcionFuncion = "Máximo sobre datos([Números])";
            descripcionLargaFuncion = "Máximo sobre datos([Números]): Encuentra"
                    + " el mayor valor entre un conjunto de datos.";
            categoria = new String[]{};
            alternativas = "maxdatos, maximodatos, maximumdata, maxdata";
            
            nombre = "maxdatos";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("maximo", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "maximodatos";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("maximo", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "maximumdata";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("maximo", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "maxdata";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("maximo", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Minimo sobre datos
            descripcionFuncion = "Mínimo sobre datos([Números])";
            descripcionLargaFuncion = "Mínimo sobre datos([Números]): Encuentra"
                    + " el menor valor entre un conjunto de datos.";
            categoria = categoriaEstadisticas;
            alternativas = "mindatos, minimodatos, minimumdata, mindata";
            
            nombre = "mindatos";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("minimo", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "minimodatos";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("minimo", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "minimumdata";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("minimo", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "mindata";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("minimo", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            //Rango
            descripcionFuncion = "Rango([Números])";
            descripcionLargaFuncion = "Rango([Números]): Diferencia entre el ma"
                    + "yor valor y el menor valor de un conjunto de datos.";
            categoria = categoriaEstadisticas;
            alternativas = "rango, rank";
            
            nombre = "rango";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rango", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rank";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rango", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            //Media Geométrica
            descripcionFuncion = "Media Geométrica([Números])";
            descripcionLargaFuncion = "Media Geométrica([Números]): La media ge"
                    + "ométrica de un grupo de números.";
            categoria = categoriaEstadisticasMedias;
            alternativas = "mgeom, mgeometrica, mediageometrica, geometricmean";
            
            nombre = "mgeom";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("mediaGeom", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "mgeometrica";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("mediaGeom", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "mediageometrica";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("mediaGeom", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "geometricmean";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("mediaGeom", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;

            //Media Armónica
            descripcionFuncion = "Media Armónica([Números])";
            descripcionLargaFuncion = "Media Armónica([Números]): La media armó"
                    + "nica de un grupo de números.";
            categoria = categoriaEstadisticasMedias;
            alternativas = "marmon, mharmon, harmonicmean, mediaarmonica";
            
            nombre = "marmon";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("mediaArmon", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "mharmon";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("mediaArmon", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "harmonicmean";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("mediaArmon", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "mediaarmonica";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("mediaArmon", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            //Mediana
            descripcionFuncion = "Mediana([Números])";
            descripcionLargaFuncion = "Mediana([Números]): La mediana de un gru"
                    + "po de números.";
            categoria = categoriaEstadisticas;
            alternativas = "mediana, median";
            
            nombre = "mediana";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("mediana", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "median";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("mediana", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            //Moda
            descripcionFuncion = "Moda([Números])";
            descripcionLargaFuncion = "Moda([Números]): La moda de un conjunto "
                    + "de datos.";
            categoria = categoriaEstadisticas;
            alternativas = "moda, mode";
            
            nombre = "moda";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("moda", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "mode";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("moda", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            //Contar
            descripcionFuncion = "Contar([Números])";
            descripcionLargaFuncion = "Contar([Números]): Tamaño del conjunto d"
                    + "e entrada";
            categoria = categoriaEstadisticas;
            alternativas = "contar, count";
            
            nombre = "contar";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("contar", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "count";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("contar", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            //ContarSi
            descripcionFuncion = "Contar Si(([Números]), x)";
            descripcionLargaFuncion = "Contar Si(([Números]), x): Cuenta los va"
                    + "lores equivalentes a x.";
            categoria = categoriaEstadisticas;
            alternativas = "contarsi, countif";
            
            nombre = "contarsi";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("contarSi", new Class[] {
                        java.lang.String.class, double.class}),
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "countif";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("contarSi", new Class[] {
                        java.lang.String.class, double.class}),
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            //Permutaciones sin repeticion
            descripcionFuncion = "Permutaciones sin Repetición(x, y)";
            descripcionLargaFuncion = "Permutaciones sin Repetición(x, y): Núme"
                    + "ro de formas en que se pueden organizar y elementos de x"
                    + ".";
            categoria = categoriaEstadisticas;
            alternativas = "npr, npermuter, npermutar";
            
            nombre = "npr";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("npr",
                    new Class[] {double.class,
                        double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "npermuter";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("npr",
                    new Class[] {double.class,
                        double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "npermutar";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("npr",
                    new Class[] {double.class,
                        double.class}), FuncionesNativas.class,
                     descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            //Combinaciones sin repeticion
            descripcionFuncion = "Combinaciones sin Repetición(x, y)";
            descripcionLargaFuncion = "Combinaciones sin Repetición(x, y): Núme"
                    + "ro de grupos de tamaño y en x.";
            categoria = categoriaEstadisticas;
            alternativas = "ncr, ncombiner, ncombinar";
            
            nombre = "ncr";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("ncr",
                    new Class[] {double.class, double.class}), 
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "ncombiner";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("ncr",
                    new Class[] {double.class, double.class}), 
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "ncombinar";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("ncr",
                    new Class[] {double.class, double.class}), 
                    FuncionesNativas.class, descripcionFuncion, 
                    descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Sumatoria
            descripcionFuncion = "Sumatoria([números])";
            descripcionLargaFuncion = "Sumatoria([números]): Suma un conjunto "
                    + "de números.";
            categoria = categoriaEstadisticas;
            alternativas = "suma, sumatoria, sum, summation";
            
            nombre = "suma";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("suma", new Class[] {
                        String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "sumatoria";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("suma", new Class[] {
                        String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "sum";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("suma", new Class[] {
                        String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "summation";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("suma", new Class[] {
                        String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Sumatoria Producto
            descripcionFuncion = "SumatoriaProducto([números])";
            descripcionLargaFuncion = "SumatoriaProducto([números]): Eleva, cad"
                    + "a elemento de, un conjunto de números al cuadrado. Luego"
                    + ", suma los cuadrados.";
            categoria = categoriaEstadisticas;
            alternativas = "sumap, sumatoriap, sump, summationp, sumaproducto, "
                    + "sumatoriaproducto, sumproduct, summationproduct";
            
            nombre = "sumap";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("sumaProducto", new Class[] {
                        String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "sumatoriap";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("sumaProducto", new Class[] {
                        String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "sump";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("sumaProducto", new Class[] {
                        String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "summationp";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("sumaProducto", 
                    new Class[] {String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "sumaproducto";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("sumaProducto", 
                    new Class[] {String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "sumatoriaproducto";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("sumaProducto", 
                    new Class[] { String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "sumproduct";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("sumaProducto", 
                    new Class[] { String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "summationproduct";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("sumaProducto", 
                    new Class[] { String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            //Post-añadidas


            //Frecuencia
            descripcionFuncion = "Frecuencia([números], dato)";
            descripcionLargaFuncion = "Frecuencia([numeros], dato): Halla el número de ocurrencias de un dato en una lista de datos numéricos.";
            categoria = categoriaEstadisticas;
            alternativas = "frecuencia, frecuency";
            
            nombre = "frecuencia";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("frecuencia", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "frequency";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("frecuencia", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            

            //Curtosis (Fisher)
            descripcionFuncion = "CurtosisFisher([números])";
            descripcionLargaFuncion = "CurtosisFisher([numeros]): Mide cómo se reparten las frecuencias relativas de los datos entre el centro y los extremos, tomando como comparación la campana de Gauss. Usa la varianza y promedio.";
            categoria = categoriaEstadisticasCurtosis;
            alternativas = "cfisher, kfisher, curtosisf, curtosisfisher, kurtosisf, kurtosisfisher";
            
            nombre = "cfisher";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("curtosisFisher", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "kfisher";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("curtosisFisher", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "curtosisf";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("curtosisFisher", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "curtosisfisher";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("curtosisFisher", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "kurtosisf";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("curtosisFisher", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "kurtosisfisher";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("curtosisFisher", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;   
            
            
            //Desviación Estándar
            descripcionFuncion = "Desviación Estandar([números])";
            descripcionLargaFuncion = "Desviación Estandar([números]): La raíz cuadrada de: la media de las desviaciones respecto de la media, al cuadrado.";
            categoria = categoriaEstadisticas;
            alternativas = "desvestandar, stddeviation";
            
            nombre = "desvestandar";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("desvEstandar", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "stddeviation";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("desvEstandar", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Varianza
            descripcionFuncion = "Varianza([números])";
            descripcionLargaFuncion = "Varianza([números]): La media de las desviaciones respecto de la media, al cuadrado.";
            categoria = categoriaEstadisticas;
            alternativas = "var, variance, varianza";
            
            nombre = "var";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("varianza", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "variance";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("varianza", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "varianza";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("varianza", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //CuasiVarianza
            descripcionFuncion = "CuasiVarianza([números])";
            descripcionLargaFuncion = "CuasiVarianza([números]): La media de las desviaciones respecto de la media, al cuadrado. Multiplicada por: n/ (n - 1)";
            categoria = categoriaEstadisticas;
            alternativas = "cuasivar, cuasivariance, cuasivarianza";
            
            nombre = "cuasivar";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cuasiVarianza", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "cuasivariance";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cuasiVarianza", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "cuasivarianza";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cuasiVarianza", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Coeficiente de Variación de Pearson
            descripcionFuncion = "Coeficiente de Variación de Pearson([números])";
            descripcionLargaFuncion = "Coeficiente de Variación de Pearson([números]): El número de veces que la media está contenida en la desviación típica.";
            categoria = categoriaEstadisticas;
            alternativas = "cvar, coeficientevar, coeficientevariacion, coefficientvar, coefficientvariance";
            
            nombre = "cvar";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cVPearson", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "coeficientevar";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cVPearson", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "coeficientevariacion";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cVPearson", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "coefficientvar";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cVPearson", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "coefficientvariance";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cVPearson", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Coeficiente Apertura
            descripcionFuncion = "Coeficiente Apertura([números])";
            descripcionLargaFuncion = "Coeficiente Apertura([números]): El cociente entre los valores extremos de la distribución de datos. Se usa para comparar salarios de empresas.";
            categoria = categoriaEstadisticas;
            alternativas = "coefa, oprat, coefapertura, opratio, coeficienteapertura, opennessratio";
            
            nombre = "coefa";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cApertura", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "coefapertura";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cApertura", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "coeficienteapertura";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cApertura", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "oprat";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cApertura", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "opratio";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cApertura", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "opennessratio";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cApertura", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Rango Relativo
            descripcionFuncion = "Rango Relativo([números])";
            descripcionLargaFuncion = "Rango Relativo([números]): El cociente entre el rango y la media aritmética.";
            categoria = categoriaEstadisticas;
            alternativas = "rangor, rrank, rangorelativo, relativerank";
            
            nombre = "rangor";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rangoRelativo", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rrank";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rangoRelativo", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rangorelativo";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rangoRelativo", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "relativerank";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rangoRelativo", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Percentil
            descripcionFuncion = "Percentil([números], percentil)";
            descripcionLargaFuncion = "Percentil([números], percentil): Dato que deja por debajo de sí 'percentil' por ciento de los datos.";
            categoria = categoriaEstadisticasCentil;
            alternativas = "percentil";
            
            nombre = "percentil";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("percentil", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Cuartil
            descripcionFuncion = "Cuartil([números], cuartil)";
            descripcionLargaFuncion = "Cuartil([números], cuartil): Dato que deja por debajo de sí 'cuartil x 25' por ciento de los datos";
            categoria = categoriaEstadisticasCentil;
            alternativas = "cuartil, quartil";
            
            nombre = "cuartil";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("cuartil", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "quartil";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("cuartil", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Decil
            descripcionFuncion = "Decil([números], decil)";
            descripcionLargaFuncion = "Decil([números], decil): Dato que deja por debajo de sí 'cuartil x 10' por ciento de los datos";
            categoria = categoriaEstadisticasCentil;
            alternativas = "decil";
            
            nombre = "decil";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("decil", new Class[] {
                        java.lang.String.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Recorrido Intercuartílico Relativo
            descripcionFuncion = "Recorrido Intercuartílico Relativo([números])";
            descripcionLargaFuncion = "Recorrido Intercuartílico Relativo([números]): (Q3 - Q1) / Q2.";
            categoria = categoriaEstadisticas;
            alternativas = "ricr, riqr, recorridointercuartilicorelativo, relativeinterquartilerange";
            
            nombre = "ricr";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rIQR", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "riqr";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rIQR", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "recorridointercuartilicorelativo";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rIQR", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "relativeinterquartilerange";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rIQR", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Recorrido Semiintercuartílico Relativo
            descripcionFuncion = "Recorrido Semiintercuartílico Relativo([números])";
            descripcionLargaFuncion = "Recorrido Semiintercuartílico Relativo([números]): (Q3 - Q1) / (Q3 + Q1).";
            categoria = categoriaEstadisticas;
            alternativas = "rsir, rsicr, rsiqr, recorridosemiintercuartilicorelativo, relativesemiinterquartilerange";
            
            nombre = "rsir";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rSIR", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rsicr";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rSIR", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rsiqr";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rSIR", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "recorridosemiintercuartilicorelativo";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rSIR", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "relativesemiinterquartilerange";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("rSIR", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Coeficiente de Asimetría de Bowley
            descripcionFuncion = "Coeficiente de Asimetría de Bowley([números])";
            descripcionLargaFuncion = "Coeficiente de Asimetría de Bowley([números]): (Q3 + Q1 - 2 x Q2) / (Q3 - Q1).";
            categoria = categoriaEstadisticasAsimetrias;
            alternativas = "asimbowley, asimetriab, skewnessb, skewbowley";
            
            nombre = "asimbowley";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cABowley", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "asimetriab";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cABowley", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "skewnessb";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cABowley", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "skewbowley";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cABowley", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Coeficiente de Asimetría de Pearson
            descripcionFuncion = "Coeficiente de Asimetría de Pearson([números])";
            descripcionLargaFuncion = "Coeficiente de Asimetría de Pearson([números]): (promedio - moda ) / desviación estándar.";
            categoria = categoriaEstadisticasAsimetrias;
            alternativas = "asimpearson, asimetriap, skewnessp, skewpearson";
            
            nombre = "asimpearson";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cAPearson", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "asimetriap";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cAPearson", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "skewnessp";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cAPearson", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "skewpearson";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("cAPearson", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Volumen de un Prisma Regular
            descripcionFuncion = "Volumen de un Prisma Regular(area base, altura)";
            descripcionLargaFuncion = "Volumen de un Prisma Regular(area base, altura): area base x altura.";
            categoria = categoriaVolumenes;
            alternativas = "vprismareg, vregprism, volumenpreg, volumeregp, vregularp, vpregular";
            
            nombre = "vprismareg";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volPrismaRegular", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "vregprism";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volPrismaRegular", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumenpreg";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volPrismaRegular", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumeregp";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volPrismaRegular", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "vregularp";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volPrismaRegular", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "vpregular";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volPrismaRegular", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Volumen Ortoedro
            descripcionFuncion = "Volumen Ortoedro(largo, alto, profundidad)";
            descripcionLargaFuncion = "Volumen Ortoedro(largo, alto, profundidad): largo x alto x profundidad.";
            categoria = categoriaVolumenes;
            alternativas = "vortoedro, vcuboid, volumecuboid, volumenorto";
            
            nombre = "vortoedro";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("volOrtoedro", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "vcuboid";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("volOrtoedro", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumecuboid";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("volOrtoedro", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumenorto";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("volOrtoedro", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Volumen Cubo
            descripcionFuncion = "Volumen Cubo(lado)";
            descripcionLargaFuncion = "Volumen Cubo(lado): lado ^ 3.";
            categoria = categoriaVolumenes;
            alternativas = "vcubo, vcube, volumenc, volumec";
            
            nombre = "vcubo";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("volCubo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "vcube";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("volCubo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumenc";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("volCubo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumec";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("volCubo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Volumen Cilindro
            descripcionFuncion = "Volumen Cilindro(radio, altura)";
            descripcionLargaFuncion = "Volumen Cilindro(radio, altura): PI x radio ^ 2 x altura.";
            categoria = categoriaVolumenes;
            alternativas = "vcilindro, vcylinder, volumencil, volumecyl";
            
            nombre = "vcilindro";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volCilindro", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "vcylinder";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volCilindro", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumencil";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volCilindro", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumecyl";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volCilindro", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Volumen Esfera
            descripcionFuncion = "Volumen Esfera(radio)";
            descripcionLargaFuncion = "Volumen Esfera(radio): (4 / 3) x PI x radio ^ 3.";
            categoria = categoriaVolumenes;
            alternativas = "vesfera, vsphere, volumene, volumes";
            
            nombre = "vesfera";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("volEsfera", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "vsphere";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("volEsfera", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumene";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("volEsfera", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumes";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("volEsfera", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Volumen Elipsoide
            descripcionFuncion = "Volumen Elipsoide(a, b, c)";
            descripcionLargaFuncion = "Volumen Elipsoide(a, b, c): a, b, y c son los semi-ejes principales. Volumen = (4 / 3) x PI x a x b x c.";
            categoria = categoriaVolumenes;
            alternativas = "velipsoide, vellipsoid, volumenelipsoide, volumeellipsoid";
            
            nombre = "velipsoide";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("volElipsoide", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "vellipsoid";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("volElipsoide", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumenelipsoide";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("volElipsoide", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumeellipsoid";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("volElipsoide", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Volumen Pirámide
            descripcionFuncion = "Volumen Pirámide(area, altura)";
            descripcionLargaFuncion = "Volumen Pirámide(area, altura): 3 x altura x area.";
            categoria = categoriaVolumenes;
            alternativas = "vpiramide, vpyramid, volumenpiramide, volumepyramid";
            
            nombre = "vpiramide";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volPiramide", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "vpyramid";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volPiramide", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumenpiramide";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volPiramide", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumepyramid";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volPiramide", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Volumen Cono
            descripcionFuncion = "Volumen Cono(radio, altura)";
            descripcionLargaFuncion = "Volumen Cono(radio, altura): (1 / 3) x PI x radio ^ 2 x altura.";
            categoria = categoriaVolumenes;
            alternativas = "vcono, vcone, volumencono, volumecone";
            
            nombre = "vcono";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volCono", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "vcone";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volCono", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumencono";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volCono", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "volumecone";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("volCono", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Área Triángulo
            descripcionFuncion = "Área Triángulo(base, altura)";
            descripcionLargaFuncion = "Área Triángulo(base, altura): base x altura / 2.";
            categoria = categoriaAreas;
            alternativas = "atriangulo, areatriangulo, trianglea, trianglearea";
            
            nombre = "atriangulo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaTriangulo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "areatriangulo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaTriangulo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "trianglea";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaTriangulo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "trianglearea";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaTriangulo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Área Triángulo Herón
            descripcionFuncion = "Área Triángulo Herón(lado a, lado b, lado c)";
            descripcionLargaFuncion = "Área Triángulo Herón(lado a, lado b, lado c): Raiz Cuadrada de (s x (s - a) x (s - b) x (s - c)). Donde s es (a + b + c) / 2.";
            categoria = categoriaAreas;
            alternativas = "atrianguloh, atheron, heronta, areatrianguloheron, herontrianglearea";
            
            nombre = "atrianguloh";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("areaTrianguloHeron", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "atheron";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("areaTrianguloHeron", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "heronta";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("areaTrianguloHeron", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "areatrianguloheron";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("areaTrianguloHeron", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "herontrianglearea";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("areaTrianguloHeron", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Área Cuadrilatero
            descripcionFuncion = "Área Cuadrilatero(lado AB, lado BC, lado CD, lado DA, diagonal AC, diagonal BD)";
            descripcionLargaFuncion = "Área Cuadrilatero(lado AB, lado BC, lado CD, lado DA, diagonal AC, diagonal BD): (1 / 4) x raiz cuadrada de (4 x AC ^ 2 x BD ^ 2 - (AB ^ 2 + BC ^ 2 - CD ^ 2 - DA ^ 2) ^ 2).";
            categoria = categoriaAreas;
            alternativas = "acuadrilatero, areacuadrilatero, quadrilaterala, quadrilateralarea";
            
            nombre = "acuadrilatero";
            listaFunciones.add(new FuncionNativa(nombre, 6,
                    FuncionesNativas.class.getMethod("areaCuadrilatero", new Class[] {
                        double.class, double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "areacuadrilatero";
            listaFunciones.add(new FuncionNativa(nombre, 6,
                    FuncionesNativas.class.getMethod("areaCuadrilatero", new Class[] {
                        double.class, double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "quadrilaterala";
            listaFunciones.add(new FuncionNativa(nombre, 6,
                    FuncionesNativas.class.getMethod("areaCuadrilatero", new Class[] {
                        double.class, double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "quadrilateralarea";
            listaFunciones.add(new FuncionNativa(nombre, 6,
                    FuncionesNativas.class.getMethod("areaCuadrilatero", new Class[] {
                        double.class, double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Área Rombo
            descripcionFuncion = "Área Rombo(diagonal AC, diagonal BD)";
            descripcionLargaFuncion = "Área Rombo(diagonal AC, diagonal BD): AC x BD / 2.";
            categoria = categoriaAreas;
            alternativas = "arombo, arearombo, rhombusa, rhombusarea";
            
            nombre = "arombo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaRombo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arearombo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaRombo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rhombusa";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaRombo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rhombusarea";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaRombo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Área Rectángulo
            descripcionFuncion = "Área Rectángulo(base, altura)";
            descripcionLargaFuncion = "Área Rectángulo(base, altura): base x altura.";
            categoria = categoriaAreas;
            alternativas = "arectangulo, arearectangulo, rectanglea, rectanglearea";
            
            nombre = "arectangulo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaRectangulo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arearectangulo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaRectangulo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rectanglea";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaRectangulo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rectanglearea";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaRectangulo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Área Cuadrado
            descripcionFuncion = "Área Cuadrado(lado)";
            descripcionLargaFuncion = "Área Cuadrado(lado): lado ^ 2.";
            categoria = categoriaAreas;
            alternativas = "acuadrado, squarea, areacuadrado, squarearea";
            
            nombre = "acuadrado";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("areaCuadrado", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "squarea";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("areaCuadrado", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "areacuadrado";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("areaCuadrado", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "squarearea";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("areaCuadrado", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Área Romboide
            descripcionFuncion = "Área Romboide(base, altura)";
            descripcionLargaFuncion = "Área Romboide(base, altura): base x altura.";
            categoria = categoriaAreas;
            alternativas = "aromboide, rhomboida, arearomboide, rhomboidarea";
            
            nombre = "aromboide";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaRomboide", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rhomboida";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaRomboide", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "arearomboide";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaRomboide", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rhomboidarea";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaRomboide", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Área Trapecio
            descripcionFuncion = "Área Trapecio(base menor, base mayor, altura)";
            descripcionLargaFuncion = "Área Trapecio(base menor, base mayor, altura): (base menor + base mayor) x altura / 2.";
            categoria = categoriaAreas;
            alternativas = "atrapecio, trapezea, areatrapecio, trapezearea";
            
            nombre = "atrapecio";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("areaTrapecio", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "trapezea";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("areaTrapecio", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "areatrapecio";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("areaTrapecio", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "trapezearea";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("areaTrapecio", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Área Círculo
            descripcionFuncion = "Área Círculo(radio)";
            descripcionLargaFuncion = "Área Círculo(radio): PI x radio ^ 2.";
            categoria = categoriaAreas;
            alternativas = "acirculo, circlea, areacirculo, circlearea";
            
            nombre = "acirculo";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("areaCirculo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "circlea";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("areaCirculo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "areacirculo";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("areaCirculo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "circlearea";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("areaCirculo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Área Elipse
            descripcionFuncion = "Área Elipse(semi-eje mayor, semi-eje menor)";
            descripcionLargaFuncion = "Área Elipse(semi-eje mayor, semi-eje men"
                    + "or): Una elipse es la curva simétrica cerrada que result"
                    + "a al cortar la superficie de un cono por un plano oblicu"
                    + "o al eje de simetría –con ángulo mayor que el de la gene"
                    + "ratriz respecto del eje de revolución. Área = PI x semi-"
                    + "eje mayor x semi-eje menor.";
            categoria = categoriaAreas;
            alternativas = "aelipse, ellipsea, areaelipse, ellipsearea";
            
            nombre = "aelipse";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaElipse", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "ellipsea";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaElipse", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "areaelipse";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaElipse", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "ellipsearea";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaElipse", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Área Esfera
            descripcionFuncion = "Área Esfera(radio)";
            descripcionLargaFuncion = "Área Esfera(radio): 2 x PI x radio ^ 2.";
            categoria = categoriaAreas;
            alternativas = "aesfera, areaesfera, spherea, spherearea";
            
            nombre = "aesfera";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("areaEsfera", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "areaesfera";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("areaEsfera", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "spherea";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("areaEsfera", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "spherearea";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("areaEsfera", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Área Cono
            descripcionFuncion = "Área Cono(radio, altura)";
            descripcionLargaFuncion = "Área Cono(radio, altura): PI x radio x ("
                    + "radio + raiz cuadrada de (altura ^ 2 + radio ^ 2)).";
            categoria = categoriaAreas;
            alternativas = "acono, areacono, conea, conearea";
            
            nombre = "acono";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaCono", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "areacono";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaCono", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "conea";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaCono", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "conearea";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaCono", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Área Cilindro
            descripcionFuncion = "Área Cilindro(radio, altura)";
            descripcionLargaFuncion = "Área Cilindro(radio, altura): PI x radio x (2 x altura + radio).";
            categoria = categoriaAreas;
            alternativas = "acilindro, areacilindro, cylindera, cylinderarea";
            
            nombre = "acilindro";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaCilindro", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "areacilindro";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaCilindro", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "cylindera";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaCilindro", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "cylinderarea";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("areaCilindro", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Distancia entre un punto y una recta
            descripcionFuncion = "Distancia entre un punto y una recta(pendiente, desplazamiento, X, Y)";
            descripcionLargaFuncion = "Distancia entre un punto y una recta(pendiente, desplazamiento, X, Y): valor absoluto (Y - (X x pendiente) + desplazamiento) / raiz cuadrada de (m  ^ 2 + 1). Donde X e Y son las coordenadas del punto.";
            categoria = categoriaDistancias;
            alternativas = "dpuntorecta, pointlined, distanciapuntorecta, pointlinedistance";
            
            nombre = "dpuntorecta";
            listaFunciones.add(new FuncionNativa(nombre, 4,
                    FuncionesNativas.class.getMethod("dPuntoRecta", new Class[] {
                        double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "pointlined";
            listaFunciones.add(new FuncionNativa(nombre, 4,
                    FuncionesNativas.class.getMethod("dPuntoRecta", new Class[] {
                        double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "distanciapuntorecta";
            listaFunciones.add(new FuncionNativa(nombre, 4,
                    FuncionesNativas.class.getMethod("dPuntoRecta", new Class[] {
                        double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "pointlinedistance";
            listaFunciones.add(new FuncionNativa(nombre, 4,
                    FuncionesNativas.class.getMethod("dPuntoRecta", new Class[] {
                        double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Distancia entre un punto y un plano
            descripcionFuncion = "Distancia entre un punto y un plano(a, b, c, d, X, Y, Z)";
            descripcionLargaFuncion = "Distancia entre un punto y un plano(a, b, c, d, X, Y, Z): Valor absoluto(a x X + b x Y + C x Z + D) / raiz cuadrada de (a ^ 2 + b ^ 2 + c ^ 2). Donde X, Y e Z son las coordenadas del punto. Y la ecuación del plano es: Ax + By + Cz + D = 0.";
            categoria = categoriaDistancias;
            alternativas = "dpuntoplano, pointplaned, distanciapuntoplano, pointplanedistance";
            
            nombre = "dpuntoplano";
            listaFunciones.add(new FuncionNativa(nombre, 7,
                    FuncionesNativas.class.getMethod("dPuntoPlano", new Class[] {
                        double.class, double.class, double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "pointplaned";
            listaFunciones.add(new FuncionNativa(nombre, 7,
                    FuncionesNativas.class.getMethod("dPuntoPlano", new Class[] {
                        double.class, double.class, double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "distanciapuntoplano";
            listaFunciones.add(new FuncionNativa(nombre, 7,
                    FuncionesNativas.class.getMethod("dPuntoPlano", new Class[] {
                        double.class, double.class, double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "pointplanedistance";
            listaFunciones.add(new FuncionNativa(nombre, 7,
                    FuncionesNativas.class.getMethod("dPuntoPlano", new Class[] {
                        double.class, double.class, double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Distancia entre dos rectas
            descripcionFuncion = "Distancia entre dos rectas(pendiente, desplazamiento primera, desplazamiento segunda)";
            descripcionLargaFuncion = "Distancia entre dos rectas(pendiente, desplazamiento primera, desplazamiento segunda): valor absoluto de (desplazamiento primera - desplazamiento segunda) / raiz cuadrada de (pendiente ^ 2 + 1). Las dos rectas están en coordenadas cartesianas y son paralelas (sino lo fueran la distancia sería cero).";
            categoria = categoriaDistancias;
            alternativas = "drectarecta, linelined, distanciarectarecta, linelinedistance";
            
            nombre = "drectarecta";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("dRectaRecta", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "linelined";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("dRectaRecta", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "distanciarectarecta";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("dRectaRecta", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "linelinedistance";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("dRectaRecta", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Distancia entre una recta y un plano
            descripcionFuncion = "Distancia entre una recta y un plano(X, Y, Z, A1, A2, A3, A, B, C, D)";
            descripcionLargaFuncion = "Distancia entre una recta y un plano(X, Y, Z, A1, A2, A3, A, B, C, D): La distancia se mide en dos puntos distintos de la recta. Si la distancia es la misma se devuelve, sino la distancia es cero. La ecuación del plano es: Ax + By + Cz + D = 0. Y la ecuación de la recta es: x = A1 x t + X; y = A2 x t + Y; z = A3 x t + Z.";
            categoria = categoriaDistancias;
            alternativas = "drectaplano, distanciarectaplano, lineplaned, lineplanedistance";
            
            nombre = "drectaplano";
            listaFunciones.add(new FuncionNativa(nombre, 10,
                    FuncionesNativas.class.getMethod("dRectaPlano", new Class[] {
                        double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "distanciarectaplano";
            listaFunciones.add(new FuncionNativa(nombre, 10,
                    FuncionesNativas.class.getMethod("dRectaPlano", new Class[] {
                        double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "lineplaned";
            listaFunciones.add(new FuncionNativa(nombre, 10,
                    FuncionesNativas.class.getMethod("dRectaPlano", new Class[] {
                        double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "lineplanedistance";
            listaFunciones.add(new FuncionNativa(nombre, 10,
                    FuncionesNativas.class.getMethod("dRectaPlano", new Class[] {
                        double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Distancia entre dos planos
            descripcionFuncion = "Distancia entre dos planos(A, B, C, D1, D2)";
            descripcionLargaFuncion = "Distancia entre dos planos(A, B, C, D1, D2): valor absoluto de (D2 - D1) / raiz cuadrada de (A ^ 2 + B ^ 2 + C ^ 2). Donde la ecuación del primer plano es: Ax + By + Cz + D1 = 0. Y la ecuación del segundo plano es: Ax + By + Cz + D2 = 0.";
            categoria = categoriaDistancias;
            alternativas = "dplanoplano, distanciaplanoplano, planeplaned, planeplanedistance";
            
            nombre = "dplanoplano";
            listaFunciones.add(new FuncionNativa(nombre, 5,
                    FuncionesNativas.class.getMethod("dPlanoPlano", new Class[] {
                        double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "distanciaplanoplano";
            listaFunciones.add(new FuncionNativa(nombre, 5,
                    FuncionesNativas.class.getMethod("dPlanoPlano", new Class[] {
                        double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "planeplaned";
            listaFunciones.add(new FuncionNativa(nombre, 5,
                    FuncionesNativas.class.getMethod("dPlanoPlano", new Class[] {
                        double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "planeplanedistance";
            listaFunciones.add(new FuncionNativa(nombre, 5,
                    FuncionesNativas.class.getMethod("dPlanoPlano", new Class[] {
                        double.class, double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Distancia entre dos puntos
            descripcionFuncion = "Distancia entre dos puntos(X0, Y0, X1, Y1)";
            descripcionLargaFuncion = "Distancia entre dos puntos(X0, Y0, X1, Y1): raiz cuadrada de ((X1 - X0) ^ 2 + (Y1 - Y0) ^ 2). Donde X0 y Y0; X1 y Y1 son las coordenadas cartesianas del primero y del segundo punto, repectivamente.";
            categoria = categoriaDistancias;
            alternativas = "dpuntopunto, distanciapuntopunto, pointpointd, pointpointdistance";
            
            nombre = "dpuntopunto";
            listaFunciones.add(new FuncionNativa(nombre, 4,
                    FuncionesNativas.class.getMethod("dPuntoPunto", new Class[] {
                        double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "distanciapuntopunto";
            listaFunciones.add(new FuncionNativa(nombre, 4,
                    FuncionesNativas.class.getMethod("dPuntoPunto", new Class[] {
                        double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "pointpointd";
            listaFunciones.add(new FuncionNativa(nombre, 4,
                    FuncionesNativas.class.getMethod("dPuntoPunto", new Class[] {
                        double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "pointpointdistance";
            listaFunciones.add(new FuncionNativa(nombre, 4,
                    FuncionesNativas.class.getMethod("dPuntoPunto", new Class[] {
                        double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Perímetro cuadrado
            descripcionFuncion = "Perímetro cuadrado(lado)";
            descripcionLargaFuncion = "Perímetro cuadrado(lado): 4 x lado.";
            categoria = categoriaPerimetros;
            alternativas = "pcuadrado, perimetrocuadrado, scuarep, scuareperimeter";
            
            nombre = "pcuadrado";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("pCuadrado", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "perimetrocuadrado";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("pCuadrado", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "scuarep";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("pCuadrado", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "scuareperimeter";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("pCuadrado", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Perímetro rectángulo
            descripcionFuncion = "Perímetro rectángulo(lado a, lado b)";
            descripcionLargaFuncion = "Perímetro Rectángulo(lado a, lado b): 2 x a + 2 x b. Donde a y b son lados adyacentes.";
            categoria = categoriaPerimetros;
            alternativas = "prectangulo, perimetrorectangulo, rectanglep, rectangleperimeter";
            
            nombre = "prectangulo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("pRectangulo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "perimetrorectangulo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("pRectangulo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rectanglep";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("pRectangulo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rectangleperimeter";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("pRectangulo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Perímetro Rombo
            descripcionFuncion = "Perímetro Rombo(lado a, lado b)";
            descripcionLargaFuncion = "Perímetro Rombo(lado a, lado b): 2 x a + 2 x b. Donde a y b son lados adyacentes.";
            categoria = categoriaPerimetros;
            alternativas = "prombo, perimetrorombo, rhombusp, rhombusperimeter";
            
            nombre = "prombo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("pRombo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "perimetrorombo";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("pRombo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rhombusp";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("pRombo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rhombusperimeter";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("pRombo", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Perímetro Triángulo
            descripcionFuncion = "Perímetro Triángulo(lado a, lado b, lado c)";
            descripcionLargaFuncion = "Perímetro Triángulo(lado a, lado b, lado c): a + b + c.";
            categoria = categoriaPerimetros;
            alternativas = "ptriangulo, perimetrotriangulo, trianglep, triangleperimeter";
            
            nombre = "ptriangulo";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("pTriangulo", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "perimetrotriangulo";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("pTriangulo", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "trianglep";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("pTriangulo", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "triangleperimeter";
            listaFunciones.add(new FuncionNativa(nombre, 3,
                    FuncionesNativas.class.getMethod("pTriangulo", new Class[] {
                        double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Perímetro Romboide
            descripcionFuncion = "Perímetro Romboide(lado AB, lado BC, lado CD, lado DA)";
            descripcionLargaFuncion = "Perímetro Romboide(lado AB, lado BC, lado CD, lado DA): AB + BC + CD + DA.";
            categoria = categoriaPerimetros;
            alternativas = "promboide, perimetroromboide, rhomboidp, rhomboidperimeter";
            
            nombre = "promboide";
            listaFunciones.add(new FuncionNativa(nombre, 4,
                    FuncionesNativas.class.getMethod("pRomboide", new Class[] {
                        double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "perimetroromboide";
            listaFunciones.add(new FuncionNativa(nombre, 4,
                    FuncionesNativas.class.getMethod("pRomboide", new Class[] {
                        double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rhomboidp";
            listaFunciones.add(new FuncionNativa(nombre, 4,
                    FuncionesNativas.class.getMethod("pRomboide", new Class[] {
                        double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "rhomboidperimeter";
            listaFunciones.add(new FuncionNativa(nombre, 4,
                    FuncionesNativas.class.getMethod("pRomboide", new Class[] {
                        double.class, double.class, double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Perímetro círculo
            descripcionFuncion = "Perímetro círculo(radio)";
            descripcionLargaFuncion = "Perímetro círculo(radio): 2 x PI x radio.";
            categoria = categoriaPerimetros;
            alternativas = "pcirculo, perimetrocirculo, circlep, circleperimeter";
            
            nombre = "pcirculo";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("pCirculo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "perimetrocirculo";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("pCirculo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "circlep";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("pCirculo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "circleperimeter";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("pCirculo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Perímetro elipse
            descripcionFuncion = "Perímetro elipse(foco a, foco b)";
            descripcionLargaFuncion = "Perímetro elipse(lado a, lado b): PI x [3 x (a + b) - raiz cuadrada de ((3 x a + b) x (a + 3 x b))]. Esta esuna aproximación hecha por el matemático indio Ramanujan.";
            categoria = categoriaPerimetros;
            alternativas = "pelipse, perimetroelipse, ellipsep, ellipseperimeter";
            
            nombre = "pelipse";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("pElipse", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "perimetroelipse";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("pElipse", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "ellipsep";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("pElipse", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "ellipseperimeter";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("pElipse", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Test de primalidad
            descripcionFuncion = "Test de primalidad(número)";
            descripcionLargaFuncion = "Test de primalidad: Retorna uno (1) si el número es primo, o 0 (cero) si no es primo. Un número es primo si no existe algún número, menor que él y mayor que 1 (uno), tal que el residuo de la división entre los dos sea 0 (cero).";
            categoria = categoriaNumerosPrimos;
            alternativas = "esprimo, isprime, testprimalidad, primalitytest";
            
            nombre = "esprimo";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("esPrimo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "isprime";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("esPrimo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "testprimalidad";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("esPrimo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "primalitytest";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("esPrimo", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Número de primos
            descripcionFuncion = "Número de primos(minimo, maximo)";
            descripcionLargaFuncion = "Número de primos(minimo, maximo): Cuenta el número de primos en el intervalo [a, b]. Un número es primo si no existe algún número, menor que él y mayor que 1 (uno), tal que el residuo de la división entre los dos sea 0 (cero).";
            categoria = categoriaNumerosPrimos;
            alternativas = "nprimos, primec, numeroprimos, primecount";
            
            nombre = "nprimos";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("nPrimos", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "primec";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("nPrimos", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "numeroprimos";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("nPrimos", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "primecount";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("nPrimos", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Primo siguiente a un número
            descripcionFuncion = "Primo siguiente a un número(número)";
            descripcionLargaFuncion = "Primo siguiente a un número(número): El menor primo mayor que un número.";
            categoria = categoriaNumerosPrimos;
            alternativas = "mpmq, lpgt, menorprimomayorque, leastprimegreaterthan";
            
            nombre = "mpmq";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("menorPrimoMayorQue", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "lpgt";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("menorPrimoMayorQue", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "menorprimomayorque";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("menorPrimoMayorQue", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "leastprimegreaterthan";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("menorPrimoMayorQue", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Índice de Masa Corporal
            descripcionFuncion = "Índice de Masa Corporal(masa(Kg), altura(cm))";
            descripcionLargaFuncion = "Índice de Masa Corporal(masa(Kg), altura(cm)): masa / (altura ^ 2). El índice de masa corporal denota la relación entre la masa y la altura corporal. Los parámetros saludables oscilan entre 18.5 y 24.99.";
            categoria = categoriaSalud;
            alternativas = "imc, bmi, indicemasacorporal, bodymassindex";
            
            nombre = "imc";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("IMC", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "bmi";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("IMC", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "indicemasacorporal";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("IMC", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "bodymassindex";
            listaFunciones.add(new FuncionNativa(nombre, 2,
                    FuncionesNativas.class.getMethod("IMC", new Class[] {
                        double.class, double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Límite de Masa Corporal
            descripcionFuncion = "Límite de Masa Corporal(altura(cm))";
            descripcionLargaFuncion = "Límite de Masa Corporal(altura(cm)): 24.99 x (altura ^ 2). El índice de masa corporal denota la relación entre la masa y la altura corporal. El LMC es el límite superior de masa corporal saludable para cierta altura. Las alturas mayores a 0 (cero) son las únicas que se concideran.";
            categoria = categoriaSalud;
            alternativas = "lmc, bml, limitemasacorporal, bodymasslimit";
            
            nombre = "lmc";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("LMC", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "bml";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("LMC", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "limitemasacorporal";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("LMC", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "bodymasslimit";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("LMC", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Convertir segundos a horas
            descripcionFuncion = "Convertir segundos a horas(segundos)";
            descripcionLargaFuncion = "Convertir segundos a horas(segundos): segundos / 3600.";
            categoria = categoriaTiempo;
            alternativas = "convertirsh, convertirsegundoshoras, convertsh, convertsecondshours";
            
            nombre = "convertirsh";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convSegundosHoras", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertirsegundoshoras";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convSegundosHoras", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertsh";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convSegundosHoras", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertsecondshours";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convSegundosHoras", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Convertir segundos a minutos
            descripcionFuncion = "Convertir segundos a minutos(segundos)";
            descripcionLargaFuncion = "Convertir segundos a minutos(segundos): segundos / 60.";
            categoria = categoriaTiempo;
            alternativas = "convertirsm, convertirsegundosminutos, convertsm, convertsecondsminutes";
            
            nombre = "convertirsm";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convSegundosMinutos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertirsegundosminutos";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convSegundosMinutos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertsm";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convSegundosMinutos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertsecondsminutes";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convSegundosMinutos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Convertir horas a segundos
            descripcionFuncion = "Convertir horas a segundos(horas)";
            descripcionLargaFuncion = "Convertir horas a segundos(horas): horas x 3600.";
            categoria = categoriaTiempo;
            alternativas = "convertirhs, convertirhorassegundos, converths, converthoursseconds";
            
            nombre = "convertirhs";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convHorasSegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertirhorassegundos";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convHorasSegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "converths";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convHorasSegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "converthoursseconds";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convHorasSegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Convertir minutos a segundos
            descripcionFuncion = "Convertir minutos a segundos(minutos)";
            descripcionLargaFuncion = "Convertir minutos a segundos(minutos): minutos x 60.";
            categoria = categoriaTiempo;
            alternativas = "convertirms, convertirminutossegundos, convertms, convertminutesseconds";
            
            nombre = "convertirms";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convMinutosSegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertirminutossegundos";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convMinutosSegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertms";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convMinutosSegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertminutesseconds";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convMinutosSegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Convertir milisegundos a segundos
            descripcionFuncion = "Convertir milisegundos a segundos(milisegundos)";
            descripcionLargaFuncion = "Convertir milisegundos a segundos(milisegundos): milisegundos / 1000.";
            categoria = categoriaTiempo;
            alternativas = "convertirmss, convertmss, convertirmilisegundossegundos, convertmilisecondsseconds";
            
            nombre = "convertirmss";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convMilisegundosSegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertmss";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convMilisegundosSegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertirmilisegundossegundos";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convMilisegundosSegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertmilisecondsseconds";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convMilisegundosSegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Convertir segundos a milisegundos
            descripcionFuncion = "Convertir segundos a milisegundos(segundos)";
            descripcionLargaFuncion = "Convertir segundos a milisegundos(segundos): milisegundos x 1000.";
            categoria = categoriaTiempo;
            alternativas = "convertirsms, convertsms, convertirsegundosmilisegundos, convertsecondsmiliseconds";
            
            nombre = "convertirsms";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convSegundosMilisegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertsms";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convSegundosMilisegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertirsegundosmilisegundos";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convSegundosMilisegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "convertsecondsmiliseconds";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("convSegundosMilisegundos", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Masa Elemento(número elemento)
            descripcionFuncion = "Masa Elemento(número elemento)";
            descripcionLargaFuncion = "Masa Elemento(número elemento): A los elementos químicos conocidos se les asigna un número dependiendo de sus cualiades atómicas. Dicho número sirve para identificar a un elemento para, en este caso, obtener la masa atómica del elemento.";
            categoria = categoriaQuimica;
            alternativas = "masaelementon, masaelementonumero, elementmassn, elementmassnumber";
            
            nombre = "masaelementon";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("masaElemento", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "masaelementonumero";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("masaElemento", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "elementmassn";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("masaElemento", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "elementmassnumber";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("masaElemento", new Class[] {
                        double.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            
            //Masa Elemento(elemento)
            descripcionFuncion = "Masa Elemento(elemento)";
            descripcionLargaFuncion = "Masa Elemento(elemento): Masa atómica de un elemento con base en su nombre o su símbolo. El símbolo es una abreviación tomada del nombre del elemento en Latín.";
            categoria = categoriaQuimica;
            alternativas = "masaelemento, elementmass";
            
            nombre = "masaelemento";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("masaElemento", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            nombre = "elementmass";
            listaFunciones.add(new FuncionNativa(nombre, 1,
                    FuncionesNativas.class.getMethod("masaElemento", new Class[] {
                        java.lang.String.class}), FuncionesNativas.class, 
                    descripcionFuncion, descripcionLargaFuncion, categoria));
            mapaFunciones.put(nombre, alternativas);
            numeroDiferentes++;
            
            time = System.nanoTime() - time;
        } catch (NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(Funcion.class.getName()).log(Level.SEVERE, null,
                    ex);
            System.out.println("carajo");
        } finally {
            funciones = listaFunciones;
            funcionesNativas = mapaFunciones;
            System.out.println("Total Funciones Nativas: " + numeroDiferentes);
            System.out.println("Total Nombres Registrados: " + mapaFunciones.size());
            System.out.println("Promedio Nombres Registrados por Función: " + (((double) numeroDiferentes) / listaFunciones.size()));
            System.out.println("Tiempo: " + (time / 1e9) + " segundos");
        }
    }

    /**
     *
     * @param expresion
     * <p/>
     * @throws CaracterIlegal
     */
    public static void agregarFuncion(String expresion) throws CaracterIlegal {
        if (expresion == null || expresion.isEmpty()) {
            throw new CaracterIlegal(" ", 0);
        } else {
            int primerIgual = expresion.indexOf("=");
            int segundoIgual = expresion.indexOf("=", primerIgual + 1);

            if (primerIgual < 0) {
                throw new CaracterIlegal(expresion, expresion.length() - 1);
            } else if (segundoIgual >= 0) {
                throw new CaracterIlegal(expresion, segundoIgual);
            } else {
                String[] partesExpresion = expresion.split("=");
                String partesFirma = partesExpresion[0].trim();
                String partesCambio = partesExpresion[1].trim();
                
                int inicioParentesis = partesFirma.indexOf("(");
                int inicioParentesisCuadrado = partesFirma.indexOf("[");
                int finParentesis = partesFirma.indexOf(")");
                int finParentesisCuadrado = partesFirma.indexOf("]");
                int segundoInicioParentesis = partesFirma.indexOf("(",
                        inicioParentesis + 1);
                int segundoInicioParentesisCuadrado = partesFirma.indexOf("[",
                        inicioParentesisCuadrado + 1);
                int segundoFinParentesis = partesFirma.indexOf(")", 
                        finParentesis + 1);
                int segundoFinParentesisCuadrado = partesFirma.indexOf("]", 
                        finParentesisCuadrado + 1);
                
                if(!((inicioParentesis >= 0 
                        && inicioParentesis < partesFirma.length() - 1)
                         ^ (inicioParentesisCuadrado >= 0 
                        && inicioParentesisCuadrado < partesFirma.length() 
                        - 1))
                        || !((finParentesis > 0 
                        && finParentesis < partesFirma.length())
                         ^ (finParentesisCuadrado > 0 
                        && finParentesisCuadrado < partesFirma.length()))
                        || (segundoInicioParentesis >= 0)
                        || (segundoInicioParentesisCuadrado >= 0)
                        || (segundoFinParentesis >= 0)
                        || (segundoFinParentesisCuadrado >= 0)) {
                    throw new CaracterIlegal(expresion, 0);
                }
                
                int inicio = Math.max(inicioParentesis, 
                        inicioParentesisCuadrado);
                int fin = Math.max(finParentesis, finParentesisCuadrado);
                
                String nombre = partesFirma.substring(0, inicio);
                String[] argumentos = partesFirma.substring(inicio + 1, fin)
                        .split(",");
                String[] argumentosRepeticion = Arrays.copyOf(argumentos,
                        argumentos.length);
                Arrays.sort(argumentosRepeticion);

                for (int i = 1; i < argumentosRepeticion.length; i++) {
                    if (argumentosRepeticion[i].equals(argumentosRepeticion[i -
                            1])) {
                        throw new CaracterIlegal(expresion, 0);
                    }
                }

                for (int i = 0; i < argumentos.length; i++) {
                    argumentos[i] = argumentos[i].trim();

                    if (!patronVariable.matcher(argumentos[i]).matches()) {
                        throw new CaracterIlegal(expresion, 0);
                    }
                }

                String[] rpn = Procesador.convertirRPN(Procesador
                        .evaluarRPN(Procesador.convertirRPN(partesCambio))
                        .toString());
                
                funciones.add(new FuncionPersonalizada(nombre, argumentos,
                        rpn));
            }
        }
    }

    /**
     *
     * @param cadenaProcesar
     * @return
     */
    public static String[] listaFuncionesSufijoDescendente(String cadenaProcesar) {
        String cadena = cadenaProcesar.toLowerCase();
        Queue<String> colaFunciones = new LinkedList<>();

        for (Funcion funcion : funciones) {
            String nombre = funcion.nombre.trim().toLowerCase();

            if (cadena.endsWith(nombre)) {
                colaFunciones.offer(nombre);
            }
        }

        String[] salidaAscendente = colaFunciones
                .toArray(new String[colaFunciones.size()]);
        String[] salidaDescendente = new String[salidaAscendente.length];
        sort(salidaAscendente);

        for (int i = 0, j = salidaDescendente.length - 1;
                i < salidaAscendente.length; i++, j--) {
            salidaDescendente[j] = salidaAscendente[i];
        }

        return salidaDescendente;
    }

    /**
     *
     * @return
     */
    public static NavigableMap<String, String> listaFuncionesNativas() {
        return new TreeMap<>(funcionesNativas);
    }

    
    /**
     *
     * @param argumentos
     */
    public void actualizarArgumentos(Object[] argumentos) {
        if (argumentos != null) {
            if (!nativa) {
                boolean todosBigDecimal = true;

                for (Object objeto : argumentos) {
                    if (objeto == null || !(objeto instanceof BigDecimal)) {
                        todosBigDecimal = false;
                        break;
                    }
                }

                computable = todosBigDecimal;
            } else {
                FuncionNativa estaFuncion = (FuncionNativa) this;
                java.lang.reflect.Method metodo = estaFuncion.metodoJava;
                Class[] clasesArgumentos = metodo.getParameterTypes();
                boolean parametrosCorrectos = true;

                if (argumentos.length != clasesArgumentos.length) {
                    computable = false;
                } else {
                    for (int i = 0; i < argumentos.length; i++) {
                        if (argumentos[i] instanceof BigDecimal) {
                            argumentos[i] = ((BigDecimal) argumentos[i])
                                    .doubleValue();
                        } else if (argumentos[i] instanceof Double) {
                            argumentos[i] = ((Double) argumentos[i])
                                    .doubleValue();
                        }
                    }

                    for (int i = 0; i < clasesArgumentos.length; i++) {
                        if (argumentos[i] == null) {
                            parametrosCorrectos = false;
                            break;
                        }

                        if ((argumentos[i] instanceof Double) ^
                                 (clasesArgumentos[i].isAssignableFrom(
                                double.class))) {
                            parametrosCorrectos = false;
                            break;
                        }

                        if ((argumentos[i] instanceof String) ^
                                 (clasesArgumentos[i].isAssignableFrom(
                                String.class))) {
                            parametrosCorrectos = false;
                            break;
                        }
                    }

                    computable = parametrosCorrectos;
                }
            }

            argumentosActuales = argumentos;
        }
    }

    /**
     *
     * @return
     */
    public abstract Object computar();
    
    /**
     *
     * @return
     */
    public abstract String obtenerDescripcion();

    /**
     *
     * @return
     */
    public abstract String obtenerDescripcionLarga();
    
    /**
     *
     * @param nombre
     * @param argumentos
     * <p/>
     * @return
     */
    public static Object computar(String nombre, Object[] argumentos) {
        Funcion funcion = obtenerFuncion(nombre);
        if (funcion != null) {
            funcion.actualizarArgumentos(argumentos);
            return funcion.computar();
        } else {
            return "";
        }
    }

    /**
     *
     * @param nombre
     * @return
     */
    public static String obtenerDescripcion(String nombre) {
        Funcion funcion = obtenerFuncion(nombre);
        
        if(funcion == null) {
            return "";
        } else {
            return funcion.obtenerDescripcion();
        }
    }
    
    /**
     *
     * @param nombre
     * @return
     */
    public static String obtenerDescripcionLarga(String nombre) {
        Funcion funcion = obtenerFuncion(nombre);
        
        if(funcion == null) {
            return "";
        } else {
            return funcion.obtenerDescripcionLarga();
        }
    }
    
    /**
     *
     * @param nombre
     * <p/>
     * @return
     */
    public static int obtenerNumeroArgumentos(String nombre) {
        Funcion funcion = obtenerFuncion(nombre);
        return funcion.numeroArgumentos;
    }

    /**
     *
     * @param nombreUsuario
     */
    public static void cambiarNombreUsuario(String nombreUsuario) {
        Funcion funcion = obtenerFuncion(nombreUsuario);
        funcion.setNombreUsuario(nombreUsuario);
    }
    
    /**
     *
     * @return
     */
    public boolean tieneArgumentosString() {
        return tieneArgumentosString;
    }

    /**
     *
     * @param nombre
     * <p/>
     * @return
     */
    public static boolean tieneArgumentosString(String nombre) {
        return obtenerFuncion(nombre).tieneArgumentosString;
    }

    /**
     * Método principal de Funcion.
     * <p/>
     * @param args Los argumentos del programa.
     * <p/>
     * @throws CaracterIlegal
     */
    public static void main(String[] args) throws CaracterIlegal {
//        Funcion instancia = new FuncionNativa("", 0);
//        System.out.println(FuncionesNativas.evaluarExpresionPunto("x sen x cos +", 0));
//        System.out.println(FuncionesNativas.derivar("x sen x cos +", 0));
//        System.out.println(Procesador.procesar("npr(5, 2)"));
//        System.out.println(Procesador.procesar("npr(16, 3)"));
        //System.out.println(Procesador.procesar("mod(4.75, 2.5)"));
//        System.out.println(Procesador.procesar("ncr(16, 3)"));
//        long time = System.nanoTime();
//        double r;  
//        r = FuncionesNativas.fact(10);
//        time = System.nanoTime() - time;
//        System.out.println("Fact[10] = " + r + ": " + (time) + " nanoseconds");
//        System.out.println(FuncionesNativas.menorPrimoMayorQue(3571));
        System.out.println(FuncionesNativas.LMC(1.83));        
    }

    @Override
    public int compareTo(Funcion otraFuncion) {
        if (otraFuncion == null || otraFuncion.nombre == null) {
            return 0;
        }

        return nombre.compareToIgnoreCase(otraFuncion.nombre);
    }

    @Override
    public boolean equals(Object otraFuncion) {
        if (otraFuncion == null || !(otraFuncion instanceof Funcion)) {
            return false;
        } else {
            return compareTo((Funcion) otraFuncion) == 0;
        }
    }

    /**
     *
     * @return
     */
    public boolean computable() {
        return computable;
    }

    /**
     *
     * @return
     */
    public String nombre() {
        return nombre;
    }

    /**
     *
     * @return
     */
    public boolean esNativa() {
        return nativa;
    }

    /**
     *
     * @return
     */
    public int numeroParametros() {
        return numeroArgumentos;
    }

    /**
     *
     * @return
     */
    public Object[] argumentosActuales() {
        return argumentosActuales;
    }

    public String[] obtenerCategorizacion() {
        String[] categoria = new String[categorizacion.length];
        arraycopy(categorizacion, 0, categoria, 0, 
                categorizacion.length);
        return categoria;
    }
    
    public static String[] obtenerCategorizacion(String nombre) {
        Funcion f = obtenerFuncion(nombre);
        
        if(f == null) {
            return new String[0];
        } else {
            return f.obtenerCategorizacion();
        }
    }
    
    /**
     *
     * @param funcion
     * <p/>
     * @return
     */
    public static boolean existeFuncion(Funcion funcion) {
        return funciones.contains(funcion);
    }

    /**
     *
     * @param nombre
     * <p/>
     * @return
     */
    public static boolean existeFuncion(String nombre) {
        return obtenerFuncion(nombre) != null;
    }
    
    /**
     *
     * @param nombre
     * <p/>
     * @return
     */
    protected static FuncionNativa obtenerFuncionNativa(String nombre) {
        FuncionNativa fn = null;

        for (Funcion funcion : funciones) {
            if (funcion instanceof FuncionNativa) {
                if (funcion.nombre.equalsIgnoreCase(nombre)) {
                    fn = (FuncionNativa) funcion;
                    break;
                }
            }
        }

        return fn;
    }

    private static Funcion obtenerFuncion(String nombre) {
        Funcion fn = null;

        for (Funcion funcion : funciones) {
            int comparacion = funcion.nombre.compareToIgnoreCase(nombre);

            if (comparacion == 0) {
                fn = funcion;
                break;
            }

            if (comparacion > 0) {
                break;
            }
        }

        return fn;
    }

    @Override
    public String toString() {
        if (aRPN) {
            StringBuilder sb = new StringBuilder(nombre.length() +
                     numeroArgumentos * 10);

            for (int i = argumentosActuales.length - 1; i >= 0; i--) {
                sb = sb.append(argumentosActuales[i]).append(" ");
            }

            if(nombreUsuario.length() == 0 || !nombreUsuario.equalsIgnoreCase(
                    nombre)) {
                sb = sb.append(nombre);
            } else {
                sb = sb.append(nombreUsuario);
            }

            return sb.toString();
        } else {
            StringBuilder sb = new StringBuilder(nombre.length() +
                     numeroArgumentos * 10);
            
            if(nombreUsuario.length() == 0 || !nombreUsuario.equalsIgnoreCase(
                    nombre)) {
                sb = sb.append(nombre);
            } else {
                sb = sb.append(nombreUsuario);
            }
            
            sb = sb.append("(");

            if (numeroArgumentos > 0) {
                sb = sb.append(argumentosActuales[0]);
            }

            for (int i = 1; i < argumentosActuales.length; i++) {
                sb = sb.append(", ").append(argumentosActuales[i]);
            }

            return sb.append(")").toString();
        }
    }

    /**
     *
     * @return
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     *
     * @param nombreUsuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
final class FuncionesNativas {
    final static MathContext CONTEXTO = Funcion.CONTEXTO;

    public static double fact(double numero) {
        double respuesta = 1;

        for (int i = 2; i <= numero; respuesta *= i++);

        return respuesta;
    }

    public static double csc(double angulo) {
        return 1.0 / Math.sin(angulo);
    }

    public static double sec(double angulo) {
        return 1.0 / Math.cos(angulo);
    }

    public static double cot(double angulo) {
        return 1.0 / Math.tan(angulo);
    }

    public static double acsc(double angulo) {
        return Math.asin(1.0 / angulo);
    }

    public static double asec(double angulo) {
        return Math.acos(1.0 / angulo);
    }

    public static double acot(double angulo) {
        return Math.atan(1.0 / angulo);
    }

    public static double logb(double base, double exponente) {
        return Math.log(exponente) / Math.log(base);
    }
    
    private static Random generadorNumerosAleatorios;

    public static double gaussian() {
        if (generadorNumerosAleatorios == null) {
            generadorNumerosAleatorios = new Random();
        }

        return generadorNumerosAleatorios.nextGaussian();
    }

    public static double derivar(String expresion, double punto) {
        String[] expresionRPN;

        try {
            expresionRPN = Procesador.convertirRPN(expresion.trim());

            double f1 = evaluarExpresionPunto(expresionRPN, punto + 0.005);
            double f_1 = evaluarExpresionPunto(expresionRPN, punto - 0.005);
            double f2 = evaluarExpresionPunto(expresionRPN, punto + 0.01);
            double f_2 = evaluarExpresionPunto(expresionRPN, punto - 0.01);
            double resultado = (f_2 + 8 * (f1 - f_1) - f2) / 0.06;

            return resultado;
        } catch (CaracterIlegal ex) {
            Logger.getLogger(FuncionesNativas.class.getName()).log(Level.SEVERE,
                    null, ex);
            return NaN;
        }
    }

    public static double derivar2(String expresion, double punto) {
        String[] expresionRPN;

        try {
            expresionRPN = Procesador.convertirRPN(expresion.trim());

            double f0 = evaluarExpresionPunto(expresionRPN, punto);
            double f1 = evaluarExpresionPunto(expresionRPN, punto + 0.005);
            double f_1 = evaluarExpresionPunto(expresionRPN, punto - 0.005);
            double f2 = evaluarExpresionPunto(expresionRPN, punto + 0.01);
            double f_2 = evaluarExpresionPunto(expresionRPN, punto - 0.01);
            double resultado = (-f_2 + 16 * (f_1 + f1) - 30 * f0 - f2) / 0.0003;

            return resultado;
        } catch (CaracterIlegal ex) {
            Logger.getLogger(FuncionesNativas.class.getName()).log(Level.SEVERE,
                    null, ex);
            return NaN;
        }
    }
    
    public static double derivar3(String expresion, double punto) {
        String[] expresionRPN;

        try {
            expresionRPN = Procesador.convertirRPN(expresion.trim());

            double f1 = evaluarExpresionPunto(expresionRPN, punto + 0.005);
            double f_1 = evaluarExpresionPunto(expresionRPN, punto - 0.005);
            double f2 = evaluarExpresionPunto(expresionRPN, punto + 0.01);
            double f_2 = evaluarExpresionPunto(expresionRPN, punto - 0.01);
            double resultado = (-f_2 + 2 * (f_1 - f1) + f2) / 0.00000025;

            return resultado;
        } catch (CaracterIlegal ex) {
            Logger.getLogger(FuncionesNativas.class.getName()).log(Level.SEVERE,
                    null, ex);
            return NaN;
        }
    }
    
    public static double derivar4(String expresion, double punto) {
        String[] expresionRPN;

        try {
            expresionRPN = Procesador.convertirRPN(expresion.trim());

            double f0 = evaluarExpresionPunto(expresionRPN, punto);
            double f1 = evaluarExpresionPunto(expresionRPN, punto + 0.005);
            double f_1 = evaluarExpresionPunto(expresionRPN, punto - 0.005);
            double f2 = evaluarExpresionPunto(expresionRPN, punto + 0.01);
            double f_2 = evaluarExpresionPunto(expresionRPN, punto - 0.01);
            double resultado = (f_2 - 4 * (f1 + f_1) + 6 * f0  + f2) / (0.000000000625);

            return resultado;
        } catch (CaracterIlegal ex) {
            Logger.getLogger(FuncionesNativas.class.getName()).log(Level.SEVERE,
                    null, ex);
            return NaN;
        }
    }
        
    public static double mod(double dividendo, double divisor) {
        return dividendo % divisor;
    }
    
    public static double npr(double tamanio, double grupos) {
        int t = (int) tamanio;
        int g = (int) grupos;
        int resultado = 1;
        
        if(tamanio - t != 0 || grupos - g != 0) {
            return NaN;
        }
        
        if(t < g || g <= 0 || t <= 0) {
            return 0;
        }
        
        for (int i = t; i > t - g; resultado *= i--);
        
        return resultado;
    }
    
    public static double ncr(double tamanio, double grupos) {
        int t = (int) tamanio;
        int g = (int) grupos;
        long resultado = 1;
        
        if(tamanio - t != 0 || grupos - g != 0) {
            return NaN;
        }
        
        if(t < g || g <= 0 || t <= 0) {
            return 0;
        }
        
        if(t > (g << 1)) {//t - g > g
            for (int i = t; i > t - g; resultado *= i--);
            
            resultado /= fact(g);
        } else {
            for (int i = t; i > g; resultado *= i--);
            
            resultado /= fact(t - g);
        }
        
        return resultado;
    }
    
    public static double gcd(double a, double b) {
        long enteroA = (long) a;
        long enteroB = (long) b;
        
        if(a - enteroA != 0 || b - enteroB != 0) {
            return NaN;
        }
        
        if(enteroA == 0) {
            return enteroB;
        }
        
        if(enteroB == 0) {
            return enteroA;
        }
        
        if(enteroA == enteroB) {
            return enteroA;
        }
        
        if(enteroA < 0 || enteroB < 0) {
            return NaN;
        }
        
        return new BigInteger("" + enteroA).gcd(new BigInteger("" + enteroB))
                .doubleValue();
    }
    
    public static double mcm(double a, double b) {
        return (a / gcd(a, b)) * b;
    }
    
    public static double maximo(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        
        datos = eliminarPareterizacionDatos(datos);
        
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        
        int total = numerosCadena.length;
        
        if(total <= 0) {
            return 0;
        }
        
        BigDecimal acumulador = new BigDecimal(numerosCadena[0]);
        
        for (String numero : numerosCadena) {
            if(Procesador.esNumero(numero)) {
                BigDecimal num = new BigDecimal(numero);
                
                if(num.compareTo(acumulador) > 0) {
                    acumulador = num;
                }
            } else {
                return NaN;
            }
        }
        
        return acumulador.doubleValue();
    }
    
    public static double minimo(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        datos = eliminarPareterizacionDatos(datos);
        
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        
        int total = numerosCadena.length;
        
        if(total <= 0) {
            return 0;
        }
        
        BigDecimal acumulador = new BigDecimal(numerosCadena[0]);
        
        for (String numero : numerosCadena) {
            if(Procesador.esNumero(numero)) {
                BigDecimal num = new BigDecimal(numero);
                
                if(num.compareTo(acumulador) < 0) {
                    acumulador = num;
                }
            } else {
                return NaN;
            }
        }
        
        return acumulador.doubleValue();
    }
    
    public static double prom(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        
        datos = eliminarPareterizacionDatos(datos);
        
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        
        int total = numerosCadena.length;
        
        if(total <= 0) {
            return 0;
        }
        
        BigDecimal acumulador = ZERO;
        
        
        for (String numero : numerosCadena) {
            if(Procesador.esNumero(numero)) {
                acumulador = acumulador.add(new BigDecimal(numero), CONTEXTO);
            } else {
                return NaN;
            }
        }
        
        return acumulador.divide(new BigDecimal(total), 
                CONTEXTO).doubleValue();
    }
    
    public static double contar(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        datos = eliminarPareterizacionDatos(datos);
        
        return datos.split("[ ]*,[ ]*").length;
    }
    
    public static double contarSi(String datos, double dato) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        datos = eliminarPareterizacionDatos(datos);
        
        if((datos.startsWith("(") || datos.startsWith("[")) 
                && (datos.endsWith(")") || datos.endsWith("]"))) {
            datos = datos.substring(1, datos.length() - 1);
        }
        
        String[] valores = datos.split("[ ]*,[ ]*");
        int cuenta = 0;
        
        for (String valor : valores) {
            if(Procesador.esNumero(valor) && Double.parseDouble(valor) 
                    == dato) {
                cuenta++;
            }
        }
        
        return cuenta;
    }
    
    public static double moda(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        datos = eliminarPareterizacionDatos(datos);
        
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        
        int total = numerosCadena.length;
        
        if(total <= 0) {
            return 0;
        }
        
        TreeMap<Double, Integer> conteoValores = new TreeMap<>();
        
        for (String numero : numerosCadena) {
            if(Procesador.esNumero(numero)) {
                Double num = Double.valueOf(numero);
                
                if(conteoValores.containsKey(num)) {
                    conteoValores.put(num, conteoValores.get(
                            num).intValue() + 1);
                } else {
                    conteoValores.put(num, 1);
                }
            } else {
                return NaN;
            }
        }
        
        Queue<Double> modas = new LinkedList<>();
        int modaMaxima = 0;
        
        for (Double valor : conteoValores.keySet()) {
            int valorModa = conteoValores.get(valor);
            
            if(valorModa > modaMaxima) {
                modas.clear();
                modaMaxima = valorModa;
            }
            
            if(valorModa >= modaMaxima) {
                modas.offer(valor);
            }
        }
        
        if(modas.size() == 1) {
            return modas.poll().doubleValue();
        } else {
            BigDecimal acumulador = ZERO;
            int totalModas = modas.size();
            
            
            for (Double valor : modas) {
                acumulador = acumulador.add(new BigDecimal(valor.toString()), 
                        CONTEXTO);
            }
            
            return acumulador.divide(new BigDecimal(totalModas), CONTEXTO)
                    .doubleValue();
        }
    }
    
    public static double suma(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        
        datos = eliminarPareterizacionDatos(datos);
        
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        
        int total = numerosCadena.length;
        
        if(total <= 0) {
            return 0;
        }
        
        BigDecimal acumulador = ZERO;
        
        
        for (String numero : numerosCadena) {
            if(Procesador.esNumero(numero)) {
                acumulador = acumulador.add(new BigDecimal(numero), CONTEXTO);
            } else {
                return NaN;
            }
        }
        
        return acumulador.doubleValue();
    }
    
    public static double sumaProducto(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        datos = eliminarPareterizacionDatos(datos);
        
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        
        int total = numerosCadena.length;
        
        if(total <= 0) {
            return 0;
        }
        
        BigDecimal acumulador = ZERO;
        
        
        for (String numero : numerosCadena) {
            if(Procesador.esNumero(numero)) {
                BigDecimal actual = new BigDecimal(numero);
                acumulador = acumulador.add(actual.multiply(actual, CONTEXTO), 
                        CONTEXTO);
            } else {
                return NaN;
            }
        }
        
        return acumulador.doubleValue();
    }
    
    public static double mediaGeom(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        datos = eliminarPareterizacionDatos(datos);
        
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        
        int total = numerosCadena.length;
        
        if(total <= 0) {
            return 0;
        }
        
        BigDecimal acumulador = ONE;
        
        
        for (String numero : numerosCadena) {
            if(Procesador.esNumero(numero)) {
                acumulador = acumulador.multiply(new BigDecimal(numero), 
                        CONTEXTO);
            } else {
                return NaN;
            }
        }
        
        int comparador = acumulador.compareTo(ZERO);
        
        if(comparador < 0) {
            return NaN;
        } else if (comparador == 0) {
            return 0;
        } else {
            return Math.pow(acumulador.doubleValue(), 1 / ((double) total));
        }
    }
    
    public static double mediaArmon(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        datos = eliminarPareterizacionDatos(datos);
        
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        
        int total = numerosCadena.length;
        
        if(total <= 0) {
            return 0;
        }
        
        BigDecimal acumulador = ZERO;
        
        
        for (String numero : numerosCadena) {
            if(Procesador.esNumero(numero)) {
                BigDecimal num = new BigDecimal(numero);
                if(num.compareTo(ZERO) == 0) {
                    return NaN;
                } else {
                    acumulador = acumulador.add(ONE.divide(num,
                            CONTEXTO), CONTEXTO);
                }
            } else {
                return NaN;
            }
        }
        
        return new BigDecimal(total).divide(acumulador, 
                CONTEXTO).doubleValue();
    }
    
    public static double mediana(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        
        datos = eliminarPareterizacionDatos(datos);
        
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        int total = numerosCadena.length;
        
        if(total <= 0) {
            return 0;
        }
        
        Queue<Double> colaNumeros = new LinkedList<>();
        
        for (String numero : numerosCadena) {
            if(Procesador.esNumero(numero)) {
                colaNumeros.offer(Double.valueOf(numero));
            } else {
                return NaN;
            }
        }
        
        Double[] valores = colaNumeros.toArray(new Double[colaNumeros.size()]);
        Arrays.sort(valores);
        
        if(valores.length % 2 != 0) {
            return valores[valores.length / 2];
        } else {
            return (valores[valores.length / 2] + valores[(valores.length / 2) 
                    - 1]) / 2;
        }
    }
    
    public static double rango(String datos) {
        return maximo(datos) - minimo(datos);
    }
    
    //<To Add> -> public static double 
    
    public static double frecuencia(String datos, double dato) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        
        datos = eliminarPareterizacionDatos(datos);
        
        String[] valores = datos.split("[ ]*,[ ]*");
        int cuenta = 0;
        
        for (String valor : valores) {
            if(Procesador.esNumero(valor) && Double.parseDouble(valor) 
                    == dato) {
                cuenta++;
            }
        }
        
        return cuenta / ((double) valores.length);
    }
    
    public static double curtosisFisher(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        datos = eliminarPareterizacionDatos(datos);
        
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        int tamanio = numerosCadena.length;
        
        if(tamanio <= 0) {
            return 0;
        }
        
        BigDecimal acumulador = ZERO;
        
        Queue<BigDecimal> numerosLista = new LinkedList<>();
        
        for (String numeroCadena : numerosCadena) {
            if(Procesador.esNumero(numeroCadena)) {
                BigDecimal numero = new BigDecimal(numeroCadena);
                acumulador = acumulador.add(numero, CONTEXTO);
                numerosLista.offer(numero);
            } else {
                return NaN;
            }
        }
        
        BigDecimal[] numeros = numerosLista.toArray(new BigDecimal[tamanio]);
        BigDecimal total = new BigDecimal(tamanio);
        BigDecimal promedio = acumulador.divide(total, CONTEXTO);
        
        //suma numerador
        
        BigDecimal numerador = ZERO;
        
        for (BigDecimal numero : numeros) {
            numerador = numerador.add(numero.subtract(promedio, CONTEXTO).pow(
                    4, CONTEXTO), CONTEXTO);
        }
        
        //suma denominador
        
        BigDecimal denominador = ZERO;
        
        for (BigDecimal numero : numeros) {
            denominador = denominador.add(numero.subtract(promedio, CONTEXTO)
                    .pow(2, CONTEXTO), CONTEXTO); // (Xi - promedio) ^ 2
        }
        
        denominador = denominador.pow(2, CONTEXTO).divide(total.pow(3, CONTEXTO
                ), CONTEXTO);
        
        return numerador.divide(denominador, CONTEXTO).subtract(new BigDecimal(
                "3"), CONTEXTO).doubleValue();
    }
    
    public static double desvEstandar(String datos) {
        return Math.sqrt(varianza(datos));
    }
    
    public static double varianza(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        datos = eliminarPareterizacionDatos(datos);
        
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        int tamanio = numerosCadena.length;
        
        if(tamanio <= 0) {
            return 0;
        }
        
        BigDecimal acumulador = ZERO;
        
        Queue<BigDecimal> numerosLista = new LinkedList<>();
        
        for (String numeroCadena : numerosCadena) {
            if(Procesador.esNumero(numeroCadena)) {
                BigDecimal numero = new BigDecimal(numeroCadena);
                acumulador = acumulador.add(numero, CONTEXTO);
                numerosLista.offer(numero);
            } else {
                return NaN;
            }
        }
        
        BigDecimal[] numeros = numerosLista.toArray(new BigDecimal[tamanio]);
        BigDecimal total = new BigDecimal(tamanio);
        BigDecimal promedio = acumulador.divide(total, CONTEXTO);
        
        //numerador
        BigDecimal numerador = ZERO;
        
        for (BigDecimal numero : numeros) {
            numerador = numerador.add(numero.subtract(promedio, CONTEXTO).pow(
                    2, CONTEXTO), CONTEXTO);
        }
        
        return numerador.divide(total, CONTEXTO).doubleValue();
    }
    
    public static double cuasiVarianza(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        datos = eliminarPareterizacionDatos(datos);
        
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        int tamanio = numerosCadena.length;
        
        if(tamanio <= 0) {
            return 0;
        }
        
        if(tamanio == 1) {
            return NaN;
        }
        
        BigDecimal acumulador = ZERO;
        
        Queue<BigDecimal> numerosLista = new LinkedList<>();
        
        for (String numeroCadena : numerosCadena) {
            if(Procesador.esNumero(numeroCadena)) {
                BigDecimal numero = new BigDecimal(numeroCadena);
                acumulador = acumulador.add(numero, CONTEXTO);
                numerosLista.offer(numero);
            } else {
                return NaN;
            }
        }
        
        BigDecimal[] numeros = numerosLista.toArray(new BigDecimal[tamanio]);
        BigDecimal total = new BigDecimal(tamanio);
        BigDecimal promedio = acumulador.divide(total, CONTEXTO);
        
        //numerador
        BigDecimal numerador = ZERO;
        
        for (BigDecimal numero : numeros) {
            numerador = numerador.add(numero.subtract(promedio, CONTEXTO).pow(
                    2, CONTEXTO), CONTEXTO);
        }
        
        return numerador.divide(total.subtract(ONE, CONTEXTO), 
                CONTEXTO).doubleValue();
    }
    
    public static double cVPearson(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        
        datos = eliminarPareterizacionDatos(datos);
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        int tamanio = numerosCadena.length;
        
        if(tamanio <= 0) {
            return 0;
        }
        
        BigDecimal acumulador = ZERO;
        
        Queue<BigDecimal> numerosLista = new LinkedList<>();
        
        for (String numeroCadena : numerosCadena) {
            if(Procesador.esNumero(numeroCadena)) {
                BigDecimal numero = new BigDecimal(numeroCadena);
                acumulador = acumulador.add(numero, CONTEXTO);
                numerosLista.offer(numero);
            } else {
                return NaN;
            }
        }
        
        BigDecimal[] numeros = numerosLista.toArray(new BigDecimal[tamanio]);
        BigDecimal total = new BigDecimal(tamanio);
        BigDecimal promedio = acumulador.divide(total, CONTEXTO);
        BigDecimal porcentaje = new BigDecimal("10000");
        int signoPromedio = promedio.signum();
        
        if(signoPromedio == 0) {
            return NaN;
        }
        
        //numerador
        BigDecimal numerador = ZERO;
        
        for (BigDecimal numero : numeros) {
            numerador = numerador.add(numero.subtract(promedio, CONTEXTO).pow(
                    2, CONTEXTO), CONTEXTO);
        }        
        
        return signoPromedio * Math.sqrt(signoPromedio * numerador.divide(
                promedio, CONTEXTO).pow(2, CONTEXTO).multiply(porcentaje, 
                CONTEXTO).multiply(total, CONTEXTO).doubleValue());
    }
    
    public static double cApertura(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        datos = eliminarPareterizacionDatos(datos);
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        int tamanio = numerosCadena.length;
        
        if(tamanio <= 0) {
            return 0;
        }
        
        //Calculo
        BigDecimal min = new BigDecimal(numerosCadena[0]);
        BigDecimal max = min;
        
        
        for (String numero : numerosCadena) {
            if(Procesador.esNumero(numero)) {
                BigDecimal num = new BigDecimal(numero);
                
                if(num.compareTo(min) < 0) {
                    min = num;
                }
                
                if(num.compareTo(max) > 0) {
                    max = num;
                }
            } else {
                return NaN;
            }
        }
        
        if(min.equals(ZERO)) {
            if(max.equals(ZERO)) {
                return NaN;
            } else {
                int signo = max.signum();
                
                if(signo > 0) {
                    return POSITIVE_INFINITY;
                } else {
                    return NEGATIVE_INFINITY;
                }
            }
        }
        
        return max.divide(min, CONTEXTO).doubleValue();
    }
    
    public static double rangoRelativo(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        datos = eliminarPareterizacionDatos(datos);
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        int tamanio = numerosCadena.length;
        
        if(tamanio <= 0) {
            return 0;
        }
        
        //Calculo
        BigDecimal min = new BigDecimal(numerosCadena[0]);
        BigDecimal max = min;
        BigDecimal promedio = ZERO;
        BigDecimal total = new BigDecimal(tamanio);
        
        
        for (String numero : numerosCadena) {
            if(Procesador.esNumero(numero)) {
                BigDecimal num = new BigDecimal(numero);
                
                if(num.compareTo(min) < 0) {
                    min = num;
                }
                
                if(num.compareTo(max) > 0) {
                    max = num;
                }
                
                promedio = promedio.add(num, CONTEXTO);
            } else {
                return NaN;
            }
        }
        
        promedio = promedio.divide(total, CONTEXTO);
        BigDecimal rango = max.subtract(min, CONTEXTO);
        
        if(promedio.equals(ZERO)) {
            if(rango.equals(ZERO)) {
                return NaN;
            } else {
                int signo = rango.signum();
                
                if(signo > 0) {
                    return POSITIVE_INFINITY;
                } else {
                    return NEGATIVE_INFINITY;
                }
            }
        }
        
        return rango.divide(promedio, CONTEXTO).doubleValue();
    }
    
    public static double percentil(String datos, double percentil) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        
        datos = eliminarPareterizacionDatos(datos);
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        int tamanio = numerosCadena.length;
        
        if(tamanio <= 0) {
            return 0;
        }
        
        BigDecimal p;
        
        try {
            p = new BigDecimal(percentil);
        } catch (NumberFormatException | NullPointerException e) {
            return NaN;
        }
        
        Queue<BigDecimal> numerosLista = new LinkedList<>();
        
        for (String numeroCadena : numerosCadena) {
            if(Procesador.esNumero(numeroCadena)) {
                BigDecimal numero = new BigDecimal(numeroCadena);
                numerosLista.offer(numero);
            } else {
                return NaN;
            }
        }
        
        BigDecimal[] numeros = numerosLista.toArray(new BigDecimal[tamanio]);
        BigDecimal salida = percentil(numeros, p);
        
        if(salida == null) {
            return NaN;
        } else {
            return salida.doubleValue();
        }
    }
    
    public static double cuartil(String datos, double cuartil) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        
        datos = eliminarPareterizacionDatos(datos);
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        int tamanio = numerosCadena.length;
        
        if(tamanio <= 0) {
            return 0;
        }
        
        BigDecimal p;
        
        try {
            p = new BigDecimal(cuartil * 25);
        } catch (NumberFormatException | NullPointerException e) {
            return NaN;
        }
        
        Queue<BigDecimal> numerosLista = new LinkedList<>();
        
        for (String numeroCadena : numerosCadena) {
            if(Procesador.esNumero(numeroCadena)) {
                BigDecimal numero = new BigDecimal(numeroCadena);
                numerosLista.offer(numero);
            } else {
                return NaN;
            }
        }
        
        BigDecimal[] numeros = numerosLista.toArray(new BigDecimal[tamanio]);
        BigDecimal salida = percentil(numeros, p);
        
        if(salida == null) {
            return NaN;
        } else {
            return salida.doubleValue();
        }
    }
    
    public static double decil(String datos, double decil) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        
        datos = eliminarPareterizacionDatos(datos);
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        int tamanio = numerosCadena.length;
        
        if(tamanio <= 0) {
            return 0;
        }
        
        BigDecimal p;
        
        try {
            p = new BigDecimal(decil * 10);
        } catch (NumberFormatException | NullPointerException e) {
            return NaN;
        }
        
        Queue<BigDecimal> numerosLista = new LinkedList<>();
        
        for (String numeroCadena : numerosCadena) {
            if(Procesador.esNumero(numeroCadena)) {
                BigDecimal numero = new BigDecimal(numeroCadena);
                numerosLista.offer(numero);
            } else {
                return NaN;
            }
        }
        
        BigDecimal[] numeros = numerosLista.toArray(new BigDecimal[tamanio]);
        BigDecimal salida = percentil(numeros, p);
        
        if(salida == null) {
            return NaN;
        } else {
            return salida.doubleValue();
        }
    }
    
    public static double rIQR(String datos) { //Recorrido Intercuartilico Relativo
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        
        datos = eliminarPareterizacionDatos(datos);
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        int tamanio = numerosCadena.length;
        
        if(tamanio <= 0) {
            return 0;
        }
        
        Queue<BigDecimal> numerosLista = new LinkedList<>();
        
        for (String numeroCadena : numerosCadena) {
            if(Procesador.esNumero(numeroCadena)) {
                BigDecimal numero = new BigDecimal(numeroCadena);
                numerosLista.offer(numero);
            } else {
                return NaN;
            }
        }
        
        
        BigDecimal[] numeros = numerosLista.toArray(new BigDecimal[tamanio]);
        
        BigDecimal cuartilUno = percentil(numeros, new BigDecimal("25"));
        BigDecimal cuartilDos = percentil(numeros, new BigDecimal("50"));
        BigDecimal cuartilTres = percentil(numeros, new BigDecimal("75"));
        
        BigDecimal numerador = cuartilTres.subtract(cuartilUno, CONTEXTO);
        BigDecimal denominador = cuartilDos;
        
        if(denominador.equals(ZERO)) {
            int signo = numerador.signum();
            
            if(signo == 0) {
                return NaN;
            } else if(signo > 0) {
                return POSITIVE_INFINITY;
            } else {
                return NEGATIVE_INFINITY;
            }
        }
        
        return numerador.divide(denominador, CONTEXTO).doubleValue();
    }
    
    public static double rSIR(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        
        datos = eliminarPareterizacionDatos(datos);
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        int tamanio = numerosCadena.length;
        
        if(tamanio <= 0) {
            return 0;
        }
        
        Queue<BigDecimal> numerosLista = new LinkedList<>();
        
        for (String numeroCadena : numerosCadena) {
            if(Procesador.esNumero(numeroCadena)) {
                BigDecimal numero = new BigDecimal(numeroCadena);
                numerosLista.offer(numero);
            } else {
                return NaN;
            }
        }
        
        
        BigDecimal[] numeros = numerosLista.toArray(new BigDecimal[tamanio]);
        
        BigDecimal cuartilUno = percentil(numeros, new BigDecimal("25"));
        BigDecimal cuartilTres = percentil(numeros, new BigDecimal("75"));
        
        BigDecimal numerador = cuartilTres.subtract(cuartilUno, CONTEXTO);
        BigDecimal denominador = cuartilTres.add(cuartilUno, CONTEXTO);
        
        if(denominador.equals(ZERO)) {
            int signo = numerador.signum();
            
            if(signo == 0) {
                return NaN;
            } else if(signo > 0) {
                return POSITIVE_INFINITY;
            } else {
                return NEGATIVE_INFINITY;
            }
        }
        
        return numerador.divide(denominador, CONTEXTO).doubleValue();
    }
    
    public static double cABowley(String datos) {
        if(datos == null || datos.length() == 0) {
            return 0;
        }
        
        datos = eliminarPareterizacionDatos(datos);
        String[] numerosCadena = datos.split("[ ]*,[ ]*");
        int tamanio = numerosCadena.length;
        
        if(tamanio <= 0) {
            return 0;
        }
        
        Queue<BigDecimal> numerosLista = new LinkedList<>();
        
        for (String numeroCadena : numerosCadena) {
            if(Procesador.esNumero(numeroCadena)) {
                BigDecimal numero = new BigDecimal(numeroCadena);
                numerosLista.offer(numero);
            } else {
                return NaN;
            }
        }
        
        
        BigDecimal[] numeros = numerosLista.toArray(new BigDecimal[tamanio]);
        
        BigDecimal cuartilUno = percentil(numeros, new BigDecimal("25"));
        BigDecimal cuartilDos = percentil(numeros, new BigDecimal("50"));
        BigDecimal cuartilTres = percentil(numeros, new BigDecimal("75"));
        
        BigDecimal numerador = cuartilTres.add(cuartilUno, CONTEXTO).subtract(
                cuartilDos.multiply(new BigDecimal("2"), CONTEXTO), CONTEXTO);
        BigDecimal denominador = cuartilTres.subtract(cuartilUno, CONTEXTO);
        
        if(denominador.equals(ZERO)) {
            int signo = numerador.signum();
            
            if(signo == 0) {
                return NaN;
            } else if(signo > 0) {
                return POSITIVE_INFINITY;
            } else {
                return NEGATIVE_INFINITY;
            }
        }
        
        return numerador.divide(denominador, CONTEXTO).doubleValue();
    }
    
    public static double cAPearson(String datos) {
        double numerador = prom(datos) - moda(datos);
        double denominador = desvEstandar(datos);
        
        if(denominador == 0) {
            if(numerador == 0) {
                return NaN;
            } else if(numerador < 0) {
                return NEGATIVE_INFINITY;
            } else {
                return POSITIVE_INFINITY;
            }
        }
        
        return numerador / denominador;
    }
    
    public static double volPrismaRegular(double areaBase, double altura) {
        return new BigDecimal("" + areaBase).multiply(new BigDecimal("" 
                + altura)).doubleValue();
    }
    
    public static double volOrtoedro(double largo, double alto, 
            double profundidad) {
        return new BigDecimal("" + largo).multiply(new BigDecimal("" 
                + alto)).multiply(new BigDecimal("" + profundidad))
                .doubleValue();
    }
    
    public static double volCubo(double lado) {
        return new BigDecimal("" + lado).pow(3).doubleValue();
    }
    
    public static double volCilindro(double radio, double altura) {
        BigDecimal pi = Funcion.PI;
        BigDecimal r = new BigDecimal(radio);
        BigDecimal h = new BigDecimal(altura);
        return pi.multiply(r.pow(2)).multiply(h).doubleValue();
    }
    
    public static double volEsfera(double radio) {
        
        BigDecimal pi = Funcion.PI;
        BigDecimal r = new BigDecimal(radio);
        BigDecimal three = new BigDecimal("3");
        BigDecimal four = new BigDecimal("4");
        return four.multiply(pi, CONTEXTO).multiply(r.pow(3, CONTEXTO), CONTEXTO
                ).divide(three, CONTEXTO).doubleValue();
    }
    
    public static double volElipsoide(double a, double b, double c) {
        
        BigDecimal pi = Funcion.PI;
        BigDecimal aGrande = new BigDecimal(a);
        BigDecimal bGrande = new BigDecimal(b);
        BigDecimal cGrande = new BigDecimal(c);
        BigDecimal three = new BigDecimal("3");
        BigDecimal four = new BigDecimal("4");
        return four.multiply(pi, CONTEXTO).multiply(aGrande, CONTEXTO).multiply(
                bGrande, CONTEXTO).multiply(cGrande, CONTEXTO).divide(three, 
                CONTEXTO).doubleValue();
    }
    
    public static double volPiramide(double area, double altura) {
        
        BigDecimal a = new BigDecimal(area);
        BigDecimal h = new BigDecimal(altura);
        BigDecimal three = new BigDecimal("3");
        return a.multiply(h, CONTEXTO).divide(three, CONTEXTO).doubleValue();
    }
    
    public static double volCono(double radio, double altura) {
        
        BigDecimal pi = Funcion.PI;
        BigDecimal r = new BigDecimal(radio);
        BigDecimal h = new BigDecimal(altura);
        BigDecimal three = new BigDecimal("3");
        return pi.multiply(r.pow(2, CONTEXTO), CONTEXTO).multiply(h, CONTEXTO)
                .divide(three, CONTEXTO).doubleValue();
    }
    
    public static double areaTriangulo(double base, double altura) {
        BigDecimal b = new BigDecimal("" + base);
        BigDecimal h = new BigDecimal("" + altura);
        return b.multiply(h).divide(new BigDecimal("2")).doubleValue();
    }
    
    public static double areaTrianguloHeron(double a, double b, double c) {
        BigDecimal aGrande = new BigDecimal("" + Math.abs(a));
        BigDecimal bGrande = new BigDecimal("" + Math.abs(b));
        BigDecimal cGrande = new BigDecimal("" + Math.abs(c));
        BigDecimal s = aGrande.add(bGrande).add(cGrande).divide(new BigDecimal(
                "2"));
        return sqrt(s.multiply(s.subtract(aGrande)).multiply(s.subtract(
                bGrande)).multiply(s.subtract(cGrande)), CONTEXTO)
                .doubleValue();
    }
    
    public static double areaCuadrilatero(double a, double b, double c, 
            double d, double ac, double bd) {
        BigDecimal A = new BigDecimal(a + "").pow(2);
        BigDecimal B = new BigDecimal(b + "").pow(2);
        BigDecimal C = new BigDecimal(c + "").pow(2);
        BigDecimal D = new BigDecimal(d + "").pow(2);
        BigDecimal AC = new BigDecimal(ac + "").pow(2);
        BigDecimal BD = new BigDecimal(bd + "").pow(2);
        BigDecimal cuatro = new BigDecimal("4");
        BigDecimal dieciseis = new BigDecimal("16");
        BigDecimal minuendo = cuatro.multiply(AC).multiply(BD);
        BigDecimal sustraendo = B.add(D).subtract(A).subtract(C).pow(2);
        return sqrt(minuendo.subtract(sustraendo).divide(dieciseis, CONTEXTO), 
                CONTEXTO).doubleValue();
    }
    
    public static double areaRombo(double ac, double bd) {
        BigDecimal AC = new BigDecimal(ac + "");
        BigDecimal BD = new BigDecimal(bd + "");
        return AC.multiply(BD).divide(new BigDecimal("2")).doubleValue();
    }
    
    public static double areaRectangulo(double a, double b) {
        BigDecimal A = new BigDecimal(a + "");
        BigDecimal B = new BigDecimal(b + "");
        return A.multiply(B).doubleValue();
    }
    
    public static double areaCuadrado(double lado) {
        return new BigDecimal("" + lado).pow(2).doubleValue();
    }
    
    public static double areaRomboide(double base, double altura) {
        BigDecimal b = new BigDecimal(base + "");
        BigDecimal h = new BigDecimal(altura + "");
        return b.multiply(h).doubleValue();
    }
    
    public static double areaTrapecio(double baseMenor, double baseMayor, 
            double altura) {
        BigDecimal a = new BigDecimal("" + baseMenor);
        BigDecimal b = new BigDecimal("" + baseMayor);
        BigDecimal h = new BigDecimal("" + altura);
        return a.add(b).multiply(h).divide(new BigDecimal("2")).doubleValue();
    }
    
    public static double areaCirculo(double radio) {
        BigDecimal r = new BigDecimal("" + radio);
        return Funcion.PI.multiply(r.pow(2)).doubleValue();
    }
    
    public static double areaElipse(double a, double b) {
        BigDecimal aGrande = new BigDecimal("" + a);
        BigDecimal bGrande = new BigDecimal("" + b);
        return Funcion.PI.multiply(aGrande).multiply(bGrande).doubleValue();
    }
    
    public static double areaEsfera(double radio) {
        BigDecimal r = new BigDecimal("" + radio);
        return Funcion.PI.multiply(r.pow(2)).multiply(new BigDecimal("4"))
                .doubleValue();
    }
    
    public static double areaCono(double radio, double altura) {
        BigDecimal r = new BigDecimal("" + radio);
        BigDecimal h = new BigDecimal("" + altura);
        return Funcion.PI.multiply(r).multiply(r.add(sqrt(r.pow(2).add(h
                .pow(2)), CONTEXTO))).doubleValue();
    }
    
    public static double areaCilindro(double radio, double altura) {
        BigDecimal r = new BigDecimal("" + radio);
        BigDecimal h = new BigDecimal("" + altura);
        return Funcion.PI.multiply(r).multiply(new BigDecimal("2").multiply(h)
                .add(r)).doubleValue();
    }
    
    public static double dPuntoRecta(double pendiente, double desplazamiento, 
            double x, double y) {
        BigDecimal m = new BigDecimal("" + pendiente);
        BigDecimal b = new BigDecimal("" + desplazamiento);
        BigDecimal equis = new BigDecimal("" + x);
        BigDecimal ye = new BigDecimal("" + y);
        
        return sqrt(m.multiply(equis).subtract(ye).add(b).pow(2).divide(m.pow(2)
                .add(ONE), CONTEXTO), CONTEXTO).doubleValue();
    }
    
    public static double dPuntoPlano(double a, double b, double c, double d, 
            double x, double y, double z) {
        BigDecimal aBD = new BigDecimal("" + a);
        BigDecimal bBD = new BigDecimal("" + b);
        BigDecimal cBD = new BigDecimal("" + c);
        BigDecimal dBD = new BigDecimal("" + d);
        BigDecimal xBD = new BigDecimal("" + x);
        BigDecimal yBD = new BigDecimal("" + y);
        BigDecimal zBD = new BigDecimal("" + z);
        
        return sqrt(aBD.multiply(xBD).add(bBD.multiply(yBD)).add(cBD
                .multiply(zBD)).add(dBD).pow(2).divide(aBD.pow(2).add(bBD
                .pow(2)).add(cBD.pow(2)), CONTEXTO), CONTEXTO).doubleValue();
    }
    
    public static double dRectaRecta(double m, double b1, double b2) {
        
        
        //Valores
        BigDecimal M = new BigDecimal("" + m);
        BigDecimal B1 = new BigDecimal("" + b1);
        BigDecimal B2 = new BigDecimal("" + b2);
        
        BigDecimal numerador = B1.subtract(B2).pow(2);
        BigDecimal denominador = ONE.add(M.pow(2));
        
        if(numerador.equals(ZERO)) {
            return 0;
        } else {
            return sqrt(numerador.divide(denominador, CONTEXTO), CONTEXTO)
                    .doubleValue();
        }
    }
    
    public static double dRectaPlano(double x0, double y0, double z0, double a1,
            double a2, double a3, double a, double b, double c, double d) {
        
        
        //Vector director
        BigDecimal A1 = new BigDecimal("" + a1);
        BigDecimal A2 = new BigDecimal("" + a2);
        BigDecimal A3 = new BigDecimal("" + a3);
        
        //Plano
        BigDecimal A = new BigDecimal("" + a);
        BigDecimal B = new BigDecimal("" + b);
        BigDecimal C = new BigDecimal("" + c);
        BigDecimal D = new BigDecimal("" + d);
        
        //Primer punto
        BigDecimal x = new BigDecimal("" + x0);
        BigDecimal y = new BigDecimal("" + y0);
        BigDecimal z = new BigDecimal("" + z0);
        
        //Primer numerador
        BigDecimal primerNumerador = A.multiply(x).add(B.multiply(y)).add(C
                .multiply(z)).add(D).pow(2);
        
        //Segundo punto
        x = new BigDecimal("" + (a1 + x0));
        y = new BigDecimal("" + (a2 + y0));
        z = new BigDecimal("" + (a3 + z0));
        
        //Segundo numerador
        BigDecimal segundoNumerador = A.multiply(x).add(B.multiply(y)).add(C
                .multiply(z)).add(D).pow(2);
        
        if(primerNumerador.equals(segundoNumerador)) {
            return sqrt(primerNumerador.divide(A.pow(2).add(B.pow(2)).add(C
                    .pow(2)), CONTEXTO), CONTEXTO).doubleValue();
        } else {
            return 0;
        }
    }
    
    public static double dPlanoPlano(double a, double b, double c, double d1, 
            double d2) {
        
        
        //Planos
        BigDecimal A = new BigDecimal("" + a);
        BigDecimal B = new BigDecimal("" + b);
        BigDecimal C = new BigDecimal("" + c);
        BigDecimal D1 = new BigDecimal("" + d1);
        BigDecimal D2 = new BigDecimal("" + d2);
        
        //Calculo
        BigDecimal numerador = D2.subtract(D1).pow(2);
        BigDecimal denominador = A.pow(2).add(B.pow(2)).add(C.pow(2));
        
        if(denominador.equals(ZERO)) {
            int comparador = numerador.signum();
            
            if(comparador == 0) {
                return NaN;
            } else if(comparador < 0) {
                return NEGATIVE_INFINITY;
            } else {
                return POSITIVE_INFINITY;
            }
        } else {
            return sqrt(numerador.divide(denominador, CONTEXTO), CONTEXTO)
                    .doubleValue();
        }
    }
    
    public static double dPuntoPunto(double x1, double y1, double x2,
            double y2) {
        return Math.hypot(x2 - x1, y2 - y1);
    }
    
    public static double pCuadrado(double a) {
        return new BigDecimal("4").multiply(new BigDecimal("" + a))
                .doubleValue();
    }
    
    public static double pRectangulo(double a, double b) {
        return new BigDecimal("2").multiply(new BigDecimal("" + a). add(new 
                BigDecimal("" + b))).doubleValue();
    }
    
    public static double pRombo(double ab, double bc) {
        return new BigDecimal("2").multiply(new BigDecimal("" + ab). add(new 
                BigDecimal("" + bc))).doubleValue();
    }
    
    public static double pTriangulo(double a, double b, double c) {
        BigDecimal A = new BigDecimal("" + a);
        BigDecimal B = new BigDecimal("" + b);
        BigDecimal C = new BigDecimal("" + c);
        
        return A.add(B).add(C).doubleValue();
    }
    
    public static double pRomboide(double ab, double bc, double cd, double da) {
        BigDecimal AB = new BigDecimal("" + ab);
        BigDecimal BC = new BigDecimal("" + bc);
        BigDecimal CD = new BigDecimal("" + cd);
        BigDecimal DA = new BigDecimal("" + da);
        
        return AB.add(BC).add(CD).add(DA).doubleValue();
    }
    
    public static double pCirculo(double radio) {
        BigDecimal r = new BigDecimal("" + radio);
        
        return new BigDecimal("2").multiply(r).multiply(Funcion.PI)
                .doubleValue();
    }
    
    public static double pElipse(double a, double b) {
        BigDecimal A = new BigDecimal("" + a);
        BigDecimal B = new BigDecimal("" + b);
        BigDecimal tres = new BigDecimal("3");
        
        //Correccion del signo
        A = A.abs();
        B = B.abs();
        
        BigDecimal minuendo = tres.multiply(A.add(B));
        BigDecimal producto = tres.multiply(A).add(B).multiply(A.add(tres
                .multiply(B)));
        BigDecimal raiz = sqrt(producto, CONTEXTO);
        return Funcion.PI.multiply(minuendo.subtract(raiz)).doubleValue();
    }
    
    public static double esPrimo(double a) {
        BigDecimal A = new BigDecimal("" + a);
        BigDecimal decimal = A.remainder(ONE);
        
        if(decimal.equals(ZERO)) {
            BigInteger entero = A.toBigIntegerExact();
            
            if(entero.isProbablePrime(Integer.MAX_VALUE)) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
    
    public static double nPrimos(double a, double b) {
        BigDecimal minD = new BigDecimal("" + Math.min(a, b));
        BigDecimal maxD = new BigDecimal("" + Math.max(a, b));
        BigDecimal dos = new BigDecimal("2");
        
        //Correccion del signo
        if(maxD.signum() < 0) {
            return 0;
        } else if(minD.signum() < 0 || minD.compareTo(dos) < 0) {
            minD = dos;
        }
        
        BigInteger max = maxD.toBigInteger();
        BigInteger min = minD.toBigInteger();
        BigDecimal resto = minD.remainder(ONE);
        
        if(resto.compareTo(ZERO) != 0) {
            min = min.add(BigInteger.ONE);
        }
        
        double cuenta = 0;
        
        for(BigInteger i = min; i.compareTo(max) <= 0; i = i.add(BigInteger
                .ONE)) {
            if(i.isProbablePrime(Integer.MAX_VALUE)) {
                cuenta++;
            }
        }
        
        return cuenta;
    }
    
    public static double menorPrimoMayorQue(double a) {
        BigDecimal A = new BigDecimal("" + Math.max(1, a));
        
        BigInteger inicio = A.toBigInteger().add(BigInteger.ONE);
        BigInteger fin = BigInteger.valueOf(Long.MAX_VALUE);
        
        for(BigInteger i = inicio; i.compareTo(fin) <= 0; i = i.add(BigInteger
                .ONE)) {
            if(i.isProbablePrime(Integer.MAX_VALUE)) {
                return i.doubleValue();
            }
        }
        
        return NaN;
    }
    
    public static double IMC(double masa, double altura) {
        if(altura < 0 || masa < 0) {
            return NaN;
        }
        
        //Evitar dividir por cero
        if(altura == 0) {
            if(masa == 0) {
                return NaN;
            } else if(masa < 0) {
                return NEGATIVE_INFINITY;
            } else {
                return POSITIVE_INFINITY;
            }
        }
        
        BigDecimal m = new BigDecimal("" + masa);
        BigDecimal h = new BigDecimal("" + altura);
        
        return m.divide(h.pow(2), CONTEXTO).doubleValue();
    }
    
    public static double LMC(double altura) {
        if(altura < 0) {
            return NaN;
        }
        
        //Evitar dividir por cero
        if(altura == 0) {
            return POSITIVE_INFINITY;
        }
        
        BigDecimal m = new BigDecimal("24.99");
        BigDecimal h = new BigDecimal("" + altura);
        
        return m.multiply(h.pow(2)).doubleValue();
    }
    
    public static double convSegundosHoras(double segundos) {
        BigDecimal factor = new BigDecimal("3600");
        return new BigDecimal("" + segundos).divide(factor, CONTEXTO)
                .doubleValue();
    }
    
    public static double convSegundosMinutos(double segundos) {
        BigDecimal factor = new BigDecimal("60");
        return new BigDecimal("" + segundos).divide(factor, CONTEXTO)
                .doubleValue();
    }
    
    public static double convHorasSegundos(double horas) {
        BigDecimal factor = new BigDecimal("3600");
        return new BigDecimal("" + horas).multiply(factor).doubleValue();
    }
    
    public static double convMinutosSegundos(double minutos) {
        BigDecimal factor = new BigDecimal("60");
        return new BigDecimal("" + minutos).multiply(factor).doubleValue();
    }
    
    public static double convMilisegundosSegundos(double milisegundos) {
        BigDecimal factor = new BigDecimal("1000");
        return new BigDecimal("" + milisegundos).divide(factor, CONTEXTO)
                .doubleValue();
    }
    
    public static double convSegundosMilisegundos(double segundos) {
        BigDecimal factor = new BigDecimal("1000");
        return new BigDecimal("" + segundos).multiply(factor).doubleValue();
    }
    
    public static double masaElemento(double numeroElemento) {
        Elemento elemento = Elemento.buscarElemento((int) numeroElemento);
        
        if(elemento == null) {
            return 0;
        } else {
            return elemento.obtenerPeso().doubleValue();
        }
    }
    
    public static double masaElemento(String nombreSimbolo) {
        Elemento elemento = Elemento.buscarElemento(nombreSimbolo);
        
        if(elemento == null) {
            return 0;
        } else {
            return elemento.obtenerPeso().doubleValue();
        }
    }
    //</To Add>
    
    public static double integrar(String expresion, double minimo,
            double maximo) {
        String[] expresionRPN;

        try {
            expresionRPN = Procesador.convertirRPN(expresion.trim());

            
            BigDecimal a = new BigDecimal(minimo);
            BigDecimal b = new BigDecimal(maximo);
            final int M = 1000;

            BigDecimal h = b.subtract(a, CONTEXTO).divide(
                    new BigDecimal(M << 1), CONTEXTO);
            BigDecimal resultado;// = new BigDecimal(BigInteger.ZERO, CONTEXTO);
            BigDecimal f_a = evaluarExpresionPunto(expresionRPN, a);
            BigDecimal f_b = evaluarExpresionPunto(expresionRPN, b);

            BigDecimal sumaCentro = ZERO;

            for (int k = 1; k < M; k++) {
                sumaCentro = sumaCentro.add(evaluarExpresionPunto(expresionRPN,
                        a.add(h.multiply(new BigDecimal(k << 1), CONTEXTO),
                        CONTEXTO)), CONTEXTO);
            }

            BigDecimal sumaDerecha = ZERO;

            for (int k = 1; k <= M; k++) {
                sumaDerecha = sumaDerecha.add(evaluarExpresionPunto(
                        expresionRPN, a.add(h.multiply(new BigDecimal((k << 1) -
                         1), CONTEXTO), CONTEXTO)), CONTEXTO);
            }

            resultado = f_a.add(f_b, CONTEXTO);
            sumaCentro = sumaCentro.multiply(new BigDecimal(2), CONTEXTO);
            sumaDerecha = sumaDerecha.multiply(new BigDecimal(4), CONTEXTO);
            resultado = resultado.add(sumaCentro, CONTEXTO).add(sumaDerecha,
                    CONTEXTO);
            resultado = resultado.multiply(h, CONTEXTO).divide(
                    new BigDecimal(3), CONTEXTO);
            return resultado.doubleValue();
        } catch (CaracterIlegal ex) {
            Logger.getLogger(FuncionesNativas.class.getName()).log(Level.SEVERE,
                    null, ex);
            return NaN;
        }
    }

    public static double max(String expresion, double minimo,
            double maximo) {
        String[] expresionRPN;

        try {
            expresionRPN = Procesador.convertirRPN(expresion.trim());
            double max = evaluarExpresionPunto(expresionRPN, minimo);
            double delta = (maximo - minimo) / 1000;

            for (int i = 1; i < 1001; i++) {
                max = Math.max(max, evaluarExpresionPunto(expresionRPN,
                        i * delta + minimo));
            }

            return max;
        } catch (CaracterIlegal ex) {
            Logger.getLogger(FuncionesNativas.class.getName()).log(Level.SEVERE,
                    null, ex);
            return NaN;
        }
    }

    public static double min(String expresion, double minimo,
            double maximo) {
        String[] expresionRPN;

        try {
            expresionRPN = Procesador.convertirRPN(expresion.trim());
            double min = evaluarExpresionPunto(expresionRPN, minimo);
            double delta = (maximo - minimo) / 1000;

            for (int i = 1; i < 1001; i++) {
                min = Math.min(min, evaluarExpresionPunto(expresionRPN,
                        i * delta + minimo));
            }

            return min;
        } catch (CaracterIlegal ex) {
            Logger.getLogger(FuncionesNativas.class.getName()).log(Level.SEVERE,
                    null, ex);
            return NaN;
        }
    }

    static double evaluarExpresionPunto(String[] expresionRPN, double punto) {
        String variable = "";
        StringBuilder colaExpresion = new StringBuilder(("" + punto).length() +
                 expresionRPN.length);

        for (String item : expresionRPN) {
            colaExpresion = colaExpresion.append(" ");

            if (Procesador.esVariable(item)) {
                if (variable.isEmpty()) {
                    variable = item;
                } else if (!variable.equals(item)) {
                    return NaN;
                }

                colaExpresion = colaExpresion.append(punto);
            } else {
                colaExpresion = colaExpresion.append(item);
            }
        }

        Object evaluado;

        try {
            evaluado = Procesador.evaluarRPN(colaExpresion.toString()
                    .substring(1).split(" "));

            if (evaluado instanceof String && !(evaluado instanceof Number)) {
                return NaN;
            } else {
                return ((Number) evaluado).doubleValue();
            }
        } catch (CaracterIlegal ex) {
            Logger.getLogger(FuncionesNativas.class.getName()).log(Level.SEVERE,
                    null, ex);
            return NaN;
        }
    }

    static BigDecimal evaluarExpresionPunto(String[] expresionRPN,
            BigDecimal punto) {
        String variable = "";
        StringBuilder colaExpresion = new StringBuilder(("" + punto.toString())
                .length() + expresionRPN.length);

        for (String item : expresionRPN) {
            colaExpresion = colaExpresion.append(" ");

            if (Procesador.esVariable(item)) {
                if (variable.isEmpty()) {
                    variable = item;
                } else if (!variable.equals(item)) {
                    return ZERO;
                }

                colaExpresion = colaExpresion.append(punto);
            } else {
                colaExpresion = colaExpresion.append(item);
            }
        }

        Object evaluado;

        try {
            evaluado = Procesador.evaluarRPN(colaExpresion.toString()
                    .substring(1).split(" "));

            if (evaluado instanceof String && !(evaluado instanceof Number)) {
                return ZERO;
            } else {
                if (evaluado instanceof BigDecimal) {
                    return (BigDecimal) evaluado;
                } else {
                    return new BigDecimal(((Number) evaluado).doubleValue());
                }
            }
        } catch (CaracterIlegal ex) {
            Logger.getLogger(FuncionesNativas.class.getName()).log(Level.SEVERE,
                    null, ex);
            return ZERO;
        }
    }

    static String eliminarPareterizacionDatos(String datos) {
        if((datos.startsWith("(") || datos.startsWith("[")) 
                && (datos.endsWith(")") || datos.endsWith("]"))) {
            datos = datos.substring(1, datos.length() - 1);
        }
        
        return datos;
    }
    
    static BigDecimal percentil(BigDecimal[] datos, BigDecimal percentil) {
        if(datos == null || percentil == null) {
            return null;
        }
        
        final BigDecimal CIEN = new BigDecimal("100");
        int comparacionUno = percentil.compareTo(ONE);
        int comparacionCien = percentil.compareTo(CIEN);
        
        if(comparacionUno < 0 || comparacionCien > 0) {
            return null;
        }
        
        int tamanio = datos.length;
        
        if(tamanio == 0) {
           return null; 
        }
        
        BigDecimal total = new BigDecimal(tamanio);
        BigDecimal[] numeros = new BigDecimal[tamanio];
        arraycopy(datos, 0, numeros, 0, tamanio);
        sort(numeros);
        
        
        //calculo
        BigDecimal n = percentil.multiply(total.add(ONE, CONTEXTO), 
                CONTEXTO).divide(CIEN, CONTEXTO);
        
        if(n.equals(ONE)) {
            return numeros[0];
        } else if(n.equals(total)) {
            return numeros[tamanio - 1];
        } else {
            BigDecimal d = n.remainder(ONE, CONTEXTO);
            BigDecimal k = n.subtract(d, CONTEXTO);
            int posicion = k.subtract(ONE, CONTEXTO).intValue();
            return numeros[posicion].add(d.multiply(numeros[posicion + 1]
                    .subtract(numeros[posicion], CONTEXTO), CONTEXTO), 
                    CONTEXTO);
        }
    }

    /**
     * Compute the square root of x to a given scale, x >= 0.
     * Use Newton's algorithm.
     * @param x the value of x
     * @param scale the desired scale of the result
     * @return the result value
     * @see http://www.java-forums.org/advanced-java/44345-square-rooting-bigdecimal.html
     */
    static BigDecimal sqrt(BigDecimal x, int scale) {
        // Check that x >= 0.
        if (x.signum() < 0) {
            throw new IllegalArgumentException("x < 0");
        }
 
        // n = x*(10^(2*scale))
        BigInteger n = x.movePointRight(scale << 1).toBigInteger();
 
        // The first approximation is the upper half of n.
        int bits = (n.bitLength() + 1) >> 1;
        BigInteger ix = n.shiftRight(bits);
        BigInteger ixPrev;
 
        // Loop until the approximations converge
        // (two successive approximations are equal after rounding).
        do {
            ixPrev = ix;
 
            // x = (x + n/x)/2
            ix = ix.add(n.divide(ix)).shiftRight(1);
        } while (ix.compareTo(ixPrev) != 0);
 
        return new BigDecimal(ix, scale);
    }
    
    /**
     * Compute the square root of x to a given scale, x >= 0.
     * Use Newton's algorithm.
     * @param x the value of x
     * @param context the desired scale and rounding mode of the result
     * @return the result value
     * @see http://www.java-forums.org/advanced-java/44345-square-rooting-bigdecimal.html
     */
    static BigDecimal sqrt(BigDecimal x, MathContext context) {
        return sqrt(x, context.getPrecision());
    }
}