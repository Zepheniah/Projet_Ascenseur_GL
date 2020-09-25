package GUI;

import javax.swing.*;

public class Ascenceur_GUI {
    private JButton a3Button;
    private JButton a2Button;
    private JButton ARRETURGENCEButton;
    private JButton RDCButton;
    private JButton a1Button;
    private JPanel gui;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ascenceur_GUI");
        frame.setContentPane(new Ascenceur_GUI().gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
