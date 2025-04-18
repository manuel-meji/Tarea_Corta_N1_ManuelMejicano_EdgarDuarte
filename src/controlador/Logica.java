package controlador;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import modelo.Colaborador;
import modelo.Gastos;
import modelo.Ingresos;
import vista.Vista;

public class Logica {
   public ArrayList<Colaborador> colaboradores = new ArrayList<>();
   public ArrayList<Ingresos> ingresos = new ArrayList<>();
   public ArrayList<Gastos> gastos = new ArrayList<>();
   public float totalGastos = 0;
   public float totalIngresos = 0;
   public float Balance = 0;
   
    
    //Clase de vista
    Vista vista = new Vista(this);

    public Logica() {
        vista.setVisible(true);

    }


    public void guardar(){
        try {
            // Guardar los colaboradores en un archivo
            FileOutputStream fileOut = new FileOutputStream("colaboradores.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(colaboradores);
            out.close();
            fileOut.close();
            
            // Guardar los ingresos en un archivo
            fileOut = new FileOutputStream("ingresos.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(ingresos);
            out.close();
            fileOut.close();
            
            // Guardar los gastos en un archivo
            fileOut = new FileOutputStream("gastos.ser");
            out = new ObjectOutputStream(fileOut);
            out.writeObject(gastos);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    

   

}
