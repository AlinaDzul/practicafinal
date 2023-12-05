package practica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Practica extends JFrame {
    private JTextField campoA, campoB, campoC;
    private JLabel etiquetaResultado;

    public Practica() {

        setTitle("Calculadora");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel etiquetaA = new JLabel("A:");
        JLabel etiquetaB = new JLabel("B:");
        JLabel etiquetaC = new JLabel("C:");

        campoA = new JTextField(10);
        campoB = new JTextField(10);
        campoC = new JTextField(10);

        etiquetaResultado = new JLabel("Resultado:");

        JButton botonCalcular = new JButton("Calcular");
        JButton botonLimpiar = new JButton("Limpiar");
        

        setLayout(new SpringLayout());

        add(etiquetaA);
        add(campoA);
        add(etiquetaB);
        add(campoB);
        add(etiquetaC);
        add(campoC);

        add(etiquetaResultado);
        add(new JLabel());

        add(botonCalcular);
        add(botonLimpiar);
        

        // Configurar eventos
        botonCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularEcuacionCuadratica();
            }
        });

        botonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

      

        // Alinear y mostrar la ventana
        SpringUtilities.makeCompactGrid(this.getContentPane(), 5, 2, 10, 10, 10, 10);
        setVisible(true);
    }

    private void calcularEcuacionCuadratica() {
        try {
            double coefA = Double.parseDouble(campoA.getText());
            double coefB = Double.parseDouble(campoB.getText());
            double coefC = Double.parseDouble(campoC.getText());

            double discriminante = coefB * coefB - 4 * coefA * coefC;

            if (discriminante < 0) {
                etiquetaResultado.setText("No hay soluciones reales");
            } else if (discriminante == 0) {
                double solucion = -coefB / (2 * coefA);
                etiquetaResultado.setText("La solución es: " + solucion);
            } else {
                double raiz1 = (-coefB + Math.sqrt(discriminante)) / (2 * coefA);
                double raiz2 = (-coefB - Math.sqrt(discriminante)) / (2 * coefA);
                etiquetaResultado.setText("Las soluciones son: " + raiz1 + " y " + raiz2);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos en A, B y C", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        campoA.setText("");
        campoB.setText("");
        campoC.setText("");
        etiquetaResultado.setText("Resultado:");
    }

    private void borrarCampos() {
        campoA.setText("");
        campoB.setText("");
        campoC.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Practica();
            }
        });
    }
}

class SpringUtilities {
    public static void makeCompactGrid(Container parent,
                                       int rows, int cols,
                                       int initialX, int initialY,
                                       int xPad, int yPad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout) parent.getLayout();
        } catch (ClassCastException exc) {
            throw new RuntimeException("El primer argumento de makeCompactGrid debe usar SpringLayout.");
        }

        Spring xPadSpring = Spring.constant(xPad);
        Spring yPadSpring = Spring.constant(yPad);
        Spring initialXSpring = Spring.constant(initialX);
        Spring initialYSpring = Spring.constant(initialY);
        int max = rows * cols;

        Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0)).getWidth();
        Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0)).getHeight();
        for (int i = 1; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));

            maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
            maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
        }

        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));

            cons.setWidth(maxWidthSpring);
            cons.setHeight(maxHeightSpring);
        }

        SpringLayout.Constraints lastCons = null;
        SpringLayout.Constraints lastRowCons = null;
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));
            if (i % cols == 0) {
                lastRowCons = lastCons;
                cons.setX(initialXSpring);
            } else {
                cons.setX(Spring.sum(lastCons.getConstraint(SpringLayout.EAST), xPadSpring));
            }

            if (i / cols == 0) {
                cons.setY(initialYSpring);
            } else {
                cons.setY(Spring.sum(lastRowCons.getConstraint(SpringLayout.SOUTH), yPadSpring));
            }
            lastCons = cons;
        }

        layout.getConstraints(parent).setConstraint(SpringLayout.SOUTH, Spring.sum(
                lastCons.getConstraint(SpringLayout.SOUTH), yPadSpring));
        layout.getConstraints(parent).setConstraint(SpringLayout.EAST, Spring.sum(
                lastCons.getConstraint(SpringLayout.EAST), xPadSpring));
    }
}

