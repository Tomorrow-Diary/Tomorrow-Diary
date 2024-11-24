<template>
  <div class="overlay" @click.self="closeDiary">
    <div
      class="diary-container"
      @mousedown="startDrag"
      @mousemove="onDrag"
      @mouseup="endDrag"
    >
      <div v-if="!isSummaryPage" class="diary-content">
        <!-- 왼쪽: 이미지 및 날짜/시간 -->
        <div class="diary-left">
          <img
            class="diary-image"
            :src="currentDiary.image"
            alt="Diary Image"
            @click="openImagePopup(currentDiary)"
            draggable="false"
          />
          <p class="diary-date">{{ currentDiary.date }} {{ currentDiary.time }}</p>
          <p class="diary-activity">{{ currentDiary.activity }}</p>
        </div>

        <!-- 가운데 선 -->
        <div class="divider"></div>

        <!-- 오른쪽: 제목 및 내용 -->
        <div class="diary-right">
          <h2 class="diary-title">{{ currentDiary.title }}</h2>
          <p class="diary-text">{{ currentDiary.text }}</p>
        </div>
      </div>

      <div v-else class="summary-content">
        <!-- 요약 페이지 왼쪽 -->
        <div class="summary-left">
          <img
            class="summary-image"
            src="/diaryImage/summaryImage.png"
            alt="Summary Image"
            draggable="false"
          />
        </div>

        <!-- 가운데 선 -->
        <div class="divider"></div>

        <!-- 요약 페이지 오른쪽 -->
        <div class="summary-right">
          <div
            class="thumbnail-grid"
            :class="{ 'grid-two': diaries.length === 2, 'grid-one': diaries.length === 1 }"
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

      <!-- 화살표 버튼 -->
      <button v-if="currentIndex > 0" class="arrow left-arrow" @click="prevDiary">
        <font-awesome-icon :icon="['fas', 'caret-left']" />
      </button>
      <button v-if="currentIndex < diaries.length" class="arrow right-arrow" @click="nextDiary">
        <font-awesome-icon :icon="['fas', 'caret-right']" />
      </button>
    </div>

    <!-- 이미지 팝업 -->
    <ImageDetail
      v-if="imagePopupVisible"
      :image="selectedImage.image"
      :time="selectedImage.time"
      :title="selectedImage.title"
      @close="closeImagePopup"
    />
  </div>
</template>

<script setup>
import { ref, computed, defineEmits } from "vue";
import ImageDetail from "./ImageDetail.vue";

const emit = defineEmits(["close"]);

const diaries = ref([
  {
    image: "/diaryImage/diary1.png",
    date: "2024년 11월 15일",
    time: "07:00 AM",
    activity: "기상 및 출근준비",
    title: "새로운 시작, 익숙함 속의 설렘",
    text: "새벽에 몇 번 깨다 말고...",
  },
  {
    image: "/diaryImage/diary2.png",
    date: "2024년 11월 15일",
    time: "08:00 AM",
    activity: "출근시간",
    title: "즐거운 출근시간",
    text: "오늘은 버스로...",
  },
]);

const summary = {
  date: "2024년 11월 15일",
  text: "새 자취방에서 설렘 가득한 첫 출근을 마친 하루였다. 🌟",
};

const currentIndex = ref(0);
const currentDiary = computed(() => diaries.value[currentIndex.value]);
const isSummaryPage = computed(() => currentIndex.value === diaries.value.length);

const imagePopupVisible = ref(false);
const selectedImage = ref({ image: "", time: "", title: "" });

const openImagePopup = (diary) => {
  selectedImage.value = { image: diary.image, time: diary.time, title: diary.title };
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
  // 요약 페이지로 이동 가능
  if (currentIndex.value < diaries.value.length) {
    currentIndex.value++;
  }
};

// Drag-related functionality
const dragThreshold = 50; // 드래그로 인식할 최소 거리 (픽셀)
const isDragging = ref(false);
const startX = ref(0);
const currentX = ref(0);
const hasDragged = ref(false); // 드래그 여부 플래그

const startDrag = (event) => {
  // 드래그 시작
  isDragging.value = true;
  startX.value = event.clientX;
  hasDragged.value = false; // 초기화
};

const onDrag = (event) => {
  if (isDragging.value) {
    currentX.value = event.clientX;

    // 드래그 거리 계산
    const dragDistance = Math.abs(currentX.value - startX.value);
    if (dragDistance > dragThreshold) {
      hasDragged.value = true; // 드래그로 간주
    }
  }
};

const endDrag = (event) => {
  if (isDragging.value && hasDragged.value) {
    const dragDistance = startX.value - currentX.value;

    const startArea = startX.value < window.innerWidth / 2 ? "diary-left" : "diary-right";
    const endArea = currentX.value < window.innerWidth / 2 ? "diary-left" : "diary-right";

    if (startArea === "diary-right" && endArea === "diary-left" && dragDistance > dragThreshold) {
      nextDiary();
    } else if (startArea === "diary-left" && endArea === "diary-right" && dragDistance < -dragThreshold) {
      prevDiary();
    }
  }
  isDragging.value = false;
};

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
}

.diary-left,
.summary-left {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.diary-image {
  width: 90%;
  height: 75%;
  object-fit: cover;
  border-radius: 10px;
  cursor: pointer;
  draggable: false;
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
}

.diary-right,
.summary-right {
  flex: 1;
  padding: 1rem;
  display: flex;
  flex-direction: column;
}

.diary-title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 1rem;
}

.summary-date{
  padding-top: 20px;
}

.diary-text,
.summary-text {
  flex: 1;
  font-size: 1rem;
  line-height: 1.5;
}

.thumbnail-grid {
  display: grid;
  gap: 10px;
  height: 90%;
  overflow-y: auto;
  grid-template-columns: repeat(3, 1fr);
}

.thumbnail-grid.grid-two {
  grid-template-columns: repeat(2, 1fr);
}

.thumbnail-grid.grid-one {
  grid-template-columns: repeat(1, 1fr);
}

.thumbnail-image {
  width: 100%;
  height: 100%;
  aspect-ratio: 1;
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

.divider {
  width: 2px;
  background-color: #ccc;
  margin: 0 10px;
  height: 600px;
  align-self: stretch;
}
</style>
