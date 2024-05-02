package view.clientPatient;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;

import control.ClientController;
import control.IClientView;
import view.exam.ExamRecordView;
import view.pet.PetInfoView;

public class ClientPageView extends JPanel implements IClientView {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private ClientController controller;
    ClientsView clientsView;
    ClientInfoView clientInfo;
    PetInfoView petInfo;
    ExamRecordView examInfo;

    public ClientPageView() {
        controller = ClientController.getInstance();
        controller.setClientPage(this);
        controller.registerView(this);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        clientsView = new ClientsView();
        clientInfo = new ClientInfoView();
        petInfo = new PetInfoView();
        examInfo = new ExamRecordView();

        // Add your views to the card panel
        cardPanel.add(clientsView, "clientsView");
        cardPanel.add(clientInfo, "clientInfo");
        cardPanel.add(petInfo, "petInfo");
        cardPanel.add(examInfo, "examInfo");

        // Set the initial view to be shown
        cardLayout.show(cardPanel, "clientsView");

        // Add the card panel to the main panel
        setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);
    }

    public void refresh() {

    }

    public void showClientInfo() {
        cardLayout.show(cardPanel, "clientInfo");
    }

    public void showPetInfo() {
        cardLayout.show(cardPanel, "petInfo");
    }

    public void closeClientInfoView() {
        cardLayout.show(cardPanel, "clientsView");
    }

    public void closePetInfoView() {
        cardLayout.show(cardPanel, "clientInfo");
    }

    public void showExamInfo() {
        cardLayout.show(cardPanel, "examInfo");
    }

    public void closeExamInfoView() {
        petInfo.refresh();
        cardLayout.show(cardPanel, "petInfo");
    }
}