package repository;

import domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends Repository<Appointment, Integer>{
    List<Appointment> sortByID();
    List<Appointment> sortByService();
    List<Appointment> sortByDate();
    List<Appointment> filterByPatientID(Integer id);
    List<Appointment> filterByService(String service);
    List<Appointment> filterByDate(LocalDateTime date);
}
