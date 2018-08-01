package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.models.User;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class FileManager {

    private Logger logger = LoggerFactory.getLogger(FileManager.class);

    private File file;
    private AtomicLong atomicLong = new AtomicLong();

    public FileManager(File file) {
        this.file = file;
    }

    public synchronized HashMap<String, User> readUsersFromFile() {
        logger.info("read users from file = " + new Date());
        HashMap<String, User> users = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            String[] readData;
            while ((line = bufferedReader.readLine()) != null) {
                readData = line.split(";");
                users.put(readData[0], new User(getNextID(), readData[0], readData[1], readData[2], Boolean.getBoolean(readData[3])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public synchronized void saveUsersToFile(HashMap<String, User> users) {
        logger.info("save users to file = " + new Date());
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            StringBuilder stringBuilder = new StringBuilder();
            User user;
            for (HashMap.Entry<String, User> userEntry : users.entrySet()) {
                user = userEntry.getValue();
                stringBuilder.append(user.getUsername()).append(";")
                        .append(user.getPassword()).append(";")
                        .append(user.getEmail()).append(";")
                        .append(Boolean.toString(user.isOnline()));
                bufferedWriter.write(stringBuilder.toString());
                bufferedWriter.newLine();
                stringBuilder.setLength(0);
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNextID() {
        return Long.toString(atomicLong.incrementAndGet());
    }
}
