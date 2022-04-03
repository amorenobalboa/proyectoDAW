/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProyectoSprint3;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author amore
 */
public class GUICategorias extends javax.swing.JFrame {

    /**
     * CREAMOS LA INTERFAZ GRÁFICA GUICategorias
     */
    public GUICategorias() {
        initComponents();
        muestraCategorias();
        buscar();

        //Funciones gráficas varias
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("imagenes/icon.jpg")));
    }


    /*
    GENERAMOS NUESTRA TABLA CON LA BASE DE DATOS
     */
    public void muestraCategorias() {
        bbdd conexion = new bbdd();
        ArrayList<Categoria> cats = conexion.datosCategorias();
        DefaultTableModel modelo = (DefaultTableModel) categoriasTabla.getModel();
        Object[] row = new Object[7];
        for (int i = 0; i < cats.size(); i++) {
            row[0] = cats.get(i).codicat;
            row[1] = cats.get(i).nombre;
            row[2] = cats.get(i).titulo;
            row[3] = cats.get(i).contenido;
            row[4] = cats.get(i).metadesc;
            row[5] = cats.get(i).sitemap;
            row[6] = cats.get(i).actualizado;
            modelo.addRow(row);
            categoriasTabla.setRowHeight(i, 22);
        }
        panelPrincipal.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Lista de categorías", TitledBorder.LEFT,
                TitledBorder.TOP));

    }

    /*
    MÉTODO PARA ACTUALIZAR LA TABLA EN ELIMINACIÓN DE CATEGORÍAS
     */
    public void actualizar() {
        bbdd conexion = new bbdd();
        ArrayList<Categoria> cats = conexion.datosCategorias();
        DefaultTableModel modelo = (DefaultTableModel) categoriasTabla.getModel();
        Object[] row = new Object[7];

        //BORRA TODA LA TABLA
        for (int i = modelo.getRowCount() - 1; i >= 0; i--) {
            modelo.removeRow(i);
        }
        modelo.setRowCount(0);
        // GENERA DE NUEVO TODA LA TABLA

        for (int i = 0; i < cats.size(); i++) {
            row[0] = cats.get(i).codicat;
            row[1] = cats.get(i).nombre;
            row[2] = cats.get(i).titulo;
            row[3] = cats.get(i).contenido;
            row[4] = cats.get(i).metadesc;
            row[5] = cats.get(i).sitemap;
            row[6] = cats.get(i).actualizado;
            modelo.addRow(row);
            categoriasTabla.setRowHeight(i, 22);

        }

        panelPrincipal.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Lista de categorías", TitledBorder.LEFT,
                TitledBorder.TOP));
    }

    /*
    CREAMOS EL CAMPO PARA BUSCAR LA CATEGORÍA
     */
    private void buscar() {
        TableRowSorter<TableModel> rowSorter
                = new TableRowSorter<>(categoriasTabla.getModel());
        categoriasTabla.setRowSorter(rowSorter);
        textoBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = textoBuscar.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = textoBuscar.getText();
                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });

    }

    public void botonCrear() {
        new GUICrearCategoria().setVisible(true);
        this.setVisible(false);
    }

    /**
     * CREAMOS LA INTERFAZ GRÁFICA DE TODO EL PANEL PRINCIPAL JUNTO CON LA TABLA
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        scrollBar = new javax.swing.JScrollPane();
        categoriasTabla = new javax.swing.JTable();
        panelBusqueda = new javax.swing.JPanel();
        labelBuscar = new javax.swing.JLabel();
        textoBuscar = new javax.swing.JTextField();
        sitemap = new javax.swing.JButton();
        salirSesion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelPrincipal.setBackground(new java.awt.Color(197, 221, 253));
        panelPrincipal.setAutoscrolls(true);
        panelPrincipal.setPreferredSize(new java.awt.Dimension(1000, 750));
        panelPrincipal.setLayout(new java.awt.BorderLayout());

        scrollBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        categoriasTabla.setAutoCreateRowSorter(true);
        categoriasTabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codicat", "Nombre", "Title", "Contenido", "Metadesc", "Sitemap", "Última actualización"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        categoriasTabla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        scrollBar.setViewportView(categoriasTabla);
        if (categoriasTabla.getColumnModel().getColumnCount() > 0) {
            categoriasTabla.getColumnModel().getColumn(0).setPreferredWidth(80);
            categoriasTabla.getColumnModel().getColumn(1).setPreferredWidth(150);
            categoriasTabla.getColumnModel().getColumn(2).setPreferredWidth(200);
            categoriasTabla.getColumnModel().getColumn(3).setPreferredWidth(240);
            categoriasTabla.getColumnModel().getColumn(4).setPreferredWidth(180);
            categoriasTabla.getColumnModel().getColumn(5).setPreferredWidth(80);
            categoriasTabla.getColumnModel().getColumn(6).setPreferredWidth(200);
        }

        panelPrincipal.add(scrollBar, java.awt.BorderLayout.CENTER);

        panelBusqueda.setLayout(new javax.swing.BoxLayout(panelBusqueda, javax.swing.BoxLayout.LINE_AXIS));

        labelBuscar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        labelBuscar.setText("Busca la categoria ");
        labelBuscar.setName(""); // NOI18N
        labelBuscar.setVerifyInputWhenFocusTarget(false);
        labelBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        panelBusqueda.add(labelBuscar);
        panelBusqueda.add(textoBuscar);

        sitemap.setText("Generar Sitemap");
        sitemap.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sitemap.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        sitemap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sitemapMouseClicked(evt);
            }
        });
        panelBusqueda.add(sitemap);

        salirSesion.setForeground(new java.awt.Color(255, 51, 51));
        salirSesion.setText("Salir");
        salirSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salirSesionMouseClicked(evt);
            }
        });
        panelBusqueda.add(salirSesion);

        panelPrincipal.add(panelBusqueda, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 1039, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 756, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * ASOCIAMOS LOS EVENTOS DE LOS BOTONES CON SUS FUNCIONES ASOCIADAS
     *
     * @param evt
     */

    private void salirSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salirSesionMouseClicked
        this.setVisible(false);
        new GUILog().setVisible(true);
    }//GEN-LAST:event_salirSesionMouseClicked

    private void sitemapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sitemapMouseClicked
        Sitemap generarSitemap = new Sitemap();
        generarSitemap.siteMap();
    }//GEN-LAST:event_sitemapMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable categoriasTabla;
    private javax.swing.JLabel labelBuscar;
    private javax.swing.JPanel panelBusqueda;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JButton salirSesion;
    private javax.swing.JScrollPane scrollBar;
    private javax.swing.JButton sitemap;
    private javax.swing.JTextField textoBuscar;
    // End of variables declaration//GEN-END:variables
}
