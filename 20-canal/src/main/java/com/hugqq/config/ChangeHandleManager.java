package com.hugqq.config;

import com.hugqq.service.IChangeHandler;
import com.hugqq.service.ITableChangeHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ChangeHandleManager {

    public static List<IChangeHandler> list = new ArrayList<>();

    static {
        Map<String, ITableChangeHandler> result = SpringUtils.getApplicationContext().getBeansOfType(ITableChangeHandler.class);
        try {
            for (Map.Entry<String, ITableChangeHandler> entry : result.entrySet()) {
                registerChangeHandler(SpringUtils.getBean(entry.getValue().getClass()));
            }
        } catch (Exception e) {
            log.error("ChangeHandleManager fail ! e=", e);
        }
    }

    public static List<IChangeHandler> getChangeHandler() {
        return list;
    }

    private static void registerChangeHandler(IChangeHandler changeHandler) {
        if (changeHandler != null) {
            list.add(changeHandler);
        }
    }
}
