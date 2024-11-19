<template>
  <div class="transportation-container">
    <h1>주로 이용하시는 이동수단이 무엇인가요?</h1>
    <div class="transportation-options">
      <button
        v-for="(option, index) in options"
        :key="index"
        :class="['option-button', { selected: selectedOption === option.name }]"
        @click="navigateToNextPage(option)"
      >
        <div class="button-content">
          <font-awesome-icon :icon="option.icon" /> <!-- 아이콘 컴포넌트 사용 -->
          <span>{{ option.name }}</span>
        </div>
        <span v-if="selectedOption === option.name" class="check-icon">✔</span>
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router"; // Vue Router 사용

// Font Awesome 아이콘 가져오기
import { faCar, faBus, faWalking } from "@fortawesome/free-solid-svg-icons";

const options = [
  { name: "자가용", icon: faCar },
  { name: "대중교통", icon: faBus },
  { name: "도보", icon: faWalking },
];

const selectedOption = ref(null);
const router = useRouter(); // 라우터 인스턴스

const navigateToNextPage = (option) => {
  selectedOption.value = option.name; // 선택된 항목 저장
  console.log(`${option.name} 선택됨`);
  router.push("/destination"); // 다음 페이지로 이동
};
</script>

<style scoped>
.transportation-container {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-family: Arial, sans-serif;
  text-align: center;
  background-color: #fff;
}

h1 {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 2rem;
}

.transportation-options {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  max-width: 300px;
}

.option-button {
  width: 100%;
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
  font-size: 1.2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  transition: background-color 0.3s;
}

.option-button:hover {
  background-color: #eee;
}

.button-content {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.option-button.selected {
  background-color: #ff6600;
  color: white;
  border-color: #ff6600;
}

.check-icon {
  position: absolute;
  right: 10px;
  font-size: 1.2rem;
  color: white;
}
</style>
