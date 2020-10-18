package learn.library.model;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
@Slf4j
public class MigrateDataToMongoModel {

    private final Job job;

    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    @ShellMethod(value = "Migrate Data From Sql Database to Mongo", key = "fillBooks")
    public String getAllBooks() {
        try {
            jobLauncher.run(job, new JobParametersBuilder().toJobParameters());
        } catch (Exception e) {
            String error = "Error While Migrate Data From Sql Database to Mongo Job ";
            e.printStackTrace();
            log.error(error + e.getMessage());
            return error;
        }

        return jobExplorer.getJobNames().toString();
    }
}

