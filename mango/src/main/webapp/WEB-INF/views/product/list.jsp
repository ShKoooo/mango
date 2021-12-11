<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.section-header {
  margin-top: 60px;
  margin-bottom: 60px;
}

.jb {
	width: 30px;
	height: 30px;
	
}

.jb:hover {
  transform: scale( 1.2, 1.2 )
}
</style>

		<div class="content-wrapper">
            <div class="inner-container container">
                <div class="row">
                    <div class="section-header col-md-12">
                        <h2>중고거래</h2>
                        <span>망설이지 말고 고! 이웃과 거래를 시작해보세요!</span>
                    </div>
                </div>
                <div class="projects-holder-3">
                    <div class="filter-categories">
                        <ul class="project-filter">
                            <li class="filter" data-filter="all"><span>All</span></li>
                            <li class="filter" data-filter="buildings"><span>Buildings</span></li>
                            <li class="filter" data-filter="design"><span>Design</span></li>
                            <li class="filter" data-filter="architecture"><span>Architecture</span></li>
                            <li class="filter" data-filter="nature"><span>Nature</span></li>
                        </ul>
                    </div>
                    <div>
                    	<a style="color:orange" href="${pageContext.request.contextPath}/product/write"><i class="fa fa-plus-circle jb fa-4x" aria-hidden="true"></i></a>
                    </div>
                    <div class="projects-holder">
                        <div class="row">
                            <div class="col-md-4 project-item mix design">
                                <div class="project-thumb">
                                    <img src="images/projects/project_1.jpg" alt="">
                                    <div class="overlay-b">
                                        <div class="overlay-inner">
                                            <a href="images/projects/project_1.jpg" class="fancybox fa fa-expand" title="Project Title Here"></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-content project-detail">
                                    <h2><a href="${articleUrl}&pNum=${dto.pNum}">글제목</a></h2>
                                    <p>인증한동네(등록한 동네)</p>
                                    <p>채팅현황(채팅몇건햇는지(유저당1))</p>
                                    <p>관심 수</p>
                                </div>
                            </div>
                            <div class="col-md-4 project-item mix nature">
                                <div class="project-thumb">
                                    <img src="images/projects/project_2.jpg" alt="">
                                    <div class="overlay-b">
                                        <div class="overlay-inner">
                                            <a href="images/projects/project_2.jpg" class="fancybox fa fa-expand" title="Project Title Here"></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-content project-detail">
                                    <h2><a href="project-details.html">Instant Domestic Enclosure</a></h2>
                                    <p><a href="http://www.templatemo.com/preview/templatemo_423_artcore">Artcore</a> is free HTML5 template by <b class="blue">template</b><b class="green">mo</b>. Credit goes to <a rel="nofollow" href="http://unsplash.com">Unsplash</a> for images.</p>
                                </div>
                            </div>
                            <div class="col-md-4 project-item mix architecture">
                                <div class="project-thumb">
                                    <img src="images/projects/project_3.jpg" alt="">
                                    <div class="overlay-b">
                                        <div class="overlay-inner">
                                            <a href="images/projects/project_3.jpg" class="fancybox fa fa-expand" title="Project Title Here"></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-content project-detail">
                                    <h2><a href="project-details.html">Ganado & Associates offices</a></h2>
                                    <p>Nullam a vehicula tellus. Integer sodales ante eu feugiat. Sed fermentum diam dui at.</p>
                                </div>
                            </div>
                            <div class="col-md-4 project-item mix buildings">
                                <div class="project-thumb">
                                    <img src="images/projects/project_4.jpg" alt="">
                                    <div class="overlay-b">
                                        <div class="overlay-inner">
                                            <a href="images/projects/project_4.jpg" class="fancybox fa fa-expand" title="Project Title Here"></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-content project-detail">
                                    <h2><a href="project-details.html">St Barbara Bastion</a></h2>
                                    <p>Nullam a vehicula tellus. Integer sodales ante eu feugiat. Sed fermentum diam dui at.</p>
                                </div>
                            </div>
                            <div class="col-md-4 project-item mix design">
                                <div class="project-thumb">
                                    <img src="images/projects/project_5.jpg" alt="">
                                    <div class="overlay-b">
                                        <div class="overlay-inner">
                                            <a href="images/projects/project_5.jpg" class="fancybox fa fa-expand" title="Project Title Here"></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-content project-detail">
                                    <h2><a href="project-details.html">Drawing Out Collapse</a></h2>
                                    <p>Nullam a vehicula tellus. Integer sodales ante eu feugiat. Sed fermentum diam dui at.</p>
                                </div>
                            </div>
                            <div class="col-md-4 project-item mix buildings architecture">
                                <div class="project-thumb">
                                    <img src="images/projects/project_6.jpg" alt="">
                                    <div class="overlay-b">
                                        <div class="overlay-inner">
                                            <a href="images/projects/project_6.jpg" class="fancybox fa fa-expand" title="Project Title Here"></a>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-content project-detail">
                                    <h2><a href="project-details.html">St Ursula Street Apartment</a></h2>
                                    <p>Nullam a vehicula tellus. Integer sodales ante eu feugiat. Sed fermentum diam dui at.</p>
                                </div>
                            </div>
                        </div>
                        <div class="load-more">
                            <a href="javascript:void(0)" class="load-more">Load More</a>
                        </div>
                    </div>
                </div>
            </div>
		</div>