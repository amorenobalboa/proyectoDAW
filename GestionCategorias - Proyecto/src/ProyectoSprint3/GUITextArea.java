/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProyectoSprint3;

/**
 *
 * @author amore
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @version 1.0
 * @author Dani
 */
class GUITextArea extends JFrame implements ActionListener {

    int tipo;
    int categoria;
    String nombre;
    String contenido;
    bbdd conexion;
    JPanel panelPrincipal;
    JLabel labelText;
    JTextArea textArea;
    JScrollPane scrollTextarea;
    JButton modificar;

    public GUITextArea(int tipo, String nombre, int categoria, String contenido) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.contenido = contenido;

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new FlowLayout());
        panelPrincipal.setBackground(new java.awt.Color(154, 218, 237));

        textArea = new JTextArea();
        textArea.setLineWrap(true);

        scrollTextarea = new JScrollPane(textArea);
        scrollTextarea.setPreferredSize(new Dimension(900, 500));

        labelText = new JLabel();

        modificar = new JButton("Modificar");
        modificar.addActionListener(this);

        funcionCerrar();

        switch (tipo) {
            case 1: {
                setTitle("Modificar el contenido de la categoría \"" + nombre + "\"");
                labelText.setText("Nuevo contenido");
                textArea.setText(contenido);
                textArea.selectAll();
                break;
            }
            case 2: {
                setTitle("Modificar la meta description de la categoría \"" + nombre + "\"");
                labelText.setText("Nueva meta description");
                textArea.setText(contenido);
                textArea.selectAll();
                break;
            }
            default:
                break;
        }

        panelPrincipal.add(labelText);
        panelPrincipal.add(scrollTextarea);

        setLayout(new BorderLayout());
        add("Center", panelPrincipal);
        add("South", modificar);
        setSize(940, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("imagenes/icon.jpg")));
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        conexion = new bbdd();
        JButton be = (JButton) e.getSource();
        if (be.equals(modificar)) {
            switch (tipo) {
                case 1: {
                    conexion.modificarContenido(categoria, textArea.getText());
                    conexion.updateDate(categoria);
                    dispose();
                    menuCategorias panelCategorias = new menuCategorias();
                    panelCategorias.setVisible(true);
                    break;
                }
                case 2: {
                    conexion.modificarMetadesc(categoria, textArea.getText());
                    conexion.updateDate(categoria);
                    dispose();
                    menuCategorias panelCategorias = new menuCategorias();
                    panelCategorias.setVisible(true);
                    break;
                }
                default:
                    break;
            }
        }
        conexion.close();
    }

    public void funcionCerrar() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                menuCategorias panelCategorias = new menuCategorias();
                panelCategorias.setVisible(true);
            }
        });
    }
}
