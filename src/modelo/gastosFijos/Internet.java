package modelo.gastosFijos;

import java.time.LocalDate;
import modelo.Gastos;

public class Internet extends Gastos {

    final static String proveedor = "Starlink";
    final static int megas = 300;


    @Override
    public String mostrarAdicionales() {
        String txt = "";
        txt += "Proveedor: " + proveedor + "\n";
        txt += "Megas contradas: " + megas + "\n";
        return txt;
    }

    
    public Internet(Float monto, LocalDate fecha, String motivo, String mes, String categoria, String año) {
        super(monto, fecha, motivo, mes, categoria, año);
    }

    @Override
    public String getTipo() {
        return "Internet";
    }
}
