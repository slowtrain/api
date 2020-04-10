<!--
 Class Name  : LicenseCompany.vue
 Description : 라이선스 > 업체관리
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.28  최영훈  최초 생성
-->
<template>
  <div>
    <el-dialog :title="labels.company" width=1000px :visible.sync="showDialogFlag" v-on:close="close">
      <b-row>
        <b-col sm="12">
          <b-row>
            <b-col sm="12">
              <div class="search-component">
                <input class="form-control mt-1" style="width: 100%;" v-model="searchParam" v-on:keyup.enter="enterSearch" />
              </div>
            </b-col>
          </b-row>
          <b-row>
            <b-col sm="12">
              <p class="grid-line"></p>
              <data-tables layout='table, pagination' :table-props='listGrid.tableProps' :data='listGrid.data' :filters='listGrid.filters' :page-size='listGrid.pageProps.pageSize' :pagination-props='listGrid.pageProps' :current-page.sync='listGrid.currentPage'>
                <el-table-column :width='listGrid.titles[0].width' >
                  <template slot-scope="props">
                    <el-checkbox class='single' v-model="props.row.chk" v-on:change="selectGridRow(props.row)"/>
                  </template>
                </el-table-column>
                <el-table-column :prop='listGrid.titles[1].prop' :label='labels.id'           sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[2].prop' :label='labels.companyname'  sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[3].prop' :label='labels.description'  sortable='custom' ></el-table-column>
                <el-table-column :prop='listGrid.titles[4].prop' :label='labels.companytype'  sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[5].prop' :label='labels.companynum'   sortable='custom' :width='listGrid.titles[5].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[6].prop' :label='labels.companytel'   sortable='custom' :width='listGrid.titles[6].width'></el-table-column>
                <el-table-column :prop='listGrid.titles[7].prop' :label='labels.ownername'    sortable='custom' :width='listGrid.titles[7].width'></el-table-column>
              </data-tables>
            </b-col>
          </b-row>
        </b-col>
      </b-row>
      <b-row>
        <div class="col mt-2">
          <div class='d-flex justify-content-end'>
            <b-button variant="primary" class="px-3 sm" v-on:click="confirm" >{{labels.confirm}}</b-button>
            <b-button variant="primary" class="px-3 sm" v-on:click="showDialogFlag = false">{{labels.close}}</b-button>
          </div>
        </div>
      </b-row>
    </el-dialog>
  </div>
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
        dataBak: [],
        filters: [{
          prop: ['companyId', 'companyname', 'companydescription'],
          value: ''
        }],
        filteredData: [],
        selection: [],
        titles: [{
          prop: 'chk',
          width: '30px'
        }, {
          prop: 'companyId',
          width: '100px'
        }, {
          prop: 'companyname',
          width: '150px'
        }, {
          prop: 'companydescription',
          width: '200px'
        }, {
          prop: 'companytypenm',
          width: '100px'
        }, {
          prop: 'companynum',
          width: '120px'
        }, {
          prop: 'companytel',
          width: '130px'
        }, {
          prop: 'ownername',
          width: '100px'
        }],
        currentData: {},
        total: 0
      },
      selectedItem: {},
      // Gloval var
      orgId: '',
      // Gloval Flag
      showDialogFlag: false,
      searchParam: ''
    }
  },
  watch: {
    'showDialogFlagProp': function (val) {
      this.showDialogFlag = val
      if (val) {
        this.selectedItem = {}
        this.searchParam = ''
        this.listGrid.filters[0].value = ''
        this.listGrid.currentData = {}
        this.listGrid.currentPage = 1
        this.getListData()
      }
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.orgId = this.$store.getters.getUser.orgId
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    getInitData: function () {
      this.getListData()
    },
    getListData: function () {
      this.$http.get(
        '/episode/api/asset/management/company?orgId=' + this.orgId + '&companytype=' + this.param.companytype
      ).then(res => {
        this.listGrid.data = res.data.data
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    selectGridRow: function (row) {
      let chkedList = this.listGrid.data.filter((data) => data.chk === true)
      chkedList.forEach(data => {
        if (data.id !== row.id) {
          data.chk = false
        }
      })
      this.selectedItem = row
    },
    confirm: function () {
      this.$emit('confirm', this.selectedItem)
    },
    close: function () {
      this.$emit('close')
    },
    enterSearch: function () {
      this.listGrid.filters[0].value = this.searchParam
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
