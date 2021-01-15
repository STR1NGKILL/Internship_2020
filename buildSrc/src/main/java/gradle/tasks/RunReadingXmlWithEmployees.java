package gradle.tasks;

import gradle.plugin.InsertARecordToTheTableEmployees;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

public class RunReadingXmlWithEmployees  extends DefaultTask {

    @TaskAction
    public void run() throws SAXException, ParserConfigurationException, SQLException, IOException {
        InsertARecordToTheTableEmployees insertARecordToTheTableEmployees = new InsertARecordToTheTableEmployees("/employees.xml","org.postgresql.Driver", "jdbc:postgresql://localhost:5432/EmployeeDb2", "postgres", "4145");
    }
}
