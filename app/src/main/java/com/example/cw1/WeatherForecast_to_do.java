package com.example.cw1;

public class WeatherForecast_to_do {
    private String title;
    private String description;

    public WeatherForecast_to_do() {
        this.title = "";
        this.description = "";
    }

    public WeatherForecast_to_do(String title, String description) {
        this.title = title;
        this.description = description;
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

    @Override
    public String toString() {
        return "WeatherForecast{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

