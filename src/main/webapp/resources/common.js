$(function(){
	console.log("TEST");
});

function transaction(option){
	var jsonStr = "";
		
	if(option.param){
		jsonStr = JSON.stringify(option.param);
		console.log("TEST",jsonStr);
	}
	
	$.ajax({
        url  : option.url
       ,type : option.type ? option.type : 'POST'
       ,data : {jsonStr:jsonStr}
       ,timeout:100000
       ,success : function(result){
          if(option.success) option.success(result);
       }
       ,error : function(e){
           alert("시스템 오류가 발생했습니다.");
       }
       ,beforeSend:function(){
           console.log("param ...",jsonStr);
       }
       ,complete:function(){
       }
   });
}

function fileTransaction(option){
	var formData = new FormData();
	var fileEl = option.fileEl;
	for(var i=0; i<fileEl.files.length; i++){
        formData.append('file',fileEl.files[i]);
    }
	var path = option.path;
	formData.append('path',path);
	$.ajax({
        url  : option.url
       ,data: formData
       ,enctype: 'multipart/form-data'
       ,processData: false
       ,contentType: false
       ,type: 'POST'
       ,timeout:100000
       ,success : function(result){
          if(option.success) option.success(result);
       }
       ,error : function(e){
           alert("시스템 오류가 발생했습니다.");
       }
       ,beforeSend:function(){
    	   for(var pair of formData.entries()){
    	 	   console.log(pair[0],pair[1]);
    	   }
       }
       ,complete:function(){
       }
   });
}
