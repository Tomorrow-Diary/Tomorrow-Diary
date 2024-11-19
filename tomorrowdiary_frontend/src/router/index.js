import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import LandingPage from "../views/LandingPage.vue";
import DestinationSearch from '@/views/DestinationSearch.vue'
import TransportationSelection from '@/views/TransportationSelection.vue'

const routes = [
  {
    path: "/",
    name: "Home",
    component: HomeView,
  },
  {
    path: "/landing",
    name: "Landing",
    component: LandingPage, // 랜딩 페이지 경로
  },{
    path: "/destination",
    name: "DestinationSearch",
    component: DestinationSearch, // 랜딩 페이지 경로
  },{
    path: "/transportations",
    name: "TransportationSelection",
    component: TransportationSelection,
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
