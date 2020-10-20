package learn.library.integration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class AnotherService {

    //only for sleep
    public <T> T doSomething(T o) throws InterruptedException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        log.info("time before " + formatter.format(new Date()));
        log.info("doSomething");
        Thread.sleep(10000);
        log.info(o.toString());
        log.info("time after " + formatter.format(new Date()));
        return o;
    }

}
