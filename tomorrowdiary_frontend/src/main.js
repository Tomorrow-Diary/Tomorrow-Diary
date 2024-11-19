import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { fas } from "@fortawesome/free-solid-svg-icons"; // Solid 아이콘 패키지 가져오기

import App from './App.vue'
import router from './router'

library.add(fas);

const app = createApp(App)

app.component("font-awesome-icon", FontAwesomeIcon);
app.use(createPinia())
app.use(router)

app.mount('#app')
