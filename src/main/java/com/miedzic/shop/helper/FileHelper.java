package com.miedzic.shop.helper;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileHelper {
    public void fileCopy(InputStream is, Path path) throws IOException {
        Files.copy(is,path);
    }
    public void fileDelete(Path path) throws IOException {
        Files.delete(path);
    }
}

