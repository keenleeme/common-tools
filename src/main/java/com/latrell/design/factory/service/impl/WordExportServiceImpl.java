package com.latrell.design.factory.service.impl;

import com.latrell.design.factory.service.ExportService;

/**
 * Word文件导出
 *
 * @author liz
 * @date 2023/3/11-15:42
 */
public class WordExportServiceImpl implements ExportService {

    @Override
    public void export() {
        System.out.println("导出word文件成功！");
    }
}
