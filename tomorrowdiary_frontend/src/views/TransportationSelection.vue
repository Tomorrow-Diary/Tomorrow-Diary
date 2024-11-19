<template>
  <div class="transportation-container">
    <h1>주로 이용하시는 이동수단이 무엇인가요?</h1>
    <div class="transportation-options">
      <button
        v-for="(option, index) in options"
        :key="index"
        :class="['option-button', { selected: selectedOption === option }]"
        @click="selectOption(option)"
      >
        <i :class="option.icon"></i> {{ option.name }}
        <!-- 선택된 버튼에 체크 아이콘 표시 -->
        <span v-if="selectedOption === option" class="check-icon">✔</span>
      </button>
    </div>
    <button
      v-if="selectedOption"
      class="register-button"
      @click="register"
    >
      회원가입
    </button>
  </div>
</template>


<script setup>
import { ref } from "vue";

const options = [
  { name: "자가용", icon: "fa fa-car" },
  { name: "대중교통", icon: "fa fa-bus" },
  { name: "도보", icon: "fa fa-walking" },
];

const selectedOption = ref(null);

const selectOption = (option) => {
  selectedOption.value = option;
};

const register = () => {
  alert(`${selectedOption.value.name}을 선택하셨습니다! 회원가입 진행.`);
};
</script>

<style scoped>
.transportation-container {
  position: fixed; /* 화면에 고정 */
  top: 50%; /* 화면의 수직 중앙 */
  left: 50%; /* 화면의 수평 중앙 */
  transform: translate(-50%, -50%); /* 요소 크기를 고려한 정확한 중앙 정렬 */
  display: flex;
  flex-direction: column;
  align-items: center; /* 수평 가운데 정렬 */
  justify-content: center; /* 수직 가운데 정렬 */
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
  width: 100%; /* 버튼이 부모의 너비만큼 차지 */
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
  font-size: 1.2rem;
  display: flex;
  align-items: center; /* 아이콘과 텍스트를 수직 가운데 정렬 */
  justify-content: center; /* 아이콘과 텍스트를 수평 가운데 정렬 */
  gap: 0.5rem;
  cursor: pointer;
  transition: background-color 0.3s;
}

.option-button:hover {
  background-color: #eee;
}

.option-button.selected {
  background-color: #ff6600;
  color: white;
  border-color: #ff6600;
}

.register-button {
  margin-top: 2rem;
  padding: 1rem 2rem;
  font-size: 1.2rem;
  background-color: #ff6600;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.register-button:hover {
  background-color: #e65c00;
}

.register-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

</style>
