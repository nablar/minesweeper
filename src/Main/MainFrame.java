package Main;

import Settings.Options;
import Box.Box;
import Settings.Chrono;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    public Chrono chrono;

    private void centreWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dimension.width - this.getSize().width) / 2;
        int y = (dimension.height - this.getSize().height) / 2;
        this.setLocation(x, y);
    }

    private static ArrayList<Box> grid;
    public ArrayList<Box> getGrid(){return grid;}

    public MainFrame(){
        this.setJMenuBar(Options.menuBar);
        Options.setC(this);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(chrono = new Chrono(),BorderLayout.NORTH);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(Options.ROWS,Options.COLUMNS));
        this.getContentPane().add(panel,BorderLayout.CENTER);

        ArrayList<Integer> mines = Main.getRandomMinesIndices();
        Main.boxesLeft=Options.ROWS*Options.COLUMNS - Options.NUMBER_OF_MINES;
        System.out.println("======= GRID =======");
        for(int x=0;x<Options.ROWS;x++){
            for(int y=0;y<Options.COLUMNS;y++){
                int type = mines.contains(x*Options.ROWS+y)? Box.MINE_BOX: Box.NORMAL_BOX;
                panel.add(new Box(x,y).setType(type));
                String towrite = type==1? Options.FLAG_CHARACTER:"⬜";
                System.out.print(towrite+" ");
            }
            System.out.println();
        }
        Component[] compGrid = panel.getComponents();
        grid = new ArrayList<>();
        for(Component c:compGrid){
            grid.add((Box)c);
        }
        for(Component box : grid){
            ((Box) box).findMinesNeighbour(grid);
        }
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(new Dimension(2*dimension.width/3,2*dimension.height/3));
        this.pack();
        centreWindow();
        this.setVisible(true);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if(MainFrame.this.noPopupDialog()){
                    return;
                }
                if (JOptionPane.showConfirmDialog(MainFrame.this,
                        "Are you sure you want to quit ?", "Stop playing ?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
                else{
                    MainFrame.this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    private boolean noPopupDialog(){
        return Main.aboutToRestart;
    }

    public static void repaintGrid(){
        for(Box b : grid){
            b.verifyColor();
        }
    }
}
