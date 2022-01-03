<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-main {
	max-width: 800px;
}
</style>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tabs.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boot-board.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.css" type="text/css">


<style type="text/css">
.hover-tr:hover {
	cursor: pointer;
	background: #fffdfd;
}
</style>

<script type="text/javascript">
$(function(){
	$("#tab-0").addClass("active");
});

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data){
			fn(data);
		},
		beforeSend : function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error : function(jqXHR) {
			if (jqXHR.status == 403) {
				location.href="${pageContext.request.contextPath}/member/login";
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패했습니다.");
				return false;
			}
			console.log(jqXHR.responseText);
		}
	});
}

function searchList() {
	var f=document.searchForm;
	f.state.value=$("#selectEnabled").val();
	f.action="${pageContext.request.contextPath}/admin/cscenter/list";
	f.submit();
}
	
function detailedMember(inquiryNum, state) {
	var dlg = $("#member-dialog").dialog({
		  autoOpen: false,
		  modal: true,
		  buttons: {
		       " 삭제 " : function() {
		    	   deleteOk(inquiryNum);
			   },
		       " 닫기 " : function() {
		    	   $(this).dialog("close");
		       }
		  },
		  height: 520,
		  width: 800,
		  title: "1대1 문의",
		  close: function(event, ui) {
		  }
	});

	var url = "${pageContext.request.contextPath}/admin/cscenter/detaile";
	var query = "inquiryNum="+inquiryNum;
	
	var fn = function(data){
		$('#member-dialog').html(data);
		dlg.dialog("open");
	};
	ajaxFun(url, "post", query, "html", fn);
}

function deleteOk(inquiryNum) {
	if(confirm("선택한 문의를 삭제 하시겠습니까 ?")) {
		var url = "${pageContext.request.contextPath}/admin/cscenter/delete";
		var query = "inquiryNum=" + inquiryNum;
		
		var fn = function(){
			alert("삭제 완료");
			location.reload();
		};
		ajaxFun(url, "post", query, "json", fn);
		
	}
	
	$('#member-dialog').dialog("close");
}


function selectStateChange() {
	var f = document.deteailedMemberForm;
	
	var s = f.stateCode.value;
	var txt = f.stateCode.options[f.stateCode.selectedIndex].text;
	
	f.memo.value = "";	
	if(! s) {
		return;
	}

	if(s!="0" && s!="6") {
		f.memo.value = txt;
	}
	
	f.memo.focus();
}
</script>

<main>
	<h1>Admin Page</h1>
	
	<div class="body-container">
	    <div class="body-title">
			<h2><i class="icofont-users"></i> 1대1 문의 관리 </h2>
	    </div>
	    
	    <div class="body-main ms-30">
			<div>
				<ul class="tabs">
					<li id="tab-0" data-tab="0"><i class="icofont-waiter-alt"></i>문의 리스트</li>
				</ul>
			</div>
			<div id="tab-content" style="clear:both; padding: 20px 10px 0;">
			
				<table class="table">
					<tr>
						<td align="left" width="50%">
							총 문의 갯수 : ${dataCount}개 (${page}/${total_page} 페이지)
						</td>
						<td align="right">
							<select id="selectEnabled" class="selectField" onchange="searchList();">
								<option value="" ${state=="" ? "selected='selected'":""}>::응답 구분::</option>
								<option value="0" ${state=="0" ? "selected='selected'":""}>미완료</option>
								<option value="1" ${state=="1" ? "selected='selected'":""}>완료</option>
							</select>
						</td>
					</tr>
				</table>
					
				<table class="table table-border table-list">
					<thead>
						<tr> 
							<th class="w-100">문의 번호</th>
							<th class="w-100">아이디</th>
							<th class="w-100">이메일</th>
							<th class="w-150">내용</th>
							<th class="w-120">문의 날짜</th>
							<th class="w-100">처리 상태</th>
							<th class="w-auto">첨부파일</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach var="dto" items="${list}">
						<tr class="hover-tr" onclick="detailedMember('${dto.inquiryNum}');"> 
							<td>${dto.inquiryNum}</td>
							<td>${dto.userId}</td>
							<td>${dto.inquiryEmail}</td>
							<td>${dto.inquiryContent}</td>
							<td>${dto.inquiryRegDate}</td>
							<td>${dto.state == 0 ? "미완료" : "완료"}</td>
							<td>
								<c:if test="${not empty dto.saveFilename}">
									<a href="${pageContext.request.contextPath}/admin/cscenter/download?inquiryNum=${dto.inquiryNum}" class="text-reset"><i class="li_cloud"></i></a>
								</c:if>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
						 
				<div class="page-box">
					${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
				</div>
						
				<table class="table">
					<tr>
						<td align="left" width="100">
							<button type="button" class="btn" onclick="javascript:location.href='${pageContext.request.contextPath}/admin/cscenter/list';">새로고침</button>
						</td>
						<td align="center">
							<form name="searchForm" action="${pageContext.request.contextPath}/admin/memberManage/list" method="post">
								<select name="condition" class="selectField">
									<option value="userId"     ${condition=="userId" ? "selected='selected'":""}>아이디</option>
									<option value="inquiryEmail"      ${condition=="email" ? "selected='selected'":""}>이메일</option>
								</select>
								<input type="text" name="keyword" class="boxTF" value="${keyword}">
								<input type="hidden" name="page" value="1">
								<input type="hidden" name="state" value="${state}">
								<button type="button" class="btn" onclick="searchList()">검색</button>
							</form>
						</td>
						<td align="right" width="100">&nbsp;</td>
					</tr>
				</table>
			
			</div>
			
	    </div>
	</div>
	<div id="member-dialog" style="display: none;"></div>
</main>
