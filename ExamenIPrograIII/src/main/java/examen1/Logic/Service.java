package examen1.Logic;

import examen1.Data.XmlPersister;
import examen1.Data.Data;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private static Service theInstance;

    public static Service instance(){
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }
    private Data data;

    public List<categoria> getCategorias(){
        return data.getCategorias();
    }
    public List<activo> getActivos(){
        return data.getActivos();
    }

    private Service(){
        try{
            data = XmlPersister.instance().load();
        }catch(Exception e){ data = new Data(); }
    }
    public void stop() {
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void create(activo e) throws Exception{
         activo result = data.getActivos().stream()
                .filter(i->i.getCodigo().equals(e.getCodigo())).findFirst().orElse(null);
        if (result==null) data.getActivos().add(e);
        else throw new Exception("ACTIVO YA EXISTE");
    }

    public void update(activo e) throws Exception{
        activo result;
        try{
            result = this.search(e);
            data.getActivos().remove(result);
            data.getActivos().add(e);
        }catch (Exception ex) {
            throw new Exception("ACTIVO NO EXISTE");
        }
    }

    public activo search(activo e){
        for(activo f : data.getActivos()){
            if(f.getCodigo().equals(e.getCodigo())){
                return f;
            }
        }
        return null;
    }
}

