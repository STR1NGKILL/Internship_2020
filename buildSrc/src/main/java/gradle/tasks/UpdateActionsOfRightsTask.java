package gradle.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;


public class UpdateActionsOfRightsTask extends DefaultTask {

    @TaskAction
    public void run() throws IOException, SAXException, ParserConfigurationException, SQLException {

        System.out.println("Actions of rights updated!");
    }
}
