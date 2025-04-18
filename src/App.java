
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

        Logica logica = new Logica();

       

    }
}
