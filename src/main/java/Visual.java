import additions.Window;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Visual extends Component implements ActionListener {
    private Visual(){
        Window win = new Window();
        win.initComponents();
    }

    public static  void main(String[] args){
        SwingUtilities.invokeLater(Visual::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
