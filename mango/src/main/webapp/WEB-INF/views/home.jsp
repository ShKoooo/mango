<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto+Slab:400,700,300,100">
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,400italic,300italic,300,500,500italic,700,900">

       
        <div class="swiper-container">
            <div class="swiper-wrapper">

                <div class="swiper-slide" style="background-image: url(images/slide1.jpg);">
                    <div class="overlay-s"></div>
                    <div class="slider-caption">
                        <div class="inner-content">
                            <h2>Earth New House Project</h2>
                            <p>Artcore is free HTML5 template by <b class="blue">template</b><b class="green">mo</b>. Credit goes to <a rel="nofollow" href="http://unsplash.com">Unsplash</a> for photos.</p>
                            <a href="#" class="main-btn white">View Projects</a>
                        </div>
                    </div>
                </div>

                <div class="swiper-slide" style="background-image: url(images/slide2.jpg);">
                    <div class="overlay-s"></div>
                    <div class="slider-caption">
                        <div class="inner-content">
                            <h2>Hotel and Residence Concept in Montenegro</h2>
                            <p>We come with new fresh and unique ideas.</p>
                            <a href="#" class="main-btn white">View Projects</a>
                        </div>
                    </div>
                </div>

                <div class="swiper-slide" style="background-image: url(images/slide3.jpg);">
                    <div class="overlay-s"></div>
                    <div class="slider-caption">
                        <div class="inner-content">
                            <h2>Natural 3d Architecture Design</h2>
                            <p>Natural concrete is a material which is calm and clean.</p>
                            <a href="#" class="main-btn white">View Projects</a>
                        </div>
                    </div>
                </div>
            </div>
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
			                    	<img class="card-img-top" width="308px" height="200px" src="${dto.vImgFilename}" alt="" />
			                        	<div class="card-body p-4">
			                            	<a class="logo text-decoration-none link-dark stretched-link" href="${articleUrl}&vNum=${dto.vNum}"><div class="h5 card-title mb-3">${dto.subject}</div></a>
			                                	
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