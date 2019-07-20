package additions;

import javax.swing.*;

public class Help {


    public void init(){
        JFrame form2 = new JFrame("ПРАВИЛА ПОЛЬЗОВАНИЯ");
        form2.setBounds(100,100,252,200);
        form2.setVisible(true);
        form2.setResizable(false);

        JLabel example1 = new JLabel();
        example1.setText("Правила записываются в форме:");
        example1.setVisible(true);
        example1.setSize(270,25);
        example1.setLocation(5, 2);
        form2.getContentPane().add(example1);
        form2.getContentPane().add(new JLabel());

    }
}
