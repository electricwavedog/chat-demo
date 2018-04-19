package com.example.chat.common;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @author liuyiqian
 */
public class FileUtil {

    public static String saveFile(MultipartFile file, String path) throws IOException {
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        String fileName = Long.toString(System.currentTimeMillis()) + ".jpg";
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));
        out.write(file.getBytes());
        out.flush();
        out.close();
        return fileName;
    }

    public static void uploadFile(MultipartFile file, String filePath, String fileName) {
        try {
            File targetPath = new File(filePath);
            if (!targetPath.exists()) {
                targetPath.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath + File.separator + fileName));
            bos.write(file.getBytes());
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadFile(String filePath, String fileName, HttpServletResponse response) {
        try {
            File file = new File(filePath + fileName);
            if (!file.exists()) {
                return;
            }
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            OutputStream os = response.getOutputStream();
            byte[] buff = new byte[1024];
            int len;
            while ((len = bis.read(buff)) != -1) {
                os.write(buff, 0, len);
            }
            os.flush();
            os.close();
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadZip(List<String> filePath, String zipPath, String zipName, HttpServletResponse response) {
        try {
            File targetPath = new File(zipPath);
            if (!targetPath.exists()) {
                targetPath.mkdirs();
            }
            File zip = new File(zipPath + zipName);
            if (!zip.exists()) {
                zip.createNewFile();
            }
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
            for (String fp : filePath) {
                File file = new File(fp);
                zipFile(file, zos);
            }
            zos.close();

            downloadFile(zipPath, zipName, response);

            zip.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFile(File inputFile, ZipOutputStream zipoutputStream) {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {

                    FileInputStream fis = new FileInputStream(inputFile);
                    BufferedInputStream bis = new BufferedInputStream(fis);

                    ZipEntry ze = new ZipEntry(inputFile.getName());
                    zipoutputStream.putNextEntry(ze);

                    byte[] buff = new byte[1024];
                    int len;
                    while ((len = bis.read(buff)) != -1) {
                        zipoutputStream.write(buff, 0, len);
                    }
                    zipoutputStream.closeEntry();
                    bis.close();
                    fis.close();
                } else {
                    File[] files = inputFile.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        zipFile(files[i], zipoutputStream);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] convertToByte(String path) throws IOException {
        File file = new File(path);
        byte[] data = null;
        FileImageInputStream input = new FileImageInputStream(file);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int numBytesRead = 0;
        while ((numBytesRead = input.read(buf)) != -1) {
            output.write(buf, 0, numBytesRead);
        }
        data = output.toByteArray();
        output.close();
        input.close();
        return data;
    }
}
