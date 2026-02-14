import javax.swing.*;

public class GUIFrame extends JFrame {
    public GUIFrame(){
        GUIPanel panel = new GUIPanel();
        add(panel);

        setTitle("MineSweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
