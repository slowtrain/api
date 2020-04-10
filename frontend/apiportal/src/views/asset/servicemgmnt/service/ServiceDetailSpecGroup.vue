<!--
 Class Name  : ServiceDetailSpecGroup.vue
 Description : 서비스 > 상세 > 세부정보 (어플리케이션)
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.04  최영훈  최초 생성
-->
<template>
  <div>
    <b-row>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln1}}</label>
        <input v-model="listGrid.currentData.extaln1" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln2}}</label>
        <input v-model="listGrid.currentData.extaln2" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln3}}</label>
        <input v-model="listGrid.currentData.extaln3" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln18}}</label>
        <input v-model="listGrid.currentData.extaln18" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extnum1}}</label>
        <input v-model="listGrid.currentData.extnum1" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extdate1}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate1" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extdate2}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate2" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extdate3}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate3" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extdate4}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate4" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extdate5}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate5" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extbool1}}</label>
        <el-switch v-model="listGrid.currentData.extbool1" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-switch>
      </div>
    </b-row>
  </div>
</template>
<script>
export default {
  props: ['labels', 'comboData', 'listGrid', 'displayAllFlag'],
  data () {
    return {}
  },
  computed: {
    extdate1: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.extdate1)
      },
      set: function (val) {
        this.listGrid.currentData.extdate1 = val
      }
    },
    extdate2: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.extdate2)
      },
      set: function (val) {
        this.listGrid.currentData.extdate2 = val
      }
    },
    extdate3: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.extdate3)
      },
      set: function (val) {
        this.listGrid.currentData.extdate3 = val
      }
    },
    extdate4: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.extdate4)
      },
      set: function (val) {
        this.listGrid.currentData.extdate4 = val
      }
    },
    extdate5: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.extdate5)
      },
      set: function (val) {
        this.listGrid.currentData.extdate5 = val
      }
    }
  },
  methods: {
    init: function () {},
    cmpType: function (key) {
      return key.substr(0, 4)
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
