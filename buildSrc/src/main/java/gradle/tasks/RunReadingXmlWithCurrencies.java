package gradle.tasks;

import gradle.plugin.XmlReaderAndDbUploaderCurrencies;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;

public class RunReadingXmlWithCurrencies extends DefaultTask {

    @TaskAction
    public void run() throws SAXException, ParserConfigurationException, SQLException, IOException {
        XmlReaderAndDbUploaderCurrencies xmlReaderAndDbUploaderCurrencies = new XmlReaderAndDbUploaderCurrencies("/currencies.xml","org.postgresql.Driver", "jdbc:postgresql://localhost:5432/MainDb", "postgres", "4145");
    }
}
