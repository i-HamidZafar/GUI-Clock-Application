import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Objects;

public class Stopwatch extends BaseAppLayout {
    private JLabel stopwatchLabel;
    private JButton startButton;
    private JButton pauseButton;
    private JButton resetButton;
    private TimerTask updateTimerTask;
    private long startTime;
    private long elapsedTime;
    private boolean isRunning;

    public Stopwatch() {
        super();
        setVisible(true);
        this.headingLabel.setText("Stopwatch");
        stopwatchLabel = new JLabel("00:00:00.00");
        stopwatchLabel.setForeground(Color.WHITE);
        stopwatchLabel.setFont(new Font("Roboto", Font.BOLD, 40));
        stopwatchLabel.setBounds(90, 60, 300, 100);
        this.bodyPanel.add(stopwatchLabel);

        Image stopwatchSelectedIcon = new ImageIcon(this.getClass().getResource("alarmSelectedIcon.png")).getImage();
        this.stopwatch.setForeground(new Color(0x00E499));
        this.stopwatch.setIcon(new ImageIcon(stopwatchSelectedIcon));
        
        startButton = new JButton();
        startButton.setFocusable(false);
        Image startBtnIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("startIcon.png"))).getImage();
        startButton.setIcon(new ImageIcon(startBtnIcon));
        startButton.setContentAreaFilled(false);
//        pauseButton.setVisible(false);
        startButton.setBorderPainted(false);
        startButton.setBounds(60, 350, 80, 40);
        this.bodyPanel.add(startButton);

        pauseButton = new JButton();
        Image pauseBtnIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("pauseIcon.png"))).getImage();
        pauseButton.setIcon(new ImageIcon(pauseBtnIcon));
        pauseButton.setContentAreaFilled(false);
        pauseButton.setBounds(150, 350, 80, 40);
        pauseButton.setBorderPainted(false);
        this.bodyPanel.add(pauseButton);

        resetButton = new JButton();
        resetButton.setFocusable(false);
        Image resetBtnIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("resetIcon.png"))).getImage();
        resetButton.setIcon(new ImageIcon(resetBtnIcon));
        resetButton.setContentAreaFilled(false);
        resetButton.setBorderPainted(false);
        resetButton.setBounds(240, 350, 80, 40);
        resetButton.setEnabled(false);
        this.bodyPanel.add(resetButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    startButton.setEnabled(false);
                    pauseButton.setEnabled(true);
                    resetButton.setEnabled(true);

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
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    startButton.setEnabled(true);
                    pauseButton.setEnabled(false);

                    isRunning = false;
                    elapsedTime = System.currentTimeMillis() - startTime;
                    updateTimerTask.stop();
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimerTask.stop();
                stopwatchLabel.setText("00:00:00.00");
                startButton.setEnabled(true);
                pauseButton.setEnabled(false);
                resetButton.setEnabled(false);

                isRunning = false;
                elapsedTime = 0;

            }
        });

    }



    private class TimerTask implements Runnable {
        private boolean running = true;

        public void stop() {
            running = false;
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
                        df.format(seconds) + "." +
                        ms.format(milliseconds);

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        stopwatchLabel.setText(timeString);
                    }
                });

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
