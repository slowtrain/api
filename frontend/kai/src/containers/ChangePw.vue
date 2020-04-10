<!--
 File Name  : ChangePw.vue
 Description : 사용자 비밀번호 변경 팝업
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.06  이정호  최초 생성
-->
<template>
  <el-dialog :title="labels.chagnePwTitle" :visible="true" width="500px" :show-close="false">
        <el-form label-width="120px">
          <div class="row mt-2">
            <div class="col-md-4">
              <label>{{labels.firstname}}</label>
            </div>
            <div class="col-md-8">
              <input class="formdata sm" v-model="user.firstname" autocomplete="off" disabled="disabled"/>
            </div>
          </div>
          <div class="row mt-2">
            <div class="col-md-4">
              <label>{{labels.username}}</label>
            </div>
            <div class="col-md-8">
              <input class="formdata sm" v-model="user.username" autocomplete="off" disabled="disabled"/>
            </div>
          </div>
          <div class="row mt-2">
            <div class="col-md-4">
              <label>{{labels.password}}<span class="require-mark">*</span></label>
            </div>
            <div class="col-md-8">
              <input class="formdata sm" v-model="passwordChange.pw" autocomplete="off" type='password' v-on:blur="getPwChk" maxlength="20"/>
              <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.pwChk)" v-text="validMsg.pwChk"></p>
            </div>
          </div>
          <div class="row mt-2">
            <div class="col-md-4">
              <label>{{labels.newPassword}}<span class="require-mark">*</span></label>
            </div>
            <div class="col-md-8">
              <input class="formdata sm" v-model="passwordChange.newPw" autocomplete="off" type='password' v-on:blur="blurPw" maxlength="20"/>
              <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.newPw)" v-text="validMsg.newPw"></p>
            </div>
          </div>
          <div class="row mt-2">
            <div class="col-md-4">
              <label>{{labels.confirmNewPassword}}<span class="require-mark">*</span></label>
            </div>
            <div class="col-md-8">
              <input class="formdata sm" v-model="passwordChange.confirmNewPw" autocomplete="off" type='password' v-on:blur="confirmPw" maxlength="20"/>
              <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.confirmNewPw)" v-text="validMsg.confirmNewPw"></p>
            </div>
          </div>
        </el-form>
      <span slot="footer" class="dialog-footer">
        <b-button class='px-3 sm' variant="primary" v-on:click="pwSave">{{labels.save}}</b-button>
        <b-button class='px-3 sm' variant="primary" v-on:click="close">{{labels.close}}</b-button>
      </span>
    </el-dialog>
</template>

<script>
export default {
  name: 'DefaultContainer',
  props: ['labels', 'dateFlag'],
  data () {
    return {
      user: {},
      passwordChange: {
        pw: '',
        newPw: '',
        confirmNewPw: ''
      },
      dialogFormVisible: false,
      validMsg: {
        pwChk: '',
        newPw: '',
        confirmNewPw: ''
      }
    }
  },
  methods: {
    initialize: function () {
      this.user = this.$store.getters.getUser
    },
    getPwChk: function () {
      if (!this.$util.isNull(this.passwordChange.pw)) {
        this.$http.put(
          '/episode/api/sidebar/userpw/chk/' + this.user.id, this.passwordChange
        ).then(res => {
          this.validMsg.pwChk = ''
        }).catch(error => {
          console.log(error)
          if (error.response.data === 0) {
            this.validMsg.pwChk = this.labels.pwChkMsg
          }
        })
      } else {
        this.validMsg.pwChk = this.labels.pwChk
      }
      if (this.validMsg.pwChk !== '') {
        return false
      } else {
        return true
      }
    },
    blurPw: function () {
      var pw = this.passwordChange.newPw
      var num = pw.search(/[0-9]/g)
      var eng = pw.search(/[a-z]/ig)
      var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi)
      if (this.$util.isNull(pw)) {
        this.validMsg.newPw = this.labels.newPwMsg
        return false
      }

      if (num < 0 || eng < 0 || spe < 0) {
        this.validMsg.newPw = this.labels.pwMsg
        return false
      } else if (pw.length < 9 || pw.length > 20) {
        this.validMsg.newPw = this.labels.pwlengthMessage
        return false
      } else if (/.*(.)\1\1.*/.test(pw)) {
        this.validMsg.newPw = this.labels.pwtextMessage
        return false
      } else if (pw.search(this.user.username) > -1) {
        this.validMsg.newPw = this.labels.pwSameMessage
        return false
      } else if (pw === this.passwordChange.pw) {
        this.validMsg.newPw = this.labels.pwSameMsg
        return false
      } else {
        this.validMsg.newPw = ''
        return true
      }
    },
    confirmPw: function () {
      if (this.$util.isNull(this.passwordChange.newPw)) {
        this.validMsg.newPw = this.labels.newPwMsg
        this.passwordChange.confirmNewPw = ''
        return false
      } else if (this.$util.isNull(this.passwordChange.confirmNewPw)) {
        this.validMsg.confirmNewPw = this.labels.confirmPw
        return false
      } else if (this.passwordChange.newPw !== this.passwordChange.confirmNewPw) {
        this.validMsg.confirmNewPw = this.labels.pwConfirm
        return false
      } else {
        this.validMsg.confirmNewPw = ''
        return true
      }
    },
    pwSave: function () {
      this.user.password = this.passwordChange.confirmNewPw

      let getPwChk = this.getPwChk()
      let blurPw = this.blurPw()
      let confirmPw = this.confirmPw()

      if (getPwChk && blurPw && confirmPw) {
        this.$http.put(
          '/episode/api/sidebar/userpw/' + this.user.id, this.user
        ).then(res => {
          if (this.user.changepassword) {
            this.user.changepassword = false
          }
          this.$util.esmessage(this, 'check', this.labels.saveMessage)
          this.$emit('changePwClose')
          this.$emit('changeFlag')
        }).catch(error => {
          console.log(error)
          if (error.response.data === 0) {
            this.$util.esmessage(this, 'warn', this.labels.pwMsg)
          } else if (error.response.data === 1) {
            this.$util.esmessage(this, 'warn', this.labels.pwBeforeMessage)
          } else {
            this.$util.esmessage(this, 'warn', this.labels.failMessage)
          }
        })
      }
    },
    close: function () {
      this.$emit('changePwClose')
      // if (this.user.changepassword) {
      //   this.$util.esmessage(this, 'warn', this.labels.pwNecessary)
      // } else if (this.dateFlag) {
      //   this.$util.esmessage(this, 'warn', this.labels.pwChangeMessage)
      // } else {
      //   this.$emit('changePwClose')
      // }
    }
  },
  mounted () {
    this.dialogFormVisible = true
    this.passwordChange.confirmNewPw = ''
    this.passwordChange.newPw = ''
    this.passwordChange.pw = ''
    for (let key in this.validMsg) {
      this.validMsg[key] = ''
    }
  },
  created () {
    this.initialize()
  }
}
</script>
