package com.sky.controller.admin;

import com.aliyun.oss.model.MultipartUpload;
import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用接口
 */
@Slf4j
@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 上传图片到阿里云
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("上传图片")
    public Result<String> upload (MultipartFile file) {
        log.info("文件上传:{}", file);
        try {
            //获取原始文件名
            String originalFilename = file.getOriginalFilename();
            //获取原始文件名后缀
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名称
            String newFileName = UUID.randomUUID().toString() + suffix;
            //返回文件请求路径
            return Result.success(aliOssUtil.upload(file.getBytes(), newFileName));
        } catch (IOException e) {
            log.error("文件上传失败:{}", e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
