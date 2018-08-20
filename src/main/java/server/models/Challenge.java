package server.models;

import java.util.Date;
import java.util.Objects;

public class Challenge {
    private String challengeID;
    private String title;
    private String description;
    private Date startTime;
    private Date lastStatusModifiedTime;
    private DifficultyLevel difficultyLevel;
    private Status status;
    private String userReceiver;
    private String userSender;
    private boolean synchronize;
    private Date endTime;

    public Challenge() {
    }

    public Challenge(String challengeID, String title, String description, Date startTime, Date lastStatusModifiedTime,
                     DifficultyLevel difficultyLevel, Status status, String userReceiver, String userSender,
                     boolean synchronize, Date endTime) {
        this.challengeID = challengeID;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.lastStatusModifiedTime = lastStatusModifiedTime;
        this.difficultyLevel = difficultyLevel;
        this.status = status;
        this.userReceiver = userReceiver;
        this.userSender = userSender;
        this.synchronize = synchronize;
        this.endTime = endTime;
    }

    public String getChallengeID() {
        return challengeID;
    }

    public void setChallengeID(String challengeID) {
        this.challengeID = challengeID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getLastStatusModifiedTime() {
        return lastStatusModifiedTime;
    }

    public void setLastStatusModifiedTime(Date lastStatusModifiedTime) {
        this.lastStatusModifiedTime = lastStatusModifiedTime;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(String userReceiver) {
        this.userReceiver = userReceiver;
    }

    public String getUserSender() {
        return userSender;
    }

    public void setUserSender(String userSender) {
        this.userSender = userSender;
    }

    public boolean isSynchronize() {
        return synchronize;
    }

    public void setSynchronize(boolean synchronize) {
        this.synchronize = synchronize;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Challenge{" +
                "challengeID='" + challengeID + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startTime=" + startTime +
                ", lastStatusModifiedTime=" + lastStatusModifiedTime +
                ", difficultyLevel=" + difficultyLevel +
                ", status=" + status +
                ", userReceiver='" + userReceiver + '\'' +
                ", userSender='" + userSender + '\'' +
                ", synchronize=" + synchronize +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public boolean equals(Object challenge) {
        if (challenge == null){
            return false;
        }
        if (!(challenge instanceof Challenge)){
            return false;
        }
        Challenge c = (Challenge) challenge;
        return this.challengeID.equals(c.challengeID) && this.getStatus().compareTo(c.status) == 0 && this.startTime.compareTo(c.startTime) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(challengeID, title, description, startTime, lastStatusModifiedTime, difficultyLevel, status, userReceiver, userSender, synchronize, endTime);
    }
}
