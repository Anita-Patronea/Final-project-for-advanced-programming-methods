package services;

public class DentistAppointmentServicesException extends RuntimeException{
    public DentistAppointmentServicesException(){}
    public DentistAppointmentServicesException(String message){ super(message); }
    public DentistAppointmentServicesException(Exception exception){
        super(exception);
    }
}
