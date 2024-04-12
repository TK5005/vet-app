package control;

import javax.swing.table.TableModel;
import javax.swing.event.TableModelListener;

import model.DataModel;

/**
 * AppController is the controller class for the application. It is responsible for
 * handling user input and updating the view. It communicates with the model to
 * retrieve data and update the view.
 */
public class AppController
{
    DataModel model;

    public AppController()
    {
        model = DataModel.getInstance();
    }

    public String[][] loadActivePatient()
    {
        String[][] data = model.loadActivePatient();
        System.out.println("Received data from model: " + data);
        return data;
    }
    public String[][] loadAppointments()
    {
        String[][] data = model.loadTempAppointments();
        return data;
    }
    public String[][] loadReview()
    {
        String[][] data = model.loadReview();
        return data;
    }
    public String[][] loadMedication()
    {
        String[][] data = model.loadMedication();
        return data;
    }
}