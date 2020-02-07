<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"> -->
    <title>로그인</title>
	<link rel="stylesheet" type="text/css" href="/resources/style.css">
	<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript" src="/resources/common.js"></script>
	<script type="text/javascript" src="/resources/pagejs/filePage.js"></script>
</head>
<body>
<header>
    <ul class="route-list"></ul>
</header>

    <div class="content">
        <div class="directory-wrapper">
            <div class="directory-wrap">
                <ul class="directory-list">

                </ul>
            </div>
            <div class="file-item-box">
            </div>
            <div class="file-input-box">
	            <input class="file-upload" type="file" multiple="multiple" onchange="uploadFile(this)">
            </div>
        </div>
    </div>

	<div class="tooltip-wrapper" style="display:none;">
        <div class="tooltip-wrap">
            <div class="name" id="pop-file-name"></div>
            <div class="name" id="pop-file-type"></div>
            <div class="name" id="pop-file-size"></div>
            <ul class="list">
                <li onclick="delReauest(this)">삭제요청</li>
                <li onclick="downloadFile(this)">다운로드</li>
            </ul>
        </div>
    </div>

    <footer>
        <span class="total"><strong class="num">20</strong> 개 항목</span>
    </footer>
</body>
</html>