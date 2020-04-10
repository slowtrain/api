import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: null,
    auth: [],
    apps: [],
    lang: null,
    login_dt: null,
    orgvariable: []
  },

  getters: {
    getState: state => state,

    getUser: state => state.user,

    getAuth: state => state.auth,

    getApps: state => state.apps,

    getLang: state => state.lang,

    getOrgvariable: state => state.orgvariable
  },

  mutations: {
    toEmpty (state) {
      state.login_dt = null
      state.user = null
      state.apps = []
      state.lang = null
      state.orgvariable = []
    },

    setTime (state) {
      state.login_dt = new Date()
    },

    setUser (state, user) {
      state.user = user
    },

    setAuth (state, auth) {
      state.auth = auth
    },

    setApps (state, apps) {
      state.apps = apps
    },

    setLang (state, lang) {
      state.lang = lang
      console.log(state.lang)
    },

    setOrgvariable (state, orgvariable) {
      state.orgvariable = orgvariable
    }
  }
})
