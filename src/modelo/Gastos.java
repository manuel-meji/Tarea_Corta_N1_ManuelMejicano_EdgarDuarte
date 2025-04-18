package modelo;

import java.time.LocalDate;

public abstract class Gastos {

    protected Float monto;
    protected LocalDate fecha;
    protected String motivo, mes,año;

    protected String tipo;

    public Gastos() {

    }

public Gastos(Float monto, LocalDate fecha, String motivo, String mes,String tipo, String año) {
        this.monto = monto;
        this.fecha = fecha;
        this.motivo = motivo;
        this.mes = mes;
        this.tipo = tipo;
        this.año = año;
    }

    public abstract void mostrarAdicionales();
    

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

}
