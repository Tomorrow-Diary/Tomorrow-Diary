<template>
  <header class="header">
    <div class="header-left">
      <h1 class="no-drag">내일일기</h1>
    </div>
    <div class="header-right">
      <div class="user-info">
        <!-- 유저 아이콘을 버튼으로 변경 -->
        <button class="user-icon-button">
          <font-awesome-icon icon="user" class="user-icon" />
        </button>
        <div class="user-details">
          <span class="user-name">ssafy님</span>
          <button class="logout-button" @click="logout">로그아웃</button>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup>
const logout = () => {
  alert("로그아웃 되었습니다!");
  // 로그아웃 로직 추가 필요
};
</script>

<style scoped>
.header {
  display: flex;
  justify-content: space-between; /* 좌우 정렬 */
  align-items: center; /* 세로 가운데 정렬 */
  padding: 0.5rem 2rem;
  position: fixed; /* 화면 상단에 고정 */
  top: 0; /* 화면 상단에서 시작 */
  left: 50%; /* 화면의 가운데로 이동 */
  transform: translateX(-50%); /* 정확히 중앙 정렬 */
  width: 95%; /* 헤더의 전체 너비를 화면의 90%로 설정 */
  height: 60px; /* 고정 높이 */
  z-index: 1000; /* 다른 요소 위로 배치 */
}

.header-left h1 {
  font-size: 1.5rem;
  color: #333;
  margin: 0;
}

/* 텍스트 드래그 방지 */
.no-drag {
  user-select: none; /* 텍스트 선택 불가 */
}

.header-right {
  display: flex;
  align-items: center; /* 세로 정렬 */
  gap: 1rem; /* 아이콘과 버튼 간격 */
}

.user-info {
  display: flex;
  align-items: center; /* 세로 정렬 */
  gap: 0.5rem; /* 아이콘과 이름 간격 */
}

.user-details {
  display: flex;
  flex-direction: column; /* 수직 정렬 */
  align-items: flex-start; /* 왼쪽 정렬 */
  gap: 0.2rem; /* 이름과 버튼 간격 */
}

/* 유저 아이콘 버튼 스타일 */
.user-icon-button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  display: flex;
  align-items: center;
}

.user-icon {
  font-size: 1.5rem; /* 아이콘 크기 */
  color: #333; /* 아이콘 색상 */
}

.user-name {
  font-size: 1rem;
  font-weight: bold;
  color: #333;
}

.logout-button {
  all: unset; /* 모든 기본 스타일 제거 */
  color: inherit; /* 부모 요소의 텍스트 색상 상속 */
  font: inherit; /* 부모 요소의 폰트 스타일 상속 */
  cursor: pointer; /* 클릭 가능한 포인터 유지 */
  font-size: 0.9rem; /* 로그아웃 버튼 크기 조정 */
}

.logout-button:hover {
  text-decoration: underline; /* 버튼에 마우스를 올리면 밑줄 표시 */
}
</style>
