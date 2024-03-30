package src.control;
import src.model.DataModel;

/**
 * AppController is the controller class for the application. It is responsible for
 * handling user input and updating the view. It communicates with the model to
 * retrieve data and update the view.
 */
public class AppController
{
    DataModel model;

    public AppController(DataModel model)
    {
        this.model = model;
    }

    public String loadTestData()
    {
        System.out.println("Asking model for test data");
        String data = model.loadTestData();
        System.out.println("Received data from model: " + data);
        return data;
    }
}