package com.example.testaplication.Adapter;

public class DisplayVote {
    private String username;
    private String points;

    public DisplayVote(String username, String points) {
        this.username = username;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "DisplayVote{" +
                "username='" + username + '\'' +
                ", points='" + points + '\'' +
                '}';
    }
}
