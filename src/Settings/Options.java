package Settings;

import Main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Options {
    public static HashMap<Integer, Color> colorScheme = new HashMap<>();
    public static JMenuBar menuBar = new JMenuBar();
    private static Component c;

    public static final int FLAG_COLOR = 233;
    public static final int MINE_COLOR = 234;
    public static final int UNCLICKED_COLOR = 235;
    public static final int CLICKED_COLOR = 236;

    public static String FLAG_CHARACTER="\uD83D\uDEA9";
    public static String MINE_CHARACTER="\uD83D\uDCA3";


    public static int NUMBER_OF_MINES = 12;
    public static int ROWS = 10;
    public static int COLUMNS = 10;
    public static boolean COUNTDOWN_MODE;
    public static double COUNTDOWN_START=180;

    private static boolean easyModeOn = false;
    private static int hintsUsed = 0;

    public static void init(){
        colorScheme.put(0,null);
        colorScheme.put(1,Color.blue);
        colorScheme.put(2,new Color(87, 188, 50));
        colorScheme.put(3,Color.red);
        colorScheme.put(4,Color.magenta);
        colorScheme.put(5,Color.cyan);
        colorScheme.put(6,Color.orange);
        colorScheme.put(7,Color.gray);
        colorScheme.put(8,Color.black);
        colorScheme.put(FLAG_COLOR,Color.red);
        colorScheme.put(MINE_COLOR,Color.red);
        colorScheme.put(UNCLICKED_COLOR,new Color(130, 207, 255));
        colorScheme.put(CLICKED_COLOR,Color.LIGHT_GRAY);

        JMenu options = new JMenu("Options");
        JMenu cheats = new JMenu("");

        JMenuItem params = new JMenuItem("Change parameters", KeyEvent.VK_C);
        params.getAccessibleContext().setAccessibleDescription("Change the number of mines, lines, columns and colors.");
        params.addActionListener(e -> new OptionsFrame(c));
        options.add(params);

        JMenuItem description = new JMenuItem("You found the hidden cheats !");
        description.setEnabled(false);
        cheats.add(description);
        cheats.addSeparator();

        JMenuItem hint = new JMenuItem("Get a hint",KeyEvent.VK_H);
        hint.getAccessibleContext().setAccessibleDescription("Put a flag on an undiscovered mine box");
        hint.addActionListener(e -> {
            Main.giveHint();
            hintsUsed++;
            if(hintsUsed>=Options.NUMBER_OF_MINES){
                hint.setEnabled(false);
            }
        });
        cheats.add(hint);

        JMenuItem easy = new JMenuItem("Toggle super easy mode",KeyEvent.VK_S);
        easy.getAccessibleContext().setAccessibleDescription("Change the color of mine boxes");
        easy.addActionListener(e -> {
            easyModeOn=!easyModeOn;
            Main.superEasyMode(easyModeOn);
        });
        cheats.add(easy);


        JMenuItem reveal = new JMenuItem("Reveal grid",KeyEvent.VK_R);
        reveal.getAccessibleContext().setAccessibleDescription("Show all the mines and numbers (you cannot continue playing after this)");
        reveal.addActionListener(e -> Main.gameEnded(true,true));
        cheats.add(reveal);

        menuBar.add(options);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(cheats);
    }

    public static void updateColor(int id, Color color){
        colorScheme.replace(id,color);
    }


    public static void setNbrMines(int n){
        if(n<Options.ROWS*Options.COLUMNS){
            Options.NUMBER_OF_MINES = n;
            return;
        }
        Options.NUMBER_OF_MINES = Options.ROWS*Options.COLUMNS - 1;
    }

    public static void setC(Component c) {
        Options.c = c;
    }
}
