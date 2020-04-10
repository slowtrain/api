<!--
 Class Name  : EventDetail.vue
 Description : CI 이벤트 상세
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.07  이정호  최초 생성
-->
<template>
  <el-dialog :title="labels.title" :visible.sync="dialogFormVisible">
    <b-row>
      <div class='col'>
        <el-form label-width="120px">
          <div class="form-row mt-2">
            <div class='form-group col-md-3' style="padding-right:1%">
              <label for="ciId">{{labels.ciId}}</label>
              <input id="ciId" class="formdata sm float-right" style="width: 100%" v-model="ciEventData.ciId" disabled="disabled" />
            </div>
            <div class='form-group col-md-3' style="padding-right:1%">
              <label for="ciname">{{labels.ciName}}</label>
              <input id="ciname" class="formdata sm float-right" style="width: 100%" v-model="ciEventData.ciname" disabled="disabled" />
            </div>
            <div class='form-group col-md-3' style="padding-right:1%">
              <label for="classstructureId">{{labels.classstructureId}}</label>
              <input id="classstructureId" class="formdata sm float-right" style="width: 100%" v-model="ciEventData.classstructureNm" disabled="disabled" />
            </div>
            <div class='form-group col-md-3' style="padding-right:1%">
              <label for="ipAddress">{{labels.ipAddress}}</label>
              <input id="ipAddress" class="formdata sm float-right" style="width: 100%" v-model="ciEventData.extaln17" disabled="disabled" />
            </div>
          </div>
          <div class="form-row mt-2">
            <div class='form-group col-md-3' style="padding-right:1%">
              <label for="severity">{{labels.severity}}</label>
              <input id="severity" class="formdata sm float-right" style="width: 100%" v-model="ciEventData.severity" disabled="disabled" />
            </div>
            <div class='form-group col-md-3' style="padding-right:1%">
              <label for="evtresource">{{labels.evtresource}}</label>
              <input id="evtresource" class="formdata sm float-right" style="width: 100%" v-model="ciEventData.evtresource" disabled="disabled" />
            </div>
            <div class='form-group col-md-3' style="padding-right:1%">
              <label for="evtcondition">{{labels.evtcondition}}</label>
              <input id="evtcondition" class="formdata sm float-right" style="width: 100%" v-model="ciEventData.evtcondition" disabled="disabled" />
            </div>
            <div class='form-group col-md-3' style="padding-right:1%">
              <label for="originDt">{{labels.originDt}}</label>
              <input id="originDt" class="formdata sm float-right" style="width: 100%" :placeholder="dateTime(ciEventData.originDt)" disabled="disabled" />
            </div>
          </div>

          <div class="form-row mt-2">
            <div class='form-group col-md-12' style="padding-right:1%">
              <label for="message">{{labels.message}}</label>
              <b-badge :style='badgeStyle(ciEventData.severity)' class="m-1 badge-es" pill>{{ciEventData.severity}}</b-badge>
              <textarea id="message" class="formdata sm float-right"  v-model="ciEventData.message" disabled="disabled"></textarea>
            </div>
          </div>
        </el-form>
      </div>
    </b-row>
    <span slot="footer" class="dialog-footer">
      <b-button variant="primary" v-on:click="popupClose"  class="px-3">{{labels.close}}</b-button>
    </span>
  </el-dialog>
</template>

<script>
import {eventBus} from '@/eventBus/eventBus'
export default {
  name: 'EVENTDETAIL',
  data () {
    return {
      dialogFormVisible: false,
      severityColor: {},
      ciEventData: {},
      labels: {
        title: '',
        ciId: '',
        ciName: '',
        classstructureId: '',
        ipAddress: '',
        severity: '',
        evtresource: '',
        evtcondition: '',
        originDt: '',
        message: '',
        close: ''
      }
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
    dateTime: function (date) {
      return this.$util.dateTime(date)
    },
    popupClose: function () {
      this.dialogFormVisible = false
    }
  },
  mounted () {
    let $this = this
    eventBus.$off('eventDetailOpen')
    eventBus.$on('eventDetailOpen', function (eventData) {
      $this.dialogFormVisible = true
      $this.ciEventData = eventData
      $this.initialize()
    })
  },

  created () {
    this.initialize()
  }
}
</script>
