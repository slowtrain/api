<!--
 Class Name  : ServiceRelationDialog.vue
 Description : 서비스 > 관련정보 > 관련 정보 등록 DIALOG
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.11.04  최영훈  최초 생성
-->
<template>
  <el-dialog :title="labels.ciDialogTitle" :visible.sync="showDialogFlag" width=1250px  v-on:close="close">
    <b-row>
      <b-col sm="3">
        <Tree
          v-bind:tree="tree"
          ref="tree"
          v-on:clickNode="clickNode"
        />
      </b-col>
      <b-col sm="9">
        <b-row>
          <b-col sm="12">
            <div class="search-component">
              <input class="form-control mt-1" style="width: 100%;" v-model="searchParam" v-on:keyup.enter="enterSearch" v-bind:placeholder=labels.search />
            </div>
          </b-col>
        </b-row>
        <b-row>
          <b-col sm="12" class='form-group'>
            <p class="grid-line"></p>
            <data-tables-server ref="listGrid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" :pagination-props="{pageSizes: [5, 10, 15] }" @selection-change="selectCIGridRow" :current-page.sync='listGrid.currentPage'>
              <el-table-column type="selection" width="60"></el-table-column>
              <el-table-column :prop='listGrid.titles[3].prop' :label='labels.ciId'             sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[4].prop' :label='labels.classstructureId' sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[5].prop' :label='labels.description'      sortable='custom' :width='listGrid.titles[5].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[6].prop' :label='labels.status'           sortable='custom' :width='listGrid.titles[6].width'></el-table-column>
              <el-table-column :label="labels.classtype" >
                <template slot-scope="props">
                  <el-select v-model="props.row.classType" :placeholder="labels.selectData" v-bind:disabled="props.row.chk" v-on:focus="focusClassType(props.row.hierarchypath)">
                    <el-option v-for="classType in classTypeSelect" :prop="classType.key" :label="classType.value" :value="classType.key" :key="classType.key">
                      {{classType.value}}
                    </el-option>
                  </el-select>
                </template>
              </el-table-column>
            </data-tables-server>
          </b-col>
        </b-row>
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
import Tree from '@/views/common/TreeCom'
export default {
  name: 'CI',
  props: ['labels', 'showDialogFlagProp', 'param'],
  components: {
    'Tree': Tree
  },
  data () {
    return {
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
          return true
        }
      },
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
          pageSize: 10,
          pageSizes: [10, 20, 30]
        },
        data: [],
        dataBak: [],
        filters: [{
          prop: ['id', 'orgId', 'ciId', 'classstructureNm', 'description', 'status'],
          value: ''
        }, {
          prop: 'tree',
          value: ''
        }],
        filteredData: [],
        selection: [],
        titles: [{
          prop: 'chk',
          width: '100px'
        }, {
          prop: 'id',
          width: '100px'
        }, {
          prop: 'orgId',
          width: '100px'
        }, {
          prop: 'ciId',
          width: '130px'
        }, {
          prop: 'classstructureNm',
          width: '150px'
        }, {
          prop: 'description',
          width: '200x'
        }, {
          prop: 'status',
          width: '120px'
        }],
        columns: ['id', 'orgId', 'ciId', 'classstructureId', 'description', 'status'],
        columnNames: ['id', 'orgId', 'ciId', 'classstructureId', 'description', 'status'],
        currentData: {},
        total: 0
      },
      // Combo Data
      classTypeSelect: [],
      classTypeData: [],
      // Global Var
      orgId: '',
      searchParam: '',
      ciDialogParam: '',
      showDialogFlag: false
    }
  },
  watch: {
    'showDialogFlagProp': function (val) {
      this.showDialogFlag = val
      if (val) {
        this.initialize()
        let chkedList = this.listGrid.data.filter((data) => data.chk === true)
        chkedList.forEach(data => {
          data.chk = false
        })
        this.$http.get(
          '/episode/api/system/common/classtree?orgId=' + this.orgId + '&object=' + this.param.object
        ).then(res => {
          this.tree.data = res.data
          this.listGrid.currentPage = 1
          this.searchParam = ''
          this.listGrid.filters[0].value = '/'
          this.listGrid.filters[0].value = ''
          this.listGrid.filters[1].value = ''
        }).catch(error => {
          console.log(error)
        })
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
    getInitData: function () {
      let param = {
        obj: this,
        paramList: [
          {dataObj: 'classTypeData', domainId: 'SERVICERELATIONTYPE'}
        ]
      }
      this.$util.getComboData(param)
    },
    getListData: function (srchCnd) {
      let page = 1
      let pageSize = 5
      let orderBy = 'id'
      let sort = 'ascending'
      let searchParam = ''
      let treeSearchParam = ''
      if (srchCnd !== null && srchCnd !== undefined && srchCnd !== 'undefined') {
        page = srchCnd.page
        pageSize = srchCnd.pageSize
        if (srchCnd.sort.prop != null) {
          orderBy = srchCnd.sort.prop
          sort = srchCnd.sort.order
        }
        searchParam = srchCnd.filters[0].value
        treeSearchParam = srchCnd.filters[1].value
      }
      this.$http.get(
        '/episode/api/asset/servicemgmnt/service?page=' + page + '&pageSize=' + pageSize + '&orderBy=' + orderBy + '&sort=' + sort +
        '&orgId=' + this.orgId + '&searchParam=' + encodeURI(searchParam) + '&treeSearchParam=' + treeSearchParam + '&object=' + this.param.object + '&ciDialogType=relation' + '&ciDialogParam=' + this.param.ciId
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
      }).catch(error => {
        console.log(error)
      })
    },
    // EVENT FN ///////////////////////////////////////////
    clickNode: function (node) {
      this.searchParam = ''
      this.listGrid.filters[0].value = ''
      if (!this.$util.isNull(node)) {
        let filterVal = (node.data.classstructureId === 'CLASSSTRUCTURE' ? '' : node.data.classstructureId)
        this.listGrid.filters[1].value = filterVal
      }
    },
    enterSearch: function () {
      this.listGrid.filters[0].value = this.searchParam
    },
    selectCIGridRow: function (selectedList) {
      this.listGrid.selection = selectedList
      this.listGrid.data.forEach(data => {
        data.chk = true
        let select = selectedList.filter((dataChk) => data.id === dataChk.id)
        if (select.length > 0) {
          select[0].chk = false
        } else {
          data.relationname = ''
          data.classType = ''
        }
      })
    },
    focusClassType: function (hierarchypath) {
      let classTypeSource = this.classTypeData.filter(data => (data.key === 'SOURCE'))
      let classTypeTarget = this.classTypeData.filter(data => (data.key === 'TARGET'))
      if (this.param.hierarchypath.indexOf('BUSINESSSYSTEM') > -1) {
        if (hierarchypath.indexOf('BUSINESSSYSTEM') > -1) {
          this.classTypeSelect = this.classTypeData
        } else if (hierarchypath.indexOf('GROUP_1') > -1) {
          this.classTypeSelect = classTypeTarget
        }
      } else if (this.param.hierarchypath.indexOf('GROUP_1') > -1) {
        if (hierarchypath.indexOf('BUSINESSSYSTEM') > -1) {
          this.classTypeSelect = classTypeSource
        } else if (hierarchypath.indexOf('GROUP_1') > -1) {
          this.classTypeSelect = this.classTypeData
        } else if (hierarchypath.indexOf('SOFTWARE') > -1 || hierarchypath.indexOf('HARDWARE') > -1 || hierarchypath.indexOf('DATAINTERFACE') > -1) {
          this.classTypeSelect = classTypeTarget
        }
      }
    },
    add: function () {
      this.$emit('add', this.listGrid.selection)
    },
    close: function () {
      this.$emit('close')
    }
  },
  created: function () {}
}
</script>
