import javax.swing.JFrame;

public class Main {
     public static void main(String[] args) {

          // Inizializza il gestore del database e carica le credenziali
          DBManager.loadAdminCredentials();
          DBManager.loadPlayerCredentials();

          // Crea l'istanza di MainFrame
          WelcomeFrame welcomeFrame = new WelcomeFrame();

     }

}