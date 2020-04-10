<!--
 Class Name  : CiMonitorDetail.vue
 Description : 장비별 대시보드
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.02  이정호  최초 생성
-->
<template>
  <div class="wrapper">
    <div class="animated fadeIn">
      <b-card>
        <b-row>
          <b-col sm="12">
           <div class='row'>
            <div class='col-md-12'>
              <p class="h4">{{labels.title}}</p>
            </div>
          </div>
        </b-col>
      </b-row>
      <b-row>
        <b-col sm="6">
         <b-card no-body>
          <div class='card-header head-sm'>
            {{labels.information}}
          </div>
          <b-card-body class='card-body sm'>
            <b-form v-if="monitorFlag">
              <b-row>
                <b-col sm="6">
                  <b-form-group description="" :label="labels.ci" label-for="ci" :label-cols="3">
                   <div class="search-component sm">
                   <b-form-input id="ci" type="text" v-model="ciData.ciId" :disabled="true"></b-form-input>
                  <el-popover placement="right" width="120" trigger="click">
                  <el-button size="mini" type="text" v-on:click="moveCI">{{labels.moveCi}}</el-button>
                  <br>
                  <el-button size="mini" type="text" v-on:click="topologyOpen">{{labels.topologyOpen}}</el-button>
                  <a slot="reference" class="action-button cursor-pointer">search</a>
                </el-popover>
                </div>
                  </b-form-group>
            </b-col>
            <b-col sm="6">
              <b-form-group description="" :label="labels.name" label-for="name" :label-cols="3">
                <b-form-input id="name" type="text" v-model="ciData.ciname" :disabled="true"></b-form-input>
            </b-form-group>
            </b-col>
          </b-row>
          <p class='event-line'></p>
           <b-row>
            <b-col sm="6">
              <b-form-group description="" :label="labels.classstructure" label-for="classstructure" :label-cols="3">
                <b-form-input id="classstructure" type="text" v-model="ciData.classstructureNm" :disabled="true"></b-form-input>
              </b-form-group>
            </b-col>
            <b-col sm="6">
              <b-form-group description="" :label="labels.ipAddress" label-for="ipAddress" :label-cols="3">
                   <div class="search-component sm" v-popover:ipPopover>
                   <b-form-input id="ipAddress"  class='os cursor-pointer' type="text" v-model="ciData.extaln17" :disabled="true"></b-form-input>
                  <el-popover ref="ipPopover" placement="right" width="300" trigger="focus" :content="ciData.extaln17" ></el-popover>
                </div>
              </b-form-group>
              </b-col>
          </b-row>
         <p class='event-line'></p>
           <b-row>
            <b-col sm="6">
              <b-form-group description="" :label="labels.os" label-for="os" :label-cols="3">
                   <div class="search-component sm" v-popover:osPopover>
                   <b-form-input id="os"  class='os cursor-pointer' type="text" v-model="ciData.extaln7" :disabled="true"></b-form-input>
                  <el-popover ref="osPopover" placement="right" width="300" trigger="focus" :content="ciData.extaln7" ></el-popover>
                </div>
              </b-form-group>
            </b-col>
            <b-col sm="6">
              <b-form-group description="" :label="labels.operation" label-for="operation" :label-cols="3">
                <b-form-input id="operation" type="text" v-model="ciData.type" :disabled="true"></b-form-input>
              </b-form-group>
            </b-col>
          </b-row>
           <p class='event-line'></p>
           <b-row>
            <b-col sm="6">
              <b-form-group description="" :label="labels.location" label-for="location" :label-cols="3">
                <b-form-input id="location" type="text" v-model="ciData.locationId" :disabled="true"></b-form-input>
              </b-form-group>
            </b-col>
            <b-col sm="6">
              <b-form-group description="" :label="labels.manager" label-for="manager" :label-cols="3">
                <b-form-input id="manager" type="text" v-model="managerName" :disabled="true"></b-form-input>
              </b-form-group>
            </b-col>
          </b-row>
           <p class='event-line'></p>
           <b-row>
           <b-col sm="6">
              <b-form-group v-show="systemName === 'SERVER'" description="" :label="labels.cpu" label-for="cpu" :label-cols="3">
                <b-form-input id="cpu" type="text" v-model="cpuD" :disabled="true"></b-form-input>
              </b-form-group>
              <b-form-group v-show="systemName === 'NETWORK'" description="" :label="labels.importances" label-for="cpu" :label-cols="3">
                <b-form-input id="cpu" type="text" v-model="ciData.extaln8" :disabled="true"></b-form-input>
              </b-form-group>
            </b-col>

            <b-col sm="6">
              <b-form-group v-show="systemName === 'SERVER'" description="" :label="labels.memory" label-for="memory" :label-cols="3">
                <b-form-input id="memory" type="text" v-model="memD" :disabled="true"></b-form-input>
              </b-form-group>
            </b-col>
          </b-row>
        </b-form>
            </b-card-body>
          </b-card>

        <b-card no-body v-show='systemName === "SERVER"'>
          <div class='card-header head-sm'>
            {{labels.performance}}
          </div>
          <b-card-body>
      <b-row>
        <b-col>
          <ServerPerf
          v-bind:labels="labels"
          v-bind:ciData="ciData"
          v-bind:system="systemName"
          v-bind:cycle="cyclePref"
          v-bind:datePref="datePref"
          ref="serverPerf"
           />
        </b-col>
      </b-row>
      </b-card-body>
        </b-card>
        <b-card no-body v-show='systemName === "NETWORK"'>
          <div class='card-header head-sm'>
            {{labels.resTime}}
          </div>
          <b-card-body>
      <b-row>
        <b-col>
          <NetPerf
          v-bind:labels="labels"
          v-bind:ciData="ciData"
          v-bind:system="systemName"
          v-bind:cycle="cyclePref"
          v-bind:datePref="datePref"
          ref="netPerf"
           />
        </b-col>
      </b-row>
      </b-card-body>
        </b-card>
        <b-card no-body v-show='systemName === "SERVER"'>
           <div class='card-header head-sm'>
           {{labels.networkIO}}
          </div>
          <b-card-body>
             <b-row>
        <b-col>
        <ServerNetIO
          v-bind:labels="labels"
          v-bind:ciData="ciData"
          v-bind:system="systemName"
          v-bind:cycle="cyclePref"
          v-bind:datePref="datePref"
          ref="serverNetIO"
           />
        </b-col>
             </b-row>
          </b-card-body>
        </b-card>
     <b-card no-body v-show='systemName === "SERVER" && serverDbFlag'>
           <div class='card-header head-sm'>
           {{labels.databaseSw}}
            <div class="card-header-actions" style="height: 21px;">
              <el-switch v-model="dbSc" :active-text='this.labels.legend'></el-switch>
            </div>
          </div>
          <b-card-body>
             <b-row>
        <b-col>
          <ServerDb
          v-bind:labels="labels"
          v-bind:ciData="ciData"
          v-bind:system="systemName"
          v-bind:dbSc="dbSc"
          v-bind:cycle="cyclePref"
          v-bind:datePref="datePref"
          ref="serverDb"
          v-on:serverDbData="serverDbOpen"
           />
        </b-col>
             </b-row>
          </b-card-body>
      </b-card>
        </b-col>

        <b-col cols="12" md="6" v-show='systemName === "SERVER" || systemName === "NETWORK"'>
        <b-card no-body>
          <div class='card-header head-sm'>
            {{labels.event}}
          </div>
          <b-card-body class='card-body sm'>
            <div v-if='ciEventList.length !== 0' >
              <div class="block">
                <el-timeline v-if="monitorFlag" style="padding-left: 0px;">
                  <CiEvent v-for='event in ciEventList'
                     v-bind:key="event.id"
                     v-bind:data='event'
                     v-bind:severityClass='severityColor'
                     v-bind:vueName='$options.name'
                     v-bind:labels='labels'
                  />
                </el-timeline>
              </div>
            </div>
            <div v-if='ciEventList.length === 0' class='el-table__empty-text'>
              {{labels.noEventData}}
            </div>
         </b-card-body>
          </b-card>

        <b-card no-body v-show="systemName === 'SERVER'">
           <div class='card-header head-sm'>
            {{labels.fileSystem }}
          </div>
          <b-card-body>
          <ServerDisk
          v-bind:labels="labels"
          v-bind:system="systemName"
          v-bind:ciData="ciData"
          v-bind:cycle="cyclePref"
          v-bind:datePref="datePref"
          ref="serverDisk"
           />
        </b-card-body>
        </b-card>

        <b-card no-body v-show='systemName === "SERVER"'>
           <div class='card-header head-sm'>
             {{labels.diskIO}}
            <div class="card-header-actions" style="height: 21px;">
              <el-switch v-model="diskIoSc" :active-text='this.labels.legend'></el-switch>
            </div>
          </div>
          <b-card-body>
             <b-row>
        <b-col>
          <ServerDiskIO
          v-bind:labels="labels"
          v-bind:system="systemName"
          v-bind:ciData="ciData"
          v-bind:diskIoSc="diskIoSc"
          v-bind:cycle="cyclePref"
          v-bind:datePref="datePref"
          ref="serverDiskIO"
           />
        </b-col>
             </b-row>
          </b-card-body>
  </b-card>
         <b-card no-body v-show='systemName === "SERVER" && serverWasFlag'>
           <div class='card-header head-sm'>
           {{labels.middlewareSw}}
            <div class="card-header-actions" style="height: 21px;">
              <el-switch v-model="wasSc" :active-text='this.labels.legend'></el-switch>
            </div>
          </div>
          <b-card-body>
             <b-row>
        <b-col>
          <ServerWas
          v-bind:labels="labels"
          v-show='systemName === "SERVER" && serverWasFlag'
          v-bind:ciData="ciData"
          v-bind:system="systemName"
          v-bind:wasSc="wasSc"
          v-bind:cycle="cyclePref"
          v-bind:datePref="datePref"
          ref="serverWas"
          v-on:serverWasData="serverWasOpen"
           />
        </b-col>
             </b-row>
          </b-card-body>
  </b-card>
        </b-col>
    </b-row>
    </b-card>
     </div>
  </div>
</template>

<script>
import {eventBus} from '@/eventBus/eventBus'
import NetPerf from '@/views/monitor/cidetail/perfraw/NetPerf'
import ServerPerf from '@/views/monitor/cidetail/perfraw/ServerPerf'
import ServerDisk from '@/views/monitor/cidetail/perfraw/ServerDisk'
import ServerDiskIO from '@/views/monitor/cidetail/perfraw/ServerDiskIO'
import ServerNetIO from '@/views/monitor/cidetail/perfraw/ServerNetIO'
import ServerWas from '@/views/monitor/cidetail/perfraw/ServerWas'
import ServerDb from '@/views/monitor/cidetail/perfraw/ServerDb'
import LineChart from '@/views/monitor/chart/LineChart'
import HorizonalBarChart from '@/views/monitor/chart/HorizonalBarChart'
const CiEvent = () => import('@/views/common/CiEvent')
export default {
  name: 'CIMONITORDETAIL',
  components: {
    CiEvent, LineChart, HorizonalBarChart, ServerPerf, ServerDisk, ServerDiskIO, ServerNetIO, ServerWas, ServerDb, NetPerf
  },
  watch: {
    '$route': 'initialize'
  },
  data () {
    return {
      cycle: 60,
      cyclePref: 60,
      datePref: 15,
      defaultChartOption: {
        responsive: true,
        maintainAspectRatio: false
      },
      monitorFlag: false,
      wasSc: false,
      dbSc: false,
      diskIoSc: false,
      severityColor: {},
      monitorSystem: [],
      systemFlag: false,
      systemName: '',
      serverWasFlag: false,
      serverDbFlag: false,
      labels: {
        topologyOpen: '',
        moveCI: '',
        ci: '',
        type: '',
        information: '',
        title: '',
        name: '',
        ipAddress: '',
        operation: '',
        purpose: '',
        location: '',
        manager: '',
        memory: '',
        event: '',
        fileSystem: '',
        topology: '',
        performance: '',
        interface: '',
        cpu: '',
        os: '',
        importances: '',
        networkIO: '',
        diskIO: '',
        middlewareSW: '',
        databaseSw: '',
        legend: '',
        noEventData: '',
        day: '',
        previousDay: '',
        icmp: '',
        dbSessCount: '',
        dbResTime: '',
        avgResTime: '',
        waitTime: '',
        responseTime: '',
        diskUse: '',
        diskIoUse: '',
        netIn: '',
        netOut: '',
        cpuUse: ''
      },
      ciId: '',
      ancestor: '',
      managerName: '',
      ciEventList: [],
      ciData: {},
      ciEventInterval: {}
    }
  },
  computed: {
    cpuD: function () {
      let ghz = 0
      let core = 0
      if (!this.$util.isNull(this.ciData.extnum1)) ghz = this.ciData.extnum1
      if (!this.$util.isNull(this.ciData.extnum4)) core = this.ciData.extnum4
      let style = (ghz / 1000).toFixed(2) + ' GHz' + ' / ' + core + ' CORE'
      return style
    },
    memD: function () {
      let gb = 0
      if (!this.$util.isNull(this.ciData.extnum5)) gb = this.ciData.extnum5
      let style = (gb / 1000000).toFixed(2) + ' GB'
      return style
    }
  },
  methods: {
    initialize: function () {
      this.monitorFlag = true
      this.wasSc = false
      this.dbSc = false
      this.diskIoSc = false
      this.serverWasFlag = false
      this.serverDbFlag = false
      this.$http.get('/episode/api/system/common/domain?domainId=SEVERITYCOLOR').then(res => {
        this.setSeverityColor(res.data)
      }).catch(error => {
        console.log(error)
      })
      this.$http.get('/episode/api/system/common/domain?domainId=MONITORSYSTEM').then(res => {
        this.monitorSystem = res.data
      }).catch(error => {
        console.log(error)
      })
      let orgvariable = this.$store.getters.getOrgvariable
      if (orgvariable.length !== 0) {
        this.rpaCycle = parseInt(orgvariable.filter(r => r.key === 'EVENT')[0].value)
        this.cyclePref = parseInt(orgvariable.filter(r => r.key === 'PERF')[0].value)
        this.datePref = parseInt(orgvariable.filter(r => r.key === 'PERFDELAY')[0].value)
      }
      this.$util.setLabels(this.$options.name, this.labels)
      this.ciId = this.$route.params.id

      this.getCiData()
      clearInterval(this.ciEventInterval)
    },
    setSeverityColor: function (data) {
      data.forEach(row => {
        this.severityColor[row.key] = row.value
      })
    },
    topologyOpen: function () {
      eventBus.$emit('topologyOpen', this.ciData)
    },
    moveCI: function () {
      this.$util.move(this, 'CI', this.ciData.id)
    },
    getCiData: function () {
      this.$http.get(
        '/episode/api/monitor/detail/cidata/' + this.ciId
      ).then(res => {
        this.systemName = res.data.system
        this.ciData = res.data.ci
        this.managerName = res.data.managerName
        this.systemChk(this.systemName)
        if (res.data.system === 'SERVER' || res.data.system === 'NETWORK') {
          this.getCiEvent()
          this.ciEventInterval = setInterval(this.getCiEvent, (this.cycle * 1000))
        }
      }).catch(error => {
        console.log(error)
      })
    },
    systemChk: function (systemName) {
      this.monitorSystem.some(r => {
        if (r.value === systemName) {
          this.systemFlag = true
        }
        return (r.value === systemName)
      })
    },
    getCiEvent: function () {
      this.$http.get(
        '/episode/api/event/cievent/' + this.ciId
      ).then(res => {
        this.ciEventList = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    serverWasOpen: function (data) {
      if (data.length !== 0) this.serverWasFlag = true
    },
    serverDbOpen: function (data) {
      if (data.length !== 0) this.serverDbFlag = true
    }
  },
  mounted () {
    this.initialize()
  },
  beforeDestroy () {
    this.ciEventList = []
    this.ciData = {}
    this.severityColor = {}
    this.monitorSystem = []
    this.labels = {}
    this.$nextTick(function () {
      clearInterval(this.ciEventInterval)
      this.monitorFlag = false
    })
  }
}
</script>
