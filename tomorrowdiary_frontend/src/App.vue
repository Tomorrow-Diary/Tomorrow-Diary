<script setup>
import { RouterView } from 'vue-router'
import { useSignupStore } from "./stores/signup";

const signupStore = useSignupStore();

// 앱 초기화 시 LocalStorage에서 닉네임 복원
const storedNickname = localStorage.getItem("nickname");
if (storedNickname) {
  signupStore.setNickname(storedNickname); // Pinia 상태 갱신
}
</script>

<template>
  <RouterView />
</template>

<style>
/* Pretendard 폰트 로드 */
@import "pretendard/dist/web/static/pretendard.css";

/* 글로벌 폰트 설정 */
body {
  font-family: 'Pretendard', -apple-system, BlinkMacSystemFont, system-ui, Roboto, 'Helvetica Neue', 'Segoe UI', Arial, 'Noto Sans', sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol', 'Noto Color Emoji';
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
</style>
