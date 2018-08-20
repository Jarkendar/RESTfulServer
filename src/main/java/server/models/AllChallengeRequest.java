package server.models;

import java.util.List;

public class AllChallengeRequest {
    private User user;
    private List<Challenge> challengeList;

    public AllChallengeRequest() {
    }

    public AllChallengeRequest(User user, List<Challenge> challengeList) {
        this.user = user;
        this.challengeList = challengeList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Challenge> getChallengeList() {
        return challengeList;
    }

    public void setChallengeList(List<Challenge> challengeList) {
        this.challengeList = challengeList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Challenge challenge : challengeList){
            stringBuilder.append(challenge).append("\n");
        }
        return "AllChallengeRequest{" +
                "user=" + user +
                ", challengeList=[" + stringBuilder.toString() +
                "]}";
    }
}
