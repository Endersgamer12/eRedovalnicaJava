import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.io.Console;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginPage implements ActionListener {

    JPanel panel;
    JFrame frame;

    JLabel usernamelabel;
    JLabel passwordlabel;

    private static JTextField userText;
    private static JPasswordField passwordText;

    JButton loginButton;
    JButton registerButton;

    public LoginPage() {

        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(350, 240);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Prijava");
        panel.setBackground(new Color(76, 218, 240));
        frame.add(panel);

        panel.setLayout(null);

        usernamelabel = new JLabel("User");
        usernamelabel.setBounds(10, 20, 80, 25);
        panel.add(usernamelabel);

        userText = new JTextField();
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        passwordlabel = new JLabel("Password");
        passwordlabel.setBounds(10, 60, 80, 25);
        panel.add(passwordlabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100, 60, 165, 25);
        panel.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setBounds(100, 100, 120, 25);
        loginButton.setBackground(new Color(77, 152, 218));
        panel.add(loginButton);

        registerButton = new JButton("Need Account?");
        registerButton.addActionListener(this);
        registerButton.setBounds(110, 150, 100, 20);
        registerButton.setFont(new Font("Arial", Font.PLAIN, 8));
        registerButton.setBackground(new Color(77, 152, 218));
        panel.add(registerButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new LoginPage();
    }

    public int gettrust() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://ep-wild-darkness-767526.eu-central-1.aws.neon.tech/neondb?user=nik.krnjovsek&password=a1hjwRmFZeE0",
                            "nik.krnjovsek", "a1hjwRmFZeE0");
            Statement select = c.createStatement();
            String sql = "SELECT login('" + userText.getText() + "', '" + passwordText.getText() + "')";
            ResultSet rs = select.executeQuery(sql);
            if (rs.next()) {
                if (rs.getBoolean(1)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            int babadoey = gettrust();
            if (babadoey == 1) {
                Dashboard dashboard = new Dashboard();
                frame.dispose();
            }
        }
        if (e.getSource() == registerButton) {

        }
    }
}
