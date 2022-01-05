<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.ck.ck-editor {
	max-width: 99%;
}
.ck-editor__editable {
    min-height: 250px;
}
.ck-content .image>figcaption {
	min-height: 25px;
}

</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boot-board.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/ckeditor5/ckeditor.js"></script>

<script type="text/javascript">
function sendOk() {
    var f = document.boardForm;
	var str;
	
	var dateFrom = document.getElementById('dateFrom');
	var dateTo = document.getElementById('dateTo');
	
	dateFrom = new Date(dateFrom.value);
	dateTo = new Date(dateTo.value);
	
	if(isNaN(dateFrom.valueOf())) {
		alert("시작 날짜를 선택 해주세요");
		return false;
	} else if(isNaN(dateTo.valueOf())) {
		alert("종료 날짜를 선택 해주세요");
		return false;
	}
	
	if(dateFrom.valueOf() > dateTo.valueOf()) {
		alert("종료 날짜는 시작 날짜보다 커야 합니다.");
		return false;
	}

	
    str = f.subject.value.trim();
    if(!str) {
        alert("제목을 입력하세요. ");
        f.faqSubject.focus();
        return;
    }

    str = window.editor.getData().trim();
    if(! str) {
        alert("내용을 입력하세요. ");
        window.editor.focus();
        return;
    }
	f.content.value = str;

    f.action = "${pageContext.request.contextPath}/csEvent/${mode}";
    f.submit();
}

</script>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 이벤트 </h3>
		</div>
		
		<div class="body-main">
		
		
		
			<form name="boardForm" method="post" enctype="multipart/form-data">
				<table class="table write-form mt-5">
				
					<tr>
						<td class="table-light" scope="row">이벤트 공개 유무</td>
						<td>
							<div class="row">
								<div class="col-sm-4 pe-1">
									<select name="show" class="form-select">
										<option value="y">공개</option>
										<option value="n">비공개</option>
									</select>
								</div>

							</div>
						</td>
					</tr>
					
					<tr>
						<td class="table-light" scope="row">이벤트 날짜 설정</td>
						<td>
							<input type="date" id="dateFrom" name="start_date" value="${dto.start_date}" style="border-radius: 10px; color: black;"> 
							&nbsp;~&nbsp; 
							<input type="date" id="dateTo" name="end_date" value="${dto.end_date}" style="border-radius: 10px; color: red;">
						</td>
					</tr>
					
					<tr>
						<td class="table-light" scope="row">제 목</td>
						<td>
							<input type="text" name="subject" class="form-control" value="${dto.subject}">
						</td>
					</tr>
        
					<tr>
						<td class="table-light" scope="row">작성자명</td>
 						<td>
							<p class="form-control-plaintext">${sessionScope.member.userId}</p>
						</td>
					</tr>

					<tr>
						<td class="table-light" scope="row">내 용</td>
						<td>
							<!-- 
								<textarea name="content" id="content" class="form-control">${dto.content}</textarea>
							 -->
							<div class="editor form-control">${dto.content}</div>
							<input type="hidden" name="content" id="content">
						</td>
					</tr>

				</table>
				
				<table class="table table-borderless">
 					<tr>
						<td class="text-center">
							<button type="button" class="btn btn-dark" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}&nbsp;<i class="bi bi-check2"></i></button>
							<button type="reset" class="btn btn-light">다시입력</button>
							<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/csEvent/event';">${mode=='update'?'수정취소':'등록취소'}&nbsp;<i class="bi bi-x"></i></button>
							<c:if test="${mode=='update'}">
								<input type="hidden" name="num" value="${dto.num}">
								<input type="hidden" name="page" value="${pageNo}">
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
