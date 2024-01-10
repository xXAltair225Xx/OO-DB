import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameCheck{

    private static JFrame frame;
    private static JPanel panel;
    private static JButton button;
    private static JLabel label;

    public FrameCheck() {
        frame = new JFrame();
        panel = new JPanel();
        label = new JLabel("Success!");
        button= new JButton("Ok");



        frame.setSize(200, 150);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        panel.setLayout(null);

        label.setBounds(70,20,60,20);
        button.setBounds(65,55,60,20);

        frame.add(panel);
        panel.add(label);
        panel.add(button);




        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });


        frame.setVisible(true);



    }
}
