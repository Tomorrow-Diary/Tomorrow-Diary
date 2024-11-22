<script setup>
import Header from "./Header.vue";
import MapSideBar from "../components/HouseSearch/MapSideBar.vue";
import { ref, onMounted } from "vue";

const currentDong = ref("위치 정보 로딩 중...");
let map; // 지도 객체 저장

// 지도 초기화
const initializeMap = () => {
  const container = document.getElementById("map"); // 지도를 표시할 div
  const defaultLocation = new window.kakao.maps.LatLng(37.5665, 126.9780); // 기본 위치 (서울 시청)
  const options = {
    center: defaultLocation,
    level: 3, // 지도 확대 레벨
  };

  map = new window.kakao.maps.Map(container, options);

  // 현재 위치 요청
  navigator.geolocation.getCurrentPosition(
    (position) => {
      const lat = position.coords.latitude;
      const lng = position.coords.longitude;
      const locPosition = new window.kakao.maps.LatLng(lat, lng);

      // 지도를 현재 위치로 이동
      map.setCenter(locPosition);

      // 현재 위치의 주소 변환
      updateDongInfo(lng, lat);
    },
    (error) => {
      // 현재 위치를 가져오지 못했을 경우 기본 위치 사용
      console.error("위치 정보를 가져올 수 없습니다: ", error.message);
      currentDong.value = "현재 위치를 가져올 수 없습니다.";
      updateDongInfo(defaultLocation.getLng(), defaultLocation.getLat());
    }
  );

  // 지도 드래그 후 동네 업데이트
  window.kakao.maps.event.addListener(map, "dragend", () => {
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

// 선택된 장소로 지도 이동
const moveToPlace = (place) => {
  const { x: lng, y: lat } = place; // place 객체에서 좌표 가져오기
  const locPosition = new window.kakao.maps.LatLng(lat, lng);

  // 지도의 중심 이동
  map.panTo(locPosition);

  // 새 위치의 주소 업데이트
  updateDongInfo(lng, lat);
};

onMounted(() => {
  initializeMap();
});
</script>

<template>
  <div class="map-container">
    <!-- Header -->
    <Header :class="{'with-background': true}" />

    <!-- MapSideBar 컴포넌트 -->
    <MapSideBar @placeSelected="moveToPlace" />

    <!-- 현재 위치 표시 -->
    <div class="location-info">
      <h3>{{ currentDong }}</h3>
    </div>

    <!-- 카카오 맵 -->
    <div id="map" class="map"></div>
  </div>
</template>



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
  padding: 0.5rem 1rem;
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
