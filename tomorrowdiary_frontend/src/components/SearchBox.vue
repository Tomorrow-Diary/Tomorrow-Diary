<template>
  <div class="search-container">
    <h1>어떤 집을 찾고 계세요?</h1>
    <div class="search-box">
      <input
        type="text"
        placeholder="시/도 또는 시/군/구 또는 읍/면/동을 입력하세요"
        v-model="keyword"
      />
      <button @click="search"><font-awesome-icon icon="magnifying-glass" /></button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const keyword = ref("");

const search = () => {
  if (!keyword.value) {
    alert("검색어를 입력해주세요!");
    return;
  }
  alert(`"${keyword.value}"로 검색합니다.`);
};
</script>

<style scoped>
.search-container {
  display: flex;
  flex-direction: column;
  align-items: center; /* 수평 가운데 정렬 */
  justify-content: center; /* 수직 가운데 정렬 */
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%); /* 정확히 중앙에 위치하도록 설정 */
  padding: 2rem;
  border-radius: 10px;
}

.search-container h1 {
  font-size: 3rem;
  color: #eeeeee;
  margin-bottom: 1rem;
}

.search-box {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  width: 100%;
}

.search-box input {
  width: 500px;
  padding: 0.8rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}

.search-box input::placeholder {
  text-align: center;
}

.search-box button {
  background-color: #ff6600;
  color: white;
  padding: 0.8rem 1rem;
  font-size: 1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.search-box button:hover {
  background-color: #e65c00;
}
</style>
