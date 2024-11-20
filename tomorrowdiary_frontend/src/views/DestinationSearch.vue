<template>
  <div class="destination-container">
    <h1>주로 방문하는 목적지를 입력하세요!</h1>
    <div class="search-box">
      <input
        type="text"
        v-model="keyword"
        placeholder="이름으로 검색해주세요"
        @keyup.enter="searchPlaces"
      />
      <button @click="searchPlaces"><font-awesome-icon icon="magnifying-glass" /></button>
    </div>
    <div v-if="places.length > 0" class="places-list"> <!-- 검색 결과 있을 때만 표시 -->
      <div
        v-for="(place, index) in places"
        :key="index"
        :class="['place-item', { selected: selectedPlace === place }]"
        @click="navigateToNextPage(place)"
      >
        <div>
          <p class="place-name">{{ place.place_name }}</p>
          <p class="place-address">{{ place.road_address_name || place.address_name }}</p>
        </div>
        <span v-if="selectedPlace === place" class="check-icon">✔</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router"; // Vue Router 사용

const keyword = ref(""); // 사용자 입력 키워드
const places = ref([]); // 검색 결과 목록
const router = useRouter(); // 라우터 인스턴스

const searchPlaces = () => {
  if (!window.kakao || !window.kakao.maps) {
    alert("Kakao Maps API가 로드되지 않았습니다. API 키를 확인해주세요.");
    return;
  }

  if (!keyword.value) {
    alert("검색어를 입력해주세요!");
    return;
  }

  const ps = new window.kakao.maps.services.Places();

  ps.keywordSearch(keyword.value, (data, status) => {
    if (status === window.kakao.maps.services.Status.OK) {
      places.value = data; // 검색 결과 저장
    } else if (status === window.kakao.maps.services.Status.ZERO_RESULT) {
      alert("검색 결과가 없습니다.");
      places.value = [];
    } else {
      alert("검색 중 오류가 발생했습니다.");
    }
  });
};

// 검색된 목록 클릭 시 라우팅
const navigateToNextPage = (place) => {
  console.log(`선택된 장소: ${place.place_name}`);
  router.push("/transportations"); // /transportations 경로로 이동
};
</script>

<style scoped>
.destination-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start; /* 상단 정렬 */
  height: 100vh;
  width: 100vw;
  position: fixed; /* fixed를 사용하여 부모의 영향을 받지 않도록 설정 */
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  font-family: Arial, sans-serif;
  text-align: center;
  overflow: hidden; /* 검색 결과가 부모를 넘지 않도록 설정 */
}

h1 {
  font-size: 1.5rem;
  font-weight: bold;
  margin-top: 10%;
}

.search-box {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 2rem;
  gap: 0.5rem;
}

input {
  width: 300px;
  padding: 0.8rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}

button {
  background-color: #ff6600;
  color: white;
  padding: 0.8rem 1.5rem;
  font-size: 1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #e65c00;
}

/* 검색 결과의 최대 높이 제한 */
.places-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
  width: 100%;
  max-width: 400px;
  max-height: 500px; /* 최대 높이를 500px로 제한 */
  overflow-y: auto; /* 초과된 내용은 스크롤 가능 */
  padding: 0.5rem; /* 내부 여백 추가 */
  border: 1px solid #ccc; /* 테두리로 영역 강조 */
  border-radius: 5px;
}

.place-item {
  width: 100%;
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  text-align: left;
  display: flex;
  justify-content: space-between; /* 텍스트와 체크 아이콘 간격 확보 */
  align-items: center;
  cursor: pointer;
  transition: background-color 0.3s;
}

.place-item > div {
  flex-grow: 1; /* 텍스트가 체크 아이콘과 겹치지 않도록 확장 */
}

.place-item:hover {
  background-color: #eee;
}

.place-item.selected {
  background-color: #ff6600; /* 선택된 항목 배경색 */
  color: white; /* 선택된 항목 텍스트 색상 */
  border-color: #ff6600; /* 선택된 항목 테두리 색상 */
}

.check-icon {
  font-size: 1.2rem;
  color: white; /* 선택된 항목의 체크 표시 색상 */
  margin-left: 10px;
}

.place-name {
  font-size: 1.2rem;
  font-weight: bold;
}

.place-address {
  font-size: 1rem;
  color: #555;
  margin-top: 0.3rem; /* 이름과 주소 사이의 간격 */
}
</style>
