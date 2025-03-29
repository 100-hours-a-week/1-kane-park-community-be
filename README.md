# 1-kane-park-community-be

<b><h1>커뮤니티 프로젝트</h1></b>
<h2>backend API</h2>
src.main.resources.application.properties 내 <br>
MySQL 정보 수정 후 실행 <br><br>
<span style="color: #1e00ff">POST /signup </span>
회원가입<br>
<span style="color: #1e00ff">POST /login </span>
로그인<br>
<span style="color: #1e00ff">GET /board  </span>
게시글 목록 조회<br>
<span style="color: #1e00ff">GET /board/{id} </span>
게시글 상세 조회<br>
<span style="color: #1e00ff">POST /board </span>
게시글 작<br>
<span style="color: #1e00ff">PUT /board/{id} </span>
게시글 수정<br>
<span style="color: #1e00ff">PUT /board/{id}/like </span>
좋아요 증가<br>
<span style="color: #1e00ff">PUT /board/{id}/view </span>
조회수 증가<br>
<span style="color: #1e00ff">DELETE /board/{id} </span>
게시글 삭제<br>
<span style="color: #1e00ff">GET /board/{id}/comments </span>
특정 게시글 댓글 조회<br>
<span style="color: #1e00ff">GET /board/{id}/comments/count </span>
특정 게시글 댓글 카운<br>
<span style="color: #1e00ff">POST /board/{id}/comment </span>
특정 게시글 댓글 작성<br>
<span style="color: #1e00ff">PUT /comment/{id} </span>
댓글 수<br>
<span style="color: #1e00ff">DELETE /comment/{id} </span>
댓글 삭제<br>

