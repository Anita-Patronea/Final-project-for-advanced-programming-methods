package repository;

import domain.Appointment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AppointmentFileRepository extends AppointmentInMemoryRepository {
    private String filename;
    private PatientRepository patientRepository;
    private static int idGenerator = 0;

    public AppointmentFileRepository(String filename, PatientRepository patientRepository) throws IOException {
        this.filename = filename;
        this.patientRepository = patientRepository;
        readFromFile();
    }

    protected void readFromFile(){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(this.filename))){      //try-with-resources
            String line = bufferedReader.readLine();
            try{
                idGenerator = Integer.parseInt(line);
            }catch (NumberFormatException ex){
                System.err.println("Invalid value for ID generator!");
            }
            while((line = bufferedReader.readLine()) != null){
                String[] elements = line.split("[|]");
                if(elements.length != 4){
                    System.err.println("Not a valid number of attributes: " + line);
                    continue;
                }
                try{
                    int appointmentID = Integer.parseInt(elements[0]);
                    int patientID = Integer.parseInt(elements[1]);
                    LocalDateTime date = LocalDateTime.parse(elements[3], DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                    Appointment appointment = new Appointment(appointmentID, patientRepository.findById(patientID), elements[2], date);
                    super.add(appointment);
                }catch(Exception ex){
                    System.err.println("Error reading data from file: "+ ex + " " + line);
                    continue;
                }
            }
        }catch(IOException ex){
            throw new RepositoryPersistenceException("Error reading data from file: " + ex);
        }
    }

    protected void writeToFile() {
        try(PrintWriter printWriter = new PrintWriter(filename)) {
            printWriter.println(idGenerator);
            for(Appointment elem : this.findAll()) {
                String line = elem.getID() + "|" + elem.getPatient().getID() + '|'  + elem.getService() + '|' + elem.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                printWriter.println(line);
            }
        }catch(IOException ex) {
            throw new RepositoryPersistenceException("Error writing data to file: " + ex);
        }

    }

    @Override
    public Integer add(Appointment elem) {
        try {
            elem.setID(getNextID());
            Integer id = super.add(elem);
            writeToFile();
            return id;
        } catch (Exception ex) {
            throw new RepositoryPersistenceException("Object was not added: " + ex);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            super.delete(id);
            writeToFile();
        } catch (Exception ex) {
            throw new RepositoryPersistenceException("Object was not deleted: " + ex);
        }
    }

    @Override
    public void update(Appointment elem, Integer id) {
        try {
            super.update(elem, id);
            writeToFile();
        } catch (Exception ex) {
            throw new RepositoryPersistenceException("Object was not updated: " + ex);
        }
    }

    private static int getNextID() {return idGenerator++;}
}
