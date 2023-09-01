import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;


public class Alarm extends BaseAppLayout{
    protected Image alarmSelectedIcon;
    protected JButton addAlarmButton;
    protected Image addAlarmIcon;

    protected ArrayList<JLabel> alarmsLabel=new ArrayList<JLabel>();
    protected ArrayList<JLabel> alarmsTimeLabel=new ArrayList<JLabel>();

    static int y_axis_bounds=60;

    public Alarm(){
        super();
        setVisible(true);
        this.headingLabel.setText("Alarm");
        alarmSelectedIcon = new ImageIcon(this.getClass().getResource("alarmSelectedIcon.png")).getImage();
        this.alarm.setForeground(new Color(0x00E499));
        this.alarm.setIcon(new ImageIcon(alarmSelectedIcon));
        addAlarmButton = new JButton("");
        addAlarmIcon = new ImageIcon(this.getClass().getResource("newClockIcon.png")).getImage();
        addAlarmButton.setIcon(new ImageIcon(addAlarmIcon));
        addAlarmButton.setContentAreaFilled(false);
        addAlarmButton.setBorderPainted(false);
        addAlarmButton.setBounds(135,335,100,40);

        String[] hours = new String[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = String.format("%02d", i);
        }

        String[] minutes = new String[60];
        for (int i = 0; i < 60; i++) {
            minutes[i] = String.format("%02d", i);
        }
        String[] seconds = minutes;

        JComboBox<String> minutesCBox= new JComboBox<String>(minutes);
        JComboBox<String> hoursCBox= new JComboBox<String>(hours);
        JComboBox<String> secondsCBox= new JComboBox<String>(seconds);
    //forcheck

        //
        addAlarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JDialog addAlarmDialog = new JDialog();
                addAlarmDialog.setIconImage(new ImageIcon(this.getClass().getResource("alarmSelectedIcon.png")).getImage());
                addAlarmDialog.setTitle("Set Alarm");
                addAlarmDialog.setVisible(true);
                addAlarmDialog.setResizable(false);
                addAlarmDialog.setSize(340,300);
                addAlarmDialog.setLayout(null);
                addAlarmDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                JPanel panel = new JPanel();
                panel.setBounds(0,0,340,300);
                panel.setLayout(null);
                panel.setBackground(Color.darkGray);
                panel.add(minutesCBox);

                JLabel alarmHeading = new JLabel("Set Alarm Time");
                alarmHeading.setFont(new Font("Roboto",Font.BOLD,16));
                alarmHeading.setForeground(Color.WHITE);
                alarmHeading.setBounds(85,12,200,30);

                JLabel hoursLabel = new JLabel("Hours");
                hoursLabel.setFont(new Font("Roboto",Font.PLAIN,14));
                hoursLabel.setForeground(Color.WHITE);
                hoursLabel.setBounds(70,105,60,27);
                panel.add(hoursLabel);

                JLabel timeLabel = new JLabel("Time");
                timeLabel.setFont(new Font("Roboto",Font.BOLD,16));
                timeLabel.setForeground(Color.WHITE);
                timeLabel.setBounds(20,75,60,27);
                panel.add(timeLabel);

                hoursCBox.setFont(new Font("Roboto",Font.PLAIN,12));
                hoursCBox.setForeground(Color.BLACK);
                hoursCBox.setBackground(Color.WHITE);
                hoursCBox.setMaximumRowCount(4);
                hoursCBox.setBorder(BorderFactory.createCompoundBorder());
                hoursCBox.setBounds(70,75,60,27);
                hoursCBox.setSelectedItem(hours[23]);
                panel.add(hoursCBox);

                JLabel minutesLabel = new JLabel("Minutes");
                minutesLabel.setFont(new Font("Roboto",Font.PLAIN,14));
                minutesLabel.setForeground(Color.WHITE);
                minutesLabel.setBounds(138,105,60,27);
                panel.add(minutesLabel);

                minutesCBox.setFont(new Font("Roboto",Font.PLAIN,12));
                minutesCBox.setForeground(Color.BLACK);
                minutesCBox.setBackground(Color.WHITE);
                minutesCBox.setMaximumRowCount(4);
                minutesCBox.setBorder(BorderFactory.createCompoundBorder());
                minutesCBox.setBounds(138,75,60,27);
                minutesCBox.setSelectedItem(minutes[59]);
                panel.add(minutesCBox);

                JLabel secondsLabel = new JLabel("Seconds");
                secondsLabel.setFont(new Font("Roboto",Font.PLAIN,14));
                secondsLabel.setForeground(Color.WHITE);
                secondsLabel.setBounds(208,105,60,27);
                panel.add(secondsLabel);

                secondsCBox.setFont(new Font("Roboto",Font.PLAIN,12));
                secondsCBox.setForeground(Color.BLACK);
                secondsCBox.setBackground(Color.WHITE);
                secondsCBox.setMaximumRowCount(4);
                secondsCBox.setBorder(BorderFactory.createCompoundBorder());
                secondsCBox.setBounds(208,75,60,27);
                secondsCBox.setSelectedItem(seconds[0]);
                panel.add(secondsCBox);

                JButton addBtn = new JButton("Set Alarm");
                addBtn.setFocusable(false);
                addBtn.setBackground(Color.WHITE);
                addBtn.setForeground(Color.BLACK);

                addBtn.setBounds(105,197,100,30);
                panel.add(addBtn);

                panel.add(alarmHeading);
                addAlarmDialog.add(panel);
                java.util.Timer taskTimer = new java.util.Timer();
                // Create a TimerTask to update the date
                TimerTask updateDateTask = new TimerTask()
                {
                    @Override
                    public void run() {

                        for(int i=0;i<alarmsTimeLabel.size();i++)
                        {
                            if(alarmsTimeLabel.get(i).getText().equals(Clock.timeLabel))
                                System.out.println("Hi");
                        }
                    }
                };
                // Schedule the TimerTask to run every second
                taskTimer.scheduleAtFixedRate(updateDateTask, 0, 1000);

                addBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String alarmTimeString= hoursCBox.getSelectedItem()+":"+minutesCBox.getSelectedItem()+":"+secondsCBox.getSelectedItem();
                        addAlarm(alarmTimeString);
                        addAlarmDialog.dispose();

                    }
                });
            
            }

        });

        this.bodyPanel.add(addAlarmButton);
        this.bodyPanel.setLayout(null);
    }
    private void addAlarm(String Time){
        alarmsTimeLabel.add(new JLabel(Time));
        alarmsTimeLabel.get(alarmsTimeLabel.size()-1).setFont(new Font("Roboto",Font.BOLD,20));
        alarmsTimeLabel.get(alarmsTimeLabel.size()-1).setForeground(Color.WHITE);
        alarmsTimeLabel.get(alarmsTimeLabel.size()-1).setBounds(250,y_axis_bounds,200,40);
        bodyPanel.add(alarmsTimeLabel.get(alarmsTimeLabel.size()-1));

        alarmsLabel.add(new JLabel("Alarm "+(alarmsLabel.size()+1)+":"));
        alarmsLabel.get(alarmsLabel.size()-1).setFont(new Font("Roboto",Font.BOLD,20));
        alarmsLabel.get(alarmsLabel.size()-1).setForeground(new Color(0x00E499));
        alarmsLabel.get(alarmsLabel.size()-1).setBounds(25,y_axis_bounds,200,40);
        bodyPanel.add(alarmsLabel.get(alarmsLabel.size()-1));
        y_axis_bounds+=40;
//        return alarmsTimeLabel.get(alarmsTimeLabel.size()-1);

    }
    private void checkAlarms()
    {
        for(int i= 0 ; i<alarmsTimeLabel.size();i++)
        {
            if(alarmsTimeLabel.get(i).getText().equals(Clock.timeLabel.getText()))
                JOptionPane.showMessageDialog(null,alarmsLabel.get(i).getText()+"Alarm executed.");

        }
    }
}
//timertask masla