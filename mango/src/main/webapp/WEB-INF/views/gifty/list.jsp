<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<style type="text/css">
.body-container {
	max-width: 800px;
}
</style>
        <div class="content-wrapper">
            <div class="inner-container container">
                <div class="row">
                    <div class="section-header col-md-12">
                        <h2>기프티콘 거래</h2>
                        <span>안쓰는 기프티콘 팔아요</span>
                    </div> <!-- /.section-header -->
                    <div>
                    	<button type="button" onclick="location.href='${pageContext.request.contextPath}/gifty/write';"  >기프티콘 등록</button>
                    </div>
       
                </div> <!-- /.row -->
                <div class="projects-holder-3">
                    <div class="filter-categories">
                        <ul class="project-filter">
                            <li class="filter" data-filter="all"><span>전체보기</span></li>
                            <li class="filter" data-filter="buildings"><span>카페</span></li>
                            <li class="filter" data-filter="design"><span>음식</span></li>
                            <li class="filter" data-filter="architecture"><span>기타</span></li>
                            <li class="filter" data-filter="nature"><span>Nature</span></li>
                        </ul>
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
                                    <h2><a href="${pageContext.request.contextPath}/gifty/article">기프티콘 이름</a></h2>
                                    <p>Nullam a vehicula tellus. Integer sodales ante eu feugiat. Sed fermentum diam dui at.</p>
                                </div>
                            </div> <!-- /.project-item -->
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
                            </div> <!-- /.project-item -->
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
                            </div> <!-- /.project-item -->
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
                            </div> <!-- /.project-item -->
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
                            </div> <!-- /.project-item -->
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
                            </div> <!-- /.project-item -->
                        </div> <!-- /.row -->
                        <div class="load-more">
                            <a href="javascript:void(0)" class="load-more">Load More</a>
                        </div>
                    </div> <!-- /.projects-holder -->
                </div> <!-- /.projects-holder-2 -->
            </div> <!-- /.inner-content -->
        </div> <!-- /.content-wrapper -->

        
       
