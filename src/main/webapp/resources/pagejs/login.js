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
