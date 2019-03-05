package Settings;

import javax.swing.*;
import java.awt.*;

public class ColorFrame extends JFrame {
    private JLabel label1 = new JLabel("\tColor for 1:");
    private ColorButton button1 = new ColorButton(1, this);

    private JLabel label2 = new JLabel("\tColor for 2:");
    private ColorButton button2 = new ColorButton(2, this);

    private JLabel label3 = new JLabel("\tColor for 3:");
    private ColorButton button3 = new ColorButton(3, this);

    private JLabel label4 = new JLabel("\tColor for 4:");
    private ColorButton button4 = new ColorButton(4, this);

    private JLabel label5 = new JLabel("\tColor for 5:");
    private ColorButton button5 = new ColorButton(5, this);

    private JLabel label6 = new JLabel("\tColor for 6:");
    private ColorButton button6 = new ColorButton(6, this);

    private JLabel label7 = new JLabel("\tColor for 7:");
    private ColorButton button7 = new ColorButton(7, this);

    private JLabel label8 = new JLabel("\tColor for 8:");
    private ColorButton button8 = new ColorButton(8, this);

    private JLabel labelflag = new JLabel("Color for flags:");
    private ColorButton buttonflag = new ColorButton(Options.FLAG_COLOR, this);

    private JLabel labelmine = new JLabel("Color for mines:");
    private ColorButton buttonmine = new ColorButton(Options.MINE_COLOR, this);

    private JLabel labelclicked = new JLabel("Clicked boxes:");
    private ColorButton buttonclicked = new ColorButton(Options.CLICKED_COLOR, this);

    private JLabel labelunclicked = new JLabel("Unclicked boxes:");
    private ColorButton buttonunclicked = new ColorButton(Options.UNCLICKED_COLOR, this);

    public ColorFrame(Component parent){
        addActions();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JPanel panel12 = new JPanel();
        panel12.add(label1);
        panel12.add(button1);
        panel.add(panel12);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));
        panel12.add(label2);
        panel12.add(button2);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));

        JPanel panel34 = new JPanel();
        panel34.add(label3);
        panel34.add(button3);
        panel.add(panel34);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));
        panel34.add(label4);
        panel34.add(button4);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));

        JPanel panel56 = new JPanel();
        panel56.add(label5);
        panel56.add(button5);
        panel.add(panel56);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));
        panel56.add(label6);
        panel56.add(button6);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));

        JPanel panel78 = new JPanel();
        panel78.add(label7);
        panel78.add(button7);
        panel.add(panel78);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));
        panel78.add(label8);
        panel78.add(button8);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));

        JPanel panelflagmine = new JPanel();
        panelflagmine.add(labelflag);
        panelflagmine.add(buttonflag);
        panel.add(panelflagmine);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));
        panelflagmine.add(labelmine);
        panelflagmine.add(buttonmine);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));

        JPanel panelclick = new JPanel();
        panelclick.add(labelunclicked);
        panelclick.add(buttonunclicked);
        panelclick.add(labelclicked);
        panelclick.add(buttonclicked);
        panel.add(panelclick);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));

        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void addAct(ColorButton b){
        b.addActionListener(e -> {
            Color color;
            if(b.getId()==Options.FLAG_COLOR || b.getId()==Options.MINE_COLOR){
                color = b.getForeground();
            }
            else{color=b.getBackground();}
            Color pickedColor = JColorChooser.showDialog(null,"Change color",color);
            b.setColor(pickedColor);
        });
    }

    private void addActions(){
        addAct(button1);
        addAct(button2);
        addAct(button3);
        addAct(button4);
        addAct(button5);
        addAct(button6);
        addAct(button7);
        addAct(button8);
        addAct(buttonflag);
        addAct(buttonmine);
        addAct(buttonclicked);
        addAct(buttonunclicked);
    }

    public void bgChanged(boolean clicked){
        if(clicked){
            button1.updateColor(clicked);
            button2.updateColor(clicked);
            button3.updateColor(clicked);
            button4.updateColor(clicked);
            button5.updateColor(clicked);
            button6.updateColor(clicked);
            button7.updateColor(clicked);
            button8.updateColor(clicked);
            buttonmine.updateColor(clicked);
        }
        else{
            buttonflag.updateColor(!clicked);
        }
    }
}
