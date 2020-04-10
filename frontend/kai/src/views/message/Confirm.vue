<template>
  <div class="dimmed-film" style="z-index: 9999;">
    <div class="tiny-popup-wrap">
      <span :class="iconClass"></span>
      <p class="paragraph">
        <strong class="normal" v-text="confirmText"></strong>
      </p>
      <div class="popup-btn-center">
        <b-button class='px-3 sm' variant="primary" v-on:click="onClickYes">{{labels.yes}}</b-button>
        <b-button class='px-3 sm' variant="primary" v-on:click="onClickNo">{{labels.no}}</b-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CONFIRM',

  data () {
    return {
      labels: {
        yes: '',
        no: ''
      },
      iconClass: 'popup-img ' + this.confirmType
    }
  },

  props: {
    confirmText: {
      type: String
    },
    confirmType: {
      type: String
    }
  },

  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
    },

    popupClose: function () {
      this.$parent.hideConfirm()
    },

    onClickYes: function () {
      this.$parent.callBackFunc()
      this.popupClose()
    },

    onClickNo: function () {
      if (!this.$util.isNull(this.$parent.callBackFuncCancle)) {
        this.$parent.callBackFuncCancle()
      }
      this.popupClose()
    }
  },

  created () {
    this.initialize()
  }
}
</script>
