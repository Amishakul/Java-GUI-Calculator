package pa;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField displayField;
    private JButton[] numberButtons;
    private JButton[] operatorButtons;
    private JButton decimalButton;
    private JButton clearButton;
    private JButton equalsButton;

    private double firstNumber;
    private String operator;
    private boolean hasFirstNumber;
    private boolean hasOperator;

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(new ImageIcon("image.png").getImage());
        

        displayField = new JTextField();
        displayField.setPreferredSize(new Dimension(300, 50));
        displayField.setEditable(false);
        add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setPreferredSize(new Dimension(50, 50));
            numberButtons[i].addActionListener(this);
            gbc.gridx = i % 3;
            gbc.gridy = 3 - (i / 3);
            buttonPanel.add(numberButtons[i], gbc);
        }

        operatorButtons = new JButton[5];
        operatorButtons[0] = new JButton("+");
        operatorButtons[1] = new JButton("-");
        operatorButtons[2] = new JButton("*");
        operatorButtons[3] = new JButton("/");
        operatorButtons[4] = new JButton("%");

        for (int i = 0; i < 5; i++) {
            operatorButtons[i].setPreferredSize(new Dimension(50, 50));
            operatorButtons[i].addActionListener(this);
            gbc.gridx = 3;
            gbc.gridy = 4 - i;
            buttonPanel.add(operatorButtons[i], gbc);
        }

        decimalButton = new JButton(".");
        decimalButton.setPreferredSize(new Dimension(50, 50));
        decimalButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(decimalButton, gbc);

        clearButton = new JButton("C");
        clearButton.setPreferredSize(new Dimension(50, 50));
        clearButton.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(clearButton, gbc);

        equalsButton = new JButton("=");
        equalsButton.setPreferredSize(new Dimension(50, 50));
        equalsButton.addActionListener(this);
        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonPanel.add(equalsButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);

        pack();
        setSize(400, 500);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == clearButton) {
            displayField.setText("");
            hasFirstNumber = false;
            hasOperator = false;
        } else if (source == decimalButton) {
            String currentText = displayField.getText();
            if (!currentText.contains(".")) {
                displayField.setText(currentText + ".");
            }
        } else if (source == equalsButton) {
            if (!hasFirstNumber) {
                displayField.setText("Error: Enter operator");
                return;
            }
            
            String secondNumberString = displayField.getText();
            if (secondNumberString.isEmpty()) {
                displayField.setText("Error: Enter second number");
                return;
            }
            double secondNumber;
            try {
                secondNumber = Double.parseDouble(secondNumberString);
            } catch (NumberFormatException ex) {
                displayField.setText("Error: Invalid second number");
                return;
            }

            double result = 0.0;
            if (operator.equals("/") && secondNumber == 0) {
                displayField.setText("Error: Cannot divide by zero");
                return;
            } else {
                switch (operator) {
                    case "+":
                        result = firstNumber + secondNumber;
                        break;
                    case "-":
                        result = firstNumber - secondNumber;
                        break;
                    case "*":
                        result = firstNumber * secondNumber;
                        break;
                    case "/":
                        result = firstNumber / secondNumber;
                        break;
                    case "%":
                        result = firstNumber % secondNumber;
                        break;
                }
            }

            displayField.setText(String.valueOf(result));
        } else {
            for(int i = 0; i < 10; i++) {
                if (source == numberButtons[i]) {
                    displayField.setText(displayField.getText() + i);
                    return;
                }
            }

            for (int i = 0; i < 5; i++) {
                if (source == operatorButtons[i]) {
                    if (displayField.getText().isEmpty()) {
                        displayField.setText("Error: Enter first number");
                        return;
                    }
                    if (hasOperator) {
                        displayField.setText("Error: Enter second number");
                        return;
                    }
                    firstNumber = Double.parseDouble(displayField.getText());
                    operator = operatorButtons[i].getText();
                    displayField.setText("");
                    hasFirstNumber = true;
                    hasOperator = true;
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Calculator();
            }
        });
    }
}

