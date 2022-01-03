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

	
    str = f.faqSubject.value.trim();
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
	f.faqContent.value = str;

    f.action = "${pageContext.request.contextPath}/csQna/${mode}";
    f.submit();
}

</script>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 자주 묻는 질문 </h3>
		</div>
		
		<div class="body-main">
		
		
		
			<form name="boardForm" method="post" enctype="multipart/form-data">
				<table class="table write-form mt-5">
				
					<tr>
						<td class="table-light" scope="row">카테고리</td>
						<td>
							<div class="row">
								<div class="col-sm-4 pe-1">
									<select name="categoryNum" class="form-select">
										<c:forEach var="vo" items="${listCategory}">
											<option value="${vo.categoryNum}" ${dto.categoryNum==vo.categoryNum?"selected='selected'":""}>${vo.category}</option>
										</c:forEach>
									</select>
								</div>

							</div>
						</td>
					</tr>
					
					<tr>
						<td class="table-light" scope="row">제 목</td>
						<td>
							<input type="text" name="faqSubject" class="form-control" value="${dto.faqSubject}">
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
								<textarea name="faqContent" id="content" class="form-control">${dto.faqContent}</textarea>
							 -->
							<div class="editor form-control">${dto.faqContent}</div>
							<input type="hidden" name="faqContent" id="content">
						</td>
					</tr>

				</table>
				
				<table class="table table-borderless">
 					<tr>
						<td class="text-center">
							<button type="button" class="btn btn-dark" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}&nbsp;<i class="bi bi-check2"></i></button>
							<button type="reset" class="btn btn-light">다시입력</button>
							<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/cscenter/csNotice';">${mode=='update'?'수정취소':'등록취소'}&nbsp;<i class="bi bi-x"></i></button>
							<c:if test="${mode=='update'}">
								<input type="hidden" name="faqNum" value="${dto.faqNum}">
								<input type="hidden" name="pageNo" value="${pageNo}">
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
