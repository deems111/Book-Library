package learn.library.model;

import learn.library.integration.BookIntegrationGateway;
import learn.library.integration.service.BookIntegrationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
@Slf4j
public class ShellModel {

    private final Job job;

    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    private final BookIntegrationGateway bookIntegrationGateway;
    private final BookIntegrationService bookIntegrationService;

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

    @ShellMethod(value = "Start Integration", key = "i")
    public void saveIntegrateBooks() {
        try {
            bookIntegrationGateway.process(bookIntegrationService.createBook());
        } catch (Exception e) {
            String error = "Error While Integration ";
            e.printStackTrace();
            log.error(error + e.getMessage());
        }
    }

}

