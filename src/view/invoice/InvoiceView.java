package view.invoice;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;

import control.IVetAppView;
import control.InvoiceController;

public class InvoiceView extends JPanel implements IVetAppView {
    private InvoiceController controller;
    private CardLayout layout;

    public InvoiceView() {
        this.controller = InvoiceController.getInstance();
        controller.registerView(this);
        controller.setInvoicePanel(this);
        createUI();
    }

    private void createUI() {
        layout = new CardLayout();
        setLayout(layout);
        setBackground(Color.WHITE);
        InvoiceListView listView = new InvoiceListView();
        add(listView, "list");
        layout.show(this, "list");
    }

    public void refresh() {
        // Refresh the view
    }

    public void showListView() {
        layout.show(this, "list");
    }
}