package repository;

import domain.Patient;

import java.util.List;
import java.util.stream.Collectors;

public class PatientInMemoryRepository extends AbstractRepository<Patient, Integer> implements PatientRepository{
    @Override
    public List<Patient> sortByID() {
        return this.getAll().stream().sorted((p1, p2) -> p1.getID().compareTo(p2.getID())).collect(Collectors.toList());
    }

    @Override
    public List<Patient> sortByName() {
        return this.getAll().stream().sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
    }

    @Override
    public List<Patient> filterByName(String name) {
        return getAll().stream().filter(p -> p.getName().toLowerCase().startsWith(name.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public List<Patient> filterByCNP(String CNP) {
        return getAll().stream().filter(p -> p.getCNP().toLowerCase().startsWith(CNP.toLowerCase())).collect(Collectors.toList());

    }
}
