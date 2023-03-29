package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.xml";
        List<Employee> list = parseXML(fileName);
        String json = listToJson(list);
        writeString(json, "data.json");
    }

    private static List<Employee> parseXML(String fileName) {
        try {
            List<Employee> employeeList = new ArrayList<>();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(fileName);


            Node node = document.getDocumentElement();
            NodeList nodeList = node.getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node innerNode = nodeList.item(i);
                if (innerNode.getNodeName().equals("employee")) {
                   Employee employee = createEmployee(innerNode);
                   employeeList.add(employee);
                }
            }
            return employeeList;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Employee createEmployee(Node innerNode) {
        Employee employee;
        List<String> empList = new ArrayList<>();
        NodeList nodeList = innerNode.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()){
                empList.add(node.getTextContent());
            }
        }
        employee = new Employee(
                Long.parseLong(empList.get(0)),
                empList.get(1),
                empList.get(2),
                empList.get(3),
                Integer.parseInt(empList.get(4))
        );
        return employee;
    }


    private static void writeString(String json, String url) {
        try (FileWriter fileWriter = new FileWriter(url)) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(list, listType);
        return json;

    }
}