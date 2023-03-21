import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.applet.Applet;
import org.postgresql.core.BaseConnection;
import org.postgresql.copy.CopyManager;

public class starsPregled implements ActionListener {

    JPanel panel;
    JFrame frame;

    ArrayList<ArrayList<String>> oceneAr = new ArrayList<ArrayList<String>>();

    String[][] dataArray = new String[1000][1000];

    JTable booksTable;
    JScrollPane scrollPane;

    JButton odjavaButton;

    GridBagConstraints gbc = new GridBagConstraints();

    public starsPregled(String gmail) {

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        frame = new JFrame();
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBackground(new Color(33, 42, 53));
        frame.setBackground(new Color(33, 42, 53));
        frame.setTitle("Stars");

        getGradesData(gmail);
        String vrste[] = { "ID", "Ocena", "Opis", "Ucenec", "Predmet", "Ucitelj" };
        DefaultTableModel tableModel = new DefaultTableModel(vrste, 0);

        for (int i = 0; i < oceneAr.size(); i++) {
            String idUcenca = oceneAr.get(i).get(0);
            String imeUcenca = oceneAr.get(i).get(1);
            String priimekUcenca = oceneAr.get(i).get(2);
            String oceneUcenca = oceneAr.get(i).get(3);
            String oceneUcenca1 = oceneAr.get(i).get(4);
            String oceneUcenca2 = oceneAr.get(i).get(5);

            String[] data = { idUcenca, imeUcenca, priimekUcenca, oceneUcenca, oceneUcenca1, oceneUcenca2 };

            tableModel.addRow(data);
        }

        booksTable = new JTable(tableModel);
        booksTable.setBackground(new Color(48, 56, 71));
        booksTable.setForeground(new Color(255, 255, 255));
        booksTable.setFont(new Font("Arial", Font.PLAIN, 15));
        booksTable.setShowVerticalLines(false);
        // booksTable.getColumnModel().setColumnMargin(20);
        booksTable.getTableHeader().setFont(new Font("Arial", Font.ITALIC, 16));
        booksTable.getTableHeader().setBackground(new Color(48, 56, 71));
        booksTable.getTableHeader().setForeground(new Color(255, 255, 255));
        booksTable.setRowHeight(30);

        TableColumn col0 = booksTable.getColumnModel().getColumn(0);
        TableColumn col1 = booksTable.getColumnModel().getColumn(1);
        TableColumn col2 = booksTable.getColumnModel().getColumn(2);
        TableColumn col3 = booksTable.getColumnModel().getColumn(3);
        TableColumn col4 = booksTable.getColumnModel().getColumn(4);
        TableColumn col5 = booksTable.getColumnModel().getColumn(5);

        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
        col0.setCellRenderer(dtcr);
        col1.setCellRenderer(dtcr);
        col2.setCellRenderer(dtcr);
        col3.setCellRenderer(dtcr);
        col4.setCellRenderer(dtcr);
        col5.setCellRenderer(dtcr);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 20, 5, 20);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        scrollPane = new JScrollPane(booksTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.getViewport().setBackground(new Color(33, 42, 53));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(33, 42, 53);
            }
        });
        scrollPane.getVerticalScrollBar().setBackground(new Color(50, 59, 70));
        scrollPane.setBackground(new Color(48, 56, 71));
        scrollPane.setVisible(true);
        scrollPane.setMinimumSize(new Dimension(400, 400));

        panel.add(scrollPane, gbc);

        odjavaButton = new RoundedJButton("Odjava");
        odjavaButton.setPreferredSize(new Dimension(150, 30));
        odjavaButton.addActionListener(this);
        odjavaButton.setBorder(null);
        odjavaButton.setBackground(new Color(41, 53, 66));
        odjavaButton.setForeground(new Color(255, 255, 255));

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 20, 5, 0);

        panel.add(odjavaButton, gbc);

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

                oceneAr.add(new ArrayList<String>());
                oceneAr.get(counter).add(0, chop[0]);
                oceneAr.get(counter).add(1, chop[1]);
                oceneAr.get(counter).add(2, chop[2]);
                oceneAr.get(counter).add(3, chop[3]);
                oceneAr.get(counter).add(4, chop[4]);
                oceneAr.get(counter).add(5, chop[5]);
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
        if (e.getSource() == odjavaButton) {
            try {
                LoginPage lPage = new LoginPage();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            frame.dispose();
        }
    }
}
