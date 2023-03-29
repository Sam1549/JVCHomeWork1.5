package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String json = readString("data.json");
        System.out.println(json);
        List<Employee> list = jsonToList(json);


    }



    public static String readString(String url) {
        try (BufferedReader reader = new BufferedReader(new FileReader(url));) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            return stringBuilder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Employee> jsonToList(String json) {
        List<Employee> employees = new Gson().fromJson(json, new TypeToken<List<Employee>>() {}.getType());
        employees.forEach(System.out::println);
        return employees;
    }
}