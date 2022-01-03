<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap5/css/bootstrap.min.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/vendor/bootstrap5/icon/bootstrap-icons.css" type="text/css">

<style type="text/css">
.adminStateBox {
	border-radius: 5px;
}

.body-container {
	margin-left : 20px;
	max-width: 1000px;
}
</style>

<script type="text/javascript">
$(function(){
	$("body").on("click",".btn-saveState", function() {
		var msCodeNum = $("#opStateCode option:selected").val();
		var memo = $("#stateMemo").val();
		var minusDeg = $("#minusDeg").val();
		
		if (!msCodeNum) {
			$("#opStateCode").focus();
			return false;
		}
		if (!memo) {
			$("#stateMemo").focus();
			return false;
		}
		
		memo = "Activated by: ${sessionScope.member.userId}\n"+memo;
		
		memo = encodeURIComponent(memo);
		
		var query = "page=${page}"
			+"&chkBlk=${chkBlk}"
			+"&chkBus=${chkBus}"
			+"&chkRep=${chkRep}"
			+"&userId=${userId}"
			+"&memberId=${memberId}"
			+"&userNickName=${userNickName}"
			+"&msCodeNum="+msCodeNum
			+"&memo="+memo
			+"&minusDeg="+minusDeg;
			
		var url = "${pageContext.request.contextPath}/admin/member/updateSubmit";
		url += "?"+query;
		
		console.log(url);
		location.href = url;
	});
});

$(function() {
	$("body").on("click",".btn-cancelState", function() {
		var query = "page=${page}"
			+"&chkBlk=${chkBlk}"
			+"&chkBus=${chkBus}"
			+"&chkRep=${chkRep}"
			+"&userId=${userId}"
			+"&memberId=${memberId}"
			+"&userNickName=${userNickName}";
		
		var url = "${pageContext.request.contextPath}/admin/member/id";
		url += "?"+query;
		
		console.log(url);
		location.href = url;
	});
});
</script>

<main>
	<h1>Admin Page</h1>
	
	<div class="body-container">
	    <div class="body-title">
			<h2><i class="icofont-interface"></i> ${memberId} 상태 변경 </h2>
	    </div>
	    
	    <div class="body-main">
	    	<form class="form-floating">
		    	<div class="row mb-2">
		    		<textarea id="stateMemo" class="form-control adminStateBox" name="content" style="height: 120px; resize: none;"></textarea>
		    	</div>
		    	<div class="row mb-5">
		    		<div class="col-auto me-6">
		    			<select id="opStateCode" name="opStateCode" class="adminStateBox">
		    				<option value="" selected>상태코드 선택</option>
		    				<c:forEach var="dto" items="${stateCodeList}">
		    					<option value="${dto.msCodeNum}">${dto.msCodeName}</option>	
		    				</c:forEach>
		    			</select>
		    		</div>
		    		<div class="col-auto ms-6 me-auto">
		    			프로필 체온 감점&nbsp;&nbsp;<input class="adminStateBox" type="number" id="minusDeg" name="minusDeg" min="0" max="5" step="0.5" value="0">
		    		</div>
		    		<div class="col-auto text-right">
		    			<button type="button" class="btn btn-danger btn-cancelState">
		    				변경취소
		    			</button>
		    			&nbsp;
		    			<button type="button" class="btn btn-primary btn-saveState">
		    				변경
		    			</button>
		    		</div>
		    	</div>
	    	</form>
	    
	    	<div class="row mb-3 mt-2">
				<table class="table">
					<thead class="table-light">
						<tr>
							<th class="col-1">상태</th>
							<th class="col-3">메모</th>
							<th class="col-1">처리일자</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="dto" items="${stateList}">
							<tr>
								<td>${dto.msCodeName}</td>
								<td>${dto.memo}</td>
								<td>${dto.regDate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<c:if test="${not empty stateList}">
					${paging}
				</c:if>
				<c:if test="${empty stateList}">
					상태 변경 이력이 없습니다.
				</c:if>
	    	</div>	    	
	    </div>
	</div>
	
</main>