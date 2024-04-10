package examen1.Logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class categoria {
    @XmlID
    private String codigo;
    private String nombre;
    private int vida;

    public categoria(String codigo, String nombre, int vida) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.vida = vida;
    }
    public categoria(){this("","",0);}

    public String getCodigo() {
        return codigo;
    }

    public categoria setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public categoria setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public int getVida() {
        return vida;
    }

    public categoria setVida(int vida) {
        this.vida = vida;
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final categoria other = (categoria) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    public String toString() { return (this.nombre + " (" + vida +") años");
    }
}
