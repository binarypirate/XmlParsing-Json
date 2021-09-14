package com.example.xmlparsingjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> contactList;

    Button btnParsXmlData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList = new ArrayList<>();
        btnParsXmlData = findViewById(R.id.parseXml);


        btnParsXmlData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startXmlParsing();
            }
        });

    }

    private void startXmlParsing() {
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("xmlData.xml");
        }catch (Exception e){
            e.printStackTrace();
        }
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document doc = null;
        try {
            doc = documentBuilder.parse(inputStream);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        Element element = doc.getDocumentElement();
        element.normalize();

        NodeList nodeList = doc.getElementsByTagName("string");

        for (int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ATTRIBUTE_NODE){
                Element element1 = (Element) node;
                String id = element1.getElementsByTagName("id").item(Integer.parseInt("0")).getTextContent();
                String name = element1.getElementsByTagName("name").item(Integer.parseInt("0")).getTextContent();
                String sureName = element1.getElementsByTagName("surename").item(Integer.parseInt("0")).getTextContent();
                String age = element1.getElementsByTagName("age").item(Integer.parseInt("0")).getTextContent();
                addingValueToHashMap(id,name,sureName,age);
            }
        }
        ListView listView = findViewById(R.id.listView);
        ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList,
                R.layout.list_items, new  String[] {"id","name","surename","age"},
                new int[]{ R.id.idNum,R.id.name,R.id.sureName,R.id.age});
        listView.setAdapter(adapter);
    }

    private void addingValueToHashMap(String id, String name, String sureName, String age) {
        HashMap<String, String> myData = new HashMap<>();
        myData.put("id", id);
        myData.put("name",name);
        myData.put("surename",sureName);
        myData.put("age",age);

        contactList.add(myData);

    }
}