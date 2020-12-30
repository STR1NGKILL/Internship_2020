package gradle.tasks;

import gradle.plugin.XmlReaderAndDbUploaderActions;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

public class RunReadingXmlWithActionsOfRights extends DefaultTask {

    @TaskAction
    public void run() throws SAXException, ParserConfigurationException, SQLException, IOException {
        XmlReaderAndDbUploaderActions xmlReaderAndDbUploaderAction = new XmlReaderAndDbUploaderActions("/actions.xml","org.postgresql.Driver", "jdbc:postgresql://localhost:5432/EmployeeDb", "postgres", "4145");
    }
}
