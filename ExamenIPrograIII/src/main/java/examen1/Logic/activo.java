package examen1.Logic;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;

import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class activo {
    @XmlID
    private String codigo;
    private String activo;
    private int fabricacion;
    private int valor;
    @XmlIDREF
    private categoria cat;

    public activo(String codigo, String activo, int fabricacion, int valor, categoria cat) {
        this.codigo = codigo;
        this.activo = activo;
        this.fabricacion = fabricacion;
        this.valor = valor;
        this.cat = cat;
    }
    public activo(){this("","",0,0, new categoria());}

    public String getCodigo() {
        return codigo;
    }

    public activo setCodigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public String getActivo() {
        return activo;
    }

    public activo setActivo(String activo) {
        this.activo = activo;
        return this;
    }

    public int getFabricacion() {
        return fabricacion;
    }

    public activo setFabricacion(int fabricacion) {
        this.fabricacion = fabricacion;
        return this;
    }

    public int getValor() {
        return valor;
    }

    public activo setValor(int valor) {
        this.valor = valor;
        return this;
    }

    public categoria getCat() {
        return cat;
    }

    public activo setCat(categoria cat) {
        this.cat = cat;
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
        final activo other = (activo) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }
}
