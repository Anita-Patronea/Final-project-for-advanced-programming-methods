package repository;

import domain.Patient;

import java.util.List;

public interface PatientRepository extends Repository<Patient, Integer>{
    List<Patient> sortByID();
    List<Patient> sortByName();
    List<Patient> filterByName(String name);
    List<Patient> filterByCNP(String CNP);
}
