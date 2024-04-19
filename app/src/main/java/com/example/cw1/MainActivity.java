package com.example.cw1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView weatherTextView, Overview, Temperatue, Pressure, Visibility, dayOfWeekTextView, dateTextView, locationTextView, detailedWeatherTextView, currentWeatherTextView;
    private Button prevLocationButton, nextLocationButton;



    private java.util.Map<String, String> locationMap;




    private int currentLocationIndex = 0;

    private final Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                weatherTextView.setText(msg.obj.toString());
            }
            return true;
        }
    });

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherTextView = findViewById(R.id.weatherInfoTextView);

        Overview = findViewById(R.id.Overview);
        Temperatue = findViewById(R.id.Temperatue);
        Pressure = findViewById(R.id.Pressure);
        Visibility = findViewById(R.id.Visibility);

        prevLocationButton = findViewById(R.id.prevLocationButton);
        prevLocationButton.setOnClickListener(this);

        nextLocationButton = findViewById(R.id.nextLocationButton);
        nextLocationButton.setOnClickListener(this);

        dayOfWeekTextView = findViewById(R.id.dayOfWeekTextView);
        dateTextView = findViewById(R.id.dateTextView);
//        locationTextView = findViewById(R.id.locationTextView);
//        detailedWeatherTextView = findViewById(R.id.detailedWeatherTextView);
        currentWeatherTextView = findViewById(R.id.currentWeatherTextView);

        locationMap = new HashMap<>();
        locationMap.put("Glasgow", "2648579");
        locationMap.put("London", "2643743");
        locationMap.put("New York", "5128581");
        locationMap.put("Oman", "287286");
        locationMap.put("Mauritius", "934154");
         locationMap.put("Bangladesh", "1185241");

        fetchWeatherData();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.navigation_home) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.navigation_settings) {
                    fragment = new SettingsFragment();
                }else if (item.getItemId() == R.id.search_setting) {
                    fragment = new SearchFragment();
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
                    return true;
                }
                return false;
            }




        });


    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.prevLocationButton) {
            navigateToPreviousLocation();
        } else if (v.getId() == R.id.nextLocationButton) {
            navigateToNextLocation();
        }



    }

//    private void fetchWeatherData() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    StringBuilder weatherData = new StringBuilder();
//
//                    String currentLocation = getCurrentLocation();
//                    String locationId = locationMap.get(currentLocation);
//
//                    URL url = new URL("https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/" + locationId);
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("GET");
//
//                    InputStream inputStream = connection.getInputStream();
//                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//                    Document doc = dBuilder.parse(inputStream);
//                    doc.getDocumentElement().normalize();
//
//                    NodeList itemList = doc.getElementsByTagName("item");
//
//                    weatherData.append("Location: ").append(currentLocation).append("\n\n");
//
//                    for (int i = 0; i < itemList.getLength(); i++) {
//                        Element item = (Element) itemList.item(i);
//                        String title = item.getElementsByTagName("title").item(0).getTextContent();
//                        String description = item.getElementsByTagName("description").item(0).getTextContent();
//
//                        String minMaxTemperature = extractTemperature(title);
//
//                        weatherData.append(title).append("\n")
//                                .append(minMaxTemperature).append("\n")
//                                .append("Description: ").append(description).append("\n\n");
//                        break; // Display only one day's information
//                    }
//
//                    updateUI(weatherData.toString());
//
//                    // Call the method to fetch three-day forecast
//                    fetchThreeDayForecast(locationId);
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }


    private void fetchWeatherData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder weatherData = new StringBuilder();


                    String currentLocation = getCurrentLocation();
                    String locationId = locationMap.get(currentLocation);

                    URL url = new URL("https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/" + locationId);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    InputStream inputStream = connection.getInputStream();
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(inputStream);
                    doc.getDocumentElement().normalize();

                    NodeList itemList = doc.getElementsByTagName("item");

                    // Get current date and time
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateAndTime = dateFormat.format(new Date());


                    weatherData.append("Location: ").append(currentLocation).append("\n");
                    weatherData.append(" ").append(currentDateAndTime).append("\n");

                    String title = "";
                    String OverviewText = "";
                    String description = "";
                    String TemperatueText = " ";
                    String PressureText = " ";
                    String VisibilityText = " ";

                    for (int i = 0; i < itemList.getLength(); i++) {
                        Element item = (Element) itemList.item(i);
                         title = item.getElementsByTagName("title").item(0).getTextContent();
                         description = item.getElementsByTagName("description").item(0).getTextContent();

                        String minMaxTemperature = extractTemperature(title);

                        //weatherData.append(title);
                                //.append(minMaxTemperature).append("\n");
                                //.append("Description: ").append(description).append("\n\n");

                        List<String> DescriptionList = Arrays.asList(description.split(","));

                        //weatherData.append(DescriptionList.toString()).append("\n");

                        OverviewText = DescriptionList.get(3).toString();
                        TemperatueText = DescriptionList.get(0).toString();
                        PressureText = DescriptionList.get(4).toString();
                        VisibilityText = DescriptionList.get(6).toString();

                        break; // Display only one day's information
                    }





                    updateUI(weatherData.toString(),OverviewText ,TemperatueText, PressureText, VisibilityText);

                    // Call the method to fetch three-day forecast
                    fetchThreeDayForecast(locationId);
                    fetchExtendedForecast(locationId);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void fetchThreeDayForecast(final String locationId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/" + locationId);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    InputStream inputStream = connection.getInputStream();
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(inputStream);
                    doc.getDocumentElement().normalize();

                    NodeList itemList = doc.getElementsByTagName("item");

                    final StringBuilder firstDayForecast = new StringBuilder();
                    final StringBuilder secondDayForecast = new StringBuilder();
                    final StringBuilder thirdDayForecast = new StringBuilder();

                    for (int i = 0; i < itemList.getLength(); i++) {
                        Element item = (Element) itemList.item(i);
                        String title = item.getElementsByTagName("title").item(0).getTextContent();
                        String description = item.getElementsByTagName("description").item(0).getTextContent();

                        String minMaxTemperature = extractTemperature(title);

                        // Append forecast information for each day to the corresponding StringBuilder
                        if (i == 0) {
                            firstDayForecast.append(title).append("\n")
                                    .append(minMaxTemperature).append("\n");
                                    //.append("Description: ").append(description).append("\n\n");
                        } else if (i == 1) {
                            secondDayForecast.append(title).append("\n")
                                    .append(minMaxTemperature).append("\n");
                                    //.append("Description: ").append(description).append("\n\n");
                        } else if (i == 2) {
                            thirdDayForecast.append(title).append("\n")
                                    .append(minMaxTemperature).append("\n");
                                    //.append("Description: ").append(description).append("\n\n");
                        }
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Update UI with three-day forecast
                            ((TextView) findViewById(R.id.threeDayForecastTextView)).setText("Three Day Forecast:");
                            ((TextView) findViewById(R.id.firstDayForecastTextView)).setText(firstDayForecast.toString());

                            // Display forecasts for second and third days
                            ((TextView) findViewById(R.id.secondDayForecastTextView)).setText(secondDayForecast.toString());
                            ((TextView) findViewById(R.id.thirdDayForecastTextView)).setText(thirdDayForecast.toString());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }



    private void fetchExtendedForecast(final String locationId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/" + locationId);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    InputStream inputStream = connection.getInputStream();
                    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                    Document doc = dBuilder.parse(inputStream);
                    doc.getDocumentElement().normalize();

                    NodeList itemList = doc.getElementsByTagName("item");

                    final StringBuilder extendedForecast = new StringBuilder();

                    // Append extended forecast information for the next three days
                    for (int i = 0; i < itemList.getLength() && i < 1; i++) {
                        Element item = (Element) itemList.item(i);
                        //String title = item.getElementsByTagName("title").item(0).getTextContent();
                        String description = item.getElementsByTagName("description").item(0).getTextContent();

//                        String minMaxTemperature = extractTemperature(title);

                        extendedForecast.append("Description: ").append(description).append("\n\n");
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Update the extended forecast TextView
                            ((TextView) findViewById(R.id.thirdDayForecastExtendedTextView)).setText(extendedForecast.toString());
                            ((TextView) findViewById(R.id.secondDayForecastExtendedTextView)).setText(extendedForecast.toString());
                            ((TextView) findViewById(R.id.firstDayForecastExtendedTextView)).setText(extendedForecast.toString());



                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }





//    private String extractTemperature(String title) {
//        String[] parts = title.split(":");
//        if (parts.length >= 2) {
//            String temperaturePart = parts[1].trim();
//            // Check if temperature part contains Celsius or Fahrenheit
//            if (temperaturePart.contains("°C") || temperaturePart.contains("°F")) {
//                // Extract temperature and weather classification
//                String[] temperatureParts = temperaturePart.split(",");
//                if (temperatureParts.length >= 2) {
//                    String minTemperature = temperatureParts[0].trim();
//                    String weatherClassification = temperatureParts[1].trim();
//                    return "Minimum Temperature: " + minTemperature + ", Weather: " + weatherClassification;
//                } else {
//                    Log.e("ExtractTemperature", "Unexpected format for temperature parts: " + temperaturePart);
//                }
//            } else {
//                Log.e("ExtractTemperature", "Temperature format not recognized: " + temperaturePart);
//            }
//        } else {
//            Log.e("ExtractTemperature", "Unexpected format for title: " + title);
//        }
//        return "";
//    }

    private String extractTemperature(String title) {
        String[] parts = title.split(":");
        if (parts.length >= 2) {
            String temperaturePart = parts[1].trim();
            // Check if temperature part contains Celsius or Fahrenheit
            if (temperaturePart.contains("°C") || temperaturePart.contains("°F")) {
                // Extract temperature and weather classification
                String[] temperatureParts = temperaturePart.split(",");
                if (temperatureParts.length >= 2) {
                    String minTemperature = temperatureParts[0].trim();
                    String weatherClassification = temperatureParts[1].trim();
                    return "Minimum Temperature: " + minTemperature + ", Weather: " + weatherClassification;
                } else {
                    Log.e("ExtractTemperature", "Unexpected format for temperature parts: " + temperaturePart);
                }
            } else {
                Log.e("ExtractTemperature", "Temperature format not recognized: " + temperaturePart);
            }
        } else {
            Log.e("ExtractTemperature", "Unexpected format for title: " + title);
        }
        return "";
    }


    private void updateUI(final String weatherInfo, String overview , String temperture , String pressure, String visibility) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                weatherTextView.setText(weatherInfo);
                Overview.setText(overview);
                Temperatue.setText(temperture);
                Pressure.setText(pressure);
                Visibility.setText(visibility);
            }
        });
    }

    private void navigateToPreviousLocation() {
        currentLocationIndex--;
        if (currentLocationIndex < 0) {
            currentLocationIndex = locationMap.size() - 1;
        }
        fetchWeatherData();
    }

    private void navigateToNextLocation() {
        currentLocationIndex++;
        if (currentLocationIndex >= locationMap.size()) {
            currentLocationIndex = 0;
        }
        fetchWeatherData();
    }

    private String getCurrentLocation() {
        String[] keys = locationMap.keySet().toArray(new String[0]);
        return keys[currentLocationIndex];
    }


    public void onThirdDayForecastClick(View view) {
        String currentLocation = getCurrentLocation();

        String locationId = locationMap.get(currentLocation);
        TextView extendedForecastTextView = findViewById(R.id.thirdDayForecastExtendedTextView);
        // Toggle visibility of the extended forecast TextView
        if (extendedForecastTextView.getVisibility() == View.GONE) {
            extendedForecastTextView.setVisibility(View.VISIBLE);
            // Fetch and display extended forecast data when the TextView is expanded
            fetchExtendedForecast(locationId);
        } else {
            extendedForecastTextView.setVisibility(View.GONE);
        }
    }

    public void onFirstDayForecastExtendedClick(View view) {
        String currentLocation = getCurrentLocation();
        String locationId = locationMap.get(currentLocation);
        TextView extendedForecastTextView = findViewById(R.id.firstDayForecastExtendedTextView);
        // Toggle visibility of the extended forecast TextView
        if (extendedForecastTextView.getVisibility() == View.GONE) {
            extendedForecastTextView.setVisibility(View.VISIBLE);
            // Fetch and display extended forecast data when the TextView is expanded
            fetchExtendedForecast(locationId);
        } else {
            extendedForecastTextView.setVisibility(View.GONE);
        }
    }

    public void onSecondDayForecastExtendedTextView(View view) {
        String currentLocation = getCurrentLocation();
        String locationId = locationMap.get(currentLocation);
        TextView extendedForecastTextView = findViewById(R.id.secondDayForecastExtendedTextView);
        // Toggle visibility of the extended forecast TextView
        if (extendedForecastTextView.getVisibility() == View.GONE) {
            extendedForecastTextView.setVisibility(View.VISIBLE);
            // Fetch and display extended forecast data when the TextView is expanded
            fetchExtendedForecast(locationId);
        } else {
            extendedForecastTextView.setVisibility(View.GONE);
        }
    }


}
