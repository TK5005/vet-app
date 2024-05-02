package control;

import java.util.ArrayList;

public class ViewController {
    private ArrayList<IVetAppView> views;

    /*
     * Constructor
     */
    public ViewController() {
        views = new ArrayList<IVetAppView>();
    }

    /*
     * Register a view with the controller
     */
    public void registerView(IVetAppView view) {
        views.add(view);
    }

    /**
     * Refresh the views
     */
    protected void refreshViews() {
        for (IVetAppView view : views) {
            view.refresh();
        }
    }
}