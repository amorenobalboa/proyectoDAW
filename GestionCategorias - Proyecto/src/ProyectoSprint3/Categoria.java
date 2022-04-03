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
public class Categoria {
    
    int codicat;
    String nombre;
    String titulo;
    String contenido;
    String metadesc;
    int sitemap;
    String actualizado;

    public Categoria(int codicat, String nombre, String titulo, String contenido, String metadesc, int sitemap, String actualizado) {
        this.codicat = codicat;
        this.nombre = nombre;
        this.titulo = titulo;
        this.contenido = contenido;
        this.metadesc = metadesc;
        this.sitemap = sitemap;
        this.actualizado = actualizado;
    }
    
    public Categoria(String nombre, String titulo, String contenido, String metadesc, int sitemap, String actualizado) {
        this.nombre = nombre;
        this.titulo = titulo;
        this.contenido = contenido;
        this.metadesc = metadesc;
        this.sitemap = sitemap;
        this.actualizado = actualizado;
    }
    
}
