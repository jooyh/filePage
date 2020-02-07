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
</head>
<body>
	<div id="container">
	    <div class="content">

	        <div class="login-content">
	            <div class="login-title">
	                <h2 class="tit">LOGIN</h2>
	            </div>

	            <div class="login-form">
	                <div class="login-area">
	                    <div class="input-box">
	                        <input type="text" name="userNm" placeholder="ID"/>
	                    </div>
	                    <div class="input-box">
	                        <input type="password" name="userPw" placeholder="PASS"/>
	                    </div>
	                    <div class="join">
	                        <a href="#">회원가입먼저하고</a>
	                    </div>
	                    <div class="btn-area">
	                        <button type="submit" class="btn-basic" onclick="login()">로그인해야지</button>
	                    </div>
	                </div>
	            </div>
	        </div>

	        <!--  popup  -->
	        <div class="pop-wrapper pop-join" style="display:none">
	            <div class="pop-wrap">
	                <div class="pop-header">
	                    <h2 class="tit">JOOOOOOOOOIN(깔쌈하쥬?)</h2>
	                 </div>
	                <div class="pop-content">
	                    <div class="join-form">
	                        <div class="login-area">
	                            <div class="input-box">
	                                <input type="text" name="userNm" dupl="Y" placeholder="ID" onchange="initDupl(this)"/>
	                                <button type="button" class="btn-basic type-input" onclick="duplCheck(this)">중복확인</button>
	                            </div>
	                            <div class="input-box">
	                                <input type="password" name="userPw" placeholder="PASS"/>
	                            </div>
<!-- 	                            <div class="input-box">  -->
<!-- 	                                <input type="password" placeholder="PASS"/> -->
<!-- 	                            </div> -->
<!-- 	                            <div class="input-box">  -->
<!-- 	                                <input type="text" placeholder=""/> -->
<!-- 	                            </div> -->
<!-- 	                            <div class="input-box">  -->
<!-- 	                                <input type="text" placeholder=""/> -->
<!-- 	                            </div> -->
	                            <div class="btn-area">
	                                <button type="button" class="btn-basic close">취소</button>
	                                <button type="submit" class="btn-basic" onclick="join()">확인</button>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	        <div class="pop-dim" style="display:none"></div>
	    </div>
	</div>
</body>

<script>
	$(function(){
	    $(".close").click(function(){
	        $(this).parents(".pop-wrapper").fadeOut();
	        $(".pop-dim").fadeOut();
	    })
	    $(".join a").click(function(){
	        $(".pop-join").fadeIn();
	        $(".pop-dim").fadeIn();
	    })
	})
	function login(){
		var loginInfo = {};

		$(".login-form").find("input").each(function(i,item){
			loginInfo[$(item).attr("name")] = $(item).val();
		});

		transaction({
			url : "/login.do",
			param : loginInfo,
			success : function(result){
				console.log(result.userNm);
				if(!result.userNm){
					alert("로그인 정보를 확인 해 주세요");
				}else{
					if(result.userLvl == "A"){
						location.href = "/filePage_adm";
					}else{
						location.href = "/filePage";
					}
				}
			}
		});
	}

	function duplCheck(el){
		var idEl = $(el).prev();
		if(!$(idEl).val()) return;

		transaction({
			url : "/login.do",
			param : {userNm : $(idEl).val()},
			success : function(result){
				if(result.userNm){
					alert("사용 불가능한 아이디입니다.")
					idEl.attr("dupl","Y");
				}else{
					alert("사용 가능한 아이디입니다.")
					idEl.attr("dupl","N")
				}
			}
		});
	}

	function join(){
		console.log( $(".join-form").find("[name='userNm']"));
		var duplYn = $(".join-form").find("[name='userNm']").attr("dupl");
		if(duplYn == "Y"){
			alert("아이디 중복여부를 확인 해 주세요.");
			return;
		}

		var joinInfo = {};
		$(".join-form").find("input").each(function(i,item){
			joinInfo[$(item).attr("name")] = $(item).val();
		});

		transaction({
			url : "/join.do",
			param : joinInfo,
			success : function(result){
				console.log("result",result);
				if(result.rsltCnt == 1){
					alert("정상 가입되었습니다.");
					$(".join-form").find("input").each(function(i,item){
						$(item).val("");
					});
					$(".close").click();
				}
			}
		});
	}

	function initDupl(el){
		$(el).attr("dupl","Y");
	}
</script>
</html>