package com.hugqq.config;

import com.hugqq.utils.CanalUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CanalEventConfig implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        EnvironmentAlone.createInstance(event.getApplicationContext().getEnvironment());
        String dbIp =EnvironmentAlone.getInstance().getEnvironmentParam("db.ip");
        String tables =EnvironmentAlone.getInstance().getEnvironmentParam("tables");
        String canalName =EnvironmentAlone.getInstance().getEnvironmentParam("canal.name");
        CanalUtils.getInstance().attachChangeHandler(ChangeHandleManager.getChangeHandler()).connServer(dbIp,tables,canalName);
    }
}
