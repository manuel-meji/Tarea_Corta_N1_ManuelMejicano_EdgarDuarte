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
    // Se crea un contenedor para agregar los componentes
    Container cont = new Container();
    // se crean los modelos de tablas de forma global para poder acceder a ellas
    // desde cualquier metodo
    DefaultTableModel mtIngresos = new DefaultTableModel();
    DefaultTableModel mtColaborador = new DefaultTableModel();
    DefaultTableModel mtGastos = new DefaultTableModel();

    // aqui se crea el JComboBox para los contribuyentes, que me permite modificarlo
    // desde varias clases al ser global
    JComboBox<String> contribuyentesJCombo = new JComboBox<>();

    Logica logica;
    private float ingreso = 0;
    private float gasto = 0;
    private float balance = 0;

    public Vista(Logica logica) {
        this.logica = logica;
        // en este metodo se inicializan los componentes de la ventana, creando un
        // JTabbedPane y agregando los paneles correspondientes
        // a cada pestaña, y los paneles contienen otros diferentes segun las
        // necesidades
        inicializarComponentes();

        // este apartado es super importante, ya que cuando el usuario cierra la
        // ventana, se le pregunta si desea guardar los cambios, y si es asi se llama al
        // metodo guardar de la clase logica
        // y se cierra la ventana, si no desea guardar los cambios se cierra la ventana
        // sin guardar nada, y si elige cancelar se queda en la ventana
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Desea guardar los cambios?", "Guardar",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    try {
                        // se llama al metodo guardar de la clase logica, que guarda los datos en un
                        // archivo .dat
                        logica.guardar();
                        System.exit(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (respuesta == JOptionPane.NO_OPTION) {
                    // unicamente se cierra la ventana sin guardar nada y cierra el programa
                    System.exit(0);
                } else if (respuesta == JOptionPane.CANCEL_OPTION) {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }

            }
        });
    }
    // aqui se crea el JTabbedPane y se le agregan los tabs de cada apartado

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
        tabbedPane.setBounds(0, 5, 1000, 800);

        cont.add(tabbedPane);

        tabbedPane.add("Ingresos", panelIngresos());
        tabbedPane.add("Contribuyentes", panelContribuyentes());
        tabbedPane.add("Gastos", RegistroGastos());
        tabbedPane.add("Consultas", panelConsultas());

    }

    // moldeamos el panel de ingresos, donde se agregan los componentes necesarios
    // para ingresar los datos de los ingresos, como el monto, la fecha y el metodo
    // de pago
    // y se crea la tabla para mostrar los ingresos ingresados, y se le agregan los
    // botones de agregar y eliminar los ingresos
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

        JTable tablaIngresos = new JTable();
        tablaIngresos.setBounds(10, 150, 900, 400);
        tablaIngresos.setModel(mtIngresos);

        JScrollPane scroll = new JScrollPane(tablaIngresos);
        scroll.setBounds(10, 170, 950, 400);

        panel.add(scroll);
        tablaIngresos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablaIngresos.setFillsViewportHeight(true);

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.setBounds(800, 600, 100, 30);
        botonAgregar.setBackground(new Color(0x097CFF));
        botonAgregar.setForeground(Color.white);
        botonAgregar.addActionListener(e -> {

            Colaborador contribuyente = logica.getColaboradores().get(contribuyentesJCombo.getSelectedIndex());
            Float monto = Float.parseFloat(montoIngreso.getText());
            LocalDate fecha = fechaIngreso.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            String metodo = (String) metodoJCombo.getSelectedItem();

            // se agrega un nuevo ingreso
            agregarIngreso(contribuyente, monto, fecha, metodo);

            // se suma el monto al contribuyente, para llevar contabilizado cuanto ha
            // aportado
            logica.getColaboradores().get(contribuyentesJCombo.getSelectedIndex()).setMontos(monto);
            // se añade dicho ingreso a la tabla de ingresos
            mtIngresos.addRow(new Object[] { contribuyente, monto, fecha, metodo });

            // limpia los campos de texto
            contribuyentesJCombo.setSelectedItem(0);
            montoIngreso.setText("");
            fechaIngreso.setDate(null);
            metodoJCombo.setSelectedIndex(0);

            generarTablaIngresos();
            generarTablaColaboradores();
        });

        panel.add(botonAgregar);

        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.setBounds(50, 600, 100, 30);
        botonEliminar.setBackground(new Color(0xFF0000));
        botonEliminar.setForeground(Color.white);
        botonEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaIngresos.getSelectedRow();
            if (filaSeleccionada != -1) {
                // Eliminar la fila seleccionada de la tabla y del Arraylist de ingresos
                mtIngresos.removeRow(filaSeleccionada);
                logica.ingresos.remove(filaSeleccionada);
                generarTablaGastos();
                logica.calcularEstadisticas();
            }
        });

        panel.add(botonEliminar);

        return panel;
    }

    public void agregarIngreso(Colaborador colaborador, float monto, LocalDate fecha, String metodo) {
        String mes;

        // con ayuda de la herramienta de IA obtenemos el mes en español utilizando el
        // parametro de la fecha que recibimos
        java.util.Locale locale = new java.util.Locale("es", "ES");
        java.time.format.TextStyle textStyle = java.time.format.TextStyle.FULL;
        mes = fecha.getMonth().getDisplayName(textStyle, locale);
        // se obtiene el mes en español y en formato String
        // obtenemos el año en formato String
        String año = String.valueOf(fecha.getYear());
        // instanciamos el objeto ingresos y lo añadimos al arraylist de ingresos
        logica.ingresos.add(new Ingresos(colaborador, mes, año, fecha, metodo, monto));

        // se hace la llamada al metodo de la clase logica que calcula las estadisticas
        // generales, como el total de ingresos ,gastos y el balance
        logica.calcularEstadisticas();

    }

    // este metodo genera la tabla de ingresos, que se muestra en el panel de
    // ingresos, y se le añaden los datos de los ingresos ingresados
    // tambien se llama al inicio del programa para mostrar los datos recuperados en
    // caso de que se hayan guardado varios datos
    // y asi poder verlos al iniciar el programa
    public void generarTablaIngresos() {
        mtIngresos.setRowCount(0); // Limpiar la tablaColaboradores antes de agregar los nuevos datos
        for (int i = 0; i < logica.ingresos.size(); i++) {
            mtIngresos.addRow(new Object[] { logica.ingresos.get(i).getColaborador().getNombre(),
                    "₡" + logica.ingresos.get(i).getMonto(),
                    logica.ingresos.get(i).getFecha(),
                    logica.ingresos.get(i).getMetodo() });
        }
    }

    // este metodo genera el panel de contribuyentes, donde se agregan los
    // componentes necesarios para ingresar los datos de los contribuyentes, como el
    // nombre y el parentesco
    // posterior a eso, se genera la tabla de colaboradores, ademas de los botones
    // de agregar y eliminar colaboradores
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
        String[] columnas = { "Nombre", "Parentesco", "Monto aportado" };

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
            logica.colaboradores.add(new Colaborador(nombre, parentesco));
            generarTablaColaboradores();
            JOptionPane.showMessageDialog(null, "Agregado exitosamente");
            
            // limpiamos campos de texto
            nombreContribuyente.setText("");

        });

        panel.add(botonAgregar);

        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.setBounds(50, 600, 100, 30);
        botonEliminar.setBackground(new Color(0xFF0000));
        botonEliminar.setForeground(Color.white);
        botonEliminar.addActionListener( e -> {
            int filaSeleccionada = tablaColaboradores.getSelectedRow();
            if (filaSeleccionada != -1) {
                // Eliminar la fila seleccionada de la tabla y del Arraylist de contribuyentes
               
                logica.colaboradores.remove(filaSeleccionada);
                JOptionPane.showMessageDialog(null, "Eliminado exitosamente");
                generarTablaColaboradores();
            }else{JOptionPane.showMessageDialog(null, "Seleccione una fila de la tabla para eliminar");}
        });
        panel.add(botonEliminar);

        return panel;
    }

    // este metodo genera la tabla de colaboradores, que se muestra en el panel de
    // colaboradores, y se le añaden los datos de los colaboradores ingresados
    // tambien se llama al inicio del programa para mostrar los datos recuperados en
    // caso de que se hayan guardado varios datos
    // y asi poder verlos al iniciar el programa
    public void generarTablaColaboradores() {
        mtColaborador.setRowCount(0); // Limpiar la tablaColaboradores antes de agregar los nuevos datos
        contribuyentesJCombo.removeAllItems();

        for (int i = 0; i < logica.colaboradores.size(); i++) {
            mtColaborador.addRow(new Object[] {
                    logica.colaboradores.get(i).getNombre(),
                    logica.colaboradores.get(i).getParentesco(),
                    "₡" + logica.colaboradores.get(i).getMontos()
            });

            contribuyentesJCombo.addItem(logica.colaboradores.get(i).getNombre());
        }

    }

    // este metodo genera el panel de gastos, donde se agregan los componentes
    // necesarios para ingresar los datos de los gastos, como el monto, la fecha y
    // el tipo de gasto
    // y se crea la tabla para mostrar los gastos ingresados, y se le agregan los
    // botones de agregar y eliminar los gastos
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

        // Se crea un JDateChooser para seleccionar la fecha
        // Se utiliza la librería com.toedter.calendar para esto
        // esto permite que el usuario seleccione la fecha de una manera más simple ya
        // que pude seleccionar la fecha desde un calendario
        // y no escribirla manualmente
        // y se le da el formato de dia/mes/año
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

            // con ayuda de la herramienta de IA obtenemos el mes en español utilizando el
            // parametro de la fecha que recibimos
            LocalDate fecha = fechaGasto.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            java.util.Locale locale = new java.util.Locale("es", "ES");
            java.time.format.TextStyle textStyle = java.time.format.TextStyle.FULL;
            mes = fecha.getMonth().getDisplayName(textStyle, locale);
            String motivo = "Pago de " + tipo;
            String año = String.valueOf(fecha.getYear());

            String categorias[] = { "Medicamentos", "Alimentos", "Transporte", "Ropa", "Actividades recreativas",
                    "Otros" };
            JComboBox jComboCategoria = new JComboBox<>(categorias);

            // en este switch se selecciona el tipo de gasto, y dependiendo del tipo de
            // gasto se crea un objeto diferente, y se añade al arraylist de gastos
            // dependiendo del tipo de gasto se le pide al usuario el motivo del gasto
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

                // En caso de que el gasto no sea fijo, se le pide al usuario el motivo del
                // gasto
                // al igual se le pide al usuario la categoria del gasto
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

            // una vez añadido al arraylist de gastos, se añade a la tabla de gastos
            // y se llama al metodo de la clase logica que calcula las estadisticas
            // generales, como el total de ingresos ,gastos y el balance
            generarTablaGastos();
            logica.calcularEstadisticas();

            JOptionPane.showMessageDialog(null, "Agregado exitosamente");

        });
        panel.add(botonAgregar);

        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.setBounds(50, 580, 100, 30);
        botonEliminar.setBackground(new Color(0xFF0000));
        botonEliminar.setForeground(Color.white);
        botonEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaConsulta.getSelectedRow();
            // Eliminar la fila seleccionada de la tabla y del Arraylist de gastos
            if (filaSeleccionada != -1) {
                mtGastos.removeRow(filaSeleccionada);
                logica.gastos.remove(filaSeleccionada);
                JOptionPane.showMessageDialog(null, "Eliminado exitosamente");
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
            }

            // como se elimina un gasto, se vuelve a calcular el balance y los ingresos
            // y se actualiza la tabla de gastos
            logica.calcularEstadisticas();
        });
        panel.add(botonEliminar);
        return panel;

    }


    // al igual que el metodo de ingresos, este metodo genera la tabla de gastos,
    // que se muestra en el panel de gastos, y se le añaden los datos de los gastos
    // ingresados
    // tambien se llama al inicio del programa para mostrar los datos recuperados en
    // caso de que se hayan guardado varios datos
    public void generarTablaGastos() {
        mtGastos.setRowCount(0); // Limpiar la tablaColaboradores antes de agregar los nuevos datos

        for (int i = 0; i < logica.gastos.size(); i++) {
            mtGastos.addRow(new Object[] { "₡" + logica.gastos.get(i).getMonto(),
                    logica.gastos.get(i).getFecha(),
                    logica.gastos.get(i).getMotivo(),
                    logica.gastos.get(i).getTipo(),
                    logica.gastos.get(i).getMes() });
        }

    }

    // Modelos de tablas definidas de forma global para poder controlarla en métodos
    // distintos.
    // mas que todo para poder acceder a ellas desde el panel de consultas, ya que
    // se
    // necesita cambiar el modelo de la tabla dependiendo del tipo de consulta que
    // se realize

    DefaultTableModel mtConsultaGastos = new DefaultTableModel();
    DefaultTableModel mtConsultaIngresos = new DefaultTableModel();

    // Igual el textArea
    JTextArea balanceArea = new JTextArea("");

    public JPanel panelConsultas() {

        String[] columnas = { "Contribuyente", "Monto", "Fecha", "Método" };
        mtConsultaIngresos.setColumnIdentifiers(columnas);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        balanceArea.setBounds(10, 130, 900, 80);
        balanceArea.setFont(new Font("Arial", Font.BOLD, 13));
        balanceArea.setEditable(false);
        panel.add(balanceArea);

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

        /// se le pide al usuario que seleccione el mes que desea consultar de maera especifica, y se le da la opcion de seleccionar entre los meses de enero a diciembre
        JComboBox<String> mesJCombo = new JComboBox<>();
        mesJCombo.setBounds(10, 70, 200, 30);
        String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre",
                "Octubre", "Noviembre", "Diciembre" };
        for (String mes : meses) {
            mesJCombo.addItem(mes);
        }
        panel.add(mesJCombo);
        // se le pide al usuario que seleccione el año, y se le da la opcion de
        // seleccionar entre los años 2020 y 2025
        JLabel label3 = new JLabel("Seleccione el año:");
        label3.setBounds(300, 40, 200, 30);
        panel.add(label3);
        JComboBox<String> anioJCombo = new JComboBox<>();
        anioJCombo.setBounds(300, 70, 200, 30);
        for (int i = 2025; i >= 2020; i--) { // Cambiar para que se ajuste al año actual
            anioJCombo.addItem(String.valueOf(i));
        }
        panel.add(anioJCombo);

        // se le solicita al usuario que indique que desea consultar, si ingresos,
        // gastos o balance
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
            // Cambiar el modelo de la tabla dependiendo del tipo de consulta
            // una vez seleccionado el tipo de consulta, se cambia el modelo de la tabla
            // para que se ajuste a los datos que se van a mostrar posteriormente
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

            // una vez se le de click al boton de consultar, se obtiene el mes, año y tipo
            // de consulta seleccionados
            ingreso = 0;
            gasto = 0;
            String mes = (String) mesJCombo.getSelectedItem();
            String anio = (String) anioJCombo.getSelectedItem();
            String tipoConsulta = (String) tipoConsultaJCombo.getSelectedItem();

            switch (tipoConsulta) {
                // segun el tipo de cosulta, se cargan los datos correspondientes a la tabla
                // llamando a los diferentes arraylist de la clase logica, y se filtran por mes
                // y año
                case "Ingresos":

                    mtConsultaIngresos.setRowCount(0);
                    for (int i = 0; i < logica.ingresos.size(); i++) {
                        if (logica.ingresos.get(i).getMes().equalsIgnoreCase(mes)
                                && logica.ingresos.get(i).getAño().equals(anio)) {
                            mtConsultaIngresos
                                    .addRow(new Object[] { logica.ingresos.get(i).getColaborador().getNombre(),
                                            "₡" + logica.ingresos.get(i).getMonto(),
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

                    balanceArea.setText("Total de ingresos en " + mes + ": ₡" + ingreso);

                    break;
                case "Gastos":

                    mtConsultaGastos.setRowCount(0);
                    for (int i = 0; i < logica.gastos.size(); i++) {
                        if (logica.gastos.get(i).getMes().equalsIgnoreCase(mes)
                                && logica.gastos.get(i).getAño().equals(anio)) {
                            mtConsultaGastos.addRow(new Object[] { "₡" + logica.gastos.get(i).getMonto(),
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

                    balanceArea.setText("Total de gastos en " + mes + ": ₡" + gasto);

                    break;

                // en este caso se calcula el balance, y se muestra en un JOptionPane si es
                // positivo, negativo o cero
                // y se muestra el total de ingresos y gastos en el textArea
                // se hace un ciclo for para recorrer los arraylist de ingresos y gastos, y se
                // filtran por mes y año
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

                    balanceArea.setText("Información de " + mes + " de " + anio + ": \n\n" +
                            "Total de ingresos: ₡" + ingreso + "\n" +
                            "Total de gastos: ₡" + gasto + "\n" +
                            "Saldo disponible: ₡" + balance);

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

        JButton buttonDetalles = new JButton("Detalles");
        buttonDetalles.setBounds(800, 600, 100, 30);
        buttonDetalles.setBackground(new Color(0x097CFF));
        buttonDetalles.setForeground(Color.white);
        buttonDetalles.addActionListener(e -> {
            // aqui se le muestra al usuario los detalles del gasto seleccionado, y se le da
            // la opcion de ver los detalles de un gasto en especifico
            if (tipoConsultaJCombo.getSelectedIndex() == 1) {

                int filaSeleccionada = tablaConsulta.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String detalles = logica.gastos.get(filaSeleccionada).mostrarGasto();
                    JOptionPane.showMessageDialog(null, detalles, "Detalles del gasto",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para ver los detalles");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione la pestaña de gastos para ver los detalles");
            }

        });
        panel.add(buttonDetalles);

        // aqui se le muestra al usuario las estadisticas generales, como el total de
        // ingresos, gastos y el balance total de todos los ingresos y gastos
        // registrados
        JButton buttonEstadisticas = new JButton("Reporte general");
        buttonEstadisticas.setBounds(50, 600, 120, 30);
        buttonEstadisticas.setBackground(new Color(0xFF832251));
        buttonEstadisticas.setForeground(Color.white);
        buttonEstadisticas.addActionListener(e -> {
            String estadisticas = "Total de ingresos hasta el momento: ₡" + logica.totalIngresos + "\n"
                    + "Total de gastos hasta el momento:  ₡"
                    + logica.totalGastos + "\n" + "Balance total: ₡" + logica.Balance;
            JOptionPane.showMessageDialog(null, estadisticas, "Estadísticas generales",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(buttonEstadisticas);

        return panel;
    }

}
