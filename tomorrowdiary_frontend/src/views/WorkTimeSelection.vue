<template>
  <div class="time-selection-container">
    <h1>출근 시간과 퇴근 시간을 입력하세요!</h1>

    <!-- 출근 시간 입력 -->
    <div class="time-input">
      <label>출근 시간</label>
      <div class="time-selection">
        <select v-model="startPeriod" @change="checkInputs">
          <option value="AM">오전</option>
          <option value="PM">오후</option>
        </select>
        <select v-model="startHour" @change="checkInputs">
          <option disabled value="">시간</option>
          <option v-for="hour in hours" :key="hour" :value="hour">{{ hour }}시</option>
        </select>
        <select v-model="startMinute" @change="checkInputs">
          <option disabled value="">분</option>
          <option v-for="minute in minutes" :key="minute" :value="minute">{{ minute }}분</option>
        </select>
      </div>
    </div>

    <!-- 퇴근 시간 입력 -->
    <div class="time-input">
      <label>퇴근 시간</label>
      <div class="time-selection">
        <select v-model="endPeriod" @change="checkInputs">
          <option disabled value="">오전/오후</option>
          <option value="AM">오전</option>
          <option value="PM">오후</option>
        </select>
        <select v-model="endHour" @change="checkInputs">
          <option disabled value="">시간</option>
          <option v-for="hour in hours" :key="hour" :value="hour">{{ hour }}시</option>
        </select>
        <select v-model="endMinute" @change="checkInputs">
          <option disabled value="">분</option>
          <option v-for="minute in minutes" :key="minute" :value="minute">{{ minute }}분</option>
        </select>
      </div>
    </div>

    <!-- 회원가입 버튼 -->
    <button
      class="register-button"
      :class="{ hidden: !canRegister }"
      @click="register"
    >
      회원가입
    </button>
  </div>
</template>

<script setup>
import { ref } from "vue";

// 시간 및 분 선택 값
const hours = Array.from({ length: 12 }, (_, i) => i + 1); // 1~12
const minutes = [0, 15, 30, 45]; // 15분 단위

// 출근 시간 - 기본 오전 설정
const startPeriod = ref("AM"); // 기본값: 오전
const startHour = ref(""); // 1~12
const startMinute = ref(""); // 0, 15, 30, 45

// 퇴근 시간 - 기본 오후 설정
const endPeriod = ref("PM"); // 기본값: 오후
const endHour = ref(""); // 1~12
const endMinute = ref(""); // 0, 15, 30, 45

// 회원가입 버튼 표시 여부
const canRegister = ref(false);

// 입력값 확인
const checkInputs = () => {
  canRegister.value =
    startPeriod.value &&
    startHour.value &&
    startMinute.value !== "" &&
    endPeriod.value &&
    endHour.value &&
    endMinute.value !== "";
};

// 회원가입 버튼 클릭 이벤트
const register = () => {
  const startTime = createDate(startPeriod.value, startHour.value, startMinute.value);
  const endTime = createDate(endPeriod.value, endHour.value, endMinute.value);

  alert(`출근 시간: ${startTime.toLocaleString()}, 퇴근 시간: ${endTime.toLocaleString()} 회원가입 진행.`);
};

// 시간을 Date 객체로 생성하는 함수
const createDate = (period, hour, minute) => {
  const now = new Date(); // 현재 날짜
  let hours = parseInt(hour, 10); // 입력된 시간

  // 오전/오후에 따라 시간 변환
  if (period === "PM" && hours !== 12) {
    hours += 12; // 오후면 12시간 추가
  } else if (period === "AM" && hours === 12) {
    hours = 0; // 오전 12시는 0으로 설정
  }

  // 새로운 Date 객체 반환
  return new Date(now.getFullYear(), now.getMonth(), now.getDate(), hours, parseInt(minute, 10));
};

</script>

<style scoped>
.time-selection-container {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  flex-direction: column;
  align-items: center;
  font-family: Arial, sans-serif;
  text-align: center;
  background-color: #fff;
}

h1 {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 2rem;
}

.time-input {
  margin-bottom: 1.5rem;
  display: flex;
  flex-direction: column;
  align-items: center;
}

label {
  font-size: 1rem;
  margin-bottom: 0.5rem;
}

.time-selection {
  display: flex;
  align-items: center;
  gap: 1rem;
}

select {
  padding: 0.5rem;
  font-size: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 100px;
}

/* 회원가입 버튼 */
.register-button {
  margin-top: 2rem;
  padding: 1rem 2rem;
  font-size: 1.2rem;
  background-color: #ff6600;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: opacity 0.3s ease, visibility 0.3s ease;
  visibility: hidden; /* 기본적으로 보이지 않음 */
  opacity: 0; /* 투명도 설정 */
}

.register-button.hidden {
  visibility: hidden; /* 여전히 공간 차지, 보이지 않음 */
  opacity: 0;
}

.register-button:not(.hidden) {
  visibility: visible; /* 보이도록 설정 */
  opacity: 1;
}

.register-button:hover {
  background-color: #e65c00;
}
</style>
