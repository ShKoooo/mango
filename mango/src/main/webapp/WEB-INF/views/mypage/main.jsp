<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style type="text/css">
.body-container {
	max-width: 800px;
}
</style>

<div class="container">
	<div class="body-container">	
		<div class="body-title">
			<h3><i class="bi bi-app"></i> 마이 페이지 </h3>
		</div>
		
		<div class="body-main">
			<div class="row mb-3">
				<h3>
					<a href="${pageContext.request.contextPath}/member/pwd">
						<i class="icofont-refresh"></i> 회원 정보수정
					</a>
				</h3>
			</div>
			<div class="row mb-3">
				매너 온도 : ${mannerDto.mannerDeg}&nbsp;도
			</div>
			<div>
				<h4>
					<a href="${pageContext.request.contextPath}/member/address">
						주소 인증하기
					</a>
				</h4>
			</div>
			<div>
				<h4>
					<a href="${pageContext.request.contextPath}/member/business">
						비즈니스 프로필 관리
					</a>
				</h4>
			</div>
			<div>
				<h4>
					<a href="#">
						관심유저, 차단유저, 관심키워드
					</a>
				</h4>
			</div>
			<div>
				<h4>
					<a href="#">
						가계부
					</a>
				</h4>
			</div>
			<div>
				<h4>
					<a href="#">
						내 활동
					</a>
				</h4>
			</div>
			<div>
				<h4>
					<a href="#">
						쪽지
					</a>
				</h4>
			</div>
		</div>
	</div>
</div>