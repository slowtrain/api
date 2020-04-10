<template>
  <div>
    <DisCIDetailDefault
      ref="disCIDetailDefault"
      v-bind:labels="labels"
      v-bind:authorities="authorities"
      v-bind:listGrid="listGrid"
    />
    <b-row>
      <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>EXTALN1</label>
        <input v-model="listGrid.currentData.extaln1" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN2</label>
        <input v-model="listGrid.currentData.extaln2" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN3</label>
        <input v-model="listGrid.currentData.extaln3" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN4</label>
        <input v-model="listGrid.currentData.extaln4" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN5</label>
        <input v-model="listGrid.currentData.extaln5" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN6</label>
        <input v-model="listGrid.currentData.extaln6" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>EXTALN7</label>
        <input v-model="listGrid.currentData.extaln7" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN8</label>
        <input v-model="listGrid.currentData.extaln8" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN9</label>
        <input v-model="listGrid.currentData.extaln9" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN10</label>
        <input v-model="listGrid.currentData.extaln10" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN11</label>
        <input v-model="listGrid.currentData.extaln11" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN12</label>
        <input v-model="listGrid.currentData.extaln12" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>EXTALN13</label>
        <input v-model="listGrid.currentData.extaln13" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN14</label>
        <input v-model="listGrid.currentData.extaln14" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN15</label>
        <input v-model="listGrid.currentData.extaln15" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN16</label>
        <input v-model="listGrid.currentData.extaln16" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN17</label>
        <input v-model="listGrid.currentData.extaln17" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN18</label>
        <input v-model="listGrid.currentData.extaln18" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>EXTALN19</label>
        <input v-model="listGrid.currentData.extaln19" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTALN20</label>
        <input v-model="listGrid.currentData.extaln20" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>EXTNUM1</label>
        <input v-model="listGrid.currentData.extnum1" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTNUM2</label>
        <input v-model="listGrid.currentData.extnum2" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTNUM3</label>
        <input v-model="listGrid.currentData.extnum3" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTNUM4</label>
        <input v-model="listGrid.currentData.extnum4" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTNUM5</label>
        <input v-model="listGrid.currentData.extnum5" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>EXTDATE1</label>
        <input v-bind:value="this.$util.dateTimeWithTimeZone(listGrid.currentData.extdate1)" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTDATE2</label>
        <input v-bind:value="this.$util.dateTimeWithTimeZone(listGrid.currentData.extdate2)" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTDATE3</label>
        <input v-bind:value="this.$util.dateTimeWithTimeZone(listGrid.currentData.extdate3)" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTDATE4</label>
        <input v-bind:value="this.$util.dateTimeWithTimeZone(listGrid.currentData.extdate4)" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
      <div class="form-group col-md-2">
        <label>EXTDATE5</label>
        <input v-bind:value="this.$util.dateTimeWithTimeZone(listGrid.currentData.extdate5)" class="formdata sm" autocomplete="off" disabled="disabled"/>
      </div>
    </b-row>
    <b-row>
      <div class="form-group col-md-2">
        <label>EXTBOOL1</label>
        <el-switch v-model="listGrid.currentData.extbool1" class="formdata sm" disabled="disabled"></el-switch>
      </div>
      <div class="form-group col-md-2">
        <label>EXTBOOL2</label>
        <el-switch v-model="listGrid.currentData.extbool2" class="formdata sm" disabled="disabled"></el-switch>
      </div>
      <div class="form-group col-md-2">
        <label>EXTBOOL3</label>
        <el-switch v-model="listGrid.currentData.extbool3" class="formdata sm" disabled="disabled"></el-switch>
      </div>
      <div class="form-group col-md-2">
        <label>EXTBOOL4</label>
        <el-switch v-model="listGrid.currentData.extbool4" class="formdata sm" disabled="disabled"></el-switch>
      </div>
      <div class="form-group col-md-2">
        <label>EXTBOOL5</label>
        <el-switch v-model="listGrid.currentData.extbool5" class="formdata sm" disabled="disabled"></el-switch>
      </div>
    </b-row>
    <b-row class="mb-2">
      <div class="col" style="padding-top:25px; padding-left:20px;">
        <a class="title-box">{{labels.spec}}</a>
      </div>
    </b-row>
    <b-row>
      <div class='col'>
        <p class="grid-line"></p>
        <data-tables layout='table, pagination' :data='specGrid.data' :filters='specGrid.filters' :page-size='specGrid.pageProps.pageSize' :pagination-props='specGrid.pageProps'>
          <el-table-column :prop='specGrid.titles[0].prop' :label='labels.spec' sortable='custom'></el-table-column>
          <el-table-column :prop='specGrid.titles[1].prop' :label='labels.value' sortable='custom'>
            <template slot-scope="props">
              {{props.row.type === 'ALNVALUE' ? props.row.alnvalue : props.row.numvalue}}
            </template>
          </el-table-column>
        </data-tables>
      </div>
    </b-row>
  </div>
</template>
<script>
import DisCIDetailDefault from './DisCIDetailDefault'
export default {
  props: ['labels', 'authorities', 'comboData', 'detailType', 'listGrid', 'specGrid'],
  components: {
    'DisCIDetailDefault': DisCIDetailDefault
  },
  data () {
    return {}
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {}
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    // EVENT FN ///////////////////////////////////////////
  },
  created: function () {
    this.initialize()
  }
}
</script>
