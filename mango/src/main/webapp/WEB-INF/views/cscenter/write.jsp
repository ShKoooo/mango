<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boot-board.css" type="text/css">

<script type="text/javascript">
function sendOk() {
    var f = document.boardForm;
	var str;
	
    str = f.nSubject.value.trim();
    if(!str) {
        alert("제목을 입력하세요. ");
        f.nSubject.focus();
        return;
    }

    str = f.nContent.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.nContent.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/cscenter/${mode}";
    f.submit();
}

<c:if test="${mode=='update'}">
	function deleteFile(noticeNum) {
		if( ! confirm("파일을 삭제하시겠습니까 ?") ) {
			return;
		}
		var url = "${pageContext.request.contextPath}/cscenter/deleteFile?noticeNum=" + noticeNum + "&page=${page}";
		location.href = url;
	}
</c:if>
</script>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 공지사항 </h3>
		</div>
		
		<div class="body-main">
		
			<form name="boardForm" method="post" enctype="multipart/form-data">
				<table class="table write-form mt-5">
					<tr>
						<td class="table-light" scope="row">제 목</td>
						<td>
							<input type="text" name="nSubject" class="form-control" value="${dto.nSubject}">
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
							<textarea name="nContent" id="content" class="form-control">${dto.nContent}</textarea>
						</td>
					</tr>
					
					<tr>
						<td class="table-light">첨&nbsp;&nbsp;&nbsp;&nbsp;부</td>
						<td> 
							<input type="file" name="selectFile" class="form-control">
						</td>
					</tr>
					
					<c:if test="${mode=='update'}">
						<tr>
							<td class="table-light" scope="row">첨부된파일</td>
							<td> 
								<p class="form-control-plaintext">
									<c:if test="${not empty dto.saveFilename}">
										<a href="javascript:deleteFile('${dto.noticeNum}');"><i class="bi bi-trash"></i></a>
										${dto.originalFilename}
									</c:if>
									&nbsp;
								</p>
							</td>
						</tr>
					</c:if>
				</table>
				
				<table class="table table-borderless">
 					<tr>
						<td class="text-center">
							<button type="button" class="btn btn-dark" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}&nbsp;<i class="bi bi-check2"></i></button>
							<button type="reset" class="btn btn-light">다시입력</button>
							<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/cscenter/csNotice';">${mode=='update'?'수정취소':'등록취소'}&nbsp;<i class="bi bi-x"></i></button>
							<c:if test="${mode=='update'}">
								<input type="hidden" name="noticeNum" value="${dto.noticeNum}">
								<input type="hidden" name="saveFilename" value="${dto.saveFilename}">
								<input type="hidden" name="originalFilename" value="${dto.originalFilename}">
								<input type="hidden" name="page" value="${page}">
							</c:if>
						</td>
					</tr>
				</table>
			</form>
		
		</div>
	</div>
</div>