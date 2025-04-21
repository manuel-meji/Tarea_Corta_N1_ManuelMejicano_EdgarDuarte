
import javax.swing.UIManager;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import controlador.Logica;

public class App {

    public static void main(String[] args) throws Exception {

        try {
           UIManager.setLookAndFeel(new FlatMacLightLaf());

        } catch (Exception ex) {
            System.err.println("Algo no salió bien");
        }
        // Se inicializa la clase Logica, que es la encargada de manejar la logica del programa
        // y de interactuar con la vista y el modelo
        Logica logica = new Logica();

       

    }
}
