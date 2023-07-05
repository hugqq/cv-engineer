package com.hugqq.service;

import javax.validation.constraints.NotNull;
import java.util.logging.Logger;

public interface ITableChangeHandler extends IChangeHandler {


    @Override
    default void handleChange(@NotNull List<CanalEntry.Entry> entryList) {
        for (CanalEntry.Entry entry : entryList) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            CanalEntry.RowChange rowChange = null;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                logger.error("## ERROR ##, data : " + entry.toString(), e);
            }

            CanalEntry.EventType eventType = rowChange.getEventType();
            logger.info(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                if (eventType == CanalEntry.EventType.DELETE) {
                    delete(rowData.getBeforeColumnsList(),entry.getHeader().getSchemaName(), entry.getHeader().getTableName());
                } else if (eventType == CanalEntry.EventType.INSERT) {
                    insert(rowData.getAfterColumnsList(),entry.getHeader().getSchemaName(), entry.getHeader().getTableName());
                } else if (eventType == CanalEntry.EventType.UPDATE) {
                    update(rowData.getBeforeColumnsList(),rowData.getAfterColumnsList(),entry.getHeader().getSchemaName(), entry.getHeader().getTableName());
                }
            }
        }
    }

    void delete(List<CanalEntry.Column> beforeList, String db, String table);
    void insert(List<CanalEntry.Column> afterList, String db, String table);
    void update(List<CanalEntry.Column> beforeList, List<CanalEntry.Column> afterList, String db, String table);

}
