<!--
 Class Name  : SwcatLicense.vue
 Description : 소프트웨어 카탈로그 > 관련 라이선스
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
        <a class="title-box">{{labels.license}}</a>
      </b-col>
    </b-row>
    <b-row>
      <b-col sm="12">
        <p class="grid-line"></p>
        <data-tables style="width: 100%" layout='table, pagination' :table-props='licenseGrid.tableProps' :page-size='licenseGrid.pageProps.pageSize' :data='licenseGrid.data' :filters='licenseGrid.filters' :pagination-props='licenseGrid.pageProps'>
          <el-table-column :prop='licenseGrid.titles[0].prop' :label='labels.id'           sortable='custom' :width='licenseGrid.titles[0].width'></el-table-column>
          <el-table-column :prop='licenseGrid.titles[1].prop' :label='labels.name'         sortable='custom' ></el-table-column>
          <el-table-column :prop='licenseGrid.titles[2].prop' :label='labels.licensetype'  sortable='custom' :width='licenseGrid.titles[2].width'></el-table-column>
          <el-table-column :prop='licenseGrid.titles[3].prop' :label='labels.purchasetype' sortable='custom' :width='licenseGrid.titles[3].width'></el-table-column>
          <el-table-column :prop='licenseGrid.titles[4].prop' :label='labels.status'       sortable='custom' :width='licenseGrid.titles[4].width'></el-table-column>
          <el-table-column :prop='licenseGrid.titles[5].prop' :label='labels.startDt'      sortable='custom' :width='licenseGrid.titles[5].width'>
            <template slot-scope="props">
              {{$util.date(props.row.startDt)}}
            </template>
          </el-table-column>
          <el-table-column :prop='licenseGrid.titles[6].prop' :label='labels.endDt'        sortable='custom' :width='licenseGrid.titles[6].width'>
            <template slot-scope="props">
              {{$util.date(props.row.endDt)}}
            </template>
          </el-table-column>
          <el-table-column align="right" width="80">
            <template  slot-scope="props">
              <el-button v-on:click="openLicenseDialog(props.row)" :title="labels.viewdetaillic" style="padding: 0; width:30px; height: 30px;">
                <img src="@/assets/img/icons/icon_inventory_search.png" width="60%" />
              </el-button>
            </template>
          </el-table-column>
        </data-tables>
      </b-col>
    </b-row>
    <SwcatLicenseDialog
      ref="swcatLicenseDialog"
      v-bind:labels="labels"
      v-bind:showDialogFlagProp="showLicenseDialogFlag"
      v-bind:param="{id:selectedId}"
      v-on:close="closeLicenseDialog"
    />
  </div>
</template>
<script>
import SwcatDetailDefault from './SwcatDetailDefault'
import SwcatLicenseDialog from './SwcatLicenseDialog'
export default {
  props: ['labels', 'auth', 'comboData', 'detailType', 'listGrid', 'licenseGrid', 'validMsg'],
  components: {
    'SwcatDetailDefault': SwcatDetailDefault,
    'SwcatLicenseDialog': SwcatLicenseDialog
  },
  data () {
    return {
      showLicenseDialogFlag: false,
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
    openLicenseDialog: function (row) {
      this.showLicenseDialogFlag = true
      this.selectedId = row.id
    },
    closeLicenseDialog: function () {
      this.showLicenseDialogFlag = false
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
