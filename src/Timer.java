import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Timer extends BaseAppLayout{
    protected JLabel timerLabel= new JLabel("00:00:00");
    protected Image timerSelectedIcon;
    protected JButton startTimerBtn;
    protected Image startBtnIcon;
    protected JButton pauseTimerBtn;
    protected Image pauseBtnIcon;
    protected JButton resetTimerBtn;
    protected Image resetBtnIcon;
    protected ArrayList<JButton> timerBtns= new ArrayList<JButton>();
    protected JPanel timersPanel= new JPanel();
    protected int x_axis_bound= 30;
    protected int y_axis_bound= 5;

    private TimerTask updateTimerTask= new TimerTask();
    private long startTime;
    private long elapsedTime;
    private boolean isRunning;

//for adding additional timers
    protected JComboBox<String> hoursCBox;
    protected JComboBox<String> minutesCBox;
    protected JComboBox<String> secondsCBox;

    protected int timerHours;
    protected int timerMinutes;
    protected int timerSeconds;
    protected int hrs=0,mins=0,sec=0;
    public Timer(){
        super();
        setVisible(true);
        this.headingLabel.setText("Timer");
        timerSelectedIcon = new ImageIcon(this.getClass().getResource("timerSelectedIcon.png")).getImage();
        this.timer.setForeground(new Color(0x00E499));
        this.timer.setIcon(new ImageIcon(timerSelectedIcon));

        //label
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setFont(new Font("Roboto", Font.BOLD, 40));
        timerLabel.setBounds(100, 60, 300, 100);
        this.bodyPanel.add(timerLabel);

        //buttons panel

        timersPanel.setBounds(0,150,380,230);

        //adding buttons to the array list
        timerBtns.add(addBtn("Add","+"));
        timerBtns.add(addBtn("Meeting","00:20:00"));
        timerBtns.add(addBtn("Sleep","00:10:00"));
        timerBtns.add(addBtn("Exercise","00:05:00"));

        startTimerBtn= new JButton();
        startBtnIcon = new ImageIcon(this.getClass().getResource("startIcon.png")).getImage();
        startTimerBtn.setIcon(new ImageIcon(startBtnIcon));
        startTimerBtn.setContentAreaFilled(false);
        startTimerBtn.setBorderPainted(false);
        startTimerBtn.setBounds(150,350,80,40);

        startTimerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timerLabel.getText().equals("00:00:00"))
                    JOptionPane.showMessageDialog(null, "Please add timer duration");
                else {
                    pauseTimerBtn.setVisible(true);
                    startTimerBtn.setVisible(false);
                    updateTimerTask.setElapsedTime(timerLabel.getText());
                    for (int i = 0; i < timerBtns.size(); i++)
                        timerBtns.get(i).setEnabled(false);
                    if (!isRunning) {
                        startTimerBtn.setEnabled(false);
                        pauseTimerBtn.setEnabled(true);
                        resetTimerBtn.setEnabled(true);

                        if (elapsedTime == 0) {
                            startTime = System.currentTimeMillis();
                        } else {
                            startTime = System.currentTimeMillis() - elapsedTime;
                        }

                        isRunning = true;
                        updateTimerTask = new TimerTask();
                        new Thread(updateTimerTask).start();

                    }
                }
            }
        });
        this.bodyPanel.add(startTimerBtn);

        resetTimerBtn= new JButton();
        resetBtnIcon = new ImageIcon(this.getClass().getResource("resetIcon.png")).getImage();
        resetTimerBtn.setIcon(new ImageIcon(resetBtnIcon));
        resetTimerBtn.setContentAreaFilled(false);
        resetTimerBtn.setBorderPainted(false);
        resetTimerBtn.setBounds(90,350,80,40);
        resetTimerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                updateTimerTask.stop();
                timerLabel.setText("00:00:00");
                startTimerBtn.setEnabled(true);
                pauseTimerBtn.setEnabled(false);
                resetTimerBtn.setEnabled(false);

                isRunning = false;
                elapsedTime = 0;

                if(timerBtns.get(1).isEnabled())
                {
                    for(int i= 0;i<timerBtns.size();i++)
                    {
                        timerBtns.get(i).setEnabled(true);
                        if(timerBtns.get(i).getForeground().equals(Color.GREEN))
                            timerBtns.get(i).setForeground(Color.WHITE);
                    }
                }
                else {
                    for(int i= 0;i<timerBtns.size();i++)
                    {

                        timerBtns.get(i).setEnabled(true);
                        if(timerBtns.get(i).getForeground().equals(Color.GREEN))
                            timerBtns.get(i).setForeground(Color.WHITE);
                    }


                    startTimerBtn.setVisible(true);
                    pauseTimerBtn.setVisible(false);}
                //timertask
//                timerLabel.setText("00:00:00");
            }
        });
        this.bodyPanel.add(resetTimerBtn);

        pauseTimerBtn= new JButton();
        pauseBtnIcon = new ImageIcon(this.getClass().getResource("pauseIcon.png")).getImage();
        pauseTimerBtn.setIcon(new ImageIcon(pauseBtnIcon));
        pauseTimerBtn.setContentAreaFilled(false);
        pauseTimerBtn.setVisible(false);
        pauseTimerBtn.setBorderPainted(false);
        pauseTimerBtn.setBounds(150,350,80,40);
        this.bodyPanel.add(pauseTimerBtn);

        pauseTimerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimerBtn.setVisible(true);
                pauseTimerBtn.setVisible(false);

                for(int i= 0;i<timerBtns.size();i++)
                    timerBtns.get(i).setEnabled(true);
                if (isRunning) {
                    startTimerBtn.setEnabled(true);
                    pauseTimerBtn.setEnabled(false);

                    isRunning = false;
                    elapsedTime = System.currentTimeMillis() - startTime;
                    updateTimerTask.stop();
                }
//                JButton clkBtn= (JButton)e.getSource();//pata nai kya karna tha

            }
        });



        //for checking
        timersPanel.setBackground(Color.darkGray);
        timersPanel.setBounds(0,170,380,230);
        timersPanel.setLayout(null);
        timersPanel.setVisible(true);
        this.bodyPanel.add(timersPanel);
    }
    private JButton addBtn(String label, String duration){

        timerBtns.add(new JButton("<html>"+label+"<br>"+duration+"</html>"));
        timerBtns.get(timerBtns.size()-1).setContentAreaFilled(false);
        timerBtns.get(timerBtns.size()-1).setForeground(Color.WHITE);

        if (timerBtns.size() == 1) {
            timerBtns.get(timerBtns.size()-1).setBounds(x_axis_bound, y_axis_bound, 90, 60);
        } else if (timerBtns.size() > 1 && x_axis_bound < 250) {
            timerBtns.get(timerBtns.size() - 1).setBounds(timerBtns.get(0).getBounds());
            x_axis_bound += 110;
            timerBtns.get(0).setBounds(x_axis_bound, y_axis_bound, 90, 60);
        } else {
            timerBtns.get(timerBtns.size() - 1).setBounds(timerBtns.get(0).getBounds());
            x_axis_bound = 30;
            y_axis_bound += 70;
            timerBtns.get(0).setBounds(x_axis_bound, y_axis_bound, 90, 60);
        }

        //adding action listener of Add+++ timer btn TimersBtns.get(0)

        timerBtns.get(timerBtns.size()-1).setFont(new Font("Roboto",Font.PLAIN,14));
        timerBtns.get(timerBtns.size()-1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JButton clickedButton = (JButton) e.getSource();
                if(clickedButton==timerBtns.get(0))
                {
                    showAddTimerDialog();
                }
                else
                    timerLabel.setText(duration);
                //for setting the color of the clicked button.
                for(int i= 0; i<timerBtns.size();i++)
                {
                    resetTimerBtn.setEnabled(true);
                    if(timerBtns.get(i)==clickedButton) {
                        timerBtns.get(i).setForeground(Color.GREEN);
                    }
                    else
                        timerBtns.get(i).setForeground(Color.WHITE);
                }
            }
        });
        //for checking

        timersPanel.add(timerBtns.get(timerBtns.size()-1));
        return timerBtns.get(timerBtns.size()-1);
    }

    private void showAddTimerDialog(){
        String selectedTimeString= String.format("%02d:%02d:%02d",timerHours,timerMinutes,timerSeconds);
        JDialog addTimerDialog = new JDialog();
        addTimerDialog.setIconImage(new ImageIcon(this.getClass().getResource("timerSelectedIcon.png")).getImage());
        addTimerDialog.setTitle("Set Timer");
        addTimerDialog.setVisible(true);
        addTimerDialog.setResizable(false);
        addTimerDialog.setSize(340,300);
        addTimerDialog.setLayout(null);
        addTimerDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        //dialog panel
        JPanel panel = new JPanel();
        panel.setBounds(0,0,340,300);
        panel.setLayout(null);
        panel.setBackground(Color.darkGray);

        JLabel zoneHeading = new JLabel("Set Timer Duration");
        zoneHeading.setFont(new Font("Roboto",Font.BOLD,16));
        zoneHeading.setForeground(Color.WHITE);
        zoneHeading.setBounds(85,12,200,30);

        JLabel hoursLabel = new JLabel("Hours");
        hoursLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        hoursLabel.setForeground(Color.WHITE);
        hoursLabel.setBounds(70,105,60,27);
        panel.add(hoursLabel);

        final String[][] hours = {new String[24]};
        for (int i = 0; i < 24; i++) {
            hours[0][i] = String.format("%02d", i);
        }

        String[] minutes = new String[60];
        for (int i = 0; i < 60; i++) {
            minutes[i] = String.format("%02d", i);
        }

        JComboBox<String> minutesCBox = new JComboBox<String>(minutes);
        JComboBox<String> hoursCBox = new JComboBox<String>(hours[0]);
        JComboBox<String> secondsCBox = new JComboBox<String>(minutes);

        hoursCBox.setFont(new Font("Roboto",Font.PLAIN,15));
        hoursCBox.setForeground(Color.BLACK);
        hoursCBox.setBackground(Color.WHITE);
        hoursCBox.setMaximumRowCount(4);
        hoursCBox.setBorder(BorderFactory.createCompoundBorder());
        hoursCBox.setBounds(70,75,60,27);
        hoursCBox.setSelectedItem(hours[0][0]);
        panel.add(hoursCBox);

        JLabel minutesLabel = new JLabel("Minutes");
        minutesLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        minutesLabel.setForeground(Color.WHITE);
        minutesLabel.setBounds(138,105,60,27);
        panel.add(minutesLabel);

        minutesCBox.setFont(new Font("Roboto",Font.PLAIN,15));
        minutesCBox.setForeground(Color.BLACK);
        minutesCBox.setMaximumRowCount(4);
        minutesCBox.setBackground(Color.WHITE);
        minutesCBox.setBorder(BorderFactory.createCompoundBorder());
        minutesCBox.setBounds(138,75,60,27);
        minutesCBox.setSelectedItem(minutes[0]);
        panel.add(minutesCBox);

        JLabel secondsLabel = new JLabel("Seconds");
        secondsLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        secondsLabel.setForeground(Color.WHITE);
        secondsLabel.setBounds(208,105,60,27);
        panel.add(secondsLabel);

        secondsCBox.setFont(new Font("Roboto",Font.PLAIN,15));
        secondsCBox.setMaximumRowCount(4);
        secondsCBox.setForeground(Color.BLACK);
        secondsCBox.setBackground(Color.WHITE);
        secondsCBox.setBorder(BorderFactory.createCompoundBorder());
        secondsCBox.setBounds(208,75,60,27);
        secondsCBox.setSelectedItem(minutes[0]);
        panel.add(secondsCBox);

        JTextField timerLabelField= new JTextField();
        timerLabelField.setBounds(138,140,132,27);
        panel.add(timerLabelField);

        JLabel tLabel = new JLabel("Label");
        tLabel.setFont(new Font("Roboto",Font.PLAIN,14));
        tLabel.setForeground(Color.WHITE);
        tLabel.setBounds(70,140,60,27);
        panel.add(tLabel);

        JButton startTimerBtn = new JButton("Add Timer");
        startTimerBtn.setFocusable(false);
        startTimerBtn.setBackground(Color.WHITE);
        startTimerBtn.setForeground(Color.BLACK);
        startTimerBtn.setBounds(105,197,100,30);
        startTimerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerHours = Integer.parseInt(hoursCBox.getSelectedItem().toString());
                timerMinutes = Integer.parseInt(minutesCBox.getSelectedItem().toString());
                timerSeconds = Integer.parseInt(secondsCBox.getSelectedItem().toString());
                String selectedTimeString= String.format("%02d:%02d:%02d",timerHours,timerMinutes,timerSeconds);
                timerBtns.add(addBtn(timerLabelField.getText(),selectedTimeString));
                addTimerDialog.dispose();
            }
        });

        panel.add(startTimerBtn);
        panel.add(zoneHeading);
        addTimerDialog.add(panel);
    }

        private class TimerTask implements Runnable {
            private boolean running = true;
            private String timerDuration;


            public void stop() {
                running = false;
                Thread.currentThread().interrupt();
            }

            public void setElapsedTime(String labelValue) {
                // Split the label value into hours, minutes, seconds, and milliseconds
                timerDuration= labelValue;
                String[] timeParts = labelValue.split(":");
                int hours = Integer.parseInt(timeParts[0]);
                int minutes = Integer.parseInt(timeParts[1]);
                int seconds = Integer.parseInt(timeParts[2]);
//                int milliseconds = Integer.parseInt(timeParts[3]);

                // Calculate total elapsed time in milliseconds
                long elapsedMillis = (((hours * 60 + minutes) * 60 + seconds) * 1000) ;


                // Get current time in milliseconds
                long currentTime = System.currentTimeMillis();

                // Calculate start time by subtracting elapsed time from current time
                startTime = currentTime - elapsedMillis;


            }

        @Override
        public void run() {
            DecimalFormat df = new DecimalFormat("00");
            DecimalFormat ms = new DecimalFormat("00");

            while (running) {
                long currentTime = System.currentTimeMillis();
                long elapsedMillis = currentTime - startTime;
                int hours = (int) (elapsedMillis / (1000 * 60 * 60));
                int minutes = (int) ((elapsedMillis / (1000 * 60)) % 60);
                int seconds = (int) ((elapsedMillis / 1000) % 60);
                double milliseconds = (elapsedMillis % 1000) / 10.0;

                String timeString = df.format(hours) + ":" +
                        df.format(minutes) + ":" +
                        df.format(seconds) ;

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        timerLabel.setText(timeString);
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}



