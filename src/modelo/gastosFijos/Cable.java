package modelo.gastosFijos;

import java.time.LocalDate;
import modelo.Gastos;

public class Cable extends Gastos {

    final static String compañia="Tigo";
    final static int nCliente=673894;

    public Cable() {
        super();
    }

    public Cable(Float monto, LocalDate fecha, String motivo, String mes, String categoria,String año) {
        super(monto, fecha, motivo, mes, categoria,año);
    }

    @Override
    public String toString() {
        return "Cable [monto= " + getMonto() + ", fecha= " + getFecha() + ", motivo= " + getMotivo() + ", mes= " + getMes()
                + ", categoria= " + getTipo() + "]";

    }

    @Override
    public void mostrarAdicionales() {
        System.out.println("No hay adicionales para este gasto.");
    }
}