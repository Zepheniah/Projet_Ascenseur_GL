package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Queue;
import java.util.TreeSet;

public class Ascenceur_GUI {
    private JTextField currentPosition;
    private JTextField state;
    private JSpinner floor;
    private JButton ValidStageButton;
    private JButton ARRETURGENCEButton;
    private JPanel gui;

    public Ascenceur_GUI(int minFloor, int maxFloor) {
        gui = new JPanel();
        gui.setLayout(new GridLayout(4, 2));

        gui.add(new Label("Current Position"));
        gui.add(currentPosition = new JTextField());

        gui.add(new Label("State"));
        gui.add(state = new JTextField());

        JPanel command = new JPanel();
        command.setLayout(new FlowLayout());
        command.add(floor = new JSpinner(new SpinnerNumberModel(0, 0, 509, 1)));
        command.add(ValidStageButton = new JButton("OK"));
        gui.add(new Label("Choice floor"));
        gui.add(command);
        gui.add(new Label());
        gui.add(ARRETURGENCEButton = new JButton("Force STOP"));
    }

    public static void main(String[] args) {
        TreeSet<Integer> test = new TreeSet<>();
        for (int i = 0; i < 20; i++) {
            test.add((int)(Math.random()*100));
        }
        System.out.println(test);

        JFrame frame = new JFrame("Ascenceur_GUI");
        Ascenceur_GUI gui = new Ascenceur_GUI(0, 3);
        frame.setContentPane(gui.gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
