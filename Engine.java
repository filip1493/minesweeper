import java.util.Random;

public class Engine {
    public static final int size = 9;
    public static final int mine = 10;

    public boolean[][] mines = new boolean[size][size];
    public boolean[][] flagged = new boolean[size][size];
    public boolean[][] opened = new boolean[size][size];

    public Engine(){ placeMines();}

    private void placeMines(){
        Random r = new Random();
        int placed = 0;

        while(placed < mine){
            int i = r.nextInt(size);
            int j = r.nextInt(size);

            if(!mines[i][j]){
                mines[i][j] = true;
                placed++;
            }
        }
    }

    public int countMines(int r, int c){
        int cnt = 0;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                int nr = r + i;
                int nc = c + j;
                if(nr >= 0 && nr < size && nc >= 0 && nc < size)
                    if(mines[nr][nc])
                        cnt++;
            }
        }
        return cnt;
    }

    public boolean isWin(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(!mines[i][j] && !opened[i][j])
                    return false;
            }
        }
        return true;
    }
}
