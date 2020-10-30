package mytasks.gradle;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class XmlReaderAndDbUploaderTask extends DefaultTask {

    private static ArrayList<String> readXml(String filepath) throws ParserConfigurationException, IOException, SAXException {

        ArrayList<String> queries = new ArrayList<String>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new File(filepath));
        NodeList nodeList = (document.getElementsByTagName("DefaultActions"));
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node action = nodeList.item(i);
            if (Node.ELEMENT_NODE == action.getNodeType()) {
                NamedNodeMap attributes = action.getAttributes();
                String id = attributes.getNamedItem("Id").getNodeValue();
                String name = attributes.getNamedItem("Name").getNodeValue();
                String description = attributes.getNamedItem("Description").getNodeValue();

                queries.add("INSERT INTO 'Actions'('id','name','description','status')" +
                        " VALUES ('" + id + "','" + name + "','" + description + "');");
            }
        }

        return queries;
    }

    private static void sendQueryToDb(ArrayList<String> queries) throws SQLException {

        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Company", "postgres", "41456365");
        } catch (SQLException e) {
            System.out.println("Connection fail!" + "\n\n");
            e.printStackTrace();
            return;
        }

        Statement statement = connection.createStatement();

        for (String query: queries) {
            statement.execute(query);
        }

    }

    @TaskAction
    public void run() throws IOException, SAXException, ParserConfigurationException, SQLException {
        ArrayList<String> queries = readXml("/src/main/resources/default_actions.xml");
        sendQueryToDb(queries);
        System.out.println("Queries complete!");
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, SQLException {
        ArrayList<String> queries = readXml("/src/main/resources/default_actions.xml");
        sendQueryToDb(queries);
        System.out.println("Queries complete!");
    }

}
