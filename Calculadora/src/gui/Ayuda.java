/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Andrés Sarmiento Tobón <ansarmientoto at unal.edu.co>
 */
public class Ayuda extends javax.swing.JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new form Ayuda
     */
    public Ayuda() {
        initComponents();

        try {
            setIconImage(ImageIO.read(getClass().getResource(
                    "/gui/help.png")));
        } catch (IOException ex) {
            Logger.getLogger(VentanaCalculo.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void setVisible(boolean b) {
        setLocationRelativeTo(calculadora.Calculadora.getVentana());
        super.setVisible(b);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setTitle("Ayuda");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                salirAyudaEvento(evt);
            }
        });

        jPanel1.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);

        jScrollPane1.setMaximumSize(new java.awt.Dimension(314, 430));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(314, 430));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(314, 430));

        jTree1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jTree1.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Ayuda");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Introducción");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Menú Contextual");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Introducción");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Portapapeles");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Funciones");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Operadores");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Números");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Modificación del contenido");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Procesamiento");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Funciones");
        javax.swing.tree.DefaultMutableTreeNode treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Trigonométricas");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Hiperbólicas");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Métodos Numéricos");
        javax.swing.tree.DefaultMutableTreeNode treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Derivación");
        javax.swing.tree.DefaultMutableTreeNode treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Primer Orden");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Segundo Orden");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Tercer Orden");
        treeNode5.add(treeNode6);
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Integración");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Encontrar Ceros de una Función");
        treeNode4.add(treeNode5);
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Estadísticas");
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Promedio(Media Aritmética)");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Media Geométrica");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Media Armónica");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Percentiles");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Varianza");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Desviación Estándar");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Sumatoria");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Suma Producto");
        treeNode4.add(treeNode5);
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Operadores");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Suma");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Resta");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Multiplicación");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("División");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Potenciación");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Asignación de nueva función");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Equivalencia");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Números");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Variables");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Gráficas");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Introducción");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Menú contextual");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Personalización del Gráfico");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Impresión");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Guardar como Imágen");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Portapapeles");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Escalas");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Funciones Simultáneas");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Creación de Funciones");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Exigencias");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Sintaxis");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Análisis de Igualdad(Beta)");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Sintaxis");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Problemas");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        jTree1.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTree1.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                seleccionArbolEvento(e);
            }
        });
        jScrollPane1.setViewportView(jTree1);
        jTree1.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);

        jScrollPane2.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jScrollPane2.setMaximumSize(new java.awt.Dimension(332, 430));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(332, 430));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jTextArea1.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextArea1);

        jButton1.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
        jButton1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jButton1.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        jButton1.setText("Cerrar Ayuda");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
        jButton2.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jButton2.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        jButton2.setText("Ver Lista Funciones");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jButton1)
                .addGap(124, 124, 124)
                .addComponent(jButton2)
                .addGap(59, 59, 59))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirAyudaEvento(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_salirAyudaEvento
        salir();
    }//GEN-LAST:event_salirAyudaEvento

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        salir();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        calculadora.Calculadora.getListaFunciones().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ayuda.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ayuda.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ayuda.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ayuda.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                calculadora.Calculadora.getVentanaAyuda().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

    private void salir() {
        calculadora.Calculadora.getVentana().setVisible(true);
//        setVisible(false);
    }
    
    private void seleccionArbolEvento(TreeSelectionEvent evt) {
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)
                           jTree1.getLastSelectedPathComponent();

        if (nodo == null || !nodo.isLeaf()) {
            return;
        }

        String nombreNodo = (String) nodo.getUserObject();
        Object[] caminoCompleto = evt.getPath().getPath();
        String[] camino = new String[caminoCompleto.length];
        
        for (int i = 0; i < camino.length; i++) {
            camino[i] = (String) ((DefaultMutableTreeNode) caminoCompleto[i])
                    .getUserObject();
        }
        
        switch(nombreNodo) {
            case "Introducción":
                if(camino[1].equals("Menú Contextual")) {
                    jTextArea1.setText(ParametrosAyuda
                            .introduccionMenuContextual);
                } else if (camino[1].equals("Gráficas")) {
                    jTextArea1.setText(ParametrosAyuda.introduccionGraficas);
                } else {
                    jTextArea1.setText(ParametrosAyuda.introduccionAyuda);
                }
                
                break;
            case "Portapapeles":
                if(camino[1].equals("Menú Contextual")) {
                    jTextArea1.setText(ParametrosAyuda
                            .portapapelesMenuContextual);
                } else {
                    jTextArea1.setText(ParametrosAyuda
                            .portapapelesGraficas);
                }
                
                break;
            case "Funciones":
                jTextArea1.setText(ParametrosAyuda
                            .funcionesMenuContextual);
                break;
            case "Operadores":
                jTextArea1.setText(ParametrosAyuda
                            .operadoresMenuContextual);
                break;
            case "Números":
                jTextArea1.setText(ParametrosAyuda
                            .numerosMenuContextual);
                break;
            case "Modificación del contenido":
                jTextArea1.setText(ParametrosAyuda
                            .modificacionContenidoMenuContextual);
                break;
            case "Trigonométricas":
                jTextArea1.setText(ParametrosAyuda
                            .trigonometricas);
                break;
            case "Hiperbólicas":
                jTextArea1.setText(ParametrosAyuda
                            .hiperbolicas);
                break;
            case "Primer Orden":
                jTextArea1.setText(ParametrosAyuda
                            .derivacionPrimerOrden);
                break;
            case "Segundo Orden":
                jTextArea1.setText(ParametrosAyuda
                            .derivacionSegundoOrden);
                break;
            case "Tercer Orden":
                jTextArea1.setText(ParametrosAyuda
                            .derivacionTercerOrden);
                break;
            case "Integración":
                jTextArea1.setText(ParametrosAyuda
                            .Integracion);
                break;
            case "Encontrar Ceros de una Función":
                jTextArea1.setText(ParametrosAyuda
                            .cerosFuncion);
                break;
            case "Promedio(Media Aritmética)":
                jTextArea1.setText(ParametrosAyuda
                            .promedio);
                break;
            case "Media Geométrica":
                jTextArea1.setText(ParametrosAyuda
                            .mediaGeometrica);
                break;
            case "Media Armónica":
                jTextArea1.setText(ParametrosAyuda
                            .mediaArmonica);
                break;
            case "Percentiles":
                jTextArea1.setText(ParametrosAyuda
                            .percentiles);
                break;
            case "Varianza":
                jTextArea1.setText(ParametrosAyuda
                            .varianza);
                break;
            case "Desviación Estándar":
                jTextArea1.setText(ParametrosAyuda
                            .desvEstandar);
                break;
            case "Sumatoria":
                jTextArea1.setText(ParametrosAyuda
                            .sumatoria);
                break;
            case "Suma Producto":
                jTextArea1.setText(ParametrosAyuda
                            .sumaProducto);
                break;
            case "Suma":
                jTextArea1.setText(ParametrosAyuda
                            .suma);
                break;
            case "Resta":
                jTextArea1.setText(ParametrosAyuda
                            .resta);
                break;
            case "Multiplicación":
                jTextArea1.setText(ParametrosAyuda
                            .multiplicacion);
                break;
            case "División":
                jTextArea1.setText(ParametrosAyuda
                            .division);
                break;
            case "Potenciación":  
                jTextArea1.setText(ParametrosAyuda
                            .potenciacion); 
                break;
            case "Asignación de nueva función":
                jTextArea1.setText(ParametrosAyuda
                            .nuevaFuncion); 
                break;
            case "Equivalencia":
                jTextArea1.setText(ParametrosAyuda
                            .equivalencia); 
                break;
            case "Variables":
                jTextArea1.setText(ParametrosAyuda
                            .variables); 
                break;
            case "Personalización del Gráfico":
                jTextArea1.setText(ParametrosAyuda
                            .personalizacionGrafico); 
                break;
            case "Impresión":
                jTextArea1.setText(ParametrosAyuda
                            .impresionGrafico);
                break;
            case "Guardar como Imágen":
                jTextArea1.setText(ParametrosAyuda
                            .guardarGrafico); 
                break;
            case "Escalas":
                jTextArea1.setText(ParametrosAyuda
                            .escalasGrafico); 
                break;
            case "Funciones Simultáneas":
                jTextArea1.setText(ParametrosAyuda
                            .funcionesSimultaneasGrafico); 
                break;
            case "Exigencias":
                jTextArea1.setText(ParametrosAyuda
                            .exigenciasCreacionFunciones); 
                break;
            case "Sintaxis":
                if(camino[1].equals("Creación de Funciones")) {
                    jTextArea1.setText(ParametrosAyuda
                            .sintaxisCreacionFunciones); 
                } else {
                    jTextArea1.setText(ParametrosAyuda
                            .sintaxisAnalisisIgualdad); 
                }
                
                break;
            case "Problemas":
                jTextArea1.setText(ParametrosAyuda
                            .problemasAnalisisIgualdad); 
                break;
            default:
                jTextArea1.setText("Error: Ayuda desconocida");
                jTextArea1.setRows(0);
                break;
        }
    }
}

class ParametrosAyuda {
    public static final String introduccionAyuda = "Este diálogo le permite revisar el funcionamiento de la calculadora. Si necesita buscar alguna función puede oprimir el bóton \"Ver Lista Funciones\". Esta Calculadora no guarda ningún dato computado ni el historial de datos ingresados por el usuario.";
    public static final String introduccionMenuContextual = "El menú contextual contiene las mismas funcionalidades que la interfaz. No obstante, tiene algunas adicionales, como los Métodos Numéricos, o los paréntesis.";
    public static final String introduccionGraficas = "Esta calculadora puede representar de forma gráfica una variable dada una expresión y los límites.";
    public static final String portapapelesMenuContextual = "La selección de texto actual se guarda en el portapapeles. Así mismo, lo que halla en el portapapeles aparece en el cuadro de entrada.";
    public static final String portapapelesGraficas = "Copia el grafico en el portapapeles para ser editado en cualquier programa de Edición de Imágenes.";
    public static final String funcionesMenuContextual = "Inserta la cualquier función incluida en la calculadora en el cuadro de entrada.";
    public static final String operadoresMenuContextual = "Inserta cualquiera de los operadores aritméticos en el cuadro de entrada.";
    public static final String numerosMenuContextual = "Inserta cualquiera de los digitos y el operador decimal en el cuadro de entrada.";
    public static final String modificacionContenidoMenuContextual = "Se puede retrocerder o avanzar en el historial. Así como eliminar, selectivamente o completamente, el contenido del cuadro de entrada.";
    public static final String trigonometricas = "Funciones definidas sobre un ángulo, los catetos y la hipotenusa de un triángulo rectángulo. Están todas definidas en la calculadora.";
    public static final String hiperbolicas = "Funciones períodicas definidas sobre exponenciales complejas.";
    public static final String derivacionPrimerOrden = "Derivación numérica de una expresión de primer grado";
    public static final String derivacionSegundoOrden = "Derivación numérica de una expresión de segundo grado";
    public static final String derivacionTercerOrden = "Derivación numérica de una expresión de tercer grado";
    public static final String Integracion = "Integración numérica de una expresión";
    public static final String cerosFuncion = "Busca todas las variables de una expresión y calcula los valores para los que son cero.";
    public static final String promedio = "Calcula el promedio o media aritmética de una cnjuntode números.";
    public static final String mediaGeometrica = "Calcula la media geométrica de una cnjuntode números.";
    public static final String mediaArmonica = "Calcula la media armónica de una conjunto de números.";
    public static final String percentiles = "Calcula los percentiles de un conjunto de números.";
    public static final String varianza = "Calcula la varianza de un conjunto de números.";
    public static final String desvEstandar = "Calcula la desviación estándar de un conjunto de números.";
    public static final String sumatoria = "Halla la suma de un conjunto de números.";
    public static final String sumaProducto = "Halla la suma de los cuadrados de un conjuntos de números.";
    public static final String suma = "Suma dos expresiones, la expresión queda expresa si no se conoce el resultado. Se realizan algunas simplificaciones sobre ceros.";
    public static final String resta = "Resta dos expresiones, la expresión queda expresa si no se conoce el resultado. Se realizan algunas simplificaciones sobre ceros.";
    public static final String multiplicacion = "Multiplica dos expresiones, la expresión queda expresa si no se conoce el resultado. Se realizan algunas simplificaciones sobre ceros y unos.";
    public static final String division = "Divide dos expresiones, la expresión queda expresa si no se conoce el resultado. Se realizan algunas simplificaciones sobre ceros y unos.";
    public static final String potenciacion = "Potencia dos expresiones, la expresión queda expresa si no se conoce el resultado. Se realizan algunas simplificaciones sobre ceros y unos.";
    public static final String nuevaFuncion = "Crea una nueva función con ciertos parámetros que serán reemplazados en una expresión.";
    public static final String equivalencia = "Comprueba si dos expresiones son equivalentes. En caso de haber variables se revisa si existe algun punto en el que se cumpla la igualdad.";
    public static final String variables = "Una cadena de caracteres alfanuméricos que no halla sido definida previamente como una función es una variable. La variables son números a los que no se les ha asignado un valor concreto y sirven como comodínes para ser reemplazados en caso de ser necesario.";
    public static final String personalizacionGrafico = "A cada gráfico se le puede modificar el fondo, el título del gráfico y de cada eje. Así como los trazos de cada función.";
    public static final String impresionGrafico = "Imprime el gráfico mediante una de las impresoras del sistema.";
    public static final String guardarGrafico = "Guarda el gráfico en una ubicación elegida por el usuario en formato PNG.";
    public static final String escalasGrafico = "El gráfico se puede escalar de forma horizontal y vertical. Además se puede regresar a la escala original en la que se ven todos el rango de datos.";
    public static final String funcionesSimultaneasGrafico = "Cada función graficada por el usuario se almacena y pue ser graficada de nuevo seleccionándola en la tabla de la derecha del gráfico.";
    public static final String exigenciasCreacionFunciones = "La sintaxis debe ser correcta y la expresión debe ser correcta. Además, no se garantiza una salida numérica a menos que las variables ingresadas por el usuario concuerden con las presentes en la expresión.";
    public static final String sintaxisCreacionFunciones = "<nombrefuncion>(<variables>) = <expresion>";
    public static final String sintaxisAnalisisIgualdad = "<expresion> == <expresion>";
    public static final String problemasAnalisisIgualdad = "El problema se soluciona por una búsqueda numérica. Entonces, el problema depende excepcionalmente de los valores iniciales de la búsqueda. Por lo tanto, no esgarantizado que se encontrará un valor. En cualquier caso si no se encuentra un valor se devuelve falso, pero no implica que no existan valores en lo absoluto sino que no se encontró alguno que cumpliera la condición.";
}