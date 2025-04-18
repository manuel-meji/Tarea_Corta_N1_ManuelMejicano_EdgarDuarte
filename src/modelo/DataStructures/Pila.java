package modelo.DataStructures;

public class Pila {

    protected int contador = 0;
    private Nodo<String> superior;

    public Pila() {
        superior = null;
    }

    public void apilar(String objeto) {
        Nodo<String> nodoNuevo = new Nodo<String>(objeto, null);

        if (superior == null) {
            superior = nodoNuevo;
        } else {
            nodoNuevo.setSiguiente(superior);
            superior = nodoNuevo;
        }
        contador++;
    }

    public String leer() {
        if (superior == null) {
            return null;
        }
        String objeto = superior.getObjeto();
        superior = superior.getSiguiente();
        return objeto;

    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    

}
