package modelo.gastosFijos;

import java.time.LocalDate;


import modelo.Gastos;

public class Agua extends Gastos {

   final static String proveedor = "AYA";
   final static String nAbonado = "1234567890";

    public Agua() {
        
    }

@Override
    public void mostrarAdicionales() {
        
        
    }

public Agua(Float monto, LocalDate fecha, String motivo, String mes, String categoria, String año) {
        super(monto, fecha, motivo, mes, categoria,año);
    }

    @Override
    public String toString() {
        return "Agua [monto= " + getMonto() + ", fecha= " + getFecha() + ", motivo= " + getMotivo() + ", mes= " + getMes()
                + ", categoria= " + getTipo() + "]";

    }

     

}
