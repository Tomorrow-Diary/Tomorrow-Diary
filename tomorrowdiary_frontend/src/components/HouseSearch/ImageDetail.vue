<template>
  <div class="image-popup" @click.self="closePopup">
    <div class="image-popup-content">
      <img class="popup-image" :src="image" alt="Diary Popup Image" />
      <p class="popup-time">{{ time }}</p>
      <h2 class="popup-title">{{ title }}</h2>
    </div>
  </div>
</template>

<script setup>
import { defineProps, defineEmits } from "vue";

const props = defineProps({
  image: String,
  time: String,
  title: String,
});

const emit = defineEmits(["close"]);

const closePopup = () => {
  emit("close");
};
</script>

<style scoped>
.image-popup {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.8); /* 어두운 배경 */
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2000;
}

.image-popup-content {
  background-color: white;
  padding: 2rem;
  border-radius: 10px;
  text-align: center;
  max-width: 90%;
  max-height: 90%;
}

.popup-image {
  max-height: 650px; /* 세로 길이 고정 */
  width: auto; /* 비율에 맞게 가로 크기 조정 */
  object-fit: contain;
  border-radius: 10px;
}

.popup-time {
  margin-top: 1rem;
  font-size: 1.2rem;
  font-weight: bold;
}

.popup-title {
  margin-top: 0.5rem;
  font-size: 1.5rem;
}
</style>
