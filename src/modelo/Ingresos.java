package modelo;

import java.time.LocalDate;

public class Ingresos {
    String colaborador,mes,año;
    LocalDate fecha;
    String metodo;
    float monto;


    
public Ingresos(String colaborador,String mes,String año,LocalDate fecha, String metodo, float monto) {
        this.colaborador = colaborador;
        this.mes = mes;
        this.año = año;
        this.fecha = fecha;
        this.metodo = metodo;
        this.monto = monto;
    }


    public String getColaborador() {
        return colaborador;
    }
    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public String getMetodo() {
        return metodo;
    }
    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }
    public float getMonto() {
        return monto;
    }
    public void setMonto(float monto) {
        this.monto = monto;
    }


    public String getMes() {
        return mes;
    }


    public void setMes(String mes) {
        this.mes = mes;
    }


    public String getAño() {
        return año;
    }


    public void setAño(String año) {
        this.año = año;
    }
    

    
}
