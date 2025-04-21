package modelo.gastosFijos;

import java.time.LocalDate;
import modelo.Gastos;

public class Telefono extends Gastos {

    final static String compañiaTelefonica="Kolbi";
    final static int numeroTelefono= 83654656;

    public Telefono() {
        super();
    }

    public Telefono(Float monto, LocalDate fecha, String motivo, String mes, String tipo, String año) {
        super(monto, fecha, motivo, mes, tipo, año);
    }


    @Override
    public String mostrarAdicionales() {
        String txt = "";
        txt += "Compañia telefonica: " + compañiaTelefonica + "\n";
        txt += "Numero de telefono: " + numeroTelefono + "\n";
        return txt;
    }

}
