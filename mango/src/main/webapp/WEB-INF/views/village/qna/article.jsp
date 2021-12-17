<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
<c:if test="${sessionScope.member.userId==dto.userId||sessionScope.member.membership>50}">
	function deleteBoard() {
		if(confirm("게시글을 삭제하시겠습니까?")) {
			var query = "num=${dto.vNum}&${query}";
			var url = "${pageContext.request.contextPath}/village/qna/delete?" + query;
			location.href = url;
		}
	}
</c:if>
</script>

<script type="text/javascript">
function login () {
	location.href="${pageContext.request.contextPath}/member/login";
}
function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패했습니다.");
				return false;
			}
			
			console.log(jqXHR.responseText);
		}
	});
}

</script>

<div class="content-wrapper">
	<div class="inner-container container">
		<div class="row">
			<div class="section-header col-md-12">
				<h2> 동네 질문 </h2>
				<span>Village QNA</span>
			</div>
		</div> <!-- row -->
	<div class="projects-holder">
		<div class="col-12">
			<div class="box-content">
			
				<div class="body-main">
				
					<table class="table mb-0">
						<thead>
							<tr>
								<td colspan="2" align="center">
									${dto.subject}
								</td>
							</tr>
						</thead>
						
						<tbody>
							<tr>
								<td width="50%">
									이름 : ${dto.userNickName}
								</td>
								<td align="right">
									${dto.reg_date} | 조회 ${dto.hitCount}
								</td>							
							</tr>
							
							<tr>
								<td colspan="2" valign="top" height="200" style="border-bottom: none;">
									<div class="editor">${dto.content}</div>
								</td>
							</tr>
							
							<tr>
								<td colspan="2" class="text-center p-3"> 좋아요 부분 </td>
							</tr>
						
							<tr>
								<td colspan="2">
									이전글 : 
									<c:if test="${not empty preReadDto}">
										<a href="${pageContext.request.contextPath}/village/qna/article?${query}&num=${preReadDto.vNum}">${preReadDto.subject}</a>
									</c:if>
								</td>
							</tr>
							
							<tr>
								<td colspan="2">
									다음글 : 
									<c:if test="${not empty nextReadDto}">
										<a href="${pageContext.request.contextPath}/village/qna/article?${query}&num=${nextReadDto.vNum}">${nextReadDto.subject}</a>
									</c:if>
								</td>
							</tr>
						</tbody>
					</table>
					
					<table class="table table-borderless mb-2">
						<tr>
							<td width="50%">
								<c:choose>
									<c:when test="${sessionScope.member.userId==dto.userId}">
										<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/village/qna/update?num=${dto.vNum}&page=${page}';">수정</button>
									</c:when>
								</c:choose>
								
								<c:choose>
									<c:when test="${sessionScope.member.userId==dto.userId || sessionScope.member.membership>50}">
										<button type="button" class="btn btn-light" onclick="deleteBoard();"> 삭제 </button>
									</c:when>
								</c:choose>
							</td>
							<td class="text-end">
								<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/village/qna/list?${query}';">리스트</button>
							</td>
						</tr>
					</table>
				
				</div>
			
			</div>
		</div>
	</div>
	</div>



</div>

<script type="text/javascript">
ClassicEditor
	.create( document.querySelector( '.editor'), {
	})
	.then( editor => {
		window.editor = editor;
		editor.isReadOnly = true;
		editor.ui.view.top.remove( editor.ui.view.stickyPanel );
	});

</script>