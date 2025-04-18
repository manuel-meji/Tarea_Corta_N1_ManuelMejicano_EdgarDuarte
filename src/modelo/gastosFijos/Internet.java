package modelo.gastosFijos;

import java.time.LocalDate;
import modelo.Gastos;

public class Internet extends Gastos {

    final static String proveedor = "Starlink";
    final static int megas = 300;

    public Internet() {
        super();
    }

    public Internet(Float monto, LocalDate fecha, String motivo, String mes, String categoria, String año) {
        super(monto, fecha, motivo, mes, categoria, año);
    }

    @Override
    public String toString() {
        return "Internet [monto= " + getMonto() + ", fecha= " + getFecha() + ", motivo= " + getMotivo() + ", mes= "
                + getMes()
                + ", categoria= " + getTipo() + "]";
    }

    @Override
    public void mostrarAdicionales() {
        System.out.println("No hay adicionales para este gasto fijo.");
    }

    @Override
    public String getTipo() {
        return "Internet";
    }
}
