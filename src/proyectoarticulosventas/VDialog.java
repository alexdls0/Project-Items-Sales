package proyectoarticulosventas;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VDialog extends JDialog {

    public VDialog(Connection conexion, Ventana v, String[] cabecera, StringBuilder enlace, StringBuilder est) {
        super(v, true); //Con true bloquea la ventana principal
        JPanel panel = new JPanel();
        panel.setLayout(null);
        ArrayList<JTextField> nuevo = new ArrayList<>();
        int posicionfinal = 0;
        for (int i = 0; i < cabecera.length; i++) {
            JLabel cab = new JLabel(cabecera[i]);
            cab.setBounds(100, 30 * (i + 1), 100, 30);

            JTextField resp = new JTextField();
            resp.setBounds(200, 30 * (i + 1), 100, 30);

            nuevo.add(resp);

            panel.add(cab);
            panel.add(resp);

            posicionfinal = i;
        }

        JButton volverBt = new JButton("Volver");
        volverBt.setBounds(200, 30 * (posicionfinal + 2), 100, 30);
        volverBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.add(volverBt);

        JButton guardarBt = new JButton("Guardar");
        guardarBt.setBounds(100, 30 * (posicionfinal + 2), 100, 30);
        guardarBt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                est.replace(0, est.length(), "Correcto");
                try {
                    String consulta = "insert into empresa.empleados values (";
                    for (int i = 0; i < cabecera.length; i++) {
                        if (i == cabecera.length - 1) {
                            consulta += "?)";
                        } else {
                            consulta += "?,";
                        }
                    }

                    PreparedStatement inserta = conexion.prepareStatement(consulta);
                    for (int i = 0; i < nuevo.size(); i++) {
                        inserta.setObject(i + 1, nuevo.get(i).getText());
                        enlace.append(nuevo.get(i).getText());
                        enlace.append(" ");
                    }
                    inserta.executeUpdate();
                } catch (Exception exc) {
                    est.replace(0, est.length(), "Error");
                    System.out.println("Error");
                }
                dispose();
            }
        });
        this.add(guardarBt);

        this.add(panel);
        this.setTitle("Insertar");
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
