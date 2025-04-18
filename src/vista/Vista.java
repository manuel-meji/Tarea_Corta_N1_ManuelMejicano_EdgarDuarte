package vista;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import controlador.Logica;
import modelo.Colaborador;
import modelo.Ingresos;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import java.time.LocalDate;
import modelo.GastosVarios;
import modelo.gastosFijos.Agua;
import modelo.gastosFijos.Cable;
import modelo.gastosFijos.Electricidad;
import modelo.gastosFijos.Internet;
import modelo.gastosFijos.Telefono;

public class Vista extends JFrame { // Acá se implementa herencia, heredamos nuestra clase vista con la clase JFrame
    // de Javax.Swing

    Container cont = new Container();
    DefaultTableModel mtIngresos = new DefaultTableModel();
    DefaultTableModel mtColaborador = new DefaultTableModel();
    DefaultTableModel mtGastos = new DefaultTableModel();

    JComboBox<String> contribuyentesJCombo = new JComboBox<>();

    Logica logica;
    private float ingreso = 0;
    private float gasto = 0;
    private float balance = 0;

    public Vista(Logica logica) {
        this.logica = logica;
        inicializarComponentes();
    }

    public void inicializarComponentes() {
        super.setBounds(0, 0, 1000, 800);
        super.setTitle("Finanzas familiares");
        super.setLocationRelativeTo(null);
        cont.setLayout(null);
        super.setContentPane(cont);
        super.setResizable(false);

        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creamos el JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 30, 1000, 800);

        cont.add(tabbedPane);

        tabbedPane.add("Ingresos", panelIngresos());
        tabbedPane.add("Contribuyentes", panelContribuyentes());
        tabbedPane.add("Gastos", RegistroGastos());
        tabbedPane.add("Consultas", panelConsultas());

    }

    public JPanel panelIngresos() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel label = new JLabel("Bienvenido al panel de ingresos");
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setBounds(350, 5, 300, 30);
        panel.add(label);

        contribuyentesJCombo.setBounds(10, 70, 200, 30);
        contribuyentesJCombo.addItem("Contribuyente 1");

        panel.add(contribuyentesJCombo);
        JLabel label2 = new JLabel("Seleccione el contribuyente:");
        label2.setBounds(10, 40, 200, 30);
        panel.add(label2);

        JLabel label3 = new JLabel("Monto en colones:");
        label3.setBounds(300, 40, 200, 30);
        panel.add(label3);

        JTextArea montoIngreso = new JTextArea();
        montoIngreso.setBounds(300, 70, 100, 25);
        panel.add(montoIngreso);

        JLabel label4 = new JLabel("Ingrese la fecha de ingreso:");
        label4.setBounds(500, 40, 200, 30);
        panel.add(label4);
        JDateChooser fechaIngreso = new JDateChooser();
        fechaIngreso.setDateFormatString("dd/MM/yyyy");
        fechaIngreso.setBounds(500, 70, 150, 30);
        panel.add(fechaIngreso);

        JLabel label5 = new JLabel("Seleccione el método:");
        label5.setBounds(750, 40, 200, 30);
        panel.add(label5);

        JComboBox<String> metodoJCombo = new JComboBox<>();
        metodoJCombo.setBounds(750, 70, 200, 30);
        metodoJCombo.addItem("Efectivo");
        metodoJCombo.addItem("Transferencia bancaria");
        metodoJCombo.addItem("Cheque");
        metodoJCombo.addItem("SINPE Móvil");

        panel.add(metodoJCombo);

        String[] columnas = { "Contribuyente", "Monto", "Fecha", "Método" };
        mtIngresos.setColumnIdentifiers(columnas);

        JTable tablaColaboradores = new JTable();
        tablaColaboradores.setBounds(10, 150, 900, 400);
        tablaColaboradores.setModel(mtIngresos);

        JScrollPane scroll = new JScrollPane(tablaColaboradores);
        scroll.setBounds(10, 170, 950, 400);

        panel.add(scroll);
        tablaColaboradores.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaColaboradores.setFillsViewportHeight(true);
        // modelo.addRow(new Object[] { "Contribuyente 1", "1000", "01/01/2023",
        // "Efectivo" });

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.setBounds(800, 600, 100, 30);
        botonAgregar.setBackground(new Color(0x097CFF));
        botonAgregar.setForeground(Color.white);
        botonAgregar.addActionListener(e -> {
            String contribuyente = (String) contribuyentesJCombo.getSelectedItem();
            String monto = montoIngreso.getText();
            LocalDate fecha = fechaIngreso.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            String metodo = (String) metodoJCombo.getSelectedItem();

            agregarIngreso(contribuyente, Float.parseFloat(monto), fecha, metodo);
            mtIngresos.addRow(new Object[] { contribuyente, monto, fecha, metodo });
            generarTablaIngresos();
        });

        panel.add(botonAgregar);

        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.setBounds(50, 600, 100, 30);
        botonEliminar.setBackground(new Color(0xFF0000));
        botonEliminar.setForeground(Color.white);
        botonEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaColaboradores.getSelectedRow();
            if (filaSeleccionada != -1) {
                mtIngresos.removeRow(filaSeleccionada);
                logica.ingresos.remove(filaSeleccionada);
                generartablaConsulta();
            }
        });

        panel.add(botonEliminar);

        return panel;
    }

    public void agregarIngreso(String colaborador, float monto, LocalDate fecha, String metodo) {
        String mes;
        // Obtener el mes de la fecha en español
        java.util.Locale locale = new java.util.Locale("es", "ES");
        java.time.format.TextStyle textStyle = java.time.format.TextStyle.FULL;
        mes = fecha.getMonth().getDisplayName(textStyle, locale);
        String año = String.valueOf(fecha.getYear());
        logica.ingresos.add(new Ingresos(colaborador, mes, año, fecha, metodo, monto));
        logica.totalIngresos += monto;

    }

    public void generarTablaIngresos() {
        mtIngresos.setRowCount(0); // Limpiar la tablaColaboradores antes de agregar los nuevos datos
        for (int i = 0; i < logica.ingresos.size(); i++) {
            mtIngresos.addRow(new Object[] { logica.ingresos.get(i).getColaborador(),
                    logica.ingresos.get(i).getMonto(),
                    logica.ingresos.get(i).getFecha(),
                    logica.ingresos.get(i).getMetodo() });
        }
    }

    public JPanel panelContribuyentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel label = new JLabel("Bienvenido al panel de contribuyentes");
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setBounds(350, 5, 300, 30);
        panel.add(label);

        JTextField nombreContribuyente = new JTextField();
        nombreContribuyente.setBounds(200, 70, 200, 30);
        panel.add(nombreContribuyente);

        JLabel label2 = new JLabel("Ingrese el nombre del contribuyente:");
        label2.setBounds(200, 40, 200, 30);
        panel.add(label2);

        JLabel label3 = new JLabel("Seleccione el parentesco:");
        label3.setBounds(500, 40, 200, 30);
        panel.add(label3);

        JComboBox<String> parentescoJCombo = new JComboBox<>();
        parentescoJCombo.setBounds(500, 70, 200, 30);
        parentescoJCombo.addItem("Esposo(a)");
        parentescoJCombo.addItem("Hijo(a)");
        parentescoJCombo.addItem("Padre/Madre");
        parentescoJCombo.addItem("Hermano(a)");
        parentescoJCombo.addItem("Nieto(a)");
        parentescoJCombo.addItem("Otro");

        panel.add(parentescoJCombo);

        JTable tablaColaboradores = new JTable();
        tablaColaboradores.setBounds(10, 150, 900, 400);
        String[] columnas = { "Nombre", "Parentesco" };

        mtColaborador.setColumnIdentifiers(columnas);
        tablaColaboradores.setModel(mtColaborador);
        JScrollPane scroll = new JScrollPane(tablaColaboradores);
        scroll.setBounds(10, 150, 950, 400);
        panel.add(scroll);

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.setBounds(800, 600, 100, 30);
        botonAgregar.setBackground(new Color(0x097CFF));
        botonAgregar.setForeground(Color.white);
        botonAgregar.addActionListener(e -> {
            String nombre = nombreContribuyente.getText();
            String parentesco = (String) parentescoJCombo.getSelectedItem();
            logica.colaboradores.add(new Colaborador(nombre, 0, parentesco));
            JOptionPane.showMessageDialog(null, "Agregado exitosamente");
            generarTablaColaboradores();
        });

        panel.add(botonAgregar);

        return panel;
    }

    public void generarTablaColaboradores() {
        mtColaborador.setRowCount(0); // Limpiar la tablaColaboradores antes de agregar los nuevos datos
        contribuyentesJCombo.removeAllItems();

        for (int i = 0; i < logica.colaboradores.size(); i++) {
            mtColaborador.addRow(new Object[] { logica.colaboradores.get(i).getNombre(),
                    logica.colaboradores.get(i).getParentesco() });

            contribuyentesJCombo.addItem(logica.colaboradores.get(i).getNombre());
        }

    }

    public JPanel RegistroGastos() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JLabel label = new JLabel("Bienvenido al panel de gastos");
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setBounds(350, 5, 300, 30);
        panel.add(label);

        JLabel label2 = new JLabel("Ingrese el monto del gasto:");
        label2.setBounds(10, 40, 200, 30);
        panel.add(label2);

        JTextField montoGasto = new JTextField();
        montoGasto.setBounds(10, 70, 200, 30);
        panel.add(montoGasto);

        JLabel label3 = new JLabel("Seleccione la fecha del gasto:");
        label3.setBounds(300, 40, 200, 30);
        panel.add(label3);

        JDateChooser fechaGasto = new JDateChooser();
        fechaGasto.setDateFormatString("dd/MM/yyyy");
        fechaGasto.setBounds(300, 70, 200, 30);
        panel.add(fechaGasto);

        JLabel label4 = new JLabel("Seleccione el tipo de gasto:");
        label4.setBounds(600, 40, 200, 30);

        panel.add(label4);
        JComboBox<String> tipoGastoJCombo = new JComboBox<>();
        tipoGastoJCombo.setBounds(600, 70, 200, 30);
        tipoGastoJCombo.addItem("Telefono");
        tipoGastoJCombo.addItem("Agua");
        tipoGastoJCombo.addItem("Cable");
        tipoGastoJCombo.addItem("Electricidad");
        tipoGastoJCombo.addItem("Internet");
        tipoGastoJCombo.addItem("Otros Gastos");
        panel.add(tipoGastoJCombo);

        JTable tablaConsulta = new JTable();
        tablaConsulta.setBounds(10, 150, 900, 400);
        String[] columnas = { "Monto", "Fecha", "Motivo", "Tipo", "Mes" };

        mtGastos.setColumnIdentifiers(columnas);
        tablaConsulta.setModel(mtGastos);
        JScrollPane scroll = new JScrollPane(tablaConsulta);
        scroll.setBounds(10, 150, 950, 400);
        panel.add(scroll);
        tablaConsulta.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaConsulta.setFillsViewportHeight(true);

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.setBounds(800, 580, 100, 30);
        botonAgregar.setBackground(Color.GREEN);
        botonAgregar.setForeground(Color.white);
        botonAgregar.addActionListener(e -> {
            Float monto = Float.parseFloat(montoGasto.getText());

            String tipo = (String) tipoGastoJCombo.getSelectedItem();
            String mes;
            // Obtener el mes de la fecha en español

            LocalDate fecha = fechaGasto.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            java.util.Locale locale = new java.util.Locale("es", "ES");
            java.time.format.TextStyle textStyle = java.time.format.TextStyle.FULL;
            mes = fecha.getMonth().getDisplayName(textStyle, locale);
            String motivo = "Pago de " + tipo;
            String año = String.valueOf(fecha.getYear());

            
            String categorias[]= {"Medicamentos", "Alimentos", "Transporte", "Ropa", "Actividades recreativas", "Otros"};
            JComboBox jComboCategoria = new JComboBox<>(categorias);


            switch (tipo) {
                case "Agua":
                    logica.gastos.add(new Agua(monto, fecha, motivo, mes, "Gastos Fijos", año));
                    break;
                case "Cable":
                    logica.gastos.add(new Cable(monto, fecha, motivo, mes, "Gastos Fijos", año));
                    break;
                case "Electricidad":
                    logica.gastos.add(new Electricidad(monto, fecha, motivo, mes, "Gastos Fijos", año));
                    break;
                case "Internet":
                    logica.gastos.add(new Internet(monto, fecha, motivo, mes, "Gastos Fijos", año));
                    break;

                case "Telefono":
                    logica.gastos.add(new Telefono(monto, fecha, motivo, mes, "Gastos Fijos", año));
                    break;
                case "Otros Gastos":
                int op = JOptionPane.showConfirmDialog(null, jComboCategoria, "Seleccione la categoría",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (op == JOptionPane.OK_OPTION) {
                    String categoria = (String) jComboCategoria.getSelectedItem();
                    motivo = JOptionPane.showInputDialog("Ingrese el motivo del gasto:");
                    logica.gastos.add(new GastosVarios(monto, fecha, motivo, mes, "Gastos Varios", año, categoria));
                }
                    
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Tipo de gasto no válido");
            }

            // limpiar los campos de texto
            montoGasto.setText("");
            tipoGastoJCombo.setSelectedIndex(0);
            fechaGasto.setDate(null);

            generartablaConsulta();

            JOptionPane.showMessageDialog(null, "Agregado exitosamente");

        });
        panel.add(botonAgregar);

        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.setBounds(50, 580, 100, 30);
        botonEliminar.setBackground(new Color(0xFF0000));
        botonEliminar.setForeground(Color.white);
        botonEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaConsulta.getSelectedRow();
            if (filaSeleccionada != -1) {
                mtGastos.removeRow(filaSeleccionada);
                logica.gastos.remove(filaSeleccionada);
                JOptionPane.showMessageDialog(null, "Eliminado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
            }
        });
        panel.add(botonEliminar);
        return panel;

    }

    public void generartablaConsulta() {
        mtGastos.setRowCount(0); // Limpiar la tablaColaboradores antes de agregar los nuevos datos

        for (int i = 0; i < logica.gastos.size(); i++) {
            mtGastos.addRow(new Object[] { logica.gastos.get(i).getMonto(),
                    logica.gastos.get(i).getFecha(),
                    logica.gastos.get(i).getMotivo(),
                    logica.gastos.get(i).getTipo(),
                    logica.gastos.get(i).getMes() });
        }

    }

    // Modelos de tablas definidas de forma global para poder controlarla en métodos
    // distintos.

    DefaultTableModel mtConsultaGastos = new DefaultTableModel();
    DefaultTableModel mtConsultaIngresos = new DefaultTableModel();

    // Igual el textArea
    JTextArea balanceArea = new JTextArea();

    public JPanel panelConsultas() {
        String[] columnas = { "Contribuyente", "Monto", "Fecha", "Método" };
        mtConsultaIngresos.setColumnIdentifiers(columnas);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JTable tablaConsulta = new JTable();
        tablaConsulta.setModel(mtConsultaIngresos);
        tablaConsulta.setBounds(10, 150, 900, 300);
        tablaConsulta.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaConsulta.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(tablaConsulta);
        scroll.setBounds(10, 250, 900, 300);
        panel.add(scroll);

        JLabel label = new JLabel("Bienvenido al panel de consultas");
        label.setFont(new Font("Arial", Font.BOLD, 15));
        label.setBounds(350, 5, 300, 30);
        panel.add(label);
        JLabel label2 = new JLabel("Seleccione el mes:");
        label2.setBounds(10, 40, 200, 30);
        panel.add(label2);
        JComboBox<String> mesJCombo = new JComboBox<>();
        mesJCombo.setBounds(10, 70, 200, 30);
        String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
                "Octubre", "Noviembre", "Diciembre" };
        for (String mes : meses) {
            mesJCombo.addItem(mes);
        }
        panel.add(mesJCombo);

        JLabel label3 = new JLabel("Seleccione el año:");
        label3.setBounds(300, 40, 200, 30);
        panel.add(label3);
        JComboBox<String> anioJCombo = new JComboBox<>();
        anioJCombo.setBounds(300, 70, 200, 30);
        for (int i = 2025; i >= 2020; i--) { // Cambiar para que se ajuste al año actual
            anioJCombo.addItem(String.valueOf(i));
        }
        panel.add(anioJCombo);

        JLabel label4 = new JLabel("Seleccione el tipo de consulta:");
        label4.setBounds(600, 40, 200, 30);
        panel.add(label4);
        JComboBox<String> tipoConsultaJCombo = new JComboBox<>();
        tipoConsultaJCombo.setBounds(600, 70, 200, 30);
        String[] tiposConsulta = { "Ingresos", "Gastos", "Balance" };
        for (String tipo : tiposConsulta) {
            tipoConsultaJCombo.addItem(tipo);
        }
        panel.add(tipoConsultaJCombo);

        tipoConsultaJCombo.addActionListener(e -> {
            int op = tipoConsultaJCombo.getSelectedIndex();

            switch (op) {
                case 0:
                    
                    mtConsultaIngresos.setColumnIdentifiers(columnas);
                    tablaConsulta.setModel(mtConsultaIngresos);

                    break;

                case 1:
                    String[] columnasGastos = { "Monto", "Fecha", "Motivo", "Tipo", "Mes" };
                    mtConsultaGastos.setColumnIdentifiers(columnasGastos);
                    tablaConsulta.setModel(mtConsultaGastos);
                    break;
                default:
                
                    // panel.remove(scroll);
                    break;
            }
        });

        JButton botonConsultar = new JButton("Consultar");
        botonConsultar.setBounds(800, 70, 100, 30);
        botonConsultar.setBackground(new Color(0x097CFF));
        botonConsultar.setForeground(Color.white);

        botonConsultar.addActionListener(e -> {
            ingreso = 0;
            gasto = 0;
            String mes = (String) mesJCombo.getSelectedItem();
            String anio = (String) anioJCombo.getSelectedItem();
            String tipoConsulta = (String) tipoConsultaJCombo.getSelectedItem();

            balanceArea.setBounds(10, 130, 900, 80);
            balanceArea.setEditable(false);
            panel.add(balanceArea);

            switch (tipoConsulta) {

                case "Ingresos":

                    mtConsultaIngresos.setRowCount(0);
                    for (int i = 0; i < logica.ingresos.size(); i++) {
                        if (logica.ingresos.get(i).getMes().equalsIgnoreCase(mes)
                                && logica.ingresos.get(i).getAño().equals(anio)) {
                            mtConsultaIngresos.addRow(new Object[] { logica.ingresos.get(i).getColaborador(),
                                    logica.ingresos.get(i).getMonto(),
                                    logica.ingresos.get(i).getFecha(),
                                    logica.ingresos.get(i).getMetodo() });
                            ingreso += logica.ingresos.get(i).getMonto();

                        }

                    }
                    if (mtConsultaIngresos.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null,
                                "No se encontraron ingresos para el mes y año seleccionados");
                    } else {
                        JOptionPane.showMessageDialog(null, "Consulta realizada exitosamente");
                    }

                    // JTextField totalIngresos = new JTextField("Total de ingresos: " + ingreso);
                    // totalIngresos.setBounds(10, 200, 200, 30);
                    // totalIngresos.setEditable(false);
                    // panel.add(totalIngresos);

                    balanceArea.setText("Total de ingresos: " + ingreso);

                    break;
                case "Gastos":

                    mtConsultaGastos.setRowCount(0);
                    for (int i = 0; i < logica.gastos.size(); i++) {
                        if (logica.gastos.get(i).getMes().equalsIgnoreCase(mes)
                                && logica.gastos.get(i).getAño().equals(anio)) {
                            mtConsultaGastos.addRow(new Object[] { logica.gastos.get(i).getMonto(),
                                    logica.gastos.get(i).getFecha(),
                                    logica.gastos.get(i).getMotivo(),
                                    logica.gastos.get(i).getTipo(),
                                    logica.gastos.get(i).getMes() });
                            gasto += logica.gastos.get(i).getMonto();
                        }
                    }
                    if (mtConsultaGastos.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "No se encontraron gastos para el mes y año seleccionados");
                    } else {
                        JOptionPane.showMessageDialog(null, "Consulta realizada exitosamente");
                    }

                    balanceArea.setText("Total de gastos: " + gasto);

                    break;
                case "Balance":

                    // calcular el balance
                    for (int i = 0; i < logica.ingresos.size(); i++) {
                        if (logica.ingresos.get(i).getMes().equalsIgnoreCase(mes)
                                && logica.ingresos.get(i).getAño().equals(anio)) {
                            ingreso += logica.ingresos.get(i).getMonto();
                        }
                    }

                    for (int i = 0; i < logica.gastos.size(); i++) {
                        if (logica.gastos.get(i).getMes().equalsIgnoreCase(mes)
                                && logica.gastos.get(i).getAño().equals(anio)) {
                            gasto += logica.gastos.get(i).getMonto();
                        }
                    }
                    balance = ingreso - gasto;
                    String balanceString = "Balance del mes " + mes + " del año " + anio + ": " + balance;
                    String balanceString2 = "Total de ingresos: " + ingreso + "\n" + "Total de gastos: " + gasto;
                    balanceArea.setText(balanceString + "\n" + balanceString2);
                    balanceArea.setEditable(false);
                    balanceArea.setLineWrap(true);
                    balanceArea.setWrapStyleWord(true);
                    balanceArea.setVisible(true);
                    // Mostrar el balance en un JOptionPane

                    if (balance > 0) {
                        JOptionPane.showMessageDialog(null, "Consulta realizada exitosamente. El balance es positivo.");
                    } else if (balance < 0) {
                        JOptionPane.showMessageDialog(null, "Consulta realizada exitosamente. El balance es negativo.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Consulta realizada exitosamente. El balance es cero.");
                    }

                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Tipo de consulta no válido");
            }

        });
        panel.add(botonConsultar);

        return panel;
    }

}
