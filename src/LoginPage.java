import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import java.awt.event.ActionListener;
import java.io.Console;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginPage implements ActionListener {

    JPanel panel;
    JFrame frame;

    JLabel emaillabel;
    JLabel passwordlabel;

    private static JTextField emailText;
    private static JTextField passwordText;

    JButton loginButton;

    GridBagConstraints gbc = new GridBagConstraints();

    public LoginPage() {

        // settanje up frama pa pannela
        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(350, 240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Prijava");
        panel.setBackground(new Color(76, 218, 240));
        panel.setLayout(new GridBagLayout());
        frame.add(panel);

        // vsi objekti na framu
        emaillabel = new JLabel("User");
        emaillabel.setPreferredSize(new Dimension(50, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 15);

        panel.add(emaillabel, gbc);

        emailText = new JTextField();
        emailText.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        panel.add(emailText, gbc);

        passwordlabel = new JLabel("Password");
        passwordlabel.setPreferredSize(new Dimension(50, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 15);

        panel.add(passwordlabel, gbc);

        passwordText = new JPasswordField();
        passwordText.setPreferredSize(new Dimension(100, 20));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;

        panel.add(passwordText, gbc);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setBounds(100, 100, 120, 25);
        loginButton.setBackground(new Color(77, 152, 218));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;

        panel.add(loginButton, gbc);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new LoginPage();
    }

    // preveri login, dobi ali je ucitelj ali stars
    public int gettrust() {
        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();
            String sql = "SELECT login('" + emailText.getText() + "', '" + passwordText.getText() + "')";
            ResultSet rs = select.executeQuery(sql);
            System.out.println(emailText.getText() + "', '" + passwordText.getText());
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (Exception e) {
                /* Ignored */ }
        }

        return 0;
    }

    // event caller, dobi event ki ga izvede button in naredi nekaj
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String mail = emailText.getText();
            int babadoey = gettrust();

            if (babadoey == 0) {
                JOptionPane.showMessageDialog(null, "email ter koda se ne ujemata", "InfoBox: " + "napaka",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (babadoey == 2) {
                starsPregled sPregled = new starsPregled(mail);
                frame.dispose();

            } else if (babadoey == 1) {
                uciteljPregled uPregled = new uciteljPregled(mail);
                frame.dispose();
            }
        }
    }
}
