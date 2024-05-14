package soften_exp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

public class App {

// ------------------ Main ------------------
    public static void main(String[] args) {
        App app = new App();
        
        // create the initial GUI
        JFrame frame = new JFrame("SoftEng Exp App");
        app.createInitialGUI(frame);

        // if the file_path are set, then load the file
        if (app.file_path == null) {
            // create a dialog box to get the path and filename
            // app.createGetFileGUI(frame);
        }
    }

// ------------------ Class ------------------

    String file_path = null;
    HashMap<Tuple<String, String>, Integer> graph = new HashMap<Tuple<String, String>, Integer>();
    HashSet<String> words = new HashSet<String>();

    private void createInitialGUI(JFrame frame) {
        // create the welcome screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        JPanel panel = new JPanel();

        // set the layout of the panel
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridx = 0;
        
        // print welcome message
        JLabel welcome_label = new JLabel();
        welcome_label.setText( "Welcome to the Software Engineering Experiment App!" );
        constraints.gridy = 0;
        panel.add(welcome_label, constraints);

        // add blank space
        constraints.gridy = 1;
        panel.add(Box.createVerticalStrut(10), constraints);

        // print file path
        JLabel file_path_label = new JLabel();
        if (this.file_path == null) {
            file_path_label.setText( "No file selected." );
        } else {
            file_path_label.setText( "File: " + this.file_path );
        }
        constraints.gridy = 2;
        panel.add(file_path_label, constraints);

        // add blank space
        constraints.gridy = 3;
        panel.add(Box.createVerticalStrut(10), constraints);
        
        // add a button to select a file
        JButton select_file_button = new JButton("Select File");
        select_file_button.addActionListener(e -> {
            createGetFileGUI(frame);
            file_path_label.setText( "File: " + this.file_path );
        });
        constraints.gridy = 4;
        panel.add(select_file_button, constraints);
        
        // add blank space
        constraints.gridy = 5;
        panel.add(Box.createVerticalStrut(10), constraints);

        // add a button to load the file
        JButton load_file_button = new JButton("Load File");
        load_file_button.addActionListener(e -> {
            loadFile();
        });
        constraints.gridy = 6;
        panel.add(load_file_button, constraints);
        
        // add blank space
        constraints.gridy = 7;
        panel.add(Box.createVerticalStrut(10), constraints);

        // add quit button
        JButton quit_button = new JButton("Quit");
        quit_button.addActionListener(e -> {
            System.exit(0);
        });
        constraints.gridy = 8;
        panel.add(quit_button, constraints);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void createGetFileGUI(JFrame frame) {
        // create a file choooser
        JFileChooser fileChooser = new JFileChooser();
        
        // set the current directory to the current run directory
        fileChooser.setCurrentDirectory(new java.io.File("."));

        int returnValue = fileChooser.showOpenDialog(frame);

        // if the user selects a file, then set the file_path
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            this.file_path = fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            JOptionPane.showMessageDialog(frame, "No file selected. Exiting.");
            System.exit(0);
        }
    }
    
    private void loadFile() {
        // create a list of words to store the words
        ArrayList<String> words = new ArrayList<String>();

        // read the file line by line
        try {
            BufferedReader reader = new BufferedReader(new FileReader(this.file_path));
            String line = reader.readLine();
            while (line != null) {
                // System.out.println(line);
                
                // split the line by the non-word characters, and add to words
                String[] temp_words = line.split("\\W+");
                for (String word : temp_words) {
                    words.add(word.toLowerCase());
                }
                
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String word : words) {
            // System.out.println(word);
            this.words.add(word);
        }
        
        // use graph to store the word pairs
        for (int i = 0; i < words.size() - 1; i++) {
            Tuple<String, String> pair = new Tuple<String, String>(words.get(i), words.get(i+1));
            if (this.graph.containsKey(pair)) {
                this.graph.put(pair, this.graph.get(pair) + 1);
            } else {
                this.graph.put(pair, 1);
            }
        }
        
        // print the graph and words
        for (Tuple<String, String> pair : this.graph.keySet()) {
            System.out.println(pair.x + " " + pair.y + " " + this.graph.get(pair));
        }
        for (String word : this.words) {
            System.out.println(word);
        }
    }
}

class Tuple<X, Y> { 
    public final X x; 
    public final Y y; 

    public Tuple(X x, Y y) { 
        this.x = x; 
        this.y = y; 
    } 
}
