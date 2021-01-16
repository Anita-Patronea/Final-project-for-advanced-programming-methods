package domain;

import java.io.Serializable;
import java.util.Objects;

public class Patient implements Identifiable<Integer>, Comparable<Patient>, Serializable {
    private static final long serialVersionUID = 1L;
    private int ID;
    private String name;
    private String CNP;
    private String phoneNumber;
    private String email;

    //constructors
    public Patient(){
        this.ID = 0;
        this.name = "";
        this.CNP = "";
        this.phoneNumber = "";
        this.email = "";
    }
    public Patient(int ID, String name, String CNP, String phoneNumber, String email){
        this.ID = ID;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.CNP = CNP;
        this.email = email;
    }
    public Patient(Patient patient){
        this.ID = patient.ID;
        this.name = patient.name;
        this.CNP = patient.CNP;
        this.phoneNumber = patient.phoneNumber;
        this.email = patient.email;
    }

    //getters
    @Override
    public Integer getID() { return ID; }
    public String getName() { return name; }
    public String getCNP() { return CNP; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }

    //setters
    @Override
    public void setID(Integer ID) { this.ID = ID; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setCNP(String CNP) { this.CNP = CNP; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Patient:" +
                "\n\t\tID: " + ID +
                "\n\t\tName: " + name +
                "\n\t\tCNP: " + CNP +
                "\n\t\tPhone number: " + phoneNumber +
                "\n\t\tEmail: " + email;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;  //or o instanceof domain.Patient
        Patient patient = (Patient) other;
        return ID == patient.ID &&
                Objects.equals(phoneNumber, patient.phoneNumber) &&
                Objects.equals(name, patient.name) &&
                Objects.equals(CNP, patient.CNP) &&
                Objects.equals(email, patient.email);
    }

    @Override
    public int compareTo(Patient other) {
        return Integer.compare(this.ID, other.ID);
    }
}
