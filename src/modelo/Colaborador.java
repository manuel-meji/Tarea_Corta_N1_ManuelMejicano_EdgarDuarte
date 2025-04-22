package modelo;

import java.io.Serializable;

public class Colaborador implements Serializable{
    String nombre;
    float montos;
    String parentesco;


    
    public Colaborador(String nombre, String parentesco) {
        this.nombre = nombre;
        montos=0;
        this.parentesco = parentesco;
    }

    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public float getMontos() {
        return montos;
    }
    public void setMontos(float montos) {
        this.montos += montos;
    }
    public String getParentesco() {
        return parentesco;
    }
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    

}
