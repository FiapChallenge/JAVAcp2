package br.com.fiap.models;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class ProgressBar extends JFrame {
    
    private JProgressBar progressBar;
    
    public ProgressBar() {
        setTitle("My Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);
        
        progressBar = new JProgressBar();
        progressBar.setMinimum(0);
        progressBar.setMaximum(100);
        progressBar.setStringPainted(true); // show progress percentage
        
        add(progressBar);
        
        setVisible(true);
        
        // call a method to update the progress bar
        updateProgressBar();
    }
    
    public void updateProgressBar() {
        for (int i = 0; i <= 100; i++) {
            progressBar.setValue(i);
            try {
                Thread.sleep(50); // simulate some work being done
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}