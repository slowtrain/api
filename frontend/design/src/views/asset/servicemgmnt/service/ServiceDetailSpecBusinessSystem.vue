<!--
 Class Name  : ServiceDetailSpecBusinessSystem.vue
 Description : 서비스 > 상세 > 세부정보 (서비스)
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
    </b-row>
  </div>
</template>
<script>
export default {
  props: ['labels', 'comboData', 'listGrid', 'displayAllFlag'],
  data () {
    return {}
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
