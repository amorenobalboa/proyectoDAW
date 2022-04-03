/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProyectoSprint3;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author amore
 *
 *
 * CREAMOS LOS ELEMENTOS DEL MENÚ
 *
 *
 */
public class menuCategorias extends GUICategorias {

    public menuCategorias() {
        JFrame frame = new JFrame(GUICategorias.class.getSimpleName());
        JPopupMenu menuTabla = new JPopupMenu();

        /*
        SOLUCIONAMOS TRES PROBLEMAS: 
            -->CUANDO SELECCIONAMOS VARIAS FILAS
            -->QUE SE PUEDA SELECCIONAR SIN TENER QUE HACER LEFT CLICK ANTES
            -->QUE SOLO SE PUEDA SELECCIONAR UNA FILA
         */
        menuTabla.addPopupMenuListener(
                new PopupMenuListener() {

            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e
            ) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = categoriasTabla.rowAtPoint(SwingUtilities.convertPoint(menuTabla, new Point(0, 0), categoriasTabla));
                        if (rowAtPoint > -1) {
                            categoriasTabla.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e
            ) {
                // TODO Auto-generated method stub

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e
            ) {
                // TODO Auto-generated method stub

            }
        }
        );

        //Crear categoría
        JMenuItem crear = new JMenuItem("Crear categoría");

        crear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                botonCrear();
            }
        });
        menuTabla.add(crear);

        //Eliminar categoría
        JMenuItem eliminar = new JMenuItem("Eliminar categoría");

        eliminar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int categoriaEliminada = (int) categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 0);
                if (JOptionPane.showConfirmDialog(frame, "¿Seguro que quieres eliminar la categoría de " + categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 1) + "?") == JOptionPane.YES_OPTION) {
                    bbdd conexion = new bbdd();
                    conexion.eliminarCategoria(categoriaEliminada);
                    actualizar();
                    conexion.close();
                };
            }
        });
        menuTabla.add(eliminar);
        menuTabla.addSeparator();

        //Asignar Productos
        JMenuItem asignar = new JMenuItem("Asignar productos");

        asignar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int categoriaSeleccionada = (int) categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 0);
                new GUIProductos(categoriaSeleccionada).setVisible(true);
            }
        });
        menuTabla.add(asignar);
        menuTabla.addSeparator();

        //Cambiar Nombre
        JMenuItem nombre = new JMenuItem("Cambiar Nombre");

        nombre.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int categoriaID = (int) categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 0);
                String nombre = categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 1).toString();
                String nombreModificado = JOptionPane.showInputDialog(frame, "Modificar el nombre de la categoría " + categoriaID, nombre);

                if (nombreModificado != null) {
                    if (!nombre.equals(nombreModificado)) {
                        if (nombreModificado.length() < 3) {
                            JOptionPane.showMessageDialog(null, "Nombre demasiado corto", "Error al modificar", JOptionPane.ERROR_MESSAGE);
                        } else {
                            bbdd conexion = new bbdd();
                            conexion.modificarNombre(categoriaID, nombreModificado);
                            conexion.updateDate(categoriaID);
                            actualizar();
                            JOptionPane.showMessageDialog(null, "Categoría modificada", "Categoría modificada", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        });
        menuTabla.add(nombre);

        //Cambiar Título
        JMenuItem titulo = new JMenuItem("Cambiar Título");

        titulo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int categoriaID = (int) categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 0);
                String titulo = categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 2).toString();
                String tituloModificado = JOptionPane.showInputDialog(frame, "Modificar el nombre de la categoría " + categoriaID, titulo);

                if (tituloModificado != null) {
                    if (!titulo.equals(tituloModificado)) {
                        if (tituloModificado.length() < 3) {
                            JOptionPane.showMessageDialog(null, "Nombre demasiado corto", "Error al modificar", JOptionPane.ERROR_MESSAGE);
                        } else {
                            bbdd conexion = new bbdd();
                            conexion.modificarTitulo(categoriaID, tituloModificado);
                            conexion.updateDate(categoriaID);
                            actualizar();
                            JOptionPane.showMessageDialog(null, "Categoría modificada", "Categoría modificada", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                }

            }
        });
        menuTabla.add(titulo);

        //Cambiar contenido
        JMenuItem contenido = new JMenuItem("Cambiar Contenido");

        contenido.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                int categoriaID = (int) categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 0);
                String contenido = categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 3).toString();
                String nombre = categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 2).toString();
                GUITextArea contenidoModificar = new GUITextArea(1, nombre, categoriaID, contenido);

            }
        });
        menuTabla.add(contenido);

        //Cambiar Metadescripcion
        JMenuItem metadescripcion = new JMenuItem("Cambiar Metadescripcion");

        metadescripcion.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                int categoriaID = (int) categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 0);
                String contenido = categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 4).toString();
                String nombre = categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 2).toString();
                GUITextArea contenidoModificar = new GUITextArea(2, nombre, categoriaID, contenido);

            }
        });
        menuTabla.add(metadescripcion);

        //Cambiar Sitemap
        JMenuItem sitemap = new JMenuItem("Cambiar Sitemap");

        sitemap.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int categoriaID = (int) categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 0);
                String nombre = categoriasTabla.getValueAt(categoriasTabla.getSelectedRow(), 1).toString();
                int check = JOptionPane.showConfirmDialog(frame, "¿Quieres que esta categoría se indexe en Google?",
                        "Indexacion de la categoría " + nombre, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                int checked = 0;
                if (check == 0) {
                    checked = 1;
                } else if (check == 1) {
                    checked = 0;
                }
                bbdd conexion = new bbdd();
                conexion.modificarSitemap(categoriaID, checked);
                conexion.updateDate(categoriaID);
                actualizar();
                conexion.close();
                JOptionPane.showMessageDialog(null, "Categoría modificada", "Categoría modificada", JOptionPane.INFORMATION_MESSAGE);
            }

        });
        menuTabla.add(sitemap);

        categoriasTabla.setComponentPopupMenu(menuTabla);

    }

}
