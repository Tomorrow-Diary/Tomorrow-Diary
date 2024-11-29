<template>
  <div id="map2" style="width: 100%; height: 100%;"></div>
</template>

<script>
import axios from "axios";

export default {
  name: "KakaoMap",
  props: {
    start: {
      type: Object,
      required: true,
    },
    end: {
      type: Object,
      required: true,
    },
    facilities: {
      type: Array,
      required: true,
    },
  },
  mounted() {
    this.initMap();
  },
  methods: {
    async initMap() {
      const { kakao } = window;

      const container = document.getElementById("map2");
      const options = {
        center: new kakao.maps.LatLng(this.start.lat, this.start.lng),
        level: 5,
      };
      const map = new kakao.maps.Map(container, options);

      // 지도 범위 설정을 위한 LatLngBounds 객체 생성
      const bounds = new kakao.maps.LatLngBounds();

      // 시설 경로 (초록색)
      for (let i = 0; i < this.facilities.length; i++) {
        const facility = this.facilities[i];
        const facilityRoute = await this.fetchRoute(
          `${this.start.lng},${this.start.lat}`,
          `${facility.longitude},${facility.latitude}`
        );

        if (facilityRoute) {
          const pathFacility = this.convertVertexesToLatLng(facilityRoute);
          new kakao.maps.Polyline({
            map,
            path: pathFacility,
            strokeWeight: 5,
            strokeColor: "#00FF00", // 초록색
          });

          // 경로의 모든 좌표를 bounds에 추가
          pathFacility.forEach((latLng) => bounds.extend(latLng));

          // 카테고리별 아이콘 URL 설정
          const iconMapping = {
            "카페": "https://cdn-icons-png.flaticon.com/512/9620/9620771.png",
            "음식점": "https://cdn-icons-png.flaticon.com/512/948/948036.png",
            "체육시설": "https://cdn-icons-png.flaticon.com/512/6750/6750755.png",
            "여가시설": "https://cdn-icons-png.flaticon.com/512/4854/4854246.png",
          };

          const facilityIcon =
            iconMapping[facility.facilityCategory.name] ||
            "https://cdn-icons-png.flaticon.com/512/709/709722.png";

          // 시설 위치에 마커 추가
          this.addMarker(
            map,
            facility.latitude,
            facility.longitude,
            facility.name,
            facilityIcon
          );

          // 시설 위치를 bounds에 추가
          bounds.extend(new kakao.maps.LatLng(facility.latitude, facility.longitude));
        }
      }

      // 출발지 -> 목적지 경로
      const routeDataForward = await this.fetchRoute(
        `${this.start.lng},${this.start.lat}`,
        `${this.end.lng},${this.end.lat}`
      );

      if (routeDataForward) {
        const pathForward = this.convertVertexesToLatLng(routeDataForward);
        new kakao.maps.Polyline({
          map,
          path: pathForward,
          strokeWeight: 5,
          strokeColor: "#FF0000",
          strokeOpacity: 1,
        });

        // 경로의 모든 좌표를 bounds에 추가
        pathForward.forEach((latLng) => bounds.extend(latLng));
      }

      // 출발지에 마커 추가
      this.addMarker(
        map,
        this.start.lat,
        this.start.lng,
        "집",
        "https://cdn-icons-png.flaticon.com/512/3771/3771140.png"
      );

      // 출발지 위치를 bounds에 추가
      bounds.extend(new kakao.maps.LatLng(this.start.lat, this.start.lng));

      // 목적지에 마커 추가
      this.addMarker(
        map,
        this.end.lat,
        this.end.lng,
        "회사",
        "https://cdn-icons-png.flaticon.com/512/4091/4091968.png"
      );

      // 목적지 위치를 bounds에 추가
      bounds.extend(new kakao.maps.LatLng(this.end.lat, this.end.lng));

      // 계산된 bounds로 지도 범위 설정
      map.setBounds(bounds);
    }
    ,

    async fetchRoute(origin, destination) {
      try {
        const response = await axios.get(
          "https://apis-navi.kakaomobility.com/v1/directions",
          {
            headers: {
              Authorization: "KakaoAK 7c1cbc0f742966cf72cdda5cccdcc7ed",
            },
            params: {
              origin,
              destination,
              priority: "RECOMMEND",
            },
          }
        );

        if (response.data.routes && response.data.routes.length > 0) {
          return response.data.routes[0].sections.flatMap((section) =>
            section.roads.map((road) => road.vertexes)
          ).flat();
        }
        return null;
      } catch (error) {
        console.error("경로 데이터를 요청하는 중 오류 발생:", error);
        return null;
      }
    },

    convertVertexesToLatLng(vertexes) {
      const { kakao } = window;
      const path = [];
      for (let i = 0; i < vertexes.length; i += 2) {
        const lng = vertexes[i];
        const lat = vertexes[i + 1];
        path.push(new kakao.maps.LatLng(lat, lng));
      }
      return path;
    },

    addMarker(map, lat, lng, title, iconUrl) {
      const { kakao } = window;

      // 마커 이미지 생성
      const imageSize = new kakao.maps.Size(48, 48); // 마커 이미지 크기
      const markerImage = new kakao.maps.MarkerImage(iconUrl, imageSize);

      // 마커 생성
      const marker = new kakao.maps.Marker({
        position: new kakao.maps.LatLng(lat, lng),
        image: markerImage, // 커스텀 이미지 적용
        map,
      });

      // 인포윈도우 생성
      const infoWindow = new kakao.maps.InfoWindow({
        content: `
                   <div style="width:150px;text-align:center;padding:6px;">
                       ${title}
               </div>`
      });

      // 마커에 hover 시 인포윈도우 표시
      kakao.maps.event.addListener(marker, "mouseover", () => {
        infoWindow.open(map, marker);
      });

      // 마커에서 mouseout 시 인포윈도우 닫기
      kakao.maps.event.addListener(marker, "mouseout", () => {
        infoWindow.close();
      });
    },
  },
};
</script>

<style scoped>
#map2 {
  width: 100%;
  height: 100%;
}
</style>
