package com.latrell.design.factory;

import com.latrell.design.factory.service.ExportService;
import com.latrell.design.factory.service.impl.ExcelExportServiceImpl;
import com.latrell.design.factory.service.impl.PdfExportServiceImpl;
import com.latrell.design.factory.service.impl.WordExportServiceImpl;

/**
 * 导出工厂类
 *
 * @author liz
 * @date 2023/3/11-15:45
 */
public class ExportFactory {

    public ExportService getExportService(ExportTypeEnum exportType) {
        switch (exportType) {
            case WORD:
                return new WordExportServiceImpl();
            case PDF:
                return new PdfExportServiceImpl();
            case EXCEL:
                return new ExcelExportServiceImpl();
        }
        throw new UnsupportedOperationException("不支持该种数据格式导出");
    }

}
