<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">

.table {
	width: 95%
}

.section-header {
	margin-top : 60px;
	margin-bottom: 60px;
}

.img-fluid {
	width: 100%;
	height: 250px;
}

 .carousel-control-prev {
 	justify-content: space-between;
 	width: 5%;
 }

.carousel-control-next {
	justify-content: flex-end;
	width: 5%;
}

.nav-tabs .nav-link.active {
	border-color: #fff;
	color: #f69730;
}


.nav-tabs .nav-link {
	border : none;
	color: #495057;
}

.tab-content {
	background: white;
	padding: 25px;
	overflow:hidden;
}

a:hover, a:active {
	text-decoration : none;
	color: #f69730;
}


</style>
<script type="text/javascript">

$(function() {
	
	$("#selectArea").change( function() {
		// 주소 바꿨을 때 좌표 영역에 값 세팅
		var maLat = $("#selectArea option:selected").attr("data-maLat");
		var maLon = $("#selectArea option:selected").attr("data-maLon");
		
		var f = document.searchForm;
		f.maLat.value = maLat;
		f.maLon.value = maLon;
		f.areaNum.value = $(this).val();
		searchList()
	});
	
});

</script>

<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto+Slab:400,700,300,100">
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,400italic,300italic,300,500,500italic,700,900">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">

<div class="content-wrapper">
	<div class="inner-container container">
		<div class="row">
			<div class="section-header col-md-12">
				<h2>오늘의 망고</h2>
				<span>Today's Mango</span>
			</div>
		</div> <!-- row -->
	<div class="projects-holder">
	
		<div class="col-12">
			<div class="row">
				<div class="col-md-12 project-item">
					<ul class="nav nav-tabs">
					  <li class="nav-item">
					    <a class="nav-link active" data-bs-toggle="tab" href="#qna">동네 질문</a>
					  </li>
					  <li class="nav-item">
					    <a class="nav-link" data-bs-toggle="tab" href="#eat">동네 맛집</a>
					  </li>
					  <li class="nav-item">
					    <a class="nav-link" data-bs-toggle="tab" href="#helpme">해주세요</a>
					  </li>
					  <li class="nav-item">
					    <a class="nav-link" data-bs-toggle="tab" href="#withme">같이해요</a>
					  </li>
					  <li class="nav-item">
					    <a class="nav-link" data-bs-toggle="tab" href="#news">동네 소식</a>
					  </li>
					</ul>

					
					<!-- Tab panes -->
					<div class="tab-content">
					  <div class="tab-pane container active" id="qna">
						<c:forEach var="qna" items="${listQna}" varStatus="status">
							<div class="row">
								${qna.subject}
							</div>
						</c:forEach>
					  </div>
					  <div class="tab-pane container " id="eat">
					 	<c:forEach var="eat" items="${listEat}" varStatus="status">
							<div class="row">
								${eat.subject}
							</div>
						</c:forEach>
					  </div>
					  <div class="tab-pane container " id="helpme">
					  	<c:forEach var="help" items="${listHelp}" varStatus="status">
							<div class="row">
								${help.subject}
							</div>
						</c:forEach>
					  </div>
					  <div class="tab-pane container " id="withme">
					  	<c:forEach var="with" items="${listWith}" varStatus="status">
							<div class="row">
								${with.subject}
							</div>
						</c:forEach>
					  </div>
					  <div class="tab-pane container " id="news">
					  	<c:forEach var="news" items="${listNews}" varStatus="status">
							<div class="row">
								${news.subject}
							</div>
						</c:forEach>
					  </div>
					</div>
				</div>
			</div>
		</div>
		
		
		<div class="col-12">
			<div id="carouselIndicators" class="carousel carousel-dark slide" data-bs-ride="carousel">
					<button class="carousel-control-prev" type="button" data-bs-target="#carouselIndicators" data-bs-slide="prev">
					    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
					    <span class="visually-hidden">Previous</span>
					  </button>
					  <button class="carousel-control-next" type="button" data-bs-target="#carouselIndicators" data-bs-slide="next">
					    <span class="carousel-control-next-icon" aria-hidden="true"></span>
					    <span class="visually-hidden">Next</span>
					  </button>
				<div class="carousel-inner">
					<div class="carousel-item active">
						<div class="row">
						
							<div class="col-md-4 project-item">
								<c:forEach var="lost" items="${listLost1}" varStatus="status">
								<div class="proeject-thumb">
									<img class="img-fluid" src="${lost.thumbnail}" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="${pageContext.request.contextPath}/village/lost/article?vNum=${lost.vNum}">${lost.subject}</a></h2>
								</div>
								</c:forEach>
							</div>
							<div class="col-md-4 project-item">
								<div class="project-thumb">
									<img class="img-fluid" src="https://source.unsplash.com/SsKf1L6rWJk" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="">가게 홍보 제목</a></h2>
									<p> 가게 홍보 내용 </p>
								</div>
							</div>
							
							<div class="col-md-4 project-item">
								<div class="project-thumb">
									<img class="img-fluid" src="https://source.unsplash.com/V7WkmXntA8M" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="">1인 가구</a></h2>
									<p> 추천내용 </p>
								</div>
							</div> 
							
						</div> <!-- row -->
					</div> <!-- caousal-item active -->
					<div class="carousel-item">
						<div class="row">
						
							<div class="col-md-4 project-item">
								<c:forEach var="lost" items="${listLost2}" varStatus="status">
								<div class="proeject-thumb">
									<img class="img-fluid" src="${lost.thumbnail}" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="${pageContext.request.contextPath}/village/lost/article?vNum=${lost.vNum}">${lost.subject}</a></h2>
								</div>
								</c:forEach>
							</div>

							<div class="col-md-4 project-item">
								<div class="project-thumb">
									<img class="img-fluid" src="https://unsplash.com/photos/L6T_6Rp2iEk" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="">가게 홍보 제목22</a></h2>
									<p> 가게 홍보 내용22 </p>
								</div>
							</div>
							
							<div class="col-md-4 project-item">
								<div class="project-thumb">
									<img class="img-fluid" src="https://source.unsplash.com/V7WkmXntA8M" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="">1인 가구</a></h2>
									<p> 추천내용2 </p>
								</div>
							</div> 
							
						</div> <!-- row -->
					</div>
					<div class="carousel-item">
						<div class="row">
							
							<div class="col-md-4 project-item">
								<div class="proeject-thumb">
									<img class="img-fluid" src="https://unsplash.com/photos/L6T_6Rp2iEk" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="">분실 실종 제목3</a></h2>
									<p> 분실 실종 내용3 </p>
								</div>
							</div>
							<div class="col-md-4 project-item">
								<div class="project-thumb">
									<img class="img-fluid" src="https://unsplash.com/photos/L6T_6Rp2iEk" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="">가게 홍보 제목3</a></h2>
									<p> 가게 홍보 내용3 </p>
								</div>
							</div>
							
							<div class="col-md-4 project-item">
								<div class="project-thumb">
									<img class="img-fluid" src="https://source.unsplash.com/V7WkmXntA8M" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="">1인 가구</a></h2>
									<p> 추천내용3 </p>
								</div>
							</div> 
						
						</div>
					</div>
				</div><!-- carousel inner -->
			</div>
		</div>
		
	</div> <!-- project holder -->
	</div>
</div>