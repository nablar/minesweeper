package Settings;

import Main.MainFrame;

import javax.swing.*;
import java.awt.*;

public class ColorButton extends JButton {
    private int id;
    private ColorFrame colorFrame;

    public int getId(){
        return id;
    }

    public void updateColor(boolean clicked){
        if((id<10 || id==Options.MINE_COLOR || id==Options.CLICKED_COLOR) && clicked){
            this.setBackground(Options.colorScheme.get(Options.CLICKED_COLOR));
        }
        if(!clicked && (id==Options.FLAG_COLOR || id==Options.UNCLICKED_COLOR)){
            this.setBackground(Options.colorScheme.get(Options.UNCLICKED_COLOR));
        }
    }

    public ColorButton(int id, ColorFrame colorFrame){
        super();
        this.colorFrame = colorFrame;
        this.setPreferredSize(new Dimension(50,50));
        int size;
        if(id<10){size = 20;}
        else{size = 15;}
        this.setFont(new Font(null,Font.BOLD,size));
        this.id=id;
        if(id== Options.CLICKED_COLOR || id==Options.UNCLICKED_COLOR){
            this.setBackground(Options.colorScheme.get(id));
            return;
        }
        this.setBackground(Options.colorScheme.get(Options.CLICKED_COLOR));
        this.setForeground(Options.colorScheme.get(id));
        if(id==Options.FLAG_COLOR){
            this.setBackground(Options.colorScheme.get(Options.UNCLICKED_COLOR));
            this.setText(Options.FLAG_CHARACTER);
        }
        else if(id==Options.MINE_COLOR){
            this.setText(Options.MINE_CHARACTER);
        }
        else{
            this.setText(Integer.toString(id));
        }
    }

    public void setColor(Color color){
        if(id<10 || id==Options.UNCLICKED_COLOR || id==Options.CLICKED_COLOR){this.setBackground(color);}
        else{this.setForeground(color);}
        Options.updateColor(id,color);
        MainFrame.repaintGrid();
        if(id!=Options.UNCLICKED_COLOR && id!=Options.CLICKED_COLOR){
            return;
        }
        boolean clicked = id==Options.CLICKED_COLOR;
        colorFrame.bgChanged(clicked);
    }

    public void setImage(String path){
        if(id==Options.UNCLICKED_COLOR){

        }
    }
}
