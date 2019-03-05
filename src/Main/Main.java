package Main;

import Settings.Options;

import javax.swing.*;
import Box.Box;

import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class Main extends JFrame {
    private static MainFrame frame;
    public static int boxesLeft;
    public static boolean aboutToRestart = false;



    public static void gameEnded(boolean victory, boolean cheated){
        frame.chrono.stop();
        if(cheated){
            String mine = Options.MINE_CHARACTER;
            Options.MINE_CHARACTER = Options.FLAG_CHARACTER;
            revealGrid();
            int dialogResult = JOptionPane.showConfirmDialog(frame, "You won ! Well, kinda... Play again ?", "Congratulations !",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(dialogResult == 0) {
                reset();
                Options.MINE_CHARACTER = mine;
                return;
            }
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            return;
        }
        if(victory){
            String mine = Options.MINE_CHARACTER;
            Options.MINE_CHARACTER = Options.FLAG_CHARACTER;
            revealGrid();
            int dialogResult = JOptionPane.showConfirmDialog(frame, "You won in "+frame.chrono.getTime()+" ! Play again ?", "Congratulations !",JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(dialogResult == 0) {
                reset();
                Options.MINE_CHARACTER = mine;
                return;
            }
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            return;
        }
        revealGrid();
        int dialogResult = JOptionPane.showConfirmDialog(frame, "You lost ! Play again ?", "Sorry...", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        if(dialogResult == 0) {
            reset();
            return;
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }

    public static void startStopChrono(){
        frame.chrono.stop();
    }

    public static void main(String[] args){
        new Main();
    }

    public Main(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(false);
        if(frame==null)
        {Options.init();}
        frame = new MainFrame();
    }

    public static void revealGrid(){
        for(Box b:frame.getGrid()){
            b.setReveal(true);
            b.leftClick();
        }
    }

    public static void reset(){
        aboutToRestart = true;
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        main(null);
        aboutToRestart=false;
    }

    public static void giveHint() {
        for (Box b : frame.getGrid()) {
            if (b.getType() == Box.MINE_BOX) {
                if (!b.wasFlagged()) {
                    b.setFlag();
                    break;
                }
            }
        }
    }

    public static void superEasyMode(boolean on){
        for(Box b:frame.getGrid()){
            if(b.getType()==Box.MINE_BOX){
                if(on){
                    b.setBackground(Options.colorScheme.get(Options.MINE_COLOR));
                }
                else{
                    b.setBackground(Options.colorScheme.get(Options.UNCLICKED_COLOR));
                }
            }
        }
    }

    public static ArrayList<Integer> getRandomMinesIndices(){
        ArrayList<Integer> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < Options.NUMBER_OF_MINES; i++)
        {
            int r = random.nextInt(Options.ROWS*Options.COLUMNS);
            while(list.contains(r)){
                r = random.nextInt(Options.ROWS*Options.COLUMNS);
            }
            list.add(r);
        }
        return list;
    }


    public static void boxFound(){
        if(--boxesLeft==0){
            gameEnded(true,false);
        }
    }
}
