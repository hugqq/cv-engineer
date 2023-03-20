package com.hugqq.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.function.Consumer;

import com.hugqq.entity.ChunkInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件工具类
 */
@Slf4j
public class FileInfoUtils {
    public static String getFileType(String fileName) {
        String fileType = "";
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            fileType = fileName.substring(dotIndex + 1);
        }
        return fileType;
    }

    public static String generatePath(String uploadFolder, ChunkInfo chunk) {
        StringBuilder sb = new StringBuilder();
        sb.append(uploadFolder).append(File.separator).append(chunk.getIdentifier());
        //判断uploadFolder/identifier 路径是否存在，不存在则创建
        Path dir = Paths.get(sb.toString());
        if (!Files.isWritable(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return sb.append(File.separator)
                .append(chunk.getFilename())
                .append("-")
                .append(chunk.getChunkNumber()).toString();
    }

    /**
     * 文件合并
     */
    public static String merge(String file, String folder, String filename) {
        try {
            //先判断文件是否存在
            if (fileExists(file)) {
                //文件已存在
                return "500";
            } else {
                //不存在的话，进行合并
                Files.createFile(Paths.get(file));
                Files.list(Paths.get(folder))
                        .filter(path -> !path.getFileName().toString().equals(filename))
                        .sorted(comparator())
                        .forEach(mergeFile(file));
                return "200";
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return "500";
        }
    }

    private static Consumer<Path> mergeFile(String file) {
        return path -> {
            try {
                //以追加的形式写入文件
                Files.write(Paths.get(file), Files.readAllBytes(path), StandardOpenOption.APPEND);
                //合并后删除该块
                Files.delete(path);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        };
    }

    private static Comparator<Path> comparator() {
        return (o1, o2) -> {
            String p1 = o1.getFileName().toString();
            String p2 = o2.getFileName().toString();
            return Integer.valueOf(p2.substring(p2.lastIndexOf("-"))).compareTo(Integer.valueOf(p1.substring(p1.lastIndexOf("-"))));
        };
    }

    /**
     * 根据文件的全路径名判断文件是否存在
     */
    public static boolean fileExists(String file) {
        boolean fileExists = false;
        Path path = Paths.get(file);
        fileExists = Files.exists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS});
        return fileExists;
    }
}
