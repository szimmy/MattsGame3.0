package gui;

import javax.swing.*;
import java.awt.*;

public class StatusBarPanel extends JPanel {

	private JProgressBar bar;
	private JLabel statusName;
	private int current;
	private int maxValue;
	
    public StatusBarPanel(String title, int current, int max) {
    	statusName = new JLabel(title);
        statusName.setFont(new Font(statusName.getFont().getName(), Font.BOLD, 14));
        statusName.setHorizontalAlignment(SwingConstants.CENTER);
        statusName.setVerticalAlignment(SwingConstants.BOTTOM);
		this.current = current;
        
        bar = new JProgressBar();
        bar.setStringPainted(true);

        setBarValue(current, max);
        
        statusName.setOpaque(false);
        this.setOpaque(false);
        this.setLayout(new GridLayout(2,1));
        this.add(statusName);
        this.add(bar);
        this.repaint();
	}
    
	public void setBarValue(int newValue, int newMax) {
		if (newValue >= 0 && newValue <= newMax) {
			bar.setMaximum(newMax);
            bar.setValue(newValue);
            current = newValue;
            update(newValue);
            this.repaint();
        }
	}

    public void update(int newValue) {
        bar.setString(newValue + "/" + bar.getMaximum());
        
        if(statusName.getText().equals("Health")) {
        	if(newValue > Math.round(bar.getMaximum()*.75)) {
            	bar.setForeground(new Color(0, 192, 0));
        	}
        	else if(newValue <= Math.round(bar.getMaximum()*.75) && newValue > Math.round(bar.getMaximum()*.35)) {
            	bar.setForeground(new Color(255, 128, 0));
        	}
        	else {
            	bar.setForeground(new Color(255, 0, 0));

        	}
        }
        else if(statusName.getText().equals("XP")) {
        	bar.setForeground(new Color(0, 128, 255));
        }
        this.repaint();
    }

	public int getCurrentValue() {
		return current;
	}

	public void setCurrentValue(int currentValue) {
		this.current = currentValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}
}
