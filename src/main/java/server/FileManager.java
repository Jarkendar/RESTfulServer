package server;

import server.models.User;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FileManager {

    private File file;

    public FileManager(File file) {
        this.file = file;
    }

    public synchronized HashMap<String, User> readUsersFromFile(){
        HashMap<String, User> users = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            String line;
            String[] readData;
            AtomicLong atomicLong = new AtomicLong();
            while ((line = bufferedReader.readLine()) != null){
                readData = line.split(";");
                users.put(readData[0], new User(Long.toString(atomicLong.incrementAndGet()), readData[0], readData[1], readData[2], readData[3], readData[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public synchronized void saveUsersToFile(HashMap<String,User> users){
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))){
            StringBuilder stringBuilder = new StringBuilder();
            User user;
            for(HashMap.Entry<String, User> userEntry : users.entrySet()){
                user = userEntry.getValue();
                stringBuilder.append(user.getUsername()).append(";")
                        .append(user.getPassword()).append(";")
                        .append(user.getEmail()).append(";")
                        .append(user.getFacebookProfile()).append(";")
                        .append(user.getGooglePlusProfile());
                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.newLine();
                stringBuilder.setLength(0);
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
