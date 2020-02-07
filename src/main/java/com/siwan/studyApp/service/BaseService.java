package com.siwan.studyApp.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {

	@Autowired
	private SqlSession sqlSession;
	private static final Logger logger = LoggerFactory.getLogger(BaseService.class);
	
	protected Map selectList(String queryId , Map params) {
//		Map rtnMap = new HashMap();
//		rtnMap.put("rslt",sqlSession.selectList(queryId,params));
//		return rtnMap; 
		return (Map) sqlSession.selectList(queryId,params);
	}
	
	protected Map selectList(String queryId) {
//		Map rtnMap = new HashMap();
//		rtnMap.put("rslt",sqlSession.selectList(queryId));
//		return rtnMap; 
		return (Map) sqlSession.selectList(queryId);
	}
	
	protected Map selectOne(String queryId , Map params) {
//		Map rtnMap = new HashMap();
//		rtnMap.put("rslt",sqlSession.selectOne(queryId,params));
//		return rtnMap; 
		return (Map) sqlSession.selectOne(queryId,params);
	}
	
	protected Map selectOne(String queryId) {
//		Map rtnMap = new HashMap();
//		rtnMap.put("rslt",sqlSession.selectOne(queryId));
//		return rtnMap;
		return sqlSession.selectOne(queryId);
	}
	
//	protected Map insert(String queryId , Map params) {
////		Map rtnMap = new HashMap();
////		rtnMap.put("rslt",sqlSession.insert(queryId,params));
////		return rtnMap;
//	}
//	
//	protected Map update(String queryId , Map params) {
//		Map rtnMap = new HashMap();
//		rtnMap.put("rslt",sqlSession.update(queryId,params));
//		return rtnMap;
//	}
//	
//	protected Map delete(String queryId , Map params) {
//		Map rtnMap = new HashMap();
//		rtnMap.put("rslt",sqlSession.delete(queryId,params));
//		return rtnMap;
//	}
}
