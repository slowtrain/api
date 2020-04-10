<!--
 Class Name  : CIDetailSpecCpu.vue
 Description : IT 자원 > 상세 > 세부정보 (CPU)
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.08.27  최영훈  최초 생성
-->
<template>
  <div>
    <b-row>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln1}}</label>
        <input v-model="listGrid.currentData.extaln1" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" />
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extaln2}}</label>
        <input v-model="listGrid.currentData.extaln2" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" v-bind:maxlength="200" />
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extnum1}}</label>
        <input v-model="listGrid.currentData.extnum1" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" />
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extnum3}}</label>
        <input v-model="listGrid.currentData.extnum3" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" />
      </div>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extnum4}}</label>
        <input v-model="listGrid.currentData.extnum4" v-on:keydown="$util.onlyNumber($event)" class="formdata sm" autocomplete="off" v-on:change="changeValue(listGrid.currentData)" />
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>{{listGrid.ciExtNmData.extdate1}}</label>
        <el-date-picker type="datetime" default-time="12:00:00" v-model="extdate1" v-on:change="changeValue(listGrid.currentData)" class="formdata sm"></el-date-picker>
      </div>
    </b-row>
  </div>
</template>
<script>
export default {
  name: 'CI',
  props: ['labels', 'comboData', 'listGrid'],
  data () {
    return {}
  },
  watch: {
    'listGrid.currentData.extdate1': function (val) {
      this.listGrid.currentData.extdate1 = this.$util.dateTimeCalendar(val)
    }
  },
  computed: {
    extdate1: {
      get: function () {
        return this.$util.dateTimeCalendar(this.listGrid.currentData.extdate1)
      },
      set: function (val) {
        this.listGrid.currentData.extdate1 = val
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
