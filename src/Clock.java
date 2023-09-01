import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.TimerTask;


public class Clock extends BaseAppLayout{

    protected Image clockSelectedIcon;
    protected JLabel dateLabel;
    static JLabel timeLabel;

    protected DateTimeFormatter dateFormat;
    protected DateTimeFormatter timeFormat;
    protected LocalDateTime now;
    protected JButton newClockButton;
    protected Image newClockIcon;
    protected ArrayList<JLabel>  timeZonesLabel= new ArrayList<>();
    protected ArrayList<JLabel>  timeZones= new ArrayList<>();
    static int y_axis_bounds=220;

    protected JComboBox<String> timeZonesCBox;
    public Clock() {
        super();
        setVisible(true);
//        this.clock.setEnabled(false);//look into it later on
        this.headingLabel.setText("Clock");
        clockSelectedIcon = new ImageIcon(this.getClass().getResource("clockSelectedIcon.png")).getImage();
        this.clock.setForeground(new Color(0x00E499));
        this.clock.setIcon(new ImageIcon(clockSelectedIcon));

        //for date and time
        dateFormat = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy");
        timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        now = LocalDateTime.now();

        dateLabel = new JLabel(this.date());
        timeLabel = new JLabel(this.time());

        timeLabel.setBounds(90, 75, 300, 100);
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Roboto", Font.BOLD, 45));

        dateLabel.setBounds(100, 130, 300, 100);
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Roboto", Font.PLAIN, 20));

        bodyPanel.add(timeLabel);
        bodyPanel.add(dateLabel);
        //displaying date and time

        java.util.Timer taskTimer = new java.util.Timer();
        // Create a TimerTask to update the date
        TimerTask updateDateTask = new TimerTask() {
            @Override
            public void run() {
                // Update the time&dateLabel
                dateLabel.setText(date());
                timeLabel.setText(time());
                for(int i=0;i<timeZones.size();i++)
                {
                    timeZonesLabel.get(i).setText(dateNTime(ZoneId.of(timeZones.get(i).getText())));
                }
            }
        };
        // Schedule the TimerTask to run every second
        taskTimer.scheduleAtFixedRate(updateDateTask, 0, 1000);

        //for adding new Time Zones
        newClockButton = new JButton("");
        newClockIcon = new ImageIcon(this.getClass().getResource("newClockIcon.png")).getImage();
        newClockButton.setIcon(new ImageIcon(newClockIcon));
        newClockButton.setContentAreaFilled(false);
        newClockButton.setBorderPainted(false);
        newClockButton.setBounds(135,335,100,40);
        this.bodyPanel.add(newClockButton);
        String[] allZones= TimeZone.getAvailableIDs();
        timeZonesCBox = new JComboBox<>(allZones);

        newClockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //creating a dialogBox for adding clock
                JDialog addClockDialog = new JDialog();
                addClockDialog.setIconImage(new ImageIcon(this.getClass().getResource("clockSelectedIcon.png")).getImage());
                addClockDialog.setTitle("Add Clock");
                addClockDialog.setVisible(true);
                addClockDialog.setResizable(false);
                addClockDialog.setSize(340,300);
                addClockDialog.setLayout(null);
                addClockDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                JPanel panel = new JPanel();
                panel.setBounds(0,0,340,300);
                panel.setLayout(null);
                panel.setBackground(Color.darkGray);

                JLabel zoneHeading = new JLabel("Select Clock Zone!");
                zoneHeading.setFont(new Font("Roboto",Font.BOLD,16));
                zoneHeading.setForeground(Color.WHITE);
                zoneHeading.setBounds(85,12,200,30);

                timeZonesCBox.setFont(new Font("Roboto",Font.PLAIN,12));
                timeZonesCBox.setForeground(Color.BLACK);
                timeZonesCBox.setBackground(Color.WHITE);
                timeZonesCBox.setMaximumRowCount(5);
                timeZonesCBox.setBorder(BorderFactory.createCompoundBorder());
                timeZonesCBox.setBounds(82,100,150,30);

                JButton addBtn = new JButton("Add");
                addBtn.setFocusable(false);
                addBtn.setBackground(Color.WHITE);
                addBtn.setForeground(Color.BLACK);

                addBtn.setBounds(105,197,100,30);
                panel.add(addBtn);
//                JLabel newZone= new JLabel();//later on add the zone to the frame
//      panel, scrollpane, for clocks to be added.

                addBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        addClock(timeZonesCBox.getSelectedItem().toString());
                        addClockDialog.dispose();
                    }
                });

                panel.add(zoneHeading);
                panel.add(timeZonesCBox);
                addClockDialog.add(panel);

            }
        });



    }
    public String date()
    {
        now = LocalDateTime.now();
        return dateFormat.format(now).toString();
    }
    public String time()
    {
        now = LocalDateTime.now();
        return timeFormat.format(now).toString();
    }
    public String dateNTime(ZoneId id)
    {
        ZonedDateTime currentTime = ZonedDateTime.now(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
        return currentTime.format(formatter).toString();
    }

    private void addClock(String zone)
    {
        timeZones.add(new JLabel(zone));
        timeZones.get(timeZones.size()-1).setFont(new Font("Roboto",Font.PLAIN,18));
        timeZones.get(timeZones.size()-1).setForeground(Color.WHITE);
        timeZones.get(timeZones.size()-1).setBounds(25,y_axis_bounds,200,40);
        bodyPanel.add(timeZones.get(timeZones.size()-1));

        ZoneId zoneId = ZoneId.of(zone);

        ZonedDateTime currentTime = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
//        //additional
//        String []zoneTime= formattedTime.split(" ");
//        timeZonesLabel.add(new JLabel("<html>"+zoneTime[1]+"<br>"+zoneTime[0]+"</html>"));


        timeZonesLabel.add(new JLabel(formattedTime));
        timeZonesLabel.get(timeZonesLabel.size()-1).setFont(new Font("Roboto",Font.PLAIN,18));
        timeZonesLabel.get(timeZonesLabel.size()-1).setForeground(Color.WHITE);
        timeZonesLabel.get(timeZonesLabel.size()-1).setBounds(200,y_axis_bounds,200,40);
        bodyPanel.add(timeZonesLabel.get(timeZonesLabel.size()-1));

        y_axis_bounds+=40;

//        return timeZones.get(timeZones.size()-1);
    }
}
