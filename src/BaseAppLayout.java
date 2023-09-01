import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaseAppLayout extends JFrame {
    protected JLabel headingLabel;
    protected JPanel bodyPanel;
    protected JPanel btnsPanel;

    protected JButton clock;
    protected JButton alarm;
    protected JButton timer;
    protected JButton stopwatch;

    protected Image clockIcon;
    protected Image alarmIcon;
    protected Image timerIcon;
    protected Image stopwatchIcon;


    public BaseAppLayout(){
        new JFrame ();
        setLocationRelativeTo(null);
        pack();
        this.setTitle("Clock Application");
        setLayout(null);
        setVisible(false);
        setResizable(false);
        setSize(380,500);
        bodyPanel= new JPanel();
        bodyPanel.setLayout(null);
        bodyPanel.setBounds(0,0,380,450);
        bodyPanel.setBackground(new Color(26,26,26));

        headingLabel= new JLabel("");
        headingLabel.setFont(new Font("Roboto",Font.PLAIN,17));
        headingLabel.setBounds(17,0,100,40);
        headingLabel.setForeground(Color.WHITE);
        bodyPanel.add(headingLabel);

        btnsPanel= new JPanel();
        btnsPanel.setLayout(null);
        btnsPanel.setBounds(0,414,380,50);
        btnsPanel.setBackground(Color.darkGray);

        clock = new JButton("Clock");
        alarm = new JButton("Alarm");
        timer = new JButton("Timer");
        stopwatch = new JButton("Stopwatch");

        //adding buttons to the buttons panel.
        btnsPanel.add(clock);
        btnsPanel.add(alarm);
        btnsPanel.add(timer);
        btnsPanel.add(stopwatch);

        clock.setForeground(Color.WHITE);
        alarm.setForeground(Color.WHITE);
        timer.setForeground(Color.WHITE);
        stopwatch.setForeground(Color.WHITE);

        clockIcon = new  ImageIcon(this.getClass().getResource("clockIcon.png")).getImage();
        clock.setIcon(new ImageIcon(clockIcon));
        clock.setVerticalTextPosition(SwingConstants.BOTTOM);
        clock.setHorizontalTextPosition(SwingConstants.CENTER);
        clock.setFont(new Font("Roboto",Font.PLAIN,12));
        clock.setContentAreaFilled(false);
        clock.setBorderPainted(false);
        clock.setBounds(0,4,80,40);

        alarmIcon = new  ImageIcon(this.getClass().getResource("alarmIcon.png")).getImage();
        alarm.setIcon(new ImageIcon(alarmIcon));
        alarm.setVerticalTextPosition(SwingConstants.BOTTOM);
        alarm.setHorizontalTextPosition(SwingConstants.CENTER);
        alarm.setFont(new Font("Roboto",Font.PLAIN,12));
        alarm.setContentAreaFilled(false);
        alarm.setBorderPainted(false);
        alarm.setBounds(90,4,80,40);

        timerIcon = new  ImageIcon(this.getClass().getResource("timerIcon.png")).getImage();
        timer.setIcon(new ImageIcon(timerIcon));
        timer.setVerticalTextPosition(SwingConstants.BOTTOM);
        timer.setHorizontalTextPosition(SwingConstants.CENTER);
        timer.setFont(new Font("Roboto",Font.PLAIN,12));
        timer.setContentAreaFilled(false);
        timer.setBorderPainted(false);
        timer.setBounds(180,4,80,40);

        stopwatchIcon = new  ImageIcon(this.getClass().getResource("stopwatchIcon.png")).getImage();
        stopwatch.setIcon(new ImageIcon(stopwatchIcon));
        stopwatch.setVerticalTextPosition(SwingConstants.BOTTOM);
        stopwatch.setHorizontalTextPosition(SwingConstants.CENTER);
        stopwatch.setFont(new Font("Roboto",Font.PLAIN,12));
        stopwatch.setContentAreaFilled(false);
        stopwatch.setBorderPainted(false);
        stopwatch.setBounds(270,4,100,40);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(btnsPanel);
        add(bodyPanel);

        this.clock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Clock main= new Clock();
            }
        });
        this.alarm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Alarm main= new Alarm();
            }
        });
        this.timer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Timer main= new Timer();
            }
        });
        this.stopwatch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Stopwatch main= new Stopwatch();
            }
        });
    }

}
