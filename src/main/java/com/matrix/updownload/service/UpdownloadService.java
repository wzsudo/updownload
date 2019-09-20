package com.matrix.updownload.service;

import com.matrix.updownload.domain.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName UpdownloadService
 * @Author wby
 * @Date 2019/4/22 14:24
 * @Version 1.0
 * @Description TODO
 **/
public interface UpdownloadService {
    ResponseEntity<Resource> loadFile(String url);

    void saveFile(MultipartFile[] uploadFiles);

    List<FileInfo> findList();
}
