package repository;

import domain.Patient;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class PatientFileRepository extends PatientInMemoryRepository {

    private String filename;
    private static int idGenerator = 0;
    public PatientFileRepository(String filename) {
        this.filename = filename;
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
                if(elements.length != 5){
                    System.err.println("Not a valid number of attributes: " + line);
                    continue;
                }
                try{
                    int id = Integer.parseInt(elements[0]);
                    Patient patient = new Patient(id, elements[1], elements[2], elements[3], elements[4]);
                    super.add(patient);
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
            for(Patient elem : this.findAll()) {
                String line = elem.getID() + "|" + elem.getName() + "|" +elem.getCNP() + '|' + elem.getPhoneNumber() + '|' + elem.getEmail();
                printWriter.println(line);
            }
        }catch(IOException ex) {
            throw new RepositoryPersistenceException("Error writing data to file: " + ex);
        }

    }

    @Override
    public Integer add(Patient elem) {
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
    public void update(Patient elem, Integer id) {
        try {
            super.update(elem, id);
            writeToFile();
        } catch (Exception ex) {
            throw new RepositoryPersistenceException("Object was not updated: " + ex);
        }
    }

    private static int getNextID() {return idGenerator++;}
}
