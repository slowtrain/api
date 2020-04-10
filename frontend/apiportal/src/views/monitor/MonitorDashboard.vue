<!--
 Class Name  : MonitorDashboard.vue
 Description : 모니터 대시보드
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.23  이정호  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <DashboardRpa
      v-show="readAuth"
      v-bind:labels="labels"
      v-bind:cycle="rpaCycle"
      v-bind:critical="e2eCritical"
      v-bind:warning="e2eWarning"
    />
    <DashboardEvent
      v-show="readAuth"
      v-bind:labels="labels"
      v-bind:cycle="eventCycle"
      v-bind:delayHour="eventDelay"
      v-bind:severityData="severityData"
    />
 </div>
</template>

<script>
import DashboardEvent from '@/views/monitor/DashboardEvent'
import DashboardRpa from '@/views/monitor/DashboardRpa'
export default {
  name: 'MONITORDASHBOARD',
  components: {
    DashboardEvent, DashboardRpa
  },
  props: {
    confirmResult: {
      type: Boolean
    }
  },
  data: function () {
    return {
      rpaCycle: 1800,
      eventCycle: 60,
      e2eCritical: 10,
      e2eWarning: 5,
      eventDelay: 24,
      severityData: {},
      readAuth: false,
      labels: {
        rpaTitle: '',
        rpaTopTitle: '',
        eventTitle: '',
        busiTitle: '',
        previousMonth: '',
        previousDay: '',
        day: '',
        month: '',
        server: '',
        network: '',
        was: '',
        db: '',
        business: '',
        delay: '',
        second: '',
        pdStandard: '',
        moveMsg: ''
      }
    }
  },
  methods: {
    initialize: function () {
      if (this.$util.readAuth(this, 'MONITORDASHBOARD_READ')) {
        this.readAuth = true
      }
      let orgvariable = this.$store.getters.getOrgvariable
      this.$util.setLabels(this.$options.name, this.labels)
      if (orgvariable.length !== 0) {
        this.rpaCycle = parseInt(orgvariable.filter(r => r.key === 'DSRPA')[0].value)
        this.eventCycle = parseInt(orgvariable.filter(r => r.key === 'DSEVENT')[0].value)
        this.e2eCritical = parseInt(orgvariable.filter(r => r.key === 'E2E-CRITICAL')[0].value)
        this.e2eWarning = parseInt(orgvariable.filter(r => r.key === 'E2E-WARNING')[0].value)
        this.eventDelay = parseInt(orgvariable.filter(r => r.key === 'EVENTDELAY')[0].value)
      }
      this.$http.get('/episode/api/system/common/domain?domainId=SEVERITYCOLOR').then(res => {
        this.setSeverity(res.data)
      }).catch(error => {
        console.log(error)
      })
    },
    setSeverity: function (data) {
      data.forEach(row => {
        this.severityData[row.key] = row.value
      })
    }
  },
  created () {
    this.initialize()
  }

}
</script>
