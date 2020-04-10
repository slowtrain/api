<!--
 Class Name  : LicenseRelationDialog.vue
 Description : 라이선스 > 관련 라이선스 > 관련라이선스 추가 Dialog
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.09.16  최영훈  최초 생성
-->
<template>
  <el-dialog :title="labels.licenseDialogTitle" :visible.sync="showDialogFlag" width=1000px  v-on:close="close">
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
            <data-tables-server ref="listGrid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" :page-size='listGrid.pageProps.pageSize' :pagination-props="{pageSizes: [5, 10, 15] }" @selection-change="selectCIGridRow" :current-page.sync='listGrid.currentPage'>
              <el-table-column type="selection" width="60"></el-table-column>
              <el-table-column :prop='listGrid.titles[0].prop' :label='labels.swlicenseId'    sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[1].prop' :label='labels.classstructure' sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[2].prop' :label='labels.name'           sortable='custom' ></el-table-column>
              <el-table-column :prop='listGrid.titles[3].prop' :label='labels.purchasetype'   sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[4].prop' :label='labels.startDt'        sortable='custom' :width='listGrid.titles[4].width'>
                <template slot-scope="props">
                  {{$util.date(props.row.startDt)}}
                </template>
              </el-table-column>
              <el-table-column :prop='listGrid.titles[5].prop' :label='labels.endDt'        sortable='custom' :width='listGrid.titles[5].width'>
                <template slot-scope="props">
                  {{$util.date(props.row.endDt)}}
                </template>
              </el-table-column>
            </data-tables-server>
          </b-col>
        </b-row>
    <b-row>
      <div class="col mt-2">
        <div class='d-flex justify-content-end'>
          <b-button variant="primary" class="px-3 sm" v-on:click="add" >{{labels.add}}</b-button>
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
          prop: [],
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'swlicenseId',
          width: '120px'
        }, {
          prop: 'swlictypeNm',
          width: '150px'
        }, {
          prop: 'swlicname',
          width: '180px'
        }, {
          prop: 'purchasetype',
          width: '120px'
        }, {
          prop: 'startDt',
          width: '100px'
        }, {
          prop: 'endDt',
          width: '100px'
        }],
        columns: [],
        columnNames: [],
        currentData: {},
        total: 0,
        srchCnd: null,
        selection: []
      },
      // Global Var
      orgId: '',
      searchType: 'relation',
      searchParam: '',
      ciDialogParam: '',
      showDialogFlag: false
    }
  },
  watch: {
    'showDialogFlagProp': function (val) {
      this.showDialogFlag = val
      if (val) {
        this.searchParam = ''
        let chkedList = this.listGrid.data.filter((data) => data.chk === true)
        chkedList.forEach(data => {
          data.chk = false
        })
        setTimeout(() => {
          this.listGrid.currentPage = 1
          this.listGrid.filters[0].value = '/'
          this.listGrid.filters[0].value = ''
        }, 100)
      }
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.orgId = this.$store.getters.getUser.orgId
      this.getInitData()
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    getInitData: function () {},
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 5
      let orderBy = 'id'
      let sort = 'ascending'
      let searchParam = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
      }
      this.$http.get(
        '/episode/api/asset/licensemgmnt/license?page=' + page + '&pageSize=' + pageSize + '&orderBy=' + orderBy + '&sort=' + sort +
        '&searchType=' + this.searchType + '&orgId=' + this.orgId + '&searchParam=' + encodeURI(searchParam) + '&dialogParam=' + this.param.swlicenseId
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    filterFocus: function () {
      this.listGrid.filters[0].value = ''
    },
    enterSearch: function () {
      this.listGrid.filters[0].value = this.searchParam
    },
    selectCIGridRow: function (selectedList) {
      this.listGrid.selection = selectedList
    },
    add: function () {
      this.$emit('add', this.listGrid.selection)
    },
    close: function () {
      this.$emit('close')
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
