package src.model;

/**
 * DataModel is the model class for the application. It is responsible for
 * retrieving data from the database and returning it to the controller.
 */
public class DataModel
{
    public String loadTestData()
    {
        System.out.println("Model responding to load test data request");
        return "This is test data from the model";
    }
}