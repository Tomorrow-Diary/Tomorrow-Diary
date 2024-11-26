<template>
  <div class="overlay" @click.self="closeDiary">
    <!-- 로딩바 -->
    <div v-if="isLoading" class="loading-overlay">
      <div class="spinner"></div>
    </div>
    <div
      v-show="!isLoading"
      class="diary-container"
      @mousedown="startDrag"
      @mousemove="onDrag"
      @mouseup="endDrag"
    >
      <div v-if="!isSummaryPage" class="diary-content">
        <div class="diary-left">
          <img
            class="diary-image"
            :src="currentDiary.image"
            alt="Diary Image"
            @click="openImagePopup(currentDiary)"
            draggable="false"
          />
          <p class="diary-date">{{ currentDiary.date }} {{ formattedTime }}</p>
          <p class="diary-activity">{{ currentDiary.activity }}</p>
        </div>
        <div class="diary-right">
          <p class="diary-text">{{ formattedText  }}</p>
        </div>
      </div>
      <div v-else class="summary-content">
        <div class="summary-left">
          <KakaoMap
            v-if="summaryRoute"
            :start="{ lat: props.latitude, lng: props.longitude }"
            :end="{
              lat: summaryRoute.destination.lat,
              lng: summaryRoute.destination.lng,
            }"
            :facilities="facilities"
          />
        </div>
        <div class="divider"></div>
        <div class="summary-right">
          <div
            class="thumbnail-grid"
            :class="{'grid-one': diaries.length === 1 }"
          >
            <img
              v-for="(diary, index) in diaries"
              :key="index"
              :src="diary.image"
              alt="Diary Thumbnail"
              class="thumbnail-image"
              @click="openImagePopup(diary)"
              draggable="false"
            />
          </div>
          <p class="summary-date">{{ summary.date }}</p>
          <p class="summary-text">{{ summary.text }}</p>
        </div>
      </div>
      <button v-if="currentIndex > 0" class="arrow left-arrow" @click="prevDiary">
        <font-awesome-icon :icon="['fas', 'caret-left']" />
      </button>
      <button v-if="currentIndex < diaries.length" class="arrow right-arrow" @click="nextDiary">
        <font-awesome-icon :icon="['fas', 'caret-right']" />
      </button>
    </div>
    <ImageDetail
      v-if="imagePopupVisible"
      :image="selectedImage.image"
      :time="selectedImage.time"
      @close="closeImagePopup"
    />
  </div>
</template>

<script setup>
import { ref, computed, defineProps, defineEmits, onMounted } from "vue";
import axios from "axios";
import KakaoMap from "./KakaoMap.vue";
import ImageDetail from "./ImageDetail.vue";

// Props 정의
const props = defineProps({
  facilities: Array,
  latitude: Number,
  longitude: Number,
});

const emit = defineEmits(["close"]);

// 데이터 정의
const diaries = ref([]);
const summary = ref({
  date: "",
  text: "",
  imgUrl: "",
});
const summaryRoute = ref(null);
const facilities = ref([]); // 편의시설 데이터를 저장

const isLoading = ref(false);

// 현재 인덱스
const currentIndex = ref(0);
const currentDiary = computed(() => diaries.value[currentIndex.value]);
const isSummaryPage = computed(() => currentIndex.value === diaries.value.length);

const imagePopupVisible = ref(false);
const selectedImage = ref({ image: "", time: "" });

const openImagePopup = (diary) => {
  selectedImage.value = { image: diary.image, time: diary.time };
  imagePopupVisible.value = true;
};

const closeImagePopup = () => {
  imagePopupVisible.value = false;
};

const closeDiary = () => {
  if (!imagePopupVisible.value) {
    emit("close");
  }
};

const prevDiary = () => {
  if (currentIndex.value > 0) currentIndex.value--;
};

const nextDiary = () => {
  if (currentIndex.value < diaries.value.length) {
    currentIndex.value++;
  }
};

// Diary API 호출
const createDiary = async () => {
  isLoading.value = true; // 로딩 시작
  try {
    const response = await axios.post(
      "/api/v1/diary",
      {
        facilities: props.facilities,
        latitude: props.latitude,
        longitude: props.longitude,
      },
      { withCredentials: true }
    );

    if (response.data.status === "success") {
      const { data } = response.data;

      diaries.value = data.contents.map((content) => ({
        image: content.imgUrl,
        date: data.date,
        time: content.time,
        activity: "",
        text: content.content,
      }));
      summary.value.date = data.date;
      summary.value.text = data.summary;

      // 경로 및 시설 데이터 설정
      summaryRoute.value = {
        destination: {
          lat: data.userDestination.latitude,
          lng: data.userDestination.longitude,
        },
      };

      facilities.value = data.facilities; // 편의시설 데이터 저장
    } else {
      console.error("Diary 생성 실패:", response.data.message);
    }
  } catch (error) {
    console.error("Diary 생성 중 오류 발생:", error);
  } finally {
    isLoading.value = false; // 로딩 종료
  }
};

const formattedText = computed(() => {
  if (!currentDiary.value?.text) return ''; // text가 없는 경우 빈 문자열 반환
  return currentDiary.value.text.replace(/\./g, '.\n'); // 모든 `.` 뒤에 줄바꿈 추가
});

const formattedTime = computed(() => {
  if (!currentDiary.value?.time) return ''; // time 값이 없으면 빈 문자열 반환
  const [hours, minutes] = currentDiary.value.time.split(':'); // 시간을 ":"로 분리
  return `${hours}:${minutes}`; // 시간과 분만 반환
});

// Diary 컴포넌트 로드 시 API 호출
onMounted(() => {
  createDiary();
});
</script>


<style scoped>
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1001;
}

.diary-container {
  position: relative;
  width: 80%;
  height: 70%;
  background-color: white;
  border-radius: 10px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  z-index: 1002;
  overflow: hidden;
}

.diary-content,
.summary-content {
  display: flex;
  flex: 1;
  padding: 1rem;
  user-select: none;

  background-image: url('/book.PNG'); /* 배경 이미지 경로 */
  background-size: cover; /* 이미지가 컨테이너에 맞게 조정 */
  background-position: center; /* 이미지를 가운데 정렬 */
  background-repeat: no-repeat; /* 이미지 반복 방지 */
}

.diary-left,
.summary-left {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.diary-image {
  width: 95%;
  height: 85%;
  object-fit: cover;
  border-radius: 10px;
  cursor: pointer;
  draggable: false;
  margin-left: 40px;
}

.summary-image {
  width: 90%;
  height: 90%;
  object-fit: cover;
  border-radius: 10px;
}

.diary-date,
.diary-time,
.diary-activity {
  margin: 0.5rem 0;
  text-align: center;
  font-family: 'Gyeonggi_Batang_Regular', serif;
}

.diary-right{
  flex: 1;
  padding: 1rem;

  display: flex;
  justify-content: center;
  align-items: center;

  padding-bottom: 100px;
  padding-left: 100px;
}

.summary-right {
  flex: 1;
  padding: 1rem;
  padding-left: 80px;
  display: flex;
  flex-direction: column;
}

.diary-text{
  flex: 1;
  font-size: 1rem;
  line-height: 5;
  white-space: pre-wrap; /* 줄바꿈과 공백을 유지 */
  font-family: 'Gyeonggi_Batang_Regular', serif; /* 커스텀 폰트 적용 */
}

@font-face {
  font-family: 'Gyeonggi_Batang_Regular';
  src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/2410-3@1.0/Batang_Regular.woff') format('woff');
  font-weight: 400;
  font-style: normal;
}

.summary-text {
  flex: 1;
  font-size: 1rem;
  vertical-align: middle;
  font-family: 'Gyeonggi_Batang_Regular', serif;
}

.thumbnail-grid {
  display: grid;
  gap: 10px;
  grid-template-columns: repeat(2, 1fr);
  align-items: start; /* 자식 요소 크기에 맞추기 */
  height: auto; /* 부모 컨테이너 높이를 자식 요소에 맞춤 */
}

.thumbnail-image {
  width: 100%;
  aspect-ratio: 1.3; /* 이미지 비율 유지 */
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
  object-fit: cover;
  cursor: pointer;
  draggable: false;
}


.arrow {
  position: absolute;
  top: 50%;
  background: none;
  border: none;
  font-size: 2rem;
  cursor: pointer;
  transform: translateY(-50%);
  z-index: 10;
  color: #333;
  padding: 20% 10px;
  transition: background-color 0.3s, box-shadow 0.3s;
}

.arrow:hover {
  background-color: rgba(0, 0, 0, 0.1);
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.left-arrow {
  left: 10px;
}

.right-arrow {
  right: 10px;
}

.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1002;
}

.spinner {
  width: 50px;
  height: 50px;
  border: 5px solid rgba(255, 255, 255, 0.2);
  border-top: 5px solid white;
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

</style>
