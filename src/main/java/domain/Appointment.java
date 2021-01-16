package domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Appointment implements Identifiable<Integer>, Comparable<Appointment>, Serializable {
    private static final long serialVersionUID = 1L;
    private int ID;
    private Patient patient;
    private String service;
    private LocalDateTime date;

    //constructors
    public Appointment(){
        this.ID = 0;
        this.patient = null;
        this.service = "";
        this.date = null;
    }
    public Appointment(int ID, Patient patient, String service, LocalDateTime date){
        this.ID = ID;
        this.patient = patient;
        this.service = service;
        this.date = date;
    }
    public Appointment(Appointment appointment){
        this.ID = appointment.ID;
        this.patient = appointment.patient;
        this.service = appointment.service;
        this.date = appointment.date;
    }

    //getters
    public LocalDateTime getDate() { return date; }
    public String getTimeString() {return String.valueOf(date.getHour()) + ":" + String.valueOf(date.getMinute()); }
    public Integer getID() { return ID; }
    public Patient getPatient() { return patient; }
    public String getPatientName() {return patient.getName(); }
    public String getPatientID() {return patient.getID().toString(); }
    public String getService() {return service; }

    //setters
    public void setID(Integer ID) { this.ID = ID; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public void setService(String service) { this.service = service; }
    public void setDate(LocalDateTime date) { this.date = date; }

    @Override
    public String toString() {
        return "Appointment:" +
                "\n\tID: " + ID +
                "\n\t" + patient +
                "\n\tService: " + service +
                "\n\tDate: " + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Appointment that = (Appointment) other;
        return ID == that.ID &&
                Objects.equals(patient, that.patient) &&
                Objects.equals(service, that.service) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int compareTo(Appointment other) {
        return Integer.compare(this.ID, other.ID);
    }
}
