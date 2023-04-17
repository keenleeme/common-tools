package com.latrell.design.factory;

import com.latrell.design.factory.service.ExportService;

/**
 * TODO
 *
 * @author liz
 * @date 2023/3/11-15:54
 */
public class FactoryTest {

    public static void main(String[] args) {
        // 导出Word
        ExportFactory exportFactory = new ExportFactory();
        ExportService wordExportService = exportFactory.getExportService(ExportTypeEnum.WORD);
        wordExportService.export();

        // 导出Pdf
        ExportService pdfExportService = exportFactory.getExportService(ExportTypeEnum.PDF);
        pdfExportService.export();

        // 导出Word
        ExportService excelExportService = exportFactory.getExportService(ExportTypeEnum.EXCEL);
        excelExportService.export();
    }

}
