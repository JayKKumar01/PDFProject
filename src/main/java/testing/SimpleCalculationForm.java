package testing;

import javax.swing.*;

public class SimpleCalculationForm extends JFrame {
    private JTextField txtA;
    private JTextField txtB;
    private JLabel lblResult;

    public SimpleCalculationForm() {
        // Set up the form layout
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        // Create and add the input fields and labels
        JPanel panelA = new JPanel();
        panelA.add(new JLabel("A:"));
        txtA = new JTextField(10);
        panelA.add(txtA);
        this.add(panelA);

        JPanel panelB = new JPanel();
        panelB.add(new JLabel("B:"));
        txtB = new JTextField(10);
        panelB.add(txtB);
        this.add(panelB);

        // Create and add the button to trigger the calculation
        JButton btnCalculate = new JButton("Calculate");
        btnCalculate.addActionListener(e -> calculate());
        this.add(btnCalculate);

        // Create and add the label to display the result
        lblResult = new JLabel("");
        this.add(lblResult);

        // Set up the window properties
        this.setTitle("Simple Calculation Form");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void calculate() {
        try {
            // Get the values of a and b from the text fields and parse them as integers
            int a = Integer.parseInt(txtA.getText());
            int b = Integer.parseInt(txtB.getText());

            // Calculate the result and display it in the result label
            int result = a + b;
            lblResult.setText("The result of " + a + " + " + b + " is " + result);
        } catch (NumberFormatException ex) {
            // If the user entered invalid input, display an error message
            lblResult.setText("Invalid input");
        }
    }

    public static void main(String[] args) {
        // Run the form on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new SimpleCalculationForm());
    }
}

