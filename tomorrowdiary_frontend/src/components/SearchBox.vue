<template>
  <div class="search-container">
    <h1>어떤 집을 찾고 계세요?</h1>
    <div class="search-box-wrapper">
      <div class="search-box">
        <input
          type="text"
          placeholder="시/도 또는 시/군/구 또는 읍/면/동을 입력하세요"
          v-model="keyword"
          @input="fetchSuggestions"
        />
        <button @click="search"><font-awesome-icon icon="magnifying-glass" /></button>
      </div>
      <!-- 연관 검색어 리스트 -->
      <div v-if="suggestions.length > 0" class="suggestions">
        <div
          v-for="(item, index) in suggestions"
          :key="index"
          class="suggestion-item"
          @click="selectSuggestion(item)"
        >
          {{ item.sidoName }} {{ item.gugunName }} {{ item.dongName }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

const router = useRouter();
const keyword = ref(""); // 검색어
const suggestions = ref([]); // 연관 검색어 목록
const selectedSearchResult = ref(null); // 선택된 검색 결과

// 연관 검색어 가져오기
const fetchSuggestions = async () => {
  if (!keyword.value.trim()) {
    suggestions.value = [];
    return;
  }

  try {
    const response = await axios.get(`/api/v1/address`, {
      params: { keyword: keyword.value }, // 검색어 전달
      withCredentials: true,
    });

    if (response.data.status === "success") {
      // 서버에서 받은 데이터 중 키워드와 일치하는 항목만 필터링
      const filteredResults = response.data.data.filter((item) => {
        const fullName = `${item.sidoName} ${item.gugunName} ${item.dongName}`;
        return fullName.includes(keyword.value.trim());
      });

      suggestions.value = filteredResults;
    }
  } catch (error) {
    console.error("연관 검색어 불러오기 실패:", error);
    suggestions.value = [];
  }
};

// 검색어 선택 시 처리
const selectSuggestion = (item) => {
  keyword.value = `${item.sidoName} ${item.gugunName} ${item.dongName}`; // 검색창에 반영
  selectedSearchResult.value = item; // 선택된 검색 결과 저장
  suggestions.value = []; // 목록 초기화
};

// 검색 실행
const search = () => {
  if (!selectedSearchResult.value) {
    alert("검색어를 입력하고 선택해주세요!");
    return;
  }

  // 선택된 검색 결과 객체 전달
  router.push({
    path: "/map",
    query: { searchResult: JSON.stringify(selectedSearchResult.value) },
  });
};
</script>


<style scoped>
.search-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 2rem;
  border-radius: 10px;
}

.search-container h1 {
  font-size: 3rem;
  color: #eeeeee;
  margin-bottom: 1rem;
}

.search-box-wrapper {
  position: relative;
  width: 100%;
  max-width: 500px;
}

.search-box {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  width: 100%;
}

.search-box input {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}

.search-box input::placeholder {
  text-align: center;
}

.search-box button {
  background-color: #ff6600;
  color: white;
  padding: 0.8rem 1rem;
  font-size: 1rem;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.search-box button:hover {
  background-color: #e65c00;
}

.suggestions {
  position: absolute;
  top: 100%;
  left: 0;
  width: 100%;
  margin-top: 0.5rem;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 5px;
  max-height: 200px;
  overflow-y: auto;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.suggestion-item {
  padding: 0.8rem;
  border-bottom: 1px solid #eee;
  cursor: pointer;
}

.suggestion-item:hover {
  background-color: #f9f9f9;
}

.suggestion-item:last-child {
  border-bottom: none;
}
</style>
