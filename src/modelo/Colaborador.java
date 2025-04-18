package modelo;

public class Colaborador {
    String nombre;
    float montos;
    String parentesco;


    
    public Colaborador(String nombre, float montos, String parentesco) {
        this.nombre = nombre;
        this.montos = montos;
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
        this.montos = montos;
    }
    public String getParentesco() {
        return parentesco;
    }
    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    

}
