<!--
 Class Name  : CiEventList.vue
 Description : CI 이벤트 리스트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.23  이정호  최초 생성
-->
<template>
  <el-dialog :title="labels.title" :visible.sync="dialogFormVisible">
    <b-row>
      <b-col>
        <b-card no-body>
          <b-card-body class='card-body sm' style="height: 300px;">
            <div v-if='ciEventData.length !== 0' >
              <div class="block">
                <el-timeline style="padding-left: 0px;">
                  <CiEvent
                    v-for='(event, index) in ciEventData'
                    v-bind:key="index"
                    v-bind:data='event'
                    v-bind:severityClass='severityColor'
                    v-bind:vueName='vueName'
                    v-bind:labels='labels'
                    @close='popupClose'
                    />
                </el-timeline>
              </div>
            </div>
            <div v-if='ciEventData.length === 0' class='el-table__empty-text'>
              {{labels.noEventData}}
            </div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
    <span slot="footer" class="dialog-footer">
      <b-button variant="primary" v-on:click="popupClose"  class="px-3">{{labels.close}}</b-button>
    </span>
  </el-dialog>
</template>

<script>
import {eventBus} from '@/eventBus/eventBus'
import CiEvent from '@/views/common/CiEvent'
export default {
  name: 'CIEVENTLIST_POPUP',
  components: {
    CiEvent
  },
  data () {
    return {
      dialogFormVisible: false,
      severityColor: {},
      ciEventData: [],
      vueName: '',
      labels: {
        title: '',
        event: '',
        close: '',
        noEventData: '',
        topologyOpen: '',
        moveMonitor: '',
        moveCi: ''
      }
    }
  },
  methods: {
    initialize: function () {
      this.$http.get('/episode/api/system/common/domain?domainId=SEVERITYCOLOR').then(res => {
        this.setSeverityColor(res.data)
      }).catch(error => {
        console.log(error)
      })
      this.$util.setLabels(this.$options.name, this.labels)
    },
    setSeverityColor: function (data) {
      data.forEach(row => {
        this.severityColor[row.key] = row.value
      })
    },
    getCiEvent: function (ciId) {
      this.$http.get(
        '/episode/api/event/cievent/' + ciId
      ).then(res => {
        this.ciEventData = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getSystemEvent: function (ciSystem, severity) {
      let cTime = this.$util.timestampDate(new Date())
      let params = {params: {
        ciSystem: ciSystem,
        severity: severity,
        cTime: cTime}
      }
      this.$http.get(
        '/episode/api/event/systemevent',
        params
      ).then(res => {
        this.ciEventData = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getBizEvent: function (severity, ciId) {
      let params = {params: {
        severity: severity,
        bizCiId: ciId}
      }
      this.$http.get(
        '/episode/api/event/bizevent',
        params
      ).then(res => {
        this.ciEventData = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getTopologEvent: function (ciId) {
      this.$http.get(
        '/episode/api/event/topologyevent/' + ciId
      ).then(res => {
        this.ciEventData = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    dateTime: function (date) {
      return this.$util.dateTime(date)
    },
    popupClose: function () {
      this.dialogFormVisible = false
    }
  },
  mounted () {
    let $this = this
    eventBus.$off('ciEventListOpen')
    eventBus.$on('ciEventListOpen', function (ciId) {
      $this.dialogFormVisible = true
      $this.initialize()
      $this.getCiEvent(ciId)
    })
    eventBus.$off('systemEventListOpen')
    eventBus.$on('systemEventListOpen', function (ciSystem, severity, vueName) {
      $this.dialogFormVisible = true
      $this.vueName = vueName
      $this.initialize()
      $this.getSystemEvent(ciSystem, severity)
    })
    eventBus.$off('bizEventListOpen')
    eventBus.$on('bizEventListOpen', function (severity, ciId, vueName) {
      $this.dialogFormVisible = true
      $this.vueName = vueName
      $this.initialize()
      $this.getBizEvent(severity, ciId)
    })
    eventBus.$off('topologyEventOpen')
    eventBus.$on('topologyEventOpen', function (id, vueName) {
      $this.dialogFormVisible = true
      $this.vueName = vueName
      $this.initialize()
      $this.getTopologEvent(id)
    })
  },

  created () {
    this.initialize()
  }
}
</script>
