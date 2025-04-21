package modelo;


import java.time.LocalDate;

public class GastosVarios extends Gastos {

     String categoria;

    public GastosVarios() {
        super();
    }

    

  

    public GastosVarios(String categoria) {
        this.categoria = categoria;
    }





    public GastosVarios(Float monto, LocalDate fecha, String motivo, String mes, String tipo, String año,
            String categoria) {
        super(monto, fecha, motivo, mes, tipo, año);
        this.categoria = categoria;
    }




    @Override
    public String mostrarAdicionales() {
        String txt = "";
        txt += "Categoria de gasto: " + categoria + "\n";
        return txt;
    }

}
