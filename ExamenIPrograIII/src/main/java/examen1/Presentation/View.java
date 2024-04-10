package examen1.Presentation;

import examen1.Logic.activo;
import examen1.Logic.categoria;
import examen1.Main;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer{

    private JComboBox categoria;
    private JTextField valor;
    private JButton consultar;
    private JButton limpiar;
    private JButton guardar;
    private JTextField codigo;
    private JTextField activo;
    private JTextField fabricacion;
    private JTextField edad;
    private JTextField depreciacion;
    private JTextField valorActual;
    private JPanel Panel;
    private JLabel codigoLbl;
    private JLabel activoLbl;
    private JLabel fabricacionLbl;
    private JLabel valorLbl;
    private JLabel categoriaLbl;
    Controller controller;
    Model model;
    public JPanel getPanel(){
        return Panel;
    }
    public void setModel(Model model) {
        this.model = model;
        model.addObserver(this);
    }
    public void setController(Controller controller) {
        this.controller = controller;
    }
    public void setCombos(){
        categoria.setModel(new DefaultComboBoxModel(model.getTypes().toArray(new categoria[0])));
    }
    public View() {
        edad.setText("0");
        depreciacion.setText("0");
        valorActual.setText("0");
        edad.setEnabled(false);
        depreciacion.setEnabled(false);
        valorActual.setEnabled(false);

        consultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(!codigo.getText().isEmpty()) {
                        activo filtro = new activo();
                        filtro.setCodigo(codigo.getText());
                        controller.search(filtro);
                        codigo.setEnabled(false);
                        model.setMode(Main.MODE_EDIT);
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(Panel,ex.getMessage(),"Información",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        limpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setCurrent(new activo());
                codigo.setEnabled(true);
            }
        });

        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isValid()){
                    if(model.getMode() == Main.MODE_CREATE){
                        controller.create(take());
                        JOptionPane.showMessageDialog(Panel, "ACTIVO AGREGADO", "", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        controller.update(take());
                        JOptionPane.showMessageDialog(Panel, "ACTIVO ACTUALIZADO", "", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(Panel,"Favor llenar todos los campos.","Información",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
    @Override
    public void update(Observable updatedModel, Object properties) {
        int changedProps = (int) properties;
        if ((changedProps & Model.CURRENT) == Model.CURRENT) {
            codigo.setText(model.getCurrent().getCodigo());
            activo.setText(model.getCurrent().getActivo());
            fabricacion.setText(String.valueOf(model.getCurrent().getFabricacion()));
            valor.setText(String.valueOf(model.getCurrent().getValor()));
            categoria.setSelectedItem(model.getCurrent().getCat());
            model.calcularDatos();
            edad.setText(String.valueOf(model.getEdad()));
            depreciacion.setText(String.valueOf(model.getDepreciacion()));
            valorActual.setText(String.valueOf(model.getValorActual()));
        }
        this.Panel.revalidate();
    }
    boolean isValid(){
        boolean valid = true;

        if(codigo.getText().isEmpty()){
            valid = false;
            codigoLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
            codigo.setToolTipText("Código requerido");
        }
        else{
            try{
                if(model.getMode() == Main.MODE_CREATE) {
                    activo filtro = new activo();
                    filtro.setCodigo(codigo.getText());
                    controller.search(filtro);
                    valid = false;
                    codigoLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
                    codigo.setToolTipText("Código ya registrado");
                }
            }catch (Exception e){
                codigoLbl.setBorder(BorderFactory.createMatteBorder(0,0,0,0, Color.RED));
                codigo.setToolTipText(null);
            }
        }
        if(activo.getText().isEmpty()){
            valid = false;
            activoLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
            activo.setToolTipText("Activo requerido");
        }
        else{
            activoLbl.setBorder(BorderFactory.createMatteBorder(0,0,0,0, Color.RED));
            activo.setToolTipText(null);
        }
        try{
            Integer.parseInt(fabricacion.getText());
            fabricacionLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.RED));
            fabricacion.setToolTipText(null);
            if(Integer.parseInt(fabricacion.getText())==0){
                valid = false;
                fabricacionLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
                fabricacion.setToolTipText("Año de fabricación no puede ser 0.");
            }
        }catch(Exception e) {
            valid = false;
            fabricacionLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
            fabricacion.setToolTipText("Número requerido");
        }
        try{
            Integer.parseInt(valor.getText());
            valorLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.RED));
            valor.setToolTipText(null);
            if(Integer.parseInt(valor.getText())==0){
                valid = false;
                valorLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
                valor.setToolTipText("Valor no puede ser 0.");
            }
        }catch(Exception e) {
            valid = false;
            valorLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
            valor.setToolTipText("Número requerido");
        }
        if(categoria.getSelectedItem() == null){
            valid = false;
            categoriaLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.RED));
            categoria.setToolTipText("Categoria requerida");
        }
        else{
            categoriaLbl.setBorder(BorderFactory.createMatteBorder(0,0,0,0, Color.RED));
            categoria.setToolTipText(null);
        }
        return valid;
    }
    public activo take(){
        activo bien = new activo();
        bien.setCodigo(codigo.getText());
        bien.setActivo(activo.getText());
        bien.setFabricacion(Integer.parseInt(fabricacion.getText()));
        bien.setValor(Integer.parseInt(valor.getText()));
        bien.setCat((examen1.Logic.categoria) categoria.getSelectedItem());
        return bien;
    }

}
