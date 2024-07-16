package com.dullfan.common.controller.common;

import com.dullfan.common.config.DullConfig;
import com.dullfan.common.constant.Constants;
import com.dullfan.common.entity.vo.Result;
import com.dullfan.common.utils.StringUtils;
import com.dullfan.common.utils.file.FileUploadUtils;
import com.dullfan.common.utils.file.FileUtils;
import com.dullfan.framework.config.ServerConfig;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用请求处理
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Resource
    private ServerConfig serverConfig;

    private static final String FILE_DELIMETER = ",";

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response) {
        try {
            if (!FileUtils.checkAllowDownload(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = DullConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    public Result uploadFile(MultipartFile file) {
        try {
            // 上传文件路径
            String filePath = DullConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            Result ajax = Result.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    public Result uploadFiles(List<MultipartFile> files) {
        try {
            // 上传文件路径
            String filePath = DullConfig.getUploadPath();
            List<String> urls = new ArrayList<>();
            List<String> fileNames = new ArrayList<>();
            List<String> newFileNames = new ArrayList<>();
            List<String> originalFilenames = new ArrayList<>();
            for (MultipartFile file : files) {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = serverConfig.getUrl() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            Result ajax = Result.success();
            ajax.put("urls", org.apache.commons.lang3.StringUtils.join(urls, FILE_DELIMETER));
            ajax.put("fileNames", org.apache.commons.lang3.StringUtils.join(fileNames, FILE_DELIMETER));
            ajax.put("newFileNames", org.apache.commons.lang3.StringUtils.join(newFileNames, FILE_DELIMETER));
            ajax.put("originalFilenames", org.apache.commons.lang3.StringUtils.join(originalFilenames, FILE_DELIMETER));
            return ajax;
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, HttpServletResponse response) {
        try {
            if (!FileUtils.checkAllowDownload(resource)) {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = DullConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + org.apache.commons.lang3.StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = org.apache.commons.lang3.StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
    }


    @Operation(summary = "删除单个文件")
    @DeleteMapping("/deleteFile")
    public Result deleteFile(@RequestParam("fileUrl") String fileUrl) {
        String replace = DullConfig.getProfile().replace("/", "\\");
        String url = fileUrl.replace("/profile", "");
        String filePath = (replace + url).replace("/", "\\");
        File file = new File(filePath);
        try {
            if (file.exists()) {
                file.delete();
                return Result.success("删除成功");
            } else {
                return Result.error("文件不存在");
            }
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @Operation(summary = "删除多个文件")
    @DeleteMapping("/deleteFiles")
    public Result deleteFiles(@RequestParam("fileUrls") List<String> fileUrls) {
        String replace = DullConfig.getProfile().replace("/", "\\");
        for (String fileUrl : fileUrls) {
            String url = fileUrl.replace("/profile", "");
            String filePath = (replace + url).replace("/", "\\");
            File file = new File(filePath);
            try {
                if (file.exists()) {
                    file.delete();
                } else {
                    return Result.error("文件不存在");
                }
            } catch (Exception e) {
                return Result.error(e.getMessage());
            }
        }
        return Result.success("删除成功");
    }
}
