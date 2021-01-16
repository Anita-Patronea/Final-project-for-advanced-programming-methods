package controller;

import domain.Appointment;
import domain.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import services.DentistAppointmentServices;


import java.time.LocalDateTime;
import java.util.List;

public class DentistAppointmentController {

    //connection to services
    private DentistAppointmentServices dentistAppointmentServices;

    //for Patient
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private Text totalNumberOfPatients;
    @FXML
    private TextField idFilterPatient, nameFilterPatient, cnpFilterPatient;
    @FXML
    private Text idPatient;
    @FXML
    private TextField namePatient, cnpPatient, phoneNumberPatient, emailPatient;

    //for Appointment
    @FXML
    private TableView<Appointment> appointmentTable;
    @FXML
    private Text currentDateAppointmentsNo;
    @FXML
    private TextField idFilterAppointment, idPatientFilterAppointment, serviceFilterAppointment;
    @FXML
    private DatePicker dateFilterAppointment;
    @FXML
    private Text idAppointment;
    @FXML
    private TextField idPatientInAppointment, serviceAppointment, timeAppointment;
    @FXML
    private DatePicker dateAppointment;

    //constructor
    public DentistAppointmentController() {
    }

    //setting the service
    public void setService(DentistAppointmentServices services) {
        this.dentistAppointmentServices = services;
    }

    //useful functions
    private boolean checkIfString(String s) {
        return s != null && !s.isEmpty();
    }

    private void showNotification(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Notification");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /////////PATIENT/////////////
    //fill patientTable
    @FXML
    public void refreshButtonPatientListHandler(){
        patientTable.getItems().clear();
        patientTable.getItems().addAll(dentistAppointmentServices.getAllPatient());
        setTotalNumberOfPatients();
    }

    private void setTotalNumberOfPatients(){
        totalNumberOfPatients.setText(String.valueOf(dentistAppointmentServices.getAllPatient().size()));
    }

    @FXML
    public void filterPatientByID(){
        String ID = idFilterPatient.getText();
        idFilterPatient.clear();
        if (!checkIfString(ID)) {
            showNotification("You must introduce an ID! ", Alert.AlertType.ERROR);
            return;
        }
        try{
        Patient results = dentistAppointmentServices.findByIdPatient(Integer.parseInt(ID));
        patientTable.getItems().clear();
        patientTable.getItems().addAll(results);
        } catch (Exception e){
            showNotification(e.getMessage(), Alert.AlertType.WARNING);
            refreshButtonPatientListHandler();
        }
    }

    @FXML
    public void filterPatientByName(){
        String name = nameFilterPatient.getText();
        nameFilterPatient.clear();
        if (!checkIfString(name)) {
            showNotification("You must introduce a name! ", Alert.AlertType.ERROR);
            return;
        }
        List<Patient> results = dentistAppointmentServices.filterByNamePatient(name);
        if(results.isEmpty()){
            showNotification("Nonexistent patient!", Alert.AlertType.WARNING);
            refreshButtonPatientListHandler();
            return;
        }
        patientTable.getItems().clear();
        patientTable.getItems().addAll(results);
    }

    @FXML
    public void filterPatientByCNP(){
        String CNP = cnpFilterPatient.getText();
        cnpFilterPatient.clear();
        if (!checkIfString(CNP)) {
            showNotification("You must introduce a PNC! ", Alert.AlertType.ERROR);
            return;
        }
        List<Patient> results = dentistAppointmentServices.filterByCNPPatient(CNP);
        if(results.isEmpty()){
            showNotification("Nonexistent patient!", Alert.AlertType.WARNING);
            refreshButtonPatientListHandler();
            return;
        }
        patientTable.getItems().clear();
        patientTable.getItems().addAll(results);
    }

    //refresh patient information
    @FXML
    public void refreshButtonPatientInformation(){
        idPatient.setText("");
        namePatient.setText("");
        cnpPatient.setText("");
        phoneNumberPatient.setText("");
        emailPatient.setText("");
    }

    @FXML
    public void autoFillPatientInformation(){
        try{
            Patient patient = patientTable.getSelectionModel().getSelectedItem();
            idPatient.setText(patient.getID().toString());
            namePatient.setText(patient.getName());
            cnpPatient.setText(patient.getCNP());
            phoneNumberPatient.setText(patient.getPhoneNumber());
            emailPatient.setText(patient.getEmail());
        }catch (Exception e) {
            showNotification(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void addPatientHandler(){
        if(checkIfString(idPatient.getText())){
            showNotification("You can only update an element on an already existing ID!", Alert.AlertType.ERROR);
            refreshButtonPatientInformation();
            return;
        }
        String patientName = namePatient.getText();
        String cnp = cnpPatient.getText();
        String phoneNumber = phoneNumberPatient.getText();
        String email = emailPatient.getText();

        if (checkIfString(patientName) && checkIfString(cnp) && checkIfString(phoneNumber) && checkIfString(email)){
            try {
                Integer id = dentistAppointmentServices.addPatient(patientName, cnp, phoneNumber, email);
                showNotification("Patient successfully added! With ID: " + id, Alert.AlertType.INFORMATION);
                refreshButtonPatientListHandler();
                refreshButtonPatientInformation();
            }catch(Exception exception){
                showNotification(exception.getMessage(), Alert.AlertType.ERROR);
            }
        }
        else
            showNotification("You have to fill in all the fields! ", Alert.AlertType.ERROR);
    }

    @FXML
    public void deletePatientHandler(){
        String ID = idPatient.getText();
        if(checkIfString(ID)){
            dentistAppointmentServices.deletePatient(Integer.parseInt(ID));
            showNotification("Patient successfully deleted! ", Alert.AlertType.INFORMATION);
            refreshButtonPatientListHandler();
            refreshButtonPatientInformation();
        }
        else{
            showNotification("Please select a patient!", Alert.AlertType.ERROR);
            refreshButtonPatientInformation();
        }
    }

    @FXML
    public void updatePatientHandler(){
        String ID = idPatient.getText();
        if(checkIfString(ID)){
            String patientName = namePatient.getText();
            String cnp = cnpPatient.getText();
            String phoneNumber = phoneNumberPatient.getText();
            String email = emailPatient.getText();

            if (checkIfString(patientName) && checkIfString(cnp) && checkIfString(phoneNumber) && checkIfString(email)){
                try {
                    dentistAppointmentServices.updatePatient(Integer.parseInt(ID), patientName, cnp, phoneNumber, email);
                    showNotification("Patient successfully updated! ", Alert.AlertType.INFORMATION);
                    refreshButtonPatientListHandler();
                    refreshButtonPatientInformation();
                }catch(Exception exception){
                    showNotification(exception.getMessage(), Alert.AlertType.ERROR);
                }
            }
            else {
                showNotification("You have to fill in all the fields! ", Alert.AlertType.ERROR);
            }
        }
        else{
            showNotification("Please select a patient to update!", Alert.AlertType.ERROR);
            refreshButtonAppointmentInformation();
        }
    }





    /////////APPOINTMENT/////////////
    //fill appointmentTable
    @FXML
    public void refreshButtonAppointmentListHandler(){
        appointmentTable.getItems().clear();
        appointmentTable.getItems().addAll(dentistAppointmentServices.getAllAppointment());
        setCurrentDateAndAppointmentsNo();
    }

    private void setCurrentDateAndAppointmentsNo(){
        currentDateAppointmentsNo.setText(dentistAppointmentServices.totalNumberOfAppointmentsToday().toString());
    }
    @FXML
    public void filterAppointmentByID(){
        String ID = idFilterAppointment.getText();
        idFilterAppointment.clear();
        if (!checkIfString(ID)) {
            showNotification("You must introduce an ID! ", Alert.AlertType.ERROR);
            return;
        }
        try{
            Appointment results = dentistAppointmentServices.findByIdAppointment(Integer.parseInt(ID));
            appointmentTable.getItems().clear();
            appointmentTable.getItems().addAll(results);
        } catch (Exception e){
            showNotification(e.getMessage(), Alert.AlertType.WARNING);
            refreshButtonAppointmentListHandler();
        }

    }

    @FXML
    public void filterAppointmentByPatientID(){
        String ID = idPatientFilterAppointment.getText();
        idPatientFilterAppointment.clear();
        if (!checkIfString(ID)) {
            showNotification("You must introduce an ID! ", Alert.AlertType.ERROR);
            return;
        }
        List<Appointment> results = dentistAppointmentServices.filterByPatientIdAppointment(Integer.parseInt(ID));
        if(results.isEmpty()){
            showNotification("Nonexistent appointment!", Alert.AlertType.WARNING);
            refreshButtonAppointmentListHandler();
            return;
        }
        appointmentTable.getItems().clear();
        appointmentTable.getItems().addAll(results);
    }

    @FXML
    public void filterAppointmentByService(){
        String service = serviceFilterAppointment.getText();
        serviceFilterAppointment.clear();
        if (!checkIfString(service)) {
            showNotification("You must introduce a service! ", Alert.AlertType.ERROR);
            return;
        }
        List<Appointment> results = dentistAppointmentServices.filterByServiceAppointment(service);
        if(results.isEmpty()){
            showNotification("Nonexistent appointment!", Alert.AlertType.WARNING);
            refreshButtonAppointmentListHandler();
            return;
        }
        appointmentTable.getItems().clear();
        appointmentTable.getItems().addAll(results);
    }

    @FXML
    public void filterAppointmentByDate(){
        if (dateFilterAppointment.getValue()==null) {
            showNotification("You must introduce a date! ", Alert.AlertType.ERROR);
            return;
        }
        LocalDateTime date = LocalDateTime.of(dateFilterAppointment.getValue().getYear(),dateFilterAppointment.getValue().getMonth(),dateFilterAppointment.getValue().getDayOfMonth(),0,0);
        dateFilterAppointment.setValue(null);
        List<Appointment> results = dentistAppointmentServices.filterByDateAppointment(date);
        if(results.isEmpty()){
            showNotification("Nonexistent appointment!", Alert.AlertType.WARNING);
            refreshButtonAppointmentListHandler();
            return;
        }
        appointmentTable.getItems().clear();
        appointmentTable.getItems().addAll(results);
    }

    //refresh appointment information
    @FXML
    public void refreshButtonAppointmentInformation(){
        idAppointment.setText("");
        idPatientInAppointment.setText("");
        serviceAppointment.setText("");
        dateAppointment.setValue(null);
        timeAppointment.setText("");
    }

    @FXML
    public void autoFillAppointmentInformation(){
        try{
            Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
            idAppointment.setText(appointment.getID().toString());
            idPatientInAppointment.setText(appointment.getPatient().getID().toString());
            serviceAppointment.setText(appointment.getService());
            dateAppointment.setValue(appointment.getDate().toLocalDate());
            timeAppointment.setText((appointment.getDate().getHour()) + ":" + (appointment.getDate().getMinute()));
        }catch (Exception e){
            showNotification(e.getMessage(), Alert.AlertType.ERROR);

        }
    }

    @FXML
    public void addAppointmentHandler() {
        if(checkIfString(idAppointment.getText())){
            showNotification("You can only update an element on an already existing ID!", Alert.AlertType.ERROR);
            refreshButtonAppointmentInformation();
            return;
        }
        String patientID = idPatientInAppointment.getText();
        String service = serviceAppointment.getText();
        String time = timeAppointment.getText();

        if (checkIfString(patientID) && checkIfString(service) && checkIfString(time) && dateAppointment.getValue() != null){
            try {
                int year = dateAppointment.getValue().getYear();
                int month = dateAppointment.getValue().getMonthValue();
                int day = dateAppointment.getValue().getDayOfMonth();
                String[] aTime = time.split(":");
                int hour = Integer.parseInt(aTime[0]);
                int minute = Integer.parseInt(aTime[1]);
                Integer id = dentistAppointmentServices.addAppointment(Integer.parseInt(patientID) ,service, LocalDateTime.of(year, month, day, hour, minute));
                showNotification("Appointment successfully added! With ID: " + id, Alert.AlertType.INFORMATION);
                refreshButtonAppointmentListHandler();
                refreshButtonAppointmentInformation();
            }catch(Exception exception){
                showNotification(exception.getMessage(), Alert.AlertType.ERROR);
            }
        }
        else
            showNotification("You have to fill in all the fields! ", Alert.AlertType.ERROR);
    }

    @FXML
    public void deleteAppointmentHandler(){
        String ID = idAppointment.getText();
        if(checkIfString(ID)){
            dentistAppointmentServices.deleteAppointment(Integer.parseInt(ID));
            showNotification("Appointment successfully deleted! ", Alert.AlertType.INFORMATION);
            refreshButtonAppointmentListHandler();
            refreshButtonAppointmentInformation();
        }
        else{
            showNotification("Please select an appointment!", Alert.AlertType.ERROR);
            refreshButtonAppointmentInformation();
        }
    }

    @FXML
    public void updateAppointmentHandler(){
        String ID = idAppointment.getText();
        if(checkIfString(ID)){
            String patientID = idPatientInAppointment.getText();
            String service = serviceAppointment.getText();
            String time = timeAppointment.getText();

            if (checkIfString(patientID) && checkIfString(service) && checkIfString(time) && dateAppointment.getValue() != null){
                try {
                    int year = dateAppointment.getValue().getYear();
                    int month = dateAppointment.getValue().getMonthValue();
                    int day = dateAppointment.getValue().getDayOfMonth();
                    String[] aTime = time.split(":");
                    int hour = Integer.parseInt(aTime[0]);
                    int minute = Integer.parseInt(aTime[1]);
                    dentistAppointmentServices.updateAppointment(Integer.parseInt(ID), Integer.parseInt(patientID) ,service, LocalDateTime.of(year, month, day, hour, minute));
                    showNotification("Appointment successfully updated! ", Alert.AlertType.INFORMATION);
                    refreshButtonAppointmentListHandler();
                    refreshButtonAppointmentInformation();
                }catch(Exception exception){
                    showNotification(exception.getMessage(), Alert.AlertType.ERROR);
                }
            }
            else {
                showNotification("You have to fill in all the fields! ", Alert.AlertType.ERROR);
            }
        }
        else{
            showNotification("Please select an appointment to update!", Alert.AlertType.ERROR);
            refreshButtonAppointmentInformation();
        }
    }
}


