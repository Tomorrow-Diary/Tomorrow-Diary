<template>
  <div class="home-container">
    <h1>내일일기</h1>
    <p>간편하게 로그인하고 서비스를 이용하세요!</p>
    <div class="button-container">
      <button class="kakao-login" @click="kakaoLogin">카카오로 로그인하기</button>
      <button class="naver-login" @click="naverLogin">네이버로 로그인하기</button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter(); // 라우터 인스턴스

const kakaoLogin = () => {
  const clientId = '7c1cbc0f742966cf72cdda5cccdcc7ed'; // 본인의 REST API 키로 교체
  const redirectUri = 'http://localhost:5174/callback'; // 카카오 개발자 콘솔에 등록한 Redirect URI
  const authUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${clientId}&redirect_uri=${redirectUri}`;

  // 사용자를 카카오 인증 URL로 리다이렉트
  window.location.href = authUrl;
};

const naverLogin = () => {
  console.log("네이버 로그인 버튼 클릭"); // 네이버 로그인 로직 추가
  router.push("/destination");
};
</script>

<style scoped>
.home-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  width: 100vw; /* 화면 전체 너비 */
  margin: 0; /* 외부 여백 제거 */
  position: absolute; /* 정중앙 배치를 위해 필요 */
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); /* 정확히 중앙으로 이동 */
  background-color: #fff;
  font-family: Arial, sans-serif;
  text-align: center;
}

h1 {
  font-size: 2rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

p {
  font-size: 1rem;
  margin-bottom: 2rem;
  color: #555;
}

.button-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 200px;
}

button {
  padding: 0.8rem 1rem;
  font-size: 1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.kakao-login {
  background-color: #ffeb00;
  color: #3c1e1e;
}

.kakao-login:hover {
  background-color: #fdda00;
}

.naver-login {
  background-color: #1ec800;
  color: white;
}

.naver-login:hover {
  background-color: #19b000;
}
</style>
