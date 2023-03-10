import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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

public class starsPregled implements ActionListener {

    JPanel panel;
    JFrame frame;

    String[][] dataArray = new String[1000][1000];

    JTable booksTable;
    JScrollPane scrollPane;

    GridBagConstraints gbc = new GridBagConstraints();

    public starsPregled(String gmail) {

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        frame = new JFrame();
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBackground(new Color(76, 218, 240));
        frame.setTitle("Stars");

        getGradesData(gmail);
        String vrste[] = { "ID", "Stevilka", "Opis", "Ucenec", "Predmet", "Ucitelj" };
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
        gbc.gridy = 0;
        gbc.gridwidth = 5;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        scrollPane = new JScrollPane(booksTable);
        scrollPane.setPreferredSize(new Dimension(800, 500));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(20, 160, 185), 2));
        scrollPane.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane.setVisible(true);
        panel.add(scrollPane, gbc);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new starsPregled("joze.kuznik@gmail.com");
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

            String sql = "SELECT starsPregledOcene('" + gmail + "')";
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
                String idOcene = chop[0];
                String stevilkaOcene = chop[1];
                String opisOcene = chop[2];
                String ucenecIme = chop[3];
                String predmet = chop[4];
                String uciteljIme = chop[5];
                dataArray[counter][0] = idOcene;
                dataArray[counter][1] = stevilkaOcene;
                dataArray[counter][2] = opisOcene;
                dataArray[counter][3] = ucenecIme;

                dataArray[counter][4] = predmet;
                dataArray[counter][5] = uciteljIme;
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

    }
}
