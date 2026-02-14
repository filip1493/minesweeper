import javax.swing.*;
import java.awt.*;

public class MojeDugme extends JButton {
    public int row, col;

    public MojeDugme(int row, int col) {
        this.row = row;
        this.col = col;
        setPreferredSize(new Dimension(32, 32));
        setBorderPainted(false);
        setFocusPainted(false);
    }
}
