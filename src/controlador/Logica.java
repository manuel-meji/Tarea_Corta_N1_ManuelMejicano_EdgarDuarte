package controlador;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
        
            System.out.println("Los datos han sido guardados en FAMILY.dat"); 
        } 
    }

    public void recuperar(){
        try {
            FileInputStream fileIn = new FileInputStream("colaboradores.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            
            in.readObject()
        } catch (Exception e) {
            // TODO: handle exception
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
    
    

   

}
