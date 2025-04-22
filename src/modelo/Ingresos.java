package modelo;

import java.io.Serializable;
import java.time.LocalDate;

public class Ingresos implements Serializable{
    Colaborador colaborador;
    String mes,año;
    LocalDate fecha;
    String metodo;
    float monto;


    
public Ingresos(Colaborador colaborador,String mes,String año,LocalDate fecha, String metodo, float monto) {
        this.colaborador = colaborador;
        this.mes = mes;
        this.año = año;
        this.fecha = fecha;
        this.metodo = metodo;
        this.monto = monto;
    }


    public Colaborador getColaborador() {
        return colaborador;
    }
    public void setColaborador(Colaborador colaborador) {
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
