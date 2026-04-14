package com.example.testaplication.Adapter;

public class MangaInformation {
    private int source;
    private String nameAuthors;
    private String description;

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getNameAuthors() {
        return nameAuthors;
    }

    public void setNameAuthors(String nameAuthors) {
        this.nameAuthors = nameAuthors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MangaInformation(int source, String nameAuthors, String description) {
        this.source = source;
        this.nameAuthors = nameAuthors;
        this.description = description;
    }
    public MangaInformation(String nameAuthors, String description){
        this.nameAuthors = nameAuthors;
        this.description = description;
    }
    @Override
    public String toString() {
        return "CustomPhoto{" +
                "source=" + source +
                ", nameAuthors='" + nameAuthors + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
