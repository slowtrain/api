
import 'core-js/es6/promise'
import 'core-js/es6/string'

// import cssVars from 'css-vars-ponyfill'

import Vue from 'vue'
import Vuex from 'vuex'
import 'es6-promise/auto'
import 'core-js/es7/array'
import '@babel/polyfill'
import 'core-js/modules/es6.promise'
import 'core-js/modules/es6.array.iterator'

import Axios from 'axios'
import VueAxios from 'vue-axios'
import VueResource from 'vue-resource'
import VueCookie from 'vue-cookie'
import BootstrapVue from 'bootstrap-vue'

// import Vue2Storage from 'vue2-storage'
import ElementUI from 'element-ui'
import VueDataTables from 'vue-data-tables'
import LiquorTree from 'liquor-tree'

import lang from 'element-ui/lib/locale/lang/en'
import locale from 'element-ui/lib/locale'
import util from './util/util'
import directive from './directive/directive'
import timer from './timer/timer'
import router from './router/index'
import store from './store/store'
import App from './App'

import 'element-ui/lib/theme-chalk/index.css'

Vue.config.productionTip = false
Vue.prototype.$http = Axios
Vue.prototype.$cookie = VueCookie
Vue.prototype.$util = util
Vue.use(VueResource)
Vue.use(VueCookie)
Vue.use(VueAxios, Axios)
Vue.use(BootstrapVue)
Vue.use(VueDataTables)
Vue.use(LiquorTree)
Vue.use(ElementUI)
Vue.use(Vuex)
Vue.use(directive)
Vue.use(util)

/*
Vue.use(Vue2Storage, {
  prefix: 'es_',
  driver: 'session',
  ttl: 60 * 60 * 24 * 1000
})
*/
locale.use(lang)

router.beforeEach(function (to, from, next) {
  if (to.matched.some(function (routeInfo) {
    return routeInfo.meta.authRequired
  })) {
    if (VueCookie.get('authrizeduser') === null) {
      next('/gate')
    } else {
      next()
    }
  } else {
    next()
  }
})

Axios.post(
  '/episode/api/authentication/refresh'
).then((res) => {
  store.commit('setUser', res.data.user)
  store.commit('setApps', res.data.apps)
  store.commit('setAuth', res.data.auth)
  timer.startTimer()
}).catch((error) => {
  router.push('/gate')
  console.log(error)
})

window.onbeforeunload = function (event) {
  // VueCookie.delete('authrizeduser')
  store.commit('toEmpty')
  Axios.post('/episode/api/authentication/signout')
}

document.addEventListener('click', () => {
  if (VueCookie.get('authrizeduser') !== null) {
    timer.resetTimer()
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App, LiquorTree }
})

/* Authentication by EPISODE JWT  end */

/*
router.beforeEach(function (to, from, next) {
  if (to.matched.some(function (routeInfo) {
    return routeInfo.meta.authRequired
  })) {
    if (VueCookie.get('kaiicamsecret') === null) {
      next('/gate')
    } else {
      next()
    }
  } else {
    next()
  }
})

window.onbeforeunload = function (event) {
  // VueCookie.delete('kaiicamsecret')
  store.commit('toEmpty')
  Axios.post('/episode/api/authentication/signout')
}

if (VueCookie.get('kaiicamsecret') !== null || VueCookie.get('LtpaToken2') !== null) {
  Axios.post(
    '/episode/api/authentication/sso'
  ).then((res) => {
    store.commit('setUser', res.data.user)
    store.commit('setApps', res.data.apps)
    store.commit('setAuth', res.data.auth)
    createVue()
    router.push('/')
    timer.startTimer()
  }).catch(() => {
    createVue()
    router.push('/gate')
  })
} else {
  createVue()
  router.push('/gate')
}

/*
document.addEventListener('click', () => {
  if (VueCookie.get('kaiicamsecret') !== null) {
    timer.resetTimer()
  }
})

if (VueCookie.get('LtpaToken2') === null) {
  location.href = 'http://sso.koreaaero.com'
} else {
  Axios.post(
    '/episode/api/authentication/sso', { LtpaToken2: VueCookie.get('LtpaToken2') }
  ).then((res) => {
    store.commit('setUser', res.data.user)
    store.commit('setApps', res.data.apps)
    store.commit('setAuth', res.data.auth)
    createVue()
  }).catch((error) => {
    console.log(error)
  })
}

document.addEventListener('click', function () {
  if (VueCookie.get('LtpaToken2') === null) {
    location.href = 'http://sso.koreaaero.com'
  }
})
*/

/* Authentication by KAI SSO  end */

/* Authentication by EPISODE JWT  start */
/*
router.beforeEach(function (to, from, next) {
  if (to.matched.some(function (routeInfo) {
    return routeInfo.meta.authRequired
  })) {
    if (VueCookie.get('authrizeduser') === null) {
      next('/gate')
    } else {
      next()
    }
  } else {
    next()
  }
})

Axios.post(
  '/episode/api/authentication/refresh'
).then((res) => {
  store.commit('setUser', res.data.user)
  store.commit('setApps', res.data.apps)
  store.commit('setAuth', res.data.auth)
  timer.startTimer()
}).catch((error) => {
  router.push('/gate')
  console.log(error)
})

window.onbeforeunload = function (event) {
  VueCookie.delete('authrizeduser')
  store.commit('toEmpty')
  Axios.post('/episode/api/authentication/signout')
}

document.addEventListener('click', () => {
  if (VueCookie.get('authrizeduser') !== null) {
    timer.resetTimer()
  }
})
*/

/* eslint-disable no-new */
/*
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App, LiquorTree }
})
*/

/* Authentication by EPISODE JWT  end */
