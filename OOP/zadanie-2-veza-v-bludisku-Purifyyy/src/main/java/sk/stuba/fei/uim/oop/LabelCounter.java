package sk.stuba.fei.uim.oop;

import javax.swing.*;

public class LabelCounter extends JLabel {
    private int counter;

    LabelCounter() {
        counter = 0;
        this.setText("Winning streak: " + String.valueOf(counter));
        this.setHorizontalAlignment(JLabel.CENTER);
    }

    public void incrementCounter() {
        counter++;
    }

    public void resetCounter() {
        counter = 0;
    }

    public void updateCounter() {
        this.setText("Winning streak: " + String.valueOf(counter));
    }
}
