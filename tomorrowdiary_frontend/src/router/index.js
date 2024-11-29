import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import LandingPage from "../views/LandingPage.vue";
import DestinationSearch from '@/views/DestinationSearch.vue'
import TransportationSelection from '@/views/TransportationSelection.vue'
import WorkTimeSelection from '@/views/WorkTimeSelection.vue'
import MainPage from '@/views/MainPage.vue'
import HouseSearch from '@/views/HouseSearch.vue'
import Callback from '@/views/Callback.vue'

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
  },{
    path: "/workTime",
    name: "WorkTimeSelection",
    component: WorkTimeSelection,
  },{
    path: "/main",
    name: "MainPage",
    component: MainPage,
  },{
    path: "/map",
    name: "HouseSearch",
    component: HouseSearch,
  },{
    path: '/callback',
    name: 'Callback',
    component: Callback,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
