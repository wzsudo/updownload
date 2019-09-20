package com.matrix.updownload.repository;

import com.matrix.updownload.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName FileRepository
 * @Author wby
 * @Date 2019/4/22 15:29
 * @Version 1.0
 * @Description TODO
 **/
public interface FileRepository extends JpaRepository<FileInfo, Integer> {
    @Query("select o from FileInfo o where 1=1")
    List<FileInfo> findAllData();
}
