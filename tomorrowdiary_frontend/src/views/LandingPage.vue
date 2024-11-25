<template>
  <div class="landing-container">
    <h1>내일의 일상, 내일일기</h1>
    <p>이곳에서 내 생활을 상상해보세요!</p>
    <button @click="start">시작하기</button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const start = () => {
  router.push("/"); // "/" 경로로 이동
};
</script>

<style scoped>
.landing-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  width: 100vw; /* 추가: 화면 너비를 100%로 설정 */
  margin: 0; /* 추가: 외부 여백 제거 */
  background-color: #fff;
  font-family: Arial, sans-serif;
  text-align: center;
  position: absolute; /* 추가: 정중앙 배치를 보장 */
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

h1 {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

p {
  font-size: 1rem;
  margin-bottom: 2rem;
}

button {
  background-color: #ff6600;
  color: white;
  padding: 0.8rem 2rem;
  font-size: 1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #e65c00;
}
</style>
