/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadora;

import gui.Ayuda;
import gui.Grafico;
import gui.ListaFunciones;
import gui.Preferencias;
import gui.SelectorColor;
import gui.SelectorFuncionesOcultas;
import gui.VentanaCalculo;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

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
public class Calculadora {

    private static VentanaCalculo ventana = null;
    private static Preferencias preferencias = null;
    private static SelectorColor selectorColor = null;
    private static Grafico grafico = null;
    private static SelectorFuncionesOcultas selectorFuncionesOcultas = null;
    private static Ayuda ventanaAyuda = null;
    private static ListaFunciones listaFunciones = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SplashScreen inicio = SplashScreen.getSplashScreen();

        if (inicio != null) {
            inicio.createGraphics();
            Graphics2D graficos = inicio.createGraphics();
            graficos.drawString("Iniciando...", 0, 0);
            inicio.update();
        }

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

        long nanos = System.nanoTime();
        inicializacion();
        ventana.setVisible(true);
        nanos = System.nanoTime() - nanos;
        System.out.println("INICIALIZACIÓN: " + nanos + " nanosegundos, " 
                + (nanos / 1e6) + " milisegundos, " + (nanos / 1e9) 
                + " segundos.");
    }

    /**
     *
     * @return
     */
    public static VentanaCalculo getVentana() {
        if (ventana == null) {
            inicializacion();
        }

        return ventana;
    }

    /**
     *
     * @return
     */
    public static Preferencias getPreferencias() {
        if (preferencias == null) {
            inicializacion();
        }

        return preferencias;
    }

    /**
     *
     * @return
     */
    public static SelectorColor getSelectorColor() {
        if (selectorColor == null) {
            inicializacion();
        }

        return selectorColor;
    }

    /**
     *
     * @return
     */
    public static Grafico getGrafico() {
        if (grafico == null) {
            inicializacion();
        }

        return grafico;
    }

    /**
     *
     * @return
     */
    public static SelectorFuncionesOcultas getSelectorFuncionesOcultas() {
        if (selectorFuncionesOcultas == null) {
            inicializacion();
        }

        return selectorFuncionesOcultas;
    }
    
    /**
     *
     * @return
     */
    public static Ayuda getVentanaAyuda() {
        if(ventanaAyuda == null) {
            inicializacion();
        }
        
        return ventanaAyuda;
    }
    
    /**
     *
     * @return
     */
    public static ListaFunciones getListaFunciones() {
        if(listaFunciones == null) {
            inicializacion();
        }
        
        return listaFunciones;
    }

    static javax.swing.JFrame ventanaCargador = null;

    private static void inicializacion() {
        if (ventanaCargador == null) {
            ventanaCargador = new javax.swing.JFrame();
//            javax.swing.JProgressBar barraProgreso = new javax.swing
//                    .JProgressBar();
//            
//            ventanaCargador.setDefaultCloseOperation(javax.swing.WindowConstants
//                    .EXIT_ON_CLOSE);
//            ventanaCargador.setTitle("Cargando Calculadora...");
//            ventanaCargador.setUndecorated(true);
//            
//            barraProgreso.setBackground(VentanaCalculo
//                    .COLOR_FONDO_PREDETERMINADO);
//            barraProgreso.setFont(new java.awt.Font("Ubuntu Light", 0, 24));
//            barraProgreso.setForeground(VentanaCalculo
//                    .COLOR_FUENTE_PREDETERMINADO);
//            barraProgreso.setIndeterminate(true);
//            barraProgreso.setString("Cargando Calculadora...");
//            barraProgreso.setStringPainted(true);
//            
//            javax.swing.GroupLayout ventanaCargadorLayout = 
//                    new javax.swing.GroupLayout(ventanaCargador
//                    .getContentPane());
//            ventanaCargador.getContentPane().setLayout(ventanaCargadorLayout);
//            ventanaCargadorLayout.setHorizontalGroup(
//                    ventanaCargadorLayout.createParallelGroup(javax.swing
//                    .GroupLayout.Alignment.LEADING).addComponent(barraProgreso, 
//                    javax.swing.GroupLayout.DEFAULT_SIZE, 400, 
//                    Short.MAX_VALUE));
//            ventanaCargadorLayout.setVerticalGroup(ventanaCargadorLayout
//                    .createParallelGroup(javax.swing.GroupLayout.Alignment
//                    .LEADING).addGroup(ventanaCargadorLayout
//                    .createSequentialGroup().addComponent(barraProgreso, 
//                    javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing
//                    .GroupLayout.PREFERRED_SIZE).addGap(0, 0, 0)));
//            
//            ventanaCargador.setResizable(false);
//            ventanaCargador.pack();
//            
//            try {
//                ventanaCargador.setIconImage(ImageIO.read(ventanaCargador
//                        .getClass().getResource("/gui/load.png")));
//            } catch (IOException ex) {
//                Logger.getLogger(SelectorFuncionesOcultas.class.getName())
//                        .log(Level.SEVERE, null, ex);
//            } finally {
//                ventanaCargador.setLocationRelativeTo(null);
//            }

        }

        if (ventana == null) {
            ventanaCargador.setVisible(true);
            ventana = new VentanaCalculo();
        }

        if (preferencias == null) {
            ventanaCargador.setVisible(true);
            preferencias = new Preferencias();
        }

        if (selectorColor == null) {
            ventanaCargador.setVisible(true);
            selectorColor = new SelectorColor();
        }

        if (grafico == null) {
            ventanaCargador.setVisible(true);
            grafico = new Grafico();
        }

        if (selectorFuncionesOcultas == null) {
            ventanaCargador.setVisible(true);
            selectorFuncionesOcultas = new SelectorFuncionesOcultas();
        }
        
        if(ventanaAyuda == null) {
            ventanaCargador.setVisible(true);
            ventanaAyuda = new Ayuda();
        }
        
        if(listaFunciones == null) {
            ventanaCargador.setVisible(true);
            listaFunciones = new ListaFunciones();
        }

        ventanaCargador.setVisible(false);
    }
}
