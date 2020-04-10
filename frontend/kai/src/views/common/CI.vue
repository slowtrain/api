<template>
  <el-dialog :title="labels.ciDialogTitle" :visible.sync="showDialogFlag" width=930px  v-on:close="close">
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
          <b-col sm="12">
            <p class="grid-line"></p>
            <data-tables-server ref="listGrid" :table-props='listGrid.tableProps' :data="listGrid.data" :filters="listGrid.filters" :total="listGrid.total" v-on:query-change="getListData" :pagination-props="{pageSizes: [5, 10, 15] }" :current-page.sync='listGrid.currentPage'>
              <el-table-column :width='listGrid.titles[0].width'>
                <template slot-scope="props">
                  <el-checkbox class='single' v-model="props.row.chk" v-on:change="selectGridRow(props.row)"/>
                </template>
              </el-table-column>
              <el-table-column :prop='listGrid.titles[3].prop' :label='labels.ciId'             sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[4].prop' :label='labels.classstructureId' sortable='custom' :width='listGrid.titles[4].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[5].prop' :label='labels.description'      sortable='custom' ></el-table-column>
              <el-table-column :prop='listGrid.titles[6].prop' :label='labels.status'           sortable='custom' :width='listGrid.titles[6].width'></el-table-column>
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
  props: ['showDialogFlagProp', 'param'],
  components: {
    'Tree': Tree
  },
  data () {
    return {
      labels: {
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
          prop: ['id', 'orgId', 'ciId', 'classstructureId', 'description', 'status'],
          value: ''
        }, {
          prop: 'tree',
          value: ''
        }],
        filteredData: [],
        selection: [],
        titles: [{
          prop: 'chk',
          width: '30px'
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
          prop: 'classstructureId',
          width: '150px'
        }, {
          prop: 'description',
          width: '200x'
        }, {
          prop: 'status',
          width: '120px'
        }],
        total: 0
      },
      selectedItem: {},
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
        this.searchParam = ''
        this.listGrid.filters[0].value = ''
        this.selectedItem = {}
        setTimeout(() => {
          this.listGrid.currentPage = 1
          this.listGrid.filters[0].value = '/'
          this.listGrid.filters[0].value = ''
          this.listGrid.filters[1].value = ''
        }, 100)
      }
    }
  },
  methods: {
    // INIT FN ////////////////////////////////////////////
    initialize: function () {
      this.$util.setLabels(this.$options.name, this.labels)
      this.orgId = this.$store.getters.getUser.orgId
      this.getInitData()
    },
    // USER FN ////////////////////////////////////////////
    // TRNS FN ////////////////////////////////////////////
    getInitData: function () {
      this.$http.get(
        '/episode/api/system/common/classtree?orgId=' + this.orgId + '&object=' + this.param.classObj
      ).then(res => {
        this.tree.data = res.data
      }).catch(error => {
        console.log(error)
      })
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
        '/episode/api/asset/resource/ci?page=' + page + '&pageSize=' + pageSize + '&orderBy=' + orderBy + '&sort=' + sort +
        '&orgId=' + this.orgId + '&searchParam=' + encodeURI(searchParam) + '&treeSearchParam=' + treeSearchParam + '&ciDialogType=' + this.param.ciDialogType + '&ciDialogParam=' + this.param.ciDialogParam
      ).then(res => {
        this.listGrid.data = res.data.data
        this.listGrid.total = res.data.total
        let chkedList = this.listGrid.data.filter((data) => data.chk === true)
        chkedList.forEach(data => {
          data.chk = false
        })
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
    filterFocus: function () {
      // this.listGrid.filters[0].value = ''
    },
    enterSearch: function () {
      this.listGrid.filters[0].value = this.searchParam
    },
    selectGridRow: function (row) {
      let chkedList = this.listGrid.data.filter((data) => data.chk === true)
      chkedList.forEach(data => {
        if (data.id !== row.id) {
          data.chk = false
        }
      })
      this.selectedItem = row
    },
    add: function () {
      this.$emit('add', this.selectedItem)
    },
    close: function () {
      this.$emit('close')
    }
  },
  created: function () {}
}
</script>
