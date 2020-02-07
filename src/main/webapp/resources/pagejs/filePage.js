$(function(){
	var headerHeight = $("header").outerHeight();
    $(".directory-wrapper").css("padding-top",headerHeight);

    /* tooltip 우클릭 이벤트 */
    $(document).on("mousedown", function(e) {
        // alert(e.which); // 1:좌클릭, 2:휠클릭, 3:우클릭   // 신기방기
        var windowWidth = $(window).width();
        var windowHeight = $(window).height();
        var tooltipWidth = $(".tooltip-wrapper").width();
        var tooltipHeight = $(".tooltip-wrapper").height();
        if (e.which == 3) {
            if ($(e.target).hasClass("file-item") ) {
                x = event.pageX;
                y = event.pageY;
                $(".tooltip-wrapper").css({
                    display : "block",
                    top : y,
                    left : x
                })
                if(tooltipWidth + x > windowWidth){
                    $(".tooltip-wrapper").css( "left" , x - tooltipWidth )
                }
                if(tooltipHeight + y > windowHeight){
                    $(".tooltip-wrapper").css( "top" , y - tooltipHeight )
                }
                setFileInfo(e.target);
            }
        } else {
        	if($(e.target).hasClass("tooltip-wrapper") || $(e.target).parents(".tooltip-wrapper").length){
        		return;
        	}
            $(".tooltip-wrapper").hide();
        }
    });
    /* 기본 우클릭 제거 */
    $(document).on("contextmenu",function(e){
        // console.log("c"+e);
        return false;
    });

	getDir(null,null,false);
});

function getDir(trgDirCls,dir,isFirst){
		transaction({
			url : "/getDir.do",
			param : {dir:dir},
			success : function(result){
				console.log(result);
				setPathUI(result.dir)
				mkDir(trgDirCls,result.dirList);
				if(!isFirst) mkFiles(result.fileList);
				$(".total .num").text(result.fileList.length);
			}
		});
	}

	function mkDir(trgDirCls , dirList){
		console.log("TEST",trgDirCls);
		if(!trgDirCls) trgDirCls = ".directory-list";
		var html = ``;
		for(var i in dirList){
			html += `
				${trgDirCls == ".directory-list" ? '' : '<ul>' }
				<li>
	                <span class="deps"
	                	data-dir-info='${JSON.stringify(dirList[i])}' onclick='dirClick(this)'
	                >
	                	${dirList[i].name}
	                </span>
	            </li>
				${trgDirCls == ".directory-list" ? '' : '</ul>' }
			`;
		}
//		if(trgDirCls == ".directory-list"){
//			$(trgDirCls).find("span").nextAll().remove();
//		}
		$(trgDirCls).find("span").nextAll().remove();
		$(trgDirCls).append(html);
	}

	function mkFiles(fileList){
		var html = ``;

		for(var i in fileList){
			html += `
				<div data-fileInfo = '${JSON.stringify(fileList[i])}'
				 class="file-item ${getTypeClass(fileList[i].type,fileList[i].mimeType)}">
                    <span class="item-name">${fileList[i].name}</span>
                </div>
			`;
		}
		$(".file-item-box").empty().append(html);
	}

	function getTypeClass(type,mimeType){
		if(type == "dir"){
			return "type-folder"
		}else{
			if(mimeType){
				if(mimeType.search("image") != -1){
					return "type-img";
				}else if(mimeType.search("xml") != -1
						|| mimeType.search("pdf") != -1
						|| mimeType.search("txt") != -1
						|| mimeType.search("text") != -1){
					return "type-document"
				}else{
					return "type-default"
				}
			}else{
				return "type-default"
			}
		}
	}

	function dirClick(el){
		$(".active-dir").removeClass("active-dir");
		$(el).addClass("active-dir");

		var opendFlag = $(el).attr("opendFlag");
		var isOpen = $(el).hasClass("open");
		var dirInfo = JSON.parse($(el).attr("data-dir-info"));

		if(isOpen){ // 열려있는경우 -> 닫기
			$(el).removeClass("open");
			var dirList = dirInfo.path.split("\\");
			dirList.pop();
			var dir = "";
			for(var i in dirList){
				dir += dirList[i]+"\\";
			}
			setPathUI(dir);
			getDir($(el).parent("li").parent("li"),dir);
			$(el).nextAll().remove();

		}else{ // 닫혀있는경우 -> 열기
			$(el).addClass("open");
			getDir($(el).parent("li"),dirInfo.path);
//			if(opendFlag){ //열렸던 기록이 있는경우 -> Show
//				$(el).nextAll().show();
//			}else{ // 열려있던 기록이 없는경우 -> Data 조회
//				$(el).attr("opendFlag","Y");
//			}
		}
	}

	function setPathUI(dirPath){
		var dirList = dirPath.split("\\");
		var html = ``;
		for(var i in dirList){
			html += `<li> ${dirList[i]} </li>`;
		}
		$(".route-list").empty().append(html)
		$(".route-list").attr("data-path",dirPath);
	}

	function uploadFile(fileEl){
	    var uploadPath = $(".route-list").attr("data-path")+"\\";
		fileTransaction({
			 fileEl : fileEl
			,path : uploadPath
			,url : "/uploadFile.do"
			,success : function(result){
				console.log(result)
				getDir($(".active-dir").parent("li"),uploadPath);
				fileEl.value = "";
			}
		});
	}

	function setFileInfo(el){
		var fileInfo = JSON.parse($(el).attr("data-fileInfo"));
		$(".tooltip-wrap").attr("fileInfo",JSON.stringify(fileInfo));
		$(".tooltip-wrap #pop-file-name").text(fileInfo.name);
		$(".tooltip-wrap #pop-file-type").text(fileInfo.path);
		$(".tooltip-wrap #pop-file-size").text(fileInfo.size);
	}

	function delReauest(el){
		var jsonStr = $(el).parents(".tooltip-wrap").attr("fileInfo");
		var fileInfo = JSON.parse(jsonStr);

		transaction({
			url : "/delReauest.do",
			param : {fileInfo:fileInfo},
			success : function(result){
				console.log("REQ",result);
				if(result.rsltCnt == 0){
					alert("이미 삭제 요청이 되어있는 파일입니다.");
				}else{
					alert("정상적으로 삭제 요청 되었습니다.");
				}
				$(".tooltip-wrapper").hide();
			}
		});
	}

	function downloadFile(el){
		var jsonStr = $(el).parents(".tooltip-wrap").attr("fileInfo");
		var fileInfo = JSON.parse(jsonStr);
		location.href = "/fileDownload.do?path="+encodeURI(fileInfo.path);
//		transaction({
//			url : "/fileDownload.do",
//			param : {fileInfo:fileInfo},
//			success : function(result){
//				console.log("fileDownload",result);
//			}
//		});
	}