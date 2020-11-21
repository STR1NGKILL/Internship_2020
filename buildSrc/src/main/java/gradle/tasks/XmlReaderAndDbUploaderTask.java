package gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class XmlReaderAndDbUploaderTask extends DefaultTask {

    private ArrayList<String> parseXml(InputStream file) throws ParserConfigurationException, IOException, SAXException {

        ArrayList<String> queries = new ArrayList<>();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document document = documentBuilder.parse(file);

        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;

                String id = element.getElementsByTagName("Id").item(0).getChildNodes().item(0).getNodeValue();
                String name = element.getElementsByTagName("Name").item(0).getChildNodes().item(0).getNodeValue();
                String description = element.getElementsByTagName("Description").item(0).getChildNodes().item(0).getNodeValue();

                queries.add(
                        "INSERT INTO actions(id,name,description) " +
                        "VALUES ('" + id + "','" + name + "','" + description + "') ON CONFLICT DO NOTHING ;");
            }

        }




        return queries;
    }

    private void sendQueryToDb(ArrayList<String> queries) throws SQLException {

        Connection connection = null;

        try {
            Class.forName ("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/EmployeeDb", "postgres", "4145");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Connection fail!" + "\n\n");
            e.printStackTrace();
            return;
        }

        Statement statement = connection.createStatement();

        for (String query: queries) {
            statement.execute(query);
        }
        System.out.println("Queries complete!");

    }

    @TaskAction
    public void run() throws IOException, SAXException, ParserConfigurationException, SQLException {
        ArrayList<String> queries = parseXml(this.getClass().getResourceAsStream("/actions.xml"));
        sendQueryToDb(queries);
    }


}
