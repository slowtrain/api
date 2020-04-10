<!--
 Class Name  : ServiceDetailDefault.vue
 Description : 서비스 > 상세 기본 정보
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.04  최영훈  최초 생성
-->
<template>
  <div>
    <b-row>
      <div class="form-group col-md-2">
        <label for="ciId">{{labels.ciId}}<span class="require-mark">*</span></label>
        <div class="search-component sm">
          <input id="ciId" v-model="listGrid.currentData.ciId" v-on:blur="getCIIdChk" class="formdata sm" autocomplete="off" v-bind:disabled="this.detailType=='UPDATE'"/>
          <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.ciIdMsg)" v-text="validMsg.ciIdMsg"></p>
          <el-popover placement="right" width="120" trigger="click">
            <el-button size="mini" type="text" v-on:click="openServiceview">{{labels.openserviceview}}</el-button>
            <br>
            <el-button size="mini" type="text" v-on:click="openTopology">{{labels.opentopology}}</el-button>
            <!-- <br>
            <el-button size="mini" type="text" v-on:click="openEvent">{{labels.openevent}}</el-button>
            <br>
            <el-button size="mini" type="text" v-on:click="moveDashboard">{{labels.movedashboard}}</el-button>
            <br>
            <el-button size="mini" type="text" v-on:click="moveDisci">{{labels.movedisci}}</el-button> -->
            <a slot="reference" class="action-button cursor-pointer" v-show="!$util.isNull(listGrid.currentData.id)">search</a>
          </el-popover>
        </div>
      </div>
      <div class="form-group col-md-2">
        <label for="ciname">{{labels.name}}<span class="require-mark">*</span></label>
        <div class="search-component sm">
          <input id="ciname" v-model="listGrid.currentData.ciname" class="formdata sm" autocomplete="off" v-bind:maxlength="100" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"/>
          <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.nameMsg)" v-text="validMsg.nameMsg"></p>
        </div>
      </div>
      <div class="form-group col-md-2">
        <label for="classstructureId">{{labels.classstructureId}}<span class="require-mark">*</span></label>
        <div class="search-component sm">
          <input id="classstructureId" v-model="listGrid.currentData.classstructureNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
          <a class="search-button cursor-pointer" v-on:click="openClassDialog" v-show="listGrid.currentData.userdefine">search</a>
          <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.classstructureIdMsg)" v-text="validMsg.classstructureIdMsg"></p>
        </div>
      </div>
      <div class="form-group col-md-2">
        <label for="status">{{labels.opstatus}}</label>
        <el-select v-model="listGrid.currentData.status" :placeholder="labels.selectData" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="disableStatus">
          <el-option v-for="status in comboData.statusSelect" :prop="status.value" :value="status.value" :key="status.key">
            {{status.value}}
          </el-option>
        </el-select>
      </div>
      <div class="form-group col-md-2">
        <label for="type">{{labels.type}}</label>
        <el-select v-model="listGrid.currentData.type" :placeholder="labels.selectData" v-on:change="changeValue(listGrid.currentData)">
          <el-option v-for="type in comboData.typeSelect" :prop="type.value" :value="type.value" :key="type.key">
            {{type.value}}
          </el-option>
        </el-select>
      </div>
      <div class="form-group col-md-2">
        <label for="locationId">{{labels.locationId}}</label>
        <div class="search-component sm">
          <input id="locationId" v-model="listGrid.currentData.locationId" class="formdata sm" autocomplete="off" disabled="disabled"/>
          <a class="search-button cursor-pointer" v-on:click="openLocationDialog">search</a>
        </div>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-12">
        <label for="description">{{labels.description}}<span class="require-mark">*</span></label>
        <input id="description" v-model="listGrid.currentData.description" class="formdata sm" autocomplete="off" v-bind:maxlength="100" v-on:change="changeValue(listGrid.currentData)"/>
        <p class="form_some-copy sm error" v-show="!$util.isNull(validMsg.descriptionMsg)" v-text="validMsg.descriptionMsg"></p>
      </div>
      <!-- <div class="form-group col-md-2">
        <label for="managerId">{{labels.managerId}}</label>
        <div class="search-component sm">
          <input id="managerId" v-model="listGrid.currentData.managerNm" class="formdata sm" autocomplete="off" disabled="disabled"/>
          <a class="search-button cursor-pointer" v-on:click="openUserDialog">search</a>
        </div>
      </div> -->
      <!-- <div class="form-group col-md-2">
        <label for="orgId">{{labels.orgId}}</label>
        <el-select v-model="listGrid.currentData.orgId" :placeholder="labels.selectData" disabled="disabled">
          <el-option v-for="orgId in comboData.orgSelect" :prop="orgId.orgId" :value="orgId.orgId" :key="orgId.orgId">
            {{orgId.orgId}}
          </el-option>
        </el-select>
      </div> -->
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>{{labels.userdefine}}</label>
        <el-switch v-model="listGrid.currentData.userdefine" class="formdata sm" disabled="disabled"></el-switch>
      </div>
      <div class="form-group col-md-2">
        <label>{{labels.isdelete}}</label>
        <el-switch v-model="listGrid.currentData.isdelete" class="formdata sm" disabled="disabled"></el-switch>
      </div>
      <div class='form-group col-md-2'>
        <label for="application_createId">{{labels.createId}}</label>
        <input id="application_createId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.createNm" placeholder="CREATER"  disabled="disabled"/>
      </div>
      <div class='form-group col-md-2'>
        <label for="application_createDt">{{labels.createDt}}</label>
        <input id="application_createDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.createDt)" placeholder="CREATE DATE" disabled="disabled" />
      </div>
      <div class='form-group col-md-2'>
        <label for="application_updateId">{{labels.updateNm}}</label>
        <input id="application_updateId" class="formdata sm float-right" style="width: 100%" v-model="listGrid.currentData.updateNm" placeholder="UPDATER" disabled="disabled"/>
      </div>
      <div class='form-group col-md-2'>
        <label>{{labels.updateDt}}</label>
        <input id="application_updateDt" class="formdata sm float-right" style="width: 100%" :value="$util.dateTimeWithTimeZone(listGrid.currentData.updateDt)" placeholder="UPDATE DATE" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-10">
      </div>
    </b-row>
    <!-- Class Dialog -->
    <Class
      ref="classDialog"
      v-bind:showDialogFlagProp="showClassDialogFlag"
      v-on:confirm="confirmClass"
      v-on:close="closeClassDialog"
      v-bind:param="{object: 'SERVICE', expand: null}"
    />
    <!-- User Dialog -->
    <User
      ref="userDialog"
      v-bind:showDialogFlagProp="showUserDialogFlag"
      v-bind:username="listGrid.currentData.managerId"
      v-on:confirm="confirmUser"
      v-on:close="closeUserDialog"
    />
    <!-- Location Dialog -->
    <Location
      ref="locationDialog"
      v-bind:showDialogFlagProp="showLocationDialogFlag"
      v-on:confirm="confirmLocation"
      v-on:close="closeLocationDialog"
    />
  </div>
</template>
<script>
import Class from '@/views/common/Class'
import User from '@/views/common/User'
import Location from '@/views/common/Location'
import {eventBus} from '@/eventBus/eventBus'
export default {
  name: 'CI',
  props: ['labels', 'comboData', 'detailType', 'classType', 'listGrid'],
  components: {
    'Class': Class,
    'User': User,
    'Location': Location
  },
  data () {
    return {
      // Valid Msg
      validMsg: {
        classstructureIdMsg: '',
        ciIdMsg: '',
        nameMsg: '',
        descriptionMsg: ''
      },
      // Global Flag
      showClassDialogFlag: false,
      showUserDialogFlag: false,
      showLocationDialogFlag: false
    }
  },
  watch: {
    'listGrid.currentData.ciname': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.nameMsg = ''
      }
    },
    'listGrid.currentData.classstructureId': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.classstructureIdMsg = ''
      }
    },
    'listGrid.currentData.description': function (val) {
      if (!this.$util.isNull(val)) {
        this.validMsg.descriptionMsg = ''
      }
    }
  },
  computed: {
    disableStatus: function () {
      if (!this.$util.isNull(this.listGrid.currentData.hierarchypath)) {
        return !this.listGrid.currentData.userdefine && (this.listGrid.currentData.hierarchypath.indexOf('/GROUP_1') > -1)
      }
      return false
    }
  },
  methods: {
    isValidationCheck: function () {
      let rtnFlag = true
      for (let key in this.validMsg) {
        this.validMsg[key] = ''
      }
      let ciId = this.listGrid.currentData.ciId
      let ciname = this.listGrid.currentData.ciname
      let classstructureId = this.listGrid.currentData.classstructureId
      let description = this.listGrid.currentData.description
      if (!this.$util.isNull(this.validMsg.ciIdMsg)) {
        rtnFlag = false
      }
      if (this.$util.isNull(ciId)) {
        this.validMsg.ciIdMsg = this.labels.inputData
        rtnFlag = false
      }
      if (this.$util.isNull(ciname)) {
        this.validMsg.nameMsg = this.labels.inputData
        rtnFlag = false
      }
      if (this.$util.isNull(classstructureId)) {
        this.validMsg.classstructureIdMsg = this.labels.inputData
        rtnFlag = false
      }
      if (this.$util.isNull(description)) {
        this.validMsg.descriptionMsg = this.labels.inputData
        rtnFlag = false
      }
      return rtnFlag
    },
    getCIIdChk: function () {
      if (this.listGrid.currentData.ciId !== '') {
        this.$http.get(
          '/episode/api/asset/resource/ci?orgId=' + this.listGrid.currentData.orgId + '&ciId=' + this.listGrid.currentData.ciId
        ).then(res => {
          if (res.data.data.length > 0) {
            this.listGrid.currentData.ciId = ''
            this.validMsg.ciIdMsg = this.labels.duplicatedMessage
          } else {
            this.validMsg.ciIdMsg = ''
          }
        }).catch(error => {
          console.log(error)
        })
      }
    },
    openClassDialog: function () {
      this.showClassDialogFlag = true
    },
    confirmClass: function (rslt) {
      this.showClassDialogFlag = false
      this.listGrid.currentData.classstructureId = rslt.classstructureId
      this.listGrid.currentData.classstructureNm = rslt.classstructureNm
      this.listGrid.currentData.hierarchypath = rslt.hierarchypath + '/'
      this.changeValue(this.listGrid.currentData)
      this.$http.get(
        '/episode/api/asset/resource/ci/ciextnm?classstructureId=' + rslt.classstructureId + '&lang=' + this.$store.getters.getUser.lang
      ).then(res => {
        this.listGrid.ciExtNmData = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    closeClassDialog: function () {
      this.showClassDialogFlag = false
    },
    openUserDialog: function () {
      this.showUserDialogFlag = true
    },
    confirmUser: function (rslt) {
      this.showUserDialogFlag = false
      this.listGrid.currentData.managerId = rslt.username
      this.listGrid.currentData.managerNm = rslt.firstname
      this.changeValue(this.listGrid.currentData)
    },
    closeUserDialog: function () {
      this.showUserDialogFlag = false
    },
    openLocationDialog: function () {
      this.showLocationDialogFlag = true
    },
    confirmLocation: function (rslt) {
      this.showLocationDialogFlag = false
      this.listGrid.currentData.locationId = rslt.locationId
      this.changeValue(this.listGrid.currentData)
    },
    closeLocationDialog: function () {
      this.showLocationDialogFlag = false
    },
    openServiceview: function () {
      eventBus.$emit('TopologyServiceOpen', this.listGrid.currentData)
    },
    openTopology: function () {
      // this.$util.esTopology(this, this.listGrid.currentData)
      eventBus.$emit('topologyOpen', this.listGrid.currentData)
    },
    openEvent: function () {
      eventBus.$emit('topologyEventOpen', this.listGrid.currentData.id, this.$options.name)
    },
    moveDashboard: function () {
      this.$util.move(this, 'CIMONITORDETAIL', this.listGrid.currentData.id)
    },
    moveDisci: function () {
      this.$router.push({
        name: 'DISCI',
        params: {ciId: this.listGrid.currentData.ciId}
      })
    },
    changeValue: function (data) {
      if (!this.$util.isNull(data.id)) {
        data.state = 'CHANGED'
      } else {
        data.state = 'ADDED'
      }
    }
  },
  created: function () {}
}
</script>
