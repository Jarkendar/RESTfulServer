package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.models.Challenge;
import server.models.DifficultyLevel;
import server.models.Status;
import server.models.User;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FileManager {

    private Logger logger = LoggerFactory.getLogger(FileManager.class);

    private File file;
    private AtomicLong atomicLong = new AtomicLong();
    private static final String CHALLENGES_FILE_SUFFIX = "/challenges.txt";

    public FileManager(File file) {
        this.file = file;
    }

    public synchronized Map<String, User> readUsersFromFile() {
        logger.info("read users from file = {}", new Date());
        HashMap<String, User> users = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            String[] readData;
            while ((line = bufferedReader.readLine()) != null) {
                readData = line.split(";");
                users.put(readData[0], new User(getNextID(), readData[0], readData[1], readData[2], Boolean.getBoolean(readData[3])));
            }
        } catch (IOException exception) {
            logger.error(exception.toString());
        }
        return users;
    }

    public synchronized void saveUsersToFile(Map<String, User> users) {
        logger.info("save users to file = {}", new Date());
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
        } catch (IOException exception) {
            logger.error(exception.toString());
        }
    }

    public synchronized void saveChallenge(Challenge challenge) {
        File directory = new File(challenge.getUserReceiver());
        if (!directory.exists()) {
            try {
                directory.mkdir();
            } catch (SecurityException se) {
                logger.error(se.toString());
            }
        }
        File challengesFile = new File(challenge.getUserReceiver() + CHALLENGES_FILE_SUFFIX);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(challengesFile, true))) {
            String stringBuilder = challenge.getChallengeID() + ";" +
                    challenge.getTitle() + ";" +
                    challenge.getDescription() + ";" +
                    challenge.getStartTime().getTime() + ";" +
                    challenge.getLastStatusModifiedTime().getTime() + ";" +
                    challenge.getDifficultyLevel().toString() + ";" +
                    challenge.getStatus().toString() + ";" +
                    challenge.getUserReceiver() + ";" +
                    challenge.getUserSender() + ";" +
                    Boolean.toString(challenge.isSynchronize()) + ";" +
                    ((challenge.getEndTime() != null) ? challenge.getEndTime().getTime() : "null") + ";";
            bufferedWriter.write(stringBuilder);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            logger.error(e.toString());
        }
    }

    public synchronized List<Challenge> readAllChallengesFromUser(String username) {
        LinkedList<Challenge> challenges = new LinkedList<>();
        File challengeFile = new File(username + CHALLENGES_FILE_SUFFIX);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(challengeFile))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(";");
                challenges.addLast(new Challenge(fields[0], fields[1], fields[2],
                        new Date(Long.parseLong(fields[3])), new Date(Long.parseLong(fields[4])),
                        DifficultyLevel.valueOf(fields[5]), Status.valueOf(fields[6]), fields[7],
                        fields[8], Boolean.getBoolean(fields[9]),
                        (fields[10].equals("null") ? null : new Date(Long.parseLong(fields[10])))));
            }
        } catch (IOException e) {
            logger.error(e.toString());
        }
        return challenges;
    }

    public String getNextID() {
        return Long.toString(atomicLong.incrementAndGet());
    }
}
