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

</style>

<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto+Slab:400,700,300,100">
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,400italic,300italic,300,500,500italic,700,900">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">

<div class="content-wrapper">
	<div class="inner-container container">
	    <div class="row">
	        <div class="section-header col-md-12 margin-top">
	            <h2 style="font-weight: bold;">고객 센터</h2>
	            <span>Customer Service Center</span>
	        </div> <!-- /.section-header -->
	    </div> <!-- /.row -->
	    <div class="row">
	        <div class="col-md-6 service-item service-left" style="width: 100%;">
	            <div class="box-content">
	                <div class="service-icon">
	                    <i class="li_search"></i>
	                </div>
	                <div class="service-content">
	                    <h4 style="font-weight: bold; padding-right: 255px;">망고마켓 이용에 <b class="blue bold">궁</b><b class="green bold">금</b>하신게 있으신가요 ?</h4>
	                    <input class="searchBox" type="text" placeholder="검색">
	                </div>
	            </div> <!-- /.box-content -->
	        </div> <!-- /.service-item -->

	        <div class="col-md-6 service-item service-left">
	            <div class="box-content">
	                <div class="service-icon">
	                    <i class="li_star"></i>
	                </div>
	                <div class="service-content">
	                    <h4 id="sub-title">공지 사항</h4>
	                    <p>망고 마켓의 주요 공지 사항들을 확인 합니다.<br><br>Check notice of Mango Market</p>
	                </div>
	            </div> <!-- /.box-content -->
	        </div> <!-- /.service-item -->
	        <div class="col-md-6 service-item service-right">
	            <div class="box-content">
	                <div class="service-icon">
	                    <i class="li_study"></i>
	                </div>
	                <div class="service-content">
	                    <h4 id="sub-title">자주 물어보는 질문들</h4>
	                    <p>1대1 문의 전에 자주 물어보는 질문들을 확인 해보세요.<br><br>Frequently Asked Questions on Mango Market.</p>
	                </div>
	            </div> <!-- /.box-content -->
	        </div> <!-- /.service-item -->
	        <div class="col-md-6 service-item service-left">
	            <div class="box-content">
	                <div class="service-icon">
	                    <i class="li_heart"></i>
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
	                    <i class="li_bubble"></i>
	                </div>
	                <div class="service-content">
	                    <h4 id="sub-title">이용 가이드</h4>
	                    <p>망고 마켓이 처음이라면?<br><br>If first time Using Mango Market</p>
	                </div>
	            </div> <!-- /.box-content -->
	        </div> <!-- /.service-item -->
	    </div> <!-- /.row -->
	</div> <!-- /.inner-content -->
</div> <!-- /.content-wrapper -->



