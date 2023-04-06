package com.hugqq.excel.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.data.CellData;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelExceptionListener<T> extends AnalysisEventListener<T> {

    /**
     * 每隔3000条存储数据库，清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;

    List<T> list = new ArrayList<>();

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException excelDataConvertException) {
            String cellMsg = "";
            CellData cellData = excelDataConvertException.getCellData();
            //这里有一个celldatatype的枚举值,用来判断CellData的数据类型
            CellDataTypeEnum type = cellData.getType();
            if (type.equals(CellDataTypeEnum.NUMBER)) {
                cellMsg = cellData.getNumberValue().toString();
            } else if (type.equals(CellDataTypeEnum.STRING)) {
                cellMsg = cellData.getStringValue();
            } else if (type.equals(CellDataTypeEnum.BOOLEAN)) {
                cellMsg = cellData.getBooleanValue().toString();
            }
            log.error("excel表格:第{}行,第{}列,数据值为:{},该数据值不符合要求,请检验后重新导入! 请检查其他的记录是否有同类型的错误!",
                    excelDataConvertException.getRowIndex() + 1, excelDataConvertException.getColumnIndex(), cellMsg);
        }
    }

    /**
     * 这里会一行行的返回头
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.info("解析到一条头数据:{}", headMap);
    }

    @Override
    public void invoke(T data, AnalysisContext context) {
        log.info("解析到一条数据:{}", data);
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        log.info("存储数据库成功！");
    }
}
