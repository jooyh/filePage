$(function(){
	getDelReqList();

	$("#allcheck").click(function(e){
		console.log("Test",$(e.target).is(":checked"));
		if($(e.target).is(":checked")){
			$("input[type='checkbox']").prop("checked",true);
		}else{
			$("input[type='checkbox']").prop("checked",false);
		}
	})
});

function getDelReqList(){
	transaction({
		url : "/getDelReqList.do",
		param : {},
		success : function(result){
			console.log(result);
			mkRow(result.rsltList);
		}
	});
}

function mkRow(data){
	var html = ``;
	for(var i in  data){
		html +=
			`<li class="check-item">
		        <div class="check-input-box">
		            <input type="checkbox" id="check${i}" reqInfo ='${JSON.stringify(data[i])}'/>
		            <label for="check${i}">
		                <span class="sub-txt name ${getTypeClass(data[i].reqFileType)}">
		                    ${data[i].reqFileName}
		                </span>
		                <span class="sub-txt volume">${data[i].reqFileSize}</span>
		                <span class="sub-txt date">${data[i].regDtm}</span>
		            </label>
		        </div>
		    </li>
		    `
	}
	if(html.length == 0){
		html = `
			<li class="check-item">
                <div class="check-input-box" style="text-align: center;">
					요청된 파일이 없습니다.
                </div>
            </li>
		`;
	}
	$("#delReqList").empty().append(html);
}

function getTypeClass(mimeType){
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

function deleteFile(){
	var reqInfoList = [];
	$("input[type='checkbox']:checked").each(function(i,item){
		var reqInfo = $(item).attr("reqInfo");
		if(reqInfo) reqInfoList.push(reqInfo);
	});
	console.log(reqInfoList);
	if(!reqInfoList.length) return;
	if(confirm(reqInfoList.length+"개의 파일을 삭제 하시겠습니까?")){
		transaction({
			url : "/deleteFile.do",
			param : {reqInfoList:reqInfoList},
			success : function(result){
				console.log(result);
				location.reload();
			}
		});
	}
}

