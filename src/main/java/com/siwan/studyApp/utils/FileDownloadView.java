package com.siwan.studyApp.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class FileDownloadView extends AbstractView{
	public void Download(){
		setContentType("application/download; utf-8");
	} //���� �ٿ�ε�

	@Override
	protected void renderMergedOutputModel(Map paramMap, HttpServletRequest request
			, HttpServletResponse response) throws Exception {
		File file = (File)paramMap.get("downloadFile");
        System.out.println("DownloadView --> file.getPath() : " + file.getPath());
        System.out.println("DownloadView --> file.getName() : " + file.getName());

        response.setContentType(getContentType());
        response.setContentLength((int)file.length());

        String fileName = null;

        //if(ie){
        //������ ������ ���� utf-8����
        if(request.getHeader("User-Agent").indexOf("MSIE") > -1){
            fileName = URLEncoder.encode(file.getName(), "utf-8");
        } else {
            fileName = new String(file.getName().getBytes("utf-8"));
        }// end if;

        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        OutputStream out = response.getOutputStream();
        FileInputStream fis = null;

        //���� ī�� �� ������
        try {
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);
        } catch(Exception e){
            e.printStackTrace();
        }finally{
            if(fis != null){
                try{
                    fis.close();
                }catch(Exception e){}
            }
        }// try end;
        out.flush();
	}
}
