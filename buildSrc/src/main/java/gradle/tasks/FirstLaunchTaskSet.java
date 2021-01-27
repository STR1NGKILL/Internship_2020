package gradle.tasks;

import gradle.plugin.InsertARecordToTheTableEmployees;
import gradle.plugin.InsertARecordToTheTableRights;
import gradle.plugin.XmlReaderAndDbUploaderActions;
import gradle.plugin.XmlReaderAndDbUploaderCurrencies;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

public class FirstLaunchTaskSet extends DefaultTask {

    private static String
            DRIVER_NAME = "org.postgresql.Driver",
            PATH_TO_DB1 = "jdbc:postgresql://localhost:5432/MainDb",
            PATH_TO_DB2 = "jdbc:postgresql://localhost:5432/EmployeeDb",
            LOGIN = "postgres",
            PASSWORD = "4145";


    @TaskAction
    public void run() throws SAXException, ParserConfigurationException, SQLException, IOException {
        new XmlReaderAndDbUploaderActions("/actions.xml", DRIVER_NAME, PATH_TO_DB2, LOGIN, PASSWORD);
        new XmlReaderAndDbUploaderCurrencies("/currencies.xml", DRIVER_NAME, PATH_TO_DB1, LOGIN, PASSWORD);
        new InsertARecordToTheTableRights("/rights.xml", DRIVER_NAME, PATH_TO_DB2, LOGIN, PASSWORD);
        new InsertARecordToTheTableEmployees("/employees.xml", DRIVER_NAME, PATH_TO_DB2, LOGIN, PASSWORD);
    }

}
