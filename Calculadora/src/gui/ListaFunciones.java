package gui;

import datos.Funcion;
import datos.busqueda.Busqueda;
import datos.busqueda.Documento;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author Andrés Sarmiento Tobón <ansarmientoto at unal.edu.co>
 */
public class ListaFunciones extends javax.swing.JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private String[] funciones;
    private TreeMap<String, String> nombresLargos = new TreeMap<>();
    /**
     *
     */
    private TreeMap<String, String> descripciones = new TreeMap<>();
    /**
     *
     */
    private TreeMap<String, String> descripcionesLargas = new TreeMap<>();
    private TreeMap<String, String> nombresAlternativos = new TreeMap<>();
    private TreeMap<String, String> textosDescriptivos = new TreeMap<>();
    private TreeMap<String, String> textosDescriptivosInversos = 
            new TreeMap<>();
    /**
     *
     */
    private boolean inicializado = false;
    
    
    /**
     * Creates new form ListaFunciones
     */
    public ListaFunciones() {
        inicializado = false;
        inicializarDescripcion();
        initComponents();
        inicializarInterfaz();
        inicializarBusqueda();
        inicializado = true;
        
        try {
            setIconImage(ImageIO.read(getClass().getResource(
                    "/gui/information.png")));
        } catch (IOException ex) {
            Logger.getLogger(VentanaCalculo.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void setVisible(boolean b) {
        if(b) {
            setLocationRelativeTo(calculadora.Calculadora.getVentanaAyuda());
        }
        
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
        jComboBox1 = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel3 = new javax.swing.JLabel();

        setTitle("Información Funciones");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                listaFuncionesSalirEvento(evt);
            }
        });

        jPanel1.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);

        jComboBox1.setBackground(new java.awt.Color(255, 255, 255));
        jComboBox1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(0, 0, 0));
        jComboBox1.setMaximumSize(new java.awt.Dimension(598, 38));
        jComboBox1.setMinimumSize(new java.awt.Dimension(598, 38));
        jComboBox1.setOpaque(false);
        jComboBox1.setPreferredSize(new java.awt.Dimension(423, 38));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                estadoCambiado(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jLabel2.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Descripción Función");
        jLabel2.setMaximumSize(new java.awt.Dimension(423, 38));
        jLabel2.setMinimumSize(new java.awt.Dimension(423, 38));
        jLabel2.setPreferredSize(new java.awt.Dimension(423, 38));

        jScrollPane2.setMaximumSize(new java.awt.Dimension(423, 293));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(423, 293));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(423, 293));

        jTextArea2.setEditable(false);
        jTextArea2.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jTextArea2.setForeground(new java.awt.Color(0, 0, 0));
        jTextArea2.setLineWrap(true);
        jTextArea2.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextArea2);

        jPanel2.setOpaque(false);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Buscar");
        jButton1.setMaximumSize(new java.awt.Dimension(148, 38));
        jButton1.setMinimumSize(new java.awt.Dimension(148, 38));
        jButton1.setPreferredSize(new java.awt.Dimension(148, 38));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(0, 0, 0));
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                enterBusqueda(evt);
            }
        });

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setForeground(new java.awt.Color(0, 0, 0));
        jTabbedPane1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jTabbedPane1.setMaximumSize(new java.awt.Dimension(231, 490));
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(231, 490));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(231, 490));

        jTree1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jTree1.setForeground(new java.awt.Color(0, 0, 0));
        jTree1.setModel(generarModeloFunciones());
        jTree1.setDragEnabled(true);
        jTree1.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                seleccionArbolEvento(e);
            }
        });
        jScrollPane1.setViewportView(jTree1);
        jTree1.getSelectionModel().setSelectionMode
        (TreeSelectionModel.SINGLE_TREE_SELECTION);

        jTabbedPane1.addTab("Lista de Funciones", jScrollPane1);

        jList1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jList1.setForeground(new java.awt.Color(0, 0, 0));
        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                valorPulsadoLista(evt);
            }
        });
        jScrollPane3.setViewportView(jList1);

        jTabbedPane1.addTab("Búsqueda", jScrollPane3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField2)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel3.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jLabel3.setForeground(VentanaCalculo.COLOR_FUENTE_PREDETERMINADO);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Lista Completa de Funciones");
        jLabel3.setMaximumSize(new java.awt.Dimension(423, 38));
        jLabel3.setMinimumSize(new java.awt.Dimension(423, 38));
        jLabel3.setPreferredSize(new java.awt.Dimension(423, 38));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, 0)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 598, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void estadoCambiado(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_estadoCambiado
        if(inicializado) {
            String item = (String) evt.getItem();
            actualizarAreaTexto(item);
            jComboBox1.setToolTipText(item);
        }
    }//GEN-LAST:event_estadoCambiado

    private void listaFuncionesSalirEvento(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_listaFuncionesSalirEvento
        salir();
    }//GEN-LAST:event_listaFuncionesSalirEvento

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        procesarBusqueda();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void enterBusqueda(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_enterBusqueda
        if(evt.getKeyChar() == '\n') {
            procesarBusqueda();
        }
    }//GEN-LAST:event_enterBusqueda

    private void valorPulsadoLista(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_valorPulsadoLista
        final int indice = evt.getFirstIndex();
        
        try {
            new Thread(new Runnable(){

                @Override
                public void run() {
                    final String nombre = (String) jList1.getModel()
                            .getElementAt(indice);
                    actualizarAreaTexto(nombre);
                }
            }).start();
            
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }//GEN-LAST:event_valorPulsadoLista

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
            java.util.logging.Logger.getLogger(ListaFunciones.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListaFunciones.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListaFunciones.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListaFunciones.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListaFunciones().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    private void inicializarDescripcion() {
        NavigableMap<String, String> mapa = Funcion.listaFuncionesNativas();
        TreeSet<String> nombreFunciones = new TreeSet<>();

        for (String funcion : mapa.navigableKeySet()) {
            String descripcion = Funcion.obtenerDescripcion(funcion);
            String descripcionLarga = Funcion.obtenerDescripcionLarga(funcion);
            String nombre = mapa.get(funcion);

            if (nombre.length() > 0) {
                nombre = " [" + nombre + "]";
            }

            String nombreLargo = (funcion + nombre).intern();
            nombresLargos.put(funcion, nombreLargo);
            nombresAlternativos.put(nombreLargo, nombre.intern());
            nombreFunciones.add(nombreLargo);
            descripciones.put(nombreLargo, descripcion.intern());
            descripcionesLargas.put(nombreLargo, descripcionLarga.intern());
        }
        
        funciones = nombreFunciones.toArray(new String[nombreFunciones.size()]);
        
        for (String funcion : funciones) {
            textosDescriptivos.put(funcion, actualizarTexto(funcion));
        }
    }

    private void inicializarInterfaz() {
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(funciones));
        jList1.setModel(new DefaultListModel());
        jComboBox1.setSelectedIndex(-1);
        jTextArea2.setText("");
        jTextArea2.setColumns(0);
        jTextArea2.setRows(0);
    }

    private String actualizarTexto(String funcion) {
        String cons = nombresAlternativos.get(funcion);
        String nombre = funcion.trim().intern();
        int lugarNombre = nombre.indexOf(" ");
        
        if(lugarNombre >= 0) {
            nombre = nombre.substring(0, lugarNombre).intern();
        }
        
        int lugarAlternativo = cons.lastIndexOf(",");
        String nombreAlternativo;
            
        if (lugarAlternativo < 0) {
            nombreAlternativo = cons;
        } else {
            nombreAlternativo = cons.substring(0, lugarAlternativo) + " y" 
                    + cons.substring(lugarAlternativo + 1);
        }
        
        String descripcion = descripciones.get(funcion);
        String descripcionLarga = descripcionesLargas.get(funcion);
        StringBuilder constructor = new StringBuilder(descripcion.length() 
                + nombre.length() + nombreAlternativo.length() + 100);
        
        constructor = constructor.append("Nombre: ").append(nombre)
                .append("\n\nNombres Alternativos: ")
                .append(nombreAlternativo).append("\n\nDescripción Corta: ")
                .append(descripcion).append("\n\nDescripción:\n\n").append(descripcionLarga);
//        StringBuilder constructorDescripcion = new StringBuilder(
//                descripcionLarga.length() * 2);
//        
//        for (int i = 0; i < descripcionLarga.length(); i++) {
//            char caracter = descripcionLarga.charAt(i);
//            
//            if((i > 0) && (i % 20 == 0)) {
//                constructorDescripcion = constructorDescripcion.append("\n");
//            }
//            
//            constructorDescripcion = constructorDescripcion.append(("" + 
//                    caracter).intern());
//        }
        
        return constructor.toString().intern();
    }

    Pattern lineasPatron = compile("\n");
    
    private void cambiarTamanioTexto(String salida) {
        final JTextArea area = jTextArea2;
        Matcher m = lineasPatron.matcher(salida);
        int s = 1;
        
        while(m.find()) {
            s++;
        }
        
        synchronized (area) {
            area.setRows(s);
        }
    }

    private void salir() {
        if (!calculadora.Calculadora.getVentana().isVisible()) {
            calculadora.Calculadora.getVentanaAyuda().setVisible(true);
        }
        
        setVisible(false);
    }
    
    private void procesarBusqueda() {
        String cadenaBusqueda = jTextField2.getText();
        
        if(cadenaBusqueda != null && cadenaBusqueda.length() > 0) {
            DefaultListModel modelo = (DefaultListModel) jList1.getModel();
            modelo.clear();
            jTabbedPane1.setSelectedIndex(1);
            Documento[] busqueda = Busqueda.buscar(cadenaBusqueda);
            
            if(busqueda.length > 0) {
                String documento = busqueda[0].obtenerDocumento();
                jTextArea2.setText(documento);
                String item = textosDescriptivosInversos.get(documento);
                jComboBox1.setSelectedItem(item);
                String[] nombresFunciones = new String[busqueda.length];
                
                for (int i = 0; i < nombresFunciones.length; i++) {
                    nombresFunciones[i] = textosDescriptivosInversos.get(
                            busqueda[i].obtenerDocumento());
                    modelo.addElement(nombresFunciones[i]);
                }
            } else {                
                if(Busqueda.contienePalabrasComunes(cadenaBusqueda)) {
                    JOptionPane.showMessageDialog(this, "Búsqueda no encontrada"
                            + ": " + cadenaBusqueda + ". Tip: Evitar el uso de "
                            + "palabras comunes como pronombres,\n preposicione"
                            + "s y conjunciones pues no serán incluidas en la b"
                            + "úsqueda.", "Error: Búsqueda no encontrada",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Búsqueda no encontrada"
                            + ": " + cadenaBusqueda, "Error: Búsqueda no encont"
                            + "rada", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void inicializarBusqueda() {
        for (String nombre : textosDescriptivos.keySet()) {
            String descripcion = textosDescriptivos.get(nombre);
            Documento.anadirDocumento(descripcion);
            textosDescriptivosInversos.put(descripcion, nombre);
        }
    }

    private void actualizarAreaTexto(String item) {
        String texto = textosDescriptivos.get(item);
        cambiarTamanioTexto(texto);
        final JTextArea area = jTextArea2;
        
        synchronized (area) {
            area.setText(texto);
            area.repaint();
        }
    }

    private TreeModel generarModeloFunciones() {
        DefaultMutableTreeNode nodoRaiz = new DefaultMutableTreeNode(
                "Funciones");
        TreeMap<String, DefaultMutableTreeNode> listaNodos = new TreeMap<>();
        
        for (String funcion : funciones) {
            String nombreFuncion = funcion.substring(0, funcion.indexOf("[")).
                    trim();
            String[] jerarquia = Funcion.obtenerCategorizacion(nombreFuncion);
            
            if(jerarquia != null && jerarquia.length > 0) {
                DefaultMutableTreeNode nodo;
                DefaultMutableTreeNode nodoPrevio;
                String nombreActual = jerarquia[0];
                
                if(listaNodos.containsKey(nombreActual)) {
                    nodo = listaNodos.get(nombreActual);
                } else {
                    nodo = new DefaultMutableTreeNode(nombreActual);
                    nodoRaiz.add(nodo);
                    listaNodos.put(nombreActual, nodo);
                }
                
                nodoPrevio = nodo;
                
                for (int i = 1; i < jerarquia.length; i++) {
                    nombreActual += "|" + jerarquia[i];
                    
                    if(listaNodos.containsKey(nombreActual)) {
                        nodo = listaNodos.get(nombreActual);
                    } else {
                        nodo = new DefaultMutableTreeNode(jerarquia[i]);
                        nodoPrevio.add(nodo);
                        listaNodos.put(nombreActual, nodo);
                    }
                    
                    nodoPrevio = nodo;
                }
                
                nodo = new DefaultMutableTreeNode(nombreFuncion);
                nodoPrevio.add(nodo);
            }
        }
        
        return new DefaultTreeModel(nodoRaiz);
    }
    
    private void seleccionArbolEvento(TreeSelectionEvent evt) {
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)
                           jTree1.getLastSelectedPathComponent();

        if (nodo == null || !nodo.isLeaf()) {
            return;
        }
        
        String funcion = nombresLargos.get((String) nodo.getUserObject());
        actualizarAreaTexto(funcion);
        jComboBox1.setSelectedItem(funcion);
    }
}
