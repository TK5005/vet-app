package src;
import java.awt.Dimension;

import src.control.AppController;
import src.model.DataModel;
import src.ui.AppWindow;

/**
 * VetApp is the main class for the application. It creates the model, controller,
 * and view objects and starts the application.
 */
public class VetApp
{
    public static void main(String[] args) 
    {
        DataModel model = new DataModel();
        AppController controller = new AppController(model);
        AppWindow appWindow = new AppWindow();
        appWindow.start(controller);
    }
}