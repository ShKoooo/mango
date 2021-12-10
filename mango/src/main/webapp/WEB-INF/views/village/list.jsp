<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">

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
 }

.carousel-control-next {
	justify-content: flex-end;
}



</style>
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
								<div class="project-thumb">
									<img class="img-fluid" src="https://source.unsplash.com/V7WkmXntA8M" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="">동네 소식 제목</a></h2>
									<p> 동네 소식 내용 </p>
								</div>
							</div> 
							
							<div class="col-md-4 project-item">
								<div class="proeject-thumb">
									<img class="img-fluid" src="https://source.unsplash.com/mBM4gHAj4XE" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="">분실 실종 제목</a></h2>
									<p> 분실 실종 내용 </p>
								</div>
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
							
						</div> <!-- row -->
					</div> <!-- caousal-item active -->
					<div class="carousel-item">
						<div class="row">
						
							<div class="col-md-4 project-item">
								<div class="project-thumb">
									<img class="img-fluid" src="https://unsplash.com/photos/L6T_6Rp2iEk" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="">동네 소식 제목22</a></h2>
									<p> 동네 소식 내용22 </p>
								</div>
							</div> 
							
							<div class="col-md-4 project-item">
								<div class="proeject-thumb">
									<img class="img-fluid" src="https://unsplash.com/photos/L6T_6Rp2iEk" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="">분실 실종 제목22</a></h2>
									<p> 분실 실종 내용222 </p>
								</div>
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
							
						</div> <!-- row -->
					</div>
					<div class="carousel-item">
						<div class="row">
							
							<div class="col-md-4 project-item">
								<div class="project-thumb">
									<img class="img-fluid" src="https://unsplash.com/photos/L6T_6Rp2iEk" alt="">
								</div>
								<div class="box-content project-detail">
									<h2><a href="">동네 소식 제목3</a></h2>
									<p> 동네 소식 내용3 </p>
								</div>
							</div> 
							
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
						
						</div>
					</div>
				</div><!-- carousel inner -->
			</div>
		</div>
		
		<div class="col-12">
			<div class="row">
				<div class="col-md-8 project-item">
					<div class="box-content">
					
						게시판..인데 탭을 넣을 수 있을까...............
					</div>
				</div>
				<div class="col-md-4 project-item">
					<div class="box-content">
						1인 홍보,,,,,,인데 캐러셀을 하나 더 넣을 수 잇을지..
					</div>
				</div>
			</div>
		</div>
	</div> <!-- project holder -->
	</div>
</div>