<!--
 Class Name  : ScanBox.vue
 Description : IT Infra 현황 > 대시보드 > Scan 현황 > 컴포넌트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.15  이정호  최초 생성
-->
<template>
  <div class="brand-card sm">
    <div class="brand-card-header ep" :style='cfStyle()'>
      <b>{{data.title}}</b>
    </div>
    <div class="brand-card-body">
      <div>
        <div class="text-value" style="color: red; cursor: pointer;" v-on:click="move">{{data.cnt}}</div>
        <div class="text-uppercase text-muted small">{{this.labels.needMsg}}</div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'ASSETDASHBOARD',
  props: ['data', 'labels'],
  data () {
    return {
    }
  },
  computed: {
    cfStyle () {
      return () => {
        return {backgroundColor: '#315680'}
      }
    }
  },
  methods: {
    initialize: function () {
    },
    move: function () {
      if (!this.$util.readAuth(this, 'CI_READ')) {
        this.$util.esmessage(this, 'warn', this.labels.moveMsg)
        return
      }
      this.$router.push(
        {
          name: 'CI',
          params: {ancestor: this.data.key, scan: 'FALSE', ancestorId: this.data.id}
        })
    }
  },
  created () {
    this.initialize()
  }
}
</script>
