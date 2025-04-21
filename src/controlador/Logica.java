package controlador;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Colaborador;
import modelo.Gastos;
import modelo.Ingresos;
import vista.Vista;

public class Logica implements Serializable {

    // Se declaran las variables de tipo ArrayList para almacenar los datos de los colaboradores, ingresos y gastos
    // de marera publica para que puedan ser accedidas desde otras clases
   public ArrayList<Colaborador> colaboradores = new ArrayList<>();
   public ArrayList<Ingresos> ingresos = new ArrayList<>();
   public ArrayList<Gastos> gastos = new ArrayList<>();

   // Variables para almacenar los totales de gastos, ingresos y balance
   // Se inicializan en 0 para evitar errores al calcular el balance
   // al igual que los ArrayList, son publicas para que puedan ser accedidas desde otras clases
   public float totalGastos = 0;
   public float totalIngresos = 0;
   public float Balance = 0;
   
    
    //Clase de vista
    Vista vista; 

    public Logica() { 
        // se llama al metodo recuperar() para cargar los datos al iniciar el programa
        recuperar();

        // al crear la instancia desde la clase principal, se inicializa la vista
        // y se le pasa la instancia de la clase Logica para que pueda acceder a los datos
      vista = new Vista(this);
      //se hace visible la vista para que el usuario pueda interactuar con ella
      vista.setVisible(true);

       //Llamamos a LOS MÉTODOS que crean las tablas, para ver los datos recuperados
       vista.generarTablaColaboradores();
       vista.generarTablaIngresos();
       vista.generarTablaGastos();

    }

// metodo que guarda los datos del programa en un archivo .dat
    public void guardar() throws IOException{
        FileOutputStream fileOut = new FileOutputStream("FAMILY.dat");
        try ( ObjectOutputStream out = new ObjectOutputStream(fileOut)){
            out.writeObject(colaboradores);
            out.writeObject(ingresos);
            out.writeObject(gastos);
            out.writeFloat(totalGastos);
            out.writeFloat(totalIngresos);
            out.writeFloat(Balance);

            
            out.close();
            fileOut.close();
        
            System.out.println( "Los datos han sido guardados en FAMILY.dat"); 
        } 
    }


    // metodo que recupera los datos del programa desde un archivo .dat
    public void recuperar(){
        try {
            FileInputStream fileIn = new FileInputStream("FAMILY.dat");

            try(ObjectInputStream entrada = new ObjectInputStream(fileIn)){
                colaboradores = (ArrayList<Colaborador>) entrada.readObject();
                ingresos = (ArrayList<Ingresos>) entrada.readObject();
                gastos = (ArrayList<Gastos>) entrada.readObject();
                totalGastos = entrada.readFloat();
                totalIngresos = entrada.readFloat();
                Balance = entrada.readFloat();

                entrada.close();
                
                JOptionPane.showMessageDialog(null, "¡Hey, bienvenido de nuevo!\n"
             +"Los datos han sido recuperados de FAMILY.dat");
            }
            
        } catch (IOException | ClassNotFoundException w) {
            JOptionPane.showMessageDialog(null, "Bienvenido a Finanzas Familiares\n" +
                    "No se encontraron datos guardados.");
        }

       
        
        
    }


    public ArrayList<Colaborador> getColaboradores() {
        return colaboradores;
    }


    public void setColaboradores(ArrayList<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }


    public ArrayList<Ingresos> getIngresos() {
        return ingresos;
    }


    public void setIngresos(ArrayList<Ingresos> ingresos) {
        this.ingresos = ingresos;
    }


    public ArrayList<Gastos> getGastos() {
        return gastos;
    }


    public void setGastos(ArrayList<Gastos> gastos) {
        this.gastos = gastos;
    }


    public float getTotalGastos() {
        return totalGastos;
    }


    public void setTotalGastos(float totalGastos) {
        this.totalGastos = totalGastos;
    }


    public float getTotalIngresos() {
        return totalIngresos;
    }


    public void setTotalIngresos(float totalIngresos) {
        this.totalIngresos = totalIngresos;
    }


    public float getBalance() {
        return Balance;
    }


    public void setBalance(float balance) {
        Balance = balance;
    }


    public Vista getVista() {
        return vista;
    }


    public void setVista(Vista vista) {
        this.vista = vista;
    }

    // este metodo lo que hace es recorrer los ArrayList de ingresos y gastos
    // y sumar los montos de cada uno, para luego calcular el balance
    // una vez que se han sumado todos los ingresos y gastos y se ha calculado el balance
    // se guardan en las variables correspondientes
    // declarado como public para que pueda ser accedido desde otras clases
    // y asi poder actualizar los datos en la vista
    public void calcularEstadisticas() {
        totalGastos = 0;
        totalIngresos = 0;
        Balance = 0;
        
        for (Gastos gasto : gastos) {
            totalGastos += gasto.getMonto();
        }
        
        for (Ingresos ingreso : ingresos) {
            totalIngresos += ingreso.getMonto();
        }
        
        Balance = totalIngresos - totalGastos;
    }


}
