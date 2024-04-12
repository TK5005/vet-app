import control.AppController;
import ui.AppWindow;

/**
 * VetApp is the main class for the application. It creates the model, controller,
 * and view objects and starts the application.
 */
public class VetApp
{
    public static void main(String[] args) 
    {
        AppController controller = new AppController();
        AppWindow appWindow = new AppWindow();
        appWindow.start(controller);
    }
}