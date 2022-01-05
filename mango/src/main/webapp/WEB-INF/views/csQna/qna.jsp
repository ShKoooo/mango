<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.tabBtn {
	color: black;
	font-weight: bold;
}
</style>

<script type="text/javascript">
function login() {
	location.href="${pageContext.request.contextPath}/member/login";
}

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type : method,
		url : url,
		data : query,
		dataType : dataType,
		success:function(data){
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

$(function(){
	var search = "${search}";

	if(search != "") {
		var f=document.faqSearchForm;
		f.search.value = search;
	}
	
	listPage(1);
	
	 $("button[role='tab']").on("click", function(e){
		// var tab = $(this).attr("aria-controls");
    	listPage(1);
    });
	 
});

function listPage(page) {
	var $tab = $("button[role='tab'].active");
	var categoryNum = $tab.attr("data-categoryNum");
	
	var url="${pageContext.request.contextPath}/csQna/list";
	var query="pageNo="+page+"&categoryNum="+categoryNum;
	var search = $("#mySearch").val();
	
	query = query + "&search=" + search;
	var selector = "#nav-content";
	
	var fn = function(data){
		$(selector).html(data);
	};
	ajaxFun(url, "get", query, "html", fn);
}

//글 삭제
function deleteFaq(faqNum, page) {
	var url="${pageContext.request.contextPath}/csQna/delete";
	
	var query="faqNum="+ faqNum;
	
	if(! confirm("위 게시물을 삭제 하시 겠습니까 ? ")) {
		  return;
	}
	
	var fn = function(data){
		listPage(page);
	};
	
	ajaxFun(url, "post", query, "json", fn);
	location.reload();
}

</script>
<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 자주 묻는 질문 </h3>
		</div>
			
			<div class="body-main">
			<ul class="nav nav-tabs" id="myTab" role="tablist">
				<li class="nav-item" role="presentation">
					<button class="nav-link active tabBtn" id="tab-0" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="0" aria-selected="true" data-categoryNum="0">모두</button>
				</li>
				<c:forEach var="dto" items="${listCategory}" varStatus="status">
					<li class="nav-item" role="presentation">
						<button class="nav-link tabBtn" id="tab-${status.count}" data-bs-toggle="tab" data-bs-target="#nav-content" type="button" role="tab" aria-controls="${status.count}" aria-selected="true" data-categoryNum="${dto.categoryNum}">${dto.category}</button>
					</li>
				</c:forEach>
			</ul>
			
			<div class="tab-content pt-2" id="nav-tabContent">
				<div class="tab-pane fade show active" id="nav-content" role="tabpanel" aria-labelledby="nav-tab-content">
				</div>
			</div>
			
		</div>
		  
		  </div>
		  
		<div>
		  <div class="tab-pane fade" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab">2번탭</div>
		  <div class="tab-pane fade" id="pills-contact" role="tabpanel" aria-labelledby="pills-contact-tab">3번탭</div>
		</div>
</div>

<form name="faqSearchForm" method="post">
	<input type="hidden" name="search" id="mySearch" value="">
</form>