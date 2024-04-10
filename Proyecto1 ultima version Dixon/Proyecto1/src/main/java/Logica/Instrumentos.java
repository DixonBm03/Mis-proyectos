/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Logica;

/**
 *
 * @author busto
 */
public class Instrumentos {
    private String serie;
    private String tipo;
    private String descripcion;
    private int minimo;
    private int maximo;
    private int tolerancia;

    public Instrumentos(String serie, String tipo, String descripcion, int minimo, int maximo, int tolerancia) {
        this.serie = serie;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.minimo = minimo;
        this.maximo = maximo;
        this.tolerancia = tolerancia;
    }

    public String getSerie() {
        return serie;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getMinimo() {
        return minimo;
    }

    public int getMaximo() {
        return maximo;
    }

    public int getTolerancia() {
        return tolerancia;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMinimo(int minimo) {
        this.minimo = minimo;
    }

    public void setMaximo(int maximo) {
        this.maximo = maximo;
    }

    public void setTolerancia(int tolerancia) {
        this.tolerancia = tolerancia;
    }
    
}
