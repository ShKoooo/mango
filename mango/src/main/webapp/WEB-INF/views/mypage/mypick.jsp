<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}
</style>

<script type="text/javascript">
function searchList() {
	var f = document.searchForm;
	f.submit();
}

function insertData() {
	var f = document.insertForm;
	f.submit();
}

$(function() {
	$("body").on("click",".btn-delete", function() {
		var delConfStr = $(this).closest("tr").attr("data-keyword")+" 님을 관심유저 목록에서 삭제하시겠습니까?";
		var seqNum = $(this).closest("tr").attr("data-num");
		var tableName = "pickedUser";
		
		if (!confirm(delConfStr)) {
			return false;
		}
		
		var url = "${pageContext.request.contextPath}/mypage/deleteData"
		var query = "seqNum="+seqNum+"&tableName="+tableName;
		
		$.ajax({
			type:"POST"
			,url:url
			,data:query
			,dataType:"json"
			,success:function(data) {
				var passed = data.state;

				if (passed==="true") {
					location.href = "${pageContext.request.contextPath}/mypage/mypick";
					location.reload();
				}
			}
		});
	});
});
</script>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> ${sessionScope.member.userNickName} 님의 마이 </h3>
		</div>
		
		<div class="body-main">
			<div class="row mb-3">
				<div class="col-md-7">
					<h4>
						<i class="icofont-simple-smile"></i> 관심 유저 목록
					</h4>
				</div>
				<div class="col-md-5 text-right">
					<form name="searchForm" action="${pageContext.request.contextPath}/mypage/mypick">
						<input type="text" name="keyword" value="${keyword}" class="boxTF" placeholder="닉네임">&nbsp;
						<button type="button" class="btn btn-primary" onclick="searchList();">검색</button>
					</form>
				</div>
			</div>
			<c:if test="${empty myPickedUserList}">
				<div class="border bg-light mb-3 p-3 text-center">
					관심 유저가 없습니다.
				</div>
			</c:if>
			<c:if test="${not empty myPickedUserList}">
				<div class="row mb-3 mx-3">
					<table class="table">
						<thead class="table-light">
							<tr>
								<th class="col-3">닉네임</th>
								<th class="col-5">등록 일시</th>
								<th class="col-1">&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="dto" items="${myPickedUserList}">
								<tr data-num="${dto.pickedNum}" data-keyword="${dto.userNickName}">
									<td class="col-3">${dto.userNickName}</td>
									<td class="col-5">${dto.regDate}</td>
									<td class="col-1">
										<button type="button" class="btn btn-outline-danger btn-delete" name="deleteBusn"><i class="icofont-close"></i></button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				${paging}
			</c:if>
			
			<div class="row">
				<div class="col-md-5">
					<form name="insertForm" action="${pageContext.request.contextPath}/mypage/insertPickedUser">
						<input type="text" name="keyword" value="${keyword}" class="boxTF" placeholder="닉네임">&nbsp;
						<button type="button" class="btn btn-primary" onclick="insertData();">등록</button>
					</form>
				</div>
				<div class="col-md-2 text-center">
					${message}
				</div>
				<div class="col-md-5 text-right">
					<button class="btn btn-danger" type="button" id="addrBtn" onclick="location.href='${pageContext.request.contextPath}/mypage/main'">
						뒤로가기
					</button>
				</div>
			</div>
		</div>
	</div>
</div>