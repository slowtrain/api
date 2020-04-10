import Vue from 'vue'
import axios from 'axios'
import router from '../router/index'
import store from '../store/store'
import VueCookie from 'vue-cookie'

Vue.use(axios)
Vue.use(router)

const timoutWarning = 1000 * 60 * 57 * 12
const timoutNow = 1000 * 60 * 60 * 12
let warningTimer
let timeoutTimer

export default {
  idleWarning () {
    console.log('Will be signed out after 3 minutes')
  },
  idleTimeout () {
    axios.post(
      '/episode/api/authentication/signout'
    ).then(() => {
      router.push('/gate')
      store.commit('toEmpty')
      VueCookie.delete('kaiicamsecret')
    })
  },
  startTimer () {
    clearTimeout(warningTimer)
    clearTimeout(timeoutTimer)
    warningTimer = setTimeout(this.idleWarning, timoutWarning)
    timeoutTimer = setTimeout(this.idleTimeout, timoutNow)
  },
  resetTimer () {
    clearTimeout(warningTimer)
    clearTimeout(timeoutTimer)
    this.startTimer()
  }
}
