import controller.DentistAppointmentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import repository.AppointmentFileRepository;
import repository.AppointmentRepository;
import repository.PatientFileRepository;
import repository.PatientRepository;
import services.DentistAppointmentServices;
import services.DentistAppointmentServicesException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DentistAppointmentsWindow.fxml"));
            Parent root = loader.load();
            DentistAppointmentController controller = loader.getController();
            controller.setService(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Dentist appointments");
            primaryStage.show();
        }catch (Exception exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Error while starting the app " + exception);
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    static DentistAppointmentServices getService() throws DentistAppointmentServicesException {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("Dentist_appointments.properties"));

            String appointmentFileName = properties.getProperty("AppointmentFile");
            if (appointmentFileName == null){
                appointmentFileName = "Appointment.txt";
                System.err.println("Appointments file not found. Using default " + appointmentFileName);
            }

            String patientFileName = properties.getProperty("PatientFile");
            if (patientFileName == null){
                patientFileName = "Patient.txt";
                System.err.println("Patients file not found. Using default " + patientFileName);
            }

            PatientRepository patientRepo = new PatientFileRepository(patientFileName);
            AppointmentRepository appointmentRepo = new AppointmentFileRepository(appointmentFileName, patientRepo);
            return new DentistAppointmentServices(patientRepo, appointmentRepo);
        }catch (IOException ex){
            throw new DentistAppointmentServicesException("Error while creating services" + ex);
        }
    }
}
