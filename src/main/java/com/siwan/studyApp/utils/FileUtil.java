package com.siwan.studyApp.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class FileUtil{

	private static final String BASE_DIR = "C:\\Users\\SIWAN\\Desktop";

	public Map getDrictory(String dir) throws IOException {
		List fileList = new ArrayList();
		List dirList = new ArrayList();
		Map rsltMap = new HashMap();
		if(dir == null || "".equals(dir)) {
			dir = BASE_DIR;
		}
		// 하위 디렉토리
        for (File info : new File(dir).listFiles()) {
        	Path source = Paths.get(info.getAbsolutePath()+info.getName());
        	String mimeType = Files.probeContentType(source);
        	Map fileMap = new HashMap();
        	fileMap.put("mimeType",mimeType);
        	fileMap.put("path",info.getAbsolutePath());
        	fileMap.put("name",info.getName());
        	fileMap.put("size",info.length()+"byte");

        	if (info.isDirectory()) {
        		fileMap.put("type","dir");
        		dirList.add(fileMap);
        	}else {
        		fileMap.put("type","file");
        		fileList.add(fileMap);
        	}
        }

        rsltMap.put("fileList",fileList);
        rsltMap.put("dirList",dirList);
        rsltMap.put("dir",dir);
        return rsltMap;
	}

	public void fileUpload(List<MultipartFile> fileList , String path ) throws IllegalStateException, IOException {
		System.out.println("TEST::"+path);
		for(MultipartFile mf : fileList) {
			File file = new File(path + mf.getOriginalFilename());
		    if (file.exists()){ //file중복체크
		    	file = new File(path + System.currentTimeMillis() + "_" + mf.getOriginalFilename());
		    }
			mf.transferTo(file);
		}
	}

	public Map fileDelete(Map params) throws Exception {
		Map rsltMap = new HashMap();
		List<Map> reqInfoList =  (List) params.get("reqInfoList");
		List<Integer> deletedIds = new ArrayList();
//		List<Map> unDeletedInfo = new ArrayList();
		for(Map reqInfo : reqInfoList) {
			String path = (String) reqInfo.get("reqFilePath");
			File file = new File(path);
			if( file.exists() ){ //파일존재여부확인
				if(file.delete()){
					System.out.println("파일삭제 성공");
					rsltMap.put("CODE","SUCC");
				}else{
//					unDeletedInfo.add(reqInfo);
					throw new Exception();
//					break;
				}
			}else{
//				unDeletedInfo.add(reqInfo);
				throw new Exception();
//				break;
			}
			deletedIds.add(Integer.parseInt(reqInfo.get("reqId").toString()));
		}
		rsltMap.put("deletedIds",deletedIds);
		return rsltMap;
	}
}
