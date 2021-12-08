﻿<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

	<header class="site-header container-fluid">
            <div class="top-header">
                <div class="logo col-md-6 col-sm-6">
                    <span>..</span> <!-- 아이콘 자리 -->
                    <h1><a href="${pageContext.request.contextPath}/"><em>망고</em>마켓</a></h1>
                </div> <!-- /.logo -->
                
         	<div class="container">
	         	<div class="row">
	         		<div class="col">
						<div class="d-flex justify-content-end">
							<c:choose>
								<c:when test="${empty sessionScope.member}">
									<div class="p-2">
										<a href="javascript:dialogLogin();" title="로그인"><i class="bi bi-lock"></i></a>
									</div>
									<div class="p-2">
										<a href="${pageContext.request.contextPath}/member/member" title="회원가입"><i class="bi bi-person-plus"></i></a>
									</div>	
								</c:when>
								<c:otherwise>
									<div class="p-2">
										<a href="${pageContext.request.contextPath}/member/logout" title="로그아웃"><i class="bi bi-unlock"></i></a>
									</div>					
									<div class="p-2">
										<a href="#" title="알림"><i class="bi bi-bell"></i></a>
									</div>
									<c:if test="${sessionScope.member.membership>50}">
										<div class="p-2">
											<a href="${pageContext.request.contextPath}/admin" title="관리자"><i class="bi bi-gear"></i></a>
										</div>					
									</c:if>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
	         	</div>	
	         </div>		
            </div> <!-- /.top-header -->
            
          
            <div class="main-header">
                <div class="row">
                    <div class="main-header-left col-md-3 col-sm-6 col-xs-8">
                        <a id="search-icon" class="btn-left fa fa-search" href="#search-overlay"></a>
                        <div id="search-overlay">
                            <a href="#search-overlay" class="close-search"><i class="fa fa-times-circle"></i></a>
                            <div class="search-form-holder">
                                <h2>Type keywords and hit enter</h2>
                                <form id="search-form" action="#">
                                    <input type="search" name="s" placeholder="" autocomplete="off" />
                                </form>
                            </div>
                        </div><!-- #search-overlay -->
                        <a href="#" class="btn-left arrow-left fa fa-angle-left"></a>
                        <a href="#" class="btn-left arrow-right fa fa-angle-right"></a>
                    </div> <!-- /.main-header-left -->
                    <div class="menu-wrapper col-md-9 col-sm-6 col-xs-4">
                        <a href="#" class="toggle-menu visible-sm visible-xs"><i class="fa fa-bars"></i></a>
                        <ul class="sf-menu hidden-xs hidden-sm">
                          
                    <!--    <li class="active"><a href="index.html">Home</a></li>	-->
                            <li><a href="#">사고팔기</a>
                                <ul>
                                    <li><a href="projects-2.html">중고거래</a></li>
                                    <li><a href="projects-3.html">기프티콘 거래</a></li>
                                </ul>
                            </li>
                            <li><a href="services.html">동네 커뮤니티</a></li>
                            <li><a href="#">고객센터</a>
                                <ul>
                                    <li><a href="blog.html">자주하는 질문</a></li>
                                    <li><a href="blog-single.html">1:1 문의</a></li>
                                </ul>
                            </li>
                           <!-- <li><a href="contact.html">Contact</a></li>  --> 
                        </ul>
                    </div> <!-- /.menu-wrapper -->
                </div> <!-- /.row -->
            </div> <!-- /.main-header -->
            <div id="responsive-menu">
                <ul>
                    <li><a href="#">사고팔기</a>
                        <ul>
                            <li><a href="projects-2.html">중고거래</a></li>
                            <li><a href="projects-3.html">기프티콘 거래</a></li>
                        </ul>
                    </li>
                    <li><a href="services.html">동네 커뮤니티</a></li>
                    <li><a href="#">고객센터</a>
                        <ul>
                            <li><a href="blog.html">자주하는 질문</a></li>
                            <li><a href="blog-single.html">1:1 문의</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
	</header> <!-- /.site-header -->