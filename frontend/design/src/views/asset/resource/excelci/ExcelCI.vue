<!--
 Class Name  : ExcelCI.vue
 Description : 엑셀 IT자원
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.12.26  이정호  최초 생성
-->
<template>
  <div class="animated fadeIn">
    <b-card>
      <b-row>
        <b-col sm="12">
          <div class="comm-btn-box" role="group" style="height:40px;">
          </div>
        </b-col>
      </b-row>
      <p class="grid-line"></p>
        <b-row>
          <b-col sm="4">
             <div class='search-component'>
             <input class="form-control excel-1" :placeholder='labels.clsSearch' readonly v-model="parentName" style="background-color: white"/>
               <a class="search-button cursor-pointer"  v-on:click="openParentDialog" style="top: 7px">search</a>
             </div>
          </b-col>
      <b-col sm="2">
          <b-button  style='backgroundColor: white; color: #606266; font-weight: bolder;' v-on:click='setFilters' class='px-3 sm excel-1'>{{labels.search}}</b-button>

      </b-col>
          <b-col>
            <div class="float-right mt-1" role="group">
              <el-button v-on:click="exportFilter" :title="labels.excelDownload" style="padding: 0 0 0 0; width:40px; height: 40px;">
                <img src="../../../../assets/img/icons/icon_search_download.png" width="60%" />
              </el-button>
            </div>
          </b-col>
        </b-row>
        <b-row>
          <b-col sm="12">
            <p class="grid-line"></p>
            <data-tables-server ref="grid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" :page-size='listGrid.pageProps.pageSize' :pagination-props="listGrid.pageProps" :current-page.sync='listGrid.currentPage'>
              <el-table-column :prop='listGrid.titles[0].prop' :label='labels.ciId'             sortable='custom' :width='listGrid.titles[0].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[1].prop' :label='labels.classstructureId' sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[2].prop' :label='labels.name'             sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[3].prop' :label='labels.description'      sortable='custom' ></el-table-column>
              <el-table-column :prop='listGrid.titles[4].prop' :label='"ITSM CIID"'             sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[5].prop' :label='labels.status'           sortable='custom' :width='listGrid.titles[5].width'></el-table-column>
            </data-tables-server>
          </b-col>
        </b-row>
        <!-- Parent Dialog -->
        <el-dialog :title="labels.parentDialogTitle" :visible.sync="showParentDialogFlag" width=290px>
          <b-row>
            <div class="col">
              <div class="treeDialog">
                <TreeParent v-bind:tree="tree" ref="tree"/>
              </div>
            </div>
          </b-row>
          <b-row>
            <div class="col mt-2">
              <div class='d-flex justify-content-end'>
                <b-button variant="primary" class="px-3 sm" v-on:click="confirmParentDialog">{{labels.confirm}}</b-button>
                <b-button variant="primary" class="px-3 sm" v-on:click="cancleParentDialog">{{labels.cancle}}</b-button>
              </div>
            </div>
          </b-row>
        </el-dialog>
    </b-card>
  </div>
</template>
<script>
import Tree from '@/views/common/TreeCom'
export default {
  name: 'EXCELCI',
  components: {
    'TreeParent': Tree
  },
  data () {
    return {
      labels: {
        clsSearch: '',
        noDataMessage: '',
        selectNullMsg: '',
        cancle: '',
        parentDialogTitle: '',
        excelDownload: '',
        status: '',
        search: '',
        name: '',
        description: '',
        confirm: '',
        classstructureId: '',
        ciId: ''
      },
      tree: {
        data: [],
        expandKeys: ['root'],
        selectData: {},
        showTreeFlag: true,
        showIconFlag: true,
        showCntFlag: false,
        showNodeIconFn: function (data) {
          return data.classiconsvg
        },
        shwoAddIconFn: function (data) {
          return false
        }
      },
      listGrid: {
        tableProps: {
          border: false,
          stripe: false,
          headerCellStyle: function () {
            return 'font-family: sans-serif; font-size: 14px; color: #28313b; font-weight: bold; height:60px'
          }
        },
        currentPage: 1,
        rowIndex: -1,
        pageProps: {
          pageSize: 10,
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: ['id', 'orgId', 'ciId', 'classstructureId', 'description', 'status'],
          value: ''
        }, {
          prop: 'tree',
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'ciId',
          width: '180px'
        }, {
          prop: 'classstructureNm',
          width: '200px'
        }, {
          prop: 'ciname',
          width: '250px'
        }, {
          prop: 'description',
          width: '250x'
        }, {
          prop: 'extaln18',
          width: '180px'
        }, {
          prop: 'status',
          width: '150px'
        }],
        columns: ['id', 'orgId', 'ciId', 'classstructureId', 'description', 'status'],
        columnNames: ['id', 'orgId', 'ciId', 'classstructureId', 'description', 'status'],
        currentData: {},
        ciExtNmData: {},
        total: 0,
        srchCnd: null
      },
      parent: null,
      parentName: '',
      ancestor: '',
      showParentDialogFlag: false,
      hardWareFields: [],
      softwareFields: []
    }
  },
  methods: {
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      this.orgId = this.$store.getters.getUser.orgId
      this.excelFields()
    },
    setFilters: function () {
      if (this.$util.isNull(this.parent)) {
        this.$util.esmessage(this, 'warn', this.labels.selectNullMsg)
        return
      }
      this.listGrid.filters[1].value = this.parent
      this.listGrid.currentPage = 1
    },
    excelFields: function () {
      this.$http.get(
        '/episode/api/asset/resource/excelci/property', {params: {'ancestor': 'HARDWARE'}}
      ).then(res => {
        this.hardWareFields = [
          {value: 'ciId', label: res.data.ciId},
          {value: 'classstructureNm', label: res.data.ciId},
          {value: 'ciname', label: res.data.ciId},
          {value: 'description', label: res.data.ciId},
          {value: 'status', label: res.data.ciId},
          {value: 'extaln1', label: res.data.extaln1},
          {value: 'extaln2', label: res.data.extaln2},
          {value: 'extaln3', label: res.data.extaln3},
          {value: 'extaln4', label: res.data.extaln4},
          {value: 'extaln5', label: res.data.extaln5},
          {value: 'extaln6', label: res.data.extaln6},
          {value: 'extaln7', label: res.data.extaln7},
          {value: 'extaln8', label: res.data.extaln8},
          {value: 'extaln9', label: res.data.extaln9},
          {value: 'extaln10', label: res.data.extaln10},
          {value: 'extaln11', label: res.data.extaln11},
          {value: 'extaln12', label: res.data.extaln12},
          {value: 'extaln13', label: res.data.extaln13},
          {value: 'extaln14', label: res.data.extaln14},
          {value: 'extaln15', label: res.data.extaln15},
          {value: 'extaln16', label: res.data.extaln16},
          {value: 'hardwareip', label: res.data.extaln17},
          {value: 'extaln18', label: res.data.extaln18},
          {value: 'extnum1', label: res.data.extnum1},
          {value: 'extnum2', label: res.data.extnum2},
          {value: 'extnum3', label: res.data.extnum3},
          {value: 'extnum4', label: res.data.extnum4},
          {value: 'extnum5', label: res.data.extnum5},
          {value: 'extdate1', label: res.data.extdate1},
          {value: 'extdate2', label: res.data.extdate2},
          {value: 'extbool1', label: res.data.extbool1},
          {value: 'extbool2', label: res.data.extbool2},
          {value: 'extbool3', label: res.data.extbool3}
        ]
      }).catch(error => {
        console.log(error)
      })
      this.$http.get(
        '/episode/api/asset/resource/excelci/property', {params: {'ancestor': 'SOFTWARE'}}
      ).then(res => {
        this.softwareFields = [
          {value: 'ciId', label: res.data.ciId},
          {value: 'classstructureNm', label: res.data.ciId},
          {value: 'ciname', label: res.data.ciId},
          {value: 'description', label: res.data.ciId},
          {value: 'status', label: res.data.ciId},
          {value: 'extaln1', label: res.data.extaln1},
          {value: 'extaln2', label: res.data.extaln2},
          {value: 'softwareip', label: res.data.extaln3},
          {value: 'extaln4', label: res.data.extaln4},
          {value: 'extaln5', label: res.data.extaln5},
          {value: 'extaln6', label: res.data.extaln6},
          {value: 'extaln7', label: res.data.extaln7},
          {value: 'extaln8', label: res.data.extaln8},
          {value: 'extaln9', label: res.data.extaln9},
          {value: 'extaln10', label: res.data.extaln10},
          {value: 'extaln11', label: res.data.extaln11},
          {value: 'extaln18', label: res.data.extaln18},
          {value: 'extdate1', label: res.data.extdate1},
          {value: 'extbool3', label: res.data.extbool3}
        ]
      }).catch(error => {
        console.log(error)
      })
    },
    getListData: function (srchCnd) {
      if (!this.parent) {
        return
      }
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 10
      let orderBy = 'update_dt'
      let sort = 'descending'
      let treeSearchParam = ''
      if (!this.$util.isNull(srchCnd)) {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        treeSearchParam = srchCnd.filters[1].value
      }
      let params = {params: {
        page: page,
        pageSize: pageSize,
        orderBy: orderBy,
        sort: sort,
        treeSearchParam: treeSearchParam}
      }
      this.$http.get(
        '/episode/api/asset/resource/excelci',
        params
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
        if (this.listGrid.total === 0) {
          this.$util.esmessage(this, 'warn', this.labels.noDataMessage)
          return false
        }
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    exportFilter: function () {
      if (this.listGrid.total === 0) {
        this.$util.esmessage(this, 'warn', this.labels.noDataMessage)
        return
      }
      let treeSearchParam = this.listGrid.filters[1].value
      let srchCnd = this.listGrid.srchCnd
      let orderBy = 'update_dt'
      let sort = 'descending'
      if (srchCnd != null && srchCnd !== undefined && srchCnd !== 'undefined') {
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
      }
      let params = {params: {
        orderBy: orderBy,
        sort: sort,
        treeSearchParam: treeSearchParam}
      }
      this.$http.get(
        '/episode/api/asset/resource/excelci',
        params
      ).then(res => {
        let fileName = 'excelci'
        this.$util.csvExport(res.data.data, (this.ancestor === 'HARDWARE' ? this.hardWareFields : this.softwareFields), this.listGrid.columnNames, fileName)
      }).catch(error => {
        console.log(error)
      })
    },
    openParentDialog: function () {
      this.$http.get(
        '/episode/api/system/common/classtree?orgId=' + this.orgId + '&object=EXCELCI'
      ).then(res => {
        this.tree.data = res.data
      }).catch(error => {
        console.log(error)
      })
      this.showParentDialogFlag = true
    },
    confirmParentDialog: function () {
      let treedata = this.$refs.tree.$refs.tree.getCurrentNode()
      if (this.$util.isNull(treedata)) {
        this.$util.esmessage(this, 'warn', this.labels.selectNullMsg)
        return
      }
      if (treedata.data.classstructureId !== 'CLASSSTRUCTURE') {
        this.parent = treedata.data.classstructureId
        this.parentName = treedata.data.description
        this.ancestor = (treedata.data.hierarchypath.substring(1)).split('/')[0]
        this.showParentDialogFlag = false
      } else {
        this.$util.esmessage(this, 'warn', this.labels.selectNullMsg)
        return false
      }
    },
    cancleParentDialog: function () {
      this.showParentDialogFlag = false
      // this.tree.data = []
      // this.parent = null
      // this.parentName = ''
      // this.ancestor = ''
    }
  },
  created: function () {
    this.initialize()
  }
}
</script>
