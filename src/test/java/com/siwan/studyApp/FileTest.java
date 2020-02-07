package com.siwan.studyApp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class FileTest {

	String baseDir = "C:\\Users\\SIWAN\\Desktop\\siwan_hompage";
	
	@Test
	public void getDrictory() throws IOException {
		// 하위 디렉토리
        for (File info : new File(baseDir).listFiles()) {
        	Path source = Paths.get(info.getAbsolutePath()+info.getName());
        	String mimeType = Files.probeContentType(source);
        	Map fileMap = new HashMap();
        	fileMap.put("mimeType",mimeType);
        	fileMap.put("path",info.getAbsolutePath());
        	fileMap.put("name",info.getName());
        	fileMap.put("size",info.length()+"byte");
        	System.out.println(fileMap);
        }
	}
	
	public void fileTest() {
        // 하위 디렉토리
        for (File info : new File(baseDir).listFiles()) {
            if (info.isDirectory()) {
                System.out.println(info.getName());
            }
            if (info.isFile()) {
                System.out.println(info.getName());
            }
        }
        // 디렉토리 전체 용량
//        System.out.println("전체 용량 : " +  FileUtils.sizeOfDirectory(new File(isDir)) + "Byte");
        
        // 하위의 모든 파일
//        for (File info : FileUtils.listFiles(new File(isDir), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
//            System.out.println(info.getName());
//        }
        
        // 하위의 모든 디렉토리
//        for (File info : FileUtils.listFilesAndDirs(new File(isDir), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
//            if(info.isDirectory()) {
//                System.out.println(info.getName());
//            }
//        }
	}
}
