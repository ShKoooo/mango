<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
<!--
	.body-container {
		max-width: 800px;
	}
-->
.margin-top {
	margin-top: 60px;
}

.bold {
	font-weight: bold;
}

.searchBox {
	border-radius: 20px;
	width: 100%;
}

#sub-title {
	font-weight: bold;
}

.psersonal {
	position: fixed;
	left: 90%;
	top: 90%;
}

.psersonalButton {
	width: 150px;
	height: 50px;
	font-weight: bold;
	font-size: 15px;
	color: black;
	background-color: white;
	border-radius: 50px;
	border: 1px;
	box-shadow: 5px 5px 5px;
}

.psersonal-two {
	position: fixed;
	width: 350px;
	height: 573px;
	
	left: 81%;
	top: 35%;
	
	background: white;
	
	border-radius: 20px;
	border: 1px;
	box-shadow: 5px 5px 5px;
	
	z-index: 9999;
	
	display: none;
	
}

.psersonal-two div:first-child {
	background: rgb(59, 60, 63);
	color: white;
	text-align: center;
	font-size: 17px;
	font-weight: bold;
	padding: 10px;
	padding-left: 65px;
	
	border-top-left-radius: 20px;
	border-top-right-radius: 20px;
	
}

.psersonal-two-two div:nth-child(1) {
	text-align: left;
	font-size: 15px;
	font-weight: bold;
	padding: 20px;
	color: black;
	background: white;
}

.psersonal-two-two div:nth-child(2) {
	text-align: left;
	font-size: 15px;
	font-weight: bold;
	padding: 20px;
}

.psersonal-two-two div:nth-child(3) {
	text-align: left;
	font-size: 15px;
	font-weight: bold;
	padding: 20px;
}

.psersonal-two-two div:nth-child(4) {
	padding: 20px;
}

.psersonal-two-two div:nth-child(4) > button {
	width: 100%;
	border: 1px;
	border-radius: 15px;
	
	background: rgb(59, 60, 63);
	color: white;
	font-size: 15px;
	font-weight: bold;
	
}

.close{
	cursor: pointer;
	width: 60px;
	float: right;
	
	font-size: 20px;
	color: snow;
}

#psersonal-two-return {
	width: 100%;
	height: 260px;
	text-align: center;
	
	background: white;
	padding-left: 0px;
}

#psersonal-two-return2 {
	width: 100%;
	padding-left: 97px;
}

#personalBackBtn {

	border-radius: 15px;
	color: black;
	background: white;
	
	width: 155px;
}

#personalOk {
	display: none;
}


</style>

<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto+Slab:400,700,300,100">
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,400italic,300italic,300,500,500italic,700,900">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">

<script type="text/javascript">

function sendPersonalBtn() {
	var f = document.personalForm;
	var str;
	
	str = f.inquiryEmail.value.trim();
	if(!str) {
		alert("이메일을 입력 해주세요");
		f.inquiryEmail.focus();
		return;
	}
	
	str = f.inquiryContent.value.trim();
	if(!str) {
		alert("내용을 입력 해주세요");
		f.inquiryContent.focus();
		return;
	}
	
	$(function(){
		$("#personalSend").click(function(){ // 1대1 문의 전송 버튼 눌렀을때
			$(".psersonal-two-two").hide();
			$("#personalOk").show();
		});
		
		$("#personalBackBtn").click(function(){
			$("#personalOk").hide();
			$(".psersonal-two-two input").val("");
			$(".psersonal-two-two textarea").val("");
			$(".psersonal-two-two").show();
		});
	});
	
	f.action = "${pageContext.request.contextPath}/inquiry/personalSend";
	f.submit();
	
}

$(function(){
	
	$(".psersonalButton").click(function() {
		$(".psersonalButton").hide(444);
		$(".psersonal-two").show(444);
	});
	
	$(".close").click(function(){
		$(".psersonal-two").hide(444);
		$(".psersonalButton").show(444);
	});
	
});
</script>

<script type="text/javascript">
function searchFaq() {
	var f = document.searchFaqForm;
	
	var str = f.search.value.trim();
	if(!str) {
		alert("검색어를 입력 해주세요");
		f.search.focus();
		return;
	}
	
	f.action = "${pageContext.request.contextPath}/csQna/qna";
	f.submit();
}
</script>

<div class="content-wrapper">
	<div class="inner-container container">
	    <div class="row">
	        <div class="section-header col-md-12 margin-top">
	            <h2 style="font-weight: bold;">고객 센터</h2>
	            <span>Customer Service Center</span>
	        </div> 
	    </div> 
	    <div class="row">
		    <form name="searchFaqForm" method="post">
		        <div class="col-md-6 service-item service-left" style="width: 100%;">
		            <div class="box-content">
		                <div class="service-icon">
		                    <a href="javascript:searchFaq();" style="text-decoration-line: none;"><i class="li_search"></i></a>
		                </div>
		                
	                	<div class="service-content">
		                    <h4 style="font-weight: bold; padding-right: 255px;">망고마켓 이용에 <b class="blue bold">궁</b><b class="green bold">금</b>하신게 있으신가요 ?</h4>
		                    <input class="searchBox" name="search" id="search" type="text" placeholder="검색">
	                	</div>
		                
		            </div> 
		        </div>
			</form>
			
	        <div class="col-md-6 service-item service-left">
	            <div class="box-content">
	                <div class="service-icon">
	                    <a href="${pageContext.request.contextPath}/cscenter/csNotice" style="text-decoration-line: none;"><i class="li_star"></i></a>
	                </div>
	                <div class="service-content">
	                    <h4 id="sub-title">공지 사항</h4>
	                    <p>망고 마켓의 주요 공지 사항들을 확인 합니다.<br><br>Check notice of Mango Market</p>
	                </div>
	            </div> 
	        </div> 
	        <div class="col-md-6 service-item service-right">
	            <div class="box-content">
	                <div class="service-icon">
	                    <a href="${pageContext.request.contextPath}/csQna/qna" style="text-decoration-line: none;"><i class="li_study"></i></a>
	                </div>
	                <div class="service-content">
	                    <h4 id="sub-title">자주 물어보는 질문들</h4>
	                    <p>1대1 문의 전에 자주 물어보는 질문들을 확인 해보세요.<br><br>Frequently Asked Questions on Mango Market.</p>
	                </div>
	            </div> 
	        </div> 
	        <div class="col-md-6 service-item service-left">
	            <div class="box-content">
	                <div class="service-icon">
	                    <a href="${pageContext.request.contextPath}/csEvent/event" style="text-decoration-line: none;"><i class="li_heart"></i></a>
	                </div>
	                <div class="service-content">
	                    <h4 id="sub-title">이벤트</h4>
	                    <p>망고 마켓의 다양한 이벤트를 확인 하세요.<br><br>Check various events</p>
	                </div>
	            </div> <!-- /.box-content -->
	        </div> <!-- /.service-item -->
	        <div class="col-md-6 service-item service-right">
	            <div class="box-content">
	                <div class="service-icon">
	                    <a href="${pageContext.request.contextPath}/csGuide/main" style="text-decoration-line: none;"><i class="li_bubble"></i></a>
	                </div>
	                <div class="service-content">
	                    <h4 id="sub-title">이용 가이드</h4>
	                    <p>망고 마켓이 처음이라면?<br><br>If first time Using Mango Market</p>
	                </div>		
	            </div> 
	        </div> 
	    </div>
	</div>
	
	<div class="psersonal">
		<button class="psersonalButton" type="button" style="">
			<i class="li_mail" style="font-style: normal;">&nbsp;1대1 문의</i>
		</button>
	</div>
	
	<form name="personalForm" method="post" enctype="multipart/form-data">
		<div class="psersonal-two">
			<div>
				문의하기
				<i class="li_trash close"></i>
			</div>
			<div class="psersonal-two-two">
				<div>
					<label style="width: 100%; padding-bottom: 10px;">이메일 주소</label>
					<input type="text" name="inquiryEmail" style="color: black; width: 100%; border-radius: 10px;">
				</div>
				
				<div>
					<label style="width: 100%; padding-bottom: 10px;">무엇을 도와 드릴까요?</label>
					<textarea rows="5" cols="33" name="inquiryContent" style="color: black; border-radius: 10px;"></textarea>
				</div>
				
				<div>
					<label style="width: 100%; padding-bottom: 10px;">첨부 파일</label>
					<input type="file" name="selectFile" style="border-radius: 10px; width: 100%;">
				</div>
				<div>
					<button type="button" id="personalSend" onclick="sendPersonalBtn();">보내기</button>
				</div>
			</div>
		</div>
	</form>
		
		 <div id="personalOk">
			<div id="psersonal-two-return">
				<h3 style="line-height: 14">전송 완료</h3>
			</div>
			<div id="psersonal-two-return2">
				<button type="button" id="personalBackBtn">돌아가기</button>
			</div>
		</div>
</div> 


