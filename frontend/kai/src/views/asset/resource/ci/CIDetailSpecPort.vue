<!--
 Class Name  : CIDetailSpecPort.vue
 Description : IT 자원 > 상세 > 세부정보 (port)
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
