<!--
 Class Name  : LicenseUseRelationDialog.vue
 Description : 라이선스사용 > 관련 라이선스 Dialog
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.30  최영훈  최초 생성
-->
<template>
  <el-dialog :title="labels.relationLicenseDialogTitle" :visible.sync="showDialogFlag" width=1250px v-on:close="close">
    <b-row>
      <b-col>
        <b-row>
          <b-col sm="12">
            <div class="search-component">
              <input class="form-control mt-1" style="width: 100%;" v-model="searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder=labels.search />
            </div>
          </b-col>
        </b-row>
        <b-row>
          <b-col sm="12">
            <p class="grid-line"></p>
            <data-tables style="width: 100%" layout='table, pagination' :table-props='listGrid.tableProps' :page-size='listGrid.pageProps.pageSize' :data='listGrid.data' :filters='listGrid.filters' :pagination-props='listGrid.pageProps' :current-page.sync='listGrid.currentPage'>
              <el-table-column :prop='listGrid.titles[0].prop' :label='labels.swlicenseId'  sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[1].prop' :label='labels.name'         sortable='custom'></el-table-column>
              <el-table-column :prop='listGrid.titles[2].prop' :label='labels.licensetype'  sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[3].prop' :label='labels.purchasetype' sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[4].prop' :label='labels.status'       sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[5].prop' :label='labels.startDt'      sortable='custom' :width='listGrid.titles[5].width'>
                <template slot-scope="props">
                  {{$util.dateTimeWithTimeZone(props.row.startDt)}}
                </template>
              </el-table-column>
              <el-table-column :prop='listGrid.titles[6].prop' :label='labels.endDt'        sortable='custom' :width='listGrid.titles[6].width'>
                <template slot-scope="props">
                  {{$util.dateTimeWithTimeZone(props.row.endDt)}}
                </template>
              </el-table-column>
              <el-table-column align="right" width="80">
                <template  slot-scope="props">
                  <el-button v-on:click="moveRelation(props.row)" :title="labels.movelic" style="padding: 0; width:30px; height: 30px;">
                    <img src="@/assets/img/icons/icon_search_login.png" width="60%" />
                  </el-button>
                </template>
              </el-table-column>
            </data-tables>
          </b-col>
        </b-row>
      </b-col>
    </b-row>
    <b-row>
      <div class="col mt-2">
        <div class='d-flex justify-content-end'>
          <b-button variant="primary" class="px-3 sm" v-on:click="close">{{labels.close}}</b-button>
        </div>
      </div>
    </b-row>
  </el-dialog>
</template>
<script>
export default {
  props: ['labels', 'showDialogFlagProp', 'param'],
  data () {
    return {
      listGrid: {
        tableProps: {
          border: false,
          stripe: true,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        currentPage: 1,
        rowIndex: -1,
        pageProps: {
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: ['swlicenseId', 'swlicname', 'swlictype', 'purchasetype', 'swlicstatus'],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'swlicenseId',
          width: '140px'
        }, {
          prop: 'swlicname',
          width: '140px'
        }, {
          prop: 'swlictype',
          width: '120px'
        }, {
          prop: 'purchasetype',
          width: '120px'
        }, {
          prop: 'swlicstatus',
          width: '120px'
        }, {
          prop: 'startDt',
          width: '200px'
        }, {
          prop: 'endDt',
          width: '200px'
        }],
        currentData: {},
        total: 0
      },
      // Global Var
      orgId: '',
      selectedItem: [],
      showDialogFlag: false,
      searchParam: ''
    }
  },
  watch: {
    'showDialogFlagProp': function (val) {
      this.showDialogFlag = val
      if (val) {
        this.searchParam = ''
        this.listGrid.filters[0].value = ''
        this.listGrid.currentPage = 1
        this.getListData()
      }
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      this.orgId = this.$store.getters.getUser.orgId
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    getListData: function () {
      this.$http.get(
        '/episode/api/asset/licensemgmnt/license?orgId=' + this.orgId + '&searchType=licenseuse' + '&dialogParam=' + this.param.swcatId + '&dialogParam2=' + this.param.purchaseunit
      ).then(res => {
        this.listGrid.data = res.data.data
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    enterSearch: function () {
      this.listGrid.filters[0].value = this.searchParam
    },
    close: function () {
      this.$emit('close')
    },
    moveRelation: function (row) {
      this.$util.move(this, 'LICENSE', row.id)
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
