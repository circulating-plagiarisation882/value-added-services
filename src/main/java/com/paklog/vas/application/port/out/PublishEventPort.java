package com.paklog.vas.application.port.out;

import com.paklog.vas.domain.event.DomainEvent;

public interface PublishEventPort {
    void publish(DomainEvent event);
}
