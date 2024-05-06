package control;

import Repository.StaffRepository;
import model.Staff;
import ui.AdminPanel;

import java.time.LocalDate;

public class AdminController extends ViewController{

    private static AdminController instance;

    private StaffRepository staffRepository;

    private int currentStaffID = -1;

    private AdminPanel adminView;

    private AdminController(){staffRepository = new StaffRepository();}

    public static AdminController getInstance(){
        if(instance == null){
            synchronized (AdminController.class){
                if(instance == null){
                    instance = new AdminController();
                }
            }
        }
        return instance;
    }

    public void setAdminListView(AdminPanel adminView){this.adminView = adminView;}

    public void addNewStaff(){
        Staff staff = new Staff();
        staff.setDob(LocalDate.ofYearDay(1900,1));
        staff.setFirstName("New");
        staff.setLastName("User");
        staff.setCity("Newtown");
        staff.setState("MD");
        staff.setZip(00000);
        staff.setPhone("555-555-5555");
        staff.setSex("M");
        staff.setSsn("111-11-1111");
        staffRepository.addStaff(staff);

        refreshViews();
    }

    public Staff[] getStaff(){return staffRepository.Get();}

}
