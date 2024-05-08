package control;

import Repository.ExamRepository;
import Repository.StaffRepository;
import model.*;
import ui.AdminPanel;
import java.sql.Date;
import java.time.LocalDate;

public class AdminController extends ViewController{

    private static AdminController instance;

    private StaffRepository staffRepository;
    private ExamRepository examRepository;

    private int currentStaffID = -1;

    private AdminPanel adminView;

    private AdminController(){
        staffRepository = new StaffRepository();
        examRepository = new ExamRepository();
    }

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

    public void addNewStaff(Staff staff){

        staffRepository.addStaff(staff);

        refreshViews();
    }

    public void updateStaff(Staff staff){

        staffRepository.updateStaff(staff);

        refreshViews();
    }

    public void addNewVet(Vet vet, String[] specialties){

        vet = staffRepository.addVet(vet);
        staffRepository.updateSpecialties(vet.getEmpID(), specialties);

        refreshViews();
    }

    public void updateVet(Vet vet, String[] specialties){

        staffRepository.updateVet(vet);
        staffRepository.updateSpecialties(vet.getEmpID(), specialties);

        refreshViews();
    }

    public void addNewTech(Tech tech, String[] certifications){

        tech = staffRepository.addTech(tech);
        staffRepository.updateCertifications(tech.getEmpID(), certifications);

        refreshViews();
    }

    public void updateTech(Tech tech, String[] certifications){
        staffRepository.updateTech(tech);
        staffRepository.updateCertifications(tech.getEmpID(), certifications);

        refreshViews();
    }
    public void deleteStaff(int empID){
        staffRepository.deleteStaff(empID);
        refreshViews();
    }

    public void deleteVet(int empID){
        examRepository.deleteVetExams(empID);
        staffRepository.deleteVet(empID);
        refreshViews();
    }

    public void deleteTech(int empID){
        examRepository.deleteTechExams(empID);
        staffRepository.deleteTech(empID);
        refreshViews();
    }


    public Staff[] getStaff(){return staffRepository.Get();}
    public Staff[] getGeneralStaff(){return staffRepository.getGeneralStaff();}
    public Vet[] getVets(){return staffRepository.getVets();}
    public Tech[] getTechs(){return staffRepository.getTechs();}

    public Staff getStaffByID(int empID){return staffRepository.getStaff(empID);}

    public Vet getVetByID(int empID){return staffRepository.getVet(empID);}

    public Tech getTechByID(int empID){return staffRepository.getTech(empID);}

    public Certification[] getCertificationsByTechID(int empID) { return staffRepository.getCertifications(empID);}
    public Specialty[] getSpecialtiesByVetID(int empID){return staffRepository.getSpecialties(empID);}
}
