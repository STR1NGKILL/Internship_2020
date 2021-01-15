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

public class XmlReaderAndDbUploaderCurrencies {

    String  driverName,
            pathToDb,
            login,
            password;

    public XmlReaderAndDbUploaderCurrencies(String file, String driverName, String pathToDb, String login, String password) throws IOException, SAXException, ParserConfigurationException, SQLException {

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
                String name = element.getElementsByTagName("Name").item(0).getChildNodes().item(0).getNodeValue();
                String code = element.getElementsByTagName("Code").item(0).getChildNodes().item(0).getNodeValue();
                String sign = element.getElementsByTagName("Sign").item(0).getChildNodes().item(0).getNodeValue();
                String value = element.getElementsByTagName("Value").item(0).getChildNodes().item(0).getNodeValue();

                queries.add(
                        "INSERT INTO currencies(id,name,code,sign,value) " +
                                "VALUES ('" + id + "','" + name + "','" + code + "','"  + sign + "','" + value + "') ON CONFLICT DO NOTHING ;");
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
