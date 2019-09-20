package com.matrix.updownload.service.impl;

import com.matrix.updownload.configuration.FileConfiguration;
import com.matrix.updownload.domain.FileInfo;
import com.matrix.updownload.exception.StorageFileNotFoundException;
import com.matrix.updownload.repository.FileRepository;
import com.matrix.updownload.service.UpdownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName UpdownloadServiceImpl
 * @Author wby
 * @Date 2019/4/22 14:24
 * @Version 1.0
 * @Description TODO
 **/
@Service
public class UpdownloadServiceImpl implements UpdownloadService {
    private FileRepository fileRepository;
    private Path rootLocation;

    @Autowired
    public UpdownloadServiceImpl(FileConfiguration fileConfiguration, FileRepository fileRepository) {
        this.rootLocation = Paths.get(fileConfiguration.getFilePath());
        this.fileRepository = fileRepository;
    }

    /**
     * 下载文件
     * rootLocation.resolve(url) 将相对路径解析为绝对路径
     * Content-Disposition的作用：https://blog.csdn.net/u010267906/article/details/74989791
     */

    @Override
    public ResponseEntity<Resource> loadFile(String url) {
        try {
            Path path = rootLocation.resolve(url);
            Resource resource = new UrlResource(path.toUri());
            //将文件名解码是为了确保文件名正确
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            String[] strArr = fileName.split("\\.");
            String fileNamePrefix = strArr[0];
            String fileNameSuffix = strArr[1];
            String finalFileName = null;
            try {
                finalFileName = URLEncoder.encode(fileNamePrefix, "UTF-8") + "." + fileNameSuffix;
            } catch (UnsupportedEncodingException e) {
                //log.info("文件名字转换异常");
                e.printStackTrace();
            }
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + finalFileName + "\"").body(resource);
            } else {
                throw new StorageFileNotFoundException("Could not read file: " + url);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 多文件上传
     * @param uploadFiles
     */
    @Override
    public void saveFile(MultipartFile[] uploadFiles) {
        List<MultipartFile> files = Arrays.asList(uploadFiles);
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            try {
                byte[] bytes = file.getBytes();
                String monthDate = LocalDate.now().getYear() + "-" + LocalDate.now().getMonthValue();
                Path path = rootLocation.resolve(monthDate + "/");
                //判断路径是否存在不存在则新建
                if (Files.notExists(path)) {
                    Files.createDirectories(path);
                }
                //将上传文件写入到以上路径中
                Files.write(path.resolve(file.getOriginalFilename()), bytes);
                //保存文件信息到数据库
                saveFileInfo(monthDate, file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //保存文件信息
    private void saveFileInfo(String monthDate, String fileName) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setUrl(monthDate + "/" + fileName);
        fileInfo.setName(fileName);
        fileRepository.save(fileInfo);
    }

    @Override
    public List<FileInfo> findList() {
        return fileRepository.findAllData();
    }
}
