<!--
 File Name  : UserCiEvent.vue
 Description : 사이드바 > 관심장비 이벤트 > 컴포넌트
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.13  이정호  최초 생성
-->
<template>
  <b-list-group-item :class="dividerClass[data.severity]">
    <div class="cursor-pointer" v-on:click="eventDetailOpen" >
      <div>
        <b-row>
          <b-col sm="8">
            <el-tooltip class="item" effect="dark" :content="eventData.ciname" placement="right">
              <div>
                <h6 class="mr-3 ciname">{{eventData.ciname}}</h6>
              </div>
            </el-tooltip>
          </b-col>
          <b-col  sm="4">
            <b-badge :style='badgeStyle(data.severity)' class="m-1 badge-es" pill >{{data.severity}}</b-badge>
          </b-col>
        </b-row>
      </div>
      <small class="text-muted">{{eventData.ciId}} - {{eventData.classstructureNm}}</small>
      <p class="text-muted mr-3 sm">
        <i class="icon-note"></i>
        {{eventData.message}}
      </p>
      <small class="text-muted mr-3">
        <i class="icon-calendar"></i>&nbsp;&nbsp;{{dateTime(eventData.originDt)}}
      </small>
    </div>
    <div  xl class="mb-2 mb-xl-0">
      <b-button size="sm" variant="ghost-dark" v-on:click='topologyOpen'>{{labels.topology}}</b-button>
      <b-button size="sm" variant="ghost-dark" v-on:click='ciOpen'>{{labels.ci}}</b-button>
      <b-button size="sm" variant="ghost-dark" v-on:click='monitorOpen'>{{labels.monitor}}</b-button>
    </div>
  </b-list-group-item>
</template>

<script>
import {eventBus} from '@/eventBus/eventBus'
export default {
  name: 'EVENT',
  props: ['data', 'severityClass', 'labels'],
  data () {
    return {
      severityColor: {},
      eventData: {},
      dividerClass: {
        Down: 'list-group-item-accent-down list-group-item-divider',
        Critical: 'list-group-item-accent-critical list-group-item-divider',
        Major: 'list-group-item-accent-major list-group-item-divider',
        Minor: 'list-group-item-accent-minor list-group-item-divider',
        Normal: 'list-group-item-accent-normal list-group-item-divider'}
    }
  },
  computed: {
    badgeStyle () {
      return (key) => {
        return { backgroundColor: this.severityColor[key], color: 'white' }
      }
    }
  },
  methods: {
    initialize: function () {
      this.eventData = this.data
      this.severityColor = this.severityClass
    },
    topologyOpen: function () {
      eventBus.$emit('topologyOpen', this.eventData)
    },
    monitorOpen: function () {
      this.$util.move(this, 'CIMONITORDETAIL', this.eventData.idCi)
    },
    ciOpen: function () {
      this.$util.move(this, 'CI', this.eventData.idCi)
    },
    dateTime: function (date) {
      return this.$util.dateTime(date)
    },
    eventDetailOpen: function () {
      eventBus.$emit('eventDetailOpen', this.eventData)
    }
  },
  created () {
    this.initialize()
  },
  beforeDestroy () {
    this.severityColor = {}
    this.eventData = {}
  }
}
</script>
