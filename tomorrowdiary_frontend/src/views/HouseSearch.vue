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
import { useRoute } from "vue-router";
import axios from "axios";

const route = useRoute();
const currentDong = ref("위치 정보 로딩 중...");
const isSidebarVisible = ref(true);
const houseDetailVisible = ref(false);
const diaryVisible = ref(false);
const searchResults = ref([]);
const houseDetail = ref({
  name: "",
  address: "",
  roadAddress: "",
  constructionYear: "",
  transactions: [],
});
let map;
let markers = [];
let overlays = [];

// 지도 초기화
const initializeMap = (lat, lng) => {
  const container = document.getElementById("map");
  const defaultLocation = new window.kakao.maps.LatLng(lat, lng);
  const options = {
    center: defaultLocation,
    level: 3,
  };

  map = new window.kakao.maps.Map(container, options);

  updateDongInfo(lng, lat);

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

// 아파트 데이터 요청 및 마커 추가
const fetchHouseData = async (dongCode) => {
  try {
    const response = await axios.get(`/api/v1/house`, {
      params: { dongcode: dongCode },
      withCredentials: true,
    });

    if (response.data.status === "success") {
      const houses = response.data.data;
      addMarkers(houses);
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
        name: data.name,
        address: data.jibunAddress,
        roadAddress: data.roadAddress,
        constructionYear: data.buildYear,
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

const addMarkers = (houses) => {
  // 기존 마커 및 오버레이 제거
  markers.forEach((marker) => marker.setMap(null));
  overlays.forEach((overlay) => overlay.setMap(null));
  markers = [];
  overlays = [];

  // 위도(lat) 기준으로 하우스 데이터 정렬 (위도가 낮은 것이 먼저)
  const sortedHouses = houses.sort((a, b) => b.latitude - a.latitude);

  sortedHouses.forEach((house) => {
    const markerPosition = new window.kakao.maps.LatLng(house.latitude, house.longitude);

    // 마커 생성 (아이콘)
    const marker = new window.kakao.maps.Marker({
      position: markerPosition,
      map: map,
      image: new window.kakao.maps.MarkerImage(
        "/apart.png",
        new window.kakao.maps.Size(24, 24),
        { offset: new window.kakao.maps.Point(12, 24) }
      ),
    });

    // 커스텀 오버레이 생성 (말풍선 스타일)
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

    const arrow = document.createElement("div");
    arrow.style.width = "0";
    arrow.style.height = "0";
    arrow.style.borderLeft = "6px solid transparent";
    arrow.style.borderRight = "6px solid transparent";
    arrow.style.borderTop = "6px solid white";
    overlayContent.appendChild(arrow);

    const overlay = new window.kakao.maps.CustomOverlay({
      position: markerPosition,
      content: overlayContent,
      yAnchor: 1.7,
      map: map,
    });

    const handleMarkerClick = () => {
      fetchHouseDetail(house.aptSeq); // 상세 정보 요청
    };

    // 클릭 이벤트 연결
    window.kakao.maps.event.addListener(marker, "click", handleMarkerClick);
    bubble.addEventListener("click", handleMarkerClick);
    overlayContent.addEventListener("click", handleMarkerClick);

    markers.push(marker);
    overlays.push(overlay);
  });
};




// 검색 결과 기반 지도 초기화 및 데이터 로드
const initializeWithSearchResult = () => {
  const searchResult = JSON.parse(route.query.searchResult);
  const dongCode = searchResult.dongCode;

  performAddressSearch(
    `${searchResult.sidoName} ${searchResult.gugunName} ${searchResult.dongName}`,
    (lat, lng) => {
      initializeMap(lat, lng);
      fetchHouseData(dongCode);
    }
  );
};

// 주소 검색 및 위치 이동
const performAddressSearch = (query, callback) => {
  const geocoder = new window.kakao.maps.services.Geocoder();
  geocoder.addressSearch(query, (result, status) => {
    if (status === window.kakao.maps.services.Status.OK) {
      const { y: lat, x: lng } = result[0];
      callback(parseFloat(lat), parseFloat(lng));
    } else {
      console.error("주소 검색 실패");
    }
  });
};

// Diary 표시 함수
const showDiary = () => {
  diaryVisible.value = true;
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
/* 기존 스타일 유지 */
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
