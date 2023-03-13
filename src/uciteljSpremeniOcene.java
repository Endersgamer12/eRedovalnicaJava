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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

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
import java.util.ArrayList; // import the ArrayList class

public class uciteljSpremeniOcene implements ActionListener, ListSelectionListener {

    JPanel panel;
    JFrame frame;

    String meil;
    String prdmt;
    Integer ide;
    Integer counter = 3;

    ArrayList<ArrayList<String>> oceneAr = new ArrayList<ArrayList<String>>();

    String[][] dataArray = new String[1000][1000];

    DefaultTableModel tableModel;

    JButton backButton;
    JButton newgradeButton;

    JTable booksTable;
    JScrollPane scrollPane;

    GridBagConstraints gbc = new GridBagConstraints();

    public uciteljSpremeniOcene(String gmail, Integer id, String predmet) {

        meil = gmail;
        ide = id;
        prdmt = predmet;

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        frame = new JFrame();
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBackground(new Color(76, 218, 240));
        frame.setTitle("Ucitelj");

        backButton = new RoundedJButton("Nazaj");
        backButton.setPreferredSize(new Dimension(70, 30));
        backButton.addActionListener(this);
        backButton.setBorder(null);
        backButton.setBackground(new Color(41, 53, 66));
        backButton.setForeground(new Color(255, 255, 255));

        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 1;

        panel.add(backButton, gbc);

        newgradeButton = new RoundedJButton("Nova ocena");
        newgradeButton.setPreferredSize(new Dimension(70, 30));
        newgradeButton.addActionListener(this);
        newgradeButton.setBorder(null);
        newgradeButton.setBackground(new Color(41, 53, 66));
        newgradeButton.setForeground(new Color(255, 255, 255));

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;

        panel.add(newgradeButton, gbc);

        getGradesData();
        String vrste[] = { "ID", "Ocena", "Opis" };
        tableModel = new DefaultTableModel(vrste, 0);

        for (int i = 0; i < oceneAr.size(); i++) {
            String idUcenca = oceneAr.get(i).get(0);
            String imeUcenca = oceneAr.get(i).get(1);
            String priimekUcenca = oceneAr.get(i).get(2);

            String[] data = { idUcenca, imeUcenca, priimekUcenca };

            tableModel.addRow(data);
        }
        booksTable = new JTable(tableModel);
        booksTable.getTableHeader().setOpaque(false);
        booksTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        booksTable.getTableHeader().setBackground(new Color(77, 220, 180));
        booksTable.setDefaultEditor(Object.class, null);
        booksTable.getColumnModel().getColumn(0).setPreferredWidth(1);
        booksTable.setFont(new Font("Arial", Font.PLAIN, 14));
        booksTable.setBackground(new Color(77, 218, 180));
        booksTable.setBorder(BorderFactory.createEmptyBorder());
        booksTable.getSelectionModel().addListSelectionListener(this);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 5;

        gbc.fill = GridBagConstraints.HORIZONTAL;
        scrollPane = new JScrollPane(booksTable);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        scrollPane.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane.setVisible(true);
        panel.add(scrollPane, gbc);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new uciteljSpremeniOcene("vlasta.leban@scv.si", 3, "Anglescina");
    }

    public void getGradesData() {

        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://trumpet.db.elephantsql.com:5432/vnkkwcle",
                            "vnkkwcle", "Ha3l6m0s1K4y8Uax3R_AmfynSejagg8H");
            Statement select = c.createStatement();

            String sql = "SELECT uciteljPregledDijaka('" + meil + "', " + ide + ", '" + prdmt + "')";
            ResultSet rs = select.executeQuery(sql);
            Integer counter = 0;
            oceneAr.clear();
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
                dataArray[counter][0] = idOcene;
                dataArray[counter][1] = stevilkaOcene;
                dataArray[counter][2] = opisOcene;
                oceneAr.add(new ArrayList<String>());
                oceneAr.get(counter).add(0, chop[0]);
                oceneAr.get(counter).add(1, chop[1]);
                oceneAr.get(counter).add(2, chop[2]);
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

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (int i = 0; i < oceneAr.size(); i++) {
            String idUcenca = oceneAr.get(i).get(0);
            String imeUcenca = oceneAr.get(i).get(1);
            String priimekUcenca = oceneAr.get(i).get(2);

            String[] data = { idUcenca, imeUcenca, priimekUcenca };

            tableModel.addRow(data);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            uciteljPregled uPregled = new uciteljPregled(meil);
            frame.dispose();
        } else if (e.getSource() == newgradeButton) {
            dodajOceno dOceno = new dodajOceno(ide, prdmt, meil, this);
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (counter % 3 == 0) {
            counter++;

            int row = booksTable.getSelectedRow();
            Integer id = Integer.parseInt(booksTable.getModel().getValueAt(row, 0).toString());
            Integer ocena = Integer.parseInt(booksTable.getModel().getValueAt(row, 1).toString());
            String opis = booksTable.getModel().getValueAt(row, 2).toString();

            spremeniOceno sOceno = new spremeniOceno(id, ocena, opis, this);
            booksTable.clearSelection();
        }
    }

}