package mytasks.gradle;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class XmlReaderAndDbUploaderPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getTasks().create("ToReadXmlAndToQueryToDb", XmlReaderAndDbUploaderTask.class);
    }
}
