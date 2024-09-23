import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
class VentanaPrincipal extends JFrame {
    private ArrayList<User> users;
    private JTable table;
    private DefaultTableModel tableModel;
    private int nextId = 1;

    public VentanaPrincipal() {
        users = new ArrayList<>();
        setTitle("Registro de trabajadores");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel botones = new JPanel();

        JButton Crear = new JButton("Crear");
        JButton Editar = new JButton("Editar");
        JButton Eliminar = new JButton("Eliminar");
        JButton Buscar = new JButton("Buscar");
        JButton Salir = new JButton("Salir");

        botones.add(Crear);
        botones.add(Editar);
        botones.add(Eliminar);
        botones.add(Buscar);
        botones.add(Salir);

        add(botones, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);   /*tabla */

        Crear.addActionListener(e -> crearUsuario());
        Editar.addActionListener(e -> editarUsuario());
        Eliminar.addActionListener(e -> eliminarUsuario());
        Buscar.addActionListener(e -> buscarUsuario());
        Salir.addActionListener(e -> System.exit(0));   /*acciones de los botones */
    }

    private void crearUsuario() {
        JTextField nombreField = new JTextField();
        JTextField apellidoField = new JTextField();
        Object[] message = {
            "Nombre:", nombreField,
            "Apellido:", apellidoField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Crear", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText().trim();
            String apellido = apellidoField.getText().trim();
            if (!nombre.isEmpty() && !apellido.isEmpty()) {
                User user = new User(nextId++, nombre, apellido);
                users.add(user);
                actualizarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Nombre y apellido no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void editarUsuario() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = (int) tableModel.getValueAt(selectedRow, 0);
            User user = encontrarUsuarioPorId(userId);
            if (user != null) {
                JTextField nombreField = new JTextField(user.getNombre());
                JTextField apellidoField = new JTextField(user.getApellido());
                Object[] message = {
                    "Nombre:", nombreField,
                    "Apellido:", apellidoField
                };

                int option = JOptionPane.showConfirmDialog(this, message, "Editar", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    String nombre = nombreField.getText().trim();
                    String apellido = apellidoField.getText().trim();
                    if (!nombre.isEmpty() && !apellido.isEmpty()) {
                        user.setNombre(nombre);
                        user.setApellido(apellido);
                        actualizarTabla();
                    } else {
                        JOptionPane.showMessageDialog(this, "Nombre y apellido no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario para editar.", "Editar", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void eliminarUsuario() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este usuario?", "Eliminar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int userId = (int) tableModel.getValueAt(selectedRow, 0);
                User user = encontrarUsuarioPorId(userId);
                if (user != null) {
                    users.remove(user);
                    actualizarTabla();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarUsuario() {
        String[] opciones = {"ID", "Nombre", "Apellido"};
        String criterio = (String) JOptionPane.showInputDialog(this, "Buscar por:", "Buscar Usuario", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (criterio != null) {
            String valor = JOptionPane.showInputDialog(this, "Ingrese el " + criterio + " a buscar:");
            if (valor != null && !valor.trim().isEmpty()) {
                ArrayList<User> resultados = new ArrayList<>();
                for (User user : users) {
                    switch (criterio) {
                        case "ID":
                            try {
                                if (user.getId() == Integer.parseInt(valor.trim())) {
                                    resultados.add(user);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            break;
                        case "Nombre":
                            if (user.getNombre().equalsIgnoreCase(valor.trim())) {
                                resultados.add(user);
                            }
                            break;
                        case "Apellido":
                            if (user.getApellido().equalsIgnoreCase(valor.trim())) {
                                resultados.add(user);
                            }
                            break;
                    }
                }

                if (!resultados.isEmpty()) {
                    mostrarResultados(resultados);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron resultados.", "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void mostrarResultados(ArrayList<User> resultados) {/*tabala temporal */
        DefaultTableModel resultModel = new DefaultTableModel(new Object[]{"ID", "Nombre", "Apellido"}, 0);
        for (User user : resultados) {
            resultModel.addRow(new Object[]{user.getId(), user.getNombre(), user.getApellido()});
        }
        JTable resultTable = new JTable(resultModel);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        JOptionPane.showMessageDialog(this, scrollPane, "Resultados de la Búsqueda", JOptionPane.INFORMATION_MESSAGE);
    }

    private User encontrarUsuarioPorId(int id) {
        for (User user : users) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);
        for (User user : users) {
            tableModel.addRow(new Object[]{user.getId(), user.getNombre(), user.getApellido()});
        }
    }
}

/*
git init 
git add . 
git commit -m "mi tarea"  
git remote add origin https://github.com/OnetOFuX/CRUD.git
git push origin master   
*/