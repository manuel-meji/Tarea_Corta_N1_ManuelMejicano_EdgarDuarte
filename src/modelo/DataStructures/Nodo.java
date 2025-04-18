package modelo.DataStructures;

public class Nodo <T>{
    public T objeto;
    public Nodo<T> siguiente;


    public Nodo(T objeto, Nodo<T> siguiente) {
        this.objeto = objeto;
        this.siguiente = siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente){
        this.siguiente = siguiente;
    }

    public T getObjeto() {
        return objeto;
    }

    public void setObjeto(T objeto) {
        this.objeto = objeto;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    
}
