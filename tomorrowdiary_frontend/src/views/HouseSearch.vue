<template>
  <div class="map-container">
    <Header :class="{'with-background': true}" />

    <!-- 지도 사이드바 -->
    <MapSideBar
      v-show="isSidebarVisible"
      @facilityToggled="handleFacilityToggled"
      @placeSelected="moveToPlace"
    />

    <!-- 현재 위치 정보 -->
    <div class="location-info">
      <h3>{{ currentDong }}</h3>
    </div>

    <!-- 지도 -->
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
      @createDiary="showDiary(houseDetail)"
    />
    <Diary
      v-if="diaryVisible"
      :facilities="selectedFacilities"
      :latitude="houseDetail.latitude"
      :longitude="houseDetail.longitude"
      @close="diaryVisible = false"
    />
  </div>
</template>

<script setup>
import Header from "./Header.vue";
import MapSideBar from "../components/HouseSearch/MapSideBar.vue";
import HouseDetail from "../components/HouseSearch/HouseDetail.vue";
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";
import Diary from '@/components/HouseSearch/Diary.vue'

const route = useRoute();
const currentDong = ref("위치 정보 로딩 중...");
const isSidebarVisible = ref(true);
const houseDetailVisible = ref(false);
const houseDetail = ref({
  name: "",
  address: "",
  roadAddress: "",
  constructionYear: "",
  transactions: [],
  latitude: 0, // 위도
  longitude: 0, // 경도
});

const diaryVisible = ref(false);
let houseMarkers = []
let overlays = [];
let facilityMarkers = ref({});
const map = ref(null);

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
  const defaultLocation = new window.window.kakao.maps.LatLng(lat, lng);
  const options = {
    center: defaultLocation,
    level: 3,
  };

  map.value = new window.window.kakao.maps.Map(container, options);

  // 초기 동네 정보 로드
  updateDongInfo(defaultLocation.getLng(), defaultLocation.getLat());

  // 지도 이벤트 설정
  setupMapEvents();
};

// 동네 정보 업데이트
const updateDongInfo = (lng, lat) => {
  const geocoder = new window.window.kakao.maps.services.Geocoder();
  geocoder.coord2Address(lng, lat, (result, status) => {
    if (status === window.window.kakao.maps.services.Status.OK) {
      const address = result[0]?.address;
      currentDong.value = address?.region_3depth_name || "주소 정보 없음";
    }
  });
};

// 지도 이동 및 동네 정보 갱신
const setupMapEvents = () => {
  window.window.kakao.maps.event.addListener(map.value, "dragend", () => {
    const center = map.value.getCenter();
    updateDongInfo(center.getLng(), center.getLat());
    updateFacilityMarkers(); // 드래그 후 편의시설 마커 갱신
  });
};

// 선택된 장소로 지도 이동
const moveToPlace = (place) => {
  const { x: lng, y: lat } = place; // place 객체에서 좌표 가져오기
  const locPosition = new window.kakao.maps.LatLng(lat, lng);

  // 지도의 중심 이동
  map.value.setCenter(locPosition);

  // 새 위치의 주소 업데이트
  updateDongInfo(lng, lat);
};

// 검색 결과 기반 지도 초기화 및 매매 정보 로드
const initializeWithSearchResult = async () => {
  const searchResult = route.query.searchResult;

  if (searchResult) {
    try {
      const { sidoName, gugunName, dongName, dongCode } = JSON.parse(searchResult);

      const query = `${sidoName} ${gugunName} ${dongName}`;
      performAddressSearch(query, (lat, lng) => {
        initializeMap(lat, lng);
        fetchHouseData(dongCode);
      });
    } catch (error) {
      console.error("searchResult 파싱 실패:", error);
    }
  }
};

// 주소를 좌표로 변환
const performAddressSearch = (query, callback) => {
  const geocoder = new window.window.kakao.maps.services.Geocoder();
  geocoder.addressSearch(query, (result, status) => {
    if (status === window.window.kakao.maps.services.Status.OK) {
      const { y: lat, x: lng } = result[0];
      callback(parseFloat(lat), parseFloat(lng));
    } else {
      console.error("주소 검색 실패");
    }
  });
};

// 동네 매매 정보 요청 및 마커 추가
const fetchHouseData = async (dongCode) => {
  try {
    const response = await axios.get(`/api/v1/house`, {
      params: { dongcode: dongCode },
      withCredentials: true,
    });
    if (response.data.status === "success") {
      const houses = response.data.data.houseResponseList;
      addHouseMarkers(houses);
    }
  } catch (error) {
    console.error("아파트 데이터 요청 실패:", error);
  }
};

// 아파트 상세 정보 요청
const fetchHouseDetail = async (aptSeq) => {
  try {
    const response = await axios.get(`/api/v1/house/${aptSeq}`, {
      withCredentials: true,
    });
    if (response.data.status === "success") {
      const data = response.data.data;
      houseDetail.value = {
        ...houseDetail.value,
        name: data.name,
        address: data.jibunAddress,
        roadAddress: data.roadAddress,
        constructionYear: String(data.buildYear),
        transactions: data.houseDealInfoList.map((deal) => ({
          date: deal.dealDate,
          price: `${deal.dealAmount} 만원`,
          area: `${deal.userArea} m²`,
          floor: deal.floor,
        })),
      };
      houseDetailVisible.value = true;
    }
  } catch (error) {
    console.error("아파트 상세 정보 요청 실패:", error);
  }
};

// 매매 정보 마커 추가 함수
const addHouseMarkers = (houses) => {
  // 기존 마커 및 오버레이 제거
  houseMarkers.forEach((marker) => marker.setMap(null));
  overlays.forEach((overlay) => overlay.setMap(null));
  houseMarkers = [];
  overlays = [];

  const sortedHouses = houses.sort((a, b) => b.latitude - a.latitude);

  sortedHouses.forEach((house) => {
    const markerPosition = new window.kakao.maps.LatLng(house.latitude, house.longitude);

    const marker = new window.kakao.maps.Marker({
      position: markerPosition,
      map: map.value,
      image: new window.kakao.maps.MarkerImage(
        "/apart.png",
        new window.kakao.maps.Size(24, 24),
        { offset: new window.kakao.maps.Point(12, 24) }
      ),
    });

    const overlayContent = document.createElement("div");
    overlayContent.style.position = "relative";
    overlayContent.style.display = "flex";
    overlayContent.style.flexDirection = "column";
    overlayContent.style.alignItems = "center";
    overlayContent.style.cursor = "pointer";

    const bubble = document.createElement("div");
    bubble.style.backgroundColor = "white";
    bubble.style.padding = "5px 10px";
    bubble.style.borderRadius = "10px";
    bubble.style.border = "1px solid #ccc";
    bubble.style.fontSize = "12px";
    bubble.style.textAlign = "center";
    bubble.style.boxShadow = "0px 4px 6px rgba(0, 0, 0, 0.1)";
    bubble.textContent = house.name;
    overlayContent.appendChild(bubble);

    const overlay = new window.kakao.maps.CustomOverlay({
      position: markerPosition,
      content: overlayContent,
      yAnchor: 1.7,
      map: map.value,
    });

    const handleMarkerClick = () => {
      houseDetail.value = {
        ...houseDetail.value,
        latitude: markerPosition.getLat(), // 마커의 위도
        longitude: markerPosition.getLng(), // 마커의 경도
      };
      fetchHouseDetail(house.aptSeq); // 상세 정보 요청
    };

    window.kakao.maps.event.addListener(marker, "click", handleMarkerClick);
    bubble.addEventListener("click", handleMarkerClick);
    overlayContent.addEventListener("click", handleMarkerClick);

    houseMarkers.push(marker);
    overlays.push(overlay);
  });
};

const fetchAllFacilities = async () => {
  if (!map.value) return;
  // 기존 마커 제거
  clearFacilityMarkers();
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

const addFacilityMarkers = (category, facilities) => {
  if (!facilityMarkers.value[category]) {
    facilityMarkers.value[category] = [];
  }
  facilities.forEach((facility) => {
    const markerPosition = new window.window.kakao.maps.LatLng(facility.latitude, facility.longitude);
    // 커스텀 오버레이 생성
    const iconClass = iconMap[category] || "fas fa-map-marker-alt"; // 기본 아이콘
    const overlayContent = document.createElement("div");
    overlayContent.innerHTML = `
      <div style="display: flex; justify-content: center; align-items: center; width: 40px; height: 40px; background-color: white; border-radius: 50%; box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); cursor: pointer;">
        <i class="${iconClass}" style="font-size: 18px; color: #333;"></i>
      </div>
    `;
    const markerOverlay = new window.window.kakao.maps.CustomOverlay({
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
      nameOverlay = new window.window.kakao.maps.CustomOverlay({
        position: new window.window.kakao.maps.LatLng(markerPosition.getLat() + zoomFactor, markerPosition.getLng()),
        content: nameOverlayContent,
        map: null, // 초기에는 숨김
      });
      nameOverlay.setMap(map.value); // 이름 표시
    });
    overlayContent.addEventListener("mouseleave", () => {
      nameOverlay.setMap(null); // 이름 숨김
    });
    facilityMarkers.value[category].push({ markerOverlay, nameOverlay });
  });
};

// 마커 제거 함수 수정
const clearFacilityMarkers = () => {
  for (const category in facilityMarkers.value) {
    facilityMarkers.value[category].forEach(({ markerOverlay, nameOverlay }) => {
      markerOverlay.setMap(null);
      if(nameOverlay){
        nameOverlay.setMap(null);
      }
    });
    facilityMarkers.value[category] = [];
  }
};

const showDiary = (house) => {
  diaryVisible.value = true;
  houseDetail.value = {
    ...houseDetail.value,
    latitude: house.latitude,
    longitude: house.longitude,
  };
};

// 선택된 카테고리 기반 마커 업데이트
const updateFacilityMarkers = () => {
  clearFacilityMarkers();
  fetchAllFacilities();
};

// 편의시설 카테고리 변경 처리
const handleFacilityToggled = (categories) => {
  selectedFacilities.value = categories;
  updateFacilityMarkers();
};

onMounted(() => {
  if (route.query.searchResult) {
    initializeWithSearchResult();
  } else {
    initializeMap(37.5665, 126.9780);
  }
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
