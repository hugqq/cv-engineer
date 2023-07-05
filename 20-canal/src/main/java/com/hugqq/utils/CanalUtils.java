package com.hugqq.utils;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.hugqq.service.IChangeHandler;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.net.InetSocketAddress;
import java.util.List;

public class CanalUtils {

    private static List<IChangeHandler> changeHandlers;

    private static volatile CanalUtils instance;

    public CanalUtils attachChangeHandler(List<IChangeHandler> handlers){changeHandlers = handlers; return this;}

    public static CanalUtils getInstance(){
        if(instance == null){
            synchronized (CanalUtils.class){
                if(instance == null){
                    instance = new CanalUtils();
                }
            }
        }
        return instance;
    }

    public void connServer(String dataBaseIp,String tables,String canalName) {
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(dataBaseIp, 11111),
                canalName, "", "");
        int batchSize = 1000;
        try {
            connector.connect();
            connector.subscribe(tables);
            connector.rollback();
            while (true) {
                Long batchId = null;
                try {
                    // 获取指定数量的数据
                    com.alibaba.otter.canal.protocol.Message message = connector.getWithoutAck(batchSize);
                    batchId = message.getId();
                    if (!(batchId == -1 || message.getEntries().size() == 0)) {
                        doSync(message.getEntries());
                    }
                    // 提交确认
                    connector.ack(batchId);
                }catch (Exception e){
                    e.printStackTrace();
                    connector.ack(batchId);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connector.disconnect();
        }
    }

    private void doSync(@NotNull List<CanalEntry.Entry> entries) {
        if(!CollectionUtils.isEmpty(changeHandlers)){
            for (IChangeHandler changeHandler : changeHandlers) {
                changeHandler.handleChange(entries);
            }
        }
    }
}
