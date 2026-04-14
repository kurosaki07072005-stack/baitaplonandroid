package com.example.testaplication.Adapter;

public class Vote {
    private  int resource;
    private String name;
    private String totalVote;

    public Vote(int resource, String name, String totalVote) {
        this.resource = resource;
        this.name = name;
        this.totalVote = totalVote;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalVote() {
        return totalVote;
    }

    public void setTotalVote(String totalVote) {
        this.totalVote = totalVote;
    }
}
