<template>
  <div>
    <router-view></router-view>
    <Message v-if="messageFlag" :messageText="messageText" :messageType="messageType"/>
    <Confirm v-if="confirmFlag" :confirmText="confirmText" :confirmType="confirmType"/>
  </div>
</template>

<script>
const Confirm = () => import('@/views/message/Confirm')
const Message = () => import('@/views/message/Message')

export default {
  el: '#app',
  name: 'app',
  components: {
    Confirm, Message
  },
  data () {
    return {
      messageFlag: false,
      messageText: '',
      messageType: '',

      confirmFlag: false,
      confirmText: '',
      confirmType: '',

      labels: {
        loginErr: ''
      }
    }
  },
  mounted () {
    this.$util.setLabels('MESSAGE', this.labels)
  },
  methods: {
    callBackFunc: function () {},

    esconfirm: function (type, msg, complete, cancle) {
      this.callBackFunc = complete
      this.callBackFuncCancle = cancle
      this.confirmFlag = true
      this.confirmText = msg
      this.confirmType = type
    },

    hideConfirm: function () {
      this.confirmFlag = false
    },

    esmessage: function (type, msg) {
      this.messageType = type
      this.messageText = msg
      this.messageFlag = true
    },

    hideMessage: function () {
      this.messageFlag = false
    },

    headerVisible: function (compo) {
      let header = this.$route.meta.header
      return (compo === header)
    },

    sidebarVisible: function (compo) {
      let sidebar = this.$route.meta.sidebar
      return (compo === sidebar)
    }
  }
}
</script>

<style lang="scss">
  // CoreUI Icons Set
  @import '@coreui/icons/css/coreui-icons.min.css';
  /* Import Font Awesome Icons Set */
  $fa-font-path: '~font-awesome/fonts/';
  @import 'font-awesome/scss/font-awesome.scss';
  /* Import Simple Line Icons Set */
  $simple-line-font-path: '~simple-line-icons/fonts/';
  @import 'simple-line-icons/scss/simple-line-icons.scss';
  /* Import Flag Icons Set */
  @import 'flag-icon-css/css/flag-icon.min.css';
  /* Import Bootstrap Vue Styles */
  @import 'bootstrap-vue/dist/bootstrap-vue.css';
  // Import Main styles for this application
  @import 'assets/scss/style';

  // @import 'assets/css/common.css';
  @import 'assets/css/episode.css';
</style>
