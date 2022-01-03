<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}

.star li{
	font-size:22px;
	letter-spacing:-1px;
	display:inline-block;
	color:#ccc;
	text-decoration:none;
	cursor:pointer;
}
.star li.on{
	color:#F2CB61;
}
</style>

<script type="text/javascript">
$(function(){
	$(".star li").click(function(){
		var b = $(this).hasClass("on");
		
		$(this).parent().children("li").removeClass("on");
		$(this).addClass("on").prevAll("li").addClass("on");
		if(b) {
			$(this).removeClass("on");
		}
		
		var star = $(".star .on").length;
		$("#mannerstar").val(star);
		
		
	});
});

function sendReview() {
	var f = document.reviewForm;
	var st = f.mannerstar.value; 
	
	if(st==='0') {
		alert("별점을 입력해주세요.");
		return;
	}
	
	var query = "gNum=${dto.gNum}"
	
	f.action = "${pageContext.request.contextPath}/gifty/writeReview?"+query;
	f.submit();
}

</script>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i>&lt; ${dto.gSubject} &gt; 게시물에 대한 리뷰 작성하기 </h3>
		</div>
		
		<div class="body-main">
			<ul class="star">
				<li><span>★</span></li>
				<li><span>★</span></li>
				<li><span>★</span></li>
				<li><span>★</span></li>
				<li><span>★</span></li>
			</ul>
		</div>
		
		<div class="p-1">
				<span class="fw-bold">기프티콘 상세리뷰</span>
			</div>
		<form name="reviewForm" method="POST">
				<input type="hidden" name="mannerstar" id="mannerstar" value="0" readonly="readonly">
			<div class="p-1">
				<textarea name="grContent" id="grContent" class="form-control" placeholder="${empty sessionScope.member ? '로그인 후 등록 가능합니다.':''}"></textarea>
			</div>
			<div class="p-1 text-end">
				<button type="button" class="btnSend btn btn-dark" onclick="sendReview();"> 등록하기 <i class="bi bi-check2"></i> </button>
			</div>
			<!-- 
			<div class="p-1 text-end">
				<button type="button" class="btnSend btn btn-dark" ${empty sessionScope.member ? "disabled='disabled'":""} onclick="sendOk();"> 등록하기 <i class="bi bi-check2"></i> </button>
			</div>
			 -->
		</form>
	</div>
</div>