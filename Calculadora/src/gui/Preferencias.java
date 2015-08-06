/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import calculadora.Calculadora;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JColorChooser;

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
public class Preferencias extends javax.swing.JFrame {

    private static int precision = VentanaCalculo.getPrecision();
    private static Color colorFondo = VentanaCalculo.getColorFondo();
    private static Color colorBoton = VentanaCalculo.getColorBoton();
    private static Color colorFuente = VentanaCalculo.getColorFuente();
    private static final long serialVersionUID = -10L;
    private boolean cambiandoColorFondo = false;
    private boolean cambiandoColorBoton = false;
    private boolean cambiandoColorFuente = false;

    /**
     * Creates new form Preferencias
     */
    public Preferencias() {
        initComponents();

        try {
            setIconImage(ImageIO.read(getClass().getResource(
                    "/gui/preferences.png")));
        } catch (IOException ex) {
            Logger.getLogger(VentanaCalculo.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            if (Calculadora.getSelectorColor().isAceptado()) {
                if (cambiandoColorBoton) {
                    jButton11.setBackground(Calculadora.getSelectorColor()
                            .getColor());
                    Preferencias.colorBoton = Calculadora.getSelectorColor()
                            .getColor();
                    cambiandoColorBoton = false;
                    jButton11.repaint();
                }

                if (cambiandoColorFondo) {
                    jPanel5.setBackground(Calculadora.getSelectorColor()
                            .getColor());
                    Preferencias.colorFondo = Calculadora.getSelectorColor()
                            .getColor();
                    cambiandoColorFondo = false;
                    jPanel5.repaint();
                }

                if (cambiandoColorFuente) {
                    jLabel4.setForeground(Calculadora.getSelectorColor()
                            .getColor());
                    Preferencias.colorFuente = Calculadora.getSelectorColor()
                            .getColor();
                    cambiandoColorFuente = false;
                    jLabel4.repaint();
                }

                Calculadora.getSelectorColor().setAceptado(false);
                repaint();
            } else {
                Preferencias.precision = VentanaCalculo.getPrecision();
                Preferencias.colorFondo = VentanaCalculo.getColorFondo();
                Preferencias.colorBoton = VentanaCalculo.getColorBoton();
                Preferencias.colorFuente = VentanaCalculo.getColorFuente();
                actualizar();
            }

            this.toFront();
        }

        setLocationRelativeTo(Calculadora.getVentana());
        super.setVisible(b);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jSlider1 = new javax.swing.JSlider();
        jLabel5 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();

        setTitle("Preferencias");
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                salirPreferenciasEvento(evt);
            }
        });

        jPanel2.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);

        jPanel1.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Vista", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu Light", 0, 24), java.awt.Color.black)); // NOI18N

        jPanel3.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Color", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu Light", 0, 24), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jLabel1.setText("Fuente");

        jButton1.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
        jButton1.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jButton1.setText("Cambiar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonColorFuente(evt);
            }
        });

        jPanel6.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fondo", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu Light", 0, 24), new java.awt.Color(0, 0, 0))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jLabel2.setText("Espacio vacío");

        jPanel5.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 127, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 34, Short.MAX_VALUE)
        );

        jButton2.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
        jButton2.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jButton2.setText("Cambiar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEspacioVacio(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jLabel3.setText("Botones");

        jButton3.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
        jButton3.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jButton3.setText("Cambiar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBotones(evt);
            }
        });

        jButton11.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jButton11.setText("Prueba");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton11)))
                .addGap(12, 12, 12))
        );

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Color Fuente");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Precisión", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu Light", 0, 24), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel8.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                cambioRuedaPanel(evt);
            }
        });

        jSlider1.setMaximum(64);
        jSlider1.setValue(3);
        jSlider1.setOpaque(false);
        jSlider1.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                cambioRuedaRaton(evt);
            }
        });
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                estadoDeslizante(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setLabelFor(jSlider1);
        jLabel5.setText("3");
        jLabel5.setPreferredSize(new java.awt.Dimension(51, 28));
        jLabel5.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                cambioRuedaEtiqueta(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jPanel9.setBackground(VentanaCalculo.COLOR_FONDO_PREDETERMINADO);
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Predeterminados", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu Light", 0, 24), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel9.setForeground(new java.awt.Color(0, 0, 0));

        jButton5.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
        jButton5.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jButton5.setText("Fuente");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonFuentePredeterminada(evt);
            }
        });

        jButton6.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
        jButton6.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jButton6.setText("Espacio Vacío");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEspacioVacioPredeterminado(evt);
            }
        });

        jButton7.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
        jButton7.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jButton7.setText("Botones");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBotonesPredeterminado(evt);
            }
        });

        jButton8.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
        jButton8.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jButton8.setText("Precisión");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPrecisionPredeterminada(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton8)
                .addContainerGap())
        );

        jButton9.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
        jButton9.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jButton9.setText("Aceptar");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptar(evt);
            }
        });

        jButton10.setBackground(VentanaCalculo.COLOR_BOTON_PREDETERMINADO);
        jButton10.setFont(new java.awt.Font("Ubuntu Light", 0, 24)); // NOI18N
        jButton10.setText("Cancelar");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelar(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(jButton9)
                .addGap(177, 177, 177)
                .addComponent(jButton10)
                .addGap(178, 178, 178))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9)
                    .addComponent(jButton10))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleParent(Calculadora.getVentana());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonPrecisionPredeterminada(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPrecisionPredeterminada
        Preferencias.precision = VentanaCalculo.PRECISION_PREDETERMINADA;
        actualizar();
        repaint();
    }//GEN-LAST:event_botonPrecisionPredeterminada

    private void botonAceptar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptar
        VentanaCalculo.setColorBoton(colorBoton);
        VentanaCalculo.setColorFondo(colorFondo);
        VentanaCalculo.setColorFuente(colorFuente);
        VentanaCalculo.setPrecision(precision);
        Calculadora.getVentana().cambiarColorBoton();
        Calculadora.getVentana().cambiarColorFondo();
        Calculadora.getVentana().cambiarColorFuente();
        setVisible(false);
        Calculadora.getVentana().repaint();
        Calculadora.getVentana().setVisible(true);
    }//GEN-LAST:event_botonAceptar

    private void botonCancelar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelar
        setVisible(false);
        Calculadora.getVentana().setVisible(true);
    }//GEN-LAST:event_botonCancelar

    private void botonFuentePredeterminada(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonFuentePredeterminada
        Preferencias.colorFuente = VentanaCalculo.COLOR_FUENTE_PREDETERMINADO;
        actualizar();
        repaint();
    }//GEN-LAST:event_botonFuentePredeterminada

    private void botonEspacioVacioPredeterminado(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEspacioVacioPredeterminado
        Preferencias.colorFondo = VentanaCalculo.COLOR_FONDO_PREDETERMINADO;
        actualizar();
        repaint();
    }//GEN-LAST:event_botonEspacioVacioPredeterminado

    private void botonBotonesPredeterminado(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBotonesPredeterminado
        Preferencias.colorBoton = VentanaCalculo.COLOR_BOTON_PREDETERMINADO;
        actualizar();
        repaint();
    }//GEN-LAST:event_botonBotonesPredeterminado

    private void botonColorFuente(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonColorFuente
        setVisible(false);
        cambiandoColorFuente = true;
        Calculadora.getSelectorColor().actualizar();
        Calculadora.getSelectorColor().setVisible(true);
//        colorFuente = jColorChooser2.getColor();
//        actualizar();
//        repaint();
    }//GEN-LAST:event_botonColorFuente

    private void botonEspacioVacio(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEspacioVacio
        setVisible(false);
        Calculadora.getSelectorColor().actualizar();
        Calculadora.getSelectorColor().setVisible(true);
        cambiandoColorFondo = true;
    }//GEN-LAST:event_botonEspacioVacio

    private void botonBotones(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBotones
        setVisible(false);
        Calculadora.getSelectorColor().actualizar();
        Calculadora.getSelectorColor().setVisible(true);
        cambiandoColorBoton = true;
    }//GEN-LAST:event_botonBotones

    private void estadoDeslizante(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_estadoDeslizante
        cambiarPrecisionEvento();
    }//GEN-LAST:event_estadoDeslizante

    private void cambioRuedaRaton(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_cambioRuedaRaton
        int nuevaPrecision = evt.getWheelRotation();
        
        if(nuevaPrecision != 0) {
            jSlider1.setValue(precision + nuevaPrecision);
            cambiarPrecisionEvento();
        }
    }//GEN-LAST:event_cambioRuedaRaton

    private void cambioRuedaEtiqueta(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_cambioRuedaEtiqueta
        int nuevaPrecision = evt.getWheelRotation();
        
        if(nuevaPrecision != 0) {
            jSlider1.setValue(precision + nuevaPrecision);
            cambiarPrecisionEvento();
        }
    }//GEN-LAST:event_cambioRuedaEtiqueta

    private void cambioRuedaPanel(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_cambioRuedaPanel
        int nuevaPrecision = evt.getWheelRotation();
        
        if(nuevaPrecision != 0) {
            jSlider1.setValue(precision + nuevaPrecision);
            cambiarPrecisionEvento();
        }
    }//GEN-LAST:event_cambioRuedaPanel

    private void salirPreferenciasEvento(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_salirPreferenciasEvento
        salir();
    }//GEN-LAST:event_salirPreferenciasEvento

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
            java.util.logging.Logger.getLogger(Preferencias.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Preferencias.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Preferencias.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Preferencias.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Calculadora.getPreferencias().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSlider jSlider1;
    // End of variables declaration//GEN-END:variables

    private void actualizar() {
        jPanel1.setBackground(colorFondo);
        jPanel2.setBackground(colorFondo);
        jPanel3.setBackground(colorFondo);
        jPanel5.setBackground(colorFondo);
        jPanel6.setBackground(colorFondo);
        jPanel8.setBackground(colorFondo);
        jPanel9.setBackground(colorFondo);
        jButton1.setBackground(colorBoton);
        jButton10.setBackground(colorBoton);
        jButton11.setBackground(colorFondo);
        jButton2.setBackground(colorBoton);
        jButton3.setBackground(colorBoton);
        jButton5.setBackground(colorBoton);
        jButton6.setBackground(colorBoton);
        jButton7.setBackground(colorBoton);
        jButton8.setBackground(colorBoton);
        jButton9.setBackground(colorBoton);
        jButton1.setForeground(colorFuente);
        jButton10.setForeground(colorFuente);
        jButton11.setForeground(colorFuente);
        jButton2.setForeground(colorFuente);
        jButton3.setForeground(colorFuente);
        jButton5.setForeground(colorFuente);
        jButton6.setForeground(colorFuente);
        jButton7.setForeground(colorFuente);
        jButton8.setForeground(colorFuente);
        jButton9.setForeground(colorFuente);
        jLabel1.setForeground(colorFuente);
        jLabel2.setForeground(colorFuente);
        jLabel3.setForeground(colorFuente);
        jLabel4.setForeground(colorFuente);
        jLabel5.setForeground(colorFuente);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
                "Vista", javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font(
                "Ubuntu Light", 0, 24), colorFuente));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
                "Color", javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font(
                "Ubuntu Light", 0, 24), colorFuente));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
                "Fondo", javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font(
                "Ubuntu Light", 0, 24), colorFuente));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
                "Precisión",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Ubuntu Light", 0, 24),
                colorFuente));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
                "Predeterminados",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Ubuntu Light", 0, 24),
                colorFuente));
        jSlider1.setValue(precision);
        jLabel5.setText("" + precision);
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
     * @return
     */
    public static Color getColorBoton() {
        return colorBoton;
    }

    /**
     *
     * @return
     */
    public static Color getColorFuente() {
        return colorFuente;
    }

    private void cambiarPrecisionEvento() {
        precision = jSlider1.getValue();
        jLabel5.setText("" + precision);
    }
    
    private void salir() {
        calculadora.Calculadora.getVentana().setVisible(true);
//        setVisible(false);
    }
}
