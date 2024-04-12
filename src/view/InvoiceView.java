package view;

import java.awt.CardLayout;

import javax.swing.JPanel;

import control.InvoiceController;
import control.IInvoiceView;

public class InvoiceView extends JPanel implements IInvoiceView
{
    private InvoiceController controller;
    private CardLayout layout;

    public InvoiceView()
    {
        this.controller = InvoiceController.getInstance();
        controller.registerView(this);
        controller.setInvoicePanel(this);
        createUI();
    }

    private void createUI()
    {
        layout = new CardLayout();
        setLayout(layout);

        InvoiceListView listView = new InvoiceListView();
        InvoiceDetailView detailView = new InvoiceDetailView();
        add(listView, "list");
        add(detailView, "detail");
        layout.show(this, "list");
    }

    public void refresh()
    {
        // Refresh the view
    }

    public void showListView()
    {
        layout.show(this, "list");
    }

    public void showDetailView()
    {
        layout.show(this, "detail");
    }
}