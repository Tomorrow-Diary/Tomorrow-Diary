<template>
  <div class="main-page">
    <!-- 헤더 -->
    <Header />
    <!-- 배경 이미지 -->
    <img src="/mainImage.jpg" alt="배경 이미지" class="background-image" />
    <!-- 검색창 컴포넌트 -->
    <SearchBox />
  </div>
</template>

<script setup>
import Header from "./Header.vue";
import SearchBox from "../components/SearchBox.vue";
</script>

<style scoped>
/* 부모 컨테이너 */
.main-page {
  overflow-x: hidden; /* 가로 스크롤 방지 */
  overflow-y: hidden; /* 세로 스크롤 방지 */
}

/* 배경 이미지 */
.background-image {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%; /* 부모 너비에 맞춤 */
  height: 100%; /* 부모 높이에 맞춤 */
  object-fit: cover; /* 이미지 비율 유지하며 화면 채우기 */
  z-index: -1; /* 배경으로 이동 */
}
</style>
