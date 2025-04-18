package modelo.gastosFijos;

import java.time.LocalDate;
import modelo.Gastos;

public class Electricidad extends Gastos {

   final static int NISE=2129552;
   final static String nombreEmpresa="ICE";

    public Electricidad() {
    }

    public Electricidad(Float monto, LocalDate fecha, String motivo, String mes, String categoria, String año) {
        super(monto, fecha, motivo, mes, categoria,año);
    }

    @Override
    public String toString() {
        return "Electricidad [monto= " + getMonto() + ", fecha= " + getFecha() + ", motivo= " + getMotivo() + ", mes= " + getMes()
                + ", categoria= " + getTipo() + "]";
    }

    @Override
    public void mostrarAdicionales() {
        System.out.println("Electricidad [monto= " + getMonto() + ", fecha= " + getFecha() + ", motivo= " + getMotivo() + ", mes= " + getMes()
                + ", categoria= " + getTipo() + "]");
    }

}
