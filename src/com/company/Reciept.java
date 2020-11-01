package com.company;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class Reciept extends JFrame {
    public static DefaultTableModel receiptTableModel = new DefaultTableModel();

    public Reciept() {
        setVisible(false);
        setSize(400, 700);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        Container container = getContentPane();

        // Receipt Header
        JTextPane headerText = new JTextPane();
        headerText.setText(
                "\nJOLLIBEE" +
                        "\n==Customers Copy==" +
                        "\nUnknown Place, Dasmariñas City, Cavite" +
                        "\nTel: ***********" +
                        "\nCounter #: " +
                        "\n******************************************\n"
        );
        StyledDocument headerDoc = headerText.getStyledDocument();
        SimpleAttributeSet headerCenter = new SimpleAttributeSet();
        StyleConstants.setAlignment(headerCenter, StyleConstants.ALIGN_CENTER);
        headerDoc.setParagraphAttributes(0, headerDoc.getLength(), headerCenter, false);


        //Receipt Footer
        JTextPane footerText = new JTextPane();
        footerText.setText(
                "\n******************************************" +
                        "\nThis serves as your Official Receipt." +
                        "\nThank you, and please come again" +
                        "\nFor feedback, please call us at" +
                        "\nTel: ***********" +
                        "\nEmail: feedback.sample@sample.com" +
                        "\nVisit us also at www.jollibee.com.ph\n"
        );
        StyledDocument footerDoc = footerText.getStyledDocument();
        SimpleAttributeSet footerCenter = new SimpleAttributeSet();
        StyleConstants.setAlignment(footerCenter, StyleConstants.ALIGN_CENTER);
        footerDoc.setParagraphAttributes(0, footerDoc.getLength(), footerCenter, false);


        //Main Receipt
        JPanel mainReceiptPanel = new JPanel();
        mainReceiptPanel.setBackground(Color.white);
        mainReceiptPanel.setLayout(new BoxLayout(mainReceiptPanel, BoxLayout.Y_AXIS));

        String[] receiptColumnIdentifiers = {"Name", "Total"};
        receiptTableModel.setColumnIdentifiers(receiptColumnIdentifiers);

        String[] rowData = {"Sample", "2"};
        receiptTableModel.addRow(rowData);

        JTable receiptTable = new JTable(receiptTableModel);

        mainReceiptPanel.add(new JScrollPane(receiptTable));
        mainReceiptPanel.add(new JLabel("TOTAL: " + Main.TOTAL));

        container.add(headerText, BorderLayout.NORTH);
        container.add(mainReceiptPanel, BorderLayout.CENTER);
        container.add(footerText, BorderLayout.SOUTH);
    }
}
