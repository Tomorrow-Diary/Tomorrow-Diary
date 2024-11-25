import { defineStore } from 'pinia';

export const useSignupStore = defineStore('signup', {
  state: () => ({
    nickname: '', // 로그인한 유저의 닉네임
    roadAddress: '',
    latitude: null,
    longitude: null,
    transportType: '',
    startTime: '',
    endTime: '',
  }),
  actions: {
    setNickname(nickname) {
      this.nickname = nickname;
    },
    setDestination(address, lat, lng) {
      this.roadAddress = address;
      this.latitude = lat;
      this.longitude = lng;
    },
    setTransportType(type) {
      this.transportType = type;
    },
    setWorkTimes(start, end) {
      this.startTime = start;
      this.endTime = end;
    },
    resetAll() {
      this.nickname = '';
      this.roadAddress = '';
      this.latitude = null;
      this.longitude = null;
      this.transportType = '';
      this.startTime = '';
      this.endTime = '';
    },
  },
});
