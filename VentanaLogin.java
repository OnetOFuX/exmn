import javax.swing.*;
import java.awt.*;

class VentanaLogin extends JFrame {
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;

    public VentanaLogin() {
        setTitle("Iniciar sesion");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(3, 1));

        JPanel passPanel = new JPanel();
        passPanel.add(new JLabel("Password: "));
        passwordField = new JPasswordField(15);
        passPanel.add(passwordField);
        panel.add(passPanel);

        loginButton = new JButton("Ingresar");
        panel.add(loginButton);

        statusLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(statusLabel);

        add(panel);

        // Acción del botón de login
        loginButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            if (password.equals("gatorata")) {
                // Abrir la ventana principal
                new VentanaPrincipal().setVisible(true);
                this.dispose();
            } else {
                statusLabel.setText("Contraseña incorrecta.");
            }
        });
    }
}