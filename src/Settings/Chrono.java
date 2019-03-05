package Settings;

import Main.Main;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Chrono extends JLabel {
    private double time;
    private boolean stop=false;

    public Chrono(){
        super();
        this.setOpaque(false);
        this.setFont(new Font("Calibri",Font.BOLD,20));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        if(!Options.COUNTDOWN_MODE){
            time=0;
            start();
        }
        else{
            time=Options.COUNTDOWN_START;
            startCountdown();
        }
        setTimeText();

        JPopupMenu menu = new JPopupMenu();
        JMenuItem startStop = new JMenuItem("Stop / Resume");
        startStop.addActionListener(e -> stop());
        JMenuItem reset = new JMenuItem("Reset");
        reset.addActionListener(e -> {time = Options.COUNTDOWN_MODE? Options.COUNTDOWN_START:0; setTimeText();});
        menu.add(startStop);
        menu.add(reset);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(SwingUtilities.isRightMouseButton(e)){
                    menu.show(e.getComponent(),e.getX(),e.getY());
                }
            }
        });
    }

    public void stop(){
        stop = !stop;
        if(!stop){
            if(Options.COUNTDOWN_MODE){
                startCountdown();
            }
            else{
                start();
            }
        }
    }

    public String getTime(){
        double time = Options.COUNTDOWN_MODE? Options.COUNTDOWN_START - this.time : this.time;
        String hour = time/3600>=10? Integer.toString((int)time/3600):"0"+(int)time/3600;
        String minute = (time/60)%60>=10? Integer.toString((int) (time/60)%60):"0"+((int)time/60)%60;
        String seconds = Integer.toString((int)time%60)+"s";
        hour = hour.equals("00")? "":hour+"h";
        minute = minute.equals("00")? "":minute+"m";
        return hour+minute+seconds;
    }

    public void start(){
        if(stop){
            time--;
            setTimeText();
        }
        if (!stop) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    time++;
                    setTimeText();
                    Chrono.this.start();
                }
            },1000);
        }
    }

    public void startCountdown(){
        if(stop){
            time++;
            setTimeText();
        }
        if (!stop) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    time--;
                    setTimeText();
                    if(time<=0){
                        time = 0;
                        setTimeText();
                        Main.gameEnded(false,false);
                    }
                    Chrono.this.startCountdown();
                }
            },1000);
        }
    }

    private void setTimeText(){
        String minute = (time/60)%60>=10? Integer.toString((int) (time/60)%60):"0"+((int)time/60)%60;
        String seconds = time%60>=10? Integer.toString((int)time%60):"0"+(int)time%60;
        String hour = time/3600>=10? Integer.toString((int)time/3600):"0"+(int)time/3600;
        hour = hour.equals("00")? "":hour+":";
        this.setText(hour+minute+":"+seconds);
    }
}
