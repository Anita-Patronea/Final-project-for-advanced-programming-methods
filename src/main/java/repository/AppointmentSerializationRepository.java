package repository;

import domain.Appointment;
import java.io.*;
import java.util.Map;

public class AppointmentSerializationRepository extends AppointmentInMemoryRepository {

    private String filename;

    public AppointmentSerializationRepository(String filename) throws IOException {
        this.filename = filename;
        readFromFile();
    }

    protected void readFromFile() {
        try(ObjectInputStream in = new ObjectInputStream (new FileInputStream(this.filename))){
                map = (Map<Integer, Appointment>) in.readObject();
        }catch(Exception e){
            throw new RepositoryPersistenceException("Error reading from file: " + e);
        }
    }

    protected void writeToFile() {
        try(ObjectOutputStream out = new ObjectOutputStream (new FileOutputStream(this.filename))){
            out.writeObject(map);
        } catch(IOException e){
            throw new RepositoryPersistenceException("Error writing to file: " + e);
        }
    }

    @Override
    public Integer add(Appointment elem) {
        try {
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
}
