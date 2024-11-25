<template>
  <div>
    <h1>로그인 중...</h1>
  </div>
</template>

<script setup>
import { useSignupStore } from "../stores/signup"; // Pinia store
import { useRouter, useRoute } from 'vue-router';
import axios from 'axios';

const signupStore = useSignupStore();
const router = useRouter();
const route = useRoute();

const code = route.query.code; // 인증 코드 추출

if (code) {
  console.log('인증 코드:', code);

  // 카카오 로그인 API로 POST 요청 보내기
  axios
    .post('/api/v1/user/login/kakao', {
      authorizationCode: code, // 인증 코드 전달
    })
    .then(response => {
      console.log('로그인 응답:', response.data, response.status);

      const nickname = response.data.data.nickname;
      localStorage.setItem("nickname", nickname); // 닉네임 저장
      signupStore.setNickname(nickname); // Pinia 상태 갱신

      if (response.status === 202) {
        router.push('/destination');
      } else if (response.status === 200) {
        router.push('/main');
      }
    })
    .catch(error => {
      console.error('로그인 실패:', error);
      alert('로그인 중 문제가 발생했습니다.');
      router.push('/');
    });
} else {
  console.error('인증 코드가 없습니다.');
  alert('로그인에 실패했습니다.');
  router.push('/'); // 실패 시 홈으로 이동
}
</script>
