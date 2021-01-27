package gradle.plugin;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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




public class InsertARecordToTheTableEmployees {

    String  driverName,
            pathToDb,
            login,
            password;

    public InsertARecordToTheTableEmployees(String file, String driverName, String pathToDb, String login, String password) throws IOException, SAXException, ParserConfigurationException, SQLException {

        this.driverName = driverName;
        this.pathToDb = pathToDb;
        this.login = login;
        this.password = password;

        ArrayList<String> queries = parseXml(this.getClass().getResourceAsStream(file));
        sendQueryToDb(queries);
    }

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
                String first_name = element.getElementsByTagName("First_Name").item(0).getChildNodes().item(0).getNodeValue();
                String second_name = element.getElementsByTagName("Second_Name").item(0).getChildNodes().item(0).getNodeValue();
                String patronymic = element.getElementsByTagName("Patronymic").item(0).getChildNodes().item(0).getNodeValue();
                String login = element.getElementsByTagName("Login").item(0).getChildNodes().item(0).getNodeValue();
                String password = element.getElementsByTagName("Password").item(0).getChildNodes().item(0).getNodeValue(); 
                String salt = element.getElementsByTagName("Salt").item(0).getChildNodes().item(0).getNodeValue();
                String activity = element.getElementsByTagName("Activity").item(0).getChildNodes().item(0).getNodeValue();
                String rights = element.getElementsByTagName("Rights").item(0).getChildNodes().item(0).getNodeValue();

                //salt = FirstLoginPasswordGenerator.generateSalt();
                //password = FirstLoginPasswordGenerator.getSaltPassword("1234", salt);

                queries.add(
                        "INSERT INTO employees(id,first_name,second_name,patronymic,login,password,salt,activity,rights_id) " +
                                "VALUES ('" + id + "','" + first_name + "','" + second_name + "','" + patronymic + "','" + login + "','" + password + "','" + salt + "','" + activity + "','" + rights + "') ON CONFLICT DO NOTHING ;");
            }

        }
        return queries;
    }

    private void sendQueryToDb(ArrayList<String> queries) throws SQLException {

        Connection connection = null;

        try {
            Class.forName (driverName);
            connection = DriverManager.getConnection(pathToDb, login, password);
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

}
