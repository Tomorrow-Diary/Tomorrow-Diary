<template>
  <div :class="['sidebar', { expanded: isSidebarExpanded }]">
    <button class="toggle-button" @click="toggleSidebar">
      {{ isSidebarExpanded ? '<' : '>' }}
    </button>

    <div v-show="isSidebarExpanded" class="sidebar-content">
      <div class="facility-section">
        <div class="facility-header">
          <span>편의시설</span>
        </div>
        <div class="facility-options">
          <label
            v-for="facility in facilities"
            :key="facility.value"
            class="facility-item"
          >
            <input
              type="checkbox"
              :value="facility.value"
              v-model="selectedFacilities"
              @change="toggleFacility"
            />
            {{ facility.label }}
          </label>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const emit = defineEmits(["facilityToggled"]);

const isSidebarExpanded = ref(false);
const selectedFacilities = ref([]);

const facilities = [
  { label: "체육시설", value: "체육시설" },
  { label: "음식점", value: "음식점" },
  { label: "여가시설", value: "여가시설" },
  { label: "카페", value: "카페" },
];

const toggleSidebar = () => {
  isSidebarExpanded.value = !isSidebarExpanded.value;
};

const toggleFacility = () => {
  emit("facilityToggled", [...selectedFacilities.value]);
};
</script>

<style scoped>
.sidebar {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  background-color: white;
  box-shadow: 2px 0px 5px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: width 0.3s ease;
  width: 50px;
  z-index: 1001;
}

.sidebar.expanded {
  width: 500px;
}

.toggle-button {
  position: absolute;
  top: 50%;
  right: 0;
  transform: translateY(-50%);
  border: none;
  width: 50px;
  height: 100%;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 30px;
}

.sidebar-content {
  padding-top: 5rem;
  padding-right: 50px;
  font-size: 1rem;
  color: #333;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1rem;
}

.facility-section {
  width: 90%;
  background-color: #f9f9f9;
  padding: 1rem;
  border-radius: 10px;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1);
}

.facility-header {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 1rem;
  text-align: center;
}

.facility-options {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
}

.facility-item {
  display: flex;
  align-items: center;
  gap: 0.3rem;
  font-size: 0.9rem;
  background-color: #fff;
  padding: 0.5rem 0.9rem;
  border-radius: 5px;
  box-shadow: 0px 1px 3px rgba(0, 0, 0, 0.1);
}
</style>
