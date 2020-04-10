<template>
  <div class="app flex-row align-items-center login-section" style="height:100px;">
    <div class="container">
      <b-row class="justify-content-center">
        <b-col md="8">
          <b-card-group>
            <b-card no-body class="p-4">
              <b-card-body>
                <b-form @submit.prevent="signIn(username, password)">
                  <h1>관리자</h1>
                  <!--
                  <h1>{{labels.title}}</h1>
                  <b-row>
                  <div class="form-group col-md-7">
                  <p class="text-muted">{{labels.account}}</p>
                  </div>
                  <div class="form-group col-md-5">
                    <el-select v-model="selectLang" v-on:change="changeLang" style="width:100%" >
                      <el-option v-for="lang in langList" :value="lang.value" :label="lang.description" :key="lang.key" >
                       </el-option>
                    </el-select>
                  </div>
                  </b-row>
                  -->
                  <b-input-group class="mb-3">
                    <b-input-group-prepend><b-input-group-text><i class="icon-user"></i></b-input-group-text></b-input-group-prepend>
                    <b-form-input type="text" class="form-control" placeholder="Username" autocomplete="username email" v-model="username" v-on:keyup="loginKeyCheck" />
                  </b-input-group>
                  <b-input-group class="mb-4">
                    <b-input-group-prepend><b-input-group-text><i class="icon-lock"></i></b-input-group-text></b-input-group-prepend>
                    <b-form-input type="password" class="form-control" placeholder="Password" autocomplete="current-password" v-model="password" v-on:keyup.enter="signIn" v-on:keyup="loginKeyCheck"/>
                  </b-input-group>
                  <b-row>
                    <b-col cols="6">
                      <b-button variant="primary" class="px-4" v-on:click="signIn">Login</b-button>
                    </b-col>
                    <b-col cols="6" class="text-right">
                      <!-- <b-button variant="link" class="px-0">Forgot password?</b-button> -->
                    </b-col>
                  </b-row>
                </b-form>
              </b-card-body>
            </b-card>
            <b-card no-body class="text-white bg-primary py-5 d-md-down-none" style="width:44%">
              <b-card-body class="text-center">
                <div>
                  <img src="../../assets/img/ICAM_LOGO.svg" style="width:300px">
                  <img src="../../assets/img/login/KAI.png" style="width:120px">
                  <p>Korea Aerospace Industries,Ltd.</p>
                  <!--
                  <b-button variant="primary" class="active mt-3">Register Now!</b-button>
                  -->
                </div>
              </b-card-body>
            </b-card>
          </b-card-group>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
import timer from '../../timer/timer'
import gate from '../../assets/js/gate'

export default {
  name: 'GATE',
  data () {
    return {
      GATE: gate,
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
        this.$parent.esmessage('warn', this.labels.loginErr)
        console.log(error)
      })
    },

    loginKeyCheck () {
      gate.loginKeyCheck()
    },

    skipIntroAnimation () {
      gate.skipIntroAnimation()
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
