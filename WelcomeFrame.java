import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame implements ActionListener {

        private JFrame frameWelcome;
        private JPanel panelWelcome;
        private JLabel labelWelcome;
        private JButton buttonLoginWelcome;
        private JButton buttonGuestWelcome;

        public WelcomeFrame(){

            frameWelcome = new JFrame("Bienvenidos");
            panelWelcome = new JPanel();
            labelWelcome = new JLabel();
            buttonGuestWelcome = new JButton("Guest");
            buttonLoginWelcome = new JButton("Login");

            frameWelcome.setSize(350, 200);
            frameWelcome.setResizable(false);
            frameWelcome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frameWelcome.setLocationRelativeTo(null);
            frameWelcome.add(panelWelcome);


            labelWelcome.setText("Welcome to Football player manager");
            labelWelcome.setBounds(60 , 22, 300, 25);
            panelWelcome.setLayout(null);
            panelWelcome.add(labelWelcome);


            buttonGuestWelcome.setBounds(48,65,100,25);
            buttonLoginWelcome.setBounds(180, 65,100,25);
            panelWelcome.add(buttonGuestWelcome);
            panelWelcome.add(buttonLoginWelcome);
            buttonLoginWelcome.addActionListener(this::actionPerformed);
            buttonGuestWelcome.addActionListener(this::actionPerformed);







            frameWelcome.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

          if(e.getSource() == buttonGuestWelcome){
              frameWelcome.dispose();
              Object[][] userData = DBManager.getUserData();
              new MainFrame(userData,"user", "Guest");
          }
          else if( e.getSource() == buttonLoginWelcome){
              frameWelcome.dispose();
              new LoginFrame();
        }



    }
}
