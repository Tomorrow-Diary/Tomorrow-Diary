<template>
  <div class="map-container">
    <Header :class="{'with-background': true}" />

    <MapSideBar
      v-show="isSidebarVisible"
      @facilityToggled="handleFacilityToggled"
    />

    <div class="location-info">
      <h3>{{ currentDong }}</h3>
    </div>

    <div id="map" class="map"></div>
  </div>
</template>

<script setup>
import Header from "./Header.vue";
import MapSideBar from "../components/HouseSearch/MapSideBar.vue";
import { ref, onMounted } from "vue";
import axios from "axios";

const currentDong = ref("위치 정보 로딩 중...");
const isSidebarVisible = ref(true);
const map = ref(null);
const markers = ref({});
const selectedFacilities = ref([]);

// 아이콘 맵 정의 (Font Awesome 클래스)
const iconMap = {
  체육시설: "fas fa-dumbbell",
  음식점: "fas fa-utensils",
  여가시설: "fas fa-film",
  카페: "fas fa-coffee",
};

// 지도 초기화
const initializeMap = (lat, lng) => {
  const container = document.getElementById("map");
  const defaultLocation = new window.kakao.maps.LatLng(lat, lng);
  const options = {
    center: defaultLocation,
    level: 3,
  };

  map.value = new window.kakao.maps.Map(container, options);

  // 초기 동네 정보 로드
  updateDongInfo(defaultLocation.getLng(), defaultLocation.getLat());

  // 지도 이벤트 설정
  setupMapEvents();
};

// 동네 정보 업데이트
const updateDongInfo = (lng, lat) => {
  const geocoder = new window.kakao.maps.services.Geocoder();
  geocoder.coord2Address(lng, lat, (result, status) => {
    if (status === window.kakao.maps.services.Status.OK) {
      currentDong.value = result[0]?.address?.region_3depth_name || "주소 정보 없음";
    }
  });
};

// 지도 이동 및 동네 정보 갱신
const setupMapEvents = () => {
  window.kakao.maps.event.addListener(map.value, "dragend", () => {
    const center = map.value.getCenter();
    updateDongInfo(center.getLng(), center.getLat());
    updateFacilityMarkers(); // 지도 이동 시 편의시설 갱신
  });
};

// 모든 선택된 편의시설 요청
const fetchAllFacilities = async () => {
  if (!map.value) return;

  // 기존 마커 제거
  clearAllMarkers();

  const bounds = map.value.getBounds();
  const minLatitude = bounds.getSouthWest().getLat();
  const minLongitude = bounds.getSouthWest().getLng();
  const maxLatitude = bounds.getNorthEast().getLat();
  const maxLongitude = bounds.getNorthEast().getLng();

  for (const category of selectedFacilities.value) {
    try {
      const response = await axios.get(`/api/v1/facility`, {
        params: { category, minLatitude, minLongitude, maxLatitude, maxLongitude },
      });

      if (response.data.status === "success") {
        addFacilityMarkers(category, response.data.data);
      }
    } catch (error) {
      console.error(`${category} 편의시설 데이터 요청 실패:`, error);
    }
  }
};

// 마커 추가
const addFacilityMarkers = (category, facilities) => {
  if (!markers.value[category]) {
    markers.value[category] = [];
  }

  facilities.forEach((facility) => {
    const markerPosition = new window.kakao.maps.LatLng(facility.latitude, facility.longitude);

    // 커스텀 오버레이 생성
    const iconClass = iconMap[category] || "fas fa-map-marker-alt"; // 기본 아이콘
    const overlayContent = document.createElement("div");
    overlayContent.innerHTML = `
      <div style="display: flex; justify-content: center; align-items: center; width: 40px; height: 40px; background-color: white; border-radius: 50%; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); cursor: pointer;">
        <i class="${iconClass}" style="font-size: 18px; color: #333;"></i>
      </div>
    `;

    const markerOverlay = new window.kakao.maps.CustomOverlay({
      position: markerPosition,
      content: overlayContent,
      map: map.value,
    });

    const nameOverlayContent = document.createElement("div");
    nameOverlayContent.innerHTML = `
      <div style="background-color: white; padding: 5px 10px; border-radius: 5px; box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2); font-size: 12px; color: #333;">
        ${facility.name}
      </div>
    `;
    var nameOverlay

    overlayContent.addEventListener("mouseenter", () => {
      const mapLevel = map.value.getLevel(); // 현재 지도 레벨 가져오기
      const zoomFactor = 0.0005 * (1.05 ** mapLevel); // 레벨에 따라 배율 조정

      nameOverlay = new window.kakao.maps.CustomOverlay({
        position: new window.kakao.maps.LatLng(markerPosition.getLat() + zoomFactor, markerPosition.getLng()),
        content: nameOverlayContent,
        map: null, // 초기에는 숨김
      });

      nameOverlay.setMap(map.value); // 이름 표시
    });

    overlayContent.addEventListener("mouseleave", () => {
      nameOverlay.setMap(null); // 이름 숨김
    });


    markers.value[category].push({ markerOverlay, nameOverlay });
  });
};

// 모든 마커 제거
const clearAllMarkers = () => {
  for (const category in markers.value) {
    markers.value[category].forEach(({ markerOverlay, nameOverlay }) => {
      markerOverlay.setMap(null);
      if(nameOverlay){
        nameOverlay.setMap(null);
      }
    });
    markers.value[category] = [];
  }
};

// 선택된 카테고리 기반 마커 업데이트
const updateFacilityMarkers = () => {
  clearAllMarkers();
  fetchAllFacilities();
};

// 편의시설 카테고리 변경 처리
const handleFacilityToggled = (categories) => {
  selectedFacilities.value = categories;
  updateFacilityMarkers();
};

onMounted(() => {
  initializeMap(37.5665, 126.9780);
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
  top: 100px;
  left: 50%;
  transform: translateX(-50%);
  background-color: rgba(255, 255, 255, 0.8);
  padding: 1rem 2rem;
  border-radius: 10px;
  font-size: 1.2rem;
  font-weight: bold;
  z-index: 1000;
  text-align: center;
}

.map {
  flex: 1;
  height: 100%;
  z-index: 0;
}

.with-background {
  background-color: white;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
  z-index: 1002;
}
</style>
