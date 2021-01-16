package repository;

import domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AppointmentInMemoryRepository extends AbstractRepository<Appointment, Integer> implements AppointmentRepository{
    @Override
    public List<Appointment> sortByID() {
        return this.getAll().stream().sorted((p1, p2) -> p1.getID().compareTo(p2.getID())).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> sortByService() {
        return this.getAll().stream().sorted((p1, p2) -> p1.getService().compareTo(p2.getService())).collect(Collectors.toList());

    }

    @Override
    public List<Appointment> sortByDate() {
        return this.getAll().stream().sorted((p1, p2) -> p1.getDate().compareTo(p2.getDate())).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> filterByPatientID(Integer id) {
        return getAll().stream().filter(a -> a.getPatient().getID().equals(id)).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> filterByService(String service) {
        return getAll().stream().filter(a -> a.getService().toLowerCase().startsWith(service)).collect(Collectors.toList());
    }

    @Override
    public List<Appointment> filterByDate(LocalDateTime date) {
        return getAll().stream().filter(a -> a.getDate().getYear() == date.getYear() &&
                a.getDate().getMonth() == date.getMonth() &&
                a.getDate().getDayOfMonth() == date.getDayOfMonth()).collect(Collectors.toList());

    }
}
