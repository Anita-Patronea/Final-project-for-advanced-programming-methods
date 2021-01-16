package services;

import domain.Appointment;
import domain.Patient;
import repository.AppointmentRepository;
import repository.PatientRepository;
import repository.RepositoryPersistenceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DentistAppointmentServices {

    private PatientRepository patientRepository;
    private AppointmentRepository appointmentRepository;

    public DentistAppointmentServices(PatientRepository patientRepository, AppointmentRepository appointmentRepository){
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
    }

    //PATIENT
    public Integer addPatient (String name, String CNP, String phoneNumber, String email){
        try {
            return patientRepository.add(new Patient(0, name, CNP, phoneNumber, email));
        }catch (RepositoryPersistenceException ex) {
            throw new DentistAppointmentServicesException("Error adding patient: " + ex);
        }
    }

    public void deletePatient (Integer id) {
        try {
            patientRepository.delete(id);
        }catch (RepositoryPersistenceException ex) {
            throw new DentistAppointmentServicesException("Error deleting patient: " + ex);
        }
    }

    public void updatePatient (Integer id, String name, String CNP, String phoneNumber, String email) {
        try {
            patientRepository.update(new Patient(0, name, CNP, phoneNumber, email), id);
        }catch (RepositoryPersistenceException ex) {
            throw new DentistAppointmentServicesException("Error updating patient: " + ex);
        }
    }

    public Patient findByIdPatient (Integer id){
        return patientRepository.findById(id);
    }

    public List<Patient> getAllPatient(){
        return patientRepository.getAll().stream().collect(Collectors.toList());
    }

    public List<Patient> sortByIdPatient(Integer integer){
        return patientRepository.sortByID();
    }

    public List<Patient> sortByNamePatient (){
        return patientRepository.sortByName();
    }

    public List<Patient> filterByNamePatient (String name){
        return patientRepository.filterByName(name);
    }

    public List<Patient> filterByCNPPatient (String CNP){
        return patientRepository.filterByCNP(CNP);
    }



    //APPOINTMENT
    public Integer addAppointment (Integer patientID, String service, LocalDateTime date){
        try {
            Patient p = new Patient(patientRepository.findById(patientID));
            return appointmentRepository.add(new Appointment(0, p, service, date));
        }catch (RepositoryPersistenceException ex) {
            throw new DentistAppointmentServicesException("Error adding appointment: " + ex);
        }
    }

    public void deleteAppointment (Integer id) {
        try {
            appointmentRepository.delete(id);
        }catch (RepositoryPersistenceException ex) {
            throw new DentistAppointmentServicesException("Error deleting appointment: " + ex);
        }
    }

    public void updateAppointment (Integer id, Integer patientID, String service, LocalDateTime date) {
        try {
            Patient p = new Patient(patientRepository.findById(patientID));
            appointmentRepository.update(new Appointment(0, p, service, date), id);
        }catch (RepositoryPersistenceException ex) {
            throw new DentistAppointmentServicesException("Error updating appointment: " + ex);
        }
    }


    public Appointment findByIdAppointment (Integer id){
        return appointmentRepository.findById(id);
    }

    public List<Appointment> getAllAppointment(){ return appointmentRepository.getAll().stream().collect(Collectors.toList()); }

    public List<Appointment> sortByIdAppointment (){
        return appointmentRepository.sortByID();
    }

    public List<Appointment> sortByServiceAppointment (){ return appointmentRepository.sortByService(); }

    public List<Appointment> sortByDateAppointment (){ return appointmentRepository.sortByDate(); }

    public List<Appointment> filterByPatientIdAppointment (Integer id){ return appointmentRepository.filterByPatientID(id); }

    public List<Appointment> filterByServiceAppointment (String service){ return appointmentRepository.filterByService(service); }

    public List<Appointment> filterByDateAppointment (LocalDateTime date){ return appointmentRepository.filterByDate(date); }

    public Integer totalNumberOfAppointmentsToday (){ return appointmentRepository.filterByDate(LocalDateTime.now()).size();}
}
