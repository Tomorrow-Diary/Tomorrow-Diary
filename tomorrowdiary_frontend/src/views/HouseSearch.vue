<template>
  <div class="map-container">
    <!-- Header -->
    <Header :class="{'with-background': true}" />

    <!-- MapSideBar 컴포넌트 -->
    <MapSideBar
      v-show="isSidebarVisible"
      :searchResults="searchResults"
      @searchUpdated="updateSearchResults"
      @placeSelected="moveToPlace"
    />

    <!-- 현재 위치 표시 -->
    <div class="location-info">
      <h3>{{ currentDong }}</h3>
    </div>

    <!-- 카카오 맵 -->
    <div id="map" class="map"></div>

    <!-- HouseDetail 컴포넌트 -->
    <HouseDetail
      v-if="houseDetailVisible"
      :name="houseDetail.name"
      :address="houseDetail.address"
      :roadAddress="houseDetail.roadAddress"
      :transactions="houseDetail.transactions"
      :constructionYear="houseDetail.constructionYear"
      @close="houseDetailVisible = false"
      @createDiary="showDiary"
    />

    <!-- Diary 컴포넌트 -->
    <Diary v-if="diaryVisible" @close="diaryVisible = false" />
  </div>
</template>

<script setup>
import Header from "./Header.vue";
import MapSideBar from "../components/HouseSearch/MapSideBar.vue";
import HouseDetail from "../components/HouseSearch/HouseDetail.vue";
import Diary from "../components/HouseSearch/Diary.vue";
import { ref, onMounted } from "vue";

const currentDong = ref("위치 정보 로딩 중...");
const isSidebarVisible = ref(true); // 사이드바 표시 상태
const houseDetailVisible = ref(false); // HouseDetail 표시 상태
const diaryVisible = ref(false); // Diary 표시 상태
const searchResults = ref([]); // 검색 결과를 유지
const houseDetail = ref({
  name: "",
  address: "",
  roadAddress: "",
  constructionYear: "2005",
  transactions: [
    { date: "2004-04-06", price: "20억 원", area: "59.606", floor: "13" },
    { date: "2004-04-06", price: "20억 원", area: "59.606", floor: "13" },
  ],
});
let map; // 지도 객체 저장
let marker;

// 지도 초기화
const initializeMap = () => {
  const container = document.getElementById("map"); // 지도를 표시할 div
  const defaultLocation = new window.kakao.maps.LatLng(37.5665, 126.9780); // 기본 위치 (서울 시청)
  const options = {
    center: defaultLocation,
    level: 3, // 지도 확대 레벨
  };

  map = new window.kakao.maps.Map(container, options);

  // 초기 마커 생성
  marker = new window.kakao.maps.Marker({
    position: defaultLocation,
    map: map,
  });

  // 마커 클릭 이벤트
  marker.addListener("click", () => {
    houseDetailVisible.value = true;
  });

  updateDongInfo(defaultLocation.getLng(), defaultLocation.getLat());

  // 지도 드래그 후 동네 업데이트
  map.addListener("dragend", () => {
    const center = map.getCenter();
    updateDongInfo(center.getLng(), center.getLat());
  });
};

// 동네 정보 업데이트 함수
const updateDongInfo = (lng, lat) => {
  const geocoder = new window.kakao.maps.services.Geocoder();
  geocoder.coord2Address(lng, lat, (result, status) => {
    if (status === window.kakao.maps.services.Status.OK) {
      currentDong.value = result[0]?.address?.region_3depth_name || "주소 정보 없음";
    }
  });
};

// 선택된 장소로 지도 이동 및 마커 업데이트
const moveToPlace = (place) => {
  const { x: lng, y: lat } = place; // place 객체에서 좌표 가져오기
  const locPosition = new window.kakao.maps.LatLng(lat, lng);

  // 지도의 중심 이동
  map.panTo(locPosition);

  // 마커 위치 업데이트
  marker.setPosition(locPosition);

  // 마커 정보 업데이트 (HouseDetail 갱신은 클릭 시)
  marker.setMap(map);

  // 마커 클릭 시 HouseDetail 정보 설정
  marker.addListener("click", () => {
    houseDetail.value.name = place.place_name || "아파트 이름 없음";
    houseDetail.value.address = place.address_name || "주소 정보 없음";
    houseDetail.value.roadAddress = place.road_address_name || "도로명 주소 없음";
    houseDetailVisible.value = true;
  });
};

// 검색 결과 업데이트 함수
const updateSearchResults = (results) => {
  searchResults.value = results; // 검색 결과를 부모 컴포넌트에서 유지
};

// Diary 표시 함수
const showDiary = () => {
  diaryVisible.value = true;
};

onMounted(() => {
  initializeMap();
});
</script>

<style scoped>
.map-container {
  position: relative;
  width: 100vw;
  height: 100vh;
  display: flex;
}

.location-info {
  position: absolute;
  top: 100px; /* Header 바로 아래 */
  left: 50%; /* 가로 중앙 정렬 */
  transform: translateX(-50%); /* 정확히 가운데 정렬 */
  background-color: rgba(255, 255, 255, 0.8);
  padding: 1rem 2rem;
  border-radius: 10px;
  font-size: 1.2rem;
  font-weight: bold;
  z-index: 1000;
  text-align: center; /* 텍스트 가운데 정렬 */
}

.map {
  flex: 1;
  height: 100%;
  z-index: 0;
}

/* 헤더에 흰색 배경 추가 */
.with-background {
  background-color: white;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1); /* 약간의 그림자 */
  z-index: 1002; /* 사이드바보다 높게 설정 */
}
</style>
