<!--
 Class Name  : JobBox.vue
 Description : IT Infra 현황 > 대시보드 > 작업 현황 > 컴포넌트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.15  이정호  최초 생성
-->
<template>
  <div class="brand-card sm">
    <div class="brand-card-header ep" :style='cfStyle(data.cf)'>
      <b>{{data.title}}</b>
    </div>
    <div class="brand-card-body">
      <div>
        <div class="text-value" style="color: red; cursor: pointer;" v-on:click="move">{{data.undecided}}</div>
        <div class="text-uppercase text-muted small">{{uLabel()}}</div>
      </div>
      <div>
        <div class="text-value" style="cursor: pointer;" v-on:click="moveSafe">{{data.confirm}}</div>
        <div class="text-uppercase text-muted small">{{cLabel()}}</div>
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
      return (key) => {
        if (key === 'JOBCI') {
          return {backgroundColor: '#58A2F7'}
        } else {
          return {backgroundColor: '#0A317F'}
        }
      }
    }
  },
  methods: {
    initialize: function () {
    },
    uLabel: function () {
      if (this.data.key === 'mgConfirmData') {
        return this.labels.mgUndecided
      } else if (this.data.key === 'swLinkData') {
        return this.labels.swUnlinked
      } else {
        return this.labels.inconsistency
      }
    },
    cLabel: function () {
      if (this.data.key === 'mgConfirmData') {
        return this.labels.mgConfirm
      } else if (this.data.key === 'swLinkData') {
        return this.labels.swLink
      } else {
        return this.labels.same
      }
    },
    move: function () {
      if (this.data.cf === 'JOBEMS') {
        if (!this.$util.readAuth(this, 'DISCI_READ')) {
          this.$util.esmessage(this, 'warn', this.labels.moveMsg)
          return
        }
        this.$router.push(
          {
            name: 'DISCI',
            params: {datasrc: 'WATCHALL', isdelete: 'FALSE', matching: 'FALSE', discitype: this.data.key}
          })
      } else {
        if (this.data.key === 'swcatData') {
          if (!this.$util.readAuth(this, 'SWCAT_READ')) {
            this.$util.esmessage(this, 'warn', this.labels.moveMsg)
            return
          }
          this.$router.push(
            {
              name: 'SWCAT',
              params: {swcattypeD: 'PRODUCT', swctr: 'FALSE'}
            })
        } else {
          if (!this.$util.readAuth(this, 'CI_READ')) {
            this.$util.esmessage(this, 'warn', this.labels.moveMsg)
            return
          }
          let pushData = {jobCi: this.data.key, ancestor: '', confirm: 'FALSE'}
          if (this.data.key === 'swLinkData') {
            pushData.ancestor = 'SW_DBSW,SW_MIDDLESW_WAS'
          } else {
            pushData.ancestor = 'HARDWARE,SOFTWARE'
          }
          this.$router.push(
            {
              name: 'CI',
              params: pushData
              // params: {jobCi: this.data.key, cf: 'undecided'}
            })
        }
      }
    },
    moveSafe: function () {
      if (this.data.cf === 'JOBEMS') {
        if (!this.$util.readAuth(this, 'DISCI_READ')) {
          this.$util.esmessage(this, 'warn', this.labels.moveMsg)
          return
        }
        this.$router.push(
          {
            name: 'DISCI',
            params: {datasrc: 'WATCHALL', isdelete: 'FALSE', matching: 'TRUE', discitype: this.data.key}
          })
      } else {
        if (this.data.key === 'swcatData') {
          if (!this.$util.readAuth(this, 'SWCAT_READ')) {
            this.$util.esmessage(this, 'warn', this.labels.moveMsg)
            return
          }
          this.$router.push(
            {
              name: 'SWCAT',
              params: {swcattypeD: 'PRODUCT', swctr: 'TRUE'}
            })
        } else {
          if (!this.$util.readAuth(this, 'CI_READ')) {
            this.$util.esmessage(this, 'warn', this.labels.moveMsg)
            return
          }
          let pushData = {jobCi: this.data.key, ancestor: '', confirm: 'TRUE'}
          if (this.data.key === 'swLinkData') {
            pushData.ancestor = 'SW_DBSW,SW_MIDDLESW_WAS'
          } else {
            pushData.ancestor = 'HARDWARE,SOFTWARE'
          }
          this.$router.push(
            {
              name: 'CI',
              params: pushData
              // params: {jobCi: this.data.key, cf: 'confirm'}
            })
        }
      }
    }
  },
  created () {
    this.initialize()
  }
}
</script>
