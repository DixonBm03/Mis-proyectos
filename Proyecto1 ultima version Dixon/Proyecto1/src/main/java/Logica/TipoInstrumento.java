
package Logica;

import java.util.logging.Logger;

/**
 *
 * @author bustos
 */
public class TipoInstrumento {
    private String nombre;
    private String codigo;
    private String unidad;
    
     public TipoInstrumento() {
         nombre="";
         codigo="";
         unidad="";
    }
    public TipoInstrumento(String nombre, String codigo, String unidad) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.unidad = unidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}
