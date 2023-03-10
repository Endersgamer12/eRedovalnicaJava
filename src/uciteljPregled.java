import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Objects;
import java.applet.Applet;
import org.postgresql.core.BaseConnection;
import org.postgresql.copy.CopyManager;

public class uciteljPregled implements ActionListener {

    JPanel panel;
    JFrame frame;

    String meil;

    String[][] dataArray = new String[1000][1000];

    JComboBox razred;

    JTable booksTable;
    JScrollPane scrollPane;

    GridBagConstraints gbc = new GridBagConstraints();

    public uciteljPregled(String gmail) {

        meil = gmail;

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        frame = new JFrame();
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBackground(new Color(76, 218, 240));
        frame.setTitle("Ucitelj");

        String[] arRazredi = { "4tra", "4trb" };
        razred = new JComboBox(arRazredi);
        razred.setSelectedIndex(0);
        razred.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 10, 15);
        panel.add(razred, gbc);

        getGradesData(gmail);
        String vrste[] = { "ID", "Ime", "Priimek", "Ocene" };
        booksTable = new JTable(dataArray, vrste);
        booksTable.getTableHeader().setOpaque(false);
        booksTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        booksTable.getTableHeader().setBackground(new Color(77, 220, 180));
        booksTable.getTableHeader().setBorder(BorderFactory.createLineBorder(new Color(20, 160, 185), 1));
        booksTable.setDefaultEditor(Object.class, null);
        booksTable.getColumnModel().getColumn(0).setPreferredWidth(1);
        booksTable.setFont(new Font("Arial", Font.PLAIN, 14));
        booksTable.setBackground(new Color(77, 218, 180));
        booksTable.setBorder(BorderFactory.createEmptyBorder());

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        scrollPane = new JScrollPane(booksTable);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(20, 160, 185), 2));
        scrollPane.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane.setVisible(true);
        panel.add(scrollPane, gbc);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new uciteljPregled("vlasta.leban@scv.si");
    }

    public void getGradesData(String gmail) {

        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();

            String sql = "SELECT uciteljPregledOcene('" + gmail + "', '" + razred.getSelectedItem().toString() + "')";
            ResultSet rs = select.executeQuery(sql);
            Integer counter = 0;
            while (rs.next()) {
                String item = rs.getString(1);
                item = item.replace("(", "");
                item = item.replace(")", "");
                item = item.replace("\"", "");
                item = item.replace("{", "");
                item = item.replace("}", "");

                String[] chop = item.split(",");
                String idUcenca = chop[0];
                String imeUcenca = chop[1];
                String priimekUcenca = chop[2];
                String ocene = chop[3];
                dataArray[counter][0] = idUcenca;
                dataArray[counter][1] = imeUcenca;
                dataArray[counter][2] = priimekUcenca;
                dataArray[counter][3] = ocene;
                counter++;
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (Exception e) {
                /* Ignored */ }
        }
    }

    public void deleteBook() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();
            String sql = "SELECT ime FROM ucenci LIMIT 1";
            ResultSet rs = select.executeQuery(sql);
            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == razred) {
            getGradesData(meil);
            booksTable.repaint();
        }

    }
}
