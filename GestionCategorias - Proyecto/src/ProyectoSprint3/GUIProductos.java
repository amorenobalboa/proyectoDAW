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
public class GUIProductos extends javax.swing.JFrame {

    /**
     * Creates new form asignarProductos
     */
    int cat;

    public GUIProductos(int cat) {
        this.cat = cat;
        initComponents();
        getSelectedProducts();
        getProducts();

        //Funciones gráficas varias
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("imagenes/icon.jpg")));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        panelOpciones = new javax.swing.JPanel();
        asignarProductos = new javax.swing.JButton();
        labInformativo = new javax.swing.JLabel();
        scrollTableSelects = new javax.swing.JScrollPane();
        panelProductosCategoria = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(197, 221, 253));

        panelPrincipal.setBackground(new java.awt.Color(154, 218, 237));
        panelPrincipal.setLayout(new java.awt.BorderLayout());

        asignarProductos.setText("Asignar Productos");
        asignarProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                asignarProductosMouseClicked(evt);
            }
        });

        labInformativo.setForeground(new java.awt.Color(96, 96, 96));
        labInformativo.setText("Manten Presionado control para seleccionar múltiples artículos");

        javax.swing.GroupLayout panelOpcionesLayout = new javax.swing.GroupLayout(panelOpciones);
        panelOpciones.setLayout(panelOpcionesLayout);
        panelOpcionesLayout.setHorizontalGroup(
            panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOpcionesLayout.createSequentialGroup()
                .addGroup(panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOpcionesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labInformativo))
                    .addGroup(panelOpcionesLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(asignarProductos)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        panelOpcionesLayout.setVerticalGroup(
            panelOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labInformativo)
                .addGap(18, 18, 18)
                .addComponent(asignarProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        panelPrincipal.add(panelOpciones, java.awt.BorderLayout.PAGE_END);

        panelProductosCategoria.setAutoCreateRowSorter(true);
        panelProductosCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Artículo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        panelProductosCategoria.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
        scrollTableSelects.setViewportView(panelProductosCategoria);
        if (panelProductosCategoria.getColumnModel().getColumnCount() > 0) {
            panelProductosCategoria.getColumnModel().getColumn(0).setMinWidth(40);
            panelProductosCategoria.getColumnModel().getColumn(0).setPreferredWidth(60);
            panelProductosCategoria.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        panelPrincipal.add(scrollTableSelects, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void asignarProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_asignarProductosMouseClicked
        /**
         * CREAMOS EL ARRAYLIST QUE NECESITA LA BASE DE DATOS PARA LA ASIGNACIÓN
         * DE ARTÍCULOS CON LAS CATEGORÍAS
         */
        
        ArrayList<Integer> entids = new ArrayList<Integer>();
        int[] filasSeleccionadas = panelProductosCategoria.getSelectedRows();
        
        for (int i = 0; i < filasSeleccionadas.length; i++) {
            Integer selected = (Integer) panelProductosCategoria.getModel().getValueAt(filasSeleccionadas[i], 0);
            entids.add(selected);
        }
        bbdd conexion = new bbdd();
        conexion.asignarProductos(cat, entids);
        conexion.close();
        this.setVisible(false);
    }//GEN-LAST:event_asignarProductosMouseClicked

    /**
     * SACAMOS LOS PRODUCTOS QUE YA TIENEN LAS CATEGORÍAS SEÑALADA
     */
    public void getSelectedProducts() {
        bbdd conexion = new bbdd();
        ArrayList<Producto> productos = conexion.datosProductosCategoria(cat);
        DefaultTableModel modelo = (DefaultTableModel) panelProductosCategoria.getModel();
        Object[] row = new Object[2];
        for (int i = 0; i < productos.size(); i++) {
            row[0] = productos.get(i).id;
            row[1] = productos.get(i).name;
            modelo.addRow(row);
            panelProductosCategoria.setRowSelectionInterval(0, i);
        }
        conexion.close();
    }

    /**
     * SACAMOS EL RESTO DE PRODUCTOS
     */
    public void getProducts() {
        bbdd conexion = new bbdd();
        ArrayList<Producto> productos = conexion.datosProductos(cat);
        DefaultTableModel modelo = (DefaultTableModel) panelProductosCategoria.getModel();
        Object[] row = new Object[2];
        for (int i = 0; i < productos.size(); i++) {
            row[0] = productos.get(i).id;
            row[1] = productos.get(i).name;
            modelo.addRow(row);
        }
        conexion.close();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton asignarProductos;
    private javax.swing.JLabel labInformativo;
    private javax.swing.JPanel panelOpciones;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JTable panelProductosCategoria;
    private javax.swing.JScrollPane scrollTableSelects;
    // End of variables declaration//GEN-END:variables
}
