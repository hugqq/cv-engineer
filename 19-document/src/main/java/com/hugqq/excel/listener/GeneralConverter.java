package com.hugqq.excel.listener;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

public class GeneralConverter implements Converter<String> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) {
        return cellData.getStringValue();
    }
    @Override
    public WriteCellData<String> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        // 手机号脱敏
        if (value != null && value.length() == 11) {
            value = value.substring(0, 3) + "****" + value.substring(7);
        }
        // 邮箱脱敏
        if (value != null && value.contains("@")) {
            value = value.substring(0, 3) + "****" + value.substring(value.indexOf("@"));
        }
        // 身份证脱敏
        if (value != null && value.length() == 18) {
            value = value.substring(0, 3) + "**************" + value.substring(17);
        }
        // 日期脱敏
        if (value != null && value.length() == 19) {
            value = value.substring(0, 10) + "****" + value.substring(14);
        }
        if (value == null) {
            value = "";
        }
        return new WriteCellData<>(value);
    }

}
