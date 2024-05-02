package control;

public class PetController extends ViewController {
    private static PetController instance;

    private PetController() {
        // Private constructor to prevent instantiation
    }

    public static PetController getInstance() {
        if (instance == null) {
            synchronized (PetController.class) {
                if (instance == null) {
                    instance = new PetController();
                }
            }
        }
        return instance;
    }
}
