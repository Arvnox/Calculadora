/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;
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
public class Grafico extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    private DefaultXYDataset set = new DefaultXYDataset();
    private TreeMap<String, double[][]> funciones = new TreeMap<>();
    private TreeMap<String, String> nombres = new TreeMap<>();
    private String ultimoNombre = "";

    /**
     * Creates new form Grafico
     */
    public Grafico() {
        initComponents();

        try {
            setIconImage(ImageIO.read(getClass().getResource(
                    "/gui/Graph.png")));
        } catch (IOException ex) {
            Logger.getLogger(VentanaCalculo.class.getName()).log(Level.SEVERE,
                    null, ex);
        } finally {
            setLocationRelativeTo(null);
        }
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
        jPanel2 = new ChartPanel(ChartFactory.createXYLineChart("Gráfico", "X",
            "Y", set, PlotOrientation.VERTICAL, true, true, false));
    jPanel3 = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();

    setTitle("Gráfico");

    jPanel1.setBackground(new java.awt.Color(252, 255, 252));

    jPanel2.setBackground(new java.awt.Color(254, 255, 254));
    jPanel2.setForeground(new java.awt.Color(0, 0, 0));
    jPanel2.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
    jPanel2.setPreferredSize(new java.awt.Dimension(877, 338));

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 782, Short.MAX_VALUE)
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGap(0, 0, Short.MAX_VALUE)
    );

    jPanel3.setBackground(new java.awt.Color(254, 255, 254));

    jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
    jScrollPane1.setForeground(new java.awt.Color(0, 0, 0));
    jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    jScrollPane1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
    jScrollPane1.setMaximumSize(new java.awt.Dimension(453, 324));
    jScrollPane1.setMinimumSize(new java.awt.Dimension(453, 324));
    jScrollPane1.setPreferredSize(new java.awt.Dimension(453, 324));

    jTable1.setAutoCreateRowSorter(true);
    jTable1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
    jTable1.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Mostrar", "Función"
        }
    ) {
        Class[] types = new Class [] {
            java.lang.Boolean.class, java.lang.String.class
        };
        boolean[] canEdit = new boolean [] {
            true, false
        };

        public Class getColumnClass(int columnIndex) {
            return types [columnIndex];
        }

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    jTable1.setCellSelectionEnabled(false);
    jTable1.setDragEnabled(true);
    jTable1.setFillsViewportHeight(true);
    jTable1.setIntercellSpacing(new java.awt.Dimension(4, 4));
    jTable1.setRowHeight(44);
    jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            botonDecidirMostrarFuncion(evt);
        }
    });
    jScrollPane1.setViewportView(jTable1);
    jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    jTable1.getColumnModel().getColumn(0).setMinWidth(64);
    jTable1.getColumnModel().getColumn(0).setPreferredWidth(64);
    jTable1.getColumnModel().getColumn(0).setMaxWidth(64);
    jTable1.getColumnModel().getColumn(1).setPreferredWidth(256);

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel3Layout.setVerticalGroup(
        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
    );

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonDecidirMostrarFuncion(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonDecidirMostrarFuncion
        int fila = jTable1.getSelectedRow();
        int columna = jTable1.getSelectedColumn();

        if (fila >= 0 && columna >= 0) {
            Object objetoIzquierdo = jTable1.getValueAt(fila, 0);
            Object objetoDerecho = jTable1.getValueAt(fila, 1);
            String funcion;
            Boolean mostrar;

            if ((objetoIzquierdo instanceof String) &&
                    (objetoDerecho instanceof Boolean)) {
                funcion = (String) objetoIzquierdo;
                mostrar = (Boolean) objetoDerecho;
            } else if ((objetoIzquierdo instanceof Boolean) &&
                    (objetoDerecho instanceof String)) {
                funcion = (String) objetoDerecho;
                mostrar = (Boolean) objetoIzquierdo;
            } else {
                return;
            }

            if (funcion.length() > 0) {
                if (mostrar) {
                    set.addSeries(funcion, funciones.get(funcion));
                } else {
                    set.removeSeries(funcion);
                }
            }
        }
    }//GEN-LAST:event_botonDecidirMostrarFuncion

    /**
     *
     * @param expresion
     * @param minX
     * @param maxX
     * <p/>
     * @throws CaracterIlegal
     */
    public void graficar(String expresion, double minX, double maxX)
            throws CaracterIlegal {
        String nombreVariable = "";
        String[] rpn = Procesador.convertirRPN(expresion);

        for (String item : rpn) {
            if (Procesador.esVariable(item)) {
                if (nombreVariable.isEmpty()) {
                    nombreVariable = item;
                } else if (!item.equals(nombreVariable)) {
                    throw new CaracterIlegal(expresion, 0);
                }
            }
        }

        double[] valoresX = new double[1001];
        double[] valoresY = new double[1001];
        double iteradorDoubleX = minX;
        double pasoDouble = (maxX - minX) / 1000.0;

        for (int i = 0; i < valoresX.length; i++) {
            valoresX[i] = iteradorDoubleX;

            try {
                valoresY[i] = evaluarValor(Arrays.copyOf(rpn, rpn.length),
                        nombreVariable, valoresX[i]);
            } catch (Exception e) {
                valoresY[i] = Double.NaN;
            }

            iteradorDoubleX += pasoDouble;
        }

        double pasoDoubleError = pasoDouble / 2;

        for (int i = 0; i < valoresY.length; i++) {
            if (valoresY[i] == Double.NaN) {
                double izquierdo = evaluarValor(Arrays.copyOf(rpn, rpn.length),
                        nombreVariable, valoresX[i] - pasoDoubleError);
                double derecho = evaluarValor(Arrays.copyOf(rpn, rpn.length),
                        nombreVariable, valoresX[i] + pasoDoubleError);
                valoresY[i] = (izquierdo - derecho) / 2;
            }
        }

//        System.out.println(Arrays.toString(valoresX));
//        System.out.println(Arrays.toString(valoresY));
        DefaultTableModel modeloTablaDefecto = (DefaultTableModel) jTable1
                .getModel();

        if (ultimoNombre.length() > 0) {
            set.removeSeries(nombres.get(ultimoNombre));
            modeloTablaDefecto.setValueAt(false, modeloTablaDefecto
                    .getRowCount() - 1, 0);
        }

        double[][] valores = new double[][] {valoresX, valoresY};
        String funcion = generarNombre() + "(" + nombreVariable + ") = " +
                 Procesador.evaluarRPN(Procesador.convertirRPN(expresion))
                .toString();

        set.addSeries(funcion, valores);
        nombres.put(ultimoNombre, funcion);
        funciones.put(funcion, valores);

        modeloTablaDefecto.addRow(new Object[] {true, funcion});
        jPanel2.repaint();

        setVisible(true);
    }

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
            java.util.logging.Logger.getLogger(Grafico.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Grafico g = new Grafico();

                for (int i = 0; i < 200; i++) {
                    System.out.println("\t" + g.generarNombre());
                }

//                g.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    private double evaluarValor(String[] rpn, String nombreVariable,
            double valorX) throws CaracterIlegal {
        for (int i = 0; i < rpn.length; i++) {
            if (rpn[i].equals(nombreVariable)) {
                rpn[i] = "" + valorX;
            }
        }

        return Double.parseDouble(Procesador.evaluarRPN(rpn).toString().trim());
    }
    Pattern patronSiguienteNombreFuncion = Pattern.compile("[e]++");

    private String generarNombre() {
        if (ultimoNombre.isEmpty()) {
            ultimoNombre = "f";
        } else if (patronSiguienteNombreFuncion.matcher(ultimoNombre)
                .matches()) {
            ultimoNombre = (ultimoNombre.replaceAll("e", "f") + "f").intern();
        } else {
            char[] caracteres = ultimoNombre.toCharArray();
            boolean siguiente = false;

            for (int i = caracteres.length - 1; i >= 0; i--) {
                if (!siguiente) {
                    switch (caracteres[i]) {
                        case 'a':
                            caracteres[i] = 'b';
                            siguiente = true;
                            break;
                        case 'b':
                            caracteres[i] = 'c';
                            siguiente = true;
                            break;
                        case 'c':
                            caracteres[i] = 'd';
                            siguiente = true;
                            break;
                        case 'd':
                            caracteres[i] = 'e';
                            siguiente = true;
                            break;
                        case 'e':
                            caracteres[i] = 'f';
                            siguiente = false;
                            break;
                        case 'f':
                            caracteres[i] = 'g';
                            siguiente = true;
                            break;
                        case 'g':
                            caracteres[i] = 'h';
                            siguiente = true;
                            break;
                        case 'h':
                            caracteres[i] = 'i';
                            siguiente = true;
                            break;
                        case 'i':
                            caracteres[i] = 'j';
                            siguiente = true;
                            break;
                        case 'j':
                            caracteres[i] = 'k';
                            siguiente = true;
                            break;
                        case 'k':
                            caracteres[i] = 'l';
                            siguiente = true;
                            break;
                        case 'l':
                            caracteres[i] = 'm';
                            siguiente = true;
                            break;
                        case 'm':
                            caracteres[i] = 'n';
                            siguiente = true;
                            break;
                        case 'n':
                            caracteres[i] = 'o';
                            siguiente = true;
                            break;
                        case 'o':
                            caracteres[i] = 'p';
                            siguiente = true;
                            break;
                        case 'p':
                            caracteres[i] = 'q';
                            siguiente = true;
                            break;
                        case 'q':
                            caracteres[i] = 'r';
                            siguiente = true;
                            break;
                        case 'r':
                            caracteres[i] = 's';
                            siguiente = true;
                            break;
                        case 's':
                            caracteres[i] = 't';
                            siguiente = true;
                            break;
                        case 't':
                            caracteres[i] = 'u';
                            siguiente = true;
                            break;
                        case 'u':
                            caracteres[i] = 'v';
                            siguiente = true;
                            break;
                        case 'v':
                            caracteres[i] = 'w';
                            siguiente = true;
                            break;
                        case 'w':
                            caracteres[i] = 'x';
                            siguiente = true;
                            break;
                        case 'x':
                            caracteres[i] = 'y';
                            siguiente = true;
                            break;
                        case 'y':
                            caracteres[i] = 'z';
                            siguiente = true;
                            break;
                        case 'z':
                            caracteres[i] = 'a';
                            siguiente = true;
                            break;
                    }
                }

                if (siguiente) {
                    break;
                }
            }

            ultimoNombre = new String(caracteres).intern();
        }

        return ultimoNombre;
    }
}