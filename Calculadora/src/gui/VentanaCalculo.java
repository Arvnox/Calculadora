/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import calculadora.Calculadora;
import datos.Funcion;
import datos.FuncionOculta;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.DropMode;
import javax.swing.JOptionPane;
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
public class VentanaCalculo extends javax.swing.JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    public final static int PRECISION_PREDETERMINADA = 3;
    /**
     *
     */
    public final static Color COLOR_FONDO_PREDETERMINADO =
            new Color(252, 255, 252);
    /**
     *
     */
    public final static Color COLOR_FUENTE_PREDETERMINADO =
            Color.BLACK;
    /**
     *
     */
    public final static Color COLOR_BOTON_PREDETERMINADO =
            Color.WHITE;
    /**
     *
     */
    private Deque<String> listaDeshacer = new LinkedList<>();
    /**
     *
     */
    private Queue<String> listaRehacer = new LinkedList<>();
    /**
     *
     */
    private static int precision = PRECISION_PREDETERMINADA;
    /**
     *
     */
    private static Color colorFondo = COLOR_FONDO_PREDETERMINADO;
    /**
     *
     */
    private static Color colorBoton = COLOR_BOTON_PREDETERMINADO;
    /**
     *
     */
    private static Color colorFuente = COLOR_FUENTE_PREDETERMINADO;
    /**
     *
     */
    private static BigDecimal PI = Funcion.PI;
    /**
     *
     */
    private static BigDecimal E = Funcion.E;

    /**
     * Creates new form VentanaCalculo
     */
    public VentanaCalculo() {
        initComponents();

        try {
            setIconImage(ImageIO.read(getClass().getResource(
                    "/calculadora/calculadora.png")));
        } catch (IOException ex) {
            Logger.getLogger(VentanaCalculo.class.getName()).log(Level.SEVERE,
                    null, ex);
        } finally {
            setLocationRelativeTo(null);
            ((javax.swing.JTextField) jComboBox1.getEditor()
                    .getEditorComponent()).addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    if (e.getKeyChar() == '\n') {
                        procesarExpresion();
                    } else {
                        String textoAnterior =
                                ((javax.swing.JTextField) jComboBox1.getEditor()
                                .getEditorComponent())
                                .getText();

                        if (!textoAnterior.equals(listaDeshacer.peekLast())) {
                            listaDeshacer.push(textoAnterior);
                        }
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });

            jComboBox1.requestFocus();
        }

        javax.swing.JTextField campoTexto = (javax.swing.JTextField) jComboBox1
                .getEditor().getEditorComponent();
        campoTexto.setDragEnabled(true);
        campoTexto.setDropMode(DropMode.INSERT);
    }

    /**
     *
     * @param cadena
     */
    public void preProcesarCadena(String cadena) {
        
        FuncionOculta[] funcionesOcultas = Procesador
                .extraerVariablesOcultas(cadena);
        if (funcionesOcultas.length > 0) {
            Calculadora.getSelectorFuncionesOcultas().elegirFuncionesOcultas(
                    cadena, funcionesOcultas);
            setVisible(false);
        } else {
            procesarCadena(cadena);
        }
    }

    /**
     *
     * @param cadena
     */
    public void procesarCadena(String cadena) {
        if(cadena.trim().length() == 0) {
            JOptionPane.showMessageDialog(this,
                            "Error: Cadena vacía", "Error:Expresión inválida",
                            JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (cadena.trim().toLowerCase().startsWith("graficar:")) {
            graficarCadena(cadena.trim().substring(9).trim());
        } else if (cadena.trim().replaceAll("[ ]++", "").contains("==")) {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            javax.swing.JTextField campoTexto =
                    (javax.swing.JTextField) jComboBox1
                    .getEditor().getEditorComponent();

            try {
                //        JOptionPane.showMessageDialog(this, "Error: Esta acción está en "
                //                + "desarrollo. Sentimos las molestias.", "Característica en "
                //                + "desarrollo.", JOptionPane.ERROR_MESSAGE);
                //TODO all the logic procesing
                String cadenaProcesar = cadena.trim().replaceAll("[ ]++", "");
                int primerIgual = cadenaProcesar.indexOf("==");
                int segundoIgual = cadenaProcesar.indexOf("==", primerIgual 
                        + 1);
                
                if(primerIgual < 0 || segundoIgual >= 0) {
                    throw new CaracterIlegal(cadena, segundoIgual + 1);
                }
                
                String[] convertirCadena = cadenaProcesar.split("==");
                cadenaProcesar = convertirCadena[0] + " - (" + 
                        convertirCadena[1] + ")";
                String cadenaProcesada;
                Object[] arregloSalida = Procesador.encontrarCeros(
                        cadenaProcesar);
                String[] variables = Procesador.obtenerVariablesCerosActuales();
                MathContext contextoUsuario = new MathContext(precision,
                        RoundingMode.HALF_UP);
                
                if(arregloSalida[0] instanceof Boolean) {
                    if((Boolean) arregloSalida[0]) {
                        cadenaProcesada = "Verdadero";
                    } else {
                        cadenaProcesada = "Falso";
                    }
                } else {
                    StringBuilder colaProcesada = new StringBuilder(
                            arregloSalida.length * 10);
                    colaProcesada = colaProcesada.append("[").append(
                                variables[0]).append(" = ").append(
                            new BigDecimal(arregloSalida[0].toString(), 
                            contextoUsuario));
                    for (int i = 1; i < arregloSalida.length; i++) {
                        colaProcesada = colaProcesada.append(", ").append(
                                variables[i]).append(" = ").append(((BigDecimal)
                                arregloSalida[i]).setScale(contextoUsuario
                                .getPrecision(), RoundingMode.HALF_UP));
                    }
                    
                    cadenaProcesada = colaProcesada.append("]").toString();
                }

                cambiarDinamicamenteAreaTexto(("[" + cadena + "] = " 
                        + cadenaProcesada + "\n").length());
                
                jTextArea1.append("[" + cadena + "] = " 
                        + cadenaProcesada + "\n");
                jComboBox1.addItem(agregarCadenaListaCombo(cadena));
                campoTexto = (javax.swing.JTextField) jComboBox1
                        .getEditor().getEditorComponent();
                campoTexto.setText("");
            } catch (CaracterIlegal ex) {
                int indice = ex.getIndice();

                if (indice >= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Error: Caracter inválido cerca de \'" + cadena
                            .charAt(indice) + "\'", "Error:Expresión inválida",
                            JOptionPane.ERROR_MESSAGE);
                    campoTexto.setSelectionStart(indice);
                    campoTexto.setSelectionEnd(indice + 1);
                    campoTexto.requestFocusInWindow();
                }
            } finally {
                setCursor(Cursor.getDefaultCursor());
            }
        } else if (cadena.contains("=")) {
            agregarFuncion(cadena);
        } else {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            javax.swing.JTextField campoTexto =
                    (javax.swing.JTextField) jComboBox1
                    .getEditor().getEditorComponent();

            try {
                //        JOptionPane.showMessageDialog(this, "Error: Esta acción está en "
                //                + "desarrollo. Sentimos las molestias.", "Característica en "
                //                + "desarrollo.", JOptionPane.ERROR_MESSAGE);
                //TODO all the logic procesing
                String cadenaProcesada = procesamiento.Procesador.procesar(
                        cadena).toString();
                StringBuilder colaResultado = new StringBuilder(cadenaProcesada
                        .length() * 2);
                
                String[] items = cadenaProcesada
                .replaceAll(Pattern.quote("[") +
                " ", "[").replaceAll(Pattern.quote("(") + "[ ]++", "(")
                .replaceAll("[eE]-", "!").replaceAll("[eE]" + Pattern.quote(
                "+"), "¡").replaceAll(Pattern.quote("("), " ( ")
                .replaceAll(Pattern.quote(")"), " ) ").replaceAll(Pattern.quote(
                "["), " [ ").replaceAll(Pattern.quote("]"), " ] ").replaceAll(
                ",", " , ").replaceAll(Pattern.quote("+"), " + ").replaceAll(
                "-", " - ").replaceAll(Pattern.quote("*"), " * ").replaceAll(
                "/", " / ").replaceAll("\\\\", "  \\\\  ").replaceAll(Pattern
                .quote("^"), " ^ ").replaceAll("[ ]++", " ").replaceAll(
                "!", "E-").replaceAll("¡", "E+").trim().split(" ");

                for (String item : items) {
                    if(Procesador.esNumero(item)) {
                        colaResultado = colaResultado.append(" ").append(
                                new BigDecimal(item).setScale(precision, 
                                RoundingMode.HALF_UP));
                    } else {
                        colaResultado = colaResultado.append(" ").append(item);
                    }
                }
                
                cadenaProcesada = colaResultado.substring(1);
                cambiarDinamicamenteAreaTexto((cadena + " = " 
                        + cadenaProcesada + "\n").length());
                jTextArea1.setText(jTextArea1.getText() + cadena + " = " +
                         cadenaProcesada + "\n");
                jComboBox1.addItem(agregarCadenaListaCombo(cadena));
                campoTexto = (javax.swing.JTextField) jComboBox1
                        .getEditor().getEditorComponent();
                campoTexto.setText("");
            } catch (CaracterIlegal ex) {
                int indice = ex.getIndice();

                if (indice >= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Error: Caracter inválido cerca de \'" + cadena
                            .charAt(indice) + "\'", "Error:Expresión inválida",
                            JOptionPane.ERROR_MESSAGE);
                    campoTexto.setSelectionStart(indice);
                    campoTexto.setSelectionEnd(indice + 1);
                    campoTexto.requestFocusInWindow();
                }
            } finally {
                setCursor(Cursor.getDefaultCursor());
            }
        }
        
        jComboBox1.requestFocus();
    }

    /**
     *
     * @param cadena
     */
    public void graficarCadena(String cadena) {
        if(cadena.trim().length() == 0) {
            JOptionPane.showMessageDialog(this,
                            "Error: Cadena vacía", "Error:Expresión inválida",
                            JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        javax.swing.JTextField campoTexto = (javax.swing.JTextField) jComboBox1
                .getEditor().getEditorComponent();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        if (cadena.trim().toLowerCase().startsWith("graficar")) {
            cadena = cadena.trim().substring(9).trim();
        }

        try {
            //        JOptionPane.showMessageDialog(this, "Error: Esta acción está en "
            //                + "desarrollo. Sentimos las molestias.", "Característica en "
            //                + "desarrollo.", JOptionPane.ERROR_MESSAGE);
            //TODO all the logic procesing
            String minX = JOptionPane.showInputDialog(this, "Ingrese el" +
                     " mínimo del eje x");
            String maxX = JOptionPane.showInputDialog(this, "Ingrese el" +
                     " máximo del eje x");
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Calculadora.getGrafico().graficar(cadena, Double.parseDouble(
                    minX), Double.parseDouble(maxX));

            cambiarDinamicamenteAreaTexto(("Graficar: " + cadena + "\n")
                    .length());
            
            jTextArea1.setText(jTextArea1.getText() + "Graficar: " +
                     cadena + "\n");
            jComboBox1.addItem(agregarCadenaListaCombo("Graficar: " +
                     cadena));
            campoTexto = (javax.swing.JTextField) jComboBox1
                    .getEditor().getEditorComponent();
            campoTexto.setText("");
        } catch (CaracterIlegal ex) {
            int indice = ex.getIndice();

            JOptionPane.showMessageDialog(this,
                    "Error: Caracter inválido cerca de \'" + cadena
                    .charAt(indice) + "\'", "Error:Expresión inválida",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            setCursor(Cursor.getDefaultCursor());
            jComboBox1.requestFocus();
        }
    }

    /**
     *
     * @return
     */
    public static int getPrecision() {
        return precision;
    }

//    private String reemplazarEnCadena(String cadena, Object insertado,
//            int indice, int fin) {
//        return reemplazarEnCadena(cadena, insertado.toString(), indice, fin);
//    }

    /**
     *
     */
    private String reemplazarEnCadena(String cadena, String insertado,
            int inicio, int fin) {
        StringBuilder insertador = new StringBuilder(cadena.length() +
                 insertado.length());
        String izquierda = cadena.substring(0, inicio);
        String derecha = cadena.substring(fin);
        String resultado = insertador.append(izquierda).append(insertado)
                .append(derecha).toString();
        return resultado;
    }

    /**
     *
     */
    private String englobarEnCadena(String cadena, String cadenaIzquierda,
            String cadenaDerecha, int inicio, int fin) {
        StringBuilder englobador = new StringBuilder(cadena.length() +
                 cadenaDerecha.length() + cadenaIzquierda.length());
        String izquierda = cadena.substring(0, inicio);
        String centro = cadena.substring(inicio, fin);
        String derecha = cadena.substring(fin);
        String resultado = englobador.append(izquierda).append(cadenaIzquierda)
                .append(centro).append(cadenaDerecha).append(derecha)
                .toString();
        return resultado;
    }

    /**
     *
     */
    private void reemplazarEnEditor(String cadena) {
        javax.swing.JTextField campoTexto = (javax.swing.JTextField) jComboBox1
                .getEditor().getEditorComponent();
        int inicio = campoTexto.getSelectionStart();
        int fin = campoTexto.getSelectionEnd();
        String cadenaActual = campoTexto.getText();
        listaDeshacer.push(cadenaActual);
        listaRehacer = new LinkedList<>();
        String reemplazo = reemplazarEnCadena(cadenaActual, cadena, inicio,
                fin);
        campoTexto.setText(reemplazo);
        jComboBox1.requestFocus();
        int tamanio = fin - inicio;
        int posicion = cadena.length() + fin - tamanio;
        String cadenaRevisada = cadena.trim();
        
        if (cadenaRevisada.endsWith(")") || cadenaRevisada.endsWith("]")) {
            campoTexto.setCaretPosition(posicion - 1);
        } else {
            campoTexto.setCaretPosition(posicion);
        }
    }

    /**
     *
     */
    private void englobarEnEditor(String cadenaIzquierda,
            String cadenaDerecha) {
        javax.swing.JTextField campoTexto = (javax.swing.JTextField) jComboBox1
                .getEditor().getEditorComponent();
        int inicio = campoTexto.getSelectionStart();
        int fin = campoTexto.getSelectionEnd();
        String cadenaActual = campoTexto.getText();
        listaDeshacer.push(cadenaActual);
        listaRehacer = new LinkedList<>();
        campoTexto.setText(englobarEnCadena(cadenaActual, cadenaIzquierda,
                cadenaDerecha, inicio, fin));
        jComboBox1.requestFocus();
        int posicion = cadenaIzquierda.length() + (fin - inicio) 
                + cadenaDerecha.length();
        String cadenaRevisada = cadenaDerecha.trim();
        
        if (cadenaRevisada.endsWith(")") || cadenaRevisada.endsWith("]")) {
            campoTexto.setCaretPosition(posicion - 1);
        } else {
            campoTexto.setCaretPosition(posicion);
        }
    }

    /**
     *
     */
    private void decidirEnglobarReemplazarEnEditorDerecha(String cadena) {
        javax.swing.JTextField campoTexto = (javax.swing.JTextField) jComboBox1
                .getEditor().getEditorComponent();
        int inicio = campoTexto.getSelectionStart();
        int fin = campoTexto.getSelectionEnd();
        String cadenaActual = campoTexto.getText();
        listaDeshacer.push(cadenaActual);
        listaRehacer = new LinkedList<>();
        jComboBox1.requestFocus();

        if (inicio == fin) {
            campoTexto.setText(reemplazarEnCadena(cadenaActual, cadena, inicio,
                    fin));
            int posicion = cadena.length() + fin;
            String cadenaRevisada = cadena.trim();

            if (cadenaRevisada.endsWith(")") || cadenaRevisada.endsWith("]")) {
                campoTexto.setCaretPosition(posicion - 1);
            } else {
                campoTexto.setCaretPosition(posicion);
            }
        } else {
            campoTexto.setText(englobarEnCadena(cadenaActual, "(", ")" + cadena,
                    inicio, fin));
            int posicion = fin + 1;
            campoTexto.setCaretPosition(posicion);
        }
        
    }

    /**
     *
     */
    private void decidirEnglobarReemplazarEnEditorIzquierda(String cadena) {
        javax.swing.JTextField campoTexto = (javax.swing.JTextField) jComboBox1
                .getEditor().getEditorComponent();
        int inicio = campoTexto.getSelectionStart();
        int fin = campoTexto.getSelectionEnd();
        String cadenaActual = campoTexto.getText();
        listaDeshacer.push(cadenaActual);
        listaRehacer = new LinkedList<>();

        if (inicio == fin) {
            campoTexto.setText(reemplazarEnCadena(cadenaActual, cadena, inicio,
                    fin));
            int posicion = cadena.length() + fin;
            String cadenaRevisada = cadena.trim();

            if (cadenaRevisada.endsWith(")") || cadenaRevisada.endsWith("]")) {
                campoTexto.setCaretPosition(posicion - 1);
            } else {
                campoTexto.setCaretPosition(posicion);
            }
        } else {
            campoTexto.setText(englobarEnCadena(cadenaActual, cadena + "(", ")",
                    inicio, fin));
            int posicion = cadena.length() + 1 + fin;
            campoTexto.setCaretPosition(posicion);
        }
        
        jComboBox1.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        menuPopupEvaluarExpresion = new javax.swing.JMenuItem();
        menuPopupGraficarExpresion = new javax.swing.JMenuItem();
        menuPopupAgregarFuncion = new javax.swing.JMenuItem();
        separadorMenu1 = new javax.swing.JPopupMenu.Separator();
        menuPopupCopiar = new javax.swing.JMenuItem();
        menuPopupPegar = new javax.swing.JMenuItem();
        menuPopupCortar = new javax.swing.JMenuItem();
        separadorMenu2 = new javax.swing.JPopupMenu.Separator();
        menuPopupFunciones = new javax.swing.JMenu();
        menuPopupSeparadorArgumento = new javax.swing.JMenuItem();
        menuPopupParentesisIzquierdo = new javax.swing.JMenuItem();
        menuPopupParentesisDerecho = new javax.swing.JMenuItem();
        menuPopupParentesis = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        menuPopupFuncionesGeometricas = new javax.swing.JMenu();
        menuPopupVolumen = new javax.swing.JMenu();
        menuPopupVolPrismaRegular = new javax.swing.JMenuItem();
        menuPopupVolOrtoedro = new javax.swing.JMenuItem();
        menuPopupVolCubo = new javax.swing.JMenuItem();
        menuPopupVolCilindro = new javax.swing.JMenuItem();
        menuPopupVolEsfera = new javax.swing.JMenuItem();
        menuPopupVolElipsoide = new javax.swing.JMenuItem();
        menuPopupVolPiramide = new javax.swing.JMenuItem();
        menuPopupVolCono = new javax.swing.JMenuItem();
        menuPopupArea = new javax.swing.JMenu();
        menuPopupAreaTriangulo = new javax.swing.JMenuItem();
        menuPopupAreaTrianguloHeron = new javax.swing.JMenuItem();
        menuPopupAreaCuadrilatero = new javax.swing.JMenuItem();
        menuPopupAreaRombo = new javax.swing.JMenuItem();
        menuPopupAreaRectangulo = new javax.swing.JMenuItem();
        menuPopupAreaCuadrado = new javax.swing.JMenuItem();
        menuPopupAreaRomboide = new javax.swing.JMenuItem();
        menuPopupAreaTrapecio = new javax.swing.JMenuItem();
        menuPopupAreaCirculo = new javax.swing.JMenuItem();
        menuPopupAreaElipse = new javax.swing.JMenuItem();
        menuPopupAreaEsfera = new javax.swing.JMenuItem();
        menuPopupAreaCono = new javax.swing.JMenuItem();
        menuPopupAreaCilindro = new javax.swing.JMenuItem();
        menuPopupDistancia = new javax.swing.JMenu();
        menuPopupDistanciaPuntoRecta = new javax.swing.JMenuItem();
        menuPopupDistanciaPuntoPlano = new javax.swing.JMenuItem();
        menuPopupDistanciaRectaRecta = new javax.swing.JMenuItem();
        menuPopupDistanciaRectaPlano = new javax.swing.JMenuItem();
        menuPopupDistanciaPlanoPlano = new javax.swing.JMenuItem();
        menuPopupDistanciaPuntoPunto = new javax.swing.JMenuItem();
        menuPopupPerimetro = new javax.swing.JMenu();
        menuPopupPerimetroCuadrado = new javax.swing.JMenuItem();
        menuPopupPerimetroRectangulo = new javax.swing.JMenuItem();
        menuPopupPerimetroRombo = new javax.swing.JMenuItem();
        menuPopupPerimetroTriangulo = new javax.swing.JMenuItem();
        menuPopupPerimetroRomboide = new javax.swing.JMenuItem();
        menuPopupPerimetroCirculo = new javax.swing.JMenuItem();
        menuPopupPerimetroElipse = new javax.swing.JMenuItem();
        menuPopupFuncionesEstadísticas = new javax.swing.JMenu();
        menuPopupCentralizacion = new javax.swing.JMenu();
        menuPopupPromedio = new javax.swing.JMenuItem();
        menuPopupModa = new javax.swing.JMenuItem();
        menuPopupMediaGeometrica = new javax.swing.JMenuItem();
        menuPopupMediaArmonica = new javax.swing.JMenuItem();
        menuPopupMediana = new javax.swing.JMenuItem();
        menuPopupPosicionNoCentral = new javax.swing.JMenu();
        menuPopupPercentil = new javax.swing.JMenuItem();
        menuPopupCuartil = new javax.swing.JMenuItem();
        menuPopupDecil = new javax.swing.JMenuItem();
        menuPopupFrecuencia = new javax.swing.JMenuItem();
        menuPopupMinimoDatos = new javax.swing.JMenuItem();
        menuPopupMaximoDatos = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuPopupCurtosisFisher = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuPopupDesviacionEstandar = new javax.swing.JMenuItem();
        menuPopupVarianza = new javax.swing.JMenuItem();
        menuPopupCuasiVarianza = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuPopupCVPearson = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        menuPopupCApertura = new javax.swing.JMenuItem();
        jSeparator15 = new javax.swing.JPopupMenu.Separator();
        menuPopupRangoRelativo = new javax.swing.JMenuItem();
        menuPopupRICR = new javax.swing.JMenuItem();
        menuPopupRSICR = new javax.swing.JMenuItem();
        jSeparator17 = new javax.swing.JPopupMenu.Separator();
        menuPopupCABowley = new javax.swing.JMenuItem();
        menuPopupCAPearson = new javax.swing.JMenuItem();
        jSeparator18 = new javax.swing.JPopupMenu.Separator();
        menuPopupPermutaciones = new javax.swing.JMenuItem();
        menuPopupCombinaciones = new javax.swing.JMenuItem();
        menuPopupContar = new javax.swing.JMenuItem();
        menuPopupContarSi = new javax.swing.JMenuItem();
        jSeparator19 = new javax.swing.JPopupMenu.Separator();
        menuPopupSuma = new javax.swing.JMenuItem();
        menuPopupSumaProducto = new javax.swing.JMenuItem();
        menuPopupFuncionesSalud = new javax.swing.JMenu();
        menuPopupIMC = new javax.swing.JMenuItem();
        menuPopupLMC = new javax.swing.JMenuItem();
        menuPopupFuncionesTiempo = new javax.swing.JMenu();
        menuPopupSegundosHoras = new javax.swing.JMenuItem();
        menuPopupSegundosMinutos = new javax.swing.JMenuItem();
        menuPopupHorasSegundos = new javax.swing.JMenuItem();
        menuPopupMinutosSegundos = new javax.swing.JMenuItem();
        menuPopupMilisegundosSegundos = new javax.swing.JMenuItem();
        menuPopupSegundosMilisegundos = new javax.swing.JMenuItem();
        menuPopupTrigonometricas = new javax.swing.JMenu();
        menuPopupSeno = new javax.swing.JMenuItem();
        menuPopupCoseno = new javax.swing.JMenuItem();
        menuPopupTangente = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        menuPopupCosecante = new javax.swing.JMenuItem();
        menuPopupSecante = new javax.swing.JMenuItem();
        menuPopupCotangente = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        menuPopupArcoSeno = new javax.swing.JMenuItem();
        menuPopupArcoCoseno = new javax.swing.JMenuItem();
        menuPopupArcoTangente = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        menuPopupArcoCosecante = new javax.swing.JMenuItem();
        menuPopupArcoSecante = new javax.swing.JMenuItem();
        menuPopupArcoCotangente = new javax.swing.JMenuItem();
        menuPopupHiperbolicas = new javax.swing.JMenu();
        menuPopupSenoHiperbolico = new javax.swing.JMenuItem();
        menuPopupCosenoHiperbolico = new javax.swing.JMenuItem();
        menuPopupTangenteHiperbolica = new javax.swing.JMenuItem();
        menuPopupExponenciales = new javax.swing.JMenu();
        menuPopupDivisionUnoDivididoX = new javax.swing.JMenuItem();
        menuPopupPotenciacionRaizCuadrada = new javax.swing.JMenuItem();
        menuPopupPotenciacionRaizCubica = new javax.swing.JMenuItem();
        menuPopupPotenciacionElevarCuadrado = new javax.swing.JMenuItem();
        menuPopupPotenciacionElevarCubo = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        menuPopupPotenciacionDosElevadoX = new javax.swing.JMenuItem();
        menuPopupPotenciacionExponencial = new javax.swing.JMenuItem();
        menuPopupPotenciacionDiezElevadoX = new javax.swing.JMenuItem();
        menuPopupMetodosNumericos = new javax.swing.JMenu();
        menuPopupPrimos = new javax.swing.JMenu();
        menuPopupEsPrimo = new javax.swing.JMenuItem();
        menuPopupConteoPrimos = new javax.swing.JMenuItem();
        menuPopupMenorPrimoMayorQue = new javax.swing.JMenuItem();
        menuPopupMinimoFuncion = new javax.swing.JMenuItem();
        menuPopupMaximoFuncion = new javax.swing.JMenuItem();
        menuPopupFuncionesDerivacion = new javax.swing.JMenu();
        menuPopupDerivacionUnaVariable = new javax.swing.JMenuItem();
        menuPopupSegundaDerivada = new javax.swing.JMenuItem();
        menuPopupTerceraDerivada = new javax.swing.JMenuItem();
        menuPopupCuartaDerivada = new javax.swing.JMenuItem();
        menuPopupIntegracion = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        menuPopupFactorial = new javax.swing.JMenuItem();
        menuPopupRedondeoEntero = new javax.swing.JMenuItem();
        menuPopupHipotenusa = new javax.swing.JMenuItem();
        menuPopupResiduo = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        menuPopupMCM = new javax.swing.JMenuItem();
        menuPopupMCD = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        menuPopupIgualarACeroFuncion = new javax.swing.JMenuItem();
        menuPopupAleatorio = new javax.swing.JMenu();
        menuPopupAleatorioGaussiano = new javax.swing.JMenuItem();
        menuPopupAleatorioUniforme = new javax.swing.JMenuItem();
        menuPopupLogaritmos = new javax.swing.JMenu();
        menuPopupLogaritmoNatural = new javax.swing.JMenuItem();
        menuPopupLogaritmo = new javax.swing.JMenuItem();
        menuPopupQuimica = new javax.swing.JMenu();
        menuPopupMasaElementosNumero = new javax.swing.JMenuItem();
        menuPopupMasaElementosNombreSimbolo = new javax.swing.JMenuItem();
        menuPopupNumeros = new javax.swing.JMenu();
        menuPopupNumeroCero = new javax.swing.JMenuItem();
        menuPopupNumeroUno = new javax.swing.JMenuItem();
        menuPopupNumeroDos = new javax.swing.JMenuItem();
        menuPopupNumeroTres = new javax.swing.JMenuItem();
        menuPopupNumeroCuatro = new javax.swing.JMenuItem();
        menuPopupNumeroCinco = new javax.swing.JMenuItem();
        menuPopupNumeroSeis = new javax.swing.JMenuItem();
        menuPopupNumeroSiete = new javax.swing.JMenuItem();
        menuPopupNumeroOcho = new javax.swing.JMenuItem();
        menuPopupNumeroNueve = new javax.swing.JMenuItem();
        menuPopupPuntoDecimal = new javax.swing.JMenuItem();
        menuPopupNumeroPI = new javax.swing.JMenuItem();
        menuPopupInsertarOperacion = new javax.swing.JMenu();
        menuPopupOperacionSuma = new javax.swing.JMenuItem();
        menuPopupOperacionResta = new javax.swing.JMenuItem();
        menuPopupOperacionMultiplicacion = new javax.swing.JMenuItem();
        menuPopupOperacionDivision = new javax.swing.JMenuItem();
        menuPopupDivisionInversa = new javax.swing.JMenuItem();
        menuPopupPotenciacion = new javax.swing.JMenuItem();
        separadorMenu3 = new javax.swing.JPopupMenu.Separator();
        menuPopupDeshacer = new javax.swing.JMenuItem();
        menuPopupRehacer = new javax.swing.JMenuItem();
        menuPopupEliminarContenido = new javax.swing.JMenuItem();
        separadorMenu4 = new javax.swing.JPopupMenu.Separator();
        menuPopupPreferencias = new javax.swing.JMenuItem();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButton14 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton18 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton49 = new javax.swing.JButton();
        jButton50 = new javax.swing.JButton();
        jButton51 = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        jButton58 = new javax.swing.JButton();
        jButton59 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jButton27 = new javax.swing.JButton();
        jButton29 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton33 = new javax.swing.JButton();
        jButton35 = new javax.swing.JButton();
        jButton36 = new javax.swing.JButton();
        jButton37 = new javax.swing.JButton();
        jButton38 = new javax.swing.JButton();
        jButton40 = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton43 = new javax.swing.JButton();
        jButton44 = new javax.swing.JButton();
        jButton45 = new javax.swing.JButton();
        jButton46 = new javax.swing.JButton();
        jButton48 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        jButton47 = new javax.swing.JButton();
        jButton34 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jButton39 = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        jButton53 = new javax.swing.JButton();
        jButton54 = new javax.swing.JButton();
        jButton55 = new javax.swing.JButton();
        jButton56 = new javax.swing.JButton();
        jButton57 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        jPopupMenu1.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jPopupMenu1.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        jPopupMenu1.setMinimumSize(new java.awt.Dimension(73, 24));

        menuPopupEvaluarExpresion.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupEvaluarExpresion.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupEvaluarExpresion.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupEvaluarExpresion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/equals.png"))); // NOI18N
        menuPopupEvaluarExpresion.setText("Evaluar la expresión");
        menuPopupEvaluarExpresion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupProcesarExpresion(evt);
            }
        });
        jPopupMenu1.add(menuPopupEvaluarExpresion);

        menuPopupGraficarExpresion.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupGraficarExpresion.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupGraficarExpresion.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupGraficarExpresion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Graph.png"))); // NOI18N
        menuPopupGraficarExpresion.setText("Graficar la expresión");
        menuPopupGraficarExpresion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupGraficarExpresion(evt);
            }
        });
        jPopupMenu1.add(menuPopupGraficarExpresion);

        menuPopupAgregarFuncion.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupAgregarFuncion.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAgregarFuncion.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupAgregarFuncion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/new.png"))); // NOI18N
        menuPopupAgregarFuncion.setText("Agregar la función");
        menuPopupAgregarFuncion.setToolTipText("");
        menuPopupAgregarFuncion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupAgregarFuncion(evt);
            }
        });
        jPopupMenu1.add(menuPopupAgregarFuncion);

        separadorMenu1.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        separadorMenu1.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        jPopupMenu1.add(separadorMenu1);

        menuPopupCopiar.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupCopiar.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCopiar.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupCopiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/copiar.png"))); // NOI18N
        menuPopupCopiar.setText("Copiar");
        menuPopupCopiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupCopiar(evt);
            }
        });
        jPopupMenu1.add(menuPopupCopiar);

        menuPopupPegar.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupPegar.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPegar.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupPegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/pegar.png"))); // NOI18N
        menuPopupPegar.setText("Pegar");
        menuPopupPegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupPegar(evt);
            }
        });
        jPopupMenu1.add(menuPopupPegar);

        menuPopupCortar.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupCortar.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCortar.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupCortar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/cortar.png"))); // NOI18N
        menuPopupCortar.setText("Cortar");
        menuPopupCortar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupCortar(evt);
            }
        });
        jPopupMenu1.add(menuPopupCortar);

        separadorMenu2.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        separadorMenu2.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        jPopupMenu1.add(separadorMenu2);

        menuPopupFunciones.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupFunciones.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupFunciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/selectfuncion.png"))); // NOI18N
        menuPopupFunciones.setText("Insertar función");
        menuPopupFunciones.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupSeparadorArgumento.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupSeparadorArgumento.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupSeparadorArgumento.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupSeparadorArgumento.setText(",");
        menuPopupSeparadorArgumento.setToolTipText("");
        menuPopupSeparadorArgumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupSeparadorArgumentoFuncion(evt);
            }
        });
        menuPopupFunciones.add(menuPopupSeparadorArgumento);

        menuPopupParentesisIzquierdo.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupParentesisIzquierdo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupParentesisIzquierdo.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupParentesisIzquierdo.setText("(");
        menuPopupParentesisIzquierdo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupParentesisIzquierdo(evt);
            }
        });
        menuPopupFunciones.add(menuPopupParentesisIzquierdo);

        menuPopupParentesisDerecho.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupParentesisDerecho.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupParentesisDerecho.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupParentesisDerecho.setText(")");
        menuPopupParentesisDerecho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupParentesisDerecho(evt);
            }
        });
        menuPopupFunciones.add(menuPopupParentesisDerecho);

        menuPopupParentesis.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupParentesis.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupParentesis.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupParentesis.setText("( )");
        menuPopupParentesis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupParentesis(evt);
            }
        });
        menuPopupFunciones.add(menuPopupParentesis);

        jSeparator4.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jSeparator4.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupFunciones.add(jSeparator4);

        jSeparator13.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jSeparator13.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupFunciones.add(jSeparator13);

        jSeparator5.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jSeparator5.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupFunciones.add(jSeparator5);

        menuPopupFuncionesGeometricas.setText("Funciones Geométricas");
        menuPopupFuncionesGeometricas.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupVolumen.setText("Volumen");
        menuPopupVolumen.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupVolPrismaRegular.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupVolPrismaRegular.setText("Prisma Regular");
        menuPopupVolPrismaRegular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupVolPrismaRegular(evt);
            }
        });
        menuPopupVolumen.add(menuPopupVolPrismaRegular);

        menuPopupVolOrtoedro.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupVolOrtoedro.setText("Ortoedro");
        menuPopupVolOrtoedro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupVolOrtoedro(evt);
            }
        });
        menuPopupVolumen.add(menuPopupVolOrtoedro);

        menuPopupVolCubo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupVolCubo.setText("Cubo");
        menuPopupVolCubo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupVolCubo(evt);
            }
        });
        menuPopupVolumen.add(menuPopupVolCubo);

        menuPopupVolCilindro.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupVolCilindro.setText("Cilindro");
        menuPopupVolCilindro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupVolCilindro(evt);
            }
        });
        menuPopupVolumen.add(menuPopupVolCilindro);

        menuPopupVolEsfera.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupVolEsfera.setText("Esfera");
        menuPopupVolEsfera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupVolEsfera(evt);
            }
        });
        menuPopupVolumen.add(menuPopupVolEsfera);

        menuPopupVolElipsoide.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupVolElipsoide.setText("Elipsiode");
        menuPopupVolElipsoide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupVolElipsoide(evt);
            }
        });
        menuPopupVolumen.add(menuPopupVolElipsoide);

        menuPopupVolPiramide.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupVolPiramide.setText("Pirámide");
        menuPopupVolPiramide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupVolPiramide(evt);
            }
        });
        menuPopupVolumen.add(menuPopupVolPiramide);

        menuPopupVolCono.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupVolCono.setText("Cono");
        menuPopupVolCono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupVolCono(evt);
            }
        });
        menuPopupVolumen.add(menuPopupVolCono);

        menuPopupFuncionesGeometricas.add(menuPopupVolumen);

        menuPopupArea.setText("Área");
        menuPopupArea.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupAreaTriangulo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAreaTriangulo.setText("Triángulo");
        menuPopupAreaTriangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAreaTriangulo(evt);
            }
        });
        menuPopupArea.add(menuPopupAreaTriangulo);

        menuPopupAreaTrianguloHeron.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAreaTrianguloHeron.setText("Triángulo Herón");
        menuPopupAreaTrianguloHeron.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAreaTrianguloHeron(evt);
            }
        });
        menuPopupArea.add(menuPopupAreaTrianguloHeron);

        menuPopupAreaCuadrilatero.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAreaCuadrilatero.setText("Cuadrilátero");
        menuPopupAreaCuadrilatero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAreaCuadrilatero(evt);
            }
        });
        menuPopupArea.add(menuPopupAreaCuadrilatero);

        menuPopupAreaRombo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAreaRombo.setText("Rombo");
        menuPopupAreaRombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAreaRombo(evt);
            }
        });
        menuPopupArea.add(menuPopupAreaRombo);

        menuPopupAreaRectangulo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAreaRectangulo.setText("Rectángulo");
        menuPopupAreaRectangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAreaRectangulo(evt);
            }
        });
        menuPopupArea.add(menuPopupAreaRectangulo);

        menuPopupAreaCuadrado.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAreaCuadrado.setText("Cuadrado");
        menuPopupAreaCuadrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAreaCuadrado(evt);
            }
        });
        menuPopupArea.add(menuPopupAreaCuadrado);

        menuPopupAreaRomboide.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAreaRomboide.setText("Romboide");
        menuPopupAreaRomboide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAreaRomboide(evt);
            }
        });
        menuPopupArea.add(menuPopupAreaRomboide);

        menuPopupAreaTrapecio.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAreaTrapecio.setText("Trapecio");
        menuPopupAreaTrapecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAreaTrapecio(evt);
            }
        });
        menuPopupArea.add(menuPopupAreaTrapecio);

        menuPopupAreaCirculo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAreaCirculo.setText("Círculo");
        menuPopupAreaCirculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAreaCirculo(evt);
            }
        });
        menuPopupArea.add(menuPopupAreaCirculo);

        menuPopupAreaElipse.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAreaElipse.setText("Elipse");
        menuPopupAreaElipse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAreaElipse(evt);
            }
        });
        menuPopupArea.add(menuPopupAreaElipse);

        menuPopupAreaEsfera.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAreaEsfera.setText("Esfera");
        menuPopupAreaEsfera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAreaEsfera(evt);
            }
        });
        menuPopupArea.add(menuPopupAreaEsfera);

        menuPopupAreaCono.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAreaCono.setText("Cono");
        menuPopupAreaCono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAreaCono(evt);
            }
        });
        menuPopupArea.add(menuPopupAreaCono);

        menuPopupAreaCilindro.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAreaCilindro.setText("Cilindro");
        menuPopupAreaCilindro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAreaCilindro(evt);
            }
        });
        menuPopupArea.add(menuPopupAreaCilindro);

        menuPopupFuncionesGeometricas.add(menuPopupArea);

        menuPopupDistancia.setText("Distancia");
        menuPopupDistancia.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupDistanciaPuntoRecta.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupDistanciaPuntoRecta.setText("Punto a Recta");
        menuPopupDistanciaPuntoRecta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupDistanciaPuntoRecta(evt);
            }
        });
        menuPopupDistancia.add(menuPopupDistanciaPuntoRecta);

        menuPopupDistanciaPuntoPlano.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupDistanciaPuntoPlano.setText("Punto a Rlano");
        menuPopupDistanciaPuntoPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupDistanciaPuntoPlano(evt);
            }
        });
        menuPopupDistancia.add(menuPopupDistanciaPuntoPlano);

        menuPopupDistanciaRectaRecta.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupDistanciaRectaRecta.setText("Recta a Recta");
        menuPopupDistanciaRectaRecta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupDistanciaRectaRecta(evt);
            }
        });
        menuPopupDistancia.add(menuPopupDistanciaRectaRecta);

        menuPopupDistanciaRectaPlano.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupDistanciaRectaPlano.setText("Recta a Plano");
        menuPopupDistanciaRectaPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupDistanciaRectaPlano(evt);
            }
        });
        menuPopupDistancia.add(menuPopupDistanciaRectaPlano);

        menuPopupDistanciaPlanoPlano.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupDistanciaPlanoPlano.setText("Plano a Plano");
        menuPopupDistanciaPlanoPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupDistanciaPlanoPlano(evt);
            }
        });
        menuPopupDistancia.add(menuPopupDistanciaPlanoPlano);

        menuPopupDistanciaPuntoPunto.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupDistanciaPuntoPunto.setText("Punto a Punto");
        menuPopupDistanciaPuntoPunto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupDistanciaPuntoPunto(evt);
            }
        });
        menuPopupDistancia.add(menuPopupDistanciaPuntoPunto);

        menuPopupFuncionesGeometricas.add(menuPopupDistancia);

        menuPopupPerimetro.setText("Perímetro");
        menuPopupPerimetro.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupPerimetroCuadrado.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPerimetroCuadrado.setText("Cuadrado");
        menuPopupPerimetroCuadrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupPerimetroCuadrado(evt);
            }
        });
        menuPopupPerimetro.add(menuPopupPerimetroCuadrado);

        menuPopupPerimetroRectangulo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPerimetroRectangulo.setText("Rectángulo");
        menuPopupPerimetroRectangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupPerimetroRectangulo(evt);
            }
        });
        menuPopupPerimetro.add(menuPopupPerimetroRectangulo);

        menuPopupPerimetroRombo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPerimetroRombo.setText("Rombo");
        menuPopupPerimetroRombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupPerimetroRombo(evt);
            }
        });
        menuPopupPerimetro.add(menuPopupPerimetroRombo);

        menuPopupPerimetroTriangulo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPerimetroTriangulo.setText("Triángulo");
        menuPopupPerimetroTriangulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupPerimetroTriangulo(evt);
            }
        });
        menuPopupPerimetro.add(menuPopupPerimetroTriangulo);

        menuPopupPerimetroRomboide.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPerimetroRomboide.setText("Romboide");
        menuPopupPerimetroRomboide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupPerimetroRomboide(evt);
            }
        });
        menuPopupPerimetro.add(menuPopupPerimetroRomboide);

        menuPopupPerimetroCirculo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPerimetroCirculo.setText("Círculo");
        menuPopupPerimetroCirculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupPerimetroCirculo(evt);
            }
        });
        menuPopupPerimetro.add(menuPopupPerimetroCirculo);

        menuPopupPerimetroElipse.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPerimetroElipse.setText("Elipse");
        menuPopupPerimetroElipse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupPerimetroElipse(evt);
            }
        });
        menuPopupPerimetro.add(menuPopupPerimetroElipse);

        menuPopupFuncionesGeometricas.add(menuPopupPerimetro);

        menuPopupFunciones.add(menuPopupFuncionesGeometricas);

        menuPopupFuncionesEstadísticas.setText("Funciones Estadísticas");
        menuPopupFuncionesEstadísticas.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupCentralizacion.setText("Posición Central");
        menuPopupCentralizacion.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupPromedio.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPromedio.setText("Promedio");
        menuPopupPromedio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupPromedio(evt);
            }
        });
        menuPopupCentralizacion.add(menuPopupPromedio);

        menuPopupModa.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupModa.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupModa.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupModa.setText("Moda");
        menuPopupModa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupModa(evt);
            }
        });
        menuPopupCentralizacion.add(menuPopupModa);

        menuPopupMediaGeometrica.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMediaGeometrica.setText("Media Geométrica");
        menuPopupMediaGeometrica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupMediaGeometrica(evt);
            }
        });
        menuPopupCentralizacion.add(menuPopupMediaGeometrica);

        menuPopupMediaArmonica.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMediaArmonica.setText("Media Armonica");
        menuPopupMediaArmonica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupMediaArmonica(evt);
            }
        });
        menuPopupCentralizacion.add(menuPopupMediaArmonica);

        menuPopupMediana.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMediana.setText("Mediana");
        menuPopupMediana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupMediana(evt);
            }
        });
        menuPopupCentralizacion.add(menuPopupMediana);

        menuPopupFuncionesEstadísticas.add(menuPopupCentralizacion);

        menuPopupPosicionNoCentral.setText("Posición No Central");
        menuPopupPosicionNoCentral.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupPercentil.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPercentil.setText("Percentil");
        menuPopupPercentil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupPercentil(evt);
            }
        });
        menuPopupPosicionNoCentral.add(menuPopupPercentil);

        menuPopupCuartil.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCuartil.setText("Cuartil");
        menuPopupCuartil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupCuartil(evt);
            }
        });
        menuPopupPosicionNoCentral.add(menuPopupCuartil);

        menuPopupDecil.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupDecil.setText("Decil");
        menuPopupDecil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupDecil(evt);
            }
        });
        menuPopupPosicionNoCentral.add(menuPopupDecil);

        menuPopupFuncionesEstadísticas.add(menuPopupPosicionNoCentral);

        menuPopupFrecuencia.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupFrecuencia.setText("Frecuencia");
        menuPopupFrecuencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupFrecuencia(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupFrecuencia);

        menuPopupMinimoDatos.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMinimoDatos.setText("Mínimo(datos)");
        menuPopupMinimoDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupMinimoDatos(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupMinimoDatos);

        menuPopupMaximoDatos.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMaximoDatos.setText("Máximo(datos)");
        menuPopupMaximoDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupMaximoDatos(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupMaximoDatos);
        menuPopupFuncionesEstadísticas.add(jSeparator1);

        menuPopupCurtosisFisher.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCurtosisFisher.setText("Curtosis Fisher");
        menuPopupCurtosisFisher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupCurtosisFisher(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupCurtosisFisher);
        menuPopupFuncionesEstadísticas.add(jSeparator2);

        menuPopupDesviacionEstandar.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupDesviacionEstandar.setText("Desviación Estándar");
        menuPopupDesviacionEstandar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupDesviacionEstandar(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupDesviacionEstandar);

        menuPopupVarianza.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupVarianza.setText("Varianza");
        menuPopupVarianza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupVarianza(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupVarianza);

        menuPopupCuasiVarianza.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCuasiVarianza.setText("CuasiVarianza");
        menuPopupCuasiVarianza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupCuasiVarianza(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupCuasiVarianza);
        menuPopupFuncionesEstadísticas.add(jSeparator3);

        menuPopupCVPearson.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCVPearson.setText("C. Variación Pearson");
        menuPopupCVPearson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupCVPearson(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupCVPearson);
        menuPopupFuncionesEstadísticas.add(jSeparator14);

        menuPopupCApertura.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCApertura.setText("C. Apertura");
        menuPopupCApertura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupCApertura(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupCApertura);
        menuPopupFuncionesEstadísticas.add(jSeparator15);

        menuPopupRangoRelativo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupRangoRelativo.setText("Rango Relativo");
        menuPopupRangoRelativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupRangoRelativo(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupRangoRelativo);

        menuPopupRICR.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupRICR.setText("RICR");
        menuPopupRICR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupRICR(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupRICR);

        menuPopupRSICR.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupRSICR.setText("RSICR");
        menuPopupRSICR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupRSICR(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupRSICR);
        menuPopupFuncionesEstadísticas.add(jSeparator17);

        menuPopupCABowley.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCABowley.setText("C. Asim. Bowley");
        menuPopupCABowley.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupCABowley(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupCABowley);

        menuPopupCAPearson.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCAPearson.setText("C. Asim. Pearson");
        menuPopupCAPearson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupCAPearson(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupCAPearson);
        menuPopupFuncionesEstadísticas.add(jSeparator18);

        menuPopupPermutaciones.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupPermutaciones.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPermutaciones.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupPermutaciones.setText("Permutaciones");
        menuPopupPermutaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupNPR(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupPermutaciones);

        menuPopupCombinaciones.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupCombinaciones.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCombinaciones.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupCombinaciones.setText("Combinaciones");
        menuPopupCombinaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupNCR(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupCombinaciones);

        menuPopupContar.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupContar.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupContar.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupContar.setText("Contar");
        menuPopupContar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupContar(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupContar);

        menuPopupContarSi.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupContarSi.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupContarSi.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupContarSi.setText("ContarSi");
        menuPopupContarSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupContarSi(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupContarSi);
        menuPopupFuncionesEstadísticas.add(jSeparator19);

        menuPopupSuma.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupSuma.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupSuma.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupSuma.setText("Suma");
        menuPopupSuma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupSuma(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupSuma);

        menuPopupSumaProducto.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupSumaProducto.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupSumaProducto.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupSumaProducto.setText("Suma Producto");
        menuPopupSumaProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupSumaProducto(evt);
            }
        });
        menuPopupFuncionesEstadísticas.add(menuPopupSumaProducto);

        menuPopupFunciones.add(menuPopupFuncionesEstadísticas);

        menuPopupFuncionesSalud.setText("Funciones de Salud");
        menuPopupFuncionesSalud.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupIMC.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupIMC.setText("IMC");
        menuPopupIMC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupIMC(evt);
            }
        });
        menuPopupFuncionesSalud.add(menuPopupIMC);

        menuPopupLMC.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupLMC.setText("LMC");
        menuPopupLMC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupLMC(evt);
            }
        });
        menuPopupFuncionesSalud.add(menuPopupLMC);

        menuPopupFunciones.add(menuPopupFuncionesSalud);

        menuPopupFuncionesTiempo.setText("Funciones de Tiempo");
        menuPopupFuncionesTiempo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupSegundosHoras.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupSegundosHoras.setText("C. Segundos a Horas");
        menuPopupSegundosHoras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupSegundosHoras(evt);
            }
        });
        menuPopupFuncionesTiempo.add(menuPopupSegundosHoras);

        menuPopupSegundosMinutos.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupSegundosMinutos.setText("C. Segundos a Minutos");
        menuPopupSegundosMinutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupSegundosMinutos(evt);
            }
        });
        menuPopupFuncionesTiempo.add(menuPopupSegundosMinutos);

        menuPopupHorasSegundos.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupHorasSegundos.setText("C. Horas a Segundos");
        menuPopupHorasSegundos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupHorasSegundos(evt);
            }
        });
        menuPopupFuncionesTiempo.add(menuPopupHorasSegundos);

        menuPopupMinutosSegundos.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMinutosSegundos.setText("C. Minutos a Segundos");
        menuPopupMinutosSegundos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupMinutosSegundos(evt);
            }
        });
        menuPopupFuncionesTiempo.add(menuPopupMinutosSegundos);

        menuPopupMilisegundosSegundos.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMilisegundosSegundos.setText("C. Milisegundos a Segundos");
        menuPopupMilisegundosSegundos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupMilisegundosSegundos(evt);
            }
        });
        menuPopupFuncionesTiempo.add(menuPopupMilisegundosSegundos);

        menuPopupSegundosMilisegundos.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupSegundosMilisegundos.setText("C. Segundos a Milisegundos");
        menuPopupSegundosMilisegundos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupSegundosMilisegundos(evt);
            }
        });
        menuPopupFuncionesTiempo.add(menuPopupSegundosMilisegundos);

        menuPopupFunciones.add(menuPopupFuncionesTiempo);

        menuPopupTrigonometricas.setText("Trigonométricas");
        menuPopupTrigonometricas.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupSeno.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupSeno.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupSeno.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupSeno.setText("sen(x)");
        menuPopupSeno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupSeno(evt);
            }
        });
        menuPopupTrigonometricas.add(menuPopupSeno);

        menuPopupCoseno.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupCoseno.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCoseno.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupCoseno.setText("cos(x)");
        menuPopupCoseno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupCoseno(evt);
            }
        });
        menuPopupTrigonometricas.add(menuPopupCoseno);

        menuPopupTangente.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupTangente.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupTangente.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupTangente.setText("tan(x)");
        menuPopupTangente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupTangente(evt);
            }
        });
        menuPopupTrigonometricas.add(menuPopupTangente);

        jSeparator6.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jSeparator6.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupTrigonometricas.add(jSeparator6);

        menuPopupCosecante.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupCosecante.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCosecante.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupCosecante.setText("csc(x)");
        menuPopupCosecante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupCosecante(evt);
            }
        });
        menuPopupTrigonometricas.add(menuPopupCosecante);

        menuPopupSecante.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupSecante.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupSecante.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupSecante.setText("sec(x)");
        menuPopupSecante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupSecante(evt);
            }
        });
        menuPopupTrigonometricas.add(menuPopupSecante);

        menuPopupCotangente.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupCotangente.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCotangente.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupCotangente.setText("cot(x)");
        menuPopupCotangente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupCotangente(evt);
            }
        });
        menuPopupTrigonometricas.add(menuPopupCotangente);

        jSeparator7.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jSeparator7.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupTrigonometricas.add(jSeparator7);

        menuPopupArcoSeno.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupArcoSeno.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupArcoSeno.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupArcoSeno.setText("asen(x)");
        menuPopupArcoSeno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupArcoseno(evt);
            }
        });
        menuPopupTrigonometricas.add(menuPopupArcoSeno);

        menuPopupArcoCoseno.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupArcoCoseno.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupArcoCoseno.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupArcoCoseno.setText("acos(x)");
        menuPopupArcoCoseno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupArcocoseno(evt);
            }
        });
        menuPopupTrigonometricas.add(menuPopupArcoCoseno);

        menuPopupArcoTangente.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupArcoTangente.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupArcoTangente.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupArcoTangente.setText("atan(x)");
        menuPopupArcoTangente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupArcotangente(evt);
            }
        });
        menuPopupTrigonometricas.add(menuPopupArcoTangente);

        jSeparator8.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jSeparator8.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupTrigonometricas.add(jSeparator8);

        menuPopupArcoCosecante.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupArcoCosecante.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupArcoCosecante.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupArcoCosecante.setText("acsc(x)");
        menuPopupArcoCosecante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupArcoCosecante(evt);
            }
        });
        menuPopupTrigonometricas.add(menuPopupArcoCosecante);

        menuPopupArcoSecante.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupArcoSecante.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupArcoSecante.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupArcoSecante.setText("asec(x)");
        menuPopupArcoSecante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupArcosecante(evt);
            }
        });
        menuPopupTrigonometricas.add(menuPopupArcoSecante);

        menuPopupArcoCotangente.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupArcoCotangente.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupArcoCotangente.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupArcoCotangente.setText("acot(x)");
        menuPopupArcoCotangente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupArcocotangente(evt);
            }
        });
        menuPopupTrigonometricas.add(menuPopupArcoCotangente);

        menuPopupFunciones.add(menuPopupTrigonometricas);

        menuPopupHiperbolicas.setText("Hiperbólicas");
        menuPopupHiperbolicas.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupSenoHiperbolico.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupSenoHiperbolico.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupSenoHiperbolico.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupSenoHiperbolico.setText("senh(x)");
        menuPopupSenoHiperbolico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupSenoHiperbolico(evt);
            }
        });
        menuPopupHiperbolicas.add(menuPopupSenoHiperbolico);

        menuPopupCosenoHiperbolico.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupCosenoHiperbolico.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCosenoHiperbolico.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupCosenoHiperbolico.setText("cosh(x)");
        menuPopupCosenoHiperbolico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupCosenoHiperbolico(evt);
            }
        });
        menuPopupHiperbolicas.add(menuPopupCosenoHiperbolico);

        menuPopupTangenteHiperbolica.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupTangenteHiperbolica.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupTangenteHiperbolica.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupTangenteHiperbolica.setText("tanh(x)");
        menuPopupTangenteHiperbolica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupTangenteHiperbolica(evt);
            }
        });
        menuPopupHiperbolicas.add(menuPopupTangenteHiperbolica);

        menuPopupFunciones.add(menuPopupHiperbolicas);

        menuPopupExponenciales.setText("Exponenciales");
        menuPopupExponenciales.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupDivisionUnoDivididoX.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupDivisionUnoDivididoX.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupDivisionUnoDivididoX.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupDivisionUnoDivididoX.setText("1 / x");
        menuPopupDivisionUnoDivididoX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupUnoSobre(evt);
            }
        });
        menuPopupExponenciales.add(menuPopupDivisionUnoDivididoX);

        menuPopupPotenciacionRaizCuadrada.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupPotenciacionRaizCuadrada.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPotenciacionRaizCuadrada.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupPotenciacionRaizCuadrada.setText("x ^ ½");
        menuPopupPotenciacionRaizCuadrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupRaizCuadrada(evt);
            }
        });
        menuPopupExponenciales.add(menuPopupPotenciacionRaizCuadrada);

        menuPopupPotenciacionRaizCubica.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupPotenciacionRaizCubica.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPotenciacionRaizCubica.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupPotenciacionRaizCubica.setText("x ^ ⅓ ");
        menuPopupPotenciacionRaizCubica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupRaizCubica(evt);
            }
        });
        menuPopupExponenciales.add(menuPopupPotenciacionRaizCubica);

        menuPopupPotenciacionElevarCuadrado.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupPotenciacionElevarCuadrado.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPotenciacionElevarCuadrado.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupPotenciacionElevarCuadrado.setText("x ^ 2");
        menuPopupPotenciacionElevarCuadrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupCuadrado(evt);
            }
        });
        menuPopupExponenciales.add(menuPopupPotenciacionElevarCuadrado);

        menuPopupPotenciacionElevarCubo.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupPotenciacionElevarCubo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPotenciacionElevarCubo.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupPotenciacionElevarCubo.setText("x ^ 3");
        menuPopupPotenciacionElevarCubo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupCubo(evt);
            }
        });
        menuPopupExponenciales.add(menuPopupPotenciacionElevarCubo);

        jSeparator10.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jSeparator10.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupExponenciales.add(jSeparator10);

        menuPopupPotenciacionDosElevadoX.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupPotenciacionDosElevadoX.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPotenciacionDosElevadoX.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupPotenciacionDosElevadoX.setText("2 ^ x");
        menuPopupPotenciacionDosElevadoX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupDosElevadoA(evt);
            }
        });
        menuPopupExponenciales.add(menuPopupPotenciacionDosElevadoX);

        menuPopupPotenciacionExponencial.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupPotenciacionExponencial.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPotenciacionExponencial.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupPotenciacionExponencial.setText("e ^ x");
        menuPopupPotenciacionExponencial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupEElevadoA(evt);
            }
        });
        menuPopupExponenciales.add(menuPopupPotenciacionExponencial);

        menuPopupPotenciacionDiezElevadoX.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupPotenciacionDiezElevadoX.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPotenciacionDiezElevadoX.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupPotenciacionDiezElevadoX.setText("10 ^ x");
        menuPopupPotenciacionDiezElevadoX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupDiezElevadoA(evt);
            }
        });
        menuPopupExponenciales.add(menuPopupPotenciacionDiezElevadoX);

        menuPopupFunciones.add(menuPopupExponenciales);

        menuPopupMetodosNumericos.setText("Métodos Numéricos");
        menuPopupMetodosNumericos.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupPrimos.setText("Primos");
        menuPopupPrimos.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupEsPrimo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupEsPrimo.setText("Test Primalidad");
        menuPopupEsPrimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupEsPrimo(evt);
            }
        });
        menuPopupPrimos.add(menuPopupEsPrimo);

        menuPopupConteoPrimos.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupConteoPrimos.setText("Contar Primos");
        menuPopupConteoPrimos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupConteoPrimos(evt);
            }
        });
        menuPopupPrimos.add(menuPopupConteoPrimos);

        menuPopupMenorPrimoMayorQue.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMenorPrimoMayorQue.setText("Primo Siguiente");
        menuPopupMenorPrimoMayorQue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupMenorPrimoMayorQue(evt);
            }
        });
        menuPopupPrimos.add(menuPopupMenorPrimoMayorQue);

        menuPopupMetodosNumericos.add(menuPopupPrimos);

        menuPopupMinimoFuncion.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupMinimoFuncion.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMinimoFuncion.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupMinimoFuncion.setText("mínimo(f(x), a, b)");
        menuPopupMinimoFuncion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupMinimo(evt);
            }
        });
        menuPopupMetodosNumericos.add(menuPopupMinimoFuncion);

        menuPopupMaximoFuncion.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupMaximoFuncion.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMaximoFuncion.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupMaximoFuncion.setText("máximo(f(x), a, b)");
        menuPopupMaximoFuncion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupMaximo(evt);
            }
        });
        menuPopupMetodosNumericos.add(menuPopupMaximoFuncion);

        menuPopupFuncionesDerivacion.setText("Funciones de Derivación");
        menuPopupFuncionesDerivacion.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupDerivacionUnaVariable.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupDerivacionUnaVariable.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupDerivacionUnaVariable.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupDerivacionUnaVariable.setText("derivar(f(x), b)");
        menuPopupDerivacionUnaVariable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupDerivar(evt);
            }
        });
        menuPopupFuncionesDerivacion.add(menuPopupDerivacionUnaVariable);

        menuPopupSegundaDerivada.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupSegundaDerivada.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupSegundaDerivada.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupSegundaDerivada.setText("Segunda Derivada(f(x), b)");
        menuPopupSegundaDerivada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupSegundaDerivadapopupDerivar(evt);
            }
        });
        menuPopupFuncionesDerivacion.add(menuPopupSegundaDerivada);

        menuPopupTerceraDerivada.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupTerceraDerivada.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupTerceraDerivada.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupTerceraDerivada.setText("Tercera Derivada(f(x), b)");
        menuPopupTerceraDerivada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupTerceraDerivadapopupDerivar(evt);
            }
        });
        menuPopupFuncionesDerivacion.add(menuPopupTerceraDerivada);

        menuPopupCuartaDerivada.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupCuartaDerivada.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupCuartaDerivada.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupCuartaDerivada.setText("Cuarta Derivada(f(x), b)");
        menuPopupCuartaDerivada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupCuartaDerivadapopupDerivar(evt);
            }
        });
        menuPopupFuncionesDerivacion.add(menuPopupCuartaDerivada);

        menuPopupMetodosNumericos.add(menuPopupFuncionesDerivacion);

        menuPopupIntegracion.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupIntegracion.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupIntegracion.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupIntegracion.setText("integrar(f(x), a, b)");
        menuPopupIntegracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupIntegrar(evt);
            }
        });
        menuPopupMetodosNumericos.add(menuPopupIntegracion);

        jSeparator11.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jSeparator11.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupMetodosNumericos.add(jSeparator11);

        menuPopupFactorial.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupFactorial.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupFactorial.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupFactorial.setText("Factorial");
        menuPopupFactorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupFactorial(evt);
            }
        });
        menuPopupMetodosNumericos.add(menuPopupFactorial);

        menuPopupRedondeoEntero.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupRedondeoEntero.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupRedondeoEntero.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupRedondeoEntero.setText("Redondeo Entero");
        menuPopupRedondeoEntero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupRedondeoEntero(evt);
            }
        });
        menuPopupMetodosNumericos.add(menuPopupRedondeoEntero);

        menuPopupHipotenusa.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupHipotenusa.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupHipotenusa.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupHipotenusa.setText("Hipotenusa");
        menuPopupHipotenusa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupHipotenusa(evt);
            }
        });
        menuPopupMetodosNumericos.add(menuPopupHipotenusa);

        menuPopupResiduo.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupResiduo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupResiduo.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupResiduo.setText("Residuo");
        menuPopupResiduo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupResiduo(evt);
            }
        });
        menuPopupMetodosNumericos.add(menuPopupResiduo);

        jSeparator9.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jSeparator9.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupMetodosNumericos.add(jSeparator9);

        menuPopupMCM.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupMCM.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMCM.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupMCM.setText("Máximo Común Múltiplo");
        menuPopupMCM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupGCD(evt);
            }
        });
        menuPopupMetodosNumericos.add(menuPopupMCM);

        menuPopupMCD.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupMCD.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMCD.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupMCD.setText("Máximo Común Divisor");
        menuPopupMCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupLCM(evt);
            }
        });
        menuPopupMetodosNumericos.add(menuPopupMCD);

        jSeparator12.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jSeparator12.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupMetodosNumericos.add(jSeparator12);

        menuPopupIgualarACeroFuncion.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupIgualarACeroFuncion.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupIgualarACeroFuncion.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupIgualarACeroFuncion.setText("f(x) = 0");
        menuPopupIgualarACeroFuncion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupCalcularCerosFuncion(evt);
            }
        });
        menuPopupMetodosNumericos.add(menuPopupIgualarACeroFuncion);

        menuPopupAleatorio.setText("Aleatorios");
        menuPopupAleatorio.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupAleatorioGaussiano.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupAleatorioGaussiano.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAleatorioGaussiano.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupAleatorioGaussiano.setText("Gaussiano");
        menuPopupAleatorioGaussiano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAleatorioGaussiano(evt);
            }
        });
        menuPopupAleatorio.add(menuPopupAleatorioGaussiano);

        menuPopupAleatorioUniforme.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupAleatorioUniforme.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupAleatorioUniforme.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupAleatorioUniforme.setText("Uniforme");
        menuPopupAleatorioUniforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupAleatorioUniforme(evt);
            }
        });
        menuPopupAleatorio.add(menuPopupAleatorioUniforme);

        menuPopupMetodosNumericos.add(menuPopupAleatorio);

        menuPopupFunciones.add(menuPopupMetodosNumericos);

        menuPopupLogaritmos.setText("Logaritmos");
        menuPopupLogaritmos.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupLogaritmoNatural.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupLogaritmoNatural.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupLogaritmoNatural.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupLogaritmoNatural.setText("Logaritmo Natural");
        menuPopupLogaritmoNatural.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupLogaritmoNatural(evt);
            }
        });
        menuPopupLogaritmos.add(menuPopupLogaritmoNatural);

        menuPopupLogaritmo.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupLogaritmo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupLogaritmo.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupLogaritmo.setText("Logaritmo(x, y)");
        menuPopupLogaritmo.setToolTipText("");
        menuPopupLogaritmo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupLogaritmo(evt);
            }
        });
        menuPopupLogaritmos.add(menuPopupLogaritmo);

        menuPopupFunciones.add(menuPopupLogaritmos);

        menuPopupQuimica.setText("Química");
        menuPopupQuimica.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupMasaElementosNumero.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMasaElementosNumero.setText("Masa Elemento(número)");
        menuPopupMasaElementosNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupMasaElementosNumero(evt);
            }
        });
        menuPopupQuimica.add(menuPopupMasaElementosNumero);

        menuPopupMasaElementosNombreSimbolo.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupMasaElementosNombreSimbolo.setText("Masa Elemento(Nombre o Símbolo)");
        menuPopupMasaElementosNombreSimbolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuPopupMasaElementosNombreSimbolo(evt);
            }
        });
        menuPopupQuimica.add(menuPopupMasaElementosNombreSimbolo);

        menuPopupFunciones.add(menuPopupQuimica);

        jPopupMenu1.add(menuPopupFunciones);

        menuPopupNumeros.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupNumeros.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupNumeros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/selectnumber.png"))); // NOI18N
        menuPopupNumeros.setText("Insertar número");
        menuPopupNumeros.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupNumeroCero.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupNumeroCero.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupNumeroCero.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupNumeroCero.setText("0");
        menuPopupNumeroCero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupCero(evt);
            }
        });
        menuPopupNumeros.add(menuPopupNumeroCero);

        menuPopupNumeroUno.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupNumeroUno.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupNumeroUno.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupNumeroUno.setText("1");
        menuPopupNumeroUno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupUno(evt);
            }
        });
        menuPopupNumeros.add(menuPopupNumeroUno);

        menuPopupNumeroDos.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupNumeroDos.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupNumeroDos.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupNumeroDos.setText("2");
        menuPopupNumeroDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupDos(evt);
            }
        });
        menuPopupNumeros.add(menuPopupNumeroDos);

        menuPopupNumeroTres.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupNumeroTres.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupNumeroTres.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupNumeroTres.setText("3");
        menuPopupNumeroTres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupTres(evt);
            }
        });
        menuPopupNumeros.add(menuPopupNumeroTres);

        menuPopupNumeroCuatro.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupNumeroCuatro.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupNumeroCuatro.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupNumeroCuatro.setText("4");
        menuPopupNumeroCuatro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupCuatro(evt);
            }
        });
        menuPopupNumeros.add(menuPopupNumeroCuatro);

        menuPopupNumeroCinco.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupNumeroCinco.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupNumeroCinco.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupNumeroCinco.setText("5");
        menuPopupNumeroCinco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupCinco(evt);
            }
        });
        menuPopupNumeros.add(menuPopupNumeroCinco);

        menuPopupNumeroSeis.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupNumeroSeis.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupNumeroSeis.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupNumeroSeis.setText("6");
        menuPopupNumeroSeis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupSeis(evt);
            }
        });
        menuPopupNumeros.add(menuPopupNumeroSeis);

        menuPopupNumeroSiete.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupNumeroSiete.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupNumeroSiete.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupNumeroSiete.setText("7");
        menuPopupNumeroSiete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupSiete(evt);
            }
        });
        menuPopupNumeros.add(menuPopupNumeroSiete);

        menuPopupNumeroOcho.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupNumeroOcho.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupNumeroOcho.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupNumeroOcho.setText("8");
        menuPopupNumeroOcho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupOcho(evt);
            }
        });
        menuPopupNumeros.add(menuPopupNumeroOcho);

        menuPopupNumeroNueve.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupNumeroNueve.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupNumeroNueve.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupNumeroNueve.setText("9");
        menuPopupNumeroNueve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupNueve(evt);
            }
        });
        menuPopupNumeros.add(menuPopupNumeroNueve);

        menuPopupPuntoDecimal.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupPuntoDecimal.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupPuntoDecimal.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupPuntoDecimal.setText(".");
        menuPopupPuntoDecimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupPuntoDecimal(evt);
            }
        });
        menuPopupNumeros.add(menuPopupPuntoDecimal);

        menuPopupNumeroPI.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupNumeroPI.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupNumeroPI.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupNumeroPI.setText("PI");
        menuPopupNumeroPI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupPI(evt);
            }
        });
        menuPopupNumeros.add(menuPopupNumeroPI);

        jPopupMenu1.add(menuPopupNumeros);

        menuPopupInsertarOperacion.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupInsertarOperacion.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupInsertarOperacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/selectoperator.png"))); // NOI18N
        menuPopupInsertarOperacion.setText("Insertar operación");
        menuPopupInsertarOperacion.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N

        menuPopupOperacionSuma.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupOperacionSuma.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupOperacionSuma.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupOperacionSuma.setText("+");
        menuPopupOperacionSuma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupSuma(evt);
            }
        });
        menuPopupInsertarOperacion.add(menuPopupOperacionSuma);

        menuPopupOperacionResta.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupOperacionResta.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupOperacionResta.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupOperacionResta.setText("-");
        menuPopupOperacionResta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupResta(evt);
            }
        });
        menuPopupInsertarOperacion.add(menuPopupOperacionResta);

        menuPopupOperacionMultiplicacion.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupOperacionMultiplicacion.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupOperacionMultiplicacion.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupOperacionMultiplicacion.setText("*");
        menuPopupOperacionMultiplicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupMultiplicacion(evt);
            }
        });
        menuPopupInsertarOperacion.add(menuPopupOperacionMultiplicacion);

        menuPopupOperacionDivision.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupOperacionDivision.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupOperacionDivision.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupOperacionDivision.setText("/");
        menuPopupOperacionDivision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupDivididoPor(evt);
            }
        });
        menuPopupInsertarOperacion.add(menuPopupOperacionDivision);

        menuPopupDivisionInversa.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        menuPopupDivisionInversa.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        menuPopupDivisionInversa.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        menuPopupDivisionInversa.setText("\\");
            menuPopupDivisionInversa.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    popupDivideA(evt);
                }
            });
            menuPopupInsertarOperacion.add(menuPopupDivisionInversa);

            menuPopupPotenciacion.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
            menuPopupPotenciacion.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            menuPopupPotenciacion.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
            menuPopupPotenciacion.setText("^");
            menuPopupPotenciacion.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    popupElevadoA(evt);
                }
            });
            menuPopupInsertarOperacion.add(menuPopupPotenciacion);

            jPopupMenu1.add(menuPopupInsertarOperacion);

            separadorMenu3.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
            separadorMenu3.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
            jPopupMenu1.add(separadorMenu3);

            menuPopupDeshacer.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
            menuPopupDeshacer.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            menuPopupDeshacer.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
            menuPopupDeshacer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/left.png"))); // NOI18N
            menuPopupDeshacer.setText("Deshacer");
            menuPopupDeshacer.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    popupDeshacer(evt);
                }
            });
            jPopupMenu1.add(menuPopupDeshacer);

            menuPopupRehacer.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
            menuPopupRehacer.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            menuPopupRehacer.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
            menuPopupRehacer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/right.png"))); // NOI18N
            menuPopupRehacer.setText("Rehacer");
            menuPopupRehacer.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    popupRehacer(evt);
                }
            });
            jPopupMenu1.add(menuPopupRehacer);

            menuPopupEliminarContenido.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
            menuPopupEliminarContenido.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            menuPopupEliminarContenido.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
            menuPopupEliminarContenido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/eraser.png"))); // NOI18N
            menuPopupEliminarContenido.setText("Eliminar contenido");
            menuPopupEliminarContenido.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    popupEliminarContenido(evt);
                }
            });
            jPopupMenu1.add(menuPopupEliminarContenido);

            separadorMenu4.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
            separadorMenu4.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
            jPopupMenu1.add(separadorMenu4);

            menuPopupPreferencias.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
            menuPopupPreferencias.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            menuPopupPreferencias.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
            menuPopupPreferencias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/preferences.png"))); // NOI18N
            menuPopupPreferencias.setText("Preferencias");
            menuPopupPreferencias.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    popupPreferencias(evt);
                }
            });
            jPopupMenu1.add(menuPopupPreferencias);

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Calculadora");
            setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            setResizable(false);

            jPanel2.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
            jPanel2.setComponentPopupMenu(jPopupMenu1);

            jPanel1.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
            jPanel1.setComponentPopupMenu(jPopupMenu1);

            jButton14.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton14.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton14.setText("^");
            jButton14.setToolTipText("Potenciación");
            jButton14.setComponentPopupMenu(jPopupMenu1);
            jButton14.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarPotenciacion(evt);
                }
            });

            jButton16.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton16.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton16.setText("*");
            jButton16.setToolTipText("Multiplicación");
            jButton16.setComponentPopupMenu(jPopupMenu1);
            jButton16.setMaximumSize(new java.awt.Dimension(48, 38));
            jButton16.setMinimumSize(new java.awt.Dimension(48, 38));
            jButton16.setPreferredSize(new java.awt.Dimension(48, 38));
            jButton16.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarMultiplicacion(evt);
                }
            });

            jButton17.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton17.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton17.setText("-");
            jButton17.setToolTipText("Resta");
            jButton17.setComponentPopupMenu(jPopupMenu1);
            jButton17.setMaximumSize(new java.awt.Dimension(48, 38));
            jButton17.setMinimumSize(new java.awt.Dimension(48, 38));
            jButton17.setPreferredSize(new java.awt.Dimension(48, 38));
            jButton17.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarMenos(evt);
                }
            });

            jButton13.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton13.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton13.setText("+");
            jButton13.setToolTipText("Suma");
            jButton13.setComponentPopupMenu(jPopupMenu1);
            jButton13.setMaximumSize(new java.awt.Dimension(48, 80));
            jButton13.setMinimumSize(new java.awt.Dimension(48, 80));
            jButton13.setPreferredSize(new java.awt.Dimension(48, 80));
            jButton13.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarMas(evt);
                }
            });

            jButton3.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton3.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton3.setText("9");
            jButton3.setToolTipText("Número nueve");
            jButton3.setComponentPopupMenu(jPopupMenu1);
            jButton3.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarNueve(evt);
                }
            });

            jButton4.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton4.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton4.setText("4");
            jButton4.setToolTipText("Número cuatro");
            jButton4.setComponentPopupMenu(jPopupMenu1);
            jButton4.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarCuatro(evt);
                }
            });

            jButton5.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton5.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton5.setText("5");
            jButton5.setToolTipText("Número cinco");
            jButton5.setComponentPopupMenu(jPopupMenu1);
            jButton5.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarCinco(evt);
                }
            });

            jButton6.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton6.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton6.setText("6");
            jButton6.setToolTipText("Número seis");
            jButton6.setComponentPopupMenu(jPopupMenu1);
            jButton6.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarSeis(evt);
                }
            });

            jButton12.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton12.setFont(new java.awt.Font("Arial", 0, 20)); // NOI18N
            jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/return.png"))); // NOI18N
            jButton12.setToolTipText("Evaluar la expresión escrita");
            jButton12.setComponentPopupMenu(jPopupMenu1);
            jButton12.setMaximumSize(new java.awt.Dimension(48, 80));
            jButton12.setMinimumSize(new java.awt.Dimension(48, 80));
            jButton12.setPreferredSize(new java.awt.Dimension(48, 80));
            jButton12.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    botonEnter(evt);
                }
            });

            jButton9.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton9.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton9.setText("3");
            jButton9.setToolTipText("Número tres");
            jButton9.setComponentPopupMenu(jPopupMenu1);
            jButton9.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarTres(evt);
                }
            });

            jButton11.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton11.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton11.setText(".");
            jButton11.setToolTipText("Punto decimal");
            jButton11.setComponentPopupMenu(jPopupMenu1);
            jButton11.setMaximumSize(new java.awt.Dimension(48, 38));
            jButton11.setMinimumSize(new java.awt.Dimension(48, 38));
            jButton11.setPreferredSize(new java.awt.Dimension(48, 38));
            jButton11.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarPunto(evt);
                }
            });

            jButton10.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton10.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton10.setText("0");
            jButton10.setToolTipText("Número cero");
            jButton10.setComponentPopupMenu(jPopupMenu1);
            jButton10.setMaximumSize(new java.awt.Dimension(100, 38));
            jButton10.setMinimumSize(new java.awt.Dimension(100, 38));
            jButton10.setPreferredSize(new java.awt.Dimension(100, 38));
            jButton10.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarCero(evt);
                }
            });

            jButton8.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton8.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton8.setText("2");
            jButton8.setToolTipText("Número dos");
            jButton8.setComponentPopupMenu(jPopupMenu1);
            jButton8.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarDos(evt);
                }
            });

            jButton7.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton7.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton7.setText("1");
            jButton7.setToolTipText("Número uno");
            jButton7.setComponentPopupMenu(jPopupMenu1);
            jButton7.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarUno(evt);
                }
            });

            jButton15.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton15.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton15.setText("/");
            jButton15.setToolTipText("Dividido por");
            jButton15.setComponentPopupMenu(jPopupMenu1);
            jButton15.setMaximumSize(new java.awt.Dimension(48, 38));
            jButton15.setMinimumSize(new java.awt.Dimension(48, 38));
            jButton15.setPreferredSize(new java.awt.Dimension(48, 38));
            jButton15.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarDivision(evt);
                }
            });

            jButton1.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton1.setText("7");
            jButton1.setToolTipText("Número siete");
            jButton1.setComponentPopupMenu(jPopupMenu1);
            jButton1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarSiete(evt);
                }
            });

            jButton2.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton2.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton2.setText("8");
            jButton2.setToolTipText("Número ocho");
            jButton2.setComponentPopupMenu(jPopupMenu1);
            jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    insertarOcho(evt);
                }
            });

            jButton23.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
            jButton23.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
            jButton23.setText("\\");
                jButton23.setToolTipText("Divide a");
                jButton23.setComponentPopupMenu(jPopupMenu1);
                jButton23.setMaximumSize(new java.awt.Dimension(48, 38));
                jButton23.setMinimumSize(new java.awt.Dimension(48, 38));
                jButton23.setPreferredSize(new java.awt.Dimension(48, 38));
                jButton23.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarDivisionInversa(evt);
                    }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(4, 4, 4))
                );
                jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton23, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(4, 4, 4)
                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4))
                );

                jPanel3.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
                jPanel3.setComponentPopupMenu(jPopupMenu1);

                jComboBox1.setBackground(new java.awt.Color(255, 255, 255));
                jComboBox1.setEditable(true);
                jComboBox1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jComboBox1.setToolTipText("Escriba la expresión para ser evaluada, presione \"Enter\" para evaluarla");
                jComboBox1.setComponentPopupMenu(jPopupMenu1);
                jComboBox1.setMaximumSize(new java.awt.Dimension(453, 38));
                jComboBox1.setMinimumSize(new java.awt.Dimension(453, 38));
                jComboBox1.setPreferredSize(new java.awt.Dimension(453, 38));

                jButton18.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton18.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/Graph.png"))); // NOI18N
                jButton18.setToolTipText("Graficar la expresión escrita");
                jButton18.setComponentPopupMenu(jPopupMenu1);
                jButton18.setMaximumSize(new java.awt.Dimension(48, 38));
                jButton18.setMinimumSize(new java.awt.Dimension(48, 38));
                jButton18.setPreferredSize(new java.awt.Dimension(48, 38));
                jButton18.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        graficarBoton(evt);
                    }
                });

                jButton22.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton22.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton22.setText("=");
                jButton22.setToolTipText("Evaluar la expresión escrita");
                jButton22.setComponentPopupMenu(jPopupMenu1);
                jButton22.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonIgual(evt);
                    }
                });

                jButton49.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton49.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/new.png"))); // NOI18N
                jButton49.setToolTipText("Crear nueva función");
                jButton49.setComponentPopupMenu(jPopupMenu1);
                jButton49.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonNuevaFuncion(evt);
                    }
                });

                jButton50.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton50.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton50.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/copiar.png"))); // NOI18N
                jButton50.setToolTipText("Copiar selección");
                jButton50.setComponentPopupMenu(jPopupMenu1);
                jButton50.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonCopiar(evt);
                    }
                });

                jButton51.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton51.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/cortar.png"))); // NOI18N
                jButton51.setToolTipText("Cortar selección");
                jButton51.setComponentPopupMenu(jPopupMenu1);
                jButton51.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonCortar(evt);
                    }
                });

                jButton52.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton52.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/pegar.png"))); // NOI18N
                jButton52.setToolTipText("Pegar selección");
                jButton52.setComponentPopupMenu(jPopupMenu1);
                jButton52.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonPegar(evt);
                    }
                });

                jButton58.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton58.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/preferences.png"))); // NOI18N
                jButton58.setToolTipText("Preferencias");
                jButton58.setComponentPopupMenu(jPopupMenu1);
                jButton58.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonPreferencias(evt);
                    }
                });

                jButton59.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton59.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/help.png"))); // NOI18N
                jButton59.setToolTipText("Ayuda");
                jButton59.setComponentPopupMenu(jPopupMenu1);
                jButton59.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonAyuda(evt);
                    }
                });

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jButton49, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jButton51, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jButton58, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4))
                );
                jPanel3Layout.setVerticalGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton50, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton59, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton58, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton51, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton49, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(8, 8, 8))
                );

                jPanel4.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
                jPanel4.setComponentPopupMenu(jPopupMenu1);

                jButton20.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton20.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/left.png"))); // NOI18N
                jButton20.setToolTipText("Deshacer");
                jButton20.setComponentPopupMenu(jPopupMenu1);
                jButton20.setMaximumSize(new java.awt.Dimension(48, 38));
                jButton20.setMinimumSize(new java.awt.Dimension(48, 38));
                jButton20.setPreferredSize(new java.awt.Dimension(48, 38));
                jButton20.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonDeshacer(evt);
                    }
                });

                jButton21.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton21.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/right.png"))); // NOI18N
                jButton21.setToolTipText("Rehacer");
                jButton21.setComponentPopupMenu(jPopupMenu1);
                jButton21.setMaximumSize(new java.awt.Dimension(48, 38));
                jButton21.setMinimumSize(new java.awt.Dimension(48, 38));
                jButton21.setPreferredSize(new java.awt.Dimension(48, 38));
                jButton21.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonRehacer(evt);
                    }
                });

                jButton24.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton24.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton24.setText("cos(x)");
                jButton24.setToolTipText("Coseno");
                jButton24.setComponentPopupMenu(jPopupMenu1);
                jButton24.setMaximumSize(new java.awt.Dimension(100, 38));
                jButton24.setMinimumSize(new java.awt.Dimension(100, 38));
                jButton24.setPreferredSize(new java.awt.Dimension(100, 38));
                jButton24.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarCoseno(evt);
                    }
                });

                jButton26.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton26.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton26.setText("sen(x)");
                jButton26.setToolTipText(descripcion("sen"));
                jButton26.setComponentPopupMenu(jPopupMenu1);
                jButton26.setMaximumSize(new java.awt.Dimension(100, 38));
                jButton26.setMinimumSize(new java.awt.Dimension(100, 38));
                jButton26.setPreferredSize(new java.awt.Dimension(100, 38));
                jButton26.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarSeno(evt);
                    }
                });

                jButton27.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton27.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton27.setText("sec(x)");
                jButton27.setToolTipText("Secante");
                jButton27.setComponentPopupMenu(jPopupMenu1);
                jButton27.setMaximumSize(new java.awt.Dimension(100, 38));
                jButton27.setMinimumSize(new java.awt.Dimension(100, 38));
                jButton27.setPreferredSize(new java.awt.Dimension(100, 38));
                jButton27.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarSecante(evt);
                    }
                });

                jButton29.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton29.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton29.setText("csc(x)");
                jButton29.setToolTipText(descripcion("csc"));
                jButton29.setComponentPopupMenu(jPopupMenu1);
                jButton29.setMaximumSize(new java.awt.Dimension(100, 38));
                jButton29.setMinimumSize(new java.awt.Dimension(100, 38));
                jButton29.setPreferredSize(new java.awt.Dimension(100, 38));
                jButton29.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarCosecante(evt);
                    }
                });

                jButton31.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton31.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton31.setText("tan(x)");
                jButton31.setToolTipText("Tangente");
                jButton31.setComponentPopupMenu(jPopupMenu1);
                jButton31.setMaximumSize(new java.awt.Dimension(100, 38));
                jButton31.setMinimumSize(new java.awt.Dimension(100, 38));
                jButton31.setPreferredSize(new java.awt.Dimension(100, 38));
                jButton31.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarTangente(evt);
                    }
                });

                jButton33.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton33.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton33.setText("cot(x)");
                jButton33.setToolTipText("Cotangente");
                jButton33.setComponentPopupMenu(jPopupMenu1);
                jButton33.setMaximumSize(new java.awt.Dimension(100, 38));
                jButton33.setMinimumSize(new java.awt.Dimension(100, 38));
                jButton33.setPreferredSize(new java.awt.Dimension(100, 38));
                jButton33.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarCotangente(evt);
                    }
                });

                jButton35.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton35.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/eraser.png"))); // NOI18N
                jButton35.setToolTipText("Eliminar la expresión escrita");
                jButton35.setComponentPopupMenu(jPopupMenu1);
                jButton35.setMaximumSize(new java.awt.Dimension(48, 38));
                jButton35.setMinimumSize(new java.awt.Dimension(48, 38));
                jButton35.setPreferredSize(new java.awt.Dimension(48, 38));
                jButton35.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonLimpiarTexto(evt);
                    }
                });

                jButton36.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton36.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton36.setText("e");
                jButton36.setToolTipText("e^x");
                jButton36.setComponentPopupMenu(jPopupMenu1);
                jButton36.setMaximumSize(new java.awt.Dimension(48, 38));
                jButton36.setMinimumSize(new java.awt.Dimension(48, 38));
                jButton36.setPreferredSize(new java.awt.Dimension(48, 38));
                jButton36.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarEElevadoA(evt);
                    }
                });

                jButton37.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton37.setFont(new java.awt.Font("Junicode", 0, 24)); // NOI18N
                jButton37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/pi.png"))); // NOI18N
                jButton37.setToolTipText("Insertar ╥");
                jButton37.setComponentPopupMenu(jPopupMenu1);
                jButton37.setMaximumSize(new java.awt.Dimension(48, 38));
                jButton37.setMinimumSize(new java.awt.Dimension(48, 38));
                jButton37.setPreferredSize(new java.awt.Dimension(48, 38));
                jButton37.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarPI(evt);
                    }
                });

                jButton38.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton38.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton38.setText("ln(x)");
                jButton38.setToolTipText("Logaritmo Natural (Logaritmo en base 'e')");
                jButton38.setComponentPopupMenu(jPopupMenu1);
                jButton38.setMaximumSize(new java.awt.Dimension(100, 38));
                jButton38.setMinimumSize(new java.awt.Dimension(100, 38));
                jButton38.setPreferredSize(new java.awt.Dimension(100, 38));
                jButton38.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarLogaritmoNatural(evt);
                    }
                });

                jButton40.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton40.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton40.setText("1/x");
                jButton40.setToolTipText("Dividir uno entre la expresión señalada");
                jButton40.setComponentPopupMenu(jPopupMenu1);
                jButton40.setMaximumSize(new java.awt.Dimension(100, 38));
                jButton40.setMinimumSize(new java.awt.Dimension(100, 38));
                jButton40.setPreferredSize(new java.awt.Dimension(100, 38));
                jButton40.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarUnoSobre(evt);
                    }
                });

                jButton42.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton42.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton42.setText("10^x");
                jButton42.setToolTipText("Elevar 10 a la x");
                jButton42.setComponentPopupMenu(jPopupMenu1);
                jButton42.setMaximumSize(new java.awt.Dimension(110, 38));
                jButton42.setMinimumSize(new java.awt.Dimension(110, 38));
                jButton42.setPreferredSize(new java.awt.Dimension(110, 38));
                jButton42.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarDiezElevadoA(evt);
                    }
                });

                jButton43.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton43.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton43.setText("x^2");
                jButton43.setToolTipText("Elevar al cuadrado");
                jButton43.setComponentPopupMenu(jPopupMenu1);
                jButton43.setMaximumSize(new java.awt.Dimension(100, 38));
                jButton43.setMinimumSize(new java.awt.Dimension(100, 38));
                jButton43.setPreferredSize(new java.awt.Dimension(100, 38));
                jButton43.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarCuadrado(evt);
                    }
                });

                jButton44.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton44.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton44.setText("x^3");
                jButton44.setToolTipText("Elevar al cubo");
                jButton44.setComponentPopupMenu(jPopupMenu1);
                jButton44.setMaximumSize(new java.awt.Dimension(100, 38));
                jButton44.setMinimumSize(new java.awt.Dimension(100, 38));
                jButton44.setPreferredSize(new java.awt.Dimension(100, 38));
                jButton44.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarCubo(evt);
                    }
                });

                jButton45.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton45.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton45.setText("x^⅓ ");
                jButton45.setToolTipText("Raiz cúbica");
                jButton45.setComponentPopupMenu(jPopupMenu1);
                jButton45.setMaximumSize(new java.awt.Dimension(100, 38));
                jButton45.setMinimumSize(new java.awt.Dimension(100, 38));
                jButton45.setPreferredSize(new java.awt.Dimension(100, 38));
                jButton45.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarRaizCubica(evt);
                    }
                });

                jButton46.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton46.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton46.setText("x^½");
                jButton46.setToolTipText("Raiz cuadrada");
                jButton46.setComponentPopupMenu(jPopupMenu1);
                jButton46.setMaximumSize(new java.awt.Dimension(100, 38));
                jButton46.setMinimumSize(new java.awt.Dimension(100, 38));
                jButton46.setPreferredSize(new java.awt.Dimension(100, 38));
                jButton46.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarRaizCuadrada(evt);
                    }
                });

                jButton48.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton48.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton48.setText("!");
                jButton48.setToolTipText("Factorial");
                jButton48.setComponentPopupMenu(jPopupMenu1);
                jButton48.setMaximumSize(new java.awt.Dimension(48, 38));
                jButton48.setMinimumSize(new java.awt.Dimension(48, 38));
                jButton48.setPreferredSize(new java.awt.Dimension(48, 38));
                jButton48.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarFactorial(evt);
                    }
                });

                jButton19.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton19.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton19.setText("acos(x)");
                jButton19.setToolTipText("Arcocoseno(Coseno inverso)");
                jButton19.setComponentPopupMenu(jPopupMenu1);
                jButton19.setMaximumSize(new java.awt.Dimension(110, 38));
                jButton19.setMinimumSize(new java.awt.Dimension(110, 38));
                jButton19.setPreferredSize(new java.awt.Dimension(110, 38));
                jButton19.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarACoseno(evt);
                    }
                });

                jButton30.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton30.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton30.setText("acsc(x)");
                jButton30.setToolTipText("Arcocosecante(Cosecante inversa)");
                jButton30.setComponentPopupMenu(jPopupMenu1);
                jButton30.setMaximumSize(new java.awt.Dimension(110, 38));
                jButton30.setMinimumSize(new java.awt.Dimension(110, 38));
                jButton30.setPreferredSize(new java.awt.Dimension(110, 38));
                jButton30.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarACosecante(evt);
                    }
                });

                jButton28.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton28.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton28.setText("asec(x)");
                jButton28.setToolTipText("Arcosecante(Secante inversa)");
                jButton28.setComponentPopupMenu(jPopupMenu1);
                jButton28.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarASecante(evt);
                    }
                });

                jButton25.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton25.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton25.setText("asen(x)");
                jButton25.setToolTipText("Arcoseno(Seno inverso)");
                jButton25.setComponentPopupMenu(jPopupMenu1);
                jButton25.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarASeno(evt);
                    }
                });

                jButton47.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton47.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton47.setText("f(x) = 0");
                jButton47.setToolTipText("Evaluar los ceros de la expresión en términos de las variables.");
                jButton47.setComponentPopupMenu(jPopupMenu1);
                jButton47.setMaximumSize(new java.awt.Dimension(110, 38));
                jButton47.setMinimumSize(new java.awt.Dimension(110, 38));
                jButton47.setPreferredSize(new java.awt.Dimension(110, 38));
                jButton47.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        botonCalcularCerosFuncion(evt);
                    }
                });

                jButton34.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton34.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton34.setText("acot(x)");
                jButton34.setToolTipText("Arcocotangente");
                jButton34.setComponentPopupMenu(jPopupMenu1);
                jButton34.setMaximumSize(new java.awt.Dimension(110, 38));
                jButton34.setMinimumSize(new java.awt.Dimension(110, 38));
                jButton34.setPreferredSize(new java.awt.Dimension(110, 38));
                jButton34.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarACotangente(evt);
                    }
                });

                jButton32.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton32.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton32.setText("atan(x)");
                jButton32.setToolTipText("Arcotangente");
                jButton32.setComponentPopupMenu(jPopupMenu1);
                jButton32.setMaximumSize(new java.awt.Dimension(110, 38));
                jButton32.setMinimumSize(new java.awt.Dimension(110, 38));
                jButton32.setPreferredSize(new java.awt.Dimension(110, 38));
                jButton32.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarATangente(evt);
                    }
                });

                jButton39.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton39.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton39.setText("log(x, y)");
                jButton39.setToolTipText("Logaritmo en base x de y");
                jButton39.setComponentPopupMenu(jPopupMenu1);
                jButton39.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarLogaritmo(evt);
                    }
                });

                jButton41.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton41.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton41.setText("2^x");
                jButton41.setToolTipText("Elevar dos a la expresión señalada");
                jButton41.setComponentPopupMenu(jPopupMenu1);
                jButton41.setMaximumSize(new java.awt.Dimension(100, 38));
                jButton41.setMinimumSize(new java.awt.Dimension(100, 38));
                jButton41.setPreferredSize(new java.awt.Dimension(100, 38));
                jButton41.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarDosElevadoA(evt);
                    }
                });

                jButton53.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton53.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton53.setText("tanh(x)");
                jButton53.setToolTipText("Tangente hiperbólica.");
                jButton53.setComponentPopupMenu(jPopupMenu1);
                jButton53.setMaximumSize(new java.awt.Dimension(112, 38));
                jButton53.setMinimumSize(new java.awt.Dimension(112, 38));
                jButton53.setPreferredSize(new java.awt.Dimension(112, 38));
                jButton53.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarTangenteHiperbolica(evt);
                    }
                });

                jButton54.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton54.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton54.setText("cosh(x)");
                jButton54.setToolTipText("Coseno hiperbólico.");
                jButton54.setComponentPopupMenu(jPopupMenu1);
                jButton54.setMaximumSize(new java.awt.Dimension(112, 38));
                jButton54.setMinimumSize(new java.awt.Dimension(112, 38));
                jButton54.setPreferredSize(new java.awt.Dimension(112, 38));
                jButton54.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarCosenoHiperbolico(evt);
                    }
                });

                jButton55.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton55.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton55.setText("senh(x)");
                jButton55.setToolTipText("Seno hiperbólico.");
                jButton55.setComponentPopupMenu(jPopupMenu1);
                jButton55.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarSenoHiperbolico(evt);
                    }
                });

                jButton56.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton56.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton56.setText("red(x)");
                jButton56.setToolTipText("Redondear x al entero más cercano.");
                jButton56.setComponentPopupMenu(jPopupMenu1);
                jButton56.setMaximumSize(new java.awt.Dimension(112, 38));
                jButton56.setMinimumSize(new java.awt.Dimension(112, 38));
                jButton56.setPreferredSize(new java.awt.Dimension(112, 38));
                jButton56.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarRedondeo(evt);
                    }
                });

                jButton57.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
                jButton57.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jButton57.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/hyp.png"))); // NOI18N
                jButton57.setToolTipText("Hipotenusa de un triángulo rectángulo con catetos x e y.");
                jButton57.setComponentPopupMenu(jPopupMenu1);
                jButton57.setMaximumSize(new java.awt.Dimension(112, 38));
                jButton57.setMinimumSize(new java.awt.Dimension(112, 38));
                jButton57.setPreferredSize(new java.awt.Dimension(112, 38));
                jButton57.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        insertarHipotenusa(evt);
                    }
                });

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(4, 4, 4)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jButton34, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jButton32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jButton47, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(jButton48, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton53, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton57, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4))
                );
                jPanel4Layout.setVerticalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton53, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jButton54, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jButton55, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jButton56, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jButton57, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jButton25, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(4, 4, 4)
                                                .addComponent(jButton34, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(4, 4, 4)
                                                .addComponent(jButton47, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jButton33, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(4, 4, 4)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jButton30, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jButton38, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(4, 4, 4)
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jButton40, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jButton43, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(4, 4, 4)
                                            .addComponent(jButton44, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(4, 4, 4)
                                            .addComponent(jButton46, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(4, 4, 4)
                                            .addComponent(jButton45, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(126, 126, 126))))
                                .addGap(4, 4, 4)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton21, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton35, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton36, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton37, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton48, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton39, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(4, 4, 4))
                );

                jPanel5.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);

                jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
                jScrollPane1.setForeground(new java.awt.Color(0, 0, 0));
                jScrollPane1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jScrollPane1.setInheritsPopupMenu(true);

                jTextArea1.setEditable(false);
                jTextArea1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
                jTextArea1.setForeground(new java.awt.Color(0, 0, 0));
                jTextArea1.setComponentPopupMenu(jPopupMenu1);
                jTextArea1.setDragEnabled(true);
                jTextArea1.setDropMode(javax.swing.DropMode.INSERT);
                jTextArea1.setMinimumSize(new java.awt.Dimension(0, 20));
                jScrollPane1.setViewportView(jTextArea1);

                javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                jPanel5.setLayout(jPanel5Layout);
                jPanel5Layout.setHorizontalGroup(
                    jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane1)
                        .addGap(4, 4, 4))
                );
                jPanel5Layout.setVerticalGroup(
                    jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                );

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(8, 8, 8))
                );
                jPanel2Layout.setVerticalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(8, 8, 8)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(8, 8, 8))
                );

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );

                pack();
            }// </editor-fold>//GEN-END:initComponents

    /**
     *
     */
    private void insertarHipotenusa(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarHipotenusa
        hipotenusa();
    }//GEN-LAST:event_insertarHipotenusa

    /**
     *
     */
    private void insertarRedondeo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarRedondeo
        redondeoEntero();
    }//GEN-LAST:event_insertarRedondeo

    /**
     *
     */
    private void insertarSenoHiperbolico(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarSenoHiperbolico
        senoHiperbolico();
    }//GEN-LAST:event_insertarSenoHiperbolico

    /**
     *
     */
    private void insertarCosenoHiperbolico(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarCosenoHiperbolico
        cosenoHiperbolico();
    }//GEN-LAST:event_insertarCosenoHiperbolico

    /**
     *
     */
    private void insertarTangenteHiperbolica(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarTangenteHiperbolica
        tangenteHiperbolica();
    }//GEN-LAST:event_insertarTangenteHiperbolica

    /**
     *
     */
    private void insertarDosElevadoA(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarDosElevadoA
        dosElevadoA();
    }//GEN-LAST:event_insertarDosElevadoA

    /**
     *
     */
    private void insertarLogaritmo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarLogaritmo
        logaritmo();
    }//GEN-LAST:event_insertarLogaritmo

    /**
     *
     */
    private void insertarATangente(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarATangente
        arcoTangente();
    }//GEN-LAST:event_insertarATangente

    /**
     *
     */
    private void insertarACotangente(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarACotangente
        arcoCotangente();
    }//GEN-LAST:event_insertarACotangente

    /**
     *
     */
    private void botonCalcularCerosFuncion(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCalcularCerosFuncion
        calcularCerosFuncion();
    }//GEN-LAST:event_botonCalcularCerosFuncion

    /**
     *
     */
    private void insertarASeno(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarASeno
        arcoSeno();
    }//GEN-LAST:event_insertarASeno

    /**
     *
     */
    private void insertarASecante(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarASecante
        arcoSecante();
    }//GEN-LAST:event_insertarASecante

    /**
     *
     */
    private void insertarACosecante(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarACosecante
        arcoCosecante();
    }//GEN-LAST:event_insertarACosecante

    /**
     *
     */
    private void insertarACoseno(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarACoseno
        arcoCoseno();
    }//GEN-LAST:event_insertarACoseno

    /**
     *
     */
    private void insertarFactorial(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarFactorial
        factorial();
    }//GEN-LAST:event_insertarFactorial

    /**
     *
     */
    private void insertarRaizCuadrada(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarRaizCuadrada
        raizCuadrada();
    }//GEN-LAST:event_insertarRaizCuadrada

    /**
     *
     */
    private void insertarRaizCubica(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarRaizCubica
        raizCubica();
    }//GEN-LAST:event_insertarRaizCubica

    /**
     *
     */
    private void insertarCubo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarCubo
        cubo();
    }//GEN-LAST:event_insertarCubo

    /**
     *
     */
    private void insertarCuadrado(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarCuadrado
        cuadrado();
    }//GEN-LAST:event_insertarCuadrado

    /**
     *
     */
    private void insertarDiezElevadoA(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarDiezElevadoA
        diezElevadoA();
    }//GEN-LAST:event_insertarDiezElevadoA

    /**
     *
     */
    private void insertarUnoSobre(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarUnoSobre
        unoSobre();
    }//GEN-LAST:event_insertarUnoSobre

    /**
     *
     */
    private void insertarLogaritmoNatural(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarLogaritmoNatural
        logaritmoNatural();
    }//GEN-LAST:event_insertarLogaritmoNatural

    /**
     *
     */
    private void insertarPI(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarPI
        pi();
    }//GEN-LAST:event_insertarPI

    /**
     *
     */
    private void insertarEElevadoA(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarEElevadoA
        eElevadoA();
    }//GEN-LAST:event_insertarEElevadoA

    /**
     *
     */
    private void botonLimpiarTexto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLimpiarTexto
        limpiarTexto();
    }//GEN-LAST:event_botonLimpiarTexto

    /**
     *
     */
    private void insertarCotangente(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarCotangente
        cotangente();
    }//GEN-LAST:event_insertarCotangente

    /**
     *
     */
    private void insertarTangente(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarTangente
        tangente();
    }//GEN-LAST:event_insertarTangente

    /**
     *
     */
    private void insertarCosecante(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarCosecante
        cosecante();
    }//GEN-LAST:event_insertarCosecante

    /**
     *
     */
    private void insertarSecante(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarSecante
        secante();
    }//GEN-LAST:event_insertarSecante

    /**
     *
     */
    private void insertarSeno(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarSeno
        seno();
    }//GEN-LAST:event_insertarSeno

    /**
     *
     */
    private void insertarCoseno(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarCoseno
        coseno();
    }//GEN-LAST:event_insertarCoseno

    /**
     *
     */
    private void botonRehacer(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRehacer
        rehacer();
    }//GEN-LAST:event_botonRehacer

    /**
     *
     */
    private void botonDeshacer(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonDeshacer
        deshacer();
    }//GEN-LAST:event_botonDeshacer

    /**
     *
     */
    private void botonPegar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPegar
        pegar();
    }//GEN-LAST:event_botonPegar

    /**
     *
     */
    private void botonCortar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCortar
        cortar();
    }//GEN-LAST:event_botonCortar

    /**
     *
     */
    private void botonCopiar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCopiar
        copiar();
    }//GEN-LAST:event_botonCopiar

    /**
     *
     */
    private void botonNuevaFuncion(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonNuevaFuncion
        nuevaFuncion();
    }//GEN-LAST:event_botonNuevaFuncion

    /**
     *
     */
    private void botonIgual(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIgual
        procesarExpresion();
    }//GEN-LAST:event_botonIgual

    /**
     *
     */
    private void graficarBoton(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graficarBoton
        graficar();
    }//GEN-LAST:event_graficarBoton

    /**
     *
     */
    private void insertarDivisionInversa(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarDivisionInversa
        divisionInversa();
    }//GEN-LAST:event_insertarDivisionInversa

    /**
     *
     */
    private void insertarOcho(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarOcho
        numeroOcho();
    }//GEN-LAST:event_insertarOcho

    /**
     *
     */
    private void insertarSiete(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarSiete
        numeroSiete();
    }//GEN-LAST:event_insertarSiete

    /**
     *
     */
    private void insertarDivision(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarDivision
        division();
    }//GEN-LAST:event_insertarDivision

    /**
     *
     */
    private void insertarUno(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarUno
        numeroUno();
    }//GEN-LAST:event_insertarUno

    /**
     *
     */
    private void insertarDos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarDos
        numeroDos();
    }//GEN-LAST:event_insertarDos

    /**
     *
     */
    private void insertarCero(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarCero
        numeroCero();
    }//GEN-LAST:event_insertarCero

    /**
     *
     */
    private void insertarPunto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarPunto
        puntoDecimal();
    }//GEN-LAST:event_insertarPunto

    /**
     *
     */
    private void insertarTres(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarTres
        numeroTres();
    }//GEN-LAST:event_insertarTres

    /**
     *
     */
    private void botonEnter(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEnter
        procesarExpresion();
    }//GEN-LAST:event_botonEnter

    /**
     *
     */
    private void insertarSeis(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarSeis
        numeroSeis();
    }//GEN-LAST:event_insertarSeis

    /**
     *
     */
    private void insertarCinco(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarCinco
        numeroCinco();
    }//GEN-LAST:event_insertarCinco

    /**
     *
     */
    private void insertarCuatro(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarCuatro
        numeroCuatro();
    }//GEN-LAST:event_insertarCuatro

    /**
     *
     */
    private void insertarNueve(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarNueve
        numeroNueve();
    }//GEN-LAST:event_insertarNueve

    /**
     *
     */
    private void insertarMas(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarMas
        suma();
    }//GEN-LAST:event_insertarMas

    /**
     *
     */
    private void insertarMenos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarMenos
        resta();
    }//GEN-LAST:event_insertarMenos

    /**
     *
     */
    private void insertarMultiplicacion(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarMultiplicacion
        multiplicacion();
    }//GEN-LAST:event_insertarMultiplicacion

    /**
     *
     */
    private void insertarPotenciacion(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertarPotenciacion
        potenciacion();
    }//GEN-LAST:event_insertarPotenciacion

    /**
     *
     */
    private void botonPreferencias(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPreferencias
        cambiarPreferencias();
    }//GEN-LAST:event_botonPreferencias

    /**
     *
     */
    private void popupCopiar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupCopiar
        copiar();
    }//GEN-LAST:event_popupCopiar

    /**
     *
     */
    private void popupPegar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupPegar
        pegar();
    }//GEN-LAST:event_popupPegar

    /**
     *
     */
    private void popupCortar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupCortar
        cortar();
    }//GEN-LAST:event_popupCortar

    /**
     *
     */
    private void popupDeshacer(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupDeshacer
        deshacer();
    }//GEN-LAST:event_popupDeshacer

    /**
     *
     */
    private void popupRehacer(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupRehacer
        rehacer();
    }//GEN-LAST:event_popupRehacer

    /**
     *
     */
    private void popupEliminarContenido(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupEliminarContenido
        limpiarTexto();
    }//GEN-LAST:event_popupEliminarContenido

    /**
     *
     */
    private void popupProcesarExpresion(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupProcesarExpresion
        procesarExpresion();
    }//GEN-LAST:event_popupProcesarExpresion

    /**
     *
     */
    private void popupGraficarExpresion(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupGraficarExpresion
        graficar();
    }//GEN-LAST:event_popupGraficarExpresion

    /**
     *
     */
    private void popupAgregarFuncion(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupAgregarFuncion
        nuevaFuncion();
    }//GEN-LAST:event_popupAgregarFuncion

    /**
     *
     */
    private void popupSeparadorArgumentoFuncion(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupSeparadorArgumentoFuncion
        separadorArgumentoFuncion();
    }//GEN-LAST:event_popupSeparadorArgumentoFuncion

    /**
     *
     */
    private void popupParentesisIzquierdo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupParentesisIzquierdo
        parentesisIzquierdo();
    }//GEN-LAST:event_popupParentesisIzquierdo

    /**
     *
     */
    private void popupParentesisDerecho(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupParentesisDerecho
        parentesisDerecho();
    }//GEN-LAST:event_popupParentesisDerecho

    /**
     *
     */
    private void popupParentesis(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupParentesis
        parentesis();
    }//GEN-LAST:event_popupParentesis

    /**
     *
     */
    private void popupRedondeoEntero(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupRedondeoEntero
        redondeoEntero();
    }//GEN-LAST:event_popupRedondeoEntero

    /**
     *
     */
    private void popupFactorial(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupFactorial
        factorial();
    }//GEN-LAST:event_popupFactorial

    /**
     *
     */
    private void popupHipotenusa(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupHipotenusa
        hipotenusa();
    }//GEN-LAST:event_popupHipotenusa

    /**
     *
     */
    private void popupSeno(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupSeno
        seno();
    }//GEN-LAST:event_popupSeno

    /**
     *
     */
    private void popupCoseno(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupCoseno
        coseno();
    }//GEN-LAST:event_popupCoseno

    /**
     *
     */
    private void popupTangente(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupTangente
        tangente();
    }//GEN-LAST:event_popupTangente

    /**
     *
     */
    private void popupCosecante(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupCosecante
        cosecante();
    }//GEN-LAST:event_popupCosecante

    /**
     *
     */
    private void popupSecante(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupSecante
        secante();
    }//GEN-LAST:event_popupSecante

    /**
     *
     */
    private void popupCotangente(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupCotangente
        cotangente();
    }//GEN-LAST:event_popupCotangente

    /**
     *
     */
    private void popupArcoseno(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupArcoseno
        arcoSeno();
    }//GEN-LAST:event_popupArcoseno

    /**
     *
     */
    private void popupArcocoseno(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupArcocoseno
        arcoCoseno();
    }//GEN-LAST:event_popupArcocoseno

    /**
     *
     */
    private void popupArcotangente(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupArcotangente
        arcoTangente();
    }//GEN-LAST:event_popupArcotangente

    /**
     *
     */
    private void popupArcosecante(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupArcosecante
        arcoSecante();
    }//GEN-LAST:event_popupArcosecante

    /**
     *
     */
    private void popupArcocotangente(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupArcocotangente
        arcoCotangente();
    }//GEN-LAST:event_popupArcocotangente

    /**
     *
     */
    private void popupSenoHiperbolico(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupSenoHiperbolico
        senoHiperbolico();
    }//GEN-LAST:event_popupSenoHiperbolico

    /**
     *
     */
    private void popupCosenoHiperbolico(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupCosenoHiperbolico
        cosenoHiperbolico();
    }//GEN-LAST:event_popupCosenoHiperbolico

    /**
     *
     */
    private void popupTangenteHiperbolica(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupTangenteHiperbolica
        tangenteHiperbolica();
    }//GEN-LAST:event_popupTangenteHiperbolica

    /**
     *
     */
    private void popupUnoSobre(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupUnoSobre
        unoSobre();
    }//GEN-LAST:event_popupUnoSobre

    /**
     *
     */
    private void popupRaizCuadrada(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupRaizCuadrada
        raizCuadrada();
    }//GEN-LAST:event_popupRaizCuadrada

    /**
     *
     */
    private void popupRaizCubica(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupRaizCubica
        raizCubica();
    }//GEN-LAST:event_popupRaizCubica

    /**
     *
     */
    private void popupCuadrado(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupCuadrado
        cuadrado();
    }//GEN-LAST:event_popupCuadrado

    /**
     *
     */
    private void popupCubo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupCubo
        cubo();
    }//GEN-LAST:event_popupCubo

    /**
     *
     */
    private void popupDosElevadoA(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupDosElevadoA
        dosElevadoA();
    }//GEN-LAST:event_popupDosElevadoA

    /**
     *
     */
    private void popupEElevadoA(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupEElevadoA
        eElevadoA();
    }//GEN-LAST:event_popupEElevadoA

    /**
     *
     */
    private void popupDiezElevadoA(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupDiezElevadoA
        diezElevadoA();
    }//GEN-LAST:event_popupDiezElevadoA

    /**
     *
     */
    private void popupDerivar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupDerivar
        derivar();
    }//GEN-LAST:event_popupDerivar

    /**
     *
     */
    private void popupIntegrar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupIntegrar
        integrar();
    }//GEN-LAST:event_popupIntegrar

    /**
     *
     */
    private void popupMinimo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupMinimo
        minimo();
    }//GEN-LAST:event_popupMinimo

    /**
     *
     */
    private void popupMaximo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupMaximo
        maximo();
    }//GEN-LAST:event_popupMaximo

    /**
     *
     */
    private void popupCalcularCerosFuncion(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupCalcularCerosFuncion
        calcularCerosFuncion();
    }//GEN-LAST:event_popupCalcularCerosFuncion

    /**
     *
     */
    private void popupLogaritmoNatural(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupLogaritmoNatural
        logaritmoNatural();
    }//GEN-LAST:event_popupLogaritmoNatural

    /**
     *
     */
    private void popupLogaritmo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupLogaritmo
        logaritmo();
    }//GEN-LAST:event_popupLogaritmo

    /**
     *
     */
    private void popupCero(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupCero
        numeroCero();
    }//GEN-LAST:event_popupCero

    /**
     *
     */
    private void popupUno(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupUno
        numeroUno();
    }//GEN-LAST:event_popupUno

    /**
     *
     */
    private void popupDos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupDos
        numeroDos();
    }//GEN-LAST:event_popupDos

    /**
     *
     */
    private void popupTres(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupTres
        numeroTres();
    }//GEN-LAST:event_popupTres

    /**
     *
     */
    private void popupCuatro(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupCuatro
        numeroCuatro();
    }//GEN-LAST:event_popupCuatro

    /**
     *
     */
    private void popupCinco(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupCinco
        numeroCinco();
    }//GEN-LAST:event_popupCinco

    /**
     *
     */
    private void popupSeis(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupSeis
        numeroSeis();
    }//GEN-LAST:event_popupSeis

    /**
     *
     */
    private void popupSiete(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupSiete
        numeroSiete();
    }//GEN-LAST:event_popupSiete

    /**
     *
     */
    private void popupOcho(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupOcho
        numeroOcho();
    }//GEN-LAST:event_popupOcho

    /**
     *
     */
    private void popupNueve(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupNueve
        numeroNueve();
    }//GEN-LAST:event_popupNueve

    /**
     *
     */
    private void popupPuntoDecimal(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupPuntoDecimal
        puntoDecimal();
    }//GEN-LAST:event_popupPuntoDecimal

    /**
     *
     */
    private void popupPI(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupPI
        pi();
    }//GEN-LAST:event_popupPI

    /**
     *
     */
    private void popupSuma(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupSuma
        suma();
    }//GEN-LAST:event_popupSuma

    /**
     *
     */
    private void popupResta(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupResta
        resta();
    }//GEN-LAST:event_popupResta

    /**
     *
     */
    private void popupMultiplicacion(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupMultiplicacion
        multiplicacion();
    }//GEN-LAST:event_popupMultiplicacion

    /**
     *
     */
    private void popupDivididoPor(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupDivididoPor
        division();
    }//GEN-LAST:event_popupDivididoPor

    /**
     *
     */
    private void popupDivideA(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupDivideA
        divisionInversa();
    }//GEN-LAST:event_popupDivideA

    /**
     *
     */
    private void popupElevadoA(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupElevadoA
        potenciacion();
    }//GEN-LAST:event_popupElevadoA

    /**
     *
     */
    private void popupArcoCosecante(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupArcoCosecante
        arcoCosecante();
    }//GEN-LAST:event_popupArcoCosecante

    /**
     *
     */
    private void popupPreferencias(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupPreferencias
        cambiarPreferencias();
    }//GEN-LAST:event_popupPreferencias

    /**
     *
     */
    private void popupResiduo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupResiduo
        residuo();
    }//GEN-LAST:event_popupResiduo

    /**
     *
     */
    private void popupNPR(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupNPR
        npr();
    }//GEN-LAST:event_popupNPR

    /**
     *
     */
    private void popupNCR(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupNCR
        ncr();
    }//GEN-LAST:event_popupNCR

    /**
     *
     */
    private void popupGCD(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupGCD
        gcd();
    }//GEN-LAST:event_popupGCD

    /**
     *
     */
    private void botonAyuda(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAyuda
        mostrarAyuda();
    }//GEN-LAST:event_botonAyuda

    private void popupLCM(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupLCM
        lcm();
    }//GEN-LAST:event_popupLCM

    private void popupFrecuencia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupFrecuencia
        frecuencia();
    }//GEN-LAST:event_popupFrecuencia

    private void menuPopupMasaElementosNombreSimbolo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupMasaElementosNombreSimbolo
        masaElementoNombreSimbolo();
    }//GEN-LAST:event_menuPopupMasaElementosNombreSimbolo

    private void menuPopupCurtosisFisher(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupCurtosisFisher
        curtosisFisher();
    }//GEN-LAST:event_menuPopupCurtosisFisher

    private void menuPopupDesviacionEstandar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupDesviacionEstandar
        desvEstandar();
    }//GEN-LAST:event_menuPopupDesviacionEstandar

    private void menuPopupVarianza(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupVarianza
        varianza();
    }//GEN-LAST:event_menuPopupVarianza

    private void menuPopupCuasiVarianza(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupCuasiVarianza
        cuasivarianza();
    }//GEN-LAST:event_menuPopupCuasiVarianza

    private void menuPopupCVPearson(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupCVPearson
        cvPearson();
    }//GEN-LAST:event_menuPopupCVPearson

    private void menuPopupCApertura(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupCApertura
        cApertura();
    }//GEN-LAST:event_menuPopupCApertura

    private void menuPopupRangoRelativo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupRangoRelativo
        rangoRelativo();
    }//GEN-LAST:event_menuPopupRangoRelativo

    private void menuPopupPercentil(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupPercentil
        percentil();
    }//GEN-LAST:event_menuPopupPercentil

    private void menuPopupCuartil(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupCuartil
        cuartil();
    }//GEN-LAST:event_menuPopupCuartil

    private void menuPopupDecil(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupDecil
        decil();
    }//GEN-LAST:event_menuPopupDecil

    private void menuPopupRICR(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupRICR
        rangoIntercuartilicoRelativo();
    }//GEN-LAST:event_menuPopupRICR

    private void menuPopupRSICR(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupRSICR
        rangoSemiIntercuartilicoRelativo();
    }//GEN-LAST:event_menuPopupRSICR

    private void menuPopupCABowley(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupCABowley
        cABowley();
    }//GEN-LAST:event_menuPopupCABowley

    private void menuPopupCAPearson(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupCAPearson
        cAPearson();
    }//GEN-LAST:event_menuPopupCAPearson

    private void menuPopupVolPrismaRegular(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupVolPrismaRegular
        volPrismaRegular();
    }//GEN-LAST:event_menuPopupVolPrismaRegular

    private void menuPopupVolOrtoedro(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupVolOrtoedro
        volOrtoedro();
    }//GEN-LAST:event_menuPopupVolOrtoedro

    private void menuPopupVolCubo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupVolCubo
        volCubo();
    }//GEN-LAST:event_menuPopupVolCubo

    private void menuPopupVolCilindro(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupVolCilindro
        volCilindro();
    }//GEN-LAST:event_menuPopupVolCilindro

    private void menuPopupVolEsfera(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupVolEsfera
        volEsfera();
    }//GEN-LAST:event_menuPopupVolEsfera

    private void menuPopupVolElipsoide(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupVolElipsoide
        volElipsoide();
    }//GEN-LAST:event_menuPopupVolElipsoide

    private void menuPopupVolPiramide(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupVolPiramide
        volPiramide();
    }//GEN-LAST:event_menuPopupVolPiramide

    private void menuPopupVolCono(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupVolCono
        volCono();
    }//GEN-LAST:event_menuPopupVolCono

    private void menuPopupAreaTriangulo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAreaTriangulo
        areaTriangulo();
    }//GEN-LAST:event_menuPopupAreaTriangulo

    private void menuPopupAreaTrianguloHeron(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAreaTrianguloHeron
        areaTrianguloHeron();
    }//GEN-LAST:event_menuPopupAreaTrianguloHeron

    private void menuPopupAreaCuadrilatero(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAreaCuadrilatero
        areaCuadrilatero();
    }//GEN-LAST:event_menuPopupAreaCuadrilatero

    private void menuPopupAreaRombo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAreaRombo
        areaRombo();
    }//GEN-LAST:event_menuPopupAreaRombo

    private void menuPopupAreaRectangulo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAreaRectangulo
        areaRectangulo();
    }//GEN-LAST:event_menuPopupAreaRectangulo

    private void menuPopupAreaCuadrado(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAreaCuadrado
        areaCuadrado();
    }//GEN-LAST:event_menuPopupAreaCuadrado

    private void menuPopupAreaRomboide(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAreaRomboide
        areaRomboide();
    }//GEN-LAST:event_menuPopupAreaRomboide

    private void menuPopupAreaTrapecio(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAreaTrapecio
        areaTrapecio();
    }//GEN-LAST:event_menuPopupAreaTrapecio

    private void menuPopupAreaCirculo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAreaCirculo
        areaCirculo();
    }//GEN-LAST:event_menuPopupAreaCirculo

    private void menuPopupAreaElipse(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAreaElipse
        areaElipse();
    }//GEN-LAST:event_menuPopupAreaElipse

    private void menuPopupAreaEsfera(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAreaEsfera
        areaEsfera();
    }//GEN-LAST:event_menuPopupAreaEsfera

    private void menuPopupAreaCono(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAreaCono
        areaCono();
    }//GEN-LAST:event_menuPopupAreaCono

    private void menuPopupAreaCilindro(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAreaCilindro
        areaCilindro();
    }//GEN-LAST:event_menuPopupAreaCilindro

    private void menuPopupDistanciaPuntoRecta(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupDistanciaPuntoRecta
        distanciaPuntoRecta();
    }//GEN-LAST:event_menuPopupDistanciaPuntoRecta

    private void menuPopupDistanciaPuntoPlano(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupDistanciaPuntoPlano
        distanciaPuntoPunto();
    }//GEN-LAST:event_menuPopupDistanciaPuntoPlano

    private void menuPopupDistanciaRectaRecta(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupDistanciaRectaRecta
        distanciaRectaRecta();
    }//GEN-LAST:event_menuPopupDistanciaRectaRecta

    private void menuPopupDistanciaRectaPlano(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupDistanciaRectaPlano
        distanciaRectaPlano();
    }//GEN-LAST:event_menuPopupDistanciaRectaPlano

    private void menuPopupDistanciaPlanoPlano(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupDistanciaPlanoPlano
        distanciaPlanoPlano();
    }//GEN-LAST:event_menuPopupDistanciaPlanoPlano

    private void menuPopupDistanciaPuntoPunto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupDistanciaPuntoPunto
        distanciaPuntoPunto();
    }//GEN-LAST:event_menuPopupDistanciaPuntoPunto

    private void menuPopupPerimetroCuadrado(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupPerimetroCuadrado
        perimetroCuadrado();
    }//GEN-LAST:event_menuPopupPerimetroCuadrado

    private void menuPopupPerimetroRectangulo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupPerimetroRectangulo
        perimetroRectangulo();
    }//GEN-LAST:event_menuPopupPerimetroRectangulo

    private void menuPopupPerimetroRombo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupPerimetroRombo
        perimetroRombo();
    }//GEN-LAST:event_menuPopupPerimetroRombo

    private void menuPopupPerimetroTriangulo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupPerimetroTriangulo
        perimetroTriangulo();
    }//GEN-LAST:event_menuPopupPerimetroTriangulo

    private void menuPopupPerimetroRomboide(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupPerimetroRomboide
        perimetroRomboide();
    }//GEN-LAST:event_menuPopupPerimetroRomboide

    private void menuPopupPerimetroCirculo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupPerimetroCirculo
        perimetroCirculo();
    }//GEN-LAST:event_menuPopupPerimetroCirculo

    private void menuPopupPerimetroElipse(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupPerimetroElipse
        perimetroElipse();
    }//GEN-LAST:event_menuPopupPerimetroElipse

    private void menuPopupEsPrimo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupEsPrimo
        esPrimo();
    }//GEN-LAST:event_menuPopupEsPrimo

    private void menuPopupConteoPrimos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupConteoPrimos
        conteoPrimos();
    }//GEN-LAST:event_menuPopupConteoPrimos

    private void menuPopupMenorPrimoMayorQue(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupMenorPrimoMayorQue
        menorPrimoMayorQue();
    }//GEN-LAST:event_menuPopupMenorPrimoMayorQue

    private void menuPopupIMC(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupIMC
        imc();
    }//GEN-LAST:event_menuPopupIMC

    private void menuPopupLMC(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupLMC
        lmc();
    }//GEN-LAST:event_menuPopupLMC

    private void menuPopupSegundosHoras(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupSegundosHoras
        convSegundosHoras();
    }//GEN-LAST:event_menuPopupSegundosHoras

    private void menuPopupSegundosMinutos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupSegundosMinutos
        convSegundosMinutos();
    }//GEN-LAST:event_menuPopupSegundosMinutos

    private void menuPopupHorasSegundos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupHorasSegundos
        convHorasSegundos();
    }//GEN-LAST:event_menuPopupHorasSegundos

    private void menuPopupMinutosSegundos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupMinutosSegundos
        convMinutosSegundos();
    }//GEN-LAST:event_menuPopupMinutosSegundos

    private void menuPopupMilisegundosSegundos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupMilisegundosSegundos
        convMilisegundosSegundos();
    }//GEN-LAST:event_menuPopupMilisegundosSegundos

    private void menuPopupSegundosMilisegundos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupSegundosMilisegundos
        convSegundosMilisegundos();
    }//GEN-LAST:event_menuPopupSegundosMilisegundos

    private void menuPopupMasaElementosNumero(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupMasaElementosNumero
        masaElementosNumero();
    }//GEN-LAST:event_menuPopupMasaElementosNumero

    private void menuPopupSegundaDerivadapopupDerivar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupSegundaDerivadapopupDerivar
        segundaDerivada();
    }//GEN-LAST:event_menuPopupSegundaDerivadapopupDerivar

    private void menuPopupTerceraDerivadapopupDerivar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupTerceraDerivadapopupDerivar
        terceraDerivada();
    }//GEN-LAST:event_menuPopupTerceraDerivadapopupDerivar

    private void menuPopupCuartaDerivadapopupDerivar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupCuartaDerivadapopupDerivar
        cuartaDerivada();
    }//GEN-LAST:event_menuPopupCuartaDerivadapopupDerivar

    private void menuPopupMinimoDatos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupMinimoDatos
        minimoDatos();
    }//GEN-LAST:event_menuPopupMinimoDatos

    private void menuPopupMaximoDatos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupMaximoDatos
        maximoDatos();
    }//GEN-LAST:event_menuPopupMaximoDatos

    private void menuPopupPromedio(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupPromedio
        promedio();
    }//GEN-LAST:event_menuPopupPromedio

    private void menuPopupContar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupContar
        contarDatos();
    }//GEN-LAST:event_menuPopupContar

    private void menuPopupContarSi(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupContarSi
        contarSiDatos();
    }//GEN-LAST:event_menuPopupContarSi

    private void menuPopupModa(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupModa
        moda();
    }//GEN-LAST:event_menuPopupModa

    private void menuPopupSuma(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupSuma
        sumatoria();
    }//GEN-LAST:event_menuPopupSuma

    private void menuPopupSumaProducto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupSumaProducto
        sumaProducto();
    }//GEN-LAST:event_menuPopupSumaProducto

    private void menuPopupMediaGeometrica(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupMediaGeometrica
        mediaGeometrica();
    }//GEN-LAST:event_menuPopupMediaGeometrica

    private void menuPopupMediaArmonica(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupMediaArmonica
        mediaArmonica();
    }//GEN-LAST:event_menuPopupMediaArmonica

    private void menuPopupMediana(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupMediana
        mediana();
    }//GEN-LAST:event_menuPopupMediana

    private void menuPopupAleatorioGaussiano(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAleatorioGaussiano
        aleatorioGaussiano();
    }//GEN-LAST:event_menuPopupAleatorioGaussiano

    private void menuPopupAleatorioUniforme(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPopupAleatorioUniforme
        aleatorioUniforme();
    }//GEN-LAST:event_menuPopupAleatorioUniforme

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info :
                    javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException |
                javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaCalculo.class
                    .getName()).log(java.util.logging.Level.SEVERE, null,
                    ex);
        }
        //</editor-fold>
        //</editor-fold>

//        try {
//            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager
//                    .getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException 
//                | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(VentanaCalculo.class.getName()).log(Level.SEVERE, 
//                    null, ex);
//        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Calculadora.getVentana().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton33;
    private javax.swing.JButton jButton34;
    private javax.swing.JButton jButton35;
    private javax.swing.JButton jButton36;
    private javax.swing.JButton jButton37;
    private javax.swing.JButton jButton38;
    private javax.swing.JButton jButton39;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton40;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton43;
    private javax.swing.JButton jButton44;
    private javax.swing.JButton jButton45;
    private javax.swing.JButton jButton46;
    private javax.swing.JButton jButton47;
    private javax.swing.JButton jButton48;
    private javax.swing.JButton jButton49;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton50;
    private javax.swing.JButton jButton51;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton53;
    private javax.swing.JButton jButton54;
    private javax.swing.JButton jButton55;
    private javax.swing.JButton jButton56;
    private javax.swing.JButton jButton57;
    private javax.swing.JButton jButton58;
    private javax.swing.JButton jButton59;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator15;
    private javax.swing.JPopupMenu.Separator jSeparator17;
    private javax.swing.JPopupMenu.Separator jSeparator18;
    private javax.swing.JPopupMenu.Separator jSeparator19;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JMenuItem menuPopupAgregarFuncion;
    private javax.swing.JMenu menuPopupAleatorio;
    private javax.swing.JMenuItem menuPopupAleatorioGaussiano;
    private javax.swing.JMenuItem menuPopupAleatorioUniforme;
    private javax.swing.JMenuItem menuPopupArcoCosecante;
    private javax.swing.JMenuItem menuPopupArcoCoseno;
    private javax.swing.JMenuItem menuPopupArcoCotangente;
    private javax.swing.JMenuItem menuPopupArcoSecante;
    private javax.swing.JMenuItem menuPopupArcoSeno;
    private javax.swing.JMenuItem menuPopupArcoTangente;
    private javax.swing.JMenu menuPopupArea;
    private javax.swing.JMenuItem menuPopupAreaCilindro;
    private javax.swing.JMenuItem menuPopupAreaCirculo;
    private javax.swing.JMenuItem menuPopupAreaCono;
    private javax.swing.JMenuItem menuPopupAreaCuadrado;
    private javax.swing.JMenuItem menuPopupAreaCuadrilatero;
    private javax.swing.JMenuItem menuPopupAreaElipse;
    private javax.swing.JMenuItem menuPopupAreaEsfera;
    private javax.swing.JMenuItem menuPopupAreaRectangulo;
    private javax.swing.JMenuItem menuPopupAreaRombo;
    private javax.swing.JMenuItem menuPopupAreaRomboide;
    private javax.swing.JMenuItem menuPopupAreaTrapecio;
    private javax.swing.JMenuItem menuPopupAreaTriangulo;
    private javax.swing.JMenuItem menuPopupAreaTrianguloHeron;
    private javax.swing.JMenuItem menuPopupCABowley;
    private javax.swing.JMenuItem menuPopupCAPearson;
    private javax.swing.JMenuItem menuPopupCApertura;
    private javax.swing.JMenuItem menuPopupCVPearson;
    private javax.swing.JMenu menuPopupCentralizacion;
    private javax.swing.JMenuItem menuPopupCombinaciones;
    private javax.swing.JMenuItem menuPopupContar;
    private javax.swing.JMenuItem menuPopupContarSi;
    private javax.swing.JMenuItem menuPopupConteoPrimos;
    private javax.swing.JMenuItem menuPopupCopiar;
    private javax.swing.JMenuItem menuPopupCortar;
    private javax.swing.JMenuItem menuPopupCosecante;
    private javax.swing.JMenuItem menuPopupCoseno;
    private javax.swing.JMenuItem menuPopupCosenoHiperbolico;
    private javax.swing.JMenuItem menuPopupCotangente;
    private javax.swing.JMenuItem menuPopupCuartaDerivada;
    private javax.swing.JMenuItem menuPopupCuartil;
    private javax.swing.JMenuItem menuPopupCuasiVarianza;
    private javax.swing.JMenuItem menuPopupCurtosisFisher;
    private javax.swing.JMenuItem menuPopupDecil;
    private javax.swing.JMenuItem menuPopupDerivacionUnaVariable;
    private javax.swing.JMenuItem menuPopupDeshacer;
    private javax.swing.JMenuItem menuPopupDesviacionEstandar;
    private javax.swing.JMenu menuPopupDistancia;
    private javax.swing.JMenuItem menuPopupDistanciaPlanoPlano;
    private javax.swing.JMenuItem menuPopupDistanciaPuntoPlano;
    private javax.swing.JMenuItem menuPopupDistanciaPuntoPunto;
    private javax.swing.JMenuItem menuPopupDistanciaPuntoRecta;
    private javax.swing.JMenuItem menuPopupDistanciaRectaPlano;
    private javax.swing.JMenuItem menuPopupDistanciaRectaRecta;
    private javax.swing.JMenuItem menuPopupDivisionInversa;
    private javax.swing.JMenuItem menuPopupDivisionUnoDivididoX;
    private javax.swing.JMenuItem menuPopupEliminarContenido;
    private javax.swing.JMenuItem menuPopupEsPrimo;
    private javax.swing.JMenuItem menuPopupEvaluarExpresion;
    private javax.swing.JMenu menuPopupExponenciales;
    private javax.swing.JMenuItem menuPopupFactorial;
    private javax.swing.JMenuItem menuPopupFrecuencia;
    private javax.swing.JMenu menuPopupFunciones;
    private javax.swing.JMenu menuPopupFuncionesDerivacion;
    private javax.swing.JMenu menuPopupFuncionesEstadísticas;
    private javax.swing.JMenu menuPopupFuncionesGeometricas;
    private javax.swing.JMenu menuPopupFuncionesSalud;
    private javax.swing.JMenu menuPopupFuncionesTiempo;
    private javax.swing.JMenuItem menuPopupGraficarExpresion;
    private javax.swing.JMenu menuPopupHiperbolicas;
    private javax.swing.JMenuItem menuPopupHipotenusa;
    private javax.swing.JMenuItem menuPopupHorasSegundos;
    private javax.swing.JMenuItem menuPopupIMC;
    private javax.swing.JMenuItem menuPopupIgualarACeroFuncion;
    private javax.swing.JMenu menuPopupInsertarOperacion;
    private javax.swing.JMenuItem menuPopupIntegracion;
    private javax.swing.JMenuItem menuPopupLMC;
    private javax.swing.JMenuItem menuPopupLogaritmo;
    private javax.swing.JMenuItem menuPopupLogaritmoNatural;
    private javax.swing.JMenu menuPopupLogaritmos;
    private javax.swing.JMenuItem menuPopupMCD;
    private javax.swing.JMenuItem menuPopupMCM;
    private javax.swing.JMenuItem menuPopupMasaElementosNombreSimbolo;
    private javax.swing.JMenuItem menuPopupMasaElementosNumero;
    private javax.swing.JMenuItem menuPopupMaximoDatos;
    private javax.swing.JMenuItem menuPopupMaximoFuncion;
    private javax.swing.JMenuItem menuPopupMediaArmonica;
    private javax.swing.JMenuItem menuPopupMediaGeometrica;
    private javax.swing.JMenuItem menuPopupMediana;
    private javax.swing.JMenuItem menuPopupMenorPrimoMayorQue;
    private javax.swing.JMenu menuPopupMetodosNumericos;
    private javax.swing.JMenuItem menuPopupMilisegundosSegundos;
    private javax.swing.JMenuItem menuPopupMinimoDatos;
    private javax.swing.JMenuItem menuPopupMinimoFuncion;
    private javax.swing.JMenuItem menuPopupMinutosSegundos;
    private javax.swing.JMenuItem menuPopupModa;
    private javax.swing.JMenuItem menuPopupNumeroCero;
    private javax.swing.JMenuItem menuPopupNumeroCinco;
    private javax.swing.JMenuItem menuPopupNumeroCuatro;
    private javax.swing.JMenuItem menuPopupNumeroDos;
    private javax.swing.JMenuItem menuPopupNumeroNueve;
    private javax.swing.JMenuItem menuPopupNumeroOcho;
    private javax.swing.JMenuItem menuPopupNumeroPI;
    private javax.swing.JMenuItem menuPopupNumeroSeis;
    private javax.swing.JMenuItem menuPopupNumeroSiete;
    private javax.swing.JMenuItem menuPopupNumeroTres;
    private javax.swing.JMenuItem menuPopupNumeroUno;
    private javax.swing.JMenu menuPopupNumeros;
    private javax.swing.JMenuItem menuPopupOperacionDivision;
    private javax.swing.JMenuItem menuPopupOperacionMultiplicacion;
    private javax.swing.JMenuItem menuPopupOperacionResta;
    private javax.swing.JMenuItem menuPopupOperacionSuma;
    private javax.swing.JMenuItem menuPopupParentesis;
    private javax.swing.JMenuItem menuPopupParentesisDerecho;
    private javax.swing.JMenuItem menuPopupParentesisIzquierdo;
    private javax.swing.JMenuItem menuPopupPegar;
    private javax.swing.JMenuItem menuPopupPercentil;
    private javax.swing.JMenu menuPopupPerimetro;
    private javax.swing.JMenuItem menuPopupPerimetroCirculo;
    private javax.swing.JMenuItem menuPopupPerimetroCuadrado;
    private javax.swing.JMenuItem menuPopupPerimetroElipse;
    private javax.swing.JMenuItem menuPopupPerimetroRectangulo;
    private javax.swing.JMenuItem menuPopupPerimetroRombo;
    private javax.swing.JMenuItem menuPopupPerimetroRomboide;
    private javax.swing.JMenuItem menuPopupPerimetroTriangulo;
    private javax.swing.JMenuItem menuPopupPermutaciones;
    private javax.swing.JMenu menuPopupPosicionNoCentral;
    private javax.swing.JMenuItem menuPopupPotenciacion;
    private javax.swing.JMenuItem menuPopupPotenciacionDiezElevadoX;
    private javax.swing.JMenuItem menuPopupPotenciacionDosElevadoX;
    private javax.swing.JMenuItem menuPopupPotenciacionElevarCuadrado;
    private javax.swing.JMenuItem menuPopupPotenciacionElevarCubo;
    private javax.swing.JMenuItem menuPopupPotenciacionExponencial;
    private javax.swing.JMenuItem menuPopupPotenciacionRaizCuadrada;
    private javax.swing.JMenuItem menuPopupPotenciacionRaizCubica;
    private javax.swing.JMenuItem menuPopupPreferencias;
    private javax.swing.JMenu menuPopupPrimos;
    private javax.swing.JMenuItem menuPopupPromedio;
    private javax.swing.JMenuItem menuPopupPuntoDecimal;
    private javax.swing.JMenu menuPopupQuimica;
    private javax.swing.JMenuItem menuPopupRICR;
    private javax.swing.JMenuItem menuPopupRSICR;
    private javax.swing.JMenuItem menuPopupRangoRelativo;
    private javax.swing.JMenuItem menuPopupRedondeoEntero;
    private javax.swing.JMenuItem menuPopupRehacer;
    private javax.swing.JMenuItem menuPopupResiduo;
    private javax.swing.JMenuItem menuPopupSecante;
    private javax.swing.JMenuItem menuPopupSegundaDerivada;
    private javax.swing.JMenuItem menuPopupSegundosHoras;
    private javax.swing.JMenuItem menuPopupSegundosMilisegundos;
    private javax.swing.JMenuItem menuPopupSegundosMinutos;
    private javax.swing.JMenuItem menuPopupSeno;
    private javax.swing.JMenuItem menuPopupSenoHiperbolico;
    private javax.swing.JMenuItem menuPopupSeparadorArgumento;
    private javax.swing.JMenuItem menuPopupSuma;
    private javax.swing.JMenuItem menuPopupSumaProducto;
    private javax.swing.JMenuItem menuPopupTangente;
    private javax.swing.JMenuItem menuPopupTangenteHiperbolica;
    private javax.swing.JMenuItem menuPopupTerceraDerivada;
    private javax.swing.JMenu menuPopupTrigonometricas;
    private javax.swing.JMenuItem menuPopupVarianza;
    private javax.swing.JMenuItem menuPopupVolCilindro;
    private javax.swing.JMenuItem menuPopupVolCono;
    private javax.swing.JMenuItem menuPopupVolCubo;
    private javax.swing.JMenuItem menuPopupVolElipsoide;
    private javax.swing.JMenuItem menuPopupVolEsfera;
    private javax.swing.JMenuItem menuPopupVolOrtoedro;
    private javax.swing.JMenuItem menuPopupVolPiramide;
    private javax.swing.JMenuItem menuPopupVolPrismaRegular;
    private javax.swing.JMenu menuPopupVolumen;
    private javax.swing.JPopupMenu.Separator separadorMenu1;
    private javax.swing.JPopupMenu.Separator separadorMenu2;
    private javax.swing.JPopupMenu.Separator separadorMenu3;
    private javax.swing.JPopupMenu.Separator separadorMenu4;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    private void cambiarPreferencias() {
        Calculadora.getPreferencias().setVisible(true);
        setVisible(false);
        jComboBox1.requestFocus();
    }

    /**
     *
     */
    public void cambiarColorBoton() {
        jButton1.setBackground(colorBoton);
        jButton10.setBackground(colorBoton);
        jButton11.setBackground(colorBoton);
        jButton12.setBackground(colorBoton);
        jButton13.setBackground(colorBoton);
        jButton14.setBackground(colorBoton);
        jButton15.setBackground(colorBoton);
        jButton16.setBackground(colorBoton);
        jButton17.setBackground(colorBoton);
        jButton18.setBackground(colorBoton);
        jButton19.setBackground(colorBoton);
        jButton2.setBackground(colorBoton);
        jButton20.setBackground(colorBoton);
        jButton21.setBackground(colorBoton);
        jButton22.setBackground(colorBoton);
        jButton23.setBackground(colorBoton);
        jButton24.setBackground(colorBoton);
        jButton25.setBackground(colorBoton);
        jButton26.setBackground(colorBoton);
        jButton27.setBackground(colorBoton);
        jButton28.setBackground(colorBoton);
        jButton29.setBackground(colorBoton);
        jButton3.setBackground(colorBoton);
        jButton30.setBackground(colorBoton);
        jButton31.setBackground(colorBoton);
        jButton32.setBackground(colorBoton);
        jButton33.setBackground(colorBoton);
        jButton34.setBackground(colorBoton);
        jButton35.setBackground(colorBoton);
        jButton36.setBackground(colorBoton);
        jButton37.setBackground(colorBoton);
        jButton38.setBackground(colorBoton);
        jButton39.setBackground(colorBoton);
        jButton4.setBackground(colorBoton);
        jButton40.setBackground(colorBoton);
        jButton41.setBackground(colorBoton);
        jButton42.setBackground(colorBoton);
        jButton43.setBackground(colorBoton);
        jButton44.setBackground(colorBoton);
        jButton45.setBackground(colorBoton);
        jButton46.setBackground(colorBoton);
        jButton47.setBackground(colorBoton);
        jButton48.setBackground(colorBoton);
        jButton49.setBackground(colorBoton);
        jButton5.setBackground(colorBoton);
        jButton50.setBackground(colorBoton);
        jButton51.setBackground(colorBoton);
        jButton52.setBackground(colorBoton);
        jButton53.setBackground(colorBoton);
        jButton54.setBackground(colorBoton);
        jButton55.setBackground(colorBoton);
        jButton56.setBackground(colorBoton);
        jButton57.setBackground(colorBoton);
        jButton58.setBackground(colorBoton);
        jButton6.setBackground(colorBoton);
        jButton7.setBackground(colorBoton);
        jButton8.setBackground(colorBoton);
        jButton9.setBackground(colorBoton);
    }

    /**
     *
     */
    public void cambiarColorFondo() {
        jPanel1.setBackground(colorFondo);
        jPanel2.setBackground(colorFondo);
        jPanel3.setBackground(colorFondo);
        jPanel4.setBackground(colorFondo);
        jPanel5.setBackground(colorFondo);
    }

    /**
     *
     */
    public void cambiarColorFuente() {
        jButton1.setForeground(colorFuente);
        jButton10.setForeground(colorFuente);
        jButton11.setForeground(colorFuente);
        jButton12.setForeground(colorFuente);
        jButton13.setForeground(colorFuente);
        jButton14.setForeground(colorFuente);
        jButton15.setForeground(colorFuente);
        jButton16.setForeground(colorFuente);
        jButton17.setForeground(colorFuente);
        jButton18.setForeground(colorFuente);
        jButton19.setForeground(colorFuente);
        jButton2.setForeground(colorFuente);
        jButton20.setForeground(colorFuente);
        jButton21.setForeground(colorFuente);
        jButton22.setForeground(colorFuente);
        jButton23.setForeground(colorFuente);
        jButton24.setForeground(colorFuente);
        jButton25.setForeground(colorFuente);
        jButton26.setForeground(colorFuente);
        jButton27.setForeground(colorFuente);
        jButton28.setForeground(colorFuente);
        jButton29.setForeground(colorFuente);
        jButton3.setForeground(colorFuente);
        jButton30.setForeground(colorFuente);
        jButton31.setForeground(colorFuente);
        jButton32.setForeground(colorFuente);
        jButton33.setForeground(colorFuente);
        jButton34.setForeground(colorFuente);
        jButton35.setForeground(colorFuente);
        jButton36.setForeground(colorFuente);
        jButton37.setForeground(colorFuente);
        jButton38.setForeground(colorFuente);
        jButton39.setForeground(colorFuente);
        jButton4.setForeground(colorFuente);
        jButton40.setForeground(colorFuente);
        jButton41.setForeground(colorFuente);
        jButton42.setForeground(colorFuente);
        jButton43.setForeground(colorFuente);
        jButton44.setForeground(colorFuente);
        jButton45.setForeground(colorFuente);
        jButton46.setForeground(colorFuente);
        jButton47.setForeground(colorFuente);
        jButton48.setForeground(colorFuente);
        jButton49.setForeground(colorFuente);
        jButton5.setForeground(colorFuente);
        jButton50.setForeground(colorFuente);
        jButton51.setForeground(colorFuente);
        jButton52.setForeground(colorFuente);
        jButton53.setForeground(colorFuente);
        jButton54.setForeground(colorFuente);
        jButton55.setForeground(colorFuente);
        jButton56.setForeground(colorFuente);
        jButton57.setForeground(colorFuente);
        jButton58.setForeground(colorFuente);
        jButton6.setForeground(colorFuente);
        jButton7.setForeground(colorFuente);
        jButton8.setForeground(colorFuente);
        jButton9.setForeground(colorFuente);
        jPanel1.setForeground(colorFuente);
        jPanel2.setForeground(colorFuente);
        jPanel3.setForeground(colorFuente);
        jPanel4.setForeground(colorFuente);
        jPanel5.setForeground(colorFuente);
    }

    /**
     *
     * @param precision
     */
    public static void setPrecision(int precision) {
        VentanaCalculo.precision = precision;
    }

    /**
     *
     * @return
     */
    public static Color getColorFondo() {
        return colorFondo;
    }

    /**
     *
     * @param colorFondo
     */
    public static void setColorFondo(Color colorFondo) {
        VentanaCalculo.colorFondo = colorFondo;
    }

    /**
     *
     * @return
     */
    public static Color getColorBoton() {
        return colorBoton;
    }

    /**
     *
     * @param colorBoton
     */
    public static void setColorBoton(Color colorBoton) {
        VentanaCalculo.colorBoton = colorBoton;
    }

    /**
     *
     * @return
     */
    public static Color getColorFuente() {
        return colorFuente;
    }

    /**
     *
     * @param colorFuente
     */
    public static void setColorFuente(Color colorFuente) {
        VentanaCalculo.colorFuente = colorFuente;
    }

    /**
     *
     */
    private void agregarFuncion(String cadenaActual) {
        if(cadenaActual.trim().length() == 0) {
            JOptionPane.showMessageDialog(this,
                            "Error: Cadena vacía", "Error:Expresión inválida",
                            JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            datos.Funcion.agregarFuncion(cadenaActual);
            cambiarDinamicamenteAreaTexto((cadenaActual + "\n").length());
            jTextArea1.append(cadenaActual + "\n");
        } catch (CaracterIlegal ex) {
            int indice = ex.getIndice();

            if (indice >= 0) {
                JOptionPane.showMessageDialog(this,
                        "Error: Caracter inválido cerca de \'" + cadenaActual
                        .charAt(indice) + "\'", "Error:Expresión inválida",
                        JOptionPane.ERROR_MESSAGE);
                javax.swing.JTextField campoTexto =
                        (javax.swing.JTextField) jComboBox1
                        .getEditor().getEditorComponent();
                campoTexto.setSelectionStart(indice);
                campoTexto.setSelectionEnd(indice + 1);
                campoTexto.requestFocusInWindow();
            }
        } finally {
            setCursor(Cursor.getDefaultCursor());
            jComboBox1.requestFocus();
        }
    }

    /**
     *
     */
    private Object agregarCadenaListaCombo(final String item) {
        return new Object() {
            @Override
            public String toString() {
                return item;
            }
        };
    }

    /**
     *
     */
    private void copiar() throws HeadlessException {
        String cadenaActual;
        String texto;

        if ((texto = jTextArea1.getSelectedText()) == null && !jTextArea1
                .hasFocus()) {
            javax.swing.JTextField campoTexto =
                    (javax.swing.JTextField) jComboBox1
                    .getEditor().getEditorComponent();
            int inicio;
            int fin;
            inicio = campoTexto.getSelectionStart();
            fin = campoTexto.getSelectionEnd();
            cadenaActual = campoTexto.getText();

            if (inicio != fin) {
                texto = cadenaActual.substring(inicio, fin);
            } else {
                texto = cadenaActual;
            }
        }

        if (texto.length() > 0) {
            Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit()
                    .getSystemClipboard();
            StringSelection data = new StringSelection(texto);
            clipboard.setContents(data, data);
        }
        
        jComboBox1.requestFocus();
    }

    /**
     *
     */
    private void hipotenusa() {
        englobarEnEditor(" hipot(", ", ) ");
    }

    /**
     *
     */
    private void redondeoEntero() {
        englobarEnEditor(" rent(", ") ");
    }

    /**
     *
     */
    private void senoHiperbolico() {
        englobarEnEditor(" senh(", ") ");
    }

    /**
     *
     */
    private void cosenoHiperbolico() {
        englobarEnEditor(" cosh(", ") ");
    }

    /**
     *
     */
    private void tangenteHiperbolica() {
        englobarEnEditor(" tanh(", ") ");
    }

    /**
     *
     */
    private void dosElevadoA() {
        decidirEnglobarReemplazarEnEditorIzquierda(" 2 ^ ");
    }

    /**
     *
     */
    private void logaritmo() {
        englobarEnEditor(" log(10, ", ") ");
    }

    /**
     *
     */
    private void arcoTangente() {
        englobarEnEditor(" atan(", ") ");
    }

    /**
     *
     */
    private void arcoCotangente() {
        englobarEnEditor(" acot(", ") ");
    }

    /**
     *
     */
    private void calcularCerosFuncion() throws HeadlessException {
        procesarCadena(((javax.swing.JTextField) jComboBox1.getEditor()
                .getEditorComponent()).getText() + " == 0");
    }

    /**
     *
     */
    private void arcoSeno() {
        englobarEnEditor(" asen(", ") ");
    }

    /**
     *
     */
    private void arcoSecante() {
        englobarEnEditor(" asec(", ") ");
    }

    /**
     *
     */
    private void arcoCosecante() {
        englobarEnEditor(" acsc(", ") ");
    }

    /**
     *
     */
    private void arcoCoseno() {
        englobarEnEditor(" acos(", ") ");
    }

    /**
     *
     */
    private void factorial() {
        englobarEnEditor(" fact(", ")");
    }

    /**
     *
     */
    private void raizCuadrada() {
        decidirEnglobarReemplazarEnEditorDerecha(" ^ (1 / 2)");
    }

    /**
     *
     */
    private void raizCubica() {
        decidirEnglobarReemplazarEnEditorDerecha(" ^ (1 / 3)");
    }

    /**
     *
     */
    private void cubo() {
        decidirEnglobarReemplazarEnEditorDerecha(" ^ 3 ");
    }

    /**
     *
     */
    private void cuadrado() {
        decidirEnglobarReemplazarEnEditorDerecha(" ^ 2 ");
    }

    /**
     *
     */
    private void diezElevadoA() {
        decidirEnglobarReemplazarEnEditorIzquierda(" 10 ^ ");
    }

    /**
     *
     */
    private void unoSobre() {
        decidirEnglobarReemplazarEnEditorIzquierda(" 1 / ");
    }

    /**
     *
     */
    private void logaritmoNatural() {
        englobarEnEditor(" ln(", ") ");
    }

    /**
     *
     */
    private void pi() {
        BigDecimal impresor = PI.setScale(VentanaCalculo.precision,
                BigDecimal.ROUND_HALF_UP);
        reemplazarEnEditor(" " + impresor + " ");
    }

    /**
     *
     */
    private void eElevadoA() {
        BigDecimal impresor = E.setScale(VentanaCalculo.precision,
                BigDecimal.ROUND_HALF_UP);
        decidirEnglobarReemplazarEnEditorIzquierda(impresor + "^");
    }

    /**
     *
     */
    private void limpiarTexto() {
        listaDeshacer.push(((javax.swing.JTextField) jComboBox1.getEditor()
                .getEditorComponent()).getText());
        listaRehacer = new LinkedList<>();

        javax.swing.JTextField campoTexto = (javax.swing.JTextField) jComboBox1
                .getEditor().getEditorComponent();
        int inicio = campoTexto.getSelectionStart();
        int fin = campoTexto.getSelectionEnd();

        if (inicio == fin) {
            campoTexto.setText("");
        } else {
            reemplazarEnEditor("");
        }
        
        jComboBox1.requestFocus();
    }

    /**
     *
     */
    private void cotangente() {
        englobarEnEditor(" cot(", ") ");
    }

    /**
     *
     */
    private void tangente() {
        englobarEnEditor(" tan(", ") ");
    }

    /**
     *
     */
    private void cosecante() {
        englobarEnEditor(" csc(", ") ");
    }

    /**
     *
     */
    private void secante() {
        englobarEnEditor(" sec(", ") ");
    }

    /**
     *
     */
    private void seno() {
        englobarEnEditor(" sen(", ") ");
    }

    /**
     *
     */
    private void coseno() {
        englobarEnEditor(" cos(", ") ");
    }

    /**
     *
     */
    private void rehacer() {
        if (!listaRehacer.isEmpty()) {
            javax.swing.JTextField campoTexto =
                    (javax.swing.JTextField) jComboBox1.getEditor()
                    .getEditorComponent();
            String estadoActual = campoTexto.getText();
            String estadoAnterior = listaRehacer.poll();
            listaDeshacer.push(estadoActual);
            campoTexto.setText(estadoAnterior);
        }
        
        jComboBox1.requestFocus();
    }

    /**
     *
     */
    private void deshacer() {
        if (!listaDeshacer.isEmpty()) {
            javax.swing.JTextField campoTexto =
                    (javax.swing.JTextField) jComboBox1.getEditor()
                    .getEditorComponent();
            String estadoActual = campoTexto.getText();
            String estadoAnterior = listaDeshacer.pop();
            listaRehacer.offer(estadoActual);
            campoTexto.setText(estadoAnterior);
        }
        
        jComboBox1.requestFocus();
    }

    /**
     *
     */
    private void pegar() throws HeadlessException {
        try {
            Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable t = cb.getContents(this);

            // Construimos el DataFlavor correspondiente al String java
            DataFlavor dataFlavorStringJava = new DataFlavor(
                    "application/x-java-serialized-object; class=java.lang.String");

            // Y si el dato se puede conseguir como String java, lo sacamos por pantalla
            if (t.isDataFlavorSupported(dataFlavorStringJava)) {
                String texto = (String) t.getTransferData(dataFlavorStringJava);
                javax.swing.JTextField campoTexto =
                        (javax.swing.JTextField) jComboBox1
                        .getEditor().getEditorComponent();
                int inicio = campoTexto.getSelectionStart();
                int fin = campoTexto.getSelectionEnd();
                campoTexto.setText(reemplazarEnCadena(campoTexto.getText(),
                        texto, inicio, fin));
            }
        } catch (ClassNotFoundException | UnsupportedFlavorException |
                IOException ex) {
            Logger.getLogger(VentanaCalculo.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        
        jComboBox1.requestFocus();
    }

    /**
     *
     */
    private void cortar() throws HeadlessException {
        javax.swing.JTextField campoTexto = (javax.swing.JTextField) jComboBox1
                .getEditor().getEditorComponent();
        int inicio = campoTexto.getSelectionStart();
        int fin = campoTexto.getSelectionEnd();
        String cadenaActual = campoTexto.getText();
        String texto = cadenaActual.substring(inicio, fin);
        campoTexto.setText(reemplazarEnCadena(cadenaActual, "", inicio, fin));

        if (texto.length() > 0) {
            Clipboard clipboard = java.awt.Toolkit.getDefaultToolkit()
                    .getSystemClipboard();
            StringSelection data = new StringSelection(texto);
            clipboard.setContents(data, data);
        }
        
        jComboBox1.requestFocus();
    }

    /**
     *
     */
    private void nuevaFuncion() {
        javax.swing.JTextField campoTexto = (javax.swing.JTextField) jComboBox1
                .getEditor().getEditorComponent();
        agregarFuncion(campoTexto.getText());
    }

    /**
     *
     */
    private void procesarExpresion() {
        preProcesarCadena(((javax.swing.JTextField) jComboBox1.getEditor()
                .getEditorComponent()).getText());
    }

    /**
     *
     */
    private void graficar() {
        graficarCadena(((javax.swing.JTextField) jComboBox1.getEditor()
                .getEditorComponent()).getText());
    }

    /**
     *
     */
    private void divisionInversa() {
        reemplazarEnEditor(" \\ ");
    }

    /**
     *
     */
    private void numeroOcho() {
        reemplazarEnEditor("8");
    }

    /**
     *
     */
    private void numeroSiete() {
        reemplazarEnEditor("7");
    }

    /**
     *
     */
    private void division() {
        reemplazarEnEditor(" / ");
    }

    /**
     *
     */
    private void numeroUno() {
        reemplazarEnEditor("1");
    }

    /**
     *
     */
    private void numeroDos() {
        reemplazarEnEditor("2");
    }

    /**
     *
     */
    private void numeroCero() {
        reemplazarEnEditor("0");
    }

    /**
     *
     */
    private void puntoDecimal() {
        reemplazarEnEditor(".");
    }

    /**
     *
     */
    private void numeroTres() {
        reemplazarEnEditor("3");
    }

    /**
     *
     */
    private void numeroSeis() {
        reemplazarEnEditor("6");
    }

    /**
     *
     */
    private void numeroCinco() {
        reemplazarEnEditor("5");
    }

    /**
     *
     */
    private void numeroCuatro() {
        reemplazarEnEditor("4");
    }

    /**
     *
     */
    private void numeroNueve() {
        reemplazarEnEditor("9");
    }

    /**
     *
     */
    private void suma() {
        reemplazarEnEditor(" + ");
    }

    /**
     *
     */
    private void resta() {
        reemplazarEnEditor(" -");
    }

    /**
     *
     */
    private void multiplicacion() {
        reemplazarEnEditor(" * ");
    }

    /**
     *
     */
    private void potenciacion() {
        reemplazarEnEditor(" ^ ");
    }

    /**
     *
     */
    private void separadorArgumentoFuncion() {
        reemplazarEnEditor(", ");
    }

    /**
     *
     */
    private void parentesisIzquierdo() {
        reemplazarEnEditor(" (");
    }

    /**
     *
     */
    private void parentesisDerecho() {
        reemplazarEnEditor(") ");
    }

    /**
     *
     */
    private void parentesis() {
        englobarEnEditor(" (", ") ");
    }

    /**
     *
     */
    private void derivar() {
        englobarEnEditor("derivar(", ", 0)");
    }

    /**
     *
     */
    private void integrar() {
        englobarEnEditor("integrar(", ", 0, 10)");
    }

    /**
     *
     */
    private void minimo() {
        englobarEnEditor("min(", ", 0, 10)");
    }

    /**
     *
     */
    private void maximo() {
        englobarEnEditor("max(", ", 0, 10)");
    }

    /**
     *
     */
    private void cambiarDinamicamenteAreaTexto(int tamanioSalida) {
        jTextArea1.setColumns(Math.max(jTextArea1.getColumns(), 
                tamanioSalida));
        jTextArea1.setRows(jTextArea1.getRows() + 1);
    }

    /**
     *
     */
    private void residuo() {
        englobarEnEditor("res(", ", 0)");
    }

    /**
     *
     */
    private void npr() {
        englobarEnEditor("npr(", ", 0)");
    }
    
    /**
     *
     */
    private void ncr() {
        englobarEnEditor("ncr(", ", 0)");
    }

    /**
     *
     */
    private void gcd() {
        englobarEnEditor("gcd(", ", 0)");
    }

    /**
     *
     */
    private String descripcion(String descripcion) {
        return Funcion.obtenerDescripcion(descripcion);
    }

    /**
     *
     */
    private void mostrarAyuda() {
        Calculadora.getVentanaAyuda().setVisible(true);
        setVisible(false);
    }

    private void lcm() {
        englobarEnEditor("lcm(", ", 0)");
    }

    private void frecuencia() {
        englobarEnEditor("frecuencia(", ", 0)");
    }

    private void masaElementoNombreSimbolo() {
        englobarEnEditor("masaelemento(", ")");
    }

    private void curtosisFisher() {
        englobarEnEditor("cfisher(", ")");
    }

    private void desvEstandar() {
        englobarEnEditor("desvestandar(", ")");
    }

    private void varianza() {
        englobarEnEditor("var(", ")");
    }

    private void cuasivarianza() {
        englobarEnEditor("cuasivar(", ")");
    }

    private void cvPearson() {
        englobarEnEditor("cvar(", ")");
    }

    private void cApertura() {
        englobarEnEditor("coefa(", ")");
    }

    private void rangoRelativo() {
        englobarEnEditor("rangor(", ")");
    }

    private void percentil() {
        englobarEnEditor("percentil(", ", 0)");
    }

    private void cuartil() {
        englobarEnEditor("cuartil(", ", 0)");
    }

    private void decil() {
        englobarEnEditor("decil(", ", 0)");
    }

    private void rangoIntercuartilicoRelativo() {
        englobarEnEditor("ricr(", ")");
    }

    private void rangoSemiIntercuartilicoRelativo() {
        englobarEnEditor("rsir(", ")");
    }

    private void cABowley() {
        englobarEnEditor("asimbowley(", ")");
    }

    private void cAPearson() {
        englobarEnEditor("asimpearson(", ")");
    }

    private void volPrismaRegular() {
        englobarEnEditor("vprismareg(", ", 0)");
    }

    private void volOrtoedro() {
        englobarEnEditor("vortoedro(", ", 0, 0)");
    }

    private void volCubo() {
        englobarEnEditor("vcubo(", ")");
    }

    private void volCilindro() {
        englobarEnEditor("vcilindro(", ", 0)");
    }

    private void volEsfera() {
        englobarEnEditor("vesfera(", ")");
    }

    private void volElipsoide() {
        englobarEnEditor("velipsoide(", ", 0, 0)");
    }

    private void volPiramide() {
        englobarEnEditor("vpiramide(", ", 0)");
    }

    private void volCono() {
        englobarEnEditor("vcono(", ", 0)");
    }

    private void areaTriangulo() {
        englobarEnEditor("atriangulo(", ", 0)");
    }

    private void areaTrianguloHeron() {
        englobarEnEditor("atheron(", ", 0, 0)");
    }

    private void areaCuadrilatero() {
        englobarEnEditor("acuadrilatero(", ", 0, 0, 0, 0, 0)");
    }

    private void areaRombo() {
        englobarEnEditor("arombo(", ", 0)");
    }

    private void areaRectangulo() {
        englobarEnEditor("arectangulo(", ", 0)");
    }

    private void areaCuadrado() {
        englobarEnEditor("acuadrado(", ")");
    }

    private void areaRomboide() {
        englobarEnEditor("aromboide(", ", 0)");
    }

    private void areaTrapecio() {
        englobarEnEditor("atrapecio(", ", 0, 0)");
    }

    private void areaCirculo() {
        englobarEnEditor("acirculo(", ")");
    }

    private void areaElipse() {
        englobarEnEditor("aelipse(", ", 0)");
    }

    private void areaEsfera() {
        englobarEnEditor("aesfera(", ")");
    }

    private void areaCono() {
        englobarEnEditor("acono(", ", 0)");
    }

    private void areaCilindro() {
        englobarEnEditor("acilindro(", ", 0)");
    }

    private void distanciaPuntoRecta() {
        englobarEnEditor("dpuntorecta(", ", 0, 0, 0)");
    }

    private void distanciaPuntoPunto() {
        englobarEnEditor("dpuntopunto(", ", 0, 0, 0)");
    }

    private void distanciaRectaRecta() {
        englobarEnEditor("drectarecta(", ", 0, 0)");
    }

    private void distanciaRectaPlano() {
        englobarEnEditor("drectaplano(", ", 0, 0, 0, 0, 0, 0, 0, 0, 0)");
    }

    private void distanciaPlanoPlano() {
        englobarEnEditor("dplanoplano(", ", 0, 0, 0, 0)");
    }

    private void perimetroCuadrado() {
        englobarEnEditor("pcuadrado(", ")");
    }

    private void perimetroRectangulo() {
        englobarEnEditor("prectangulo(", ", 0)");
    }

    private void perimetroRombo() {
        englobarEnEditor("prombo(", ", 0)");
    }

    private void perimetroTriangulo() {
        englobarEnEditor("ptriangulo(", ", 0, 0)");
    }

    private void perimetroRomboide() {
        englobarEnEditor("promboide(", ", 0, 0)");
    }

    private void perimetroCirculo() {
        englobarEnEditor("pcirculo(", ")");
    }

    private void perimetroElipse() {
        englobarEnEditor("pelipse(", ", 0)");
    }

    private void esPrimo() {
        englobarEnEditor("esprimo(", ")");
    }

    private void conteoPrimos() {
        englobarEnEditor("nprimos(", ", 0)");
    }

    private void menorPrimoMayorQue() {
        englobarEnEditor("mpmq(", ")");
    }

    private void imc() {
        englobarEnEditor("imc(", ", 0)");
    }

    private void lmc() {
        englobarEnEditor("lmc(", ")");
    }

    private void convSegundosHoras() {
        englobarEnEditor("convertirsh(", ")");
    }

    private void convSegundosMinutos() {
        englobarEnEditor("convertirsm(", ")");
    }

    private void convHorasSegundos() {
        englobarEnEditor("convertirhs(", ")");
    }

    private void convMinutosSegundos() {
        englobarEnEditor("convertirms(", ")");
    }

    private void convMilisegundosSegundos() {
        englobarEnEditor("convertirmss(", ")");
    }

    private void convSegundosMilisegundos() {
        englobarEnEditor("convertirsms(", ")");
    }

    private void masaElementosNumero() {
        englobarEnEditor("masaelementon(", ")");
    }

    private void segundaDerivada() {
        englobarEnEditor("derivardos(", ", 0)");
    }

    private void terceraDerivada() {
        englobarEnEditor("derivartres(", ", 0)");
    }

    private void cuartaDerivada() {
        englobarEnEditor("derivarcuatro(", ", 0)");
    }

    private void minimoDatos() {
        englobarEnEditor("mindatos(", ")");
    }

    private void maximoDatos() {
        englobarEnEditor("maxdatos(", ")");
    }

    private void promedio() {
        englobarEnEditor("prom(", ")");
    }

    private void contarDatos() {
        englobarEnEditor("contar(", ")");
    }

    private void contarSiDatos() {
        englobarEnEditor("contarsi(", ", 0)");
    }

    private void moda() {
        englobarEnEditor("moda(", ")");
    }

    private void sumatoria() {
        englobarEnEditor("suma(", ")");
    }

    private void sumaProducto() {
        englobarEnEditor("sumap(", ")");
    }

    private void mediaGeometrica() {
        englobarEnEditor("mgeom(", ")");
    }

    private void mediaArmonica() {
        englobarEnEditor("marmon(", ")");
    }

    private void mediana() {
        englobarEnEditor("mediana(", ")");
    }

    private void aleatorioGaussiano() {
        englobarEnEditor("gauss(", ")");
    }

    private void aleatorioUniforme() {
        englobarEnEditor("aleatorio(", ")");
    }
}
