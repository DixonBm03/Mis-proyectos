package examen1.Presentation;

import examen1.Logic.Service;
import examen1.Logic.activo;
import examen1.Main;

import javax.swing.table.TableModel;
import java.util.List;

public class Controller {
    View view;
    Model model;
    public Controller(View view,Model model) {
        model.init(Service.instance().getCategorias());
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
        view.setCombos();
    }
    public Model getModel(){ return model; }
    public void setCurrent(activo a){ model.setCurrent(a); model.commit();model.setMode(Main.MODE_CREATE);}
    public void search(activo filter) throws Exception{
        activo buscado = Service.instance().search(filter);
        if (buscado==null){
            throw new Exception("NINGUN REGISTRO COINCIDE");
        }
        model.setCurrent(buscado);
        model.setMode(Main.MODE_EDIT);
        model.commit();
    }
    public void create(activo w){
        try {
            Service.instance().create(w);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.setCurrent(new activo());
        model.commit();
    }

    public void update(activo ex){
        try {
            Service.instance().update(ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        model.setCurrent(ex);
        model.commit();
    }
}
