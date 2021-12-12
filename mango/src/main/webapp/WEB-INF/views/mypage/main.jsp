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
					<a href="${pageContext.request.contextPath}/">
						<i class="icofont-refresh"></i> 회원 정보수정
					</a>
				</h3>
			</div>
			<div class="row mb-3">
				매너 온도 : ${mannerDto.mannerDeg}&nbsp;도
			</div>
		</div>
	</div>
</div>