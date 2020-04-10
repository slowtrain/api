<template>
  <div class="dimmed-film" style="z-index: 9999;">
    <div class="tiny-popup-wrap">
      <span :class="iconClass"></span>
      <p class="paragraph">
        <strong class="normal" v-text="messageText"></strong>
      </p>
      <div class="popup-btn-center">
        <b-button class='px-3 sm' variant="primary" v-on:click="popupClose">{{labels.ok}}</b-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MESSAGE',

  data () {
    return {
      labels: {
        ok: ''
      },
      iconClass: 'popup-img ' + this.messageType
    }
  },

  props: {
    messageText: {
      type: String
    },
    messageType: {
      type: String
    }
  },

  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
    },

    popupClose: function () {
      this.$parent.hideMessage()
    }
  },

  created () {
    this.initialize()
  }
}
</script>
