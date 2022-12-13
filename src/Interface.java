// DATOS PERSONALES
// Nombre: José Manuel García
// C.I: 30.771.925
// Trayecto: 2-3
// Sección: 11

/* ASIGNACIÓN:
Escriba un Programa que permita hacer un CRUD sobre una tabla de empleados que conste de los siguientes campos:
CI
Nombres
Apellidos
Sueldo
*/

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class Interface extends JFrame {
    private JButton consultarBtn;
    private JButton ingresarBtn;
    private JTextField ciText;
    private JTextField nombreText;
    private JTextField apellidoText;
    private JTextField sueldoText;
    private JList lista;
    private JPanel panel;
    private JButton borrarBtn;
    Statement st;
    ResultSet r;

    PreparedStatement ps;
    DefaultListModel mod = new DefaultListModel();

    Connection con;
    final String URL = "jdbc:mysql://db4free.net/bd_empleados";
    final String USERNAME = "jose200217";
    final String PASSWORD = "contraseña";


    public Interface() {
        conectar();

        consultarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    listar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        ingresarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    insertar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        borrarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    borrar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    public void conectar(){
        try{
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Conectado");
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void listar() throws SQLException {
        lista.setModel(mod);
        st = con.createStatement();
        r = st.executeQuery("SELECT Cedula, Nombres, Apellidos, Sueldo FROM empleados");
        mod.removeAllElements();
        while (r.next()){
            mod.addElement(
                    "C.I: " + r.getString(1) +
                    " - Nombre: " + r.getString(2) +
                    " - Apellido: " + r.getString(3) +
                    " - Sueldo: " + r.getString(4)
            );
        }
    }

    public void insertar() throws SQLException {
        ps = con.prepareStatement("INSERT INTO empleados VALUES (?, ?, ?, ?)");
        ps.setInt(1, Integer.parseInt(ciText.getText()));
        ps.setString(2, nombreText.getText());
        ps.setString(3, apellidoText.getText());
        ps.setDouble(4, Double.parseDouble(sueldoText.getText()));

        if (ps.executeUpdate() > 0){
            lista.setModel(mod);
            mod.removeAllElements();
            mod.addElement("Insersion Exitosa!");

            ciText.setText("");
            nombreText.setText("");
            apellidoText.setText("");
            sueldoText.setText("");
        }
    }

    public void borrar() throws SQLException {
        mod.removeAllElements();
        mod.addElement("Todos los datos de la BD han sido borrados.");

        ps = con.prepareStatement("DELETE FROM empleados");
        ps.executeUpdate();


    }

    public static void main(String[] args){
        Interface f = new Interface();
        f.setContentPane(new Interface().panel);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();
    }

}
