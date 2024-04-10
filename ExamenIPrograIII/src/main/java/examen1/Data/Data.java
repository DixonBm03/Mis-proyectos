package examen1.Data;

import examen1.Logic.activo;
import examen1.Logic.categoria;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {
    @XmlElementWrapper(name = "categorias")
    @XmlElement(name = "categoria")
    private List<categoria> categorias;

    @XmlElementWrapper(name = "activos")
    @XmlElement(name = "activo")
    private List<activo> activos;
    public Data(){
        categorias = new ArrayList<>();
        activos = new ArrayList<>();
    }

    public List<categoria> getCategorias() {
        return categorias;
    }

    public List<activo> getActivos(){
        return activos;
    }
}
