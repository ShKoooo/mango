<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto+Slab:400,700,300,100">
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,400italic,300italic,300,500,500italic,700,900">

<style type="text/css">
.img-viewer {
	cursor: pointer;
	border: 1px solid #ccc;
	width: 45px;
	height: 45px;
	border-radius: 45px;
	padding: 0;
	background-image: url("${pageContext.request.contextPath}/resources/images/note-person-icon2.png");
	position: relative;
	/*
	z-index: 9999;
	*/
	background-repeat : no-repeat;
	background-size : cover;
}
</style>

		<!-- Carousel -->
       	<div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
		  <div class="carousel-inner">
		    <div class="carousel-item active">
		 		<div class="main" style="background-color: #fdfaf3">
		 			<div class="inner">
			    	<img src="${pageContext.request.contextPath}/resources/images/main.png" style="padding:300px;">
		 			</div>
		 		</div>
		    </div>

		    <div class="carousel-item" data-bs-interval="10000">
		    	<div class="main" style="background-image: url('resources/images/main2.jpg'); background-size: cover;">
		    		<div class="inner" style="padding: 300px;">
			    			<h3 style="color:#FFFFFF;"> 집에서 자고 있는 물건들, <br>지금 당장 망고하는 건 어떨까요? </h3>
							<a href="${pageContext.request.contextPath}/product/list" class="main-btn white">망고하기</a>
		    		</div>
		    	</div>
		    </div>
		  </div>
		  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
		    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
		    <span class="visually-hidden">Previous</span>
		  </button>
		  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
		    <span class="carousel-control-next-icon" aria-hidden="true"></span>
		    <span class="visually-hidden">Next</span>
		  </button>
		</div>
        
        <div style="height: 100px; text-align: center; margin-top: 60px;">
	       	<h3>지금 <span style="color: #f69730;">망고</span>마켓은? <span style="color: #f69730;">망고</span>마켓 BEST <span style="color: #CC3D3D;">TOP 3</span> !!</h3>
        </div>
        
        <div class="container">
        	<!-- 상품 인기리스트 -->
        	<div><h5>중고거래 <span style="color: #CC3D3D;">BEST</span>&nbsp;&nbsp;>></h5></div>
	        <div class="tab-content pt-2" id="nav-tabContent">
				<div class="tab-pane fade show active mt-3" id="nav-content" role="tabpanel" aria-labelledby="nav-tab-content">
		        	<div class="row gx-5 more-list">
			        	<!-- list div 반복 영역 -->
			            <c:forEach var="dto" items="${productPopList}">
			            	<div class="col-lg-4 mb-5">
			                	<div class="card h-100 shadow border-0">
			                    	<img class="card-img-top" width="308px" height="200px" src="${dto.pImgSaveFileName}" alt="" />
			                        	<div class="card-body p-4">
			                            	<a class="logo text-decoration-none link-dark stretched-link" href="${pArticleUrl}&pNum=${dto.pNum}"><div class="h5 card-title mb-3">${dto.pSubject}</div></a>
			                                	<p class="card-text mb-0">${dto.area3}</p>
			                            </div>
			                            <div class="card-footer p-4 pt-0 bg-transparent border-top-0">
			                            	<div class="d-flex align-items-end justify-content-between">
			                                	<div class="d-flex align-items-center">
			                                    	<c:if test="${not empty dto.userImgSaveFileName}">
														<img src="${pageContext.request.contextPath}/uploads/photo/${dto.userImgSaveFileName}"
															class="img-fluid img-thumbnail img-viewer">
													</c:if>
													<c:if test="${empty dto.userImgSaveFileName}">
														<img
															class="img-fluid img-thumbnail img-viewer">
													</c:if>
			                                        <div class="row small" style="margin-left: 2px;">
			                                        	<div class="fw-bold">${dto.userNickName}</div>
			                                            <div class="text-muted">${dto.pRegDate}</div>
			                                        </div>
			                                    </div>
			                                    <span><i class="bi bi-heart" style="color: red;"></i>&nbsp;${dto.pWishCount}</span>
			                                </div>
			                            </div>
			                   	</div>
			                </div>
			           	</c:forEach>
			            <!-- list div 반복 영역 -->
			        </div>
		    	</div>
		    </div>
		    
		    
        	<!-- 기프티콘 인기리스트 -->
        	<div><h5>기프티콘 <span style="color: #CC3D3D;">BEST</span>&nbsp;&nbsp;>></h5></div>
	        <div class="tab-content pt-2" id="nav-tabContent">
				<div class="tab-pane fade show active mt-3" id="nav-content" role="tabpanel" aria-labelledby="nav-tab-content">
		        	<div class="row gx-5 more-list">
			        	<!-- list div 반복 영역 -->
			            <c:forEach var="dto" items="${giftyPopList}">
			            	<div class="col-lg-4 mb-5">
			                	<div class="card h-100 shadow border-0">
			                    	<img class="card-img-top" width="308px" height="200px" src="${dto.gImgSaveFileName}" alt="" />
			                        	<div class="card-body p-4">
			                            	<a class="logo text-decoration-none link-dark stretched-link" href="${gArticleUrl}&gNum=${dto.gNum}"><div class="h5 card-title mb-3">${dto.gSubject}</div></a>
			                                	
			                            </div>
			                            <div class="card-footer p-4 pt-0 bg-transparent border-top-0">
			                            	<div class="d-flex align-items-end justify-content-between">
			                                	<div class="d-flex align-items-center">
			                                    	<c:if test="${not empty dto.userImgSaveFileName}">
														<img src="${pageContext.request.contextPath}/uploads/photo/${dto.userImgSaveFileName}"
															class="img-fluid img-thumbnail img-viewer">
													</c:if>
													<c:if test="${empty dto.userImgSaveFileName}">
														<img
															class="img-fluid img-thumbnail img-viewer">
													</c:if>
			                                        <div class="row small" style="margin-left: 2px;">
			                                        	<div class="fw-bold">${dto.userNickName}</div>
			                                            <div class="text-muted">${dto.gRegdate}</div>
			                                        </div>
			                                    </div>
			                                    <span><i class="bi bi-heart" style="color: red;"></i>&nbsp;${dto.gWishCount}</span>
			                                </div>
			                            </div>
			                   	</div>
			                </div>
			           	</c:forEach>
			            <!-- list div 반복 영역 -->
			        </div>
		    	</div>
		    </div>
		    
		    
        	<!-- 동네 커뮤니티 인기리스트 -->
        	<div><h5>동네 커뮤니티 <span style="color: #CC3D3D;">BEST</span>&nbsp;&nbsp;>></h5></div>
	        <div class="tab-content pt-2" id="nav-tabContent">
				<div class="tab-pane fade show active mt-3" id="nav-content" role="tabpanel" aria-labelledby="nav-tab-content">
		        	<div class="row gx-5 more-list">
			        	<!-- list div 반복 영역 -->
			            <c:forEach var="dto" items="${villagePopList}">
			            	<div class="col-lg-4 mb-5">
			                	<div class="card h-100 shadow border-0">
			                    	<img class="card-img-top" width="308px" height="200px" src="${dto.thumbnail}" alt="" />
			                        	<div class="card-body p-4">
			                            	<a class="logo text-decoration-none link-dark stretched-link" href="${vArticleUrl}&vNum=${dto.vNum}"><div class="h5 card-title mb-3">${dto.subject}</div></a>
			                                	
			                            </div>
			                            <div class="card-footer p-4 pt-0 bg-transparent border-top-0">
			                            	<div class="d-flex align-items-end justify-content-between">
			                                	<div class="d-flex align-items-center">
			                                    	<c:if test="${not empty dto.userImgSaveFileName}">
														<img src="${pageContext.request.contextPath}/uploads/photo/${dto.userImgSaveFileName}"
															class="img-fluid img-thumbnail img-viewer">
													</c:if>
													<c:if test="${empty dto.userImgSaveFileName}">
														<img
															class="img-fluid img-thumbnail img-viewer">
													</c:if>
			                                        <div class="row small" style="margin-left: 2px;">
			                                        	<div class="fw-bold">${dto.userNickName}</div>
			                                            <div class="text-muted">${dto.reg_date}</div>
			                                        </div>
			                                    </div>
			                                    <span><i class="bi bi-heart" style="color: red;"></i>&nbsp;${dto.vWishCount}</span>
			                                </div>
			                            </div>
			                   	</div>
			                </div>
			           	</c:forEach>
			            <!-- list div 반복 영역 -->
			        </div>
		    	</div>
		    </div>
	    </div>