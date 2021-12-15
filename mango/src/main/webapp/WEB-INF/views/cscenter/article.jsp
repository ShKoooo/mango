<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.twobtn {
	color: black;
	background: white;
	border-radius: 10px;
}

</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boot-board.css" type="text/css">

<script type="text/javascript">
function deleteBoard() {
    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
	    var query = "noticeNum=${dto.noticeNum}&${query}";
	    var url = "${pageContext.request.contextPath}/cscenter/delete?" + query;
    	location.href = url;
    }
}
</script>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-subtract"></i> 공지사항 </h3>
		</div>
		
		<div class="body-main">

			<table class="table mb-0">
				<thead>
				</thead>
				
				<tbody>
					<tr>
						<td width="50%">
							${dto.nSubject}
						</td>
						<td align="right">
							${dto.nRegDate}
						</td>
					</tr>
					
					<tr>
						<td colspan="2" valign="top" height="200">
							${dto.nContent}
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							파&nbsp;&nbsp;일 :
							<c:if test="${not empty dto.saveFilename}">
								<a href="${pageContext.request.contextPath}/cscenter/download?noticeNum=${dto.noticeNum}">${dto.originalFilename}</a>
							</c:if>

						</td>
					</tr>
				</tbody>
			</table>
			
			<table class="table table-borderless">
				<tr>
					<td width="50%">
						
							<c:if test="${sessionScope.member.userId == 'admin' }">
								<button type="button" class="btn btn-light twobtn" onclick="location.href='${pageContext.request.contextPath}/cscenter/update?noticeNum=${dto.noticeNum}&page=${page}';">수정</button>
							</c:if>
							
							<c:if test="${sessionScope.member.userId == 'admin' }">
								<button type="button" class="btn btn-light twobtn" onclick="deleteBoard();">삭제</button>
							</c:if>
				    	
					</td>
					<td class="text-end">
						<button type="button" class="btn btn-light twobtn" onclick="location.href='${pageContext.request.contextPath}/cscenter/csNotice?${query}';">리스트</button>
					</td>
				</tr>
			</table>
			
		</div>
	</div>
</div>