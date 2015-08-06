package procesamiento;

import datos.Funcion;
import datos.FuncionOculta;
import gui.VentanaCalculo;
import static java.lang.Double.NaN;
import java.math.BigDecimal;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import java.math.MathContext;
import static java.math.RoundingMode.HALF_UP;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.UNICODE_CASE;
import static java.util.regex.Pattern.compile;
import static java.util.regex.Pattern.quote;

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
public class Procesador {

    private static Pattern patronNumero = compile("[0-9]++.[0-9]++|.[0-9]++|[0-"
            + "9]++.|[0-9]++|[0-9]++.[0-9]++[Ee]-[0-9]++|.[0-9]++[Ee]-[0-9]++|[" 
            + "0-9]++.[Ee]-[0-9]++|[0-9]++[Ee]-[0-9]++|-[0-9]++.[0-9]++|-.[0-9]"
            + "++|-[0-9]++.|-[0-9]++|-[0-9]++.[0-9]++[Ee]-[0-9]++|-.[0-9]++[Ee]"
            + "-[0-9]++|-[0-9]++.[Ee]-[0-9]++|-[0-9]++[Ee]-[0-9]++|[0-9]++.[0-9"
            + "]++[Ee]" + quote("+") + "[0-9]++|.[0-9]++[Ee]" + quote("+") + "["
            +"0-9]++|[0-9]++.[Ee]" + quote("+") + "[0-9]++|[0-9]++[Ee]" 
            + quote("+") + "[0-9]++|-[0-9]++.[0-9]++[Ee]" + quote("+") + "[0-9]"
            + "++|-.[0-9]++[Ee]" + quote("+") + "[0-9]++|-[0-9]++.[Ee]" 
            + quote("+") + "[0-9]++|-[0-9]++[Ee]" + quote("+") + "[0-9]++",
            UNICODE_CASE | CASE_INSENSITIVE);
    private static Pattern patronVariableFuncion = compile("[a-z]++|[0-9]++[a-z"
            + "]++|[a-z]++[0-9]++", UNICODE_CASE | CASE_INSENSITIVE);
    private static Pattern patronParentesisIzquierdo = compile(quote("[") + "|" 
            + quote("("));
    private static Pattern patronParentesisDerecho = compile(quote("]") + "|" 
            + quote(")"));
    private static Pattern patronArgumento = compile(",");
    private static Pattern patronOperador = compile(quote("+") + "|" 
            + quote("-") + "|" + quote("*") + "|" + quote("/") + "|" + 
            quote("\\") + "|" + quote("^") + "|@|%");

    /**
     *
     * @param cadena
     * @param listaVariables
     * @param valoresVariables
     * <
     * p/>
     * @return
     * <
     * p/>
     * @throws CaracterIlegal
     */
    public static BigDecimal evaluar(String cadena, String[] listaVariables,
            BigDecimal[] valoresVariables) throws CaracterIlegal {
        if (valoresVariables.length != listaVariables.length) {
            return null;
        } else {
            String[] rpn = convertirRPN(cadena);
            TreeMap<String, BigDecimal> listaConversion = new TreeMap<>();

            for (int i = 0; i < listaVariables.length; i++) {
                listaConversion.put(listaVariables[i], valoresVariables[i]);
            }

            for (int i = 0; i < rpn.length; i++) {
                if (esVariable(rpn[i])) {
                    rpn[i] = valoresVariables[i].toString();
                }
            }

            return (BigDecimal) evaluarRPN(rpn);
        }
    }

    /**
     *
     * @param cadena
     * <
     * p/>
     * @return
     * <
     * p/>
     * @throws CaracterIlegal
     */
    public static Object procesar(String cadena) throws CaracterIlegal {
        String[] rpn = convertirRPN(cadena);
        return evaluarRPN(rpn);
    }

    /**
     *
     * @param rpn
     * <
     * p/>
     * @return
     * <
     * p/>
     * @throws CaracterIlegal
     */
    public static Object evaluarRPN(String[] rpn) throws CaracterIlegal {
        int numeroVariables = 0;
        MathContext contexto = new MathContext(64, HALF_UP);

        for (String item : rpn) {
            if (esVariable(item)) {
                numeroVariables++;
            }
        }

        Deque<Object> pila = new ArrayDeque<>();

        for (String item : rpn) {
            if (esNumero(item)) {
                pila.push(new BigDecimal(item));
            } else if (esVariable(item)) {
                pila.push(item);
            } else if (esOperador(item)) {
                switch (item) {
                    case "^":
                        try {
                            Object exponente = pila.pop();
                            Object base = pila.pop();
                            if (base == null || exponente == null) {
                                throw new CaracterIlegal(item, 0);
                            } else if ((base instanceof BigDecimal) &&
                                    (exponente instanceof BigDecimal)) {
                                pila.push(potenciacion((BigDecimal) base,
                                        (BigDecimal) exponente, contexto));
                            } else {
                                if ((base instanceof BigDecimal) &&
                                        (((BigDecimal) base)
                                        .compareTo(ONE) == 0 ||
                                        ((BigDecimal) base)
                                        .compareTo(ZERO) == 0)) {
                                    pila.push(base);
                                } else if ((exponente instanceof BigDecimal) &&
                                        ((BigDecimal) exponente).compareTo(
                                        ZERO) == 0) {
                                    pila.push(ONE);
                                } else if ((exponente instanceof BigDecimal) &&
                                        ((BigDecimal) exponente).compareTo(
                                        ONE) == 0) {
                                    pila.push(base.toString());
                                } else {
                                    pila.push("(" + base.toString() + " ^ " +
                                            exponente.toString() + ")");
                                }
                            }
                        } catch (NullPointerException e) {
                            throw new CaracterIlegal(item, 0);
                        }

                        break;
                    case "@":
                        try {
                            Object exponente = pila.pop();
                            Object base = pila.pop();
                            if (base == null || exponente == null) {
                                throw new CaracterIlegal(item, 0);
                            } else if ((base instanceof BigDecimal) &&
                                    (exponente instanceof BigDecimal)) {
                                pila.push(potenciacion((BigDecimal) base,
                                        ((BigDecimal) exponente).negate(), 
                                        contexto));
                            } else {
                                if ((base instanceof BigDecimal) &&
                                        (((BigDecimal) base)
                                        .compareTo(ONE) == 0 ||
                                        ((BigDecimal) base)
                                        .compareTo(ZERO) == 0)) {
                                    pila.push(base);
                                } else if ((exponente instanceof BigDecimal) &&
                                        ((BigDecimal) exponente).compareTo(
                                        ZERO) == 0) {
                                    pila.push(ONE);
                                } else {
                                    pila.push("(" + base.toString() + " ^ -(" +
                                            exponente.toString() + "))");
                                }
                            }
                        } catch (NullPointerException e) {
                            throw new CaracterIlegal(item, 0);
                        }

                        break;
                    case "%": {
                        Object divisor = pila.pop();
                        Object dividendo = pila.pop();

                        if (dividendo == null || divisor == null) {
                            throw new CaracterIlegal(item, 0);
                        } else if ((dividendo instanceof BigDecimal) &&
                                (divisor instanceof BigDecimal)) {
                            if (((BigDecimal) divisor)
                                    .compareTo(ZERO) == 0) {
                                pila.push("(" + dividendo.toString() +
                                        ") % 0");
                            } else if (((BigDecimal) dividendo).compareTo(
                                    ZERO) == 0) {
                                pila.push(ZERO);
                            } else {
                                BigDecimal resultado = ((BigDecimal) dividendo)
                                        .remainder((BigDecimal) divisor, 
                                        contexto);
                                if(resultado.signum() < 0) {
                                    pila.push(((BigDecimal) divisor).add(
                                            resultado, contexto));
                                } else {
                                    pila.push(resultado);
                                }
                            }
                        } else {
                            if ((dividendo instanceof BigDecimal) &&
                                    ((BigDecimal) dividendo).compareTo(
                                    ZERO) == 0) {
                                pila.push(ZERO);
                            } else {
                                pila.push("(" + dividendo.toString() + " % " +
                                        divisor.toString() + ")");
                            }
                        }

                        break;
                    }
                    case "/": {
                        Object divisor = pila.pop();
                        Object dividendo = pila.pop();

                        if (dividendo == null || divisor == null) {
                            throw new CaracterIlegal(item, 0);
                        } else if ((dividendo instanceof BigDecimal) &&
                                (divisor instanceof BigDecimal)) {
                            if (((BigDecimal) divisor)
                                    .compareTo(ZERO) == 0) {
                                pila.push("(" + dividendo.toString() +
                                        ") / 0");
                            } else if (((BigDecimal) dividendo).compareTo(
                                    ZERO) == 0) {
                                pila.push(ZERO);
                            } else {
                                pila.push(((BigDecimal) dividendo).divide(
                                        (BigDecimal) divisor, contexto));
                            }
                        } else {
                            if ((dividendo instanceof BigDecimal) &&
                                    ((BigDecimal) dividendo).compareTo(
                                    ZERO) == 0) {
                                pila.push(ZERO);
                            } else if ((divisor instanceof BigDecimal) &&
                                    ((BigDecimal) divisor).compareTo(
                                    ONE) == 0) {
                                pila.push(dividendo.toString());
                            } else {
                                pila.push("(" + dividendo.toString() + " / " +
                                        divisor.toString() + ")");
                            }
                        }

                        break;
                    }
                    case "\\": {
                        Object dividendo = pila.pop();
                        Object divisor = pila.pop();

                        if (dividendo == null || divisor == null) {
                            throw new CaracterIlegal(item, 0);
                        } else if ((dividendo instanceof BigDecimal) &&
                                (divisor instanceof BigDecimal)) {
                            if (((BigDecimal) divisor)
                                    .compareTo(ZERO) == 0) {
                                pila.push("0 \\ (" + dividendo.toString() +
                                        ")");
                            } else if (((BigDecimal) dividendo).compareTo(
                                    ZERO) == 0) {
                                pila.push(ZERO);
                            } else {
                                pila.push(((BigDecimal) dividendo).divide(
                                        (BigDecimal) divisor, contexto));
                            }
                        } else {
                            if ((dividendo instanceof BigDecimal) &&
                                    ((BigDecimal) dividendo).compareTo(
                                    ZERO) == 0) {
                                pila.push(ZERO);
                            } else if ((divisor instanceof BigDecimal) &&
                                    ((BigDecimal) divisor).compareTo(
                                    ONE) == 0) {
                                pila.push(dividendo.toString());
                            } else {
                                pila.push("(" + dividendo.toString() + " / " +
                                        divisor.toString() + ")");
                            }
                        }

                        break;
                    }
                    case "-":
                        Object sustraendo = pila.pop();
                        Object minuendo = pila.pop();

                        if (minuendo == null || sustraendo == null) {
                            throw new CaracterIlegal(item, 0);
                        } else if ((minuendo instanceof BigDecimal) &&
                                (sustraendo instanceof BigDecimal)) {
                            pila.push(((BigDecimal) minuendo).subtract(
                                    (BigDecimal) sustraendo, contexto));
                        } else if ((minuendo instanceof BigDecimal) &&
                                ((BigDecimal) minuendo).compareTo(
                                ZERO) == 0) {
                            pila.push(" - (" + sustraendo.toString() + ")");
                        } else if ((sustraendo instanceof BigDecimal) &&
                                ((BigDecimal) sustraendo).compareTo(
                                ZERO) == 0) {
                            pila.push(minuendo.toString());
                        } else {
                            pila.push("(" + minuendo.toString() + " - " +
                                    sustraendo.toString() + ")");
                        }

                        break;
                    case "+":
                        Object primerSumando = pila.pop();
                        Object segundoSumando = pila.pop();
                        if (primerSumando == null || segundoSumando == null) {
                            throw new CaracterIlegal(item, 0);
                        } else if ((primerSumando instanceof BigDecimal) &&
                                (segundoSumando instanceof BigDecimal)) {
                            pila.push(((BigDecimal) primerSumando).add(
                                    (BigDecimal) segundoSumando, contexto));
                        } else {
                            if ((primerSumando instanceof BigDecimal) &&
                                    ((BigDecimal) primerSumando).compareTo(
                                    ZERO) == 0) {
                                pila.push(segundoSumando.toString());
                            } else if ((segundoSumando instanceof BigDecimal) &&
                                    ((BigDecimal) segundoSumando).compareTo(
                                    ZERO) == 0) {
                                pila.push(primerSumando.toString());
                            } else {
                                pila
                                        .push("(" + primerSumando.toString() +
                                        " + " +
                                        segundoSumando.toString() + ")");
                            }
                        }
                        break;
                    case "*":
                        Object multiplicador = pila.pop();
                        Object multiplicando = pila.pop();
                        if (multiplicando == null || multiplicador == null) {
                            throw new CaracterIlegal(item, 0);
                        } else if ((multiplicando instanceof BigDecimal) &&
                                (multiplicador instanceof BigDecimal)) {
                            pila.push(((BigDecimal) multiplicando).multiply(
                                    (BigDecimal) multiplicador, contexto));
                        } else {
                            if (((multiplicador instanceof BigDecimal) &&
                                    ((BigDecimal) multiplicador).compareTo(
                                    ZERO) == 0) ||
                                    ((multiplicando instanceof BigDecimal) &&
                                    ((BigDecimal) multiplicando)
                                    .compareTo(ZERO) ==
                                    0)) {
                                pila.push(ZERO);
                            } else if ((multiplicador instanceof BigDecimal) &&
                                    ((BigDecimal) multiplicador).compareTo(
                                    ONE) == 0) {
                                pila.push(multiplicando);
                            } else if ((multiplicando instanceof BigDecimal) &&
                                    ((BigDecimal) multiplicando).compareTo(
                                    ONE) == 0) {
                                pila.push(multiplicador);
                            } else {
                                pila
                                        .push("(" + multiplicando.toString() +
                                        " * " +
                                        multiplicador.toString() + ")");
                            }
                        }
                        break;
                    default:
                        throw new CaracterIlegal(item, 0);
                }
            } else if (esFuncion(item)) {
                int numeroArgumentos = Funcion.obtenerNumeroArgumentos(item);
                Object[] argumentos = new Object[numeroArgumentos];

                for (int i = numeroArgumentos - 1; i >= 0; i--) {
                    if (pila.isEmpty()) {
                        throw new CaracterIlegal(item, 0);
                    } else {
                        argumentos[i] = pila.pop();
                    }
                }

                Funcion.cambiarNombreUsuario(item);
                pila.push(Funcion.computar(item, argumentos));
            } else if (item.trim().startsWith("(") &&
                    item.trim().endsWith(")")) {
                pila.push(item);
            } else {
                throw new CaracterIlegal(item, 0);
            }
        }

        if (pila.size() > 1) {
            throw new CaracterIlegal(pila.pop().toString(), 0);
        }

        Object salida = pila.pop();

        if ((salida instanceof String)) {
            String salidaCadena = (String) salida;

            if (salidaCadena.startsWith("(") && salidaCadena.endsWith(")")) {
                salida = salidaCadena.substring(1, salidaCadena.length() - 1);
            }
        }

        return salida;
    }

    /**
     *
     * @param cadena
     * <
     * p/>
     * @return
     * <
     * p/>
     * @throws CaracterIlegal
     */
    public static String[] convertirRPN(String cadena)
            throws CaracterIlegal {
        String preproceso = cadena.replaceAll("[eE]-", "!").replaceAll(
                "[eE]" + quote("+"), "¡").replaceAll("[ ]++", "")
                .replaceAll(quote(")") + quote("("), ")*(")
                .replaceAll(quote("]") + quote("["), "]*[")
                .replaceAll(quote(")") + quote("["), ")*[")
                .replaceAll(quote("]") + quote("("), "]*(")
                
//                .replaceAll("-" + quote("+"), " - ").replaceAll(quote("+") + "-", " - ")
//                .replaceAll("-", "+-").replaceAll("[ ]++", "").replaceAll("[" + quote("+") + "]++", "+")
                
                .replaceAll(quote("^") 
                + "-", "@").replaceAll(quote("^") + quote("+"), 
                "^").replaceAll(",", " , ").replaceAll(quote("("), "( ")
                .replaceAll(quote("["), "[ ").replaceAll(quote(
                "]"), " ]").replaceAll(quote(")"), " )").replaceAll(
                quote("+"), " + ")
                
                .replaceAll(" *-[ ]++", " -")
                
                //.replaceAll("-", " -1 * ")
                .replaceAll(
                quote("*") + quote("*"), "^").replaceAll(Pattern
                .quote("*"), " * ").replaceAll("/", " / ").replaceAll("\\\\", 
                " \\\\ ").replaceAll("%", " % ").replaceAll(quote("^"), 
                " ^ ").replaceAll("!", "E-").replaceAll("¡", "E+").replaceAll(
                "[ ]++", " ").trim();

        StringBuilder colaPreproceso = new StringBuilder(
                2 * preproceso.length());
        for (String item : preproceso.split(" ")) {
            String itemComparado = item.substring(0, item.length() - 1);

            if ((item.endsWith("(") || item.endsWith("[")) &&
                    (esVariable(itemComparado) || esNumero(itemComparado))) {
                colaPreproceso = colaPreproceso.append(itemComparado)
                        .append("*").append(item.substring(item.length() - 1));
            } else {
                itemComparado = item.substring(1);
                
                if((item.startsWith(")") || item.startsWith("]"))
                        && //itemComparado.length() > 1 &&
                    (esVariable(itemComparado) || esNumero(itemComparado))) {
                    colaPreproceso = colaPreproceso.append(item.substring(0, 1))
                            .append("*").append(itemComparado);
                } else {
                    if(item.startsWith("-") && esNumero(item)) {
                        colaPreproceso = colaPreproceso.append("#").append(
                                itemComparado);
                    } else {
                        colaPreproceso = colaPreproceso.append(item);
                    }
                }
            }
        }
//        System.out.println("*** " + colaPreproceso.toString() + " ***");
//        
        String entrada = colaPreproceso.toString()
                .replaceAll(quote("[") +
                " ", "[").replaceAll(quote("(") + "[ ]++", "(")
                .replaceAll("[eE]-", "!").replaceAll("[eE]" + quote(
                "+"), "¡").replaceAll("," + quote("+"), ",0 + ")
                .replaceAll(quote("(") + quote("+"), "(0 + ")
                .replaceAll(quote("^") + 
                "-", "@").replaceAll(quote("^") + quote("+"), 
                "^").replaceAll(quote("[") + quote("+"), "[0 + "
                ).replaceAll(quote("("), " ( ").replaceAll(Pattern
                .quote(")"), " ) ").replaceAll(quote("["), " [ ")
                .replaceAll(quote("]"), " ] ").replaceAll(",", " , ")
                .replaceAll(quote("+"), " + ")
                
                .replaceAll("-", " - ")
                .replaceAll("#", " -")
                
                .replaceAll(quote("*"), " * ").replaceAll("/", " / ")
                .replaceAll("\\\\", "  \\\\  ").replaceAll(quote("^"), 
                " ^ ").replaceAll("@", " @ ").replaceAll("%", " % ").replaceAll(
                "[ ]++", " ").replaceAll("!", "E-").replaceAll("¡", "E+")
                .trim();
        
        if (entrada.startsWith("+")) {
            entrada = "0 " + entrada;
        }
        
        if (entrada.startsWith("-")) {
            if(entrada.startsWith("- ")) {
                entrada = "0 " + entrada;
            } else {
                entrada = "0 + " + entrada;
            }
        }

//        System.out.println("*** " + entrada + " ***");
        TreeMap<Character, Integer> conteoAlfabeto = new TreeMap<>();
        //shunting yard

        String[] items = entrada.split(" ");
        String funcionActual = "";
        Queue<String> colaSalida = new LinkedList<>();
        Deque<String> stack = new ArrayDeque<>();
        StringBuilder constructorArgumento = new StringBuilder(entrada
                .length());
        boolean tieneArgumentoCadenaFuncion = false;
        int parenterizacion = -1;

        for (String item : items) {
            if (tieneArgumentoCadenaFuncion) {
                if (esParentesisDerecho(item)) {
                    if (parenterizacion == 0) {
                        colaSalida.offer(constructorArgumento.append(item)
                                .toString());
                        tieneArgumentoCadenaFuncion = false;
                        constructorArgumento = new StringBuilder(entrada
                                .length());
                        parenterizacion = -1;
                    } else {
                        constructorArgumento = constructorArgumento.append(item);
                        parenterizacion--;
                        modificarConteo(conteoAlfabeto, item);
                    }
                } else if (esSeparadorArgumento(item)) {
                    if (parenterizacion == 0 && Funcion.obtenerNumeroArgumentos(
                            funcionActual) > 1) {
                        colaSalida.offer(constructorArgumento.append(")")
                                .toString());
                        tieneArgumentoCadenaFuncion = false;
                        constructorArgumento = new StringBuilder(entrada
                                .length());
                        parenterizacion = -1;
                    } else {
                        constructorArgumento = constructorArgumento.append(item);
                        modificarConteo(conteoAlfabeto, item);
                    }
                } else {
                    constructorArgumento = constructorArgumento.append(item);
                    modificarConteo(conteoAlfabeto, item);

                    if (esParentesisIzquierdo(item)) {
                        parenterizacion++;

                        if (parenterizacion == 0) {
                            stack.push(item);
                        }
                    }
                }
            }

            if (!tieneArgumentoCadenaFuncion) {
                if (esNumero(item)) {
                    colaSalida.offer(item);
                    modificarConteo(conteoAlfabeto, item);
                } else if (esFuncion(item)|| (item.startsWith("-") 
                        && esFuncion(item.substring(1)))) {
                    if (item.startsWith("-")) {
                        colaSalida.offer("-1");
                        stack.push("*");
                        String subitem = item.substring(1);
                        stack.push(subitem);
                        
                        if (Funcion.tieneArgumentosString(subitem)) {
                            tieneArgumentoCadenaFuncion = true;
                        }
                        
                        funcionActual = subitem;
                    } else {
                        stack.push(item);
                        
                        if (Funcion.tieneArgumentosString(item)) {
                            tieneArgumentoCadenaFuncion = true;
                        }
                        
                        funcionActual = item;
                    }

                    modificarConteo(conteoAlfabeto, item);
                } else if (esVariable(item) || (item.startsWith("-") 
                        && esVariable(item.substring(1)))) {
                    if(item.startsWith("-")) {
                        colaSalida.offer(item.substring(1));
                        colaSalida.offer("-1");
                        colaSalida.offer("*");
                    } else {
                        colaSalida.offer(item);
                    }
                    
                    modificarConteo(conteoAlfabeto, item);
                } else if (esSeparadorArgumento(item)) {
                    while (stack.peek() != null && !patronParentesisIzquierdo
                            .matcher(
                            stack.peek()).matches()) {
                        colaSalida.offer(stack.poll());
                    }

                    modificarConteo(conteoAlfabeto, item);

                    if (stack.peek() == null) {
                        throw procesarError(cadena, item.charAt(0),
                                conteoAlfabeto
                                .get(item.charAt(0)));
                    }
                } else if (esOperador(item)) {
                    while (stack.peek() != null && esOperador(stack.peek()) &&
                            ((esOperadorAsociativoIzquierdo(item) &&
                            tieneOperadorMenorIgualPrecedencia(item, stack
                            .peek())) ||
                            (esOperadorAsociativoDerecho(item) &&
                            tieneOperadorMenorPrecedencia(item,
                            stack.peek())))) {
                        colaSalida.offer(stack.poll());
                    }

                    stack.push(item);
                    modificarConteo(conteoAlfabeto, item);
                } else if (esParentesisIzquierdo(item)) {
                    stack.push(item);
                    modificarConteo(conteoAlfabeto, item);
                } else if (esParentesisDerecho(item)) {
                    while (stack.peek() != null && !esParentesisIzquierdo(
                            stack.peek())) {
                        colaSalida.offer(stack.poll());
                    }

                    String topeCola = stack.poll();
                    modificarConteo(conteoAlfabeto, item);

                    if (topeCola != null) {
                        if (stack.peek() != null && esFuncion(stack.peek())) {
                            colaSalida.offer(stack.poll());
                        }
                    } else {
                        throw procesarError(cadena, item.charAt(0),
                                conteoAlfabeto
                                .get(item.charAt(0)));
                    }
                } else {
                    modificarConteo(conteoAlfabeto, item);
                    throw procesarError(cadena, item.charAt(0), conteoAlfabeto
                            .get(item.charAt(0)));
                }
            }
        }

        while (stack.peek() != null) {
            String item = stack.poll();

            if (esParentesisDerecho(item) || esParentesisIzquierdo(item)) {
                throw new CaracterIlegal(cadena, cadena.length() - 1);
            } else {
                colaSalida.offer(item);
            }
        }

//        System.out.println(colaSalida.toString().replaceAll(",", "").replaceAll(
//                quote("["), "").replaceAll(quote("]"), ""));
        return colaSalida.toArray(new String[colaSalida.size()]);
    }

    /**
     *
     * @param cadena
     * @return
     */
    public static FuncionOculta[] extraerVariablesOcultas(String cadena) {
        if (cadena == null || cadena.length() == 0) {
            return new FuncionOculta[0];
        }

        String preproceso = cadena.replaceAll("[eE]-", "!").replaceAll("[eE]" 
                + quote( "+"), "¡").replaceAll("[ ]++", "")
                .replaceAll("-" + quote("+"), " - ").replaceAll(
                quote("+") + "-", " - ").replaceAll(",", " , ")
                .replaceAll(quote("("), "( ").replaceAll(quote(
                "["), "[ ").replaceAll(quote("+"), " + ").replaceAll(
                "-", " - ").replaceAll(quote("*"), " * ").replaceAll(
                "/", " / ").replaceAll("\\\\", " \\\\ ").replaceAll(Pattern
                .quote("^"), " ^ ").replaceAll("!", "E-").replaceAll("¡", "E+")
                .replaceAll("==", " == ").replaceAll("=", " = ").trim();
        Queue<FuncionOculta> colaVariablesOcultas = new LinkedList<>();
        TreeSet<String> variablesFuncionesProcesadas = new TreeSet<>();

        for (String item : preproceso.split(" ")) {
            if (item.endsWith("(") || item.endsWith("[")) {
                String cadenaProcesar = item.substring(0, item.length() - 1);

                if (!variablesFuncionesProcesadas.contains(cadenaProcesar) &&
                        !esFuncion(cadenaProcesar)) {
                    String[] funciones = Funcion
                            .listaFuncionesSufijoDescendente(cadenaProcesar);

                    for (String funcion : funciones) {
                        String variableIzquierda = cadenaProcesar.substring(0,
                                cadenaProcesar.length() - funcion.length());

                        if (!esFuncion(variableIzquierda)) {
                            colaVariablesOcultas.offer(new FuncionOculta(
                                    funcion, variableIzquierda));
                        }
                    }

                    variablesFuncionesProcesadas.add(cadenaProcesar);
                }
            }
        }

        return colaVariablesOcultas
                .toArray(new FuncionOculta[colaVariablesOcultas
                .size()]);
    }
    private static BigDecimal delta = new BigDecimal("1e-64");
    private static BigDecimal epsilon = new BigDecimal("1e-64");

    /**
     *
     * @param cadena
     * @param variables
     * @param inicio
     * @return
     * @throws CaracterIlegal
     * @throws NumberFormatException
     */
    public static BigDecimal[] encontrarCeros(String cadena,
            String[] variables, BigDecimal[] inicio) throws CaracterIlegal,
            NumberFormatException {
        if (variables == null || inicio == null ||
                variables.length != inicio.length) {
            return new BigDecimal[0];
        }

        String[] expresionRPN = convertirRPN(cadena);
        MathContext contexto = new MathContext(64, HALF_UP);

        BigDecimal[] p = inicio;

        for (int k = 0; k < 300; k++) {
            Object fActual;
            
            try {
                fActual = Procesador.evaluarRPN(reemplazarValorFuncion(
                        expresionRPN, variables, p));
            } catch (NumberFormatException e) {
                continue;
            }

            if (!(fActual instanceof BigDecimal)) {
                return p;
            }

            BigDecimal funcionActual = (BigDecimal) fActual;
            BigDecimal[] dF;
            
            try {
                dF = calcularDerivadasParciales(expresionRPN,
                        variables, p);
            } catch (NumberFormatException e) {
                continue;
            }
                
            BigDecimal[] pSiguiente = new BigDecimal[p.length];
            System.arraycopy(p, 0, pSiguiente, 0, p.length);
            BigDecimal funcionSiguiente = funcionActual;
            BigDecimal[] pAdivinanza = new BigDecimal[p.length];

            for (int i = 0; i < p.length; i++) {
                if (dF[i].compareTo(ZERO) == 0) {
                    continue;
                }

                pAdivinanza[i] = p[i].subtract(funcionActual.divide(dF[i],
                        contexto), contexto);

                if (p[i].abs(contexto).subtract(pAdivinanza[i], contexto)
                        .compareTo(delta) < 0) {
                    continue;
                }

                BigDecimal[] pFuncion = new BigDecimal[p.length];
                System.arraycopy(p, 0, pFuncion, 0, i);

                if (i < p.length - 1) {
                    System.arraycopy(p, i + 1, pFuncion, i + 1,
                            p.length - i - 1);
                }

                pFuncion[i] = pAdivinanza[i];

                Object fTemporal;
                
                try {
                    fTemporal = Procesador.evaluarRPN(reemplazarValorFuncion(
                            expresionRPN, variables, pFuncion));
                } catch (NumberFormatException e) {
                    continue;
                }

                if (!(fTemporal instanceof BigDecimal)) {
                    return p;
                }

                BigDecimal funcionTemporal = (BigDecimal) fTemporal;

                if (funcionTemporal.abs(contexto).compareTo(funcionSiguiente
                        .abs(contexto)) < 0) {
                    funcionSiguiente = funcionTemporal;
                    pSiguiente = pFuncion;
                }
            }

            if (funcionSiguiente.equals(fActual)) {
                break;
            }

            funcionActual = funcionSiguiente;
            p = pSiguiente;

            if (funcionActual.abs(contexto).compareTo(epsilon) < 0) {
                break;
            }
        }

        return p;
    }

    private static String[] variablesCerosActuales = new String[0];
    
    /**
     *
     * @param cadena
     * @return
     * @throws CaracterIlegal
     * @throws NumberFormatException
     */
    public static Object[] encontrarCeros(String cadena)
            throws CaracterIlegal, NumberFormatException {
        String[] expresionRPN = convertirRPN(cadena);
        TreeSet<String> colaVariables = new TreeSet<>();
        MathContext contextoUsuario = new MathContext(
                VentanaCalculo.PRECISION_PREDETERMINADA, HALF_UP);
        for (String item : expresionRPN) {
            if (esVariable(item)) {
                colaVariables.add(item);
            }
        }

        String[] variables = variablesCerosActuales = colaVariables.toArray(
                new String[colaVariables.size()]);

        if (variables.length == 0) {
            Object resultado = Procesador.procesar(cadena);

            if (resultado instanceof BigDecimal) {
                if (new BigDecimal(resultado.toString(), contextoUsuario)
                        .compareTo(ZERO) == 0) {
                    return new Object[] {true};
                } else {
                    return new Object[] {false};
                }
            } else {
                return new Object[] {false};
            }
        } else {
            BigDecimal[] valores = new BigDecimal[variables.length];
            BigDecimal[] valoresIniciales = new BigDecimal[] {ONE,
                ZERO, new BigDecimal("-1"), ZERO}; //1, 0, -1, 0
            BigDecimal[] valoresPrimigenios = new BigDecimal[valores.length];
            Arrays.fill(valoresPrimigenios, ONE);
            BigDecimal resultado = BigDecimal.TEN;
            String[] rpn = Procesador.convertirRPN(cadena);

            for (int i = 0; i < 10; i++) {
//                System.out.println("Resultado-" + (i-1)+": "+resultado.setScale(
//                        contextoUsuario.getPrecision(), HALF_UP));
                if (resultado.setScale(contextoUsuario.getPrecision(), 
                        HALF_UP).compareTo(ZERO) == 0) {
                    return valores;
                }

                for (int j = 0; j < valores.length; j++) {
                    valoresPrimigenios[j] = valoresPrimigenios[j].multiply(
                            valoresIniciales[(i + j) % 4].add(ONE).add(
                            ONE));
                    valores[j] = valoresPrimigenios[j];
                }

                valores = encontrarCeros(cadena, variables, valores);
                Object resultadoObjeto = Procesador.evaluarRPN(Procesador
                        .reemplazarValorFuncion(rpn, variables, valores));
                
                if(resultadoObjeto instanceof BigDecimal) {
                    resultado = (BigDecimal) resultadoObjeto;
                }
            }

            if (resultado.setScale(contextoUsuario.getPrecision(), 
                        HALF_UP).compareTo(ZERO) == 0) {
                    return valores;
            } else {
                BigDecimal[] valoresIntento = new BigDecimal[valores.length];
                BigDecimal resultadoValoresIntento = BigDecimal.TEN;
                BigDecimal resultadoValores = resultado;
                Object resultadoObjeto;
                
                //Intento: [0...0]
                Arrays.fill(valoresIntento, ZERO);
                valoresIntento = encontrarCeros(cadena, variables, 
                        valoresIntento);
                resultadoObjeto = Procesador.evaluarRPN(Procesador
                        .reemplazarValorFuncion(rpn, variables, valoresIntento
                        ));
                
                if (resultadoObjeto instanceof BigDecimal) {
                    resultadoValoresIntento = (BigDecimal) resultadoObjeto;
                    
                    if (resultadoValoresIntento.abs().setScale(contextoUsuario
                            .getPrecision(), HALF_UP).compareTo(
                            resultadoValores.abs().setScale(contextoUsuario
                            .getPrecision(), HALF_UP)) < 0) {
                        valores = Arrays.copyOf(valoresIntento, valoresIntento
                                .length);
                        resultadoValores = resultadoValoresIntento;
                    }
                }
                
                //Intento: [1...1]
                Arrays.fill(valoresIntento, ONE);
                valoresIntento = encontrarCeros(cadena, variables, 
                        Arrays.copyOf(valoresIntento, valoresIntento.length));
                resultadoObjeto = Procesador.evaluarRPN(Procesador
                        .reemplazarValorFuncion(rpn, variables, valoresIntento
                        ));
                
                if (resultadoObjeto instanceof BigDecimal) {
                    resultadoValoresIntento = (BigDecimal) resultadoObjeto;
                    
                    if (resultadoValoresIntento.abs().setScale(contextoUsuario
                            .getPrecision(), HALF_UP).compareTo(
                            resultadoValores.abs().setScale(contextoUsuario
                            .getPrecision(), HALF_UP)) < 0) {
                        valores = Arrays.copyOf(valoresIntento, valoresIntento
                                .length);
                        resultadoValores = resultadoValoresIntento;
                    }
                }
                
                //Intento: [0.5 ... 0.5]
                Arrays.fill(valoresIntento, new BigDecimal(0.5));
                valoresIntento = encontrarCeros(cadena, variables, 
                        valoresIntento);
                resultadoObjeto = Procesador.evaluarRPN(Procesador
                        .reemplazarValorFuncion(rpn, variables, valoresIntento
                        ));
                
                if (resultadoObjeto instanceof BigDecimal) {
                    resultadoValoresIntento = (BigDecimal) resultadoObjeto;
                    
                    if (resultadoValoresIntento.abs().setScale(contextoUsuario
                            .getPrecision(), HALF_UP).compareTo(
                            resultadoValores.abs().setScale(contextoUsuario
                            .getPrecision(), HALF_UP)) < 0) {
                        valores = Arrays.copyOf(valoresIntento, valoresIntento
                                .length);
                        resultadoValores = resultadoValoresIntento;
                    }
                }
                
                //Intento: [-1 ... -1]
                Arrays.fill(valoresIntento, new BigDecimal("-1"));
                valoresIntento = encontrarCeros(cadena, variables, 
                        valoresIntento);
                resultadoObjeto = Procesador.evaluarRPN(Procesador
                        .reemplazarValorFuncion(rpn, variables, valoresIntento
                        ));
                
                if (resultadoObjeto instanceof BigDecimal) {
                    resultadoValoresIntento = (BigDecimal) resultadoObjeto;
                    
                    if (resultadoValoresIntento.abs().setScale(contextoUsuario
                            .getPrecision(), HALF_UP).compareTo(
                            resultadoValores.abs().setScale(contextoUsuario
                            .getPrecision(), HALF_UP)) < 0) {
                        valores = Arrays.copyOf(valoresIntento, valoresIntento
                                .length);
                        resultadoValores = resultadoValoresIntento;
                    }
                }
                
                //Intento: [-0.5 ... -0.5]
                Arrays.fill(valoresIntento, new BigDecimal("-0.5"));
                valoresIntento = encontrarCeros(cadena, variables, 
                        valoresIntento);
                resultadoObjeto = Procesador.evaluarRPN(Procesador
                        .reemplazarValorFuncion(rpn, variables, valoresIntento
                        ));
                
                if (resultadoObjeto instanceof BigDecimal) {
                    resultadoValoresIntento = (BigDecimal) resultadoObjeto;
                    
                    if (resultadoValoresIntento.abs().setScale(contextoUsuario
                            .getPrecision(), HALF_UP).compareTo(
                            resultadoValores.abs().setScale(contextoUsuario
                            .getPrecision(), HALF_UP)) < 0) {
                        valores = Arrays.copyOf(valoresIntento, valoresIntento
                                .length);
                        resultadoValores = resultadoValoresIntento;
                    }
                }
                
                if (resultadoValores.setScale(contextoUsuario.getPrecision(), 
                        HALF_UP).compareTo(ZERO) == 0) {
                    return valores;
                } else {
                    return new Object[]{false};
                }
            }
        }
    }

    private static CaracterIlegal procesarError(String cadena, char caracter,
            int numero) {
        int cuenta = -1;
        int numeroVeces = 0;

        for (char simbolo : cadena.toCharArray()) {
            cuenta++;

            if (simbolo == caracter) {
                numeroVeces++;

                if (numeroVeces == numero) {
                    break;
                }
            }
        }

        CaracterIlegal error = new CaracterIlegal(cadena, cuenta);
        return error;
    }

    /**
     *
     * @param item
     * <
     * p/>
     * @return
     */
    public static boolean esNumero(String item) {
        boolean numeroNormal = patronNumero.matcher(item).matches();

        if (numeroNormal) {
            return true;
        } else {
            return false;
        }

//         else {
//            boolean concuerda = patronVariableFuncion.matcher(item).matches();
//
//            if (concuerda) {
//                if (Funcion.existeFuncion(item)){
//                    return false;
//                } else {
//                    return true;
//                }
//            } else {
//                return false;
//            }
//        }
    }

    /**
     *
     * @param item
     * <
     * p/>
     * @return
     */
    public static boolean esFuncion(String item) {
        boolean concuerda = patronVariableFuncion.matcher(item).matches();

        if (concuerda) {
            if (Funcion.existeFuncion(item)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     *
     * @param item
     * <
     * p/>
     * @return
     */
    public static boolean esVariable(String item) {
        boolean concuerda = patronVariableFuncion.matcher(item).matches();

        if (concuerda) {
            if (Funcion.existeFuncion(item)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     *
     * @param item
     * <
     * p/>
     * @return
     */
    public static boolean esParentesisDerecho(String item) {
        if (patronParentesisDerecho.matcher(item).matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param item
     * <
     * p/>
     * @return
     */
    public static boolean esParentesisIzquierdo(String item) {
        if (patronParentesisIzquierdo.matcher(item).matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param item
     * <
     * p/>
     * @return
     */
    public static boolean esOperador(String item) {
        if (patronOperador.matcher(item).matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param item
     * <
     * p/>
     * @return
     */
    public static boolean esSeparadorArgumento(String item) {
        if (patronArgumento.matcher(item).matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param item
     * <
     * p/>
     * @return
     */
    public static boolean esOperadorAsociativoIzquierdo(String item) {
        if (item.equals("+") || item.equals("-") || item.equals("*") ||
                item.equals("/") || item.equals("\\") || item.equals("%")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param item
     * <
     * p/>
     * @return
     */
    public static boolean esOperadorAsociativoDerecho(String item) {
        if (item.equals("^")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param item
     * @param operadorPila
     * <
     * p/>
     * @return
     */
    public static boolean tieneOperadorMenorIgualPrecedencia(String item,
            String operadorPila) {
        if (esParentesisDerecho(item) || esParentesisIzquierdo(item)) {
            if (esParentesisDerecho(operadorPila) ||
                    esParentesisIzquierdo(operadorPila)) {
                return true;
            } else {
                return false;
            }
        } else if (item.equals("^")) {
            if (esParentesisDerecho(operadorPila) ||
                    esParentesisIzquierdo(operadorPila) ||
                    operadorPila.equals("^")) {
                return true;
            } else {
                return false;
            }
        } else if (item.equals("*") || item.equals("/") || item.equals("\\")) {
            if (esParentesisDerecho(operadorPila) ||
                    esParentesisIzquierdo(operadorPila) ||
                    operadorPila.equals("^") || operadorPila.equals("*") ||
                    operadorPila.equals("/") || operadorPila.equals("\\") 
                    || operadorPila.equals("%")) {
                return true;
            } else {
                return false;
            }
        } else if (item.equals("+") || item.equals("-")) {
            if (esParentesisDerecho(operadorPila) ||
                    esParentesisIzquierdo(operadorPila) ||
                    operadorPila.equals("^") || operadorPila.equals("*") ||
                    operadorPila.equals("/") || operadorPila.equals("\\") ||
                    operadorPila.equals("+") || operadorPila.equals("-") 
                    || operadorPila.equals("%")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     *
     * @param item
     * @param operadorPila
     * <
     * p/>
     * @return
     */
    public static boolean tieneOperadorMenorPrecedencia(String item,
            String operadorPila) {
        if (esParentesisDerecho(item) || esParentesisIzquierdo(item)) {
            return false;
        } else if (item.equals("^")) {
            if (esParentesisDerecho(operadorPila) ||
                    esParentesisIzquierdo(operadorPila)) {
                return true;
            } else {
                return false;
            }
        } else if (item.equals("*") || item.equals("/") || item.equals("\\") 
                    || operadorPila.equals("%")) {
            if (esParentesisDerecho(operadorPila) ||
                    esParentesisIzquierdo(operadorPila) ||
                    operadorPila.equals("^")) {
                return true;
            } else {
                return false;
            }
        } else if (item.equals("+") || item.equals("-")) {
            if (esParentesisDerecho(operadorPila) ||
                    esParentesisIzquierdo(operadorPila) ||
                    operadorPila.equals("^") || operadorPila.equals("*") ||
                    operadorPila.equals("/") || operadorPila.equals("\\") 
                    || operadorPila.equals("%")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     *
     * @param base
     * @param exponente
     * @param contexto
     * <
     * p/>
     * @return
     * <
     * p/>
     * @throws CaracterIlegal
     */
    public static BigDecimal potenciacion(BigDecimal base,
            BigDecimal exponente, MathContext contexto) throws CaracterIlegal {
        int signoExponente = exponente.signum();
        BigDecimal resultado = ZERO;

        try {
            // Perform X^(A+B)=X^A*X^B (B = remainder)
            double parteDecimal = base.doubleValue();
            exponente = exponente.multiply(new BigDecimal(signoExponente));
            BigDecimal restoExponente = exponente.remainder(ONE);
            BigDecimal parteEnteraExponente = exponente
                    .subtract(restoExponente, contexto);
            
            
            
            // Calculate big part of the power using context -
            // bigger range and performance but lower accuracy
            BigDecimal potenciaEntera = base.pow(parteEnteraExponente
                    .intValueExact(), contexto);
            BigDecimal doublePow =
                    new BigDecimal(Math.pow(parteDecimal, restoExponente
                    .doubleValue()));
            resultado = potenciaEntera.multiply(doublePow, contexto);
        } catch (Exception e) {
            throw new CaracterIlegal(base + "^" + exponente, base.toString()
                    .length());
        }
        // Fix negative power
        if (signoExponente == -1) {
            resultado = ONE.divide(resultado, contexto);
        }

        return resultado;
    }

    /**
     * Método principal de Procesador.
     * <p/>
     * @param args Los numeroArgumentos del programa.
     */
    public static void main(String[] args) {
        try {
//            Procesador instancia = new Procesador();
            //System.out.println("hypot (  5,6   )-sen(x)\\4+6*cos[13]/9^2: " + convertirRPN("hypot (  5,6   )-sen(x)\\4+6*cos[13]/9^2"));
            //System.out.println("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3: " + convertirRPN("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3"));
            //System.out.println("4+6*+cos[13]/9^2: " + convertirRPN("4+6*+cos[13]/9^2"));
//            System.out.println("4+6-cos[13 + 2]/9^2: " + convertirRPN("4+6-cos[13 + 2]/9^2"));
//            System.out.println("4+6: " + convertirRPN("4+6"));
//            System.out.println("4-cos(0): " + convertirRPN("4-cos(0)"));
//            System.out.println("-4+6: " + convertirRPN("-4+6"));
//            System.out.println("-4(6): " + convertirRPN("-4(6)"));
//            System.out.println("0+-4*6: " + convertirRPN("0+-4*6"));
//            System.out.println("-cos(5*x, 6*y)+-5(8): " + convertirRPN("-cos(5*x, 6*y)+-5(8)"));
//            System.out.println("0-cos(5*x, 6*y)-5*8: " + convertirRPN("0-cos(5*x, 6*y)-5*8"));
//            String salidaRPN;
//            System.out.println("-5(6-12)+-15*16/(17\\18)^3: " + (salidaRPN = convertirRPN("-5(6-12)+-15*16/(17\\18)^3")));//= -172,18107
//            System.out.println("-5(6-12)+-15*16/(17\\18)^3: " + evaluarRPN(salidaRPN.split(" ")).toString());
//            System.out.println("8\\16: " + convertirRPN("8\\16"));
//            System.out.println("cos(10)^2+sen(10)^2: " + (salidaRPN = convertirRPN("cos(10)^2+sen(10)^2")));
//            System.out.println("cos(10)^2+sen(10)^2: " + evaluarRPN(salidaRPN.split(" ")).toString());
//            System.out.println("x^2+x+0: " + (salidaRPN = convertirRPN("x^2+x+0")));
//            System.out.println("x^2+x+0: " + evaluarRPN(salidaRPN.split(" ")).toString());
//            System.out.println("f(x)=x^2+x+0: ");
//            Funcion.agregarFuncion("f(x)=x^2+x+0");
//            System.out.println("f(x): " + (salidaRPN = convertirRPN("f(3)")));
//            System.out.println("f(3)=3^2+3+0=12: " + evaluarRPN(salidaRPN.split(" ")).toString());
//            System.out.println(Arrays.toString(convertirRPN("derivar(5, y)"
//                    )));
//            System.out.println(Funcion.computar("derivar", new Object[]{"cos(x)", 0.0}
//                    ));
            System.out.println(Arrays.toString(extraerVariablesOcultas(
                    "asenasen(x) + acosacos(x) + sensen(x)")));
//            System.out.println(procesar("asenasen(x) + acosacos(x) + sensen(x) "
//                    + "+ cos(x) * sen(x)"
//                    ));
//            System.out.println(procesar("derivar(derivar(0-cos(x),x),x)"));
//            System.out.println(procesar("hipot(3,4)"));
            BigDecimal[] resultado = new BigDecimal[2];
//            System.out.println("Ceros[x ^ 2 + y ^ 2]:" + Arrays.toString(
//                    resultado = encontrarCeros("x ^ 2 + y ^ 2", new String[]{"x", "y"}, 
//                    new BigDecimal[]{new BigDecimal("10"), new BigDecimal("-10"
//                    )})));
//            System.out.println(Procesador.procesar("(0+" + resultado[0].toString() + ") ^ 2 + (0+" + resultado[1].toString() + ") ^ 2"));
            resultado = new BigDecimal[1];
            System.out.println("Ceros[x ^ 2]:" + Arrays.toString(
                    resultado = encontrarCeros("x ^ 2 ", new String[] {"x"},
                    new BigDecimal[] {new BigDecimal("10")})));
            System.out.println(Procesador.procesar("(0+" + resultado[0]
                    .toString() + ") ^ 2"));
            resultado = new BigDecimal[1];
            System.out.println("CerosT[x ^ 2 + y ^ 2]:" + Arrays.toString(
                    resultado = objetoABigDecimal(encontrarCeros("x ^ 2 + (y + 1) ^ 2"))));
            System.out.println(Arrays.toString(resultado));
            System.out.println(Arrays.toString(cambiarEscalaArreglo(resultado,
                    new MathContext(VentanaCalculo.PRECISION_PREDETERMINADA, 
                    HALF_UP))));
            System.out.println(Procesador.procesar("(0+" + resultado[0]
                    .toString() + ") ^ 2 + (0+" + resultado[1]
                    .toString() + " + 1) ^ 2"));
        } catch (CaracterIlegal ex) {
            Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null,
                    ex);
            System.out.println(ex.getLocalizedMessage() + " " + ex.getCadena()
                    .charAt(ex.getIndice()) + " " + ex.getIndice());
        }
    }

    /**
     *
     * @param arreglo
     * @param escala
     * @return
     */
    public static BigDecimal[] cambiarEscalaArreglo(BigDecimal[] arreglo, 
            MathContext escala) {
        if(arreglo == null || arreglo.length == 0) {
            return new BigDecimal[0];
        }
        
        if(escala == null) {
            return arreglo;
        }
        
        BigDecimal[] resultado = new BigDecimal[arreglo.length];
        
        for (int i = 0; i < resultado.length; i++) {
            resultado[i] = new BigDecimal(arreglo[i].toString(), escala);
        }
        
        return resultado;
    }
    
    /**
     *
     * @param arreglo
     * @return
     */
    public static BigDecimal[] objetoABigDecimal(Object[] arreglo) {
        if(arreglo == null) {
            return new BigDecimal[0];
        }
        
        BigDecimal[] salida = new BigDecimal[arreglo.length];
        
        for (int i = 0; i < salida.length; i++) {
            salida[i] = (BigDecimal) arreglo[i];
        }
        
        return salida;
    }
    
    /**
     *
     * @return
     */
    public static String[] obtenerVariablesCerosActuales() {
        return variablesCerosActuales;
    }
    
    private static void modificarConteo(
            TreeMap<Character, Integer> conteoAlfabeto, String item) {
        for (char caracter : item.toCharArray()) {
            Integer conteo = conteoAlfabeto.get(caracter);

            if (conteo == null) {
                conteoAlfabeto.put(caracter, 1);
            } else {
                conteoAlfabeto.put(caracter, conteo.intValue() + 1);
            }
        }
    }

    private static String[] reemplazarValorFuncion(String[] rpn,
            String[] nombreVariables, BigDecimal[] valores) {
        if (rpn == null) {
            return new String[0];
        }

        if (nombreVariables == null || valores == null ||
                valores.length != nombreVariables.length) {
            return Arrays.copyOf(rpn, rpn.length);
        }

        TreeMap<String, BigDecimal> cambio = new TreeMap<>();
        String[] salida = new String[rpn.length];

        for (int i = 0; i < valores.length; i++) {
            cambio.put(nombreVariables[i], valores[i]);
        }

        for (int i = 0; i < salida.length; i++) {
            BigDecimal valor = cambio.get(rpn[i]);

            if (valor != null) {
                salida[i] = valor.toString();
            } else {
                salida[i] = rpn[i];
            }
        }

        return salida;
    }

    private static BigDecimal[] calcularDerivadasParciales(String[] funcionRPN,
            String[] variables, BigDecimal[] valores) 
            throws NumberFormatException, CaracterIlegal {
        if (funcionRPN == null || variables == null || valores == null ||
                variables.length != valores.length) {
            return new BigDecimal[0];
        }

        if (variables.length == 0) {
            Object dTotal = Procesador.evaluarRPN(funcionRPN);

            if (!(dTotal instanceof BigDecimal)) {
                return new BigDecimal[0];
            }

            return new BigDecimal[] {(BigDecimal) dTotal};
        }

        BigDecimal[] derivadas = new BigDecimal[variables.length];

        for (int i = 0; i < variables.length; i++) {
            BigDecimal[] p = new BigDecimal[variables.length - 1];
            String[] variablesActuales = new String[variables.length - 1];

            System.arraycopy(valores, 0, p, 0, i);
            System.arraycopy(variables, 0, variablesActuales, 0, i);

            if (i < variables.length - 1) {
                System.arraycopy(valores, i + 1, p, i, valores.length - i - 1);
                System.arraycopy(variables, i + 1, variablesActuales, i,
                        variables.length - i - 1);
            }

            derivadas[i] = calcularDerivada(funcionRPN, variablesActuales, p,
                    valores[i]);
        }

        return derivadas;
    }

    private static BigDecimal calcularDerivada(String[] funcionRPN,
            String[] variables, BigDecimal[] valores, BigDecimal punto)
            throws NumberFormatException, CaracterIlegal {
        if (funcionRPN == null || variables == null || valores == null ||
                variables.length != valores.length) {
            return new BigDecimal(NaN);
        }

//        if(variables.length == 0) {
//            Object dTotal = Procesador.evaluarRPN(funcionRPN);
//            
//            if(!(dTotal instanceof BigDecimal)) {
//                return new BigDecimal(NaN);
//            }
//            
//            return (BigDecimal) dTotal;
//        }

        MathContext contexto = new MathContext(64, HALF_UP);
        BigDecimal cinco = new BigDecimal("0.005");
        BigDecimal uno = new BigDecimal("0.01");
        BigDecimal ocho = new BigDecimal("8");
        BigDecimal divisor = new BigDecimal("12").multiply(cinco, contexto);

        BigDecimal f1 = evaluarExpresionPunto(reemplazarValorFuncion(funcionRPN,
                variables, valores), punto.add(cinco, contexto));
        BigDecimal f_1 = evaluarExpresionPunto(reemplazarValorFuncion(
                funcionRPN, variables, valores), punto.subtract(cinco,
                contexto));
        BigDecimal f2 = evaluarExpresionPunto(reemplazarValorFuncion(funcionRPN,
                variables, valores), punto.add(uno, contexto));
        BigDecimal f_2 = evaluarExpresionPunto(reemplazarValorFuncion(
                funcionRPN, variables, valores), punto.subtract(uno, contexto));
        BigDecimal derivada = f_2.subtract(ocho.multiply(f_1, contexto),
                contexto).add(ocho.multiply(f1, contexto), contexto).subtract(
                f2, contexto).divide(divisor, contexto);

        return derivada;
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
            Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE,
                    null, ex);
            return ZERO;
        }
    }
}
