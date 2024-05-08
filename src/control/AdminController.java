package control;

import Repository.StaffRepository;
import model.Staff;
import model.Tech;
import model.Vet;
import ui.AdminPanel;
import java.sql.Date;
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

    public void addNewStaff(String firstName, String lastName, Date dob, String street, String city,
                            String state, int zip, String phone, String sex, String ssn){
        Staff staff = new Staff();
        staff.setDob(dob);
        staff.setFirstName(firstName);
        staff.setLastName(lastName);
        staff.setStreet(street);
        staff.setCity(city);
        staff.setState(state);
        staff.setZip(zip);
        staff.setPhone(phone);
        staff.setSex(sex);
        staff.setSsn(ssn);
        staffRepository.addStaff(staff);

        refreshViews();
    }

    public void updateStaff(int empID,String firstName, String lastName, LocalDate dob, String street, String city,
                            String state, int zip, String phone, String sex, String ssn){
        Staff staff = new Staff();
        staff.setEmpID(empID);
       // staff.setDob(dob);
        staff.setFirstName(firstName);
        staff.setLastName(lastName);
        staff.setStreet(street);
        staff.setCity(city);
        staff.setState(state);
        staff.setZip(zip);
        staff.setPhone(phone);
        staff.setSex(sex);
        staff.setSsn(ssn);
        staffRepository.updateStaff(staff);

        refreshViews();
    }

    public void addNewVet(String firstName, String lastName, Date dob, String street, String city,
                          String state, int zip, String phone, String sex, String ssn, String licenseNo, String[] specialties){

        Vet vet = new Vet();
        vet.setDob(dob);
        vet.setFirstName(firstName);
        vet.setLastName(lastName);
        vet.setStreet(street);
        vet.setCity(city);
        vet.setState(state);
        vet.setZip(zip);
        vet.setPhone(phone);
        vet.setSex(sex);
        vet.setSsn(ssn);
        vet = staffRepository.addVet(vet);
        staffRepository.updateSpecialties(vet.getEmpID(), specialties);

        refreshViews();
    }

    public void updateVet(int empID,String firstName, String lastName, Date dob, String street, String city,
                          String state, int zip, String phone, String sex, String ssn, String licenseNo, String[] specialties){

        Vet vet = new Vet();
        vet.setEmpID(empID);
        vet.setDob(dob);
        vet.setFirstName(firstName);
        vet.setLastName(lastName);
        vet.setStreet(street);
        vet.setCity(city);
        vet.setState(state);
        vet.setZip(zip);
        vet.setPhone(phone);
        vet.setSex(sex);
        vet.setSsn(ssn);
        vet = staffRepository.addVet(vet);
        staffRepository.updateSpecialties(vet.getEmpID(), specialties);

        refreshViews();
    }

    public void addNewTech(String firstName, String lastName, LocalDate dob, String street, String city,
                           String state, int zip, String phone, String sex, String ssn, String[] certifications){

        Tech tech = new Tech();
       // tech.setDob(dob);
        tech.setFirstName(firstName);
        tech.setLastName(lastName);
        tech.setStreet(street);
        tech.setCity(city);
        tech.setState(state);
        tech.setZip(zip);
        tech.setPhone(phone);
        tech.setSex(sex);
        tech.setSsn(ssn);
        tech = staffRepository.addTech(tech);
        staffRepository.updateCertifications(tech.getEmpID(), certifications);

        refreshViews();
    }

    public void updateTech(int empID, String firstName, String lastName, LocalDate dob, String street, String city,
                           String state, int zip, String phone, String sex, String ssn, String[] certifications){

        Tech tech = new Tech();
        tech.setEmpID(empID);
       // tech.setDob(dob);
        tech.setFirstName(firstName);
        tech.setLastName(lastName);
        tech.setStreet(street);
        tech.setCity(city);
        tech.setState(state);
        tech.setZip(zip);
        tech.setPhone(phone);
        tech.setSex(sex);
        tech.setSsn(ssn);
        tech = staffRepository.addTech(tech);
        staffRepository.updateCertifications(tech.getEmpID(), certifications);

        refreshViews();
    }

    public Staff[] getStaff(){return staffRepository.Get();}

    public Staff getStaffByID(int empID){return staffRepository.getStaff(empID);}

    public Vet getVetByID(int empID){return staffRepository.getVet(empID);}

    public Tech getTechByID(int empID){return staffRepository.getTech(empID);}

}
