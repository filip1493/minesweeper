import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIPanel extends JPanel {
    private Engine engine;
    private MojeDugme[][] dugmici;
    private ImageIcon[] ikone = new ImageIcon[13];

    private JLabel lblMine;
    private JLabel lblZastavica;

    private JPanel gridPanel;

    public GUIPanel(){
        loadIcons();
        initTopBottom();
        initGrid();
        newGame();
    }

    private void initTopBottom(){
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        lblMine = new JLabel("Broj mina: 10");
        JButton novaIgra = new JButton("Nova igra");
        novaIgra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        topPanel.add(lblMine);
        topPanel.add(novaIgra);

        JPanel bottomPanel = new JPanel();
        lblZastavica = new JLabel("Broj zastavica: 0");
        bottomPanel.add(lblZastavica);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadIcons(){
        for(int i = 0; i <= 12; i++)
            ikone[i] = new ImageIcon(new ImageIcon("src/resources/" + i + ".png").getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
    }

    private void initGrid(){
        gridPanel = new JPanel(new GridLayout(Engine.size, Engine.size, 0, 0));
        add(gridPanel, BorderLayout.CENTER);
    }

    private void newGame(){
        engine = new Engine();

        gridPanel.removeAll();

        dugmici = new MojeDugme[9][9];

        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                MojeDugme btn = new MojeDugme(i, j);
                btn.setIcon(ikone[10]);

                btn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(e.getButton() == MouseEvent.BUTTON1)
                            openField(btn.row, btn.col);
                        if(e.getButton() == MouseEvent.BUTTON3)
                            toggleFlag(btn.row, btn.col);
                    }
                });

                dugmici[i][j] = btn;
                gridPanel.add(btn);
            }
        }

        updateFlagCount();

        revalidate();
        repaint();
    }

    private void openField(int r, int c){
        if(engine.opened[r][c] || engine.flagged[r][c])
            return;

        engine.opened[r][c] = true;

        if(engine.mines[r][c]){
            loseGame();
            return;
        }

        int minesAround = engine.countMines(r, c);
        dugmici[r][c].setIcon(ikone[minesAround]);

        if(minesAround == 0)
            openNeighbours(r, c);
        if(engine.isWin())
            winGame();
    }

    private void toggleFlag(int r, int c){
        if(engine.opened[r][c])
            return;

        engine.flagged[r][c] = !engine.flagged[r][c];

        if(engine.flagged[r][c])
            dugmici[r][c].setIcon(ikone[11]);
        else
            dugmici[r][c].setIcon(ikone[10]);

        updateFlagCount();
    }

    private void updateFlagCount(){
        int count = 0;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(engine.flagged[i][j])
                    count++;
            }
        }
        lblZastavica.setText("Zastavice: " + count);
    }

    private void openNeighbours(int r, int c){
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                int nr = r + i;
                int nc = c + j;

                if(nr >= 0 && nr < 9 && nc >= 0 && nc < 9)
                    if(!engine.opened[nr][nc] && !engine.flagged[nr][nc])
                        openField(nr, nc);
            }
        }
    }

    private void loseGame(){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(engine.mines[i][j] && !engine.flagged[i][j])
                    dugmici[i][j].setIcon(ikone[9]);

                if(!engine.mines[i][j] && engine.flagged[i][j])
                    dugmici[i][j].setIcon(ikone[12]);
            }
        }
        endDialog("Izgubio si");
    }

    private void winGame(){ endDialog("Pobedio si");}

    private void endDialog(String msg){
        int op = JOptionPane.showConfirmDialog(this, "\nNova igra?", "Kraj igre", JOptionPane.YES_NO_OPTION);

        if(op == JOptionPane.YES_OPTION)
            newGame();
        else
            System.exit(0);
    }
}
