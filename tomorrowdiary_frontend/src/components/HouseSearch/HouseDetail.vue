<!-- HouseDetail.vue -->
<template>
  <div class="house-detail">
    <button class="close-button" @click="$emit('close')">✖</button>

    <!-- 아파트 이름 -->
    <h1 class="apartment-name">{{ name }}</h1>

    <!-- 단지 정보 -->
    <div class="section">
      <h2 class="section-title">단지 정보</h2>
      <div class="info">
        <p><strong>주소:</strong> {{ address }}</p>
        <p><strong>도로명 주소:</strong> {{ roadAddress }}</p>
        <p><strong>건축년도:</strong> {{ constructionYear }}</p>
      </div>
    </div>

    <!-- 매매 정보 -->
    <div class="section">
      <h2 class="section-title">매매 정보</h2>
      <table class="transactions">
        <thead>
        <tr>
          <th>계약날짜</th>
          <th>거래금액</th>
          <th>전용면적</th>
          <th>층수</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(transaction, index) in transactions" :key="index">
          <td>{{ transaction.date }}</td>
          <td>{{ transaction.price }}</td>
          <td>{{ transaction.area }}</td>
          <td>{{ transaction.floor }}</td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- 내일일기 생성 버튼 -->
    <div class="create-diary">
      <button class="create-button" @click="$emit('createDiary')">
        내일일기 생성
      </button>
    </div>
  </div>
</template>

<script setup>
defineProps({
  name: String,
  address: String,
  roadAddress: String,
  constructionYear: String,
  transactions: Array,
});
</script>

<style scoped>
.house-detail {
  position: fixed;
  top: 0;
  right: 0;
  width: 500px;
  height: 100%;
  background-color: white;
  box-shadow: -2px 0 5px rgba(0, 0, 0, 0.2);
  overflow-y: auto;
  z-index: 1001;
  padding: 5rem 2rem 2rem; /* 상단 패딩을 4rem으로 변경하여 아래로 이동 */
  box-sizing: border-box;
}

.header {
  text-align: center;
  background-color: #f5f5f5;
  padding: 1rem;
  margin-bottom: 1.5rem;
  border-radius: 8px;
}

.apartment-name {
  padding-left: 1rem;
  padding-bottom: 1rem;
  font-size: 2rem;
  font-weight: bold;
  color: #333;
}

.section {
  margin-bottom: 2rem;
  background-color: #f9f9f9;
  padding: 1rem;
  border-radius: 8px;
}

.section-title {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 1rem;
  color: #666;
  border-bottom: 2px solid #e0e0e0;
  padding-bottom: 0.5rem;
}

.info {
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
}

.info-item {
  display: flex;
  justify-content: space-between;
  font-size: 1rem;
}

.label {
  font-weight: bold;
  color: #555;
}

.value {
  color: #333;
}

.transactions {
  width: 100%;
  border-collapse: collapse;
}

.transactions th,
.transactions td {
  border: 1px solid #ccc;
  padding: 0.8rem;
  text-align: center;
}

.transactions th {
  background-color: #f5f5f5;
  font-weight: bold;
  color: #555;
}

.transactions td {
  color: #333;
}

.create-diary {
  display: flex;
  justify-content: center;
  margin-top: 1rem;
}

.create-button {
  background-color: #ff6600;
  width: 100%;
  color: white;
  font-size: 1.2rem;
  font-weight: bold;
  padding: 1rem 2rem;
  border: none;
  border-radius: 8px;
  cursor: pointer;
}

.create-button:hover {
  background-color: #e65c00;
}

.close-button {
  position: absolute;
  top: 5rem;
  right: 1rem;
  background: none;
  border: none;
  font-size: 1.5rem;
  color: #333;
  cursor: pointer;
  z-index: 1002;
}

.close-button:hover {
  color: #ff6600;
}

</style>

