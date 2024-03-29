package no.fintlabs;

import no.fintlabs.model.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/test/dispatch-gateway")
public class TestController {

    private final StatusService statusService;

    public TestController(StatusService statusService) {
        this.statusService = statusService;
    }

    @PutMapping("default-reply-status")
    public ResponseEntity<?> setDefaultReplyStatus(@RequestBody() Status replyStatus) {
        statusService.setDefaultReplyStatus(replyStatus);
        return ResponseEntity.ok(statusService.getDefaultReplyStatus());
    }

    @PutMapping("reply-status-queue")
    public ResponseEntity<List<Status>> setReplyStatusQueue(@RequestBody() List<Status> replyStatuses) {
        statusService.setReplyStatusQueue(new LinkedList<>(replyStatuses));
        return ResponseEntity.ok(statusService.getReplyStatusQueue().stream().toList());
    }

    @DeleteMapping("reply-status-configuration")
    public ResponseEntity<?> resetReplyStatus() {
        statusService.resetReplyStatus();
        return ResponseEntity.ok().build();
    }

    @GetMapping("reply-status-configuration")
    public ResponseEntity<StatusService> getReplyStatusConfiguration() {
        return ResponseEntity.ok(statusService);
    }

}
