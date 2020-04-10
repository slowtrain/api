<!--
 Class Name  : SwcatUseServer.vue
 Description : 소프트웨어 카탈로그 > 사용서버
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.14  최영훈  최초 생성
-->
<template>
  <div>
   <SwcatDetailDefault
      ref="swcatDetailDefault"
      v-bind:labels="labels"
      v-bind:comboData="comboData"
      v-bind:detailType="detailType"
      v-bind:listGrid="listGrid"
      v-bind:validMsg="validMsg"
    />
    <b-row>
      <div class="col mt-2 mb-2" style="border: 0.5px solid #c8d3df;"></div>
    </b-row>
    <b-row class="mb-1">
      <b-col style="height:60px; padding-top:25px; padding-left:20px;">
        <a class="title-box">{{labels.useserver}}</a>
      </b-col>
    </b-row>
    <b-row>
      <b-col sm="12">
        <p class="grid-line"></p>
        <data-tables style="width: 100%" layout='table, pagination' :table-props='useServerGrid.tableProps' :page-size='useServerGrid.pageProps.pageSize' :data='useServerGrid.data' :filters='useServerGrid.filters' :pagination-props='useServerGrid.pageProps'>
          <el-table-column :prop='useServerGrid.titles[0].prop' :label='labels.ciId'              sortable='custom' ></el-table-column>
          <el-table-column :prop='useServerGrid.titles[1].prop' :label='labels.classstructureId'  sortable='custom' ></el-table-column>
          <el-table-column :prop='useServerGrid.titles[2].prop' :label='labels.name'              sortable='custom' ></el-table-column>
          <el-table-column :prop='useServerGrid.titles[3].prop' :label='labels.description'       sortable='custom' ></el-table-column>
          <el-table-column :prop='useServerGrid.titles[4].prop' :label='"IP"'            sortable='custom' ></el-table-column>
          <el-table-column :prop='useServerGrid.titles[5].prop' :label='labels.manager'           sortable='custom' ></el-table-column>
          <el-table-column align="right" width="80">
            <template  slot-scope="props">
              <el-button v-on:click="openCIDialog(props.row)" :title="labels.viewdetailci" style="padding: 0; width:30px; height: 30px;">
                <img src="@/assets/img/icons/icon_inventory_search.png" width="60%" />
              </el-button>
            </template>
          </el-table-column>
        </data-tables>
      </b-col>
    </b-row>
    <CIDetail
      ref="cIDetailDialog"
      v-bind:labels="labels"
      v-bind:showDialogFlagProp="showCIDialogFlag"
      v-bind:param="{id:selectedId}"
      v-on:close="closeCIDialog"
    />
  </div>
</template>
<script>
import SwcatDetailDefault from './SwcatDetailDefault'
import CIDetail from '@/views/common/CIDetail'
export default {
  props: ['labels', 'auth', 'comboData', 'detailType', 'listGrid', 'useServerGrid', 'validMsg'],
  components: {
    'SwcatDetailDefault': SwcatDetailDefault,
    'CIDetail': CIDetail
  },
  data () {
    return {
      showCIDialogFlag: false,
      selectedId: ''
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.orgId = this.$store.getters.getUser.orgId
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    // EVENT FN ///////////////////////////////////////////
    openCIDialog: function (row) {
      this.showCIDialogFlag = true
      this.selectedId = row.id
    },
    closeCIDialog: function () {
      this.showCIDialogFlag = false
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
