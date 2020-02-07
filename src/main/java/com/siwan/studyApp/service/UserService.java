package com.siwan.studyApp.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siwan.studyApp.controller.BaseController;

@Service
public class UserService {

	protected static final Logger logger = LoggerFactory.getLogger(UserService.class);
	private static final String NAME_SPACE = "mappers.userService.";

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 회원가입
	 * @param Params
	 * @return
	 * @throws Exception
	 */
	public Map insertUser(Map params)  {
		int rsltCnt = sqlSession.insert(NAME_SPACE+"insertUser",params);
		Map rtnMap = new HashMap();
		rtnMap.put("rsltCnt",rsltCnt);
		return rtnMap;
	}

	/**
	 * 중복체크
	 * @param Params
	 * @return
	 * @throws Exception
	 */
	public Map isDuplicate(Map params)  {
		return (Map) sqlSession.selectOne(NAME_SPACE+"selectUser",params);
	}

	/**
	 * 로그인
	 * @param Params
	 * @return
	 * @throws Exception
	 */
	public Map selectUser(Map params)  {
		return (Map) sqlSession.selectOne(NAME_SPACE+"login",params);
	}

}
