package com.siwan.studyApp.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.siwan.studyApp.service.FilePageService;
import com.siwan.studyApp.utils.FileUtil;
import com.siwan.studyApp.utils.FtpUtil;

@Controller
public class FilePageController extends BaseController{

	@Autowired
	FilePageService filePageService;

	@Autowired
	FileUtil fileUtil;
	
	@Autowired
	FtpUtil ftpUtil;

	@RequestMapping("/filePage")
	public String locateFilePage(HttpServletRequest request) {
		return "filePage";
	}
	@RequestMapping("/filePage_adm")
	public String locateAdminFilePage(HttpServletRequest request) {
		return "adminfilePage";
	}

//	@RequestMapping("/getDir.do")
//	@ResponseBody
//	public Map getDir(HttpServletRequest request) throws IOException {
//		Map params = getParams(request);
//		String dir = (String) params.get("dir");
//		String isFirst = (String) params.get("isFirst");
//		return fileUtil.getDrictory(dir);
//	}
	
	@RequestMapping("/getDir.do")
	@ResponseBody
	public Map getDir(HttpServletRequest request) throws IOException {
		Map params = getParams(request);
		String dir = (String) params.get("dir");
		return ftpUtil.getFileInFtp(dir);
	}

	@RequestMapping("/uploadFile.do")
	@ResponseBody
	public String uploadFile(MultipartHttpServletRequest  mpRequest) throws IOException {
		List<MultipartFile> fileList = mpRequest.getFiles("file");
		String path = mpRequest.getParameter("path");
		ftpUtil.uploadInFtp(fileList,path);
		return "SUCC";
	}
	
	/*@RequestMapping("/uploadFile.do")
	@ResponseBody
	public String uploadFile(MultipartHttpServletRequest  mpRequest) throws IOException {
		List<MultipartFile> fileList = mpRequest.getFiles("file");
		String path = mpRequest.getParameter("path");
		fileUtil.fileUpload(fileList,path);
		return "SUCC";
	}*/
	
	@RequestMapping("/delReauest.do")
	@ResponseBody
	public Map delReauest(HttpServletRequest request) throws IOException {
		Map params = getParams(request);
		return filePageService.insertDelReq(params);
	}

	@RequestMapping("/fileDownload.do")
	@ResponseBody
	public void download(@RequestParam("path")String path) {
//		logger.debug(path);
		ftpUtil.downloadInFtp(path);
	}
//	@RequestMapping("/fileDownload.do")
//	public ModelAndView download(@RequestParam("path")String path) {
//		logger.info("@@@@@@@@@@@@@@@FileDown(1) START@@@@@@@@@@@@@@@");
//		logger.info(path);
//		File file = new File(path);
//		logger.info("@@@@@@@@@@@@@@@FileDown(1) END@@@@@@@@@@@@@@@");
//		return new ModelAndView("download", "downloadFile", file);
//	}

	@RequestMapping("/getDelReqList.do")
	@ResponseBody
	public Map getDelReqList(HttpServletRequest request){
		Map params = getParams(request);
		return filePageService.selectDelReqList(params);
	}
	@RequestMapping("/deleteFile.do")
	@ResponseBody
	public Map deleteFile(HttpServletRequest request) throws Exception{
		Map params = getParams(request);
		return filePageService.fileDelete(params);
	}
}
