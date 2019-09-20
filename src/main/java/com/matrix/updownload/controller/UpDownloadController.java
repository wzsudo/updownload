package com.matrix.updownload.controller;

import com.alibaba.fastjson.JSONArray;
import com.matrix.updownload.domain.FileInfo;
import com.matrix.updownload.service.UpdownloadService;
import com.matrix.updownload.utils.ExcelUtil;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * @ClassName UpDownload
 * @Author wby
 * @Date 2019/4/19 17:41
 * @Version 1.0
 * @Description TODO
 **/
@RestController
@RequestMapping("/files")
public class UpDownloadController {
    @Autowired
    private UpdownloadService service;

    @RequestMapping("/download")
    private void download(HttpServletRequest req, HttpServletResponse response) {
        JSONArray header = new JSONArray();
        header.add("id");
        header.add("device_id");
        header.add("start_time");
        HSSFWorkbook workBook = ExcelUtil.getExcelFile(header, "aaabbbccc");

        response.setHeader("content-type", "application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=order.xls");
        response.setHeader("Pargam", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        try {
            OutputStream out = response.getOutputStream();
            workBook.write(out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/file")
    public ResponseEntity<Resource> loadFile(String url) {
        return service.loadFile(url);
    }

    @PostMapping("/multiUpload")
    public void saveFile(@RequestParam("file") MultipartFile[] uploadFiles) {
        service.saveFile(uploadFiles);
    }

    @GetMapping("/findList")
    public ResponseEntity<List<FileInfo>> findList() {
        return ResponseEntity.ok(service.findList());
    }
}
