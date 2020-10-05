package GUI;

import control.CommandControl;
import control.ImplCommandControl;
import control.SimulationOperational;
import control.algorithm.Sort;
import control.command.Direction;
import control.command.FloorRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TreeSet;

public class Ascenceur_GUI {
    private JTextField currentPosition;
    private JTextField state;
    private JSpinner floor;
    private JButton ValidStageUpButton;
    private JButton ValidStageDownButton;
    private JButton ARRETURGENCEButton;
    private JPanel gui;

    public Ascenceur_GUI(int minFloor, int maxFloor) {
        gui = new JPanel();
        gui.setLayout(new GridLayout(4, 2));

        gui.add(new Label("Current Position"));
        gui.add(currentPosition = new JTextField());
        currentPosition.setEditable(false);

        gui.add(new Label("State"));
        gui.add(state = new JTextField());

        JPanel command = new JPanel();
        command.setLayout(new FlowLayout());
        command.add(floor = new JSpinner(new SpinnerNumberModel(0, minFloor, maxFloor, 1)));
        command.add(ValidStageUpButton = new JButton("UP"));
        command.add(ValidStageDownButton = new JButton("DOWN"));
        gui.add(new Label("Choice floor"));
        gui.add(command);
        gui.add(new Label());
        gui.add(ARRETURGENCEButton = new JButton("Force STOP"));
        SimulationOperational simulationOperational = new SimulationOperational(state, currentPosition, 0, null);
        CommandControl commandControl = new ImplCommandControl(0, new TreeSet<>(new Sort()), simulationOperational);
        simulationOperational.setCommandControl(commandControl);

        ValidStageUpButton.addActionListener((e) -> {
            commandControl.addFloorRequest(new FloorRequest(Direction.UP, (Integer) floor.getValue()));
        });

        ValidStageDownButton.addActionListener((e) -> {
            commandControl.addFloorRequest(new FloorRequest(Direction.DOWN, (Integer) floor.getValue()));
        });

        new Timer(500, simulationOperational).start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ascenceur_GUI");
        Ascenceur_GUI gui = new Ascenceur_GUI(0, 3);
        frame.setContentPane(gui.gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
