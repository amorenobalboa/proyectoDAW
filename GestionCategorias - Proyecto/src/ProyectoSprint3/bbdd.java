package ProyectoSprint3;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Angel Moreno Balboa
 */
public class bbdd {

    Connection con;
    String url, driver, usuario, password, sql;
    Statement st;

    /**
     * FUNCIONES DE CONEXIÓN Y CIERRE CON BASE DE DATOS
     */
    public bbdd() {
        con = null;
        url = "jdbc:mysql://localhost:3306/gestioncategorias?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
        driver = "com.mysql.cj.jdbc.Driver";
        usuario = "root";
        password = "";
        sql = "";

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, usuario, password);
            st = con.createStatement();
            System.out.println("Conexión establecida");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Error al intentar conectar con la base de datos: " + ex.getMessage());
        }
    }

    public void close() {
        try {
            st.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(bbdd.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * FUNCION PARA LOGUEAR EN EL SISTEMA
     *
     * @param user
     * @param pass
     * @return
     */
    public byte datosUsuario(String user, String pass) {
        byte check = 0;
        int contador = 0;
        try {
            sql = "SELECT `NOM`,`PASS` FROM `usuarios`"
                    + " WHERE `NOM` = '" + user + "' AND `PASS` = '" + pass + "'";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                contador++;
            }
            switch (contador) {
                case 0:
                    check = 0; // No hay usuario con este login
                    break;
                case 1:
                    check = 1; // Exito encontrado solo un usuario
                    break;
                default:
                    check = 2; // Hay más usuarios
                    break;
            }

            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los usuarios:\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return check;
    }

    /**
     * COMPROBACIÓN DE NOMBRES EN BASE DE DATOS
     *
     * @param user
     * @return
     */
    public byte datosCreate(String user) {
        byte check = 0;
        int contador = 0;
        try {
            sql = "SELECT `NOM` FROM `usuarios`"
                    + " WHERE `NOM` = '" + user + "'";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                contador++;
            }
            switch (contador) {
                case 0:
                    check = 0; // No hay usuario con este login
                    break;
                case 1:
                    check = 1; // Exito encontrado solo un usuario
                    break;
                default:
                    check = 2; // Hay más usuarios
                    break;
            }

            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage() + " Error al cargar los usuarios:\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return check;
    }

    /**
     * CREACIÓN DE USUARIO
     *
     * @param user
     * @param pass
     */
    public void crearUsuario(String user, String pass) {

        try {

            sql = "INSERT INTO `usuarios`(`NOM`, `PASS`,`ROL`) VALUES "
                    + "('" + user + "','" + pass + "','0')";
            st.executeUpdate(sql);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear el usuario:\n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * IMPORTACIÓN DE CATEGORÍAS PARA GENERACIÓN DE TABLA
     *
     * @return
     */
    public ArrayList<Categoria> datosCategorias() {
        ArrayList categorias = new ArrayList();

        try {

            sql = "SELECT `CODICAT`,`NOMBRE`,`TITULO`,`CONTENIDO`,`METADESC`,`SITEMAPC`,`ACTUALIZADO` FROM `categorias`";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int codicat = rs.getInt("CODICAT");
                String nombre = rs.getString("NOMBRE");
                String titulo = rs.getString("TITULO");
                String contenido = rs.getString("CONTENIDO");
                String metadesc = rs.getString("METADESC");
                String actualizado = rs.getString("ACTUALIZADO");
                int sitemap = rs.getInt("SITEMAPC");

                categorias.add(new Categoria(codicat, nombre, titulo, contenido, metadesc, sitemap, actualizado));
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;

    }

    /**
     * FUNCIÓNES PARA LA CREACIÓN DE CATEGORÍAS
     *
     * @param cat
     */
    public void crearCategoria(Categoria cat) {
        try {
            sql = "INSERT INTO `categorias`(`NOMBRE`, `TITULO`, `CONTENIDO`, `METADESC`, `SITEMAPC`, `ACTUALIZADO`)"
                    + " VALUES "
                    + "('" + cat.nombre + "','" + cat.titulo + "','" + cat.contenido + "','" + cat.metadesc + "','" + cat.sitemap + "','" + cat.actualizado + "')";
            st.executeUpdate(sql);

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error a la hora de crear la categoría:\n"
                    + e.getMessage(), "Error al crear", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(bbdd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkNombreCategoria(String nombre) {

        try {

            String sql = "SELECT NOMBRE FROM CATEGORIAS WHERE NOMBRE = '" + nombre + "'";
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }

                return false;
            } else {
                try {
                    rs.close();
                } catch (Exception e) {
                }

                return true;
            }
        } catch (SQLException s) {
            JOptionPane.showMessageDialog(null, "Error al comprobar si el nombre ya existe:\n" + s.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /**
     * FUNCIONES DEL MENÚ PRINCIPAL
     */
    /**
     * Eliminacón de categoría
     *
     * @param codicat
     */
    public void eliminarCategoria(int codicat) {

        try {
            sql = "DELETE FROM `categorias` WHERE `CODICAT` = " + codicat;
            st = con.createStatement();
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Categoría con código = " + codicat + " borrada con éxito",
                    "Borrado correcto", JOptionPane.INFORMATION_MESSAGE);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error a la hora de eliminar la categoría con código = " + codicat + ":\n"
                    + e.getMessage(), "Error al eliminar", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Modificar el nombre
     *
     * @param codicat
     * @param nombre
     */
    public void modificarNombre(int codicat, String nombre) {
        try {
            sql = "UPDATE `categorias` SET `NOMBRE`= '" + nombre + "' WHERE `CODICAT` = " + codicat;
            st = con.createStatement();
            st.executeUpdate(sql);
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error a la hora de modifcar el nombre de la categoría con código = " + codicat + ":\n"
                    + e.getMessage(), "Error al modificar", JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Modificar el título
     *
     * @param codicat
     * @param nombre
     */
    public void modificarTitulo(int codicat, String nombre) {
        try {
            sql = "UPDATE `categorias` SET `TITULO`= '" + nombre + "' WHERE `CODICAT` = " + codicat;
            st = con.createStatement();
            st.executeUpdate(sql);
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error a la hora de modifcar el título de la categoría con con código = " + codicat + ":\n"
                    + e.getMessage(), "Error al modificar", JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Modificar el contenido
     *
     * @param codicat
     * @param contenido
     */
    public void modificarContenido(int codicat, String contenido) {
        try {
            sql = "UPDATE `categorias` SET `CONTENIDO`= '" + contenido + "' WHERE `CODICAT` = " + codicat;
            st = con.createStatement();
            st.executeUpdate(sql);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error a la hora de modifcar el contenido de la categoría con con código = " + codicat + ":\n"
                    + e.getMessage(), "Error al modificar", JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Modificar la metadescription
     *
     * @param codicat
     * @param contenido
     */
    public void modificarMetadesc(int codicat, String contenido) {
        try {
            sql = "UPDATE `categorias` SET `METADESC`= '" + contenido + "' WHERE `CODICAT` = " + codicat;
            st = con.createStatement();
            st.executeUpdate(sql);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error a la hora de modifcar el contenido de la categoría con con código = " + codicat + ":\n"
                    + e.getMessage(), "Error al modificar", JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Modificar el sitemap
     *
     * @param codicat
     * @param check
     */
    public void modificarSitemap(int codicat, int check) {
        try {
            sql = "UPDATE `categorias` SET `SITEMAPC`= " + check + " WHERE `CODICAT` = " + codicat;
            st = con.createStatement();
            st.executeUpdate(sql);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error a la hora de modifcar el sitemap de la categoría con con código = " + codicat + ":\n"
                    + e.getMessage(), "Error al modificar", JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Updateamos la fecha de última actualización
     *
     * @param codicat
     */
    public void updateDate(int codicat) {
        try {
            LocalDateTime ldt = LocalDateTime.now();
            String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ldt);
            sql = "UPDATE `categorias` SET `ACTUALIZADO`= '" + date + "' WHERE `CODICAT` = " + codicat;
            st = con.createStatement();
            st.executeUpdate(sql);

        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error a la hora de modifcar el sitemap de la categoría con con código = " + codicat + ":\n"
                    + e.getMessage(), "Error al modificar", JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Construimos el ArrayList que nos permitirá exportar el sitemap de
     * categorias y productos
     *
     * @return
     */
    public ArrayList<String> getSitemap() {
        ArrayList<String> sitemap = new ArrayList<>();
        String url;
        try {
            String sql = "SELECT NOMBRE FROM CATEGORIAS";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                url = rs.getString("NOMBRE").toLowerCase().replaceAll(" ", "-") + ".html";
                sitemap.add(url);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error a la hora de importar el sitemap"
                    + e.getMessage(), "Error al modificar", JOptionPane.ERROR_MESSAGE);

        }
        try {
            String sql = "SELECT NAME FROM PRODUCTOS";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                url = rs.getString("NAME").toLowerCase().replaceAll(" ", "-") + ".html";
                sitemap.add(url);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error a la hora de importar el sitemap"
                    + e.getMessage(), "Error al modificar", JOptionPane.ERROR_MESSAGE);

        }

        return sitemap;
    }

    /**
     * IMPORTAMOS DATOS DE TODOS LOS PRODUCTOS
     *
     * @return
     */
    public ArrayList<Producto> datosProductos(int codicat) {
        ArrayList productos = new ArrayList();

        try {

            sql = "SELECT `ENTITY_ID`,`NAME` FROM `productos` p \n"
                    + "JOIN `prodcats` pp ON p.ENTITY_ID = pp.PRODUCTO \n"
                    + "JOIN `categorias` c ON c.CODICAT = pp.CATEGORIA \n"
                    + "WHERE c.CODICAT != " + codicat;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("ENTITY_ID");
                String nombre = rs.getString("NAME");
                productos.add(new Producto(id, nombre));
            }

            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error a la hora de importar los productos totales"
                    + e.getMessage(), "Error al importar", JOptionPane.ERROR_MESSAGE);
        }

        return productos;

    }

    /**
     * IMPORTAMOS DATOS DE PRODUCTOS DE CATEGORÍAS
     *
     * @param codicat
     * @return
     */
    public ArrayList<Producto> datosProductosCategoria(int codicat) {
        ArrayList productos = new ArrayList();

        try {

            sql = "SELECT `ENTITY_ID`,`NAME` FROM `productos` p \n"
                    + "JOIN `prodcats` pp ON p.ENTITY_ID = pp.PRODUCTO\n"
                    + "JOIN `categorias` c ON c.CODICAT = pp.CATEGORIA\n"
                    + "WHERE c.CODICAT= " + codicat;
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("ENTITY_ID");
                String nombre = rs.getString("NAME");
                productos.add(new Producto(id, nombre));
            }

            rs.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error a la hora de importar los productos por categoria"
                    + e.getMessage(), "Error al importar", JOptionPane.ERROR_MESSAGE);
        }

        return productos;

    }

    /**
     * PROCESO PARA ASIGNACIÓN DE PRODUCTOS A CATEGORIAS
     *
     * @param codicat
     * @param nuevosProductos
     */
    public void asignarProductos(int codicat, ArrayList<Integer> nuevosProductos) {
        /**
         * Almacenamos en un array los productos viejos para borrarlos cuando
         * sea necesario
         */
        ArrayList<Integer> productosViejos = new ArrayList<>();
        try {
            String sql = "SELECT PRODUCTO FROM PRODCATS WHERE CATEGORIA = " + codicat + ";";
            ResultSet res = st.executeQuery(sql);

            while (res.next()) {
                productosViejos.add(res.getInt("PRODUCTO"));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error a la hora de comprobar "
                    + "los productos de la categoría con código = " + codicat + ":\n"
                    + ex.getMessage(), "Error al asignar", JOptionPane.ERROR_MESSAGE);
        }

        /**
         * Condiciones necesarias para asegurar la base de datos
         */
        if (productosViejos.isEmpty() && !nuevosProductos.isEmpty()) {

            insertProductos(codicat, nuevosProductos);

        } else if (!productosViejos.isEmpty() && nuevosProductos.isEmpty()) {

            deleteProductos(codicat);

        } else if (!productosViejos.isEmpty() && !nuevosProductos.isEmpty()) {

            ArrayList<Integer> delentids = new ArrayList<>();
            ArrayList<Integer> insentids = new ArrayList<>();

            for (int i = 0; i < productosViejos.size(); i++) {
                for (int j = 0; j < nuevosProductos.size(); j++) {
                    if (productosViejos.get(i).equals(nuevosProductos.get(j))) {
                        break;
                    }
                    if (j == nuevosProductos.size() - 1) {
                        delentids.add(productosViejos.get(i));
                    }
                }
            }
            for (int j = 0; j < nuevosProductos.size(); j++) {
                for (int i = 0; i < productosViejos.size(); i++) {
                    if (productosViejos.get(i).equals(nuevosProductos.get(j))) {
                        break;
                    }
                    if (i == productosViejos.size() - 1) {
                        insentids.add(nuevosProductos.get(j));
                    }
                }
            }

            if (!delentids.isEmpty()) {
                deleteProductos(codicat, delentids);
            }

            if (!insentids.isEmpty()) {
                insertProductos(codicat, insentids);
            }
        }
    }

    /**
     * PROCESO PARA ASIGNAR PRODUCTOS
     *
     * @param codicat
     * @param newentids
     */
    public void insertProductos(Integer codicat, ArrayList<Integer> newentids) {
        try {
            String sql = "INSERT INTO PRODCATS (PRODUCTO,CATEGORIA) VALUES ";
            for (int i = 0; i < newentids.size(); i++) {
                sql += "(" + newentids.get(i) + "," + codicat + ")";
                if (i != newentids.size() - 1) {
                    sql += ",";
                } else {
                    sql += ";";
                }
            }
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Productos añadidos a la "
                    + "categoría con código = " + codicat + " con éxito",
                    "Modificación correcta", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException s) {
            JOptionPane.showMessageDialog(null, "Error a la hora de añadir "
                    + "productos a la categoría con código = " + codicat + ":\n"
                    + s.getMessage(), "Error al modificar", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * PROCESOS PARA BORRAR LOS PRODUCTOS EXISTENTES
     *
     * @param codicat
     * @param delentids
     */
    public void deleteProductos(Integer codicat, ArrayList<Integer> delentids) {
        try {
            String sql = "DELETE FROM PRODCATS WHERE ";
            for (int i = 0; i < delentids.size(); i++) {
                sql += "(PRODUCTO = " + delentids.get(i) + " AND CATEGORIA = " + codicat + ")";
                if (i != delentids.size() - 1) {
                    sql += " OR ";
                } else {
                    sql += ";";
                }
            }
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Productos eliminados de la"
                    + " categoría con código = " + codicat + " con éxito",
                    "Modificación correcta", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException s) {
            JOptionPane.showMessageDialog(null, "Error a la hora de eliminar "
                    + "productos de la categoría con código = " + codicat + ":\n"
                    + s.getMessage(), "Error al asignar", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteProductos(Integer codicat) {
        try {
            String sql = "DELETE FROM PRODCATS WHERE CATEGORIA=" + codicat;
            st.executeUpdate(sql);

        } catch (SQLException s) {
            JOptionPane.showMessageDialog(null, "Error a la hora de eliminar"
                    + " productos de la categoría con código = " + codicat + ":\n"
                    + s.getMessage(), "Error al asignar", JOptionPane.ERROR_MESSAGE);
        }
    }

}
