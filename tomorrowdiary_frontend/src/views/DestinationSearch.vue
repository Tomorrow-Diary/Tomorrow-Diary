<template>
  <div class="destination-container">
    <h1>주로 방문하는 목적지를 입력하세요!</h1>
    <KeywordSearch @placeSelected="handlePlaceSelected" />
  </div>
</template>

<script setup>
import KeywordSearch from "../components/KeywordSearchBox.vue";
import { useRouter } from "vue-router";

const router = useRouter();

const handlePlaceSelected = (place) => {
  console.log(`선택된 장소: ${place.place_name}`);
  // 다음 페이지로 이동
  router.push("/transportations");
};
</script>

<style scoped>
.destination-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  height: 100vh;
  width: 100vw;
  font-family: Arial, sans-serif;
  text-align: center;
}

h1 {
  font-size: 1.5rem;
  font-weight: bold;
  margin-top: 10%;
}
</style>
