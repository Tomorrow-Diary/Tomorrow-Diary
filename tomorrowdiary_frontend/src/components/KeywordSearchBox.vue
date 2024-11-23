<template>
  <div class="keyword-search">
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
        class="place-item"
        @click="selectPlace(place)"
      >
        <div>
          <p class="place-name">{{ place.place_name }}</p>
          <p class="place-address">{{ place.road_address_name || place.address_name }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits } from "vue";

// 컴포넌트에서 부모로 데이터 전달
const emit = defineEmits(["placeSelected"]);

const keyword = ref(""); // 사용자 입력 키워드
const places = ref([]); // 검색 결과 목록

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

const selectPlace = (place) => {
  emit("placeSelected", place); // 부모 컴포넌트에 선택된 장소 전달
};
</script>

<style scoped>
/* 스타일은 기존과 동일 */
.keyword-search {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}

.search-box {
  display: flex;
  justify-content: center;
  align-items: center;
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

.places-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  width: 100%;
  max-width: 400px;
  max-height: 500px;
  overflow-y: auto;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.place-item {
  padding: 1rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.place-item:hover {
  background-color: #eee;
}

.place-name {
  font-size: 1.2rem;
  font-weight: bold;
}

.place-address {
  font-size: 1rem;
  color: #555;
}
</style>
