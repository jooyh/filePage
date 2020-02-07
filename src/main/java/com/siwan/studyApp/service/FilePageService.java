package com.siwan.studyApp.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwan.studyApp.utils.FileUtil;

@Service
public class FilePageService {
	protected static final Logger logger = LoggerFactory.getLogger(FilePageService.class);
	private static final String NAME_SPACE = "mappers.filePageService.";

	@Autowired
	FileUtil fileUtil;

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 경로조회
	 * @param Params
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public Map getDir(Map params) throws IOException  {
		String dir = (String) params.get("dir");
		String isFirst = (String) params.get("isFirst");
		return fileUtil.getDrictory(dir);
	}

	/**
	 * 파일 삭제요청
	 * @param param
	 * @return
	 */
	public Map insertDelReq(Map param) {
		Map rsltMap = sqlSession.selectOne(NAME_SPACE+"selectDelRequest", param);
		long cnt = (Long) rsltMap.get("cnt");
		if(cnt == 0) {
			int rsltCnt = sqlSession.insert(NAME_SPACE+"insertDelRequest", param);
			rsltMap.put("rsltCnt", rsltCnt);
		}else {
			rsltMap.put("rsltCnt", 0);
		}
		return rsltMap;
	}

	/**
	 * 파일 삭제요청 목록조회
	 * @param param
	 * @return
	 */
	public Map selectDelReqList(Map param) {
		List<Map> rsltList = sqlSession.selectList(NAME_SPACE+"selectDelRequestList", param);
		Map rsltMap = new HashMap();
		rsltMap.put("rsltList",rsltList);
		return rsltMap;
	}

	/**
	 * 파일 삭제
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map fileDelete(Map param) throws Exception {
		Map rsltMap = new HashMap();
		Map rtnMap = fileUtil.fileDelete(param);
		if("SUCC".equals(rtnMap.get("CODE"))) {
			sqlSession.update("updateDelReq",rtnMap);
		}else {
			throw new Exception();
		}
		return rsltMap;
	}

}
