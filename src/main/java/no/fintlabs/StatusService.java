package no.fintlabs;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import no.fintlabs.model.Status;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@Service
public class StatusService {

    static Status SYSTEM_DEFAULT_REPLY_STATUS = Status.ACCEPTED;

    @Getter
    public Status defaultReplyStatus;
    @Getter
    public Queue<Status> replyStatusQueue;

    public StatusService() {
        defaultReplyStatus = SYSTEM_DEFAULT_REPLY_STATUS;
    }

    public void setDefaultReplyStatus(Status status) {
        log.info("Setting continuous reply status to '" + status + "'");
        defaultReplyStatus = status;
    }

    public void setReplyStatusQueue(Queue<Status> statusQueue) {
        log.info("Setting reply status queue to " + statusQueue);
        this.replyStatusQueue = statusQueue;
    }

    public void resetReplyStatus() {
        log.info("Resetting reply statuses");
        setDefaultReplyStatus(SYSTEM_DEFAULT_REPLY_STATUS);
        setReplyStatusQueue(new LinkedList<>());
    }

    public Status getNextReplyStatus() {
        if (replyStatusQueue != null && !replyStatusQueue.isEmpty()) {
            return replyStatusQueue.remove();
        }
        return defaultReplyStatus;
    }

}
