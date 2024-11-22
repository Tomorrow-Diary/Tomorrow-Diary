<template>
  <div :class="['sidebar', { expanded: isSidebarExpanded }]">
    <button class="toggle-button" @click="toggleSidebar">
      {{ isSidebarExpanded ? '<' : '>' }}
    </button>

    <!-- 사이드바 내용 (활성화 상태에서만 표시) -->
    <div v-show="isSidebarExpanded" class="sidebar-content">
      <!-- 편의시설 영역 -->
      <div class="facility-section">
        <div class="facility-header">
          <span>편의시설</span>
        </div>

        <!-- 편의시설 선택 목록 -->
        <div class="facility-options">
          <label
            v-for="facility in facilities"
            :key="facility.value"
            class="facility-item"
          >
            <input
              type="checkbox"
              :value="facility.value"
              v-model="selectedFacilities"
            />
            {{ facility.label }}
          </label>
        </div>
      </div>

      <!-- 검색 박스 -->
      <div class="search-box-wrapper">
        <KeywordSearchBox @searchUpdated="$emit('searchUpdated', $event)" @placeSelected="emitPlaceSelected" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from "vue";
import KeywordSearchBox from "../KeywordSearchBox.vue";

const emit = defineEmits(["placeSelected", "searchUpdated"]);

const isSidebarExpanded = ref(false); // 사이드바 확장 상태
const selectedFacilities = ref([]); // 선택된 편의시설

// 편의시설 목록
const facilities = [
  { label: "체육시설", value: "gym" },
  { label: "음식점", value: "restaurant" },
  { label: "여가시설", value: "leisure" },
  { label: "카페", value: "cafe" },
];

const toggleSidebar = () => {
  isSidebarExpanded.value = !isSidebarExpanded.value;
};

const emitPlaceSelected = (place) => {
  emit("placeSelected", place);
};
</script>



<style scoped>
.sidebar {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  background-color: white;
  box-shadow: 2px 0px 5px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: width 0.3s ease;
  width: 50px; /* 기본 너비 */
  z-index: 1001;
}

.sidebar.expanded {
  width: 500px; /* 확장된 너비 */
}

.toggle-button {
  position: absolute;
  top: 50%;
  right: 0;
  transform: translateY(-50%);
  border: none;
  width: 50px;
  height: 100%;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 30px;
}

.sidebar-content {
  padding-top: 5rem;
  padding-right: 50px;
  font-size: 1rem;
  color: #333;
  display: flex;
  flex-direction: column; /* 요소를 수직으로 정렬 */
  align-items: center; /* 수평 중앙 정렬 */
  gap: 1rem; /* 섹션 간 간격 */
}

/* 편의시설 섹션 */
.facility-section {
  width: 90%; /* 부모의 90% 너비 */
  background-color: #f9f9f9; /* 연한 배경색 */
  padding: 1rem;
  border-radius: 10px;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1); /* 부드러운 그림자 */
}

.facility-header {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 1rem;
  text-align: center;
}

.facility-options {
  display: flex;
  flex-wrap: wrap; /* 줄바꿈 허용 */
  justify-content: center; /* 수평 중앙 정렬 */
  align-items: center;
  gap: 0.5rem; /* 아이템 간 간격 */
}

.facility-item {
  display: flex;
  align-items: center;
  gap: 0.3rem;
  font-size: 0.9rem;
  background-color: #fff;
  padding: 0.5rem 0.9rem;
  border-radius: 5px;
  box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
}

.facility-options input {
  margin: 0;
}

/* 검색 박스 크기 조정 */
.search-box-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 1rem 0;
  box-sizing: border-box;
  overflow: hidden; /* 바깥으로 내용이 넘치지 않도록 설정 */
  width: 100%; /* 부모 너비에 맞춤 */
  height: auto; /* 높이를 부모 컨테이너에 비례 */

  width: 90%; /* 부모의 90% 너비 */
  background-color: #f9f9f9; /* 연한 배경색 */
  padding: 1rem;
  border-radius: 10px;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1); /* 부드러운 그림자 */
}
</style>
