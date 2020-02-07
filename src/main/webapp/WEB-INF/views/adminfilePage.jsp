<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"> -->
    <title>관리자</title>
    <link rel="stylesheet" type="text/css" href="/resources/style.css">
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript" src="/resources/common.js"></script>
	<script type="text/javascript" src="/resources/pagejs/adminFilePage.js"></script>
</head>
<body>

<div id="container">

    <div class="content">

        <div class="manager-wrapper">
            <div class="manager-wrap">
                <div class="manager-title">
                    <h2 class="tit">MANAGER?</h2>
                    <div class="btn-area w-type">
                        <button type="button" class="btn-basic" onclick="deleteFile()">삭제</button>
                    </div>
                </div>
                <div class="check-list-box">
                	<ul class="check-list">
                		<li class="check-item title-type">
                            <div class="check-input-box">
                                <input type="checkbox" id="allcheck"/>
                                <label for="allcheck">
                                    <span class="sub-txt name">파일명</span>
                                    <span class="sub-txt volume">용량</span>
                                    <span class="sub-txt date">시간</span>
                                </label>
                            </div>
                        </li>
                	</ul>
                    <ul class="check-list" id="delReqList"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>