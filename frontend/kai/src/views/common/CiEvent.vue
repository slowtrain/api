<!--
 Class Name  : CiEvent.vue
 Description : CI 이벤트 리스트 > 컴포넌트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.23  이정호  최초 생성
-->
<template>

      <el-timeline-item :timestamp="dateTime(eventData.originDt)" placement="top">
        <el-card>
          <div class="text-truncate font-weight-bold">
            <span class="cursor-pointer" v-popover:ipPopover>
              {{eventData.ciname}}
              <el-popover ref="ipPopover" placement="right" width="120" trigger="click" >
                <el-button size="mini" type="text" v-on:click="moveCI">{{labels.moveCi}}</el-button>
                <br>
                <el-button v-show="vueName !== 'CIMONITORDETAIL'" size="mini" type="text" v-on:click="moveMonitor">{{labels.moveMonitor}}</el-button>
                <br v-show="vueName !== 'CIMONITORDETAIL'">
                <el-button v-show="vueName !== 'TOPOLOGY_POPUP'" size="mini" type="text" v-on:click="topologyOpen">{{labels.topologyOpen}}</el-button>
              </el-popover>
            </span>
            <b-badge :style='badgeStyle(data.severity)' class="m-1 badge-es" pill >{{eventData.severity}}</b-badge>
          </div>
          <small class="text-muted">{{eventData.ciId}} - {{eventData.classstructureNm}}</small>
          <div class="mb-1">
            <i class='icon-speech' style="padding-right: 5px;"></i>
            {{eventData.message}}
          </div>
        </el-card>
      </el-timeline-item>
</template>

<script>
import {eventBus} from '@/eventBus/eventBus'
export default {
  name: 'CIEVENT',
  props: ['data', 'severityClass', 'vueName', 'labels'],
  data () {
    return {
      severityColor: {},
      eventData: {}
    }
  },
  computed: {
    badgeStyle () {
      return (key) => {
        return { backgroundColor: this.severityColor[key], color: 'white' }
      }
    }
  },
  watch: {
    data () {
      this.eventData = {}
      this.severityColor = {}
      this.eventData = this.data
      this.severityColor = this.severityClass
    }
  },
  methods: {
    initialize: function () {
      this.eventData = {}
      this.severityColor = {}
      this.eventData = this.data
      this.severityColor = this.severityClass
    },
    dateTime: function (date) {
      return this.$util.dateTime(date)
    },
    eventDetailOpen: function () {
      eventBus.$emit('eventDetailOpen', this.eventData)
    },
    topologyOpen: function () {
      eventBus.$emit('topologyOpen', this.eventData)
    },
    moveCI: function () {
      this.$refs.ipPopover.showPopper = false
      this.$util.move(this, 'CI', this.eventData.idCi)
    },
    moveMonitor: function () {
      this.$refs.ipPopover.showPopper = false
      this.$util.move(this, 'CIMONITORDETAIL', this.eventData.idCi)
    }
  },

  created () {
    this.initialize()
  }
}
</script>
