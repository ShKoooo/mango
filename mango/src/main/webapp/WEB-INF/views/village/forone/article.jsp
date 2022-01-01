<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.section-header {
	margin-top : 60px;
	margin-bottom: 60px;
}

.ck.ck-editor__main>.ck-editor__editable:not(.ck-focused) { 
	border: none;
}

textarea.form-control {
    width: 100%;
    height: 100px;
    resize: none;
}
  
.form-control {
	font-size: 12px;
}

  
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/vendor/ckeditor5/ckeditor.js"></script>
<script type="text/javascript">
<c:if test="${sessionScope.member.userId==dto.userId||sessionScope.member.membership>50}">
	function deleteBoard() {
		if(confirm("게시글을 삭제하시겠습니까?")) {
			var query = "vNum=${dto.vNum}&${query}";
			var url = "${pageContext.request.contextPath}/village/forone/delete?" + query;
			location.href = url;
		}
	}
</c:if>
</script>

<script type="text/javascript">
function login() {
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
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
	    	
			console.log(jqXHR.responseText);
		}
	});
}

// 페이징 처리
$(function() {
	listPage(1);
});

function listPage(page) {
	var url = "${pageContext.request.contextPath}/village/forone/listReply";
	var query = "vNum=${dto.vNum}&pageNo="+page;
	var selector = "#listReply";
	
	var fn = function(data) {
		$(selector).html(data);
	};
	ajaxFun(url, "get", query, "html", fn);
}

</script>

<div class="content-wrapper">
	<div class="inner-container container">
		<div class="row">
			<div class="section-header col-md-12">
				<h2>For ONE</h2>
				<span>혼자서도 잘 노는 방법!</span>
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
								 <b> 운영자 </b>
								</td>
								<td align="right">
									${dto.reg_date}
								</td>							
							</tr>
							
							<tr>
								<td colspan="2" valign="top" height="200" style="border-bottom: none; padding-top: 5px;">
									<div class="editor">${dto.content}</div>
								</td>
							</tr>
							
						</tbody>
					</table>
					
					<table class="table table-borderless mb-2">
						<tr>
							<td width="50%">
								<c:choose>
									<c:when test="${sessionScope.member.userId==dto.userId}">
										<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/village/forone/update?vNum=${dto.vNum}&page=${page}';">수정</button>
									</c:when>
								</c:choose>
								
								<c:choose>
									<c:when test="${sessionScope.member.userId==dto.userId || sessionScope.member.membership>50}">
										<button type="button" class="btn btn-light" onclick="deleteBoard();"> 삭제 </button>
									</c:when>
								</c:choose>
								
							</td>
							<td class="text-end">
								<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/village/forone/list';">리스트</button>
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