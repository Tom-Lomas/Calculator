package Calculator;
// Thomas Lomas

/* This program uses the JFrame class to create a calculator that is capable of doing binary operations as well as
   performing operations such as square root, squaring, and taking the reciprocal of a number. The calculator also has a 
   button to clear the workspace, delete the last character, add decimal points, and complete negative number operations.
*/

//Importing required classes
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Stack;
import java.awt.*;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {

    /**
     * This method takes in the input string,
     * evaluates the math expression, and
     * returns the result as a decimal value.
     * 
     * @param input the input math string,
     *              supporting: +, -, *, /, (, )
     * @return the evaluated result
     */
    public static double calc(String input) {

        input = input.replaceAll("\\s", "");

        double first = 0;
        String operand = "";
        double second = 0;
        String temp = "";
        Stack<String> op = new Stack<>();
        ArrayList<String> post = new ArrayList<>();

        while (input.isEmpty() == false) {
            if (Character.isDigit(input.charAt(0))) {
                while (!input.isEmpty()) {
                    if (Character.isDigit(input.charAt(0)) || (input.charAt(0) == '.' && !temp.contains("."))) {
                        temp = temp + input.charAt(0);
                        input = input.substring(1);
                    } else {
                        post.add(temp);
                        temp = "";
                        break;
                    }
                }
                if (!temp.isEmpty()) {
                    post.add(temp);
                    temp = "";
                }
            } else if (input.substring(0, 1).equals("(")) {
                op.push(input.substring(0, 1));
                input = input.substring(1);
            } else if (input.substring(0, 1).equals("+") || input.substring(0, 1).equals("-")) {
                if (op.isEmpty() || op.peek().equals("(")) {
                    op.push(input.substring(0, 1));
                    input = input.substring(1);
                } else if (op.peek().equals("*") || op.peek().equals("/")) {
                    while (!op.isEmpty() && !(op.peek().equals("("))) {
                        operand = op.pop();
                        first = Double.parseDouble(post.remove(post.size() - 2));
                        second = Double.parseDouble(post.remove(post.size() - 1));
                        if (operand.equals("+")) {
                            post.add(Double.toString(first + second));
                        } else if (operand.equals("-")) {
                            post.add(Double.toString(first - second));
                        } else if (operand.equals("*")) {
                            post.add(Double.toString(first * second));
                        } else {
                            post.add(Double.toString(first / second));
                        }
                    }
                    op.push(input.substring(0, 1));
                    input = input.substring(1);
                } else if (op.peek().equals("-") || op.peek().equals("+")) {
                    operand = op.pop();
                    first = Double.parseDouble(post.remove(post.size() - 2));
                    second = Double.parseDouble(post.remove(post.size() - 1));
                    if (operand.equals("-")) {
                        post.add(Double.toString(first - second));
                    } else {
                        post.add(Double.toString(first + second));
                    }
                }
            } else if (input.substring(0, 1).equals("*") || input.substring(0, 1).equals("/")) {
                if (op.isEmpty() || op.peek().equals("(")) {
                    op.push(input.substring(0, 1));
                    input = input.substring(1);
                } else if (op.peek().equals("/") || op.peek().equals("*")) {
                    operand = op.pop();
                    first = Double.parseDouble(post.remove(post.size() - 2));
                    second = Double.parseDouble(post.remove(post.size() - 1));
                    if (operand.equals("/")) {
                        post.add(Double.toString(first / second));
                    } else {
                        post.add(Double.toString(first * second));
                    }
                } else {
                    op.push(input.substring(0, 1));
                    input = input.substring(1);
                }
            } else if (input.substring(0, 1).equals(")")) {
                while (!op.isEmpty() && !(op.peek().equals("("))) {
                    operand = op.pop();
                    first = Double.parseDouble(post.remove(post.size() - 2));
                    second = Double.parseDouble(post.remove(post.size() - 1));
                    if (operand.equals("+")) {
                        post.add(Double.toString(first + second));
                    } else if (operand.equals("-")) {
                        post.add(Double.toString(first - second));
                    } else if (operand.equals("*")) {
                        post.add(Double.toString(first * second));
                    } else {
                        post.add(Double.toString(first / second));
                    }
                }
                op.pop();
                input = input.substring(1);
            }
        }
        while (op.isEmpty() == false) {
            operand = op.pop();
            first = Double.parseDouble(post.remove(post.size() - 2));
            second = Double.parseDouble(post.remove(post.size() - 1));
            if (operand.equals("+")) {
                post.add(Double.toString(first + second));
            } else if (operand.equals("-")) {
                post.add(Double.toString(first - second));
            } else if (operand.equals("*")) {
                post.add(Double.toString(first * second));
            } else {
                post.add(Double.toString(first / second));
            }
        }
        return Double.parseDouble(post.get(0));
    }

    // Initializing the variables for the buttons, the textfield, and the container
    // itself
    private JButton backButton, clearButton, divisionButton, multiplicationButton,
            subtractionButton, additionButton, equalsButton, negativeButton, decimalButton,
            zeroButton, oneButton, twoButton, threeButton, fourButton, fiveButton, sixButton,
            sevenButton, eightButton, nineButton, reciprocalButton, squaredButton, squareRootButton,
            openParentheseButton, closedParentheseButton;
    private JTextField calculation;
    private Container pane;

    public Calculator() {

        // Setting up the container for all the information
        GridBagConstraints layoutConst = null;

        setTitle("Calculator");
        setSize(500, 400);

        pane = this.getContentPane();
        pane.setBackground(new Color(107, 106, 104));

        // Assigning values to each of the buttons
        backButton = new JButton("     ←     ");
        clearButton = new JButton("     C     ");
        divisionButton = new JButton("     ÷     ");
        multiplicationButton = new JButton("     *     ");
        subtractionButton = new JButton("     -     ");
        additionButton = new JButton("     +     ");
        equalsButton = new JButton("     =     ");
        negativeButton = new JButton("    +/-    ");
        decimalButton = new JButton("     .     ");
        zeroButton = new JButton("     0     ");
        oneButton = new JButton("     1     ");
        twoButton = new JButton("     2     ");
        threeButton = new JButton("     3     ");
        fourButton = new JButton("     4     ");
        fiveButton = new JButton("     5     ");
        sixButton = new JButton("     6     ");
        sevenButton = new JButton("     7     ");
        eightButton = new JButton("     8     ");
        nineButton = new JButton("     9     ");
        reciprocalButton = new JButton("    1/x    ");
        squaredButton = new JButton("    x²    ");
        squareRootButton = new JButton("    √x    ");
        openParentheseButton = new JButton("    (    ");
        closedParentheseButton = new JButton("    )    ");

        // Creating a variable for the textfield and assigning a value to the field
        String calc = "";

        calculation = new JTextField(calc);

        // Adding the textfield to the container
        calculation = new JFormattedTextField(NumberFormat.getNumberInstance());
        calculation.setHorizontalAlignment(SwingConstants.RIGHT);
        calculation.setEditable(false);
        calculation.setText("0");
        calculation.setColumns(15);

        // Adding all of the buttons to the container in the correct location
        setLayout(new GridBagLayout());
        layoutConst = new GridBagConstraints();

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 0;
        layoutConst.gridy = 1;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(openParentheseButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 1;
        layoutConst.gridy = 1;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(closedParentheseButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 3;
        layoutConst.gridy = 1;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(backButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 2;
        layoutConst.gridy = 1;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(clearButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 3;
        layoutConst.gridy = 2;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(divisionButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 2;
        layoutConst.gridy = 2;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(squareRootButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 1;
        layoutConst.gridy = 2;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(squaredButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 0;
        layoutConst.gridy = 2;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(reciprocalButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 3;
        layoutConst.gridy = 3;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(multiplicationButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 2;
        layoutConst.gridy = 3;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(nineButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 1;
        layoutConst.gridy = 3;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(eightButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 0;
        layoutConst.gridy = 3;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(sevenButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 3;
        layoutConst.gridy = 4;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(subtractionButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 2;
        layoutConst.gridy = 4;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(sixButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 1;
        layoutConst.gridy = 4;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(fiveButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 0;
        layoutConst.gridy = 4;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(fourButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 3;
        layoutConst.gridy = 5;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(additionButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 2;
        layoutConst.gridy = 5;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(threeButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 1;
        layoutConst.gridy = 5;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(twoButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 0;
        layoutConst.gridy = 5;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(oneButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 3;
        layoutConst.gridy = 6;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(equalsButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 2;
        layoutConst.gridy = 6;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(decimalButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 1;
        layoutConst.gridy = 6;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(zeroButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.weightx = 1.0;
        layoutConst.weighty = 1.0;
        layoutConst.gridx = 0;
        layoutConst.gridy = 6;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(negativeButton, layoutConst);

        layoutConst = new GridBagConstraints();
        layoutConst.fill = GridBagConstraints.HORIZONTAL;
        layoutConst.weightx = 0;
        layoutConst.weighty = 0;
        layoutConst.gridx = 0;
        layoutConst.gridy = 0;
        layoutConst.insets = new Insets(10, 10, 10, 10);
        add(calculation, layoutConst);

        // Adding action listeners to each of the buttons so that something is performed
        // when they are pressed
        backButton.addActionListener(this);
        clearButton.addActionListener(this);
        divisionButton.addActionListener(this);
        multiplicationButton.addActionListener(this);
        additionButton.addActionListener(this);
        subtractionButton.addActionListener(this);
        decimalButton.addActionListener(this);
        negativeButton.addActionListener(this);
        zeroButton.addActionListener(this);
        oneButton.addActionListener(this);
        twoButton.addActionListener(this);
        threeButton.addActionListener(this);
        fourButton.addActionListener(this);
        fiveButton.addActionListener(this);
        sixButton.addActionListener(this);
        sevenButton.addActionListener(this);
        eightButton.addActionListener(this);
        nineButton.addActionListener(this);
        reciprocalButton.addActionListener(this);
        squaredButton.addActionListener(this);
        squareRootButton.addActionListener(this);
        equalsButton.addActionListener(this);
        openParentheseButton.addActionListener(this);
        closedParentheseButton.addActionListener(this);

        // Making sure the program stops when the application is closed and is making
        // sure the container is visible to the user
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    String calculate = "";

    public void actionPerformed(ActionEvent e) {

        // Adding actions that need to be performed when each of the buttons are pressed
        if (e.getActionCommand().equals("     ←     ")) {
            if (!(calculate.equals("0"))) {
                calculate = calculate.substring(0, calculate.length() - 1);
                calculation.setText(calculate);
            } else {
                calculation.setText("0");
            }
        } else if (e.getActionCommand().equals("    (    ")) {
            calculate += " ( ";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("    )    ")) {
            calculate += " ) ";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     C     ")) {
            calculate = "";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     ÷     ")) {
            calculate += " ÷ ";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     *     ")) {
            calculate += " * ";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     -     ")) {
            calculate += " - ";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     +     ")) {
            calculate += " + ";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     =     ")) {
            calculate = Double.toString(calc(calculate));
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("    1/x    ")) {
            calculate = Double.toString(1 / Double.parseDouble(calculate));
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("    x²    ")) {
            calculate = Double.toString(Math.pow(Double.parseDouble(calculate), 2));
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("    √x    ")) {
            calculate = Double.toString(Math.sqrt(Double.parseDouble(calculate)));
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     .     ")) {
            calculate += ".";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("    +/-    ")) {
            calculate = "-" + calculate;
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     0     ")) {
            calculate += "0";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     1     ")) {
            calculate += "1";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     2     ")) {
            calculate += "2";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     3     ")) {
            calculate += "3";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     4     ")) {
            calculate += "4";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     5     ")) {
            calculate += "5";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     6     ")) {
            calculate += "6";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     7     ")) {
            calculate += "7";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     8     ")) {
            calculate += "8";
            calculation.setText(calculate);
        } else if (e.getActionCommand().equals("     9     ")) {
            calculate += "9";
            calculation.setText(calculate);
        }
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
    }
}
