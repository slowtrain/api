<!--
 Class Name  : SwcatRelationDialog.vue
 Description : 소프트웨어 카탈로그 > 카탈로그 그룹 추가 Dialog
 Modification Information
 수정일      수정자  수정내용
 =========   ====== =========
 2019.10.14  최영훈  최초 생성
-->
<template>
  <el-dialog :title="labels.swcatgroup" :visible.sync="showDialogFlag" width=1200px  v-on:close="close">
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
              <el-table-column :prop='listGrid.titles[1].prop' :width='listGrid.titles[0].width'>
                <template slot-scope="props">
                  <el-checkbox v-model="props.row.chk" v-on:change="selectGridRow(props.row)"/>
                </template>
              </el-table-column>
              <el-table-column :prop='listGrid.titles[1].prop' :label='labels.swcatId'          sortable='custom' :width='listGrid.titles[1].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[2].prop' :label='labels.classstructureId' sortable='custom' :width='listGrid.titles[2].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[3].prop' :label='labels.publisher'        sortable='custom' :width='listGrid.titles[3].width'></el-table-column>
              <el-table-column :prop='listGrid.titles[4].prop' :label='labels.name'             sortable='custom'></el-table-column>
              <el-table-column :prop='listGrid.titles[5].prop' :label='labels.swcattype'        sortable='custom'></el-table-column>
              <el-table-column :prop='listGrid.titles[6].prop' :label='labels.version'          sortable='custom'></el-table-column>
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
          pageSize: 5,
          pageSizes: [5, 10, 15]
        },
        data: [],
        filters: [{
          prop: ['classstructure_id', 'isdomestic', 'publishername', 'swcatname', 'swcatdescription'],
          value: ''
        }, {
          prop: 'tree',
          value: ''
        }],
        filteredData: [],
        titles: [{
          prop: 'chk',
          width: '30px'
        }, {
          prop: 'swcatId',
          width: '120px'
        }, {
          prop: 'classstructureNm',
          width: '180px'
        }, {
          prop: 'publishername',
          width: '200px'
        }, {
          prop: 'swcatname',
          width: '300x'
        }, {
          prop: 'swcattype',
          width: '300x'
        }, {
          prop: 'swcatversion',
          width: '300x'
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
      searchType: 'NORMAL',
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
        let chkedList = this.listGrid.data.filter((data) => data.chk === true)
        chkedList.forEach(data => {
          data.chk = false
        })
        this.listGrid.selection = []
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
    getInitData: function () {
      this.$http.get(
        '/episode/api/system/common/classtree?orgId=' + this.orgId + '&object=SWCAT'
      ).then(res => {
        this.tree.data = res.data
        this.tree.expandKeys = [res.data[0].children[0].id]
      }).catch(error => {
        console.log(error)
      })
    },
    getListData: function (srchCnd) {
      this.listGrid.srchCnd = srchCnd
      let page = 1
      let pageSize = 5
      let orderBy = null
      let sort = 'ascending'
      let searchParam = ''
      let treeSearchParam = ''
      if (!this.$util.isNull(srchCnd)) {
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
        '/episode/api/asset/management/swcat?page=' + page + '&pageSize=' + pageSize + '&orderBy=' + orderBy + '&sort=' + sort +
        '&orgId=' + this.orgId + '&searchParam=' + encodeURI(searchParam) + '&treeSearchParam=' + treeSearchParam + '&dialogType=group' + '&dialogParam=' + this.param.swcatId
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
    filterFocus: function () {
      this.listGrid.filters[0].value = ''
    },
    enterSearch: function () {
      this.listGrid.filters[0].value = this.searchParam
    },
    selectGridRow: function (select) {
      if (select.chk) {
        this.listGrid.selection.forEach(data => {
          let checkPath = data.hierarchypath
          let targetPath = select.hierarchypath
          if (checkPath.indexOf(targetPath) > -1) {
            this.$util.esmessage(this, 'warn', this.labels.notselect)
            select.chk = false
            return false
          }
          if (targetPath.indexOf(checkPath) > -1) {
            this.$util.esmessage(this, 'warn', this.labels.notselect)
            select.chk = false
            return false
          }
        })
        if (select.chk) {
          this.listGrid.selection.push(select)
        }
      } else {
        this.listGrid.selection.forEach((data, idx) => {
          if (data.id === select.id) {
            this.listGrid.selection.splice(idx)
            return false
          }
        })
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
