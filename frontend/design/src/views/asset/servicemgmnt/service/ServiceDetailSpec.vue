<!--
 Class Name  : ServiceDetailSpec.vue
 Description : 서비스 > 상세 > 세부정보 (기본)
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.04  최영훈  최초 생성
-->
<template>
  <div>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln1)">
        <label>{{listGrid.ciExtNmData.extaln1}}</label>
        <input v-model="listGrid.currentData.extaln1" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln2)">
        <label>{{listGrid.ciExtNmData.extaln2}}</label>
        <input v-model="listGrid.currentData.extaln2" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln3)">
        <label>{{listGrid.ciExtNmData.extaln3}}</label>
        <input v-model="listGrid.currentData.extaln3" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln4)">
        <label>{{listGrid.ciExtNmData.extaln4}}</label>
        <input v-model="listGrid.currentData.extaln4" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln5)">
        <label>{{listGrid.ciExtNmData.extaln5}}</label>
        <input v-model="listGrid.currentData.extaln5" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln6)">
        <label>{{listGrid.ciExtNmData.extaln6}}</label>
        <input v-model="listGrid.currentData.extaln6" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln7)">
        <label>{{listGrid.ciExtNmData.extaln7}}</label>
        <input v-model="listGrid.currentData.extaln7" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln8)">
        <label>{{listGrid.ciExtNmData.extaln8}}</label>
        <input v-model="listGrid.currentData.extaln8" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln9)">
        <label>{{listGrid.ciExtNmData.extaln9}}</label>
        <input v-model="listGrid.currentData.extaln9" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln10)">
        <label>{{listGrid.ciExtNmData.extaln10}}</label>
        <input v-model="listGrid.currentData.extaln10" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln11)">
        <label>{{listGrid.ciExtNmData.extaln11}}</label>
        <input v-model="listGrid.currentData.extaln11" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln12)">
        <label>{{listGrid.ciExtNmData.extaln12}}</label>
        <input v-model="listGrid.currentData.extaln12" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln13)">
        <label>{{listGrid.ciExtNmData.extaln13}}</label>
        <input v-model="listGrid.currentData.extaln13" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln14)">
        <label>{{listGrid.ciExtNmData.extaln14}}</label>
        <input v-model="listGrid.currentData.extaln14" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln15)">
        <label>{{listGrid.ciExtNmData.extaln15}}</label>
        <input v-model="listGrid.currentData.extaln15" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln16)">
        <label>{{listGrid.ciExtNmData.extaln16}}</label>
        <input v-model="listGrid.currentData.extaln16" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln17)">
        <label>{{listGrid.ciExtNmData.extaln17}}</label>
        <input v-model="listGrid.currentData.extaln17" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln18)">
        <label>{{listGrid.ciExtNmData.extaln18}}</label>
        <input v-model="listGrid.currentData.extaln18" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln19)">
        <label>{{listGrid.ciExtNmData.extaln19}}</label>
        <input v-model="listGrid.currentData.extaln19" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extaln20)">
        <label>{{listGrid.ciExtNmData.extaln20}}</label>
        <input v-model="listGrid.currentData.extaln20" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extnum1)">
        <label>{{listGrid.ciExtNmData.extnum1}}</label>
        <input v-model="listGrid.currentData.extnum1" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extnum2)">
        <label>{{listGrid.ciExtNmData.extnum2}}</label>
        <input v-model="listGrid.currentData.extnum2" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extnum3)">
        <label>{{listGrid.ciExtNmData.extnum3}}</label>
        <input v-model="listGrid.currentData.extnum3" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extnum4)">
        <label>{{listGrid.ciExtNmData.extnum4}}</label>
        <input v-model="listGrid.currentData.extnum4" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extnum5)">
        <label>{{listGrid.ciExtNmData.extnum5}}</label>
        <input v-model="listGrid.currentData.extnum5" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extdate1)">
        <label>{{listGrid.ciExtNmData.extdate1}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate1" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extdate2)">
        <label>{{listGrid.ciExtNmData.extdate2}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate2" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extdate3)">
        <label>{{listGrid.ciExtNmData.extdate3}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate3" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extdate4)">
        <label>{{listGrid.ciExtNmData.extdate4}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate4" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extdate5)">
        <label>{{listGrid.ciExtNmData.extdate5}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate5" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-date-picker>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extbool1)">
        <label>{{listGrid.ciExtNmData.extbool1}}</label>
        <el-switch v-model="listGrid.currentData.extbool1" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-switch>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extbool2)">
        <label>{{listGrid.ciExtNmData.extbool2}}</label>
        <el-switch v-model="listGrid.currentData.extbool2" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-switch>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extbool3)">
        <label>{{listGrid.ciExtNmData.extbool3}}</label>
        <el-switch v-model="listGrid.currentData.extbool3" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-switch>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extbool4)">
        <label>{{listGrid.ciExtNmData.extbool4}}</label>
        <el-switch v-model="listGrid.currentData.extbool4" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-switch>
      </div>
      <div class="form-group col-md-2" v-show="displayAllFlag || !$util.isNull(listGrid.ciExtNmData.extbool5)">
        <label>{{listGrid.ciExtNmData.extbool5}}</label>
        <el-switch v-model="listGrid.currentData.extbool5" class="formdata sm" v-on:change="changeValue(listGrid.currentData)" v-bind:disabled="!listGrid.currentData.userdefine"></el-switch>
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
