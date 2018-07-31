package server;

import java.io.*;
import java.util.HashMap;

public class FileManager {

    private File file;
    private static final String LOGOUT_STRING = "LOGOUT";

    public FileManager(File file) {
        this.file = file;
    }

    public HashMap<String, String> readUsersFromFile(){
        HashMap<String, String> users = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = bufferedReader.readLine()) != null){
                users.put(line.trim(), LOGOUT_STRING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void saveUsersToFile(HashMap<String,String> users){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            for(HashMap.Entry<String, String> user : users.entrySet()){
                bufferedWriter.write(user.getKey());
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
