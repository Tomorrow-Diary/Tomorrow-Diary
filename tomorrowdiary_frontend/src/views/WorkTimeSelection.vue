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
import { useSignupStore } from "../stores/signup";
import { useRouter } from "vue-router";

const hours = Array.from({ length: 12 }, (_, i) => i + 1);
const minutes = [0, 15, 30, 45];

const startPeriod = ref("AM");
const startHour = ref("");
const startMinute = ref("");

const endPeriod = ref("PM");
const endHour = ref("");
const endMinute = ref("");

const canRegister = ref(false);
const signupStore = useSignupStore();
const router = useRouter();

const checkInputs = () => {
  canRegister.value =
    startPeriod.value &&
    startHour.value &&
    startMinute.value !== "" &&
    endPeriod.value &&
    endHour.value &&
    endMinute.value !== "";
};

const register = () => {
  const startTime = formatTime(startPeriod.value, startHour.value, startMinute.value);
  const endTime = formatTime(endPeriod.value, endHour.value, endMinute.value);
  signupStore.setWorkTimes(startTime, endTime);

  // 회원가입 API 요청
  fetch("/api/v1/user/signup", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      roadAddress: signupStore.roadAddress,
      latitude: signupStore.latitude,
      longitude: signupStore.longitude,
      transportType: signupStore.transportType,
      startTime: signupStore.startTime,
      endTime: signupStore.endTime,
    }),
  })
    .then((res) => res.json())
    .then((data) => {
      if (data.status === "success") {
        alert("회원가입 성공!");
        router.push("/map");
      } else {
        alert("회원가입 실패: " + data.message);
      }
    })
    .catch((err) => {
      console.error("회원가입 에러:", err);
      alert("회원가입 중 문제가 발생했습니다.");
    });
};

const formatTime = (period, hour, minute) => {
  let hours = parseInt(hour, 10);
  if (period === "PM" && hours !== 12) {
    hours += 12;
  } else if (period === "AM" && hours === 12) {
    hours = 0;
  }
  return `${String(hours).padStart(2, "0")}:${String(minute).padStart(2, "0")}:00`;
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
