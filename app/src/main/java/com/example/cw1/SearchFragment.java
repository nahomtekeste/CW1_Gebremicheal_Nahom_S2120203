package com.example.cw1;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SearchFragment extends Fragment {

    private EditText countrySearchEditText;
    private TextView temperatureTextView;
    private List<String> countries;

    private Map<String, String> temperatureData;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        countrySearchEditText = rootView.findViewById(R.id.countrySearchEditText);
        temperatureTextView = rootView.findViewById(R.id.temperatureTextView);

        // Initialize list of countries
        countries = new ArrayList<>();
        countries.add("Glasgow");
        countries.add("London");
        countries.add("New York");
        countries.add("Oman");
        countries.add("Mauritius");
        countries.add("Bangladesh");

        // Initialize temperature data map
        temperatureData = new HashMap<>();
        temperatureData.put("Glasgow", "20°C");
        temperatureData.put("London", "18°C");
        temperatureData.put("New York", "15°C");
        temperatureData.put("Oman", "30°C");
        temperatureData.put("Mauritius", "25°C");
        temperatureData.put("Bangladesh", "28°C");

        // Set a listener for text changes in the country search EditText
        countrySearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter countries based on the search query
                searchCountry(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed
            }
        });

        return rootView;
    }

//    private void fetchWeatherData() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    StringBuilder weatherData = new StringBuilder();
//
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
//                    // Get current date and time
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    String currentDateAndTime = dateFormat.format(new Date());
//
//
//                    weatherData.append("Location: ").append(currentLocation).append("\n");
//                    weatherData.append(" ").append(currentDateAndTime).append("\n");
//
//                    String title = "";
//                    String OverviewText = "";
//                    String description = "";
//                    String TemperatueText = " ";
//                    String PressureText = " ";
//                    String VisibilityText = " ";
//
//                    for (int i = 0; i < itemList.getLength(); i++) {
//                        Element item = (Element) itemList.item(i);
//                        title = item.getElementsByTagName("title").item(0).getTextContent();
//                        description = item.getElementsByTagName("description").item(0).getTextContent();
//
//                        String minMaxTemperature = extractTemperature(title);
//
//                        //weatherData.append(title);
//                        //.append(minMaxTemperature).append("\n");
//                        //.append("Description: ").append(description).append("\n\n");
//
//                        List<String> DescriptionList = Arrays.asList(description.split(","));
//
//                        //weatherData.append(DescriptionList.toString()).append("\n");
//
//                        OverviewText = DescriptionList.get(3).toString();
//                        TemperatueText = DescriptionList.get(0).toString();
//                        PressureText = DescriptionList.get(4).toString();
//                        VisibilityText = DescriptionList.get(6).toString();
//
//                        break; // Display only one day's information
//                    }
//
//
//
//
//
//                    updateUI(weatherData.toString(),OverviewText ,TemperatueText, PressureText, VisibilityText);
//
//                    // Call the method to fetch three-day forecast
//                    fetchThreeDayForecast(locationId);
//                    fetchExtendedForecast(locationId);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    // Method to search for a country and display its temperature
    private void searchCountry(String searchQuery) {
        String temperature = temperatureData.get(searchQuery);
        if (temperature != null) {
            String displayText = "Current Temperature in "+ searchQuery + " = " + temperature;
            temperatureTextView.setText(displayText);
        } else {
            temperatureTextView.setText("Temperature not available");
        }
    }

}
