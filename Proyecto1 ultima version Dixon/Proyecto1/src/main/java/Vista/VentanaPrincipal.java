package Vista;

import Logica.Calibraciones;
import Logica.Instrumentos;
import Logica.TipoInstrumento;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class VentanaPrincipal extends javax.swing.JFrame {

    LinkedList<TipoInstrumento> Tipoinstrumento = new LinkedList<>();
    DefaultTableModel modeloTipoIns;
    LinkedList<Instrumentos> instrumento = new LinkedList<>();
    DefaultTableModel modeloInstrumento;
    LinkedList<Calibraciones> calibracion =new LinkedList<>();
     DefaultTableModel modeloCalibracion;

    private TableRowSorter trsfiltro;
       private TableRowSorter Insfiltro;
    int filas;

    public VentanaPrincipal() {
        initComponents();
       
        //Tabla Tipos
        modeloTipoIns = new DefaultTableModel();
        modeloTipoIns.addColumn("Codigo");
        modeloTipoIns.addColumn("Nombre");
        modeloTipoIns.addColumn("Unidad");
        this.jTableTipo.setModel(modeloTipoIns);
        cargarDesdeXMLTipoInstrumentos(jTableTipo);
     
    
        
        //Tabla Instrumentos
        modeloInstrumento = new DefaultTableModel();
        modeloInstrumento.addColumn("Serie");
        modeloInstrumento.addColumn("Descripcion");
        modeloInstrumento.addColumn("Minimo");
        modeloInstrumento.addColumn("Maximo");
        modeloInstrumento.addColumn("Tolerancia");
        this.jTableInstrumentos.setModel(modeloInstrumento);
        cargarDesdeXMLInstrumentos(jTableInstrumentos);
              JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filtro=new FileNameExtensionFilter("Archivos XML", "xml");
        fileChooser.setFileFilter(filtro);
       int seleccion=fileChooser.showOpenDialog(this);
        if(seleccion==JFileChooser.APPROVE_OPTION){
            File archivo=fileChooser.getSelectedFile();
            escribirXMLCalibraciones(archivo);
        
        }
        cargarCombo();
      
                
    }

    private void cargarTableInstrumento() {
        String[] Dato = new String[5];
        Dato[0] = JtxtSerie.getText();
        Dato[1] = JtxtDescri.getText();
        Dato[2] = JtxtMinimo.getText();
        Dato[3] = JtxtMaximo.getText();
        Dato[4] = JtxtTolerancia.getText();
        modeloInstrumento.addRow(Dato);

    }
    private void cargarTablecalibraciones() {
        String[] Dato = new String[3];
        Dato[0] = JtxtNumeroCalibraciones.getText();
        Dato[1] = JtxtMediacionesCali1.getText();
        Dato[2] = JtxtFechaCalibracion.getText();
     ;
        modeloCalibracion.addRow(Dato);

    }
//Cargar Table TipoInstrumentos

    private void cargarTableTipos() {

        String[] Datos = new String[3];
        Datos[0] = jtxtcodigo.getText();
        Datos[1] = jtxtnombre.getText();
        Datos[2] = jtxtunidad.getText();
        modeloTipoIns.addRow(Datos);

    }

    private void ActualizarDatoTipoI() {
        String codigo = jtxtcodigo.getText();
        String nombre = jtxtnombre.getText();
        String unidad = jtxtunidad.getText();

        int filaExistente = -1;

        // Verificar si el dato ya existe en la tabla
        for (int i = 0; i < modeloTipoIns.getRowCount(); i++) {
            String codN = modeloTipoIns.getValueAt(i, 0).toString();
            String nomN = modeloTipoIns.getValueAt(i, 1).toString();
            String uniN = modeloTipoIns.getValueAt(i, 2).toString();

            if (codN.equals(codigo)) {
                filaExistente = i;

                if (nomN.equals(nombre)) {
                    filaExistente = i;

                    if (uniN.equals(unidad)) {
                        filaExistente = i;
                        break;
                    }
                }

                // Si el dato existe, elimínalo
                if (filaExistente != -1) {
                    modeloTipoIns.removeRow(filaExistente);
                    BorrarDelXMLTipoInstrumento();
                }

            }

        }

    }

    private void ActualizarInstrumento() {
        String serie = JtxtSerie.getText();
        String descripcion = JtxtDescri.getText();
        String minimo = JtxtMinimo.getText();
        String maximo = JtxtMaximo.getText();
        String tolerancia = JtxtTolerancia.getText();

        int filaExistente = -1;
        // Verificar si el dato ya existe en la tabla
        for (int i = 0; i < modeloInstrumento.getRowCount(); i++) {
            
            String serieN = modeloInstrumento.getValueAt(i, 0).toString();
            String desN = modeloInstrumento.getValueAt(i, 1).toString();
            String minN = modeloInstrumento.getValueAt(i, 2).toString();
            String maxN = modeloInstrumento.getValueAt(i, 3).toString();
            String tolN = modeloInstrumento.getValueAt(i, 4).toString();
    
            if (serieN.equals(serie)) {
                filaExistente = i;

                if (desN.equals(descripcion)) {
                    filaExistente = i;

                    if (minN.equals(minimo)) {
                        filaExistente = i;
                        if (maxN.equals(maximo)) {
                            filaExistente = i;

                            if (tolN.equals(tolerancia)) {
                                filaExistente = i;

                                break;
                            }
                        }
                    }
                }

                    // Si el dato existe, elimínalo
                    if (filaExistente != -1) {
                        modeloInstrumento.removeRow(filaExistente);
                        BorrarDelXMLInstrumento();
                    }

                }

            }

        }

    //Tipo Instrumentos--------------------------------------------------------------------------------
//Guardar Como XML Table TipoInstrumentos
 public void cargarDesdeXMLTipoCalibraciones(JTable table) {
    try {
       //Ubica el Archivo
        String xmlFilePath = "Calibraciones.xml";

        // Configura la fábrica de documentos y el constructor de documentos XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        // Parsea el archivo XML
        Document document = builder.parse(new File(xmlFilePath));
        // Obtiene la raíz del documento XML
        Element root = document.getDocumentElement();
        // Obtiene la lista de elementos "row" que representan las filas de datos
        NodeList rowElements = root.getElementsByTagName("row");
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        // Limpia la tabla
        model.setRowCount(0);
        //agrega los datos a la tabla
        for (int i = 0; i < rowElements.getLength(); i++) {
            Element rowElement = (Element) rowElements.item(i);
            NodeList cellElements = rowElement.getChildNodes();
            // Almacena los valores en las celdas de la tabla
            Object[] rowData = new Object[cellElements.getLength()];
            // Itera sobre las celdas de la fila y agrega los valores al array
            for (int j = 0; j < cellElements.getLength(); j++) {
                Element cellElement = (Element) cellElements.item(j);
                String columnName = cellElement.getNodeName();
                String cellValue = cellElement.getTextContent();
                rowData[j] = cellValue;
            }
            // Agrega la fila al modelo de tabla
            model.addRow(rowData);
        }
        
    } catch (Exception e) {
        
    }
}
  public void InsertarDatosEnXMLCalibraciones(JTable table) {
    try {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        // Crear Raíz
        Element rootElement = document.createElement("Calibraciones");
        document.appendChild(rootElement);

        // Iterar sobre las filas del JTable
        for (int row = 0; row < table.getRowCount(); row++) {
            Element rowData = document.createElement("row");
            rootElement.appendChild(rowData);

            // Iterar sobre las columnas del JTable
            for (int column = 0; column < table.getColumnCount(); column++) {
                String columnName = table.getColumnName(column);
                Object cellValue = table.getValueAt(row, column);

                Element cell = document.createElement(columnName);
                cell.appendChild(document.createTextNode(cellValue.toString()));
                rowData.appendChild(cell);
            }
        }

        // Especificar la ubicación y el nombre del archivo XML
        File xmlFile = new File("Calibraciones.xml");

        // Guardar el documento XML en un archivo
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new FileOutputStream(xmlFile));
        transformer.transform(source, result);
        System.out.print("Se guardaron las calibraciones en XML");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    private void InsertarXMLTipoInstrumentos(JTable table) {
        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            //Crear Raiz
            Element rootElement = document.createElement("TipoInstrumentos");
            document.appendChild(rootElement);

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            // Iterar sobre las filas del JTable
            for (int row = 0; row < model.getRowCount(); row++) {
                Element rowData = document.createElement("row");
                rootElement.appendChild(rowData);

                // Iterar sobre las columnas del JTable
                for (int column = 0; column < model.getColumnCount(); column++) {
                    String columnName = model.getColumnName(column);
                    Object cellValue = model.getValueAt(row, column);

                    Element cell = document.createElement(columnName);
                    cell.appendChild(document.createTextNode(cellValue.toString()));
                    rowData.appendChild(cell);
                }
            }

            // Especifica la ubicación y el nombre del archivo XML
            File xmlFile = new File("TipoInstrumentos.xml");

            // Guarda el documento XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(xmlFile));
            transformer.transform(source, result);

        } catch (Exception e) {

        }
    }
    //Insertar en Instrumentos

    private void InsertarXMLInstrumentos(JTable table) {
        try {
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            //Crear Raiz
            Element rootElement = document.createElement("Instrumentos");
            document.appendChild(rootElement);

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            // Iterar sobre las filas del JTable
            for (int row = 0; row < model.getRowCount(); row++) {
                Element rowData = document.createElement("row");
                rootElement.appendChild(rowData);

                // Iterar sobre las columnas del JTable
                for (int column = 0; column < model.getColumnCount(); column++) {
                    String columnName = model.getColumnName(column);
                    Object cellValue = model.getValueAt(row, column);

                    Element cell = document.createElement(columnName);
                    cell.appendChild(document.createTextNode(cellValue.toString()));
                    rowData.appendChild(cell);
                }
            }

            // Especifica la ubicación y el nombre del archivo XML
            File xmlFile = new File("Instrumentos.xml");
            // Guarda el documento XML en un archivo
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(xmlFile));
            transformer.transform(source, result);

        } catch (Exception e) {

        }
    }

//Carga desde el XML a la tabla TipoInstrumentos
    public void cargarDesdeXMLTipoInstrumentos(JTable table) {
        try {
            //Ubica el Archivo
            String xmlFilePath = "TipoInstrumentos.xml";

            // Configura la fábrica de documentos y el constructor de documentos XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Parsea el archivo XML
            Document document = builder.parse(new File(xmlFilePath));
            // Obtiene la raíz del documento XML
            Element root = document.getDocumentElement();
            // Obtiene la lista de elementos "row" que representan las filas de datos
            NodeList rowElements = root.getElementsByTagName("row");
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            // Limpia la tabla
            model.setRowCount(0);
            //agrega los datos a la tabla
            for (int i = 0; i < rowElements.getLength(); i++) {
                Element rowElement = (Element) rowElements.item(i);
                NodeList cellElements = rowElement.getChildNodes();
                // Almacena los valores en las celdas de la tabla
                Object[] rowData = new Object[cellElements.getLength()];
                // Itera sobre las celdas de la fila y agrega los valores al array
                for (int j = 0; j < cellElements.getLength(); j++) {
                    Element cellElement = (Element) cellElements.item(j);
                    String columnName = cellElement.getNodeName();
                    String cellValue = cellElement.getTextContent();
                    rowData[j] = cellValue;
                }
                // Agrega la fila al modeloTipoIns de tabla
                model.addRow(rowData);
            }

        } catch (Exception e) {

        }
    }
//Carga desde el XML a la tabla Instrumentos

    public void cargarDesdeXMLInstrumentos(JTable table) {
        try {
            //Ubica el Archivo
            String xmlFilePath = "Instrumentos.xml";

            // Configura la fábrica de documentos y el constructor de documentos XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Parsea el archivo XML
            Document document = builder.parse(new File(xmlFilePath));
            // Obtiene la raíz del documento XML
            Element root = document.getDocumentElement();
            // Obtiene la lista de elementos "row" que representan las filas de datos
            NodeList rowElements = root.getElementsByTagName("row");
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            // Limpia la tabla
            model.setRowCount(0);
            //agrega los datos a la tabla
            for (int i = 0; i < rowElements.getLength(); i++) {
                Element rowElement = (Element) rowElements.item(i);
                NodeList cellElements = rowElement.getChildNodes();
                // Almacena los valores en las celdas de la tabla
                Object[] rowData = new Object[cellElements.getLength()];
                // Itera sobre las celdas de la fila y agrega los valores al array
                for (int j = 0; j < cellElements.getLength(); j++) {
                    Element cellElement = (Element) cellElements.item(j);
                    String columnName = cellElement.getNodeName();
                    String cellValue = cellElement.getTextContent();
                    rowData[j] = cellValue;
                }
                // Agrega la fila al modeloTipoIns de tabla
                model.addRow(rowData);
            }

        } catch (Exception e) {

        }
    }

    private void limpiarT() {
        jtxtcodigo.setText("");
        jtxtnombre.setText("");
        jtxtunidad.setText("");
        btnBorrarTipo.setEnabled(false);
    }

    private void limpiarI() {
        JtxtSerie.setText("");
        JtxtDescri.setText("");
        JtxtMinimo.setText("");
        JtxtMaximo.setText("");
        JtxtTolerancia.setText("");
        borrI.setEnabled(false);
    }

    private void borrarFilaInstrumentos() {
        
    }

    private void borrarFilaTipos() {
        int filaSeleccionada = jTableTipo.getSelectedRow();
        if (filaSeleccionada >= 0) {
            modeloTipoIns.removeRow(filaSeleccionada);
        } else {
            JOptionPane.showMessageDialog(null, "Tabla vacia o seleccione un Instrumento");
        }
    }

    private void BorrarDelXMLTipoInstrumento() {
        try {
            String xmlFilePath = "TipoInstrumentos.xml";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));

            // Obtendo la raiz del xml
            Element root = document.getDocumentElement();
            // Obtiene los elementos "row" (osea de la tabla)
            NodeList rowElements = root.getElementsByTagName("row");
            int rowIndexToDelete = 0;
            if (rowIndexToDelete >= 0 && rowIndexToDelete < rowElements.getLength()) {
                // Obtén el elemento que se quiere borrar
                Node rowToDelete = rowElements.item(rowIndexToDelete);
                // Borra el elemento de la tabla 
                root.removeChild(rowToDelete);
                // Guarda el documento actualizado
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new FileOutputStream(xmlFilePath));
                transformer.transform(source, result);

            }
        } catch (Exception e) {

        }
    }
private void BorrarDelXMLInstrumento() {
        try {
            String xmlFilePath = "Instrumentos.xml";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFilePath));

            // Obtendo la raiz del xml
            Element root = document.getDocumentElement();
            // Obtiene los elementos "row" (osea de la tabla)
            NodeList rowElements = root.getElementsByTagName("row");
            int rowIndexToDelete = 0;
            if (rowIndexToDelete >= 0 && rowIndexToDelete < rowElements.getLength()) {
                // Obtén el elemento que se quiere borrar
                Node rowToDelete = rowElements.item(rowIndexToDelete);
                // Borra el elemento de la tabla 
                root.removeChild(rowToDelete);
                // Guarda el documento actualizado
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new FileOutputStream(xmlFilePath));
                transformer.transform(source, result);

            }
        } catch (Exception e) {

        }
    }


    private void buscarDatos() {

        String descripcionBusqueda = jtxtcodBusqueda.getText().trim();

        // Limpia la tabla 
        modeloTipoIns.setRowCount(0);

        // Busca los datos
        for (int row = 0; row < modeloTipoIns.getRowCount(); row++) {
            String descripcion = modeloTipoIns.getValueAt(row, 1).toString();
            if (descripcion.equalsIgnoreCase(descripcionBusqueda)) {
                String[] fila = new String[]{modeloTipoIns.getValueAt(row, 0).toString(), descripcion};
                modeloTipoIns.addRow(fila);
            }
        }

    }

    private void cargarCombo() {

        // Recorrer las filas de la tabla 
        //agregar los datos al JComboBox
        for (int i = 0; i < modeloTipoIns.getRowCount(); i++) {
            String nombre = (String) modeloTipoIns.getValueAt(i, 1);
            jcboTipo.addItem(nombre);
        }
    }
private void buscarTipoCodigo(){
      String busca = jtxtcodBusqueda.getText();
        
        // Crear un TableRowSorter para filtrar las filas de la tabla
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTipoIns);
        jTableTipo.setRowSorter(sorter);
        
        // Crear un filtro para mostrar solo las filas que contienen el dato buscado en la columna "Codigo"
        RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(busca, 0);
        sorter.setRowFilter(filter);
  
  
}
 
 
private void buscarTipoNombre(){
  String buscado = jtxtnombreBusqueda.getText();
        
        // Crear un TableRowSorter para filtrar las filas de la tabla
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTipoIns);
        jTableTipo.setRowSorter(sorter);
        
        // Crear un filtro para mostrar solo las filas que contienen el dato buscado en la columna "Codigo"
        RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(buscado, 1);
        sorter.setRowFilter(filter);
  
  
}

private void escogerInstrumentoBusqueda(){
       String buscaDesc = jtxtdescripcionBusqueda1.getText();
       String buscarSerie = jtxtSerieBusqueda.getText();
    if(buscarSerie.isEmpty()){
        // Crear un TableRowSorter para filtrar las filas de la tabla
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloInstrumento);
        jTableInstrumentos.setRowSorter(sorter);
        // Crear un filtro para mostrar solo las filas que contienen el dato buscado en la columna "Codigo"
        RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(buscaDesc, 1);
        sorter.setRowFilter(filter);
    }else{
        //
        // Crear un TableRowSorter para filtrar las filas de la tabla
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloInstrumento);
        jTableInstrumentos.setRowSorter(sorter);
        // Crear un filtro para mostrar solo las filas que contienen el dato buscado en la columna "Codigo"
        RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(buscarSerie, 0);
        sorter.setRowFilter(filter);
    }
}
private void escogerTipoInstrumentoBusqueda(){
       String buscaN = jtxtnombreBusqueda.getText();
       String buscarC = jtxtcodBusqueda.getText();
    if(buscarC.isEmpty()){
        // Crear un TableRowSorter para filtrar las filas de la tabla
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTipoIns);
        jTableTipo.setRowSorter(sorter);
        // Crear un filtro para mostrar solo las filas que contienen el dato buscado en la columna "Codigo"
        RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(buscaN, 1);
        sorter.setRowFilter(filter);
    }else{
        // Crear un TableRowSorter para filtrar las filas de la tabla
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modeloTipoIns);
        jTableTipo.setRowSorter(sorter);
        // Crear un filtro para mostrar solo las filas que contienen el dato buscado en la columna "Codigo"
        RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter(buscarC, 0);
        sorter.setRowFilter(filter);
    }
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPnTipoInstrumento = new javax.swing.JPanel();
        btnGuardarTipoInstrumento = new javax.swing.JButton();
        limpT = new javax.swing.JButton();
        btnBorrarTipo = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtxtunidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jtxtnombre = new javax.swing.JTextField();
        label1 = new java.awt.Label();
        jtxtcodigo = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jtxtcodBusqueda = new javax.swing.JTextField();
        jlabel19 = new javax.swing.JLabel();
        busT = new javax.swing.JButton();
        repT = new javax.swing.JButton();
        jtxtnombreBusqueda = new javax.swing.JTextField();
        jlabel20 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableTipo = new javax.swing.JTable();
        jPnInstrumentos = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel5 = new javax.swing.JPanel();
        btnGuardarInstrumentos = new javax.swing.JButton();
        limpI = new javax.swing.JButton();
        borrI = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        JtxtSerie = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        JtxtDescri = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        JtxtMinimo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        JtxtMaximo = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        JtxtTolerancia = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jcboTipo = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        busT1 = new javax.swing.JButton();
        repT1 = new javax.swing.JButton();
        jtxtSerieBusqueda = new javax.swing.JTextField();
        jlabel21 = new javax.swing.JLabel();
        jlabel22 = new javax.swing.JLabel();
        jtxtdescripcionBusqueda1 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableInstrumentos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jSeparator3 = new javax.swing.JSeparator();
        guarC = new javax.swing.JButton();
        limC = new javax.swing.JButton();
        borrC = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jlblInstrumentoCalibrar = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        JtxtNumeroCalibraciones = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        JtxtFechaCalibracion = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        JtxtMediacionesCali1 = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        JtxtDescripcion = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        busT2 = new javax.swing.JButton();
        repT2 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableCalibraciones = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SILAB: Sistema de Laboratorio Industrial");

        btnGuardarTipoInstrumento.setText("Guardar");
        btnGuardarTipoInstrumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarTipoInstrumentoActionPerformed(evt);
            }
        });

        limpT.setText("Limpiar");
        limpT.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        limpT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpTActionPerformed(evt);
            }
        });

        btnBorrarTipo.setText("Borrar");
        btnBorrarTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarTipoActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tipo Instrumento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel6.setToolTipText("Tipo Instrumento");
        jPanel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Codigo");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Nombre");

        label1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        label1.setName(""); // NOI18N
        label1.setText("Unidad");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtxtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(54, 54, 54)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtunidad, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtunidad, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jtxtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtxtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12)))); // NOI18N

        jtxtcodBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtcodBusquedaActionPerformed(evt);
            }
        });
        jtxtcodBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtcodBusquedaKeyTyped(evt);
            }
        });

        jlabel19.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jlabel19.setText("Codigo");

        busT.setText("Buscar");
        busT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                busTMouseClicked(evt);
            }
        });
        busT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busTActionPerformed(evt);
            }
        });

        repT.setText("Reporte");
        repT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repTActionPerformed(evt);
            }
        });

        jtxtnombreBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtnombreBusquedaKeyTyped(evt);
            }
        });

        jlabel20.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jlabel20.setText("Nombre");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jlabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtcodBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(jlabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtxtnombreBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(repT, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(busT, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabel19)
                    .addComponent(jtxtcodBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(busT)
                    .addComponent(repT))
                .addGap(49, 49, 49))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabel20)
                    .addComponent(jtxtnombreBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado"));
        jPanel8.setToolTipText("");
        jPanel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jTableTipo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTableTipo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Unidad"
            }
        ));
        jTableTipo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableTipoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableTipo);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPnTipoInstrumentoLayout = new javax.swing.GroupLayout(jPnTipoInstrumento);
        jPnTipoInstrumento.setLayout(jPnTipoInstrumentoLayout);
        jPnTipoInstrumentoLayout.setHorizontalGroup(
            jPnTipoInstrumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnTipoInstrumentoLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPnTipoInstrumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPnTipoInstrumentoLayout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPnTipoInstrumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardarTipoInstrumento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBorrarTipo, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(limpT, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        jPnTipoInstrumentoLayout.setVerticalGroup(
            jPnTipoInstrumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnTipoInstrumentoLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPnTipoInstrumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPnTipoInstrumentoLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPnTipoInstrumentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardarTipoInstrumento)
                            .addComponent(limpT))
                        .addGap(18, 18, 18)
                        .addComponent(btnBorrarTipo))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(91, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tipos de Instrumento", jPnTipoInstrumento);

        btnGuardarInstrumentos.setText("Guardar");
        btnGuardarInstrumentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarInstrumentosActionPerformed(evt);
            }
        });

        limpI.setText("Limpiar");
        limpI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpIActionPerformed(evt);
            }
        });

        borrI.setText("Borrar");
        borrI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrIActionPerformed(evt);
            }
        });

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Instrumentos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel16.setText("Descripcion");

        jLabel9.setText("Minimo");

        jLabel17.setText("Maximo");

        jLabel15.setText("Tolerancia");

        jLabel18.setText("Tipo");

        jcboTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcboTipoItemStateChanged(evt);
            }
        });

        jLabel8.setText("Serie");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(24, 24, 24)
                        .addComponent(JtxtTolerancia, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(JtxtMinimo)
                            .addComponent(JtxtSerie))))
                .addGap(22, 22, 22)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(JtxtDescri)
                    .addComponent(JtxtMaximo)
                    .addComponent(jcboTipo, 0, 178, Short.MAX_VALUE))
                .addGap(135, 135, 135))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(JtxtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(JtxtMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(JtxtMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(JtxtDescri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(JtxtTolerancia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jcboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12)))); // NOI18N

        busT1.setText("Buscar");
        busT1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                busT1ActionPerformed(evt);
            }
        });

        repT1.setText("Reporte");
        repT1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repT1ActionPerformed(evt);
            }
        });

        jtxtSerieBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtSerieBusquedaActionPerformed(evt);
            }
        });
        jtxtSerieBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtSerieBusquedaKeyTyped(evt);
            }
        });

        jlabel21.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jlabel21.setText("N° Serie");

        jlabel22.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jlabel22.setText("Descripcion");

        jtxtdescripcionBusqueda1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtdescripcionBusqueda1KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlabel21)
                .addGap(30, 30, 30)
                .addComponent(jtxtSerieBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jtxtdescripcionBusqueda1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                .addComponent(repT1)
                .addGap(18, 18, 18)
                .addComponent(busT1)
                .addGap(44, 44, 44))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlabel21)
                        .addComponent(jtxtSerieBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlabel22)
                        .addComponent(jtxtdescripcionBusqueda1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(busT1)
                        .addComponent(repT1)))
                .addGap(49, 49, 49))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado"));
        jPanel11.setToolTipText("");
        jPanel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jTableInstrumentos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTableInstrumentos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°Serie", "Descripcion", "Minimo", "Maximo", "Tolerancia"
            }
        ));
        jTableInstrumentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableInstrumentosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTableInstrumentosMouseEntered(evt);
            }
        });
        jScrollPane2.setViewportView(jTableInstrumentos);
        if (jTableInstrumentos.getColumnModel().getColumnCount() > 0) {
            jTableInstrumentos.getColumnModel().getColumn(1).setMinWidth(500);
        }

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 878, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(btnGuardarInstrumentos)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(limpI))
                                    .addComponent(borrI)))
                            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 14, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(limpI, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGuardarInstrumentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(borrI))
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel13))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPnInstrumentosLayout = new javax.swing.GroupLayout(jPnInstrumentos);
        jPnInstrumentos.setLayout(jPnInstrumentosLayout);
        jPnInstrumentosLayout.setHorizontalGroup(
            jPnInstrumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnInstrumentosLayout.createSequentialGroup()
                .addGap(989, 989, 989)
                .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
            .addGroup(jPnInstrumentosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPnInstrumentosLayout.setVerticalGroup(
            jPnInstrumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPnInstrumentosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(231, 231, 231)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Instrumentos", jPnInstrumentos);

        guarC.setText("Guardar");
        guarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guarCActionPerformed(evt);
            }
        });

        limC.setText("Limpiar");

        borrC.setText("Borrar");

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Instrumento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jlblInstrumentoCalibrar.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jlblInstrumentoCalibrar, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jlblInstrumentoCalibrar)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Calibracion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jLabel42.setText("Numero");

        JtxtNumeroCalibraciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JtxtNumeroCalibracionesActionPerformed(evt);
            }
        });

        jLabel43.setText("Mediaciones");

        jLabel47.setText("Fecha");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(jLabel42))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(JtxtNumeroCalibraciones, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel47)
                .addGap(18, 18, 18)
                .addComponent(JtxtFechaCalibracion, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel13Layout.createSequentialGroup()
                    .addGap(105, 105, 105)
                    .addComponent(JtxtMediacionesCali1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(265, Short.MAX_VALUE)))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42)
                    .addComponent(JtxtNumeroCalibraciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47)
                    .addComponent(JtxtFechaCalibracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel43)
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                    .addContainerGap(54, Short.MAX_VALUE)
                    .addComponent(JtxtMediacionesCali1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(23, 23, 23)))
        );

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "Busqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12)))); // NOI18N

        jLabel21.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel21.setText("Descripcion");

        busT2.setText("Buscar");

        repT2.setText("Reporte");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JtxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 274, Short.MAX_VALUE)
                .addComponent(repT2)
                .addGap(56, 56, 56)
                .addComponent(busT2)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(JtxtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(busT2)
                    .addComponent(repT2))
                .addGap(49, 49, 49))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Listado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel15.setToolTipText("");
        jPanel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        jTableCalibraciones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTableCalibraciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero", "Fecha", "Mediciones"
            }
        ));
        jScrollPane3.setViewportView(jTableCalibraciones);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(guarC)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(limC))
                            .addComponent(borrC)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(237, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(guarC)
                        .addComponent(limC))
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(borrC, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Calibraciones", jPanel3);

        jLabel48.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel48.setText("Created by Dixon Bustos Medina 604780542");

        jLabel49.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel49.setText("National University, School of Informatics 2023");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(583, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(379, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Acerca de..", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1037, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleDescription("qwwaesrdtf");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarTipoInstrumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarTipoInstrumentoActionPerformed
        cargarTableTipos();
        ActualizarDatoTipoI();
        InsertarXMLTipoInstrumentos(jTableTipo);
        cargarDesdeXMLTipoInstrumentos(jTableTipo);
        //cargarTable();
    }//GEN-LAST:event_btnGuardarTipoInstrumentoActionPerformed

    private void limpTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpTActionPerformed
        limpiarT();
        btnBorrarTipo.setEnabled(false);
        jtxtcodigo.setEnabled(true);
    }//GEN-LAST:event_limpTActionPerformed

    private void btnBorrarTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarTipoActionPerformed
        borrarFilaTipos();
        BorrarDelXMLTipoInstrumento();
        cargarDesdeXMLTipoInstrumentos(jTableTipo);
        cargarCombo();
    }//GEN-LAST:event_btnBorrarTipoActionPerformed

    private void busTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_busTActionPerformed
        
//buscarTipoCodigo();
//buscarTipoNombre();
        escogerTipoInstrumentoBusqueda();
    }//GEN-LAST:event_busTActionPerformed

    private void jtxtcodBusquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtcodBusquedaKeyTyped
        trsfiltro = new TableRowSorter(jTableTipo.getModel());
        jTableTipo.setRowSorter(trsfiltro);
    }//GEN-LAST:event_jtxtcodBusquedaKeyTyped

    private void limpIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpIActionPerformed
        limpiarI();
        borrI.setEnabled(false);
        JtxtNumeroCalibraciones.requestFocus();
        JtxtNumeroCalibraciones.setEnabled(true);
        JtxtNumeroCalibraciones.setEditable(true);
        
    }//GEN-LAST:event_limpIActionPerformed

    private void btnGuardarInstrumentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarInstrumentosActionPerformed
       //Cargar tabla
       cargarTableInstrumento();
       ActualizarInstrumento();
       InsertarXMLInstrumentos(jTableInstrumentos);
       cargarDesdeXMLInstrumentos(jTableInstrumentos);
      
    }//GEN-LAST:event_btnGuardarInstrumentosActionPerformed

    private void jTableTipoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableTipoMouseClicked

        int seleccion = jTableTipo.getSelectedRow();
        jtxtcodigo.setText(jTableTipo.getValueAt(seleccion, 0).toString());
        jtxtnombre.setText(jTableTipo.getValueAt(seleccion, 1).toString());
        jtxtunidad.setText(jTableTipo.getValueAt(seleccion, 2).toString());
        filas = seleccion;
        jtxtcodigo.setEnabled(false);
        btnBorrarTipo.setEnabled(true);
    }//GEN-LAST:event_jTableTipoMouseClicked

    private void jTableInstrumentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableInstrumentosMouseClicked
        int seleccion = jTableInstrumentos.getSelectedRow();
        JtxtSerie.setText(jTableInstrumentos.getValueAt(seleccion, 0).toString());
        JtxtDescri.setText(jTableInstrumentos.getValueAt(seleccion, 1).toString());
        JtxtMinimo.setText(jTableInstrumentos.getValueAt(seleccion, 2).toString());
        JtxtMaximo.setText(jTableInstrumentos.getValueAt(seleccion, 3).toString());
        JtxtTolerancia.setText(jTableInstrumentos.getValueAt(seleccion, 4).toString());
        filas = seleccion;
        JtxtSerie.setEnabled(false);
        borrI.setEnabled(true);

        //  _____________________________________________________________________
        int seleccinarFila = jTableInstrumentos.getSelectedRow(); // Obtener la fila seleccionada

        if (seleccinarFila != -1) {
            // Verificar si se ha seleccionado una fila válida
            // Obtener el dato de la columna deseada (por ejemplo, columna 0)
            //Numero Serie
            Object dato = jTableInstrumentos.getValueAt(seleccinarFila, 0);
            //Descripcion
            Object dato1 = jTableInstrumentos.getValueAt(seleccinarFila, 1);
            //Minimo
            Object dato2 = jTableInstrumentos.getValueAt(seleccinarFila, 2);
            //Maximo
            Object dato3 = jTableInstrumentos.getValueAt(seleccinarFila, 3);
            //Unidad

            // Muestra el dato en el label
            jlblInstrumentoCalibrar.setText(dato.toString() + "-" + dato1.toString()
                    + "(" + dato2.toString() + "-" + dato3.toString()
                    + ")");

        }

    }//GEN-LAST:event_jTableInstrumentosMouseClicked

    private void borrIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrIActionPerformed

        borrarFilaInstrumentos();
        BorrarDelXMLInstrumento();
        cargarDesdeXMLInstrumentos(jTableInstrumentos);
        cargarCombo();

    }//GEN-LAST:event_borrIActionPerformed

    private void jTableInstrumentosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableInstrumentosMouseEntered

    }//GEN-LAST:event_jTableInstrumentosMouseEntered

    private void jcboTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcboTipoItemStateChanged

buscarTipoCodigo();
    }//GEN-LAST:event_jcboTipoItemStateChanged

    private void busT1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_busT1ActionPerformed
  //Llama el metodo d escoger 
 escogerInstrumentoBusqueda();
    }//GEN-LAST:event_busT1ActionPerformed

    private void jtxtnombreBusquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtnombreBusquedaKeyTyped
        trsfiltro = new TableRowSorter(jTableTipo.getModel());
        jTableTipo.setRowSorter(trsfiltro);
    }//GEN-LAST:event_jtxtnombreBusquedaKeyTyped

    private void busTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_busTMouseClicked
        //buscarTipoCodigo();
    }//GEN-LAST:event_busTMouseClicked

    private void jtxtSerieBusquedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtSerieBusquedaKeyTyped
        Insfiltro = new TableRowSorter(jTableInstrumentos.getModel());
        jTableInstrumentos.setRowSorter(Insfiltro);
    }//GEN-LAST:event_jtxtSerieBusquedaKeyTyped

    private void jtxtdescripcionBusqueda1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtdescripcionBusqueda1KeyTyped
   Insfiltro = new TableRowSorter(jTableInstrumentos.getModel());
        jTableInstrumentos.setRowSorter(Insfiltro);
    }//GEN-LAST:event_jtxtdescripcionBusqueda1KeyTyped

    private void jtxtSerieBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtSerieBusquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtSerieBusquedaActionPerformed

    private void jtxtcodBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtcodBusquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtcodBusquedaActionPerformed

    private void repTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repTActionPerformed
       /*Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("TipoInstrumentos.pdf"));
            document.open();

            PdfPTable table = new PdfPTable(3); // 3 columnas en la tabla

            PdfPCell cell1 = new PdfPCell(new Paragraph("CODIGO"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("NOMBRE"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("UNIDAD"));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);

            for (TipoInstrumento tipoInstrumento : Service.instance().getTipos()) {
                PdfPCell celda1 = new PdfPCell(new Paragraph(tipoInstrumento.getCodigo()));
                PdfPCell celda2 = new PdfPCell(new Paragraph(tipoInstrumento.getNombre()));
                PdfPCell celda3 = new PdfPCell(new Paragraph(tipoInstrumento.getUnidad()));

                table.addCell(celda1);
                table.addCell(celda2);
                table.addCell(celda3);
            }
            document.add(table);
            document.close();
        } catch (Exception e) {}*/
    }//GEN-LAST:event_repTActionPerformed

    private void repT1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repT1ActionPerformed
        /*Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, new FileOutputStream("TipoInstrumentos.pdf"));
            document.open();

            PdfPTable table = new PdfPTable(3); // 3 columnas en la tabla

            PdfPCell cell1 = new PdfPCell(new Paragraph("N°SERIE"));
            PdfPCell cell2 = new PdfPCell(new Paragraph("DESCRIPCION"));
            PdfPCell cell3 = new PdfPCell(new Paragraph("MINIMO"));
            PdfPCell cell1 = new PdfPCell(new Paragraph("MAXIMO"));
            PdfPCell cell1 = new PdfPCell(new Paragraph("TOLERANCIA"));

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);

            for (TipoInstrumento tipoInstrumento : Service.instance().getTipos()) {
                PdfPCell celda1 = new PdfPCell(new Paragraph(tipoInstrumento.getCodigo()));
                PdfPCell celda2 = new PdfPCell(new Paragraph(tipoInstrumento.getNombre()));
                PdfPCell celda3 = new PdfPCell(new Paragraph(tipoInstrumento.getUnidad()));

                table.addCell(celda1);
                table.addCell(celda2);
                table.addCell(celda3);
            }
            document.add(table);
            document.close();
        } catch (Exception e) {}*/
    }//GEN-LAST:event_repT1ActionPerformed

    private void JtxtNumeroCalibracionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JtxtNumeroCalibracionesActionPerformed
        
    // TODO add your handling code here:
    }//GEN-LAST:event_JtxtNumeroCalibracionesActionPerformed

    private void guarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guarCActionPerformed
          //Cargar tabla
       cargarTablecalibraciones();
      // ActualizarInstrumento();
       InsertarDatosEnXMLCalibraciones(jTableCalibraciones);
       cargarDesdeXMLTipoCalibraciones(jTableCalibraciones);
        
    }//GEN-LAST:event_guarCActionPerformed
    private void filtro() {
        jtxtcodBusqueda.getText();
        trsfiltro.setRowFilter(RowFilter.regexFilter(jtxtcodBusqueda.getText(), 0));
    }

    private void seleccionarCombo() {

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JtxtDescri;
    private javax.swing.JTextField JtxtDescripcion;
    private javax.swing.JTextField JtxtFechaCalibracion;
    private javax.swing.JTextField JtxtMaximo;
    private javax.swing.JTextField JtxtMediacionesCali1;
    private javax.swing.JTextField JtxtMinimo;
    private javax.swing.JTextField JtxtNumeroCalibraciones;
    private javax.swing.JTextField JtxtSerie;
    private javax.swing.JTextField JtxtTolerancia;
    private javax.swing.JButton borrC;
    private javax.swing.JButton borrI;
    private javax.swing.JButton btnBorrarTipo;
    private javax.swing.JButton btnGuardarInstrumentos;
    private javax.swing.JButton btnGuardarTipoInstrumento;
    private javax.swing.JButton busT;
    private javax.swing.JButton busT1;
    private javax.swing.JButton busT2;
    private javax.swing.JButton guarC;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPnInstrumentos;
    private javax.swing.JPanel jPnTipoInstrumento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableCalibraciones;
    private javax.swing.JTable jTableInstrumentos;
    private javax.swing.JTable jTableTipo;
    private javax.swing.JComboBox<String> jcboTipo;
    private javax.swing.JLabel jlabel19;
    private javax.swing.JLabel jlabel20;
    private javax.swing.JLabel jlabel21;
    private javax.swing.JLabel jlabel22;
    private javax.swing.JLabel jlblInstrumentoCalibrar;
    private javax.swing.JTextField jtxtSerieBusqueda;
    private javax.swing.JTextField jtxtcodBusqueda;
    private javax.swing.JTextField jtxtcodigo;
    private javax.swing.JTextField jtxtdescripcionBusqueda1;
    private javax.swing.JTextField jtxtnombre;
    private javax.swing.JTextField jtxtnombreBusqueda;
    private javax.swing.JTextField jtxtunidad;
    private java.awt.Label label1;
    private javax.swing.JButton limC;
    private javax.swing.JButton limpI;
    private javax.swing.JButton limpT;
    private javax.swing.JButton repT;
    private javax.swing.JButton repT1;
    private javax.swing.JButton repT2;
    // End of variables declaration//GEN-END:variables

    private void escribirXMLTipo(File archivo) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(fichero);
            pw.println("<TipoInstrumentos>");
            for (TipoInstrumento ins : Tipoinstrumento) {
                pw.println("<nombre>" + ins.getNombre() + "</nombre>");
                pw.println("<codigo>" + ins.getCodigo() + "</codigo>");
                pw.println("<unidad>" + ins.getUnidad() + "</unidad>");
            }
            pw.println("</Instrumento>");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void escribirXMLInstrumentos(File archivo) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(fichero);
            pw.println("<Instrumento>");
            for (Instrumentos ins : instrumento) {
                pw.println("<Serie>" + ins.getSerie() + "</Serie>");
                pw.println("<Descripcion>" + ins.getDescripcion() + "</Descripcion>");
                pw.println("<Minimo>" + ins.getDescripcion() + "</Minimo>");
                pw.println("<Maximo>" + ins.getDescripcion() + "</Maximo>");
                pw.println("<Tolerancia>" + ins.getDescripcion() + "</Tolerancia>");


            }
            pw.println("</Instrumento>");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
      private void escribirXMLCalibraciones(File archivo) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(archivo);
            pw = new PrintWriter(fichero);
            pw.println("<Calibraciones>");
            for (Calibraciones ins : calibracion) {
                pw.println("<Numero>" + ins.getNumero() + "</Numero>");
                pw.println("<Fecha>" + ins.getFecha() + "</Fecha>");
                pw.println("<Medicion>" + ins.getMedicion() + "</Medicion>");
            }
            pw.println("</calibraciones>");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
