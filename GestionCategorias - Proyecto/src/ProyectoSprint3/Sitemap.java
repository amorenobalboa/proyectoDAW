/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProyectoSprint3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;

/**
 *
 * @author amore
 */
public class Sitemap {

    bbdd conexion;
    JFileChooser guardarComo;
    ArrayList sitemapGenerado;
    File sitemap;
    DateFormat dateFormat;

    public Sitemap() {

    }

    public void siteMap() {

        conexion = new bbdd();
        sitemapGenerado = conexion.getSitemap();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        guardarComo = new JFileChooser();
        guardarComo.setSelectedFile(new File("sitemap"));
        guardarComo.showSaveDialog(null);
        sitemap = new File(guardarComo.getSelectedFile() + ".xml");

        try {
            try (BufferedWriter salida = new BufferedWriter(new FileWriter(sitemap))) {
                salida.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
                salida.write("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\r\n");
                salida.write("<url>\r\n");
                salida.write("\t<loc>https://www.dominio.com/</loc>\r\n");
                salida.write("\t<lastmod>" + dateFormat.format(date) + "</lastmod>\r\n");
                salida.write("\t<changefreq>weekly</changefreq>\r\n");
                salida.write("\t<priority>1.0</priority>\r\n");
                salida.write("</url>\r\n");
                for (int i = 0; i < sitemapGenerado.size(); i++) {
                    salida.write("<url>\r\n");
                    salida.write("\t<loc>https://www.dominio.com/" + 
                            (String) sitemapGenerado.get(i) + "</loc>\r\n");
                    salida.write("\t<lastmod>" + dateFormat.format(date) + "</lastmod>\r\n");
                    salida.write("\t<changefreq>weekly</changefreq>\r\n");
                    salida.write("\t<priority>0.7</priority>\r\n");
                    salida.write("</url>\r\n");
                }
                salida.write("</urlset>\r\n");
                salida.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
