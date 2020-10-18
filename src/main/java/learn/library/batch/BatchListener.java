package learn.library.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchListener implements SkipListener<Object, Object> {

    @Override
    public void onSkipInRead(Throwable throwable) {
    }

    @Override
    public void onSkipInWrite(Object o, Throwable throwable) {
    }

    @Override
    public void onSkipInProcess(Object item, Throwable throwable) {
        log.error("Item Was Skipped " + item + throwable.getMessage());
    }
}