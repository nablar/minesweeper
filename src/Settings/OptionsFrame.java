package Settings;

import Main.MainFrame;
import Main.Main;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

public class OptionsFrame extends JFrame {
    private JPanel panel;

    private JTextField columnsField;
    private JLabel columnsLabel = new JLabel("Number of columns:");

    private JTextField rowsField;
    private JLabel rowsLabel = new JLabel("Number of rows:");

    private JTextField minesField;
    private JLabel minesLabel = new JLabel("Number of mines:");

    private JButton colorButton = new JButton("Change the colors");

    private JButton okButton = new JButton("Restart game");
    private JButton cancelButton = new JButton("Cancel");

    private JSpinner hourSpinner;
    private JSpinner minuteSpinner;
    private JSpinner secondSpinner;


    public OptionsFrame(Component parent){
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        columnsField = new JTextField(Integer.toString(Options.COLUMNS));
        rowsField = new JTextField(Integer.toString(Options.ROWS));
        minesField = new JTextField(Integer.toString(Options.NUMBER_OF_MINES));
        this.setPreferredSize(new Dimension(280,330));
        columnsField.setPreferredSize(new Dimension(50,20));
        rowsField.setPreferredSize(new Dimension(50,20));
        minesField.setPreferredSize(new Dimension(50,20));

        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                applyChanges();
            }
        });
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                Main.startStopChrono();
                OptionsFrame.this.setVisible(false);
            }
        });

        JPanel columnsPanel = new JPanel();
        columnsPanel.add(columnsLabel);
        columnsPanel.add(columnsField);
        panel.add(columnsPanel);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));
        JPanel rowsPanel = new JPanel();
        rowsPanel.add(rowsLabel);
        rowsPanel.add(rowsField);
        panel.add(rowsPanel);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));
        JPanel minesPanel = new JPanel();
        minesPanel.add(minesLabel);
        minesPanel.add(minesField);
        panel.add(minesPanel);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));


        JPanel timePanel = new JPanel();

        JPanel countdownPanel = new JPanel();
        countdownPanel.add(new JLabel("Countdown instead of timer:"));
        JCheckBox countdown = new JCheckBox();
        countdown.setSelected(Options.COUNTDOWN_MODE);
        countdown.addItemListener(e -> {
            Options.COUNTDOWN_MODE=e.getStateChange()==ItemEvent.SELECTED;
            timePanel.setVisible(Options.COUNTDOWN_MODE);

        });
        countdownPanel.add(countdown);
        panel.add(countdownPanel);

        hourSpinner = new JSpinner();
        minuteSpinner = new JSpinner();
        secondSpinner = new JSpinner();
        hourSpinner.setModel(new SpinnerNumberModel(0, 0, 99, 1));
        minuteSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
        secondSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));

        timePanel.add(new JLabel("Countdown:"));
        timePanel.add(hourSpinner);
        timePanel.add(new JLabel("h"));
        timePanel.add(minuteSpinner);
        timePanel.add(new JLabel("m"));
        timePanel.add(secondSpinner);
        timePanel.add(new JLabel("s"));
        timePanel.setVisible(Options.COUNTDOWN_MODE);
        panel.add(timePanel);

        JPanel colorPanel = new JPanel();
        colorButton.addActionListener(e -> new ColorFrame(this));
        colorPanel.add(colorButton);
        panel.add(colorPanel);
        panel.add(javax.swing.Box.createRigidArea(new Dimension(0,10)));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);
        panel.add(buttonsPanel);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
//        this.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowOpened(WindowEvent e) {
//                Main.startStopChrono();
//            }
//        });
    }

    public MainFrame getFrame(){
        return new MainFrame();
    }

    public void applyChanges(){
        try{
            Options.COLUMNS = Integer.parseInt(columnsField.getText())%200;
            Options.ROWS = Integer.parseInt(rowsField.getText())%200;
            Options.setNbrMines(Integer.parseInt(minesField.getText()));
            Options.COUNTDOWN_START=((int)hourSpinner.getModel().getValue())*3600+((int)minuteSpinner.getModel().getValue())*60+((int)secondSpinner.getModel().getValue());
            Main.reset();
            this.setVisible(false);
        } catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Please use valid values !","Wrong values passed", JOptionPane.ERROR_MESSAGE);
        }
    }


}
