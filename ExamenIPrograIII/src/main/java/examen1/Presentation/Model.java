package examen1.Presentation;

import examen1.Logic.Service;
import examen1.Logic.activo;
import examen1.Logic.categoria;
import examen1.Main;

import java.util.List;
import java.util.Observer;

public class Model extends java.util.Observable{
    List<categoria> types;
    activo current;

    int edad = 0;
    int depreciacion = 0;
    int valorActual = 0;
    int changedProps = NONE;

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        commit();
    }

    public void commit(){
        setChanged();
        notifyObservers(changedProps);
        changedProps = NONE;
    }

    public Model() {
    }

    public void init(List<categoria> list){
        mode = Main.MODE_CREATE;
        setCurrent(new activo());
        setTypes(list);
    }

    public List<categoria> getTypes() {
        return types;
    }
    public void setTypes(List<categoria> list){
        this.types = list;
    }

    public activo getCurrent() {
        return current;
    }
    public void setCurrent(activo current) {
        changedProps +=CURRENT;
        this.current = current;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public static int NONE=0;
    public static int CURRENT=1;
    public int mode;

    public int getMode() {
        return mode;
    }

    public void calcularDatos(){
        edad = 2023 - current.getFabricacion();
        try {
            float division = (float) edad /current.getCat().getVida();
            depreciacion = (int) (division*current.getValor());
        }catch (Exception e){}
        valorActual = current.getValor() - depreciacion;
    }

    public int getEdad() {
        return edad;
    }

    public int getDepreciacion() {
        return depreciacion;
    }

    public int getValorActual() {
        return valorActual;
    }
}
