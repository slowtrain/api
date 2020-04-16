import Vue from 'vue'
import Vuex from 'vuex'
import App from './App.vue'
import router from './router'
import BootstrapVue from 'bootstrap-vue'
import VueSweetalert2 from 'vue-sweetalert2'
import Axios from 'axios'
import VueAxios from 'vue-axios'
import VueResource from 'vue-resource'
import VueCookie from 'vue-cookie'
import ElementUI from 'element-ui'
import VueDataTables from 'vue-data-tables'
import lang from 'element-ui/lib/locale/lang/en'
import locale from 'element-ui/lib/locale'
import util from './util/util'
import directive from './directive/directive'
import timer from './timer/timer'
import store from './store/store'


Vue.config.productionTip = false

Vue.prototype.$http = Axios
Vue.prototype.$cookie = VueCookie
Vue.prototype.$util = util

Vue.use(BootstrapVue)
Vue.use(VueSweetalert2)
Vue.use(VueAxios, Axios)
Vue.use(VueResource)
Vue.use(VueCookie)
Vue.use(VueAxios, Axios)
Vue.use(BootstrapVue)
Vue.use(VueDataTables)

Vue.use(ElementUI)
Vue.use(Vuex)
Vue.use(directive)
// Vue.use(directive)
// Vue.use(util)
//Vue.use(LiquorTree)

locale.use(lang)

/*
Axios.post(
  '/episode/api/authentication/refresh'
).then((res) => {
  store.commit('setUser', res.data.user)
  store.commit('setApps', res.data.apps)
  store.commit('setAuth', res.data.auth)
  timer.startTimer()
}).catch((error) => {
  router.push('/auth/signin')
  console.log(error)
})

window.onbeforeunload = function () {
  // VueCookie.delete('authrizeduser')
  store.commit('toEmpty')
  Axios.post('/episode/api/authentication/signout')
}

document.addEventListener('click', () => {
  if (VueCookie.get('authrizeduser') !== null) {
    timer.resetTimer()
  }
})
*/


new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
