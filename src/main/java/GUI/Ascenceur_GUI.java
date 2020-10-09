/**
 * /class main et GUI
 * /brief Point de départ de notre programme,servant principalement à generer et afficher notre GUI
 */
package GUI;

import control.CommandControl;
import control.ImplCommandControl;
import control.SimulationOperational;
import control.algorithm.Sort2;
import control.command.Acquit;
import control.command.Direction;
import control.command.EmergencyBrake;
import control.command.FloorRequest;

import javax.swing.*;
import java.awt.*;
import java.util.TreeSet;

public class Ascenceur_GUI {
    private JTextField currentPosition;
    private JTextField state;
    private JSpinner floor;
    private JButton ValidStageUpButton;
    private JButton ValidStageDownButton;
    private JButton ARRETURGENCEButton;
    private JButton Acquitte;
    private JPanel gui;

    /**
     * Constructeur premettant d'initialiser le GUI
     * @param minFloor,maxFloor entier désignant les bornes de notre ascensceur
     *
     */
    public Ascenceur_GUI(int minFloor, int maxFloor) {


        JPanel command = new JPanel();
        init_base_gui(command);
        init_command(command, minFloor, maxFloor);

        SimulationOperational simulationOperational = new SimulationOperational(state, currentPosition, 0, null);
        ImplCommandControl commandControl = new ImplCommandControl(0, simulationOperational);
        commandControl.setCommands(new TreeSet<>(new Sort2(commandControl)));
        simulationOperational.setCommandControl(commandControl);

        init_action_listener(commandControl);

        new Timer(1000, simulationOperational).start();
    }

    /**
     * Initialisation des bouton UP & DOWN et du selecteur d'étage.
     * @param command Jpanel associé au bouton UP & DOWN et ainsi que l'entrée de l'étage demandé
     *
     */
    private void init_command(JPanel command,int minFloor,int maxFloor){
        command.setLayout(new FlowLayout());
        command.add(floor = new JSpinner(new SpinnerNumberModel(0, minFloor, maxFloor, 1)));
        command.add(ValidStageUpButton = new JButton("UP"));
        command.add(ValidStageDownButton = new JButton("DOWN"));
        command.add(Acquitte = new JButton("Acquitte"));

    }

    /**
     *  Initialisation du GUI général(fenetre et texte)
     * @param command Second panel initialisé dans init_command
     * @ref init_command
     */
    private void init_base_gui(JPanel command){
        gui = new JPanel();
        gui.setLayout(new GridLayout(4, 2));

        gui.add(new Label("Current Position"));
        gui.add(currentPosition = new JTextField());
        currentPosition.setEditable(false);

        gui.add(new Label("State"));
        gui.add(state = new JTextField());

        gui.add(new Label("Choice floor"));
        gui.add(command);
        gui.add(new Label());
        gui.add(ARRETURGENCEButton = new JButton("Force STOP"));

    }

    /**
     * Initialisation des listener lié au different bouton présent
     * @param commandControl
     */
    private void init_action_listener(CommandControl commandControl){
        ValidStageUpButton.addActionListener((e) -> {
            commandControl.addFloorRequest(new FloorRequest(Direction.UP, (Integer) floor.getValue()));
        });

        ValidStageDownButton.addActionListener((e) -> {
            commandControl.addFloorRequest(new FloorRequest(Direction.DOWN, (Integer) floor.getValue()));
        });
        ARRETURGENCEButton.addActionListener((e) ->{
            commandControl.emergencyBreak(new EmergencyBrake());
        } );
        Acquitte.addActionListener((e) ->{
            commandControl.acquit(new Acquit());
        });
    }

    /**
     *
     * Main lançant le programme
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ascenceur_GUI");
        Ascenceur_GUI gui = new Ascenceur_GUI(-1, 10);
        frame.setContentPane(gui.gui);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
