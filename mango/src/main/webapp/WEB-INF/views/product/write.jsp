<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.ck.ck-editor {
	max-width: 97%;
}
.ck-editor__editable {
    min-height: 250px;
}
.ck-content .image>figcaption {
	min-height: 25px;
}

.form-input {
	display: block;
    width: 100%;
    padding: 0.375rem 2.25rem 0.375rem 0.75rem;
    -moz-padding-start: calc(0.75rem - 3px);
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    color: #212529;
    background-color: #fff;
    /*
    background-image: url(data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16'%3e%3cpath fill='none' stroke='%23343a40' stroke-linecap='round' stroke-linejoin='round' stroke-width='2' d='M2 5l6 6 6-6'/%3e%3c/svg%3e);
    */
    background-repeat: no-repeat;
    background-position: right 0.75rem center;
    background-size: 16px 12px;
    border: 1px solid #ced4da;
    border-radius: 0.25rem;
    transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
}
  
} 
</style>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boot-board.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/ckeditor5/ckeditor.js"></script>

<script type="text/javascript">
function sendOk() {
	var f = document.productForm;
	var str;
	
	str = f.pSubject.value.trim();
	if(! str) {
		alert("글 제목을 입력하세요.");
		f.pSubject.focus();
		return;
	}
	
	str = f.areaNum.value.trim();
	if(str == "0") {
		alert("판매할동네를 선택하세요.");
		f.areaNum.focus();
		return;
	}
	
	str = f.pPrice.value.trim();
	if(!/^\d/.test(str)) {
		alert("가격을 입력하세요.");
		f.pPrice.focus();
		return;
	}
	
	str = f.pcNum.value;
	if(str == "0") {
		alert("카테고리를 선택하세요.");
		f.pcNum.focus();
		return;
	}	
	
	str = window.editor.getData().trim();
    if(! str) {
        alert("내용을 입력하세요. ");
        window.editor.focus();
        return;
    }
	f.pContent.value = str;
	
	
	str = f.pIsProposable.value;
	if(str != "T") {
		str = "F";
	}
	
	f.action = "${pageContext.request.contextPath}/product/${mode}";
	f.submit();
}


$(function(){
	$("#selectArea").change(function(){
		if($(this).val()=="0") {
			$("#pLat").val("0");
			$("#pLon").val("0");
			return false;
		}
		
		var opt = $("#selectArea option:selected");
	
		$("#pLat").val(opt.attr("data-maLat"));
		$("#pLon").val(opt.attr("data-maLon"));
	});
});


$(function(){
	$("#prop").change(function(){
		if($("input:checkbox[name='pIsProposable']").is(":checked") == true) {
			//$("#prop_hidden").val("T");
			$("#prop_hidden").attr("disabled", true);
		} else if($("input:checkbox[name='pIsProposable']").is(":checked") == false) {
			$("#prop_hidden").val("F");
		}
	});
});


</script>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 중고거래 글쓰기 </h3>
		</div>
		
		<div class="body-main">
			<form name="productForm" method="post">
				<table class="table write-form mt-5">
					
					<c:if test="${mode=='update'}">	
						<tr>	
							<td class="table-light" scope="row">거래상태</td>
							<td>
								<div class="row" style="float: left;">
									<select name="pStatus" class="form-select" id="selectStatus" style="width: 500px; margin-left: 10px;">
						    			<option selected value="판매중" ${dto.pStatus=="판매중" ? "selected='selected'" : ""}>판매중</option>
							    		<option value="예약중" ${dto.pStatus=="예약중" ? "selected='selected'" : ""}>예약중</option>
						  			</select>
								</div>
							</td>
						</tr>
					</c:if>
					
					<tr>
						<td class="table-light" scope="row">카테고리</td>
						<td>
							<div class="row">
								
				  			<select name="pcNum" class="form-select" id="inputGroupSelect01" style="width: 500px; margin-left: 10px;">
							  	<option selected value="0">선택</option>
							  	<c:forEach var="vo" items="${listCategory}">
							    	<option value="${vo.pcNum}" ${dto.pcNum==vo.pcNum?"selected='selected'":""}>${vo.pcName}</option>				    
								</c:forEach>
							</select>
								
							</div>
						</td>
					</tr>
					
					
					<!-- 거래상태. hidden으로 하면 위에 선이 없어져서 여기로 놨다.. -->
					<c:if test="${mode=='write'}">	
						<tr style="display:none;">	
							<td class="table-light" scope="row">거래상태</td>
							<td>
								<div class="row">
								
								<select name="pStatus" class="form-select" id="inputGroupSelect01" style="width: 500px; margin-left: 10px;">
					    			<option selected value="판매중">판매중</option>
					  			</select>
								</div>
							</td>
						</tr>
					</c:if>
					
					
					<tr>
						<td class="table-light" scope="row">글 제목</td>
						<td>
				  			<input type="text" name="pSubject" class="form-control" value="${dto.pSubject}">
						</td>
					</tr>
					
					<tr>
						<td class="table-light" scope="row">작성자명</td>
						<td>
							<p class="form-control-plaintext">${sessionScope.member.userNickName}</p>
						</td>
					</tr>
					
					<tr>
						<td class="table-light" scope="row">판매할 동네</td>
						<td>
							<div>
				  				<select name="areaNum" id="selectArea" class="form-select" id="inputGroupSelect01">
				  					<option selected value="0">선택</option>
				  					<c:forEach var="vo" items="${listMemberAddr}">
				    					<option value="${vo.areaNum}" ${dto.areaNum==vo.areaNum?"selected='selected'":""} data-maLat='${vo.maLat}' data-maLon='${vo.maLon}'>${vo.area3}</option>	<!-- 기존 : ${vo.maAddr1} -->				    
				    				</c:forEach>
				  				</select>
							</div>
							<div>
								<input type="hidden" name="pLat" id="pLat" value="${dto.pLat}">
								<input type="hidden" name="pLon" id="pLon" value="${dto.pLon}">
							</div>
						</td>
					</tr>
					
					<tr>
						<td class="table-light" scope="row">가격</td>
						<td>
							<input type="text" name="pPrice" value="${dto.pPrice}" placeholder="무료나눔시 0원을 입력해주세요." class="form-input" style="width: 400px; float: left;" >
							<input type="checkbox" name="pIsProposable" value="T" id="prop" style="clear: both; margin-left:20px; margin-top:12px; margin-right:5px;"/>가격 제안받기
							<input type="hidden" name="pIsProposable" value="F" id="prop_hidden"/>
						</td>
					</tr>
					
					<tr>
						<td class="table-light" scope="row">내 용</td>
						<td>
							<div class="editor">${dto.pContent}</div>
							<input type="hidden" name="pContent">
						</td>
					</tr>

				</table>

				<table class="table table-borderless">
 					<tr>
						<td class="text-center">
							<button type="button" class="btn btn-dark" onclick="sendOk();">${mode=='update'? "수정완료" : "등록하기"}&nbsp;<i class="bi bi-check2"></i></button>
							<button type="reset" class="btn btn-light">다시입력</button>
							<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/product/list';">${mode=='update'? "수정취소" : "등록취소"}&nbsp;<i class="bi bi-x"></i></button>
							<c:if test="${mode=='update'}">
								<input type="hidden" name="pNum" value="${dto.pNum}">
								<input type="hidden" name="page" value="${page}">
							</c:if>	
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
	ClassicEditor
		.create( document.querySelector( '.editor' ), {
			fontFamily: {
	            options: [
	                'default',
	                '맑은 고딕, Malgun Gothic, 돋움, sans-serif',
	                '나눔고딕, NanumGothic, Arial'
	            ]
	        },
	        fontSize: {
	            options: [
	                9, 11, 13, 'default', 17, 19, 21
	            ]
	        },
			toolbar: {
				items: [
					'heading','|',
					'fontFamily','fontSize','bold','italic','fontColor','|',
					'alignment','bulletedList','numberedList','|',
					'imageUpload','insertTable','sourceEditing','blockQuote','mediaEmbed','|',
					'undo','redo','|',
					'link','outdent','indent','|',
				]
			},
			image: {
	            toolbar: [
	                'imageStyle:full',
	                'imageStyle:side',
	                '|',
	                'imageTextAlternative'
	            ],
	
	            // The default value.
	            styles: [
	                'full',
	                'side'
	            ]
	        },
			language: 'ko',
			ckfinder: {
		        uploadUrl: '${pageContext.request.contextPath}/image/upload' // 업로드 url (post로 요청 감)
		    }
		})
		.then( editor => {
			window.editor = editor;
		})
		.catch( err => {
			console.error( err.stack );
		});
</script>