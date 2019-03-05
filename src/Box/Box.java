package Box;

import Main.Main;
import Settings.Options;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Box extends JButton {
    public static final int NORMAL_BOX=0;
    public static final int MINE_BOX=1;

    private boolean alreadyClicked = false;
    private int type = 0;
    private int nbrMinesAround = 0;
    private Point coordinates;
    private ArrayList<Box> neighbours;
    private boolean flagged;

    public Box(int x, int y){
        coordinates = new Point(x,y);
        this.setBackground(Options.colorScheme.get(Options.UNCLICKED_COLOR));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(alreadyClicked){return;}
                if(SwingUtilities.isLeftMouseButton(e)){
                    Box.this.leftClick();
                }
                else if(SwingUtilities.isRightMouseButton(e)){
                    Box.this.setFlag();
                }
                else if(SwingUtilities.isMiddleMouseButton(e)){
                    Box.this.setFlag();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e){if(SwingUtilities.isLeftMouseButton(e)){this.mouseClicked(e);}}
        });

        int size = Math.min(this.getSize().width,this.getSize().height)*2/3;
        this.setFont(new Font(null,Font.BOLD,size));

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                int size = Math.min(Box.this.getSize().width,Box.this.getSize().height)*2/3;
                Box.this.setFont(new Font(null,Font.BOLD,size));
            }
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });
    }


    public boolean wasFlagged(){
        return flagged;
    }

    private boolean reveal;
    public void setReveal(Boolean b){
        reveal=b;
    }

    public void verifyColor(){
        if(flagged){
            this.setForeground(Options.colorScheme.get(Options.FLAG_COLOR));
            this.setBackground(Options.colorScheme.get(Options.UNCLICKED_COLOR));
        }
        else if(alreadyClicked){
            this.setForeground(Options.colorScheme.get(nbrMinesAround));
            this.setBackground(Options.colorScheme.get(Options.CLICKED_COLOR));
        }
        else{
            this.setBackground(Options.colorScheme.get(Options.UNCLICKED_COLOR));
        }
        this.repaint();
    }

    public void leftClick(){
        this.alreadyClicked = true;
        if(this.type==MINE_BOX){
            this.setText(Options.MINE_CHARACTER);
            this.setForeground(Options.colorScheme.get(Options.MINE_COLOR));
            if(!reveal){Main.gameEnded(false,false);}
        }
        else{
            if(!reveal){Main.boxFound();}
            String toWrite="";
            if(nbrMinesAround==0){
                if(!reveal) {
                    for (Box box : neighbours) {
                        if (isNeighbour(box) && box.getType() == Box.NORMAL_BOX && !box.wasClicked()) {
                            box.leftClick();
                        }
                    }
                }
                this.setText(toWrite);
                this.setBackground(Options.colorScheme.get(Options.CLICKED_COLOR));
                return;
            }
            toWrite = Integer.toString(nbrMinesAround);
            this.setText(toWrite);
            this.setForeground(Options.colorScheme.get(nbrMinesAround));
        }
        this.setBackground(Options.colorScheme.get(Options.CLICKED_COLOR));
    }

    public boolean wasClicked(){
        return alreadyClicked;
    }

    public void setFlag(){
        flagged = !flagged;
        if(flagged)
        {
            this.setForeground(Options.colorScheme.get(Options.FLAG_COLOR));
            this.setText(Options.FLAG_CHARACTER);
        }
        else{this.setText("");}
    }


    public Box setType(int type){
        if(type>1 || type<0){return this;}
        this.type=type;
        return this;
    }

    public int getType(){
        return type;
    }

    public Point getCoordinates(){
        return coordinates;
    }

    public int getNbrMinesAround() {
        return nbrMinesAround;
    }

    private boolean isNeighbour(Box box){
        double distance = Math.sqrt((coordinates.x-box.getCoordinates().x)*(coordinates.x-box.getCoordinates().x)+(coordinates.y-box.getCoordinates().y)*(coordinates.y-box.getCoordinates().y));
        return !(distance >= 2);
    }

    public void findMinesNeighbour(ArrayList<Box> grid){
        this.neighbours = grid;
        int minesFound = 0;
        for(Box box : grid){
            if(!isNeighbour(box)){continue;}
            minesFound = box.getType()==Box.MINE_BOX? minesFound+1 : minesFound;
        }
        this.nbrMinesAround = minesFound;
    }
}
