package com.breaker.ssi.sys.controller;

import com.breaker.ssi.utils.result.Ret;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @description:
 * @author: Breaker93
 * @createTime: 2020/11/18 17:53
 */
@RestController
@RequestMapping("/sys/file")
public class FileController {

    @Value("${file.upload-url}")
    private String uploadUrl;

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    public Ret upload(@RequestParam MultipartFile file) {
        if(file.isEmpty()) {
            return Ret.error().setMsg("文件为空");
        }

        // 文件名
        String fileName = file.getOriginalFilename();

        // 后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));

        // 新文件名
        fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffixName;

        String dateFormat = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        String fileUrl = dateFormat + File.separator + fileName;
        File dest = new File(uploadUrl + File.separator + fileUrl);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            return Ret.ok().setData(fileUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return Ret.error().setData("文件写磁盘失败");
        }

    }

}
