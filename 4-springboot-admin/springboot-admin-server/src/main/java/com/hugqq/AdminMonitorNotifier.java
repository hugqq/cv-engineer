package com.hugqq;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
@Component
public class AdminMonitorNotifier extends AbstractStatusChangeNotifier {

    private String[] ignoreChanges = new String[]{"UNKNOWN:UP", "DOWN:UP", "OFFLINE:UP"};

    public AdminMonitorNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected boolean shouldNotify(InstanceEvent event, Instance instance) {
        if (!(event instanceof InstanceStatusChangedEvent)) {
            return false;
        } else {
            InstanceStatusChangedEvent statusChange = (InstanceStatusChangedEvent) event;
            String from = this.getLastStatus(event.getInstance());
            String to = statusChange.getStatusInfo().getStatus();
            return Arrays.binarySearch(this.ignoreChanges, from + ":" + to) < 0 && Arrays.binarySearch(this.ignoreChanges, "*:" + to) < 0 && Arrays.binarySearch(this.ignoreChanges, from + ":*") < 0;
        }
    }


    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                String status = ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus();
                switch (status) {
                    case "DOWN":
                        log.info("服务健康状态不通过 {},{}", status, instance.getRegistration().getName());
                        break;
                    case "OFFLINE":
                        log.info("服务下线状态 {},{}", status, instance.getRegistration().getName());
                        break;
                    case "UP":
                        log.info("服务上线状态 {},{}", status, instance.getRegistration().getName());
                        break;
                    case "UNKNOWN":
                        log.info("服务未知状态异常 {}", instance.getRegistration().getName());
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
