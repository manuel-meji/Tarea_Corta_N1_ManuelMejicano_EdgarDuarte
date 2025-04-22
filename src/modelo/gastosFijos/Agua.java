package modelo.gastosFijos;


import java.time.LocalDate;


import modelo.Gastos;

public class Agua extends Gastos  {

   final static String proveedor = "AYA";
   final static String nAbonado = "1234567890";


@Override
    public String mostrarAdicionales() {
        String txt = "";
        txt += "Proveedor: " + proveedor + "\n";
        txt += "Número de abonado: " + nAbonado + "\n";
        return txt;
    }

public Agua(Float monto, LocalDate fecha, String motivo, String mes, String categoria, String año) {
        super(monto, fecha, motivo, mes, categoria,año);
    }

        

}
