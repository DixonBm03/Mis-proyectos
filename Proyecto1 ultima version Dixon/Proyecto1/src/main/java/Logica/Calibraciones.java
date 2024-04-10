
package Logica;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Calibraciones {
    private String fecha;
    private int numero;
    private int medicion;
    private List<int[]> mediciones;
      private int cantMedicion;
    private Instrumentos instrumento;
    public Calibraciones() {
    this.numero = 0;
    this.fecha = "";
    this.medicion = 0;
}
    public Calibraciones(int numero,String fecha,int medicion,Instrumentos instrumento) {
        this.numero=numero;
        this.fecha=fecha;
        this.medicion=medicion;
          this.mediciones = new ArrayList<>();
            this.cantMedicion = 0;
       this.instrumento=instrumento;
    }
   
   

    // Getters y setters
    public String getFecha() {
        return fecha;
    }
public int getCanMedicion(){
    return cantMedicion;
}
    public void setFecha(String fecha) {
        
        this.fecha = fecha;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
      
        this.numero = numero;
    }
    public void setMedicion(int medicion) {
        this.medicion = medicion;
    }
public int getMedicion() {
        return medicion;
    }
public void agregarMedicion(int valorUsuario,Instrumentos ins) throws IllegalArgumentException {
        if (valorUsuario < ins.getMinimo()-ins.getTolerancia() || valorUsuario > ins.getMaximo()+ins.getTolerancia()) {
            throw new IllegalArgumentException("El valor de la medición está fuera del rango permitido.");
        }

        int[] medicion = {cantMedicion + 1,ins.getMinimo() + (int) (Math.random() * (ins.getMaximo()- ins.getMinimo() + 1)), valorUsuario};
        mediciones.add(medicion);
        cantMedicion++;
    }
   
    public void ModificaMedicion(int num, int nuevoValor) {
        if (num >= 1 && num <= cantMedicion) {
            int posicion = num - 1;
            int[] medicion = mediciones.get(posicion);
            medicion[2] = nuevoValor;
        } else {
            throw new IllegalArgumentException("Número de medición fuera de rango.");
        }
    }
    public List<int[]> getMediciones() {
        return mediciones;
    }

    public void setMediciones(List<int[]> mediciones) {
        this.mediciones = mediciones;
    }
    public Instrumentos getInstrumento(){
        return instrumento;
    }
    public void setInstrumento(Instrumentos instrumento){
        this.instrumento=instrumento;
    }
    
}