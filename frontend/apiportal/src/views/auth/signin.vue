<template>
  <section class="signin">
    <div class="container-scroller">
      <div class="container-fluid page-body-wrapper full-page-wrapper">
        <div class="content-wrapper d-flex align-items-center auth">
          <div class="row w-100 flex-grow">
            <div class="col-xl-4 col-lg-6 mx-auto">
              <div class="auth-form-light text-left p-5">
                <div class="brand-logo">
                  <img src="../../assets/images/episode.svg" />
                </div>
                <h4>Welcome! Open API Developer Portal</h4>
                <h6 class="font-weight-light">Sign in to continue.</h6>
                <form class="pt-3" @submit.prevent="signIn(username, password)">
                  <div class="form-group">
                    <!-- <input type="email" class="form-control form-control-lg" id="exampleInputEmail1" placeholder="Username"/> -->
                    <input type="text" class="form-control" placeholder="Username" autocomplete="username email" v-model="username"  />
                  </div>
                  <br>
                  <div class="form-group">
                    <!-- <input type="password" class="form-control form-control-lg" id="exampleInputPassword1" placeholder="Password"/> -->
                    <input type="password" class="form-control" placeholder="Password" autocomplete="current-password" v-model="password"  />
                  </div>
                  <!--
                  <div class="mt-3">
                    <router-link class="btn btn-block btn-gradient-primary btn-lg font-weight-medium auth-form-btn" to="/">SIGN IN</router-link>
                  </div>
                  -->
                  <div class="my-2 d-flex justify-content-between align-items-center">
                    <div class="form-check">
                      <label class="form-check-label text-muted">
                        <input type="checkbox" class="form-check-input" />
                        Keep me signed in
                        <i class="input-helper"></i>
                      </label>
                    </div>
                    <a href="javascript:void(0);" class="auth-link text-black">Forgot password?</a>
                  </div>
                  <div class="mb-2">
                    <!--
                    <button type="button" class="btn btn-block btn-facebook auth-form-btn">
                      <i class="ti-facebook mr-2"></i>Connect using facebook
                    </button>
                    -->
                    <button type="button" class="btn btn-block btn-facebook auth-form-btn" v-on:click="signIn">
                      Sign In
                    </button>
                  </div>
                  <div class="text-center mt-4 font-weight-light">
                    Don't have an account?
                    <router-link to="/auth/register" class="text-primary">Create</router-link>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
        <!-- content-wrapper ends -->
      </div>
      <!-- page-body-wrapper ends -->
    </div>
  </section>
</template>

<script>
import timer from '../../timer/timer'

export default {
  name: 'siginin',
  data () {
    return {
      username: '',
      password: '',
      passwordDialog: false,

      blurPwdNull: false,
      blurPwdType: false,
      blurChkPwdNull: false,
      blurChkPwdMatch: false,
      errorPop: false,
      errorText: '',

      langList: [],
      selectLang: 'ko-kr',

      currentData: {
        id: '',
        username: '',
        password: '',
        chk_password: ''
      },

      labels: {
        title: '',
        account: '',
        loginErr: ''
      }
    }
  },

  methods: {
    initialize () {
      this.$util.setLabels(this.$options.name, this.labels)
      this.$http.get('/episode/api/system/common/domain?domainId=LANG'
      ).then(res => {
        this.langList = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    signIn () {
      const { username } = this
      const { password } = this
      this.$http.post(
        '/episode/api/authentication/signin', { username, password }
      ).then((res) => {
        this.$store.commit('setUser', res.data.user)
        this.$store.commit('setApps', res.data.apps)
        this.$store.commit('setAuth', res.data.auth)
        this.$router.push('/')
        timer.startTimer()
      }).catch((error) => {
        // this.$parent.esmessage('warn', this.labels.loginErr)
        console.log(error)
      })
    },

    savePassword () {
      this.$http.post(
        '/episode/api/authentication/signup',
        this.currentData
      ).then((res) => {
        console.log(res)
      }).catch((error) => {
        console.log(error)
      })
    },

    blurPassword () {
      if (typeof this.currentData.password === 'undefined' || this.currentData.password === '') {
        this.blurPwdNull = true
        this.blurPwdType = false

        return null
      }

      this.blurPwdNull = false
      this.blurPwdType = false
    },

    confirmPassword () {
      if (typeof this.currentData.chk_password === 'undefined' || this.currentData.chk_password === '') {
        this.blurChkPwdNull = true
        this.blurChkPwdMatch = false

        return null
      }

      if (this.currentData.password !== this.currentData.chk_password) {
        this.blurChkPwdNull = false
        this.blurChkPwdMatch = true

        return null
      }

      this.blurChkPwdNull = false
      this.blurChkPwdMatch = false
    },
    changeLang () {
      this.$http.get(
        '/episode/api/system/label?appId=' + this.$options.name + '&lang=' + this.selectLang
      ).then(response => {
        let data = response.data
        for (let i = 0; i < data.length; i++) {
          this.labels[data[i].name] = data[i].description
        }
      }).catch((error) => {
        console.log(error)
      })
    }
  },

  mounted () {
    this.initialize()

    // gate.introAnimationStart()
    /*
    this.$http.get(
      'http://sso.koreaaero.com/isignplus/api/Miverify.jsp', {'v1': this.$cookie.get('LtpaToken2')}
    ).then(res => {
      console.log(res)
    }).catch(error => {
      console.log(error)
    })
    */
  }
}
</script>
<style lang="scss">


</style>
